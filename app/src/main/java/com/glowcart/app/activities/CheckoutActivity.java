package com.glowcart.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.glowcart.app.R;
import com.glowcart.app.models.CartItemModel;
import com.glowcart.app.utils.CartManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CheckoutActivity extends AppCompatActivity {

    EditText etFullName, etPhone, etAddress, etCity, etPincode;
    TextView tvItemsCount, tvTotalPayable;
    RadioButton rbCOD, rbUPI;
    Button btnPlaceOrder;

    private final OkHttpClient client = new OkHttpClient();

    private static final String BASE_URL = "http://10.0.2.2:8080";
    private static final String PREFS = "GlowCartPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        etFullName = findViewById(R.id.etFullName);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etCity = findViewById(R.id.etCity);
        etPincode = findViewById(R.id.etPincode);

        tvItemsCount = findViewById(R.id.tvItemsCount);
        tvTotalPayable = findViewById(R.id.tvTotalPayable);

        rbCOD = findViewById(R.id.rbCOD);
        rbUPI = findViewById(R.id.rbUPI);

        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        loadSavedAddress();
        updateSummary();

        btnPlaceOrder.setOnClickListener(v -> {

            if (CartManager.getCartList().isEmpty()) {
                Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!validateAddress()) return;

            if (rbUPI.isChecked()) {
                Toast.makeText(this, "UPI payment coming soon", Toast.LENGTH_SHORT).show();
                return;
            }

            saveAddressLocal();
            placeOrderApi();
        });
    }

    private void updateSummary() {
        List<CartItemModel> cartList = CartManager.getCartList();
        int items = 0;
        int total = 0;

        for (CartItemModel item : cartList) {
            items += item.getQty();
            total += item.getQty() * item.getPrice();
        }

        tvItemsCount.setText("Items: " + items);
        tvTotalPayable.setText("Total: ₹" + total);
    }

    private boolean validateAddress() {
        if (etFullName.getText().toString().trim().isEmpty()) {
            etFullName.setError("Required");
            return false;
        }
        if (etPhone.getText().toString().trim().length() != 10) {
            etPhone.setError("Enter valid 10 digit phone");
            return false;
        }
        if (etAddress.getText().toString().trim().isEmpty()) {
            etAddress.setError("Required");
            return false;
        }
        if (etCity.getText().toString().trim().isEmpty()) {
            etCity.setError("Required");
            return false;
        }
        if (etPincode.getText().toString().trim().length() != 6) {
            etPincode.setError("Enter valid pincode");
            return false;
        }
        return true;
    }

    private void saveAddressLocal() {
        SharedPreferences sp = getSharedPreferences(PREFS, MODE_PRIVATE);
        sp.edit()
                .putString("fullName", etFullName.getText().toString().trim())
                .putString("mobile", etPhone.getText().toString().trim())
                .putString("address", etAddress.getText().toString().trim())
                .putString("city", etCity.getText().toString().trim())
                .putString("pincode", etPincode.getText().toString().trim())
                .apply();
    }

    private void loadSavedAddress() {
        SharedPreferences sp = getSharedPreferences(PREFS, MODE_PRIVATE);

        etFullName.setText(sp.getString("fullName", ""));
        etPhone.setText(sp.getString("mobile", ""));
        etAddress.setText(sp.getString("address", ""));
        etCity.setText(sp.getString("city", ""));
        etPincode.setText(sp.getString("pincode", ""));
    }

    private void placeOrderApi() {
        try {
            JSONObject requestJson = new JSONObject();

            JSONObject addressJson = new JSONObject();
            addressJson.put("fullName", etFullName.getText().toString().trim());
            addressJson.put("mobile", etPhone.getText().toString().trim());
            addressJson.put("line1", etAddress.getText().toString().trim());
            addressJson.put("line2", "");
            addressJson.put("city", etCity.getText().toString().trim());
            addressJson.put("state", "Telangana");
            addressJson.put("pincode", etPincode.getText().toString().trim());

            requestJson.put("address", addressJson);

            JSONArray itemsArray = new JSONArray();
            int total = 0;

            for (CartItemModel item : CartManager.getCartList()) {
                JSONObject itemJson = new JSONObject();
                itemJson.put("productId", item.getProductId());
                itemJson.put("name", item.getName());
                itemJson.put("brand", item.getBrand());
                itemJson.put("price", item.getPrice());
                itemJson.put("qty", item.getQty());
                itemJson.put("imageUrl", item.getImageUrl());

                itemsArray.put(itemJson);

                total += item.getPrice() * item.getQty();
            }

            requestJson.put("items", itemsArray);
            requestJson.put("totalAmount", total);

            String url = BASE_URL + "/api/orders/place";

            RequestBody body = RequestBody.create(
                    requestJson.toString(),
                    MediaType.parse("application/json")
            );

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            btnPlaceOrder.setEnabled(false);

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> {
                        btnPlaceOrder.setEnabled(true);
                        Toast.makeText(CheckoutActivity.this, "Order failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(() -> btnPlaceOrder.setEnabled(true));

                    if (response.isSuccessful()) {
                        CartManager.clearCart();

                        runOnUiThread(() -> {
                            Toast.makeText(CheckoutActivity.this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CheckoutActivity.this, OrderSuccessActivity.class));
                            finish();
                        });
                    } else {
                        runOnUiThread(() ->
                                Toast.makeText(CheckoutActivity.this, "Order failed: " + response.code(), Toast.LENGTH_LONG).show()
                        );
                    }
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
