package com.glowcart.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.glowcart.app.network.ApiService;
import com.glowcart.app.network.RetrofitClient;
import com.google.android.material.badge.BadgeDrawable;
import com.glowcart.app.utils.CartManager;

import com.glowcart.app.fragments.CartFragment;
import com.glowcart.app.fragments.CategoriesFragment;
import com.glowcart.app.fragments.HomeFragment;
import com.glowcart.app.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    BadgeDrawable cartBadge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        cartBadge = bottomNavigationView.getOrCreateBadge(R.id.nav_cart);
        cartBadge.setVisible(false);

        // Default Fragment
        loadFragment(new HomeFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {

            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.nav_categories) {
                selectedFragment = new CategoriesFragment();
            } else if (item.getItemId() == R.id.nav_cart) {
                selectedFragment = new CartFragment();
            } else if (item.getItemId() == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }

            return true;
        });
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        apiService.healthCheck().enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                if (response.isSuccessful()) {
                    System.out.println("✅ Backend Response: " + response.body());
                } else {
                    System.out.println("❌ Backend error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                System.out.println("❌ Connection failed: " + t.getMessage());
            }
        });

    }


    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void updateCartBadge() {
        int count = CartManager.getTotalCartItems();

        if (count > 0) {
            cartBadge.setVisible(true);
            cartBadge.setNumber(count);
        } else {
            cartBadge.setVisible(false);
            cartBadge.clearNumber();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateCartBadge();
    }

}
