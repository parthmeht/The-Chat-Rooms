package com.app.thechatrooms.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.app.thechatrooms.R;
import com.app.thechatrooms.models.GroupChatRoom;
import com.app.thechatrooms.ui.messages.MessageFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class ChatFragmentAdapter extends RecyclerView.Adapter<ChatFragmentAdapter.ViewHolder>  {

    Context context;
    String userId;
    ArrayList<GroupChatRoom> chatList;
    ChatFragmentInterface chatFragmentInterface;

    public ChatFragmentAdapter(String userId, ArrayList<GroupChatRoom> chatList, Activity a, Context context, ChatFragmentInterface chatFragmentInterface){
        this.chatList = chatList;
        this.context = context;
        this.userId = userId;
        this.chatFragmentInterface = chatFragmentInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_groups_item, parent, false);
        ViewHolder viewHolder = new ChatFragmentAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GroupChatRoom group = chatList.get(position);
        if(group.getCreatedById().equals(userId)) {
            holder.deleteButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setClickable(true);
        }else{
            holder.deleteButton.setVisibility(View.GONE);
            holder.deleteButton.setClickable(false);
        }
        holder.joinButton.setVisibility(View.GONE);
        holder.joinButton.setClickable(false);
        if(!group.getCreatedById().equals(userId)) {
            holder.leaveButton.setVisibility(View.VISIBLE);
            holder.leaveButton.setClickable(true);
        }else{
            holder.leaveButton.setVisibility(View.GONE);
            holder.leaveButton.setClickable(false);
        }
        holder.groupName.setText(group.getGroupName());
        holder.createdBy.setText(group.getCreatedByName());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatFragmentInterface.deleteGroup(group);
            }
        });

        holder.leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatFragmentInterface.leaveGroup(group);
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle arguments = new Bundle();
                arguments.putString("GroupID", group.getGroupId());
                Fragment messageFragment = new MessageFragment();
                messageFragment.setArguments(arguments);
                chatFragmentInterface.openChat(messageFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView groupName;
        TextView createdBy;
        ImageButton joinButton;
        ImageButton deleteButton;
        ImageButton leaveButton;
        LinearLayout linearLayout;

        ViewHolder(View itemView){
            super(itemView);
            linearLayout = itemView.findViewById(R.id.fragment_groups_item_texxtViewLinearLayout);
            groupName = itemView.findViewById(R.id.fragment_groups_item_groupName);
            createdBy = itemView.findViewById(R.id.fragment_groups_item_createdBy);
            joinButton = itemView.findViewById(R.id.fragment_groups_item_joinButton);
            deleteButton = itemView.findViewById(R.id.fragment_groups_item_deleteButton);
            leaveButton = itemView.findViewById(R.id.fragment_groups_item_leaveButton);
        }

    }

    public interface ChatFragmentInterface{
        void deleteGroup(GroupChatRoom groupChatRoom);
        void leaveGroup(GroupChatRoom groupChatRoom);
        void openChat(Fragment fragment);
    }
}
