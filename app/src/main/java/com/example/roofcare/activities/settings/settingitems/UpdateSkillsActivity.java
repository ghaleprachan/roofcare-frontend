package com.example.roofcare.activities.settings.settingitems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.roofcare.adapters.skillsAdapter.SkillsAdapter;
import com.example.roofcare.apis.ApiCollection;
import com.example.roofcare.databinding.ActivityAddSkills2Binding;
import com.example.roofcare.helper.userDetails.UserBasicDetails;
import com.example.roofcare.models.userskillsresponse.UserSkillsResponse;
import com.example.roofcare.services.registerprofessions.ProfessionsService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class UpdateSkillsActivity extends AppCompatActivity {
    private ActivityAddSkills2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddSkills2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getUserSkills();

        binding.profession.requestFocus();
        onBackClick();
        initHint();
        onAddClick();
        onDoneClick();
    }

    private void getUserSkills() {
        try {
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    ApiCollection.getUserSkills + UserBasicDetails.getId(this),
                    response -> {
                        try {
                            ArrayList<UserSkillsResponse> offersList = new Gson().fromJson(response, new TypeToken<List<UserSkillsResponse>>() {
                            }.getType());
                            initRecyclerView(offersList);
                        } catch (Exception ex) {
                            Toast.makeText(this, "Parse error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initRecyclerView(ArrayList<UserSkillsResponse> offersList) {
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerView.setAdapter(new SkillsAdapter(this, offersList));
    }

    private void onDoneClick() {
        binding.done.setOnClickListener(v -> {
            this.finish();
        });
    }

    private void onAddClick() {
        binding.add.setOnClickListener(v -> {
            if (binding.profession.getText().toString().isEmpty()) {
                binding.profession.requestFocus();
                binding.professionLayout.setError("Enter skills, eg: Plumber..");
            } else {
                if (ProfessionsService.getProfessionId(binding.profession.getText().toString()) != -1) {
                    binding.professionLayout.setError(null);
                    addApiCall();
                } else {
                    binding.professionLayout.setError("Profession still not available!");
                }
            }
        });
    }

    private void addApiCall() {
        try {
            binding.add.setEnabled(false);
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    ApiCollection.addUserProfession + "userId=" +
                            ProfessionsService.professions.get(0).getUserId() +
                            "&professionId=" +
                            ProfessionsService.getProfessionId(binding.profession.getText().toString()),
                    response -> {
                        binding.add.setEnabled(true);
                        binding.done.setEnabled(true);
                        if (response.equals("true")) {
                            binding.profession.setText("");
                            getUserSkills();
                            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
                        } else if (response.equals("false")) {
                            Toast.makeText(this, "Skill already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
                        binding.add.setEnabled(true);
                    }
            );
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, "Ex: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initHint() {
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, ProfessionsService.getProfessions());
        binding.profession.setAdapter(countryAdapter);
    }

    private void onBackClick() {
        binding.backBtn.setOnClickListener(v -> onBackPressed());
    }
}