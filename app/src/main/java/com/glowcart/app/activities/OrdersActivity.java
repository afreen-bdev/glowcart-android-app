package com.glowcart.app.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.glowcart.app.R;
import com.glowcart.app.adapters.OrdersAdapter;
import com.glowcart.app.models.ApiOrderModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrdersActivity extends AppCompatActivity {

    RecyclerView rvOrders;
    OrdersAdapter adapter;

    private final OkHttpClient client = new OkHttpClient();
    private static final String BASE_URL = "http://10.0.2.2:8080"; // emulator

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        rvOrders = findViewById(R.id.rvOrders);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));

        adapter = new OrdersAdapter(this, new ArrayList<>());
        rvOrders.setAdapter(adapter);

        fetchOrders();
    }

    private void fetchOrders() {

        String url = BASE_URL + "/api/orders";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(OrdersActivity.this, "Failed: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (!response.isSuccessful()) {
                    runOnUiThread(() ->
                            Toast.makeText(OrdersActivity.this, "Error: " + response.code(), Toast.LENGTH_LONG).show()
                    );
                    return;
                }

                String json = response.body().string();

                List<ApiOrderModel> orders = Arrays.asList(
                        new Gson().fromJson(json, ApiOrderModel[].class)
                );

                runOnUiThread(() -> adapter.setList(orders));
            }
        });
    }
}
