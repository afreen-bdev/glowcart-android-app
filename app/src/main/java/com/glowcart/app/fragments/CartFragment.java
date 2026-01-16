package com.glowcart.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.glowcart.app.R;
import com.glowcart.app.activities.CheckoutActivity;
import com.glowcart.app.adapters.CartAdapter;
import com.glowcart.app.models.CartItemModel;
import com.glowcart.app.utils.CartManager;

import java.util.List;

public class CartFragment extends Fragment implements CartAdapter.CartListener {

    RecyclerView rvCart;
    TextView tvTotalAmount;
    Button btnCheckout;

    List<CartItemModel> cartList;
    CartAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        rvCart = view.findViewById(R.id.rvCart);
        tvTotalAmount = view.findViewById(R.id.tvTotalAmount);
        btnCheckout = view.findViewById(R.id.btnCheckout);

        rvCart.setLayoutManager(new LinearLayoutManager(getContext()));

        cartList = CartManager.getCartList();

        adapter = new CartAdapter(getContext(), cartList, this);
        rvCart.setAdapter(adapter);

        calculateTotal();

        btnCheckout.setOnClickListener(v -> {
            if (cartList == null || cartList.isEmpty()) {
                Toast.makeText(getContext(), "Cart is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(new Intent(getContext(), CheckoutActivity.class));
        });

        return view;
    }

    private void calculateTotal() {
        int total = 0;

        if (cartList != null) {
            for (CartItemModel item : cartList) {
                total += item.getPrice() * item.getQty();
            }
        }

        tvTotalAmount.setText("₹" + total);
    }

    @Override
    public void onCartUpdated() {
        calculateTotal();
    }

    @Override
    public void onResume() {
        super.onResume();

        cartList = CartManager.getCartList();
        if (adapter != null) adapter.notifyDataSetChanged();
        calculateTotal();
    }
}
