package com.glowcart.app.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.glowcart.app.R;
import com.glowcart.app.adapters.OrderItemsAdapter;
import com.glowcart.app.models.ApiOrderModel;
import com.google.gson.Gson;

public class OrderDetailsActivity extends AppCompatActivity {

    TextView tvOrderId, tvOrderStatus, tvOrderDate, tvTotal, tvAddress;
    RecyclerView rvOrderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        tvOrderId = findViewById(R.id.tvOrderId);
        tvOrderStatus = findViewById(R.id.tvOrderStatus);
        tvOrderDate = findViewById(R.id.tvOrderDate);
        tvTotal = findViewById(R.id.tvTotal);
        tvAddress = findViewById(R.id.tvAddress);
        rvOrderItems = findViewById(R.id.rvOrderItems);

        rvOrderItems.setLayoutManager(new LinearLayoutManager(this));

        // ✅ Receive order JSON
        String orderJson = getIntent().getStringExtra("order");
        ApiOrderModel order = new Gson().fromJson(orderJson, ApiOrderModel.class);

        tvOrderId.setText("Order ID: GC-" + order.getId());
        tvOrderStatus.setText("Status: " + order.getStatus());
        tvOrderDate.setText("Date: " + (order.getCreatedAt() == null ? "--" : order.getCreatedAt()));
        tvTotal.setText("Total: ₹" + order.getTotalAmount());

        // ✅ Address formatting
        if (order.getAddress() != null) {
            ApiOrderModel.Address a = order.getAddress();

            String addressText =
                    a.getFullName() + " (" + a.getMobile() + ")\n" +
                            a.getLine1() +
                            (a.getLine2() != null && !a.getLine2().isEmpty() ? (", " + a.getLine2()) : "") +
                            "\n" +
                            a.getCity() + " - " + a.getPincode() +
                            "\n" +
                            a.getState();

            tvAddress.setText(addressText);
        } else {
            tvAddress.setText("No address found");
        }

        // ✅ Items adapter
        rvOrderItems.setAdapter(new OrderItemsAdapter(this, order.getItems()));
    }
}
