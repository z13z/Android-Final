package com.example.finalproject.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalproject.R;

public class SearchPeerFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View searchPeersView = inflater.inflate(R.layout.fragment_search_peer, container, false);
        searchPeersView.findViewById(R.id.buttonCancelConnection).setOnClickListener(this);
        return searchPeersView;
    }

    @Override
    public void onClick(View v) {
        Activity activity = getActivity();
        if (activity != null) {
            activity.onBackPressed();
        }
    }
}
