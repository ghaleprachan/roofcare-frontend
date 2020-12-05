package com.example.roofcare.activities.register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roofcare.R;
import com.example.roofcare.activities.dashboard.Dashboard;
import com.example.roofcare.activities.logIn.LogIn;
import com.example.roofcare.animationsPackage.Techniques;
import com.example.roofcare.animationsPackage.YoYo;
import com.example.roofcare.helper.textWatcherValidation.TextWatcherVerify;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Register extends AppCompatActivity {
    private TextView logIn, roof, care;
    private Button register;
    private TextInputEditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        UiInitialize();
        phoneNumber.setSelection(Objects.requireNonNull(phoneNumber.getText()).toString().length());
        onLogInTextClick();
        fieldVerification();
        onRegisterButtonClick();
    }

    private void onRegisterButtonClick() {
        register.setOnClickListener(v -> {
            if (Objects.requireNonNull(phoneNumber.getText()).toString().length() != 10) {
                YoYo.with(Techniques.Shake).playOn(phoneNumber);
                phoneNumber.requestFocus();
                phoneNumber.setError("Invalid phone number");
            } else {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fieldVerification() {
        register.setEnabled(!Objects.requireNonNull(phoneNumber.getText()).toString().isEmpty());
        List<EditText> editTexts = new ArrayList<>();
        editTexts.add(phoneNumber);
        TextWatcherVerify.textWatcher(editTexts, register);
    }

    private void onLogInTextClick() {
        logIn.setOnClickListener(v -> {
            Intent sharedIntents = new Intent(this, LogIn.class);
            Pair[] pairs = new Pair[2];
            pairs[0] = new Pair<View, String>(roof, "textG");
            pairs[1] = new Pair<View, String>(care, "textNote");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pairs);
                startActivity(sharedIntents, options.toBundle());
                finish();
            }
        });
    }

    private void UiInitialize() {
        logIn = findViewById(R.id.textViewLogIn);
        roof = findViewById(R.id.textG);
        care = findViewById(R.id.textNote);
        register = findViewById(R.id.buttonRegister);
        phoneNumber = findViewById(R.id.phoneNumber);
    }
}