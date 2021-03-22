package com.example.roofcare.activities.register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.roofcare.R;
import com.example.roofcare.activities.logIn.LogInActivity;
import com.example.roofcare.animationsPackage.Techniques;
import com.example.roofcare.animationsPackage.YoYo;
import com.example.roofcare.helper.textWatcherValidation.TextWatcherVerify;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegisterPhoneNumActivity extends AppCompatActivity {
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
                Intent intent = new Intent(this, UserTypeActivity.class);
                intent.putExtra("phone", phoneNumber.getText().toString());
                startActivity(intent);
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
            /*Intent sharedIntents = new Intent(this, LogInActivity.class);
            Pair[] pairs = new Pair[2];
            pairs[0] = new Pair<View, String>(roof, "roof");
            pairs[1] = new Pair<View, String>(care, "care");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pairs);
                startActivity(sharedIntents, options.toBundle());
                this.finish();
            }*/
            onBackPressed();
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