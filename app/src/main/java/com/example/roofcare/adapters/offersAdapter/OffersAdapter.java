package com.example.roofcare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roofcare.R;

public class OffersAdapter extends RecyclerView.Adapter<MoreOptionsAdapter.MyViewHolder> {
    private Context mContext;

    @NonNull
    @Override
    public MoreOptionsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.fragment_offers_design, parent, false);
        return new MoreOptionsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoreOptionsAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            uiInitial(itemView);
        }

        private void uiInitial(View itemView) {

        }
    }
}
