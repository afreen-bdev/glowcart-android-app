package com.glowcart.app.network;

import com.glowcart.app.models.ApiCategoryModel;
import com.glowcart.app.models.ApiProductModel;
import com.glowcart.app.responses.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    @FormUrlEncoded
    @POST("api/auth/login")
    Call<UserResponse> login(
            @Field("name") String name,
            @Field("mobile") String mobile
    );

    @GET("api/products")
    Call<List<ApiProductModel>> getProducts();

    @GET("api/products")
    Call<List<ApiProductModel>> getProductsByCategory(@Query("category") String category);

    @GET("api/categories")
    Call<List<ApiCategoryModel>> getCategories();
}
