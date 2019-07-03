package rs.ispit.projekat;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import rs.ispit.projekat.model.Event;

public class EventListAdapter extends ArrayAdapter<Event> implements View.OnClickListener{

    private Context mContext;
    private int mResource;
    private List<Event> items;

    public EventListAdapter(@NonNull Context context, int resource, @NonNull List<Event> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        items = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, ''yy h:mm a");

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        switch (getItem(position).getEventType()) {
            case KLUB:
                ((ImageView) convertView.findViewById(R.id.event_image)).setImageResource(R.drawable.klub);
                break;
            case SPORT:
                ((ImageView) convertView.findViewById(R.id.event_image)).setImageResource(R.drawable.sport);
                break;
            case KONCERT:
                ((ImageView) convertView.findViewById(R.id.event_image)).setImageResource(R.drawable.koncert);
                break;
            case PRIRODA:
                ((ImageView) convertView.findViewById(R.id.event_image)).setImageResource(R.drawable.priroda);
                break;
        }
        ((TextView) convertView.findViewById(R.id.event_name)).setText(getItem(position).getName());
        ((TextView) convertView.findViewById(R.id.event_location)).setText(getItem(position).getLocation());
        ((TextView) convertView.findViewById(R.id.event_dateFrom)).setText(format.format(getItem(position).getDateFrom()));
        convertView.findViewById(R.id.event_more).setTag(getItem(position).getId());
        convertView.findViewById(R.id.event_more).setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.event_more:
                EventFragment eventFragment = new EventFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", (int)v.findViewById(v.getId()).getTag());
                eventFragment.setArguments(bundle);
                ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, eventFragment).commit();
                break;
        }
    }
}
