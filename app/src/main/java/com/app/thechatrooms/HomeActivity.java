package com.app.thechatrooms;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.app.thechatrooms.models.User;
import com.app.thechatrooms.utilities.Parameters;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private AppBarConfiguration mAppBarConfiguration;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private User user;
    // Write a message to the database
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String userId;
    StorageReference storageReference;
    ImageView userProfileImageView;
    TextView displayNameTextView;
    TextView displayEmailIdTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = preferences.getString(Parameters.USER_ID, "");
        if(!userId.equalsIgnoreCase("")) {
            myRef = database.getReference("chatRooms/userProfiles/"+userId);
            storageReference = mStorageRef.child("chatRooms/userProfiles/"+userId+".jpg");
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });
        TextView logOut = findViewById(R.id.logout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                finish();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_chats, R.id.nav_profile, R.id.nav_contacts,
                R.id.nav_groups)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View hView =  navigationView.getHeaderView(0);
        displayNameTextView = hView.findViewById(R.id.displayNameTextView);
        displayEmailIdTextView = hView.findViewById(R.id.displayEmailIdTextView);
        userProfileImageView = hView.findViewById(R.id.userProfileImageView);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                user = dataSnapshot.getValue(User.class);
                displayNameTextView.setText(user.getFirstName()+ " " + user.getLastName());
                displayEmailIdTextView.setText(user.getEmailId());
                Log.d(TAG, "Value is: " + user.toString());
                File localFile = null;
                try {
                    localFile = File.createTempFile("images", "jpg");
                    File finalLocalFile = localFile;
                    storageReference.getFile(localFile)
                            .addOnSuccessListener(taskSnapshot -> {
                                user.setUserProfileImage(Uri.fromFile(finalLocalFile));
                                Picasso.get()
                                        .load(user.getUserProfileImage())
                                        .transform(new CropCircleTransformation())
                                        .fit()
                                        .centerCrop()
                                        .into(userProfileImageView);
                            }).addOnFailureListener(exception -> {
                        exception.printStackTrace();
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
