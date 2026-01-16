package com.glowcart.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.glowcart.app.R;
import com.glowcart.app.models.CartItemModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartVH> {

    public interface CartListener {
        void onCartUpdated();
    }

    private final CartListener listener;
    private final Context context;
    private final List<CartItemModel> list;

    public CartAdapter(Context context, List<CartItemModel> list, CartListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartVH holder, int position) {

        CartItemModel model = list.get(position);

        holder.tvName.setText(model.getName());
        holder.tvBrand.setText(model.getBrand());
        holder.tvPrice.setText("₹" + model.getPrice());
        holder.tvQty.setText("Qty: " + model.getQty());

        // ✅ Glide safe load
        String url = model.getImageUrl();
        Glide.with(context)
                .load(url == null ? "" : url)
                .placeholder(R.drawable.prod1)
                .error(R.drawable.prod1)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class CartVH extends RecyclerView.ViewHolder {

        ImageView img;
        TextView tvName, tvBrand, tvPrice, tvQty;

        public CartVH(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imgProduct);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvBrand = itemView.findViewById(R.id.tvBrand);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQty = itemView.findViewById(R.id.tvQty);
        }
    }
}
