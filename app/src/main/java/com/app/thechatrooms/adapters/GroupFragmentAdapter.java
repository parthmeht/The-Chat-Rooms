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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class GroupFragmentAdapter extends RecyclerView.Adapter<GroupFragmentAdapter.ViewHolder> {
    Context context;
    FirebaseStorage storage;
    FirebaseDatabase dbRef;
    ArrayList<GroupChatRoom> groupList;
    String userId;
    GroupFragmentInterface groupFragmentInterface;

    public GroupFragmentAdapter(String user, ArrayList<GroupChatRoom> groupList, Activity a, Context context, GroupFragmentInterface groupFragmentInterface){
        this.groupList = groupList;
        this.context = context;
        this.userId = user;
        this.groupFragmentInterface = groupFragmentInterface;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_groups_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        dbRef = FirebaseDatabase.getInstance();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GroupChatRoom group = groupList.get(position);

        holder.deleteButton.setVisibility(View.GONE);
        holder.deleteButton.setClickable(false);
        holder.joinButton.setVisibility(View.VISIBLE);
        holder.joinButton.setClickable(true);

        holder.groupName.setText(group.getGroupName());
        holder.createdBy.setText(group.getCreatedBy());

        holder.joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupFragmentInterface.joinGroup(group);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView groupName;
        TextView createdBy;
        ImageButton joinButton;
        ImageButton deleteButton;

        ViewHolder(View itemView){
            super(itemView);
            groupName = itemView.findViewById(R.id.fragment_groups_item_groupName);
            createdBy = itemView.findViewById(R.id.fragment_groups_item_createdBy);
            joinButton = itemView.findViewById(R.id.fragment_groups_item_joinButton);
            deleteButton = itemView.findViewById(R.id.fragment_groups_item_deleteButton);
        }

    }

   public interface GroupFragmentInterface{
        void joinGroup(GroupChatRoom groupChatRoom);
    }
}
