package com.app.thechatrooms.ui.createGroup;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.thechatrooms.HomeActivity;
import com.app.thechatrooms.R;
import com.app.thechatrooms.models.GroupChatRoom;
import com.app.thechatrooms.models.Messages;
import com.app.thechatrooms.models.User;
import com.app.thechatrooms.ui.chats.ChatsFragment;
import com.app.thechatrooms.utilities.Parameters;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateGroupFragment extends Fragment {

    private static final String TAG = "CreateGroupFragment";
    private User user;
    private View view;
    private EditText groupName;
    private Button create;
    private FirebaseDatabase database;
    private DatabaseReference myRef, groupChatDbRef;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;

    public CreateGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_group, container, false);
        user = (User) getArguments().getSerializable(Parameters.USER_ID);
        groupName = view.findViewById(R.id.creategroup_grp_name);
        create = view.findViewById(R.id.creategroup_create_button);
        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        groupChatDbRef = database.getReference("chatRooms/groupChatRoom/");

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = groupName.getText().toString();
                Log.e(TAG,"groupName = "+name);
                GroupChatRoom groupChatRoom = new GroupChatRoom();

                String id = groupChatDbRef.push().getKey();
                String createdOn = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").format(new Date());
                ArrayList<String> memberList = new ArrayList<>();
                ArrayList<Messages> msgList = new ArrayList<>();
                memberList.add(user.getId());
                groupChatRoom.setGroupId(id);
                groupChatRoom.setGroupName(name);
                groupChatRoom.setCreatedBy(user.getId());
                groupChatRoom.setCreatedOn(createdOn);
                groupChatRoom.setMembersList(memberList);
                groupChatRoom.setMessageList(msgList);

                groupChatDbRef.child(id).setValue(groupChatRoom);

                Toast.makeText(getContext(), "Group Created", Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new ChatsFragment())
                        .commit();
            }
        });
        return view;
    }
}
