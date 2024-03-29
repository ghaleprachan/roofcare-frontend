package com.example.homesewa.activities.logIn;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.homesewa.R;
import com.example.homesewa.activities.dashboard.DashboardActivity;
import com.example.homesewa.activities.register.RegisterPhoneNumActivity;
import com.example.homesewa.animationsPackage.Techniques;
import com.example.homesewa.animationsPackage.YoYo;
import com.example.homesewa.apis.ApiCollection;
import com.example.homesewa.helper.textWatcherValidation.TextWatcherVerify;
import com.example.homesewa.models.userAuthentocaionModel.AuthenticationResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LogInActivity extends AppCompatActivity {
    private TextView register, g, note, errorInput;
    private TextInputEditText userName, password;
    private Button logIn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        UiInitialize();
        userName.setSelection(Objects.requireNonNull(userName.getText()).toString().length());
        password.setSelection(Objects.requireNonNull(password.getText()).toString().length());
        onRegisterTextClick();
        registerFormVerification();
        onLogInButtonClick();
    }

    private void registerFormVerification() {
        logIn.setEnabled(!Objects.requireNonNull(userName.getText()).toString().isEmpty()
                && !Objects.requireNonNull(password.getText()).toString().isEmpty());
        List<EditText> editTexts = new ArrayList<>();
        editTexts.add(userName);
        editTexts.add(password);
        TextWatcherVerify.textWatcher(editTexts, logIn);
    }

    private void onLogInButtonClick() {
        logIn.setOnClickListener(v -> {
            apiCall();
        });
    }

    private void apiCall() {
        try {
            errorInput.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            logIn.setEnabled(false);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Username", Objects.requireNonNull(userName.getText()).toString());
            jsonBody.put("Password", Objects.requireNonNull(password.getText()).toString());
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    ApiCollection.logInAuthentication,
                    jsonBody,
                    response -> {
                        errorInput.setVisibility(View.GONE);
                        logIn.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                        try {
                            Gson gson = new GsonBuilder().create();
                            AuthenticationResponse authenticationResponse = gson.fromJson(String.valueOf(response), AuthenticationResponse.class);
                            if (authenticationResponse.getSuccess()) {
                                errorInput.setVisibility(View.GONE);
                                gotToDashboard(authenticationResponse);
                            } else {
                                errorInput.setVisibility(View.VISIBLE);
                                YoYo.with(Techniques.Shake).playOn(errorInput);
                            }
                        } catch (Exception ex) {
                            YoYo.with(Techniques.Shake).playOn(errorInput);
                            errorInput.setVisibility(View.VISIBLE);
                            errorInput.setText(ex.getMessage());
                        }
                    },
                    error -> {
                        progressBar.setVisibility(View.GONE);
                        logIn.setEnabled(true);
                        YoYo.with(Techniques.Shake).playOn(errorInput);
                        errorInput.setVisibility(View.VISIBLE);
                        errorInput.setText(error.toString());
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(LogInActivity.this);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void gotToDashboard(AuthenticationResponse authenticationResponse) {
        SharedPreferences preferences = getSharedPreferences("LOGIN_DETAILS", 0);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("UserId", authenticationResponse.getUserId());
        editor.putString("Username", authenticationResponse.getUsername());
        editor.putString("FullName", authenticationResponse.getFullName());
        editor.putString("UserImage", ApiCollection.baseUrl + authenticationResponse.getUserImage());
        editor.putString("UserType", authenticationResponse.getUserType());
        editor.apply();
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }

    private void onRegisterTextClick() {
        register.setOnClickListener(v -> startRegisterActivity());
    }


    private void startRegisterActivity() {
        Intent sharedIntents = new Intent(this, RegisterPhoneNumActivity.class);
        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View, String>(g, "roof");
        pairs[1] = new Pair<View, String>(note, "care");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pairs);
            startActivity(sharedIntents, options.toBundle());
        }
    }

    private void UiInitialize() {
        register = findViewById(R.id.textViewRegister);
        g = findViewById(R.id.textG);
        note = findViewById(R.id.textNote);
        logIn = findViewById(R.id.buttonLogIn);
        userName = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        errorInput = findViewById(R.id.errorInput);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
