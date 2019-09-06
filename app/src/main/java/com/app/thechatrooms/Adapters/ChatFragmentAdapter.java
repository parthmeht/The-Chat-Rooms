package com.app.thechatrooms.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.thechatrooms.R;
import com.app.thechatrooms.models.GroupChatRoom;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class ChatFragmentAdapter extends RecyclerView.Adapter<ChatFragmentAdapter.ViewHolder>  {

    Context context;
    FirebaseStorage storage;
    //FirebaseDatabase dbRef;
    ArrayList<GroupChatRoom> chatList;

    public ChatFragmentAdapter(ArrayList<GroupChatRoom> chatList, Activity a, Context context){
        this.chatList = chatList;
        this.context = context;
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
        ViewHolder(View itemView){
            super(itemView);
            groupName = itemView.findViewById(R.id.fragment_groups_item_groupName);
            createdBy = itemView.findViewById(R.id.fragment_groups_item_createdBy);
        }

    }
}
