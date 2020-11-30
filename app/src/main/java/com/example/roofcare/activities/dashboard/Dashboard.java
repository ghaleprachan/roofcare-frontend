package com.example.roofcare.activities.dashboard;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.roofcare.R;
import com.example.roofcare.activities.dashboard.fragments.MoreFragment;
import com.example.roofcare.activities.dashboard.fragments.OffersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {
    private Toolbar toolbarHome;
    private BottomNavigationView bottomNavView;
    private Fragment active, moreFragment, notesFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        UiInitialize();
        setUpToolBar();

        placeFragments();
        addFragments();
        onBottomNavViewClick();
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
            Toast.makeText(this, "Opening search", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpToolBar() {
        setSupportActionBar(toolbarHome);
    }

    private void UiInitialize() {
        toolbarHome = findViewById(R.id.toolbarHome);
        bottomNavView = findViewById(R.id.bottomNavView);
    }
}
