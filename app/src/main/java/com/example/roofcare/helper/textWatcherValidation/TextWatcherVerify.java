package com.example.roofcare.helper.textWatcherValidation;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class TextWatcherVerify {
    public static void textWatcher(List<EditText> editTexts, Button button) {
        for (int i = 0; i < editTexts.size(); i++) {
            editTexts.get(i).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    for (int j = 0; j < editTexts.size(); j++) {
                        if (editTexts.get(j).getText().toString().isEmpty()) {
                            button.setEnabled(false);
                            break;
                        } else {
                            button.setEnabled(true);
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }
}
