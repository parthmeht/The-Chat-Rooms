package com.app.thechatrooms.ui.messages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts, container);
        if (getArguments()!=null)
            hashMap = (LinkedHashMap<String, GroupOnlineUsers>) getArguments().getSerializable(Parameters.SHOW_MEMBERS);
        recyclerView = rootView.findViewById(R.id.fragment_contacts_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        membersAdapter = new ShowMembersAdapter(this.getActivity(),hashMap);
        recyclerView.setAdapter(membersAdapter);

        this.getDialog().setTitle("Show Members");

        return rootView;
    }
}
