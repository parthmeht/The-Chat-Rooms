package com.app.thechatrooms.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.thechatrooms.R;
import com.app.thechatrooms.models.GroupOnlineUsers;
import com.app.thechatrooms.ui.messages.ShowMembersViewHolder;
import com.app.thechatrooms.utilities.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ShowMembersAdapter extends RecyclerView.Adapter<ShowMembersViewHolder> {

    Context context;
    LinkedHashMap<String,GroupOnlineUsers> hashMap;
    private static final String TAG = "ShowMembersAdapter";

    public ShowMembersAdapter(Context context, LinkedHashMap<String, GroupOnlineUsers> hashMap) {
        this.context = context;
        this.hashMap = hashMap;
    }

    @NonNull
    @Override
    public ShowMembersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_contacts_item,parent,false);
        ShowMembersViewHolder viewHolder = new ShowMembersViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowMembersViewHolder holder, int position) {
        GroupOnlineUsers value = (new ArrayList<>(hashMap.values())).get(position);
        Log.v(TAG,value.toString());
        holder.getDisplayNameTextView().setText(value.getDisplayName());
        Picasso.get()
                .load(value.getProfileLink())
                .transform(new CircleTransform()).centerCrop().fit()
                .into(holder.getUserProfileImageView());
        if (value.isOnline())
            holder.getOnlineIconImageView().setVisibility(View.VISIBLE);
        else
            holder.getOnlineIconImageView().setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return hashMap.size();
    }
}
