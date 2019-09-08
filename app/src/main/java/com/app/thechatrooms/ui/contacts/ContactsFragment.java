package com.app.thechatrooms.ui.contacts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.thechatrooms.R;
import com.app.thechatrooms.adapters.ContactsRecyclerView;
import com.app.thechatrooms.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ContactsFragment extends Fragment {

    private ContactsViewModel contactsViewModel;
    private StorageReference mStorageRef;

    private DatabaseReference myRef;
    private FirebaseDatabase firebaseDatabase;
    ArrayList<User> userList = new ArrayList<>();

    ContactsRecyclerView contactsRecyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactsViewModel =
                ViewModelProviders.of(this).get(ContactsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contacts, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("chatRooms/userProfiles");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for(DataSnapshot child :  dataSnapshot.getChildren()){
                    Log.d("CHILD", child.toString());
                    User user = child.getValue(User.class);
                    userList.add(user);
                    RecyclerView recyclerView = root.findViewById(R.id.fragment_contacts_recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    contactsRecyclerView = new ContactsRecyclerView(userList, getActivity(),getContext());
                    recyclerView.setAdapter(contactsRecyclerView);
                    contactsRecyclerView.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        final TextView textView = root.findViewById(R.id.text_slideshow);
        contactsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}