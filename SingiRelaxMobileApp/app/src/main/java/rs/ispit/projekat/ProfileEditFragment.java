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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import rs.ispit.projekat.api.Api;
import rs.ispit.projekat.api.ReadDataHandler;
import rs.ispit.projekat.model.User;

public class ProfileEditFragment extends Fragment implements View.OnClickListener {
    private View view;
    private User user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        view.findViewById(R.id.profile_edit_send).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_edit_send:
                try {
                    if (setData()) {
                        editProfile();
                    } else {
                        Toast.makeText(getActivity(), R.string.error_edit_profile, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), R.string.error_data_edit_profile, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void editProfile() throws JSONException {

        final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Menjaju se podaci...");
        progressDialog.show();

        sendData();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (user != null) {
                            onEditProfileSuccess();
                        } else {
                            onEditProfileFail();
                        }
                        progressDialog.dismiss();

                    }
                }, 1000);
    }

    @SuppressLint("HandlerLeak")
    private void sendData() throws JSONException {
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

    private void onEditProfileSuccess() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
    }

    private void onEditProfileFail() {
        Toast.makeText(getActivity(), R.string.error_data_edit_profile, Toast.LENGTH_SHORT).show();
    }

    private boolean setData() {
        final String email = ((TextView) view.findViewById(R.id.edit_profile_email)).getText().toString();
        final String password = ((TextView) view.findViewById(R.id.edit_profile_password)).getText().toString();
        final String fistName = ((TextView) view.findViewById(R.id.edit_profile_firstName)).getText().toString();
        final String lastName = ((TextView) view.findViewById(R.id.edit_profile_lastName)).getText().toString();
        final String phone = ((TextView) view.findViewById(R.id.edit_profile_phone)).getText().toString();
        final String address = ((TextView) view.findViewById(R.id.edit_profile_address)).getText().toString();
        final String interests = ((TextView) view.findViewById(R.id.edit_profile_interest)).getText().toString();

        user = new User();
        boolean editProfile = false;
        if (!email.isEmpty() && email.matches("^(.+)@(.+)$")) {
            user.setEmail(email);
            editProfile = true;
        }
        if (!password.isEmpty() && password.length()>=8) {
            user.setPassword(password);
            editProfile = true;
        }
        if (!fistName.isEmpty() && fistName.matches("^[A-Z]([a-z]+)$")) {
            user.setFirstName(fistName);
            editProfile = true;
        }
        if (!lastName.isEmpty() && lastName.matches("^[A-Z]([a-z]+)$")) {
            user.setLastName(lastName);
            editProfile = true;
        }
        if (!phone.isEmpty() && phone.length()>=10) {
            user.setPhone(phone);
            editProfile = true;
        }
        if (!address.isEmpty() && address.matches("^[[A-z]+\\s]+\\d+$")) {
            user.setAddress(address);
            editProfile = true;
        }
        if (!interests.isEmpty()) {
            user.setInterests(interests);
            editProfile = true;
        }
        return editProfile;
    }
}
