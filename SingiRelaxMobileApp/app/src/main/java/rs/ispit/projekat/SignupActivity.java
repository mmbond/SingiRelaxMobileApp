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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import rs.ispit.projekat.api.Api;
import rs.ispit.projekat.api.ReadDataHandler;
import rs.ispit.projekat.model.User;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        findViewById(R.id.button_signup).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_signup:
                try {
                    signUp();
                } catch (JSONException e) {
                    Toast.makeText(this, R.string.error_data_signup, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void signUp() throws JSONException {

        final String email = ((TextView) findViewById(R.id.signup_email)).getText().toString();
        final String password = ((TextView) findViewById(R.id.signup_password)).getText().toString();
        final String fistName = ((TextView) findViewById(R.id.signup_firstName)).getText().toString();
        final String lastName = ((TextView) findViewById(R.id.signup_lastName)).getText().toString();
        final String phone = ((TextView) findViewById(R.id.signup_phone)).getText().toString();
        final String address = ((TextView) findViewById(R.id.signup_address)).getText().toString();
        final String interests = ((TextView) findViewById(R.id.signup_interest)).getText().toString();

        if (email.isEmpty() || password.isEmpty() || fistName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || address.isEmpty() || interests.isEmpty() ) {
            Toast.makeText(this, R.string.error_signup, Toast.LENGTH_SHORT).show();
        } else if (!email.matches("^(.+)@(.+)$") || password.length()<8 || !fistName.matches("^[A-Z]([a-z]+)$")
                || !lastName.matches("^[A-Z]([a-z]+)$") || phone.length()<10 || !address.matches("^[[A-z]+\\s]+\\d+$")){
            Toast.makeText(this, R.string.error_match_signup, Toast.LENGTH_SHORT).show();
        } else {
            user = new User(fistName, lastName, email, password, phone, address, new Date(), interests);
            final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                    R.style.Theme_AppCompat_DayNight_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Registrujete se...");
            progressDialog.show();

            sendData();

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            if (user != null) {
                                onSignUpSuccess();
                            } else {
                                onSignUpFail();
                            }
                            progressDialog.dismiss();

                        }
                    }, 1000);
        }
    }

    @SuppressLint("HandlerLeak")
    private void sendData() throws JSONException {
        JSONObject data = new JSONObject(user.toString());
        Api.postDataJSON("http://10.0.2.2:8181/users/save", data, new ReadDataHandler() {
            @Override
            public void handleMessage(Message msg) {
                try {
                    user = User.parseJSONObject(new JSONObject(getJson()));
                } catch (JSONException ignore) {
                }
            }
        });

    }

    private void onSignUpSuccess() {
        Bundle bundle = new Bundle();
        bundle.putString("user", user.toString());

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void onSignUpFail() {
        Toast.makeText(this, R.string.error_signup, Toast.LENGTH_SHORT).show();
    }

}
