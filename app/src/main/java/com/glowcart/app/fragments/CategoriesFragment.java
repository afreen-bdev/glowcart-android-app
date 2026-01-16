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
import androidx.recyclerview.widget.RecyclerView;

import com.glowcart.app.R;
import com.glowcart.app.adapters.CategoryAdapter;
import com.glowcart.app.models.ApiCategoryModel;
import com.glowcart.app.network.ApiService;
import com.glowcart.app.network.RetrofitClient;
import com.glowcart.app.utils.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesFragment extends Fragment {

    RecyclerView rvCategories;
    CategoryAdapter adapter;
    List<ApiCategoryModel> categoryList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        rvCategories = view.findViewById(R.id.rvCategories);

        if (rvCategories == null) {
            Toast.makeText(getContext(), "RecyclerView not found in fragment_categories.xml", Toast.LENGTH_LONG).show();
            return view;
        }

        rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvCategories.addItemDecoration(new GridSpacingItemDecoration(2, 18, true));


        adapter = new CategoryAdapter(getContext(), categoryList);
        rvCategories.setAdapter(adapter);

        loadCategories();

        return view;
    }

    private void loadCategories() {

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        apiService.getCategories().enqueue(new Callback<List<ApiCategoryModel>>() {
            @Override
            public void onResponse(Call<List<ApiCategoryModel>> call, Response<List<ApiCategoryModel>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    categoryList.clear();
                    categoryList.addAll(response.body());
                    adapter.notifyDataSetChanged();

                    Log.d("CAT_API", "✅ Categories Loaded: " + categoryList.size());

                } else {
                    Toast.makeText(getContext(), "Failed to load categories", Toast.LENGTH_SHORT).show();
                    Log.e("CAT_API", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ApiCategoryModel>> call, Throwable t) {
                Toast.makeText(getContext(), "API Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("CAT_API", "Failure: " + t.getMessage());
            }
        });
    }
}
