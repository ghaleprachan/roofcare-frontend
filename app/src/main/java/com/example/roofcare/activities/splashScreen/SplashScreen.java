package com.example.roofcare.activities.splashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.example.roofcare.R;
import com.example.roofcare.activities.dashboard.DashboardActivity;
import com.example.roofcare.activities.logIn.LogInActivity;
import com.example.roofcare.animationsPackage.Techniques;
import com.example.roofcare.animationsPackage.YoYo;
import com.example.roofcare.sshSolve.HttpsTrustManager;

public class SplashScreen extends AppCompatActivity {
    private TextView roof, care;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        HttpsTrustManager.allowAllSSL();
        uiInit();
        animNote();
        finishSplashScreen();
        YoYo.with(Techniques.Shake).playOn(care);
    }

    private void finishSplashScreen() {
        new Handler().postDelayed(() -> {
            Intent sharedIntents = new Intent(this, LogInActivity.class);
            Pair[] pairs = new Pair[2];
            pairs[0] = new Pair<View, String>(roof, "roof");
            pairs[1] = new Pair<View, String>(care, "care");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                SharedPreferences prefs = getSharedPreferences("LOGIN_DETAILS", 0);
                if (prefs.getString("Username", null) != null) {
                    startActivity(new Intent(this, DashboardActivity.class));
                    finish();
                } else {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pairs);
                    startActivity(sharedIntents, options.toBundle());
                }
                finish();
            }
        }, 2000);
    }

    private void animNote() {
        /*
         * Comes out of screen and shake animation*/
        DisplayMetrics outMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        TranslateAnimation animation = new TranslateAnimation(outMetrics.widthPixels,
                0, 0, 0);
        animation.setDuration(700);
        care.startAnimation(animation);

        /*Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
        note.startAnimation(animShake);*/
        new Handler().postDelayed(() -> {
            YoYo.with(Techniques.Shake).playOn(care);
        }, 705);
    }

    private void uiInit() {
        roof = findViewById(R.id.roof);
        care = findViewById(R.id.care);
    }
}