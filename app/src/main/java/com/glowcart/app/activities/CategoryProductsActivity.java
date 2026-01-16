package com.glowcart.app.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.glowcart.app.R;
import com.glowcart.app.adapters.ProductAdapter;
import com.glowcart.app.models.ApiProductModel;
import com.glowcart.app.network.ApiService;
import com.glowcart.app.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryProductsActivity extends AppCompatActivity {

    RecyclerView rvCategoryProducts;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);

        tvTitle = findViewById(R.id.tvTitle);
        rvCategoryProducts = findViewById(R.id.rvCategoryProducts);

        rvCategoryProducts.setLayoutManager(new GridLayoutManager(this, 2));

        String category = getIntent().getStringExtra("category");
        if (category == null) category = "";

        tvTitle.setText(category);

        loadCategoryProducts(category);
    }

    private void loadCategoryProducts(String category) {

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        apiService.getProductsByCategory(category).enqueue(new Callback<List<ApiProductModel>>() {
            @Override
            public void onResponse(Call<List<ApiProductModel>> call, Response<List<ApiProductModel>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    rvCategoryProducts.setAdapter(new ProductAdapter(CategoryProductsActivity.this, response.body()));
                } else {
                    Toast.makeText(CategoryProductsActivity.this, "No products found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ApiProductModel>> call, Throwable t) {
                Toast.makeText(CategoryProductsActivity.this, "API Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
