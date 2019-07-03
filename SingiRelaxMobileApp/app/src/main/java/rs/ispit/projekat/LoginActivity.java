package rs.ispit.projekat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rs.ispit.projekat.api.Api;
import rs.ispit.projekat.api.ReadDataHandler;

import rs.ispit.projekat.model.Event;
import rs.ispit.projekat.model.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        initData();

        findViewById(R.id.button_login).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    private void initData() {
        Api.getJSON("http://10.0.2.2:8181/events/all", new ReadDataHandler() {
            @Override
            public void handleMessage(Message msg) {
                String odgovor = getJson();
                try {
                    JSONArray array = new JSONArray(odgovor);
                    // ovde mi treba da se JSONArray parsira u List<Event> objekata
                    Event.setEvents(Event.parseJSONArray(array));

                } catch (Exception e) {
                    //ovde bi isla neka obrada greske
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                try {
                    login();
                } catch (JSONException e) {
                    Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.register:
                Intent intent = new Intent(this, SignupActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void login() throws JSONException {

        final String email = ((TextView) findViewById(R.id.input_email)).getText().toString();
        final String password = ((TextView) findViewById(R.id.input_password)).getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
        } else {

            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                    R.style.Theme_AppCompat_DayNight_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Proverava se...");
            progressDialog.show();

            authenticate(email, password);

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            if (user != null) {
                                onLoginSuccess();
                            } else {
                                onLoginFail();
                            }
                            progressDialog.dismiss();

                        }
                    }, 1000);
        }
    }

    @SuppressLint("HandlerLeak")
    private void authenticate(String email, String password) throws JSONException {
        JSONObject data = new JSONObject();
        data.accumulate("email", email);
        data.accumulate("password", password);
        Api.postDataJSON("http://10.0.2.2:8181/users/authenticate", data, new ReadDataHandler() {
            @Override
            public void handleMessage(Message msg) {
                try {
                    user = User.parseJSONObject(new JSONObject(getJson()));
                } catch (JSONException ignore) {}
            }
        });

    }

    private void onLoginSuccess() {
        Bundle bundle = new Bundle();
        bundle.putString("user", user.toString());

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void onLoginFail() {
        Toast.makeText(this, R.string.login_error, Toast.LENGTH_SHORT).show();
    }

}
