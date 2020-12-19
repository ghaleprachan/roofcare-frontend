package com.example.roofcare.activities.bookingsForms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.example.roofcare.R;
import com.example.roofcare.databinding.ActivityBookRequestFormBinding;

import java.util.Calendar;
import java.util.Objects;

public class BookRequestFormActivity extends AppCompatActivity {
    private ActivityBookRequestFormBinding binding;
    private int YEAR = 0;
    private int MONTH = 0;
    private int DATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookRequestFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toast.makeText(this, getId().toString(), Toast.LENGTH_SHORT).show();
        onSetDateClick();
        onBackClick();
    }

    private void onBackClick() {
        binding.back.setOnClickListener(v -> onBackPressed());
    }

    private Integer getId() {
        Bundle bundle = getIntent().getExtras();
        return bundle.getInt("Id", 0);
    }

    private void onSetDateClick() {
        binding.serviceDate.setOnClickListener(v -> {
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
                        Calendar newCalendar = Calendar.getInstance();
                        newCalendar.set(Calendar.YEAR, year);
                        newCalendar.set(Calendar.MONTH, month);
                        newCalendar.set(Calendar.DATE, date);
                        CharSequence charSequence = DateFormat.format("MMM d, yyyy", newCalendar);
                        binding.serviceDate.setText(charSequence);
                    }, YEAR, MONTH, DATE);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });
    }
}