package com.app.thechatrooms.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.thechatrooms.R;
import com.app.thechatrooms.models.GroupChatRoom;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class ChatFragmentAdapter extends RecyclerView.Adapter<ChatFragmentAdapter.ViewHolder>  {

    Context context;
    String userId;
    ArrayList<GroupChatRoom> chatList;

    public ChatFragmentAdapter(String userId, ArrayList<GroupChatRoom> chatList, Activity a, Context context){
        this.chatList = chatList;
        this.context = context;
        this.userId = userId;
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
        if(group.getCreatedBy().equals(userId)) {
            holder.deleteButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setClickable(true);
        }else{
            holder.deleteButton.setVisibility(View.GONE);
            holder.deleteButton.setClickable(false);
        }
        holder.joinButton.setVisibility(View.GONE);
        holder.joinButton.setClickable(false);
        if(!group.getCreatedBy().equals(userId)) {
            holder.leaveButton.setVisibility(View.VISIBLE);
            holder.leaveButton.setClickable(true);
        }else{
            holder.leaveButton.setVisibility(View.GONE);
            holder.leaveButton.setClickable(false);
        }
        holder.groupName.setText(group.getGroupName());
        holder.createdBy.setText(group.getCreatedBy());
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

        ViewHolder(View itemView){
            super(itemView);
            groupName = itemView.findViewById(R.id.fragment_groups_item_groupName);
            createdBy = itemView.findViewById(R.id.fragment_groups_item_createdBy);
            joinButton = itemView.findViewById(R.id.fragment_groups_item_joinButton);
            deleteButton = itemView.findViewById(R.id.fragment_groups_item_deleteButton);
            leaveButton = itemView.findViewById(R.id.fragment_groups_item_leaveButton);
        }

    }
}
