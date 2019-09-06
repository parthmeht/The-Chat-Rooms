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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.thechatrooms.Adapters.ChatFragmentAdapter;
import com.app.thechatrooms.Adapters.GroupFragmentAdapter;
import com.app.thechatrooms.R;
import com.app.thechatrooms.models.GroupChatRoom;
import com.app.thechatrooms.models.User;
import com.app.thechatrooms.ui.groups.GroupsViewModel;
import com.app.thechatrooms.utilities.Parameters;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ChatsFragment extends Fragment {

    private ChatFragmentAdapter chatFragmentAdapter;
    private StorageReference mStorageRef;
    private User user;
    private DatabaseReference myRef;
    private FirebaseDatabase firebaseDatabase;
    ArrayList<GroupChatRoom> chatList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_groups, container, false);
        user = (User) getArguments().getSerializable(Parameters.USER_ID);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("chatRooms/groupChatRoom");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Log.d("Child", child.toString());
                    GroupChatRoom group = child.getValue(GroupChatRoom.class);
                    if(group.getMembersList().contains(user.getId())){
                        chatList.add(group);
                    }
                }
                RecyclerView recyclerView = root.findViewById(R.id.chats_recycler_view_id);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                chatFragmentAdapter = new ChatFragmentAdapter(chatList, getActivity(),getContext());
                recyclerView.setAdapter(chatFragmentAdapter);
                chatFragmentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return root;

    }
}