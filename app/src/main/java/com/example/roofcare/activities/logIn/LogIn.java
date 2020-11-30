package com.example.roofcare.activities.logIn;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.roofcare.R;
import com.example.roofcare.activities.dashboard.Dashboard;
import com.example.roofcare.activities.register.Register;
import com.example.roofcare.helper.TextWatcherVerify;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LogIn extends AppCompatActivity {
    private TextView register, g, note;
    private TextInputEditText userName, password;
    private Button logIn;

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
            startActivity(new Intent(this, Dashboard.class));
            finish();
        });
    }

    private void onRegisterTextClick() {
        register.setOnClickListener(v -> {
            startRegisterActivity();
        });
    }

    private void startRegisterActivity() {
        Intent sharedIntents = new Intent(this, Register.class);
        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View, String>(g, "roof");
        pairs[1] = new Pair<View, String>(note, "care");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pairs);
            startActivity(sharedIntents, options.toBundle());
            finish();
        }
    }

    private void UiInitialize() {
        register = findViewById(R.id.textViewRegister);
        g = findViewById(R.id.textG);
        note = findViewById(R.id.textNote);
        logIn = findViewById(R.id.buttonLogIn);
        userName = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);
    }

    @Override
    public void onBackPressed() {
        startRegisterActivity();
    }
}
