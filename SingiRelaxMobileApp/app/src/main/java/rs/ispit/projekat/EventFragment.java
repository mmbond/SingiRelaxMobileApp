package rs.ispit.projekat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import rs.ispit.projekat.api.Api;
import rs.ispit.projekat.api.ReadDataHandler;
import rs.ispit.projekat.model.Event;
import rs.ispit.projekat.model.User;

public class EventFragment extends Fragment implements View.OnClickListener {

    private User user;
    private Event event;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int id = bundle.getInt("id");
            for (Event e : Event.getEvents()) {
                if (e.getId().equals(id)) {
                    event = e;
                    break;
                }
            }
            for (Event e : User.getEvents()) {
                if (e.getId().equals(id)) {
                    event = e;
                    break;
                }
            }
        }
        if (event != null) {
            switch (event.getEventType()) {
                case KLUB:
                    ((ImageView) view.findViewById(R.id.event_image)).setImageResource(R.drawable.klub);
                    break;
                case SPORT:
                    ((ImageView) view.findViewById(R.id.event_image)).setImageResource(R.drawable.sport);
                    break;
                case KONCERT:
                    ((ImageView) view.findViewById(R.id.event_image)).setImageResource(R.drawable.koncert);
                    break;
                case PRIRODA:
                    ((ImageView) view.findViewById(R.id.event_image)).setImageResource(R.drawable.priroda);
                    break;
            }
            ((TextView) view.findViewById(R.id.event)).setText(event.getName());
            ((TextView) view.findViewById(R.id.events_location)).setText("Lokacija: " + event.getLocation());
            SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, ''yy h:mm a");
            ((TextView) view.findViewById(R.id.events_dateFrom)).setText("Datum od: " + format.format(event.getDateFrom()));
            ((TextView) view.findViewById(R.id.events_dateTo)).setText("Datum do: " + format.format(event.getDateTo()));
            ((TextView) view.findViewById(R.id.events_description)).setText("Opis: " + event.getDescription());
            ((TextView) view.findViewById(R.id.events_attendance)).setText("Poseta: " + event.getAttendance().toString());
            ifApplied();
            view.findViewById(R.id.event_apply).setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.event_apply:
                try {
                    apply();
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), R.string.event_apply_error, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void ifApplied() {
        for (Event e : User.getEvents()) {
            if (e.getId().equals(event.getId())) {
                ((Button) view.findViewById(R.id.event_apply)).setText(R.string.event_apply_already);
                view.findViewById(R.id.event_apply).setEnabled(false);
                view.findViewById(R.id.event_apply).setBackgroundColor(getResources().getColor(R.color.white));
                ((Button)view.findViewById(R.id.event_apply)).setTextColor(getResources().getColor(R.color.colorPrimary));
                return;
            }
        }
    }

    private void apply() throws JSONException {
        User.getEvents().add(event);

        final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Prijavljujete se...");
        progressDialog.show();

        sendData();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (user != null) {
                            ifApplied();
                            onEventApplySuccess();
                        } else {
                            onEventApplyFail();
                        }
                        progressDialog.dismiss();

                    }
                }, 1000);
    }

    @SuppressLint("HandlerLeak")
    private void sendData() throws JSONException {
        user = new User();
        JSONObject data = new JSONObject(user.toString());
        Api.putDataJSON("http://10.0.2.2:8181/users/update", data, new ReadDataHandler() {
            @Override
            public void handleMessage(Message msg) {
                try {
                    user = User.parseJSONObject(new JSONObject(getJson()));
                } catch (JSONException ignore) {
                }
            }
        });
    }

    private void onEventApplySuccess() {
        Toast.makeText(getActivity(), R.string.event_apply_success, Toast.LENGTH_SHORT).show();
    }

    private void onEventApplyFail() {
        Toast.makeText(getActivity(), R.string.event_apply_error, Toast.LENGTH_SHORT).show();
    }
}
