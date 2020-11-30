package com.example.roofcare.adapters.moreOptionAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roofcare.R;
import com.example.roofcare.enumClasses.MoreOptionsId;
import com.example.roofcare.models.moreOptionModel.MoreOptionsModel;

import java.util.ArrayList;

public class MoreOptionsAdapter extends RecyclerView.Adapter<MoreOptionsAdapter.MyViewHolder> {
    private final Context mContext;
    private final ArrayList<MoreOptionsModel> moreOptions;

    public MoreOptionsAdapter(Context mContext, ArrayList<MoreOptionsModel> moreOptions) {
        this.mContext = mContext;
        this.moreOptions = moreOptions;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.fragment_more_options_design, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.icon.setImageResource(moreOptions.get(position).getIcon());
        holder.title.setText(moreOptions.get(position).getName());
        onParentClick(holder.parent, position);
        setMoreVisible(holder.more, position);
    }

    private void setMoreVisible(ImageView more, int position) {
        if (moreOptions.get(position).getId() == MoreOptionsId.SETTINGS) {
            more.setVisibility(View.VISIBLE);
        } else {
            more.setVisibility(View.GONE);
        }
    }

    private void onParentClick(LinearLayout parent, int position) {
        parent.setOnClickListener(v -> {
            Toast.makeText(mContext, moreOptions.get(position).getId().toString(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return moreOptions.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon, more;
        private LinearLayout parent;
        private TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            uiInitial(itemView);
        }

        private void uiInitial(View itemView) {
            icon = itemView.findViewById(R.id.icon);
            parent = itemView.findViewById(R.id.parent);
            title = itemView.findViewById(R.id.title);
            more = itemView.findViewById(R.id.more);
        }
    }
}
