package com.example.roofcare.adapters.moreOptionAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roofcare.R;
import com.example.roofcare.activities.bookingRequest.BookingRequestItemActivity;
import com.example.roofcare.activities.bookings.BookedItemsActivity;
import com.example.roofcare.activities.logIn.LogIn;
import com.example.roofcare.activities.register.Register;
import com.example.roofcare.activities.savedOffers.SavedOfferActivity;
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

    private void onParentClick(RelativeLayout parent, int position) {
        parent.setOnClickListener(v -> {
            if (moreOptions.get(position).getId().equals(MoreOptionsId.SIGN_OUT)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                builder.setTitle("ALERT");
                builder.setIcon(R.drawable.ic_baseline_power_settings_new_24);
                builder.setMessage("Sure you want to sign out!");
                builder.setPositiveButton("YES", (dialog, which) -> {
                    SharedPreferences preferences = mContext.getSharedPreferences("LOGIN_DETAILS", 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    mContext.startActivity(new Intent(mContext, Register.class));
                    ((AppCompatActivity) mContext).finish();
                    dialog.dismiss();
                });
                android.app.AlertDialog alert = builder.create();
                alert.show();
            } else if (moreOptions.get(position).getId().equals(MoreOptionsId.SAVED)) {
                mContext.startActivity(new Intent(mContext, SavedOfferActivity.class));
            } else if (moreOptions.get(position).getId().equals(MoreOptionsId.BOOKING_REQUESTS)) {
                mContext.startActivity(new Intent(mContext, BookingRequestItemActivity.class));
            } else if (moreOptions.get(position).getId().equals(MoreOptionsId.BOOKINGS)) {
                mContext.startActivity(new Intent(mContext, BookedItemsActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return moreOptions.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon, more;
        private RelativeLayout parent;
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
