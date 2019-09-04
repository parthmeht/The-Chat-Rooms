package com.app.thechatrooms.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.thechatrooms.R;
import com.app.thechatrooms.models.User;
import com.app.thechatrooms.utilities.Parameters;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class ProfileFragment extends Fragment {

    private User user;
    private static final String TAG = "ProfileFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView displayName = root.findViewById(R.id.profile_displayNameTextView);
        final TextView emailId = root.findViewById(R.id.profile_emailIdTextView);
        final TextView city = root.findViewById(R.id.profile_cityTextView);
        final TextView gender = root.findViewById(R.id.profile_genderTextView);
        final ImageView profileImage = root.findViewById(R.id.profile_imageView);

        user = (User) getArguments().getSerializable(Parameters.USER_ID);
        Log.v(TAG, user.toString());
        displayName.setText(user.getFirstName() + " " + user.getLastName());
        emailId.setText(user.getEmailId());
        city.setText(user.getCity());
        gender.setText(user.getGender());
        Picasso.get()
                .load(user.getUserProfileImage())
                .transform(new CropCircleTransformation())
                .fit()
                .centerCrop()
                .into(profileImage);
        return root;
    }
}