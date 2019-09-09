package com.app.thechatrooms.ui.chats;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.thechatrooms.R;
import com.app.thechatrooms.adapters.ChatFragmentAdapter;
import com.app.thechatrooms.adapters.GroupFragmentAdapter;
import com.app.thechatrooms.models.GroupChatRoom;
import com.app.thechatrooms.models.OnlineUser;
import com.app.thechatrooms.models.User;
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

public class ChatsFragment extends Fragment implements ChatFragmentAdapter.ChatFragmentInterface {

    private View view;
    private User user;
    private ChatFragmentAdapter chatFragmentAdapter;
    private StorageReference mStorageRef;
    private DatabaseReference myRef;
    private FirebaseDatabase firebaseDatabase;
    ArrayList<GroupChatRoom> groupList = new ArrayList<>();
    private FirebaseAuth mAuth;
    private String userId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_chats, container, false);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        user = (User) getArguments().getSerializable(Parameters.USER_ID);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("chatRooms/groupChatRoom");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupList.clear();
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    GroupChatRoom group = new GroupChatRoom();
                    group.setCreatedByName(child.child("createdByName").getValue().toString());
                    group.setCreatedById(child.child("createdById").getValue().toString());
                    group.setCreatedOn(child.child("createdOn").getValue().toString());
                    group.setGroupId(child.child("groupId").getValue().toString());
                    group.setGroupName(child.child("groupName").getValue().toString());
                    ArrayList<OnlineUser> onlineUsersList = new ArrayList<>();
                    if(group.getCreatedById().equals(userId)){
//                        for (DataSnapshot child1: child.child("membersListWithOnlineStatus").getChildren()){
//                            //Log.d("Child", group.getCreatedByName()+" , ********* "+child1.getKey());
//                                OnlineUser onlineUser = new OnlineUser();
//                                onlineUser.setUserId(child1.getKey());
//                                onlineUser.setUserOnlineStatus(Integer.parseInt(child1.getValue().toString()));
//                                onlineUsersList.add(onlineUser);
//                                group.setMembersListWithOnlineStatus(onlineUsersList);
//                        }
                        groupList.add(group);
                    }else{
                        if(child.child("membersListWithOnlineStatus").hasChild(userId)){
//                            for (DataSnapshot child1: child.child("membersListWithOnlineStatus").getChildren()){
//                                //Log.d("Child", group.getCreatedByName()+" , ********* "+child1.getKey());
//                                OnlineUser onlineUser = new OnlineUser();
//                                onlineUser.setUserId(child1.getKey());
//                                onlineUser.setUserOnlineStatus(Integer.parseInt(child1.getValue().toString()));
//                                onlineUsersList.add(onlineUser);
//                                group.setMembersListWithOnlineStatus(onlineUsersList);
//                            }
                            groupList.add(group);
                        }
                    }
                }
                RecyclerView recyclerView = view.findViewById(R.id.fragment_chats_recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                chatFragmentAdapter = new ChatFragmentAdapter(userId, groupList, getActivity(),getContext(), ChatsFragment.this);
                recyclerView.setAdapter(chatFragmentAdapter);
                chatFragmentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    @Override
    public void deleteGroup(GroupChatRoom groupChatRoom) {
        myRef.child(groupChatRoom.getGroupId()).setValue(null);
        chatFragmentAdapter.notifyDataSetChanged();
        /*Fragment fragment = new ChatsFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
    }

    @Override
    public void leaveGroup(GroupChatRoom groupChatRoom) {
        myRef.child(groupChatRoom.getGroupId()).child("membersListWithOnlineStatus").child(userId).setValue(null);
        chatFragmentAdapter.notifyDataSetChanged();
        /*Fragment fragment = new ChatsFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
    }

    @Override
    public void openChat(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.commit();
    }
}