package com.app.thechatrooms.ui.groups;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.thechatrooms.Adapters.*;

import com.app.thechatrooms.R;
import com.app.thechatrooms.models.GroupChatRoom;
import com.app.thechatrooms.models.User;
import com.app.thechatrooms.utilities.Parameters;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class GroupsFragment extends Fragment {

    private GroupFragmentAdapter groupFragmentAdapter;
    private GroupsViewModel groupsViewModel;
    private StorageReference mStorageRef;

    private DatabaseReference myRef;
    private FirebaseDatabase firebaseDatabase;
    ArrayList<GroupChatRoom> groupList = new ArrayList<>();
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        groupsViewModel =
                ViewModelProviders.of(this).get(GroupsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_groups, container, false);

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("chatRooms/groupChatRoom");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupList.clear();
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Log.d("Child", child.toString());
                    GroupChatRoom group = child.getValue(GroupChatRoom.class);
                    groupList.add(group);
                    RecyclerView recyclerView = root.findViewById(R.id.fragment_groups_recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    groupFragmentAdapter = new GroupFragmentAdapter(userId, groupList, getActivity(),getContext());
                    recyclerView.setAdapter(groupFragmentAdapter);
                    groupFragmentAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        groupsViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}