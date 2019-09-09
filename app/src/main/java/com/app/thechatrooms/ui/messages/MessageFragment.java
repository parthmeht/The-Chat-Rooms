package com.app.thechatrooms.ui.messages;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.app.thechatrooms.R;
import com.app.thechatrooms.adapters.GroupOnlineMembersAdapter;
import com.app.thechatrooms.adapters.MessageAdapter;
import com.app.thechatrooms.models.Messages;
import com.app.thechatrooms.models.GroupOnlineUsers;
import com.app.thechatrooms.models.User;
import com.app.thechatrooms.utilities.Parameters;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment implements MessageAdapter.MessageInterface {

    private static final String TAG = "MessageFragment";
    private MessageAdapter messageAdapter;
    private User user;
    private DatabaseReference myRef, groupDbRef;
    private FirebaseDatabase firebaseDatabase;
    ArrayList<Messages> messagesArrayList = new ArrayList<>();
    private FirebaseAuth mAuth;
    private String groupdId;
    ArrayList<GroupOnlineUsers> onlineUserArrayList = new ArrayList<>();
    GroupOnlineMembersAdapter groupOnlineMembersAdapter;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        EditText editText = view.findViewById(R.id.fragment_chats_message_EditText);
        ImageButton sendButton = view.findViewById(R.id.fragment_chats_send_button);

        mAuth = FirebaseAuth.getInstance();
        String s = getArguments().getString("GroupID");
        Log.d("SSS",s);

        String userId = mAuth.getCurrentUser().getUid();
        String userName = mAuth.getCurrentUser().getDisplayName();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("chatRooms/messages/"+s);
        groupDbRef = firebaseDatabase.getReference("chatRooms/groupChatRoom/"+s+"/membersListWithOnlineStatus");
        groupDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                onlineUserArrayList.clear();
                for (DataSnapshot val: dataSnapshot.getChildren()){

                    String id = val.getKey();
                    boolean online = (boolean) val.getValue();
                    GroupOnlineUsers onlineUser = new GroupOnlineUsers(id, online);
                    onlineUserArrayList.add(onlineUser);
                    RecyclerView recyclerView = view.findViewById(R.id.fragment_message_active_users);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    groupOnlineMembersAdapter = new GroupOnlineMembersAdapter(onlineUserArrayList,getActivity(), getContext());
                    recyclerView.setAdapter(groupOnlineMembersAdapter);
                    groupOnlineMembersAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messagesArrayList.clear();
                for (DataSnapshot val: dataSnapshot.getChildren()){
                    if(val.child("groupId").getValue() == groupdId){
                        Messages messages = val.getValue(Messages.class);
                        messagesArrayList.add(messages);
                        RecyclerView recyclerView = view.findViewById(R.id.fragment_chats_recyclerView);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        messageAdapter = new MessageAdapter(userId, messagesArrayList, getActivity(), getContext(), MessageFragment.this);
                        recyclerView.setAdapter(messageAdapter);
                        messageAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!editText.getText().toString().isEmpty()){

                    String messageId = myRef.push().getKey();
                    Messages messages = new Messages();
                    messages.setMessageId(messageId);
                    messages.setGroupId(groupdId);
                    messages.setMessage(editText.getText().toString());
                    messages.setCreatedBy(userId);
                    messages.setCreatedByName(userName);
                    myRef.child(messageId).setValue(messages);
                    editText.setText("");
                    hideKeyboard(getContext(), view);
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    public void hideKeyboard(Context context, View view){
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void deleteMessage(String messageId) {
        myRef.child(messageId).setValue(null);
    }
}
