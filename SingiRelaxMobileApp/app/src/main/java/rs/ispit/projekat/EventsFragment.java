package rs.ispit.projekat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import rs.ispit.projekat.model.Event;
import rs.ispit.projekat.model.User;

public class EventsFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    private ListView listView;
    private List<Event> data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        data = Event.getEvents();
        for (Event e : User.getEvents()) {
            for (Event event : Event.getEvents()) {
                if (e.getId().equals(event.getId())) {
                    data.remove(event);
                    break;
                }
            }
        }
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        listView = view.findViewById(R.id.event_list);
        final EventListAdapter adapter = new EventListAdapter(getContext(), R.layout.events_view, data);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Pronađite");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (newText == null || newText.trim().isEmpty()) {
            listView.setAdapter(new EventListAdapter(getContext(), R.layout.events_view, data));
            return false;
        }


        List<Event> results = new ArrayList<>(data);
        for (Event event : data) {
            if (!event.getName().toLowerCase().contains(newText.toLowerCase()) && !event.getDescription().toLowerCase().contains(newText.toLowerCase()) && !event.getLocation().toLowerCase().contains(newText.toLowerCase()) && !event.getEventType().toString().toLowerCase().contains(newText.toLowerCase())) {
                results.remove(event);
            }
        }
        listView.setAdapter(new EventListAdapter(getContext(), R.layout.events_view, results));

        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }

}
