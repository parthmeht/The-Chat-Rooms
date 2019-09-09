package com.app.thechatrooms.ui.groups;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.thechatrooms.adapters.*;

import com.app.thechatrooms.R;
import com.app.thechatrooms.models.GroupChatRoom;
import com.app.thechatrooms.models.OnlineUser;
import com.app.thechatrooms.models.User;
import com.app.thechatrooms.ui.chats.ChatsFragment;
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

import java.util.ArrayList;

public class GroupsFragment extends Fragment implements GroupFragmentAdapter.GroupFragmentInterface {

    private GroupFragmentAdapter groupFragmentAdapter;
    private GroupsViewModel groupsViewModel;
    private StorageReference mStorageRef;
    private String userId;
    private User user;
    private DatabaseReference myRef;
    private FirebaseDatabase firebaseDatabase;
    ArrayList<GroupChatRoom> groupList = new ArrayList<>();
    private FirebaseAuth mAuth;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        groupsViewModel =
                ViewModelProviders.of(this).get(GroupsViewModel.class);
        root = inflater.inflate(R.layout.fragment_groups, container, false);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        user = (User) getArguments().getSerializable(Parameters.USER_ID);

        Bundle bundle = new Bundle();
        bundle.putSerializable(Parameters.USER_ID, user);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("chatRooms/groupChatRoom");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupList.clear();
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Log.d("Child", child.toString());
                    GroupChatRoom group = new GroupChatRoom();
                    group.setCreatedByName(child.child("createdByName").getValue().toString());
                    group.setCreatedById(child.child("createdById").getValue().toString());
                    group.setCreatedOn(child.child("createdOn").getValue().toString());
                    group.setGroupId(child.child("groupId").getValue().toString());
                    group.setGroupName(child.child("groupName").getValue().toString());
                    ArrayList<OnlineUser> onlineUsersList = new ArrayList<>();
                    if(!group.getCreatedById().equals(userId)){
                        if(!child.child("membersListWithOnlineStatus").hasChild(userId)){
                            for (DataSnapshot child1: child.child("membersListWithOnlineStatus").getChildren()){
                                //Log.d("Child", group.getCreatedByName()+" , ********* "+child1.getKey());
                                OnlineUser onlineUser = new OnlineUser();
                                onlineUser.setUserId(child1.getKey());
                                onlineUser.setUserOnlineStatus(Integer.parseInt(child1.getValue().toString()));
                                onlineUsersList.add(onlineUser);
                                group.setMembersListWithOnlineStatus(onlineUsersList);
                            }
                            groupList.add(group);
                        }
                    }
                }
                RecyclerView recyclerView = root.findViewById(R.id.fragment_groups_recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                groupFragmentAdapter = new GroupFragmentAdapter(userId, groupList, getActivity(),getContext(), GroupsFragment.this);
                recyclerView.setAdapter(groupFragmentAdapter);
                groupFragmentAdapter.notifyDataSetChanged();
                /*for (DataSnapshot child: dataSnapshot.getChildren()){
                    Log.d("Child", child.toString());
                    GroupChatRoom group = child.getValue(GroupChatRoom.class);
                    groupList.add(group);
                    RecyclerView recyclerView = root.findViewById(R.id.fragment_groups_recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    groupFragmentAdapter = new GroupFragmentAdapter(userId, groupList, getActivity(),getContext());
                    recyclerView.setAdapter(groupFragmentAdapter);
                    groupFragmentAdapter.notifyDataSetChanged();
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return root;
    }

    @Override
    public void joinGroup(GroupChatRoom groupChatRoom) {
        myRef.child(groupChatRoom.getGroupId()).child("membersListWithOnlineStatus").child(userId).setValue(1);
        Bundle bundleGroup = new Bundle();
        bundleGroup.putSerializable(Parameters.USER_ID, user);
        ChatsFragment fragment = new ChatsFragment();
        fragment.setArguments(bundleGroup);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_chats);

        fragmentTransaction.commit();
    }


}