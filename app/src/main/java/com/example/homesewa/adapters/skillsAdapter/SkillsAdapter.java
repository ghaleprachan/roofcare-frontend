package com.example.homesewa.adapters.skillsAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.homesewa.R;
import com.example.homesewa.apis.ApiCollection;
import com.example.homesewa.models.userskillsresponse.UserSkillsResponse;

import java.util.ArrayList;

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.SkillsViewHolder> {
    private Context context;
    private ArrayList<UserSkillsResponse> skillsResponses;

    public SkillsAdapter(Context context, ArrayList<UserSkillsResponse> skillsResponses) {
        this.context = context;
        this.skillsResponses = skillsResponses;
    }

    @NonNull
    @Override
    public SkillsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SkillsViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_skills, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull SkillsViewHolder holder, int position) {
        holder.skillName.setText(skillsResponses.get(position).getProfessionName());
        onRemoveSkillClick(holder.removeSkill, position);
    }

    private void onRemoveSkillClick(ImageView removeSkill, int position) {
        removeSkill.setOnClickListener(v -> {
            if (skillsResponses.size() == 1) {
                Toast.makeText(context, "You can't delete all the skills!", Toast.LENGTH_SHORT).show();
            } else {
                StringRequest request = new StringRequest(
                        Request.Method.DELETE,
                        ApiCollection.deleteSkill + skillsResponses.get(position).getUserProfessionId(),
                        response -> {
                            if (response.equalsIgnoreCase("Success")) {
                                skillsResponses.remove(position);
                                notifyItemChanged(position);
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                            }
                        },
                        error -> {
                            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                );
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(request);
            }
        });
    }

    @Override
    public int getItemCount() {
        return skillsResponses.size();
    }

    public static class SkillsViewHolder extends RecyclerView.ViewHolder {
        private TextView skillName;
        private ImageView removeSkill;

        public SkillsViewHolder(@NonNull View itemView) {
            super(itemView);
            skillName = itemView.findViewById(R.id.skillName);
            removeSkill = itemView.findViewById(R.id.removeSkill);
        }
    }
}
