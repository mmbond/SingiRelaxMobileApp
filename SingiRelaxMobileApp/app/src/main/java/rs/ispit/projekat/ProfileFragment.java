package rs.ispit.projekat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rs.ispit.projekat.model.User;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ((TextView)view.findViewById(R.id.profile)).append(User.getFirstName()+" "+User.getLastName());
        ((TextView)view.findViewById(R.id.profile_email)).append(User.getEmail());
        if (!User.getPhone().equals("null")) {
            ((TextView)view.findViewById(R.id.profile_phone)).append(User.getPhone());
        }
        if (!User.getAddress().equals("null")) {
            ((TextView) view.findViewById(R.id.profile_address)).append(User.getAddress());
        }
        if (!User.getInterests().equals("null")) {
            ((TextView) view.findViewById(R.id.profile_interests)).append(User.getInterests());
        }
        view.findViewById(R.id.profile_edit).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_edit:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileEditFragment()).commit();
                break;
        }
    }
}
