package com.glowcart.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.glowcart.app.R;
import com.glowcart.app.activities.ProductDetailsActivity;
import com.glowcart.app.models.ApiProductModel;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final Context context;
    private final List<ApiProductModel> productList;

    public ProductAdapter(Context context, List<ApiProductModel> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ApiProductModel product = productList.get(position);

        holder.tvName.setText(product.getName());
        holder.tvBrand.setText(product.getBrand());
        holder.tvPrice.setText("₹" + product.getPrice());

        // ✅ VERY IMPORTANT: Clear old recycled image
        Glide.with(context).clear(holder.imgProduct);

        // ✅ Load image URL (SpringBoot hosted)
        Glide.with(context)
                .load(product.getImageUrl())
                .placeholder(R.drawable.prod1)
                .error(R.drawable.prod1)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgProduct);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);

            intent.putExtra("name", product.getName());
            intent.putExtra("id", product.getId());
            intent.putExtra("brand", product.getBrand());
            intent.putExtra("price", "₹" + product.getPrice());
            intent.putExtra("oldPrice", "₹" + product.getOldPrice());
            intent.putExtra("rating", product.getRating());
            intent.putExtra("description", product.getDescription());
            intent.putExtra("imageUrl", product.getImageUrl());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvBrand, tvPrice;
        ImageView imgProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvBrand = itemView.findViewById(R.id.tvBrand);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            imgProduct = itemView.findViewById(R.id.imgProduct);
        }
    }
}
