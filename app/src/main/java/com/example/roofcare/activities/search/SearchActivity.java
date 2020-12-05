package com.example.roofcare.activities.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.roofcare.R;

public class SearchActivity extends AppCompatActivity {
    private LinearLayout back;
    private EditText searchItem;
    private ImageView clearSearch;
    private RecyclerView searchResult;

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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchItem.getText().toString().isEmpty()) {
                    clearSearch.setVisibility(View.GONE);
                } else {
                    clearSearch.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void onBackClick() {
        back.setOnClickListener(v -> onBackPressed());
    }

    private void uiInitialize() {
        back = findViewById(R.id.back);
        clearSearch = findViewById(R.id.clearSearch);
        searchItem = findViewById(R.id.searchItem);
        searchResult = findViewById(R.id.searchResult);
    }
}