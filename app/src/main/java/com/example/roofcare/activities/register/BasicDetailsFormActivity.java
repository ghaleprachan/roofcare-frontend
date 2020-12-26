package com.example.roofcare.activities.register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.example.roofcare.R;
import com.example.roofcare.databinding.ActivityBasicDetailsFormBinding;
import com.example.roofcare.models.registerModel.RegisterModel;

import java.util.Calendar;
import java.util.Objects;

public class BasicDetailsFormActivity extends AppCompatActivity {
    private ActivityBasicDetailsFormBinding binding;
    private int YEAR = 0;
    private int MONTH = 0;
    private int DATE = 0;
    private Calendar newCalendar;
    private CharSequence charSequence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBasicDetailsFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.fullName.requestFocus();
        onDobClick();
        onNextClick();
        onBackClick();
    }

    private void onBackClick() {
        binding.backBtn.setOnClickListener(v -> onBackPressed());
    }

    private void onNextClick() {
        binding.next.setOnClickListener(v -> {
            if (binding.fullName.getText().toString().isEmpty()) {
                binding.fullName.requestFocus();
                binding.fullNameL.setError("Enter your name!");
            } else if (binding.dob.getText().toString().isEmpty()) {
                binding.dob.requestFocus();
                binding.fullNameL.setError(null);
                binding.dobL.setError("Add you date of birth!");
            } else if (binding.address.getText().toString().isEmpty()) {
                binding.address.requestFocus();
                binding.dobL.setError(null);
                binding.addressL.setError("Enter your address!");
            } else if (binding.about.getText().toString().isEmpty()) {
                binding.about.requestFocus();
                binding.addressL.setError(null);
                binding.aboutL.setError("Add something about yourself!");
            } else {
                RegisterModel.setFullName(binding.fullName.getText().toString());
                RegisterModel.setDob(newCalendar);
                RegisterModel.setAddress(binding.address.getText().toString());
                RegisterModel.setAboutUser(binding.about.getText().toString());
                RegisterModel.setGender(binding.gender.getSelectedItem().toString());
                startActivity(new Intent(this, UsernamePassActivity.class));
            }
        });
    }

    private void onDobClick() {
        binding.dob.setOnClickListener(v -> {
            if (YEAR == 0 || MONTH == 0 || DATE == 0) {
                Calendar calendar = Calendar.getInstance();
                YEAR = calendar.get(Calendar.YEAR);
                MONTH = calendar.get(Calendar.MONTH);
                DATE = calendar.get(Calendar.DATE);
            }
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (datePicker, year, month, date) -> {
                        YEAR = year;
                        MONTH = month;
                        DATE = date;
                        newCalendar = Calendar.getInstance();
                        newCalendar.set(Calendar.YEAR, year);
                        newCalendar.set(Calendar.MONTH, month);
                        newCalendar.set(Calendar.DATE, date);

                        charSequence = DateFormat.format("MMM d, yyyy", newCalendar);
                        binding.dob.setText(charSequence);
                    }, YEAR, MONTH, DATE);

            datePickerDialog.show();
        });
    }
}