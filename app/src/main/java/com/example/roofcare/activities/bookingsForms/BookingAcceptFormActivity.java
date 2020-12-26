package com.example.roofcare.activities.bookingsForms;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.roofcare.apis.ApiCollection;
import com.example.roofcare.databinding.ActivityBookingAcceptFormBinding;
import com.example.roofcare.services.bookingService.BookingRequestServiceClass;

import org.json.JSONException;
import org.json.JSONObject;

public class BookingAcceptFormActivity extends AppCompatActivity {
    private ActivityBookingAcceptFormBinding vb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vb = ActivityBookingAcceptFormBinding.inflate(getLayoutInflater());
        setContentView(vb.getRoot());
        vb.serviceCharge.requestFocus();
        onBackClick();
        onServiceChargeChange();
        onDiscountChange();
        onTravellingCostChange();
        onMakeBillClick();
    }

    private void onMakeBillClick() {
        vb.makeBill.setOnClickListener(v -> {
            if (vb.serviceCharge.getText().toString().isEmpty()) {
                vb.serviceCharge.requestFocus();
                vb.serviceCharge.setError("Enter service charge");
            } else {
                try {
                    vb.makeBill.setEnabled(false);
                    vb.progress.setVisibility(View.VISIBLE);
                    JSONObject object = new JSONObject();
                    object.put("bookingId", getBookingId());
                    object.put("serviceCharge", vb.serviceCharge.getText().toString());
                    if (vb.travellingCost.getText().toString().isEmpty()) {
                        object.put("travellingCost", 0);
                    } else {
                        object.put("travellingCost", vb.travellingCost.getText().toString());
                    }
                    if (vb.discount.getText().toString().isEmpty()) {
                        object.put("discountPercentage", 0);
                    } else {
                        object.put("discountPercentage", vb.discount.getText().toString());
                    }
                    object.put("totalCharge", vb.totalCost.getText().toString());

                    JsonObjectRequest request = new JsonObjectRequest(
                            Request.Method.PUT,
                            ApiCollection.sendBookingRequest,
                            object,
                            response -> {
                                vb.makeBill.setEnabled(true);
                                vb.progress.setVisibility(View.GONE);
                                try {
                                    if (response.get("Success").equals(true)) {
                                        Toast.makeText(this, "Request Accepted", Toast.LENGTH_SHORT).show();
                                        BookingRequestServiceClass.responseModel.get(0).getImBooked().remove(getIntent().getIntExtra("position", 0));

                                        Intent intent = new Intent();
                                        intent.putExtra("BookingId", getBookingId());
                                        Intent returnIntent = new Intent();
                                        returnIntent.putExtra("result", "Ok");
                                        setResult(Activity.RESULT_OK, returnIntent);
                                        finish();
                                    } else {
                                        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(this, "Failed to accept", Toast.LENGTH_SHORT).show();
                                }
                            },
                            error -> {
                                vb.makeBill.setEnabled(true);
                                vb.progress.setVisibility(View.GONE);
                                Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    );
                    RequestQueue queue = Volley.newRequestQueue(this);
                    queue.add(request);
                } catch (Exception ex) {
                    Toast.makeText(this, "Parsing error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onTravellingCostChange() {
        vb.travellingCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                vb.totalCost.setText(getTotalCost());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void onDiscountChange() {
        vb.discount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!vb.discount.getText().toString().isEmpty()) {
                    if (Double.parseDouble(vb.discount.getText().toString()) > 100) {
                        vb.discount.requestFocus();
                        vb.discount.setError("Discount more that 100%");
                    } else {
                        vb.totalCost.setText(getTotalCost());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void onServiceChargeChange() {
        vb.serviceCharge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!vb.serviceCharge.getText().toString().isEmpty()) {
                    vb.totalCost.setText(getTotalCost());
                } else {
                    vb.totalCost.setText("0.00");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private String calculateTotalCost(double serviceCharge, double discount, double travellingCost) {
        double totalCharge = (serviceCharge * ((100.0f - discount) / 100.0f)) + travellingCost;
        return Double.toString(totalCharge);
    }

    private String getTotalCost() {
        return calculateTotalCost(getServiceCharge(), getDiscount(), getTravellingCost());
    }

    private Double getServiceCharge() {
        if (vb.serviceCharge.getText().toString().isEmpty()) {
            return 0.0;
        } else {
            return Double.parseDouble(vb.serviceCharge.getText().toString());
        }
    }

    private Double getDiscount() {
        if (vb.discount.getText().toString().isEmpty()) {
            return 0.0;
        } else {
            return Double.parseDouble(vb.discount.getText().toString());
        }
    }

    private Double getTravellingCost() {
        if (vb.travellingCost.getText().toString().isEmpty()) {
            return 0.0;
        } else {
            return Double.parseDouble(vb.travellingCost.getText().toString());
        }
    }

    private Integer getBookingId() {
        return BookingRequestServiceClass.responseModel.get(0).getImBooked().get(getIntent().getIntExtra("position", -1)).getBookingId();
    }

    private void onBackClick() {
        vb.back.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}