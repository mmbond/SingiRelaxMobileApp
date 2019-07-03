package rs.ispit.projekat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import rs.ispit.projekat.api.Api;
import rs.ispit.projekat.api.ReadDataHandler;
import rs.ispit.projekat.model.Event;
import rs.ispit.projekat.model.User;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.draw_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EventsFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_events);
        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_events:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EventsFragment()).commit();
                break;
            case R.id.nav_notifications:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotificationsFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
            case R.id.nav_logout:
                Intent intents = new Intent(this, LoginActivity.class);
                intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intents);
                finish();
                break;
            case R.id.nav_share:
                Toast.makeText(this, R.string.share, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_send:
                Toast.makeText(this, R.string.send, Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @SuppressLint("HandlerLeak")
    private void initData() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            try {
                user = User.parseJSONObject(new JSONObject(bundle.getString("user")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        Api.getJSON("http://10.0.2.2:8181/events/all", new ReadDataHandler() {
//            @Override
//            public void handleMessage(Message msg) {
//                String odgovor = getJson();
//                try {
//                    JSONArray array = new JSONArray(odgovor);
//                    // ovde mi treba da se JSONArray parsira u List<Event> objekata
//                    Event.setEvents(Event.parseJSONArray(array));
//
//
//                } catch (Exception e) {
//                    //ovde bi isla neka obrada greske
//                }
//            }
//        });

//        Api.getJSON("http://10.0.2.2:8181/users/all", new ReadDataHandler(){
//            @Override
//            public void handleMessage(Message msg) {
//                String odgovor = getJson();
//                try {
//                    JSONArray array = new JSONArray(odgovor);
//                    // ovde mi treba da se JSONArray parsira u List<User> objekata
//                    List<User> users =  User.parseJSONArray(array);
//
//                    TextView labelJson = findViewById(R.id.events);
//                    for(User user : users){
//                        String prikaz = users.toString();
//                        labelJson.append(prikaz);
//                    }
//
//                } catch (Exception e){
//                    //ovde bi isla neka obrada greske
//                }
//            }
//        });

    }


}
