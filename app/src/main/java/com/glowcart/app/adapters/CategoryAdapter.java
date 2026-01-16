package com.glowcart.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.glowcart.app.R;
import com.glowcart.app.activities.CategoryProductsActivity;
import com.glowcart.app.models.ApiCategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryVH> {

    private final Context context;
    private final List<ApiCategoryModel> list;

    public CategoryAdapter(Context context, List<ApiCategoryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new CategoryVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryVH holder, int position) {

        ApiCategoryModel model = list.get(position);

        holder.tvName.setText(model.getName());

        // ✅ icon from API
        Glide.with(context)
                .load(model.getImageUrl())
                .placeholder(R.drawable.prod1)
                .error(R.drawable.prod1)
                .into(holder.img);

        // ✅ click -> open category products page
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CategoryProductsActivity.class);
            intent.putExtra("category", model.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class CategoryVH extends RecyclerView.ViewHolder {
        LinearLayout layoutCategory;
        ImageView img;
        TextView tvName;

        public CategoryVH(@NonNull View itemView) {
            super(itemView);
            layoutCategory = itemView.findViewById(R.id.layoutCategory);
            img = itemView.findViewById(R.id.imgCategory);
            tvName = itemView.findViewById(R.id.tvCategoryName);
        }
    }
}
