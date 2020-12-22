package com.example.roofcare.adapters.bookingsAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roofcare.R;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.BookingsViewHolder> {
    @NonNull
    @Override
    public BookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookingsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_booking_design, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookingsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class BookingsViewHolder extends RecyclerView.ViewHolder {
        public BookingsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
