package com.glowcart.app.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.glowcart.app.R;
import com.glowcart.app.adapters.CategoryAdapter;
import com.glowcart.app.adapters.ProductAdapter;
import com.glowcart.app.models.ApiCategoryModel;
import com.glowcart.app.models.ApiProductModel;
import com.glowcart.app.network.ApiService;
import com.glowcart.app.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private ImageSlider imageSlider;
    private RecyclerView rvProducts;
    private RecyclerView rvCategories;

    private CategoryAdapter categoryAdapter;
    private List<ApiCategoryModel> categoryList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        imageSlider = view.findViewById(R.id.imageSlider);

        // Banner images
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner1, null));
        slideModels.add(new SlideModel(R.drawable.banner2, null));
        slideModels.add(new SlideModel(R.drawable.banner3, null));
        imageSlider.setImageList(slideModels);

        // ✅ Categories Recycler
        rvCategories = view.findViewById(R.id.rvCategories);
        rvCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        categoryAdapter = new CategoryAdapter(getContext(), categoryList);
        rvCategories.setAdapter(categoryAdapter);

        // ✅ Products Recycler (API)
        rvProducts = view.findViewById(R.id.rvProducts);
        rvProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // ✅ Load both
        loadCategoriesFromApi();
        loadProductsFromApi();

        return view;
    }

    private void loadCategoriesFromApi() {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        apiService.getCategories().enqueue(new Callback<List<ApiCategoryModel>>() {
            @Override
            public void onResponse(Call<List<ApiCategoryModel>> call, Response<List<ApiCategoryModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    categoryList.clear();
                    categoryList.addAll(response.body());
                    categoryAdapter.notifyDataSetChanged();

                    Log.d("HOME_CAT", "✅ Top Categories Loaded: " + categoryList.size());

                } else {
                    Toast.makeText(getContext(), "Failed to load categories", Toast.LENGTH_SHORT).show();
                    Log.e("HOME_CAT", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ApiCategoryModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Categories API Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("HOME_CAT", "Failure: " + t.getMessage());
            }
        });
    }

    private void loadProductsFromApi() {

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        apiService.getProducts().enqueue(new Callback<List<ApiProductModel>>() {
            @Override
            public void onResponse(Call<List<ApiProductModel>> call, Response<List<ApiProductModel>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    List<ApiProductModel> productList = response.body();
                    rvProducts.setAdapter(new ProductAdapter(getContext(), productList));
                } else {
                    Toast.makeText(getContext(), "Failed to load products", Toast.LENGTH_SHORT).show();
                    Log.e("HOME_API", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ApiProductModel>> call, Throwable t) {
                Toast.makeText(getContext(), "API Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("HOME_API", "Failure: " + t.getMessage());
            }
        });
    }
}