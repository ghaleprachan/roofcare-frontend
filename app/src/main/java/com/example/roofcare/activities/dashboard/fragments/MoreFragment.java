package com.example.roofcare.activities.dashboard.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roofcare.R;
import com.example.roofcare.services.moreOptionService.MoreOptionsService;
import com.example.roofcare.adapters.moreOptionAdapter.MoreOptionsAdapter;
import com.example.roofcare.helper.populateMoreSection.PopulateMoreOptions;


public class MoreFragment extends Fragment {
    private RecyclerView moreOptionsRecycler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uIInitial(view);
        if (MoreOptionsService.options.size() == 0)
            PopulateMoreOptions.addOptions();
        addDataToRecyclerView();
    }

    private void addDataToRecyclerView() {
        moreOptionsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        MoreOptionsAdapter optionsAdapter = new MoreOptionsAdapter(getContext(), MoreOptionsService.options);
        moreOptionsRecycler.setAdapter(optionsAdapter);
    }

    private void uIInitial(View view) {
        moreOptionsRecycler = view.findViewById(R.id.recyclerView);
    }
}
