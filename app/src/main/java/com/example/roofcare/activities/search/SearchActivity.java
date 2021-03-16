package com.example.roofcare.activities.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.roofcare.R;
import com.example.roofcare.adapters.searchAdapter.SearchAdapter;
import com.example.roofcare.apis.ApiCollection;
import com.example.roofcare.models.searchResponseModel.SearchResponseModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private LinearLayout back;
    private EditText searchItem;
    private ImageView clearSearch;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        uiInitialize();
        searchItem.requestFocus();
        onBackClick();
        textWatcher();
        onClearClick();
    }

    private void onClearClick() {
        clearSearch.setOnClickListener(v -> searchItem.setText(null));
    }

    private void textWatcher() {
        searchItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchItem.getText().toString().isEmpty()) {
                    clearSearch.setVisibility(View.GONE);
                } else {
                    clearSearch.setVisibility(View.VISIBLE);
                    searchApiCall();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void searchApiCall() {
        try {
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    ApiCollection.getSearchResult + searchItem.getText().toString(),
                    response -> {
                        try {
                            ArrayList<SearchResponseModel> searchResult = new Gson().fromJson(response, new TypeToken<List<SearchResponseModel>>() {
                            }.getType());
                            if (searchResult.size() == 0) {
                                Log.d("TAG", "searchApiCall: Empty Result");
                            } else {
                                populateRecyclerView(searchResult);
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, ex.getMessage() + " " + response, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()
            );
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);

        } catch (Exception ex) {
            Log.d("TAG", "searchApiCall: " + ex.getMessage());
        }
    }

    private void populateRecyclerView(ArrayList<SearchResponseModel> searchResult) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SearchAdapter searchAdapter;
        recyclerView.setAdapter(searchAdapter = new SearchAdapter(this, searchResult));
    }

    private void onBackClick() {
        back.setOnClickListener(v -> onBackPressed());
    }

    private void uiInitialize() {
        back = findViewById(R.id.back);
        clearSearch = findViewById(R.id.clearSearch);
        searchItem = findViewById(R.id.searchItem);
        recyclerView = findViewById(R.id.searchResult);
    }
}