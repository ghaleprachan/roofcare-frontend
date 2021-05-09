package com.example.homesewa.activities.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.homesewa.R;
import com.example.homesewa.activities.addpost.AddPostActivity;
import com.example.homesewa.activities.dashboard.fragments.MoreFragment;
import com.example.homesewa.activities.dashboard.fragments.OffersFragment;
import com.example.homesewa.activities.search.SearchActivity;
import com.example.homesewa.helper.userDetails.UserBasicDetails;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class DashboardActivity extends AppCompatActivity {
    private Toolbar toolbarHome;
    private BottomNavigationView bottomNavView;
    private Fragment active, moreFragment, notesFragment;
    private FragmentManager fragmentManager;
    private ExtendedFloatingActionButton addOffer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        UiInitialize();
        setUpToolBar();

        placeFragments();
        addFragments();
        onBottomNavViewClick();
        addOfferClick();

        try {
            if (UserBasicDetails.getUserType(this).equalsIgnoreCase("Customer")) {
                addOffer.setVisibility(View.VISIBLE);
            } else {
                addOffer.setVisibility(View.VISIBLE);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void addOfferClick() {
        addOffer.setOnClickListener(v -> {
            if (UserBasicDetails.getUserType(this).equalsIgnoreCase("Customer")) {
                Toast.makeText(this, "Switch account to use this feature..", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, AddPostActivity.class));
            }
        });
    }

    private void placeFragments() {
        moreFragment = new MoreFragment();
        notesFragment = new OffersFragment();
        fragmentManager = getSupportFragmentManager();
        active = notesFragment;
    }

    private void addFragments() {
        fragmentManager.beginTransaction().add(R.id.fragments, moreFragment, "more").hide(moreFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragments, active, "notes").commit();
    }

    private void onBottomNavViewClick() {
        bottomNavView.setOnNavigationItemSelectedListener(item -> {
            int navItemId = item.getItemId();
            if (navItemId == R.id.bottom_nav_notes) {
                fragmentManager.beginTransaction().hide(active).show(notesFragment).commit();
                active = notesFragment;
            } else {
                fragmentManager.beginTransaction().hide(active).show(moreFragment).commit();
                active = moreFragment;
            }
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuId = item.getItemId();
        if (menuId == R.id.toolbar_search) {
            startActivity(new Intent(this, SearchActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpToolBar() {
        setSupportActionBar(toolbarHome);
    }

    private void UiInitialize() {
        toolbarHome = findViewById(R.id.toolbarHome);
        bottomNavView = findViewById(R.id.bottomNavView);
        addOffer = findViewById(R.id.addOffer);
    }
}
