package com.app.thechatrooms.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.thechatrooms.R;
import com.app.thechatrooms.models.GroupChatRoom;
import com.app.thechatrooms.models.Messages;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    Context context;
    FirebaseStorage storage;
    FirebaseDatabase dbRef;
    ArrayList<Messages> messagesArrayList;
    String userId;

    public MessageAdapter(String userId, ArrayList<Messages> messagesArrayList, Activity a, Context context){
        this.messagesArrayList = messagesArrayList;
        this.userId = userId;
        this.context = context;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_message_items_theirchat, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        dbRef = FirebaseDatabase.getInstance();
        return viewHolder;
//        switch (viewType){
//            case ChooseItem.TypeA:
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragmet_chats_items_mychat, parent, false);
//                return new ViewHolder()
//        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
//        ChooseItem item =
//        holder.bindType();
        final Messages messages = messagesArrayList.get(position);
        holder.message.setText(messages.getMessage());
        holder.sentBy.setText(messages.getCreatedBy());
    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }
//
//    public class ViewHolderA extends RecyclerView.ViewHolder{
//
//
//        ViewHolderA(View itemView){
//            super(itemView);
//        }
//    }
//
//    public class ViewHolderB extends RecyclerView.ViewHolder{
//        ViewHolderB(View itemView){
//            super(itemView);
//        }
//    }
//    public interface ChooseItem{
//        int TypeA = 1;
//        int TypeB = 2;
//        int getChooseItem();
//    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView message ;
        TextView sentBy;
//        ImageView userImage;
        public ViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.fragment_message_items_theirchat_userMessage);
            sentBy = itemView.findViewById(R.id.fragment_message_items_theirchat_userName);
//            userImage = itemView.findViewById(R.id.fragment_message_items_theirchat_userImage);

        }

//        public abstract void bindType(ChooseItem item) {
//
//        }
    }


}
