package com.app.thechatrooms;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.app.thechatrooms.models.GroupChatRoom;
import com.app.thechatrooms.models.User;
import com.app.thechatrooms.ui.chats.ChatsFragment;
import com.app.thechatrooms.ui.contacts.ContactsFragment;
import com.app.thechatrooms.ui.groups.GroupsFragment;
import com.app.thechatrooms.ui.profile.ProfileFragment;
import com.app.thechatrooms.utilities.Parameters;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity";
    //private AppBarConfiguration mAppBarConfiguration;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    //private User user;
    private User user;
    private DrawerLayout drawer;
    // Write a message to the database
    private FirebaseDatabase database;
    private DatabaseReference myRef, groupChatDbRef;
    private String userId;
    private String groupName;
    StorageReference storageReference;
    ImageView userProfileImageView;
    TextView displayNameTextView;
    TextView displayEmailIdTextView;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = preferences.getString(Parameters.USER_ID, "");
        myRef = database.getReference("chatRooms/userProfiles/");
        myRef.child(userId).child("isOnline").setValue(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Initialize Firebase Auth
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = preferences.getString(Parameters.USER_ID, "");
        groupChatDbRef = database.getReference("chatRooms/groupChatRoom/");
        if(!userId.equalsIgnoreCase("")) {
            myRef = database.getReference("chatRooms/userProfiles/"+userId);
            storageReference = mStorageRef.child("chatRooms/userProfiles/"+userId+".jpg");
            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    user = dataSnapshot.getValue(User.class);
//                    new ChangeOnlineStatus().execute();
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView logOut = findViewById(R.id.logout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                finish();
            }
        });
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View hView =  navigationView.getHeaderView(0);
        displayNameTextView = hView.findViewById(R.id.displayNameTextView);
        displayEmailIdTextView = hView.findViewById(R.id.displayEmailIdTextView);
        userProfileImageView = hView.findViewById(R.id.userProfileImageView);


    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        myRef = database.getReference("chatRooms/userProfiles/");
//        myRef.child(userId).child("isOnline").setValue(false);
//
//    }



    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Finale", "hey");
        myRef = database.getReference("chatRooms/userProfiles/");
        myRef.child(userId).child("isOnline").setValue(false);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_chats:
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable(Parameters.USER_ID, user);
                ChatsFragment fragment1 = new ChatsFragment();
                fragment1.setArguments(bundle1);
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment1).commit();
                toolbar.setTitle(R.string.menu_chats);
                break;
            case R.id.nav_contacts:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ContactsFragment()).commit();
                toolbar.setTitle(R.string.menu_contacts);
                break;
            case R.id.nav_profile:
                Bundle bundle = new Bundle();
                bundle.putSerializable(Parameters.USER_ID, user);
                ProfileFragment fragment = new ProfileFragment();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                toolbar.setTitle(R.string.menu_profile);
                break;
            case R.id.nav_groups:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new GroupsFragment()).commit();
                toolbar.setTitle(R.string.menu_groups);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_createGroup:
                LayoutInflater layoutInflater = getLayoutInflater();
                final View v = layoutInflater.inflate(R.layout.alert_dialog, null);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.CreateGroupDialogTheme);
                alertDialog.setTitle("Enter Group Name :");
                EditText input = v.findViewById(R.id.etComments);
                input.setHint(R.string.group_name);
                input.setTextColor(Color.WHITE);

                alertDialog.setPositiveButton("Create",
                        (dialog, which) -> {
                            groupName = input.getText().toString();
                            Log.e(TAG,"groupName = "+groupName);
                            GroupChatRoom groupChatRoom = new GroupChatRoom();

                            String grpId = groupChatDbRef.push().getKey();
                            String createdOn = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").format(new Date());
                            groupChatRoom.setGroupId(grpId);
                            groupChatRoom.setGroupName(groupName);
                            groupChatRoom.setCreatedBy(user.getId());
                            groupChatRoom.setCreatedOn(createdOn);
                            groupChatDbRef.child(grpId).setValue(groupChatRoom);
                            groupChatDbRef.child(grpId).child("membersListWithOnlineStatus").child(user.getId()).setValue(1);
                            Toast.makeText(HomeActivity.this, "Group Created", Toast.LENGTH_LONG).show();

                        });

                alertDialog.setNegativeButton("Cancel",
                        (dialog, which) -> dialog.cancel());
                alertDialog.setView(v);
                alertDialog.show();
                /*Bundle bundle = new Bundle();
                bundle.putSerializable(Parameters.USER_ID, user);
                CreateGroupFragment fragment = new CreateGroupFragment();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                toolbar.setTitle(R.string.action_createGroup);*/
                return true;
        }
        return true;
    }

    private class ChangeOnlineStatus extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... strings) {
            myRef = database.getReference("chatRooms/userProfiles/");
            myRef.child(userId).child("isOnline").setValue(true);
            return null;

        }
    }
//    private void ChangeOnlineStatus(){
//
//    }

}
