package com.app.thechatrooms.ui.messages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.thechatrooms.R;
import com.app.thechatrooms.adapters.ShowMembersAdapter;
import com.app.thechatrooms.models.GroupOnlineUsers;
import com.app.thechatrooms.utilities.Parameters;

import java.util.LinkedHashMap;

public class ShowMembersFragment extends DialogFragment {

    RecyclerView recyclerView;
    ShowMembersAdapter membersAdapter;
    LinkedHashMap<String,GroupOnlineUsers> hashMap;

    public ShowMembersFragment(){

    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container);
        if (getArguments()!=null)
            hashMap = (LinkedHashMap<String, GroupOnlineUsers>) getArguments().getSerializable(Parameters.SHOW_MEMBERS);
        recyclerView = view.findViewById(R.id.show_members_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        membersAdapter = new ShowMembersAdapter(getContext(),hashMap);
        recyclerView.setAdapter(membersAdapter);
        membersAdapter.notifyDataSetChanged();

        Window window = getDialog().getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return view;
    }*/

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_show_members, new LinearLayout(getActivity()),false);
        if (getArguments()!=null)
            hashMap = (LinkedHashMap<String, GroupOnlineUsers>) getArguments().getSerializable(Parameters.SHOW_MEMBERS);
        recyclerView = view.findViewById(R.id.show_members_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        membersAdapter = new ShowMembersAdapter(getContext(),hashMap);
        recyclerView.setAdapter(membersAdapter);
        membersAdapter.notifyDataSetChanged();

        Dialog builder = new Dialog(getActivity());
        builder.setTitle("Show Members");
        builder.setContentView(view);

        Window window = builder.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return builder;
    }
}
