package com.glowcart.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.glowcart.app.network.ApiClient;
import com.glowcart.app.network.ApiService;
import com.glowcart.app.responses.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AppCompatActivity;

import com.glowcart.app.MainActivity;
import com.glowcart.app.R;
import com.glowcart.app.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    EditText etName, etMobile;
    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        etName = findViewById(R.id.etName);
        etMobile = findViewById(R.id.etMobile);
        btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String mobile = etMobile.getText().toString().trim();

            if (name.isEmpty()) {
                etName.setError("Enter name");
                etName.requestFocus();
                return;
            }

            if (mobile.length() != 10) {
                etMobile.setError("Enter valid mobile");
                etMobile.requestFocus();
                return;
            }

            ApiService api = ApiClient.getClient().create(ApiService.class);

            api.login(name, mobile).enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {

                        UserResponse user = response.body();

                        // ✅ Save correct values
                        SessionManager.saveUser(
                                LoginActivity.this,
                                user.getName(),
                                user.getMobile()
                        );

                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "API Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        });
    }
}