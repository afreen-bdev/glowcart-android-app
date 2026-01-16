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
import com.glowcart.app.models.ApiOrderModel;

import java.util.List;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.ItemVH> {

    private Context context;
    private List<ApiOrderModel.OrderItem> list;

    public OrderItemsAdapter(Context context, List<ApiOrderModel.OrderItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_order_product, parent, false);
        return new ItemVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemVH holder, int position) {

        ApiOrderModel.OrderItem item = list.get(position);

        holder.tvName.setText(item.getName());
        holder.tvBrand.setText(item.getBrand());
        holder.tvQty.setText("Qty: " + item.getQty());
        holder.tvPrice.setText("₹" + item.getPrice());

        Glide.with(context)
                .load(item.getImageUrl())
                .placeholder(R.drawable.prod1)
                .error(R.drawable.prod1)
                .into(holder.imgItem);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ItemVH extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView tvName, tvBrand, tvQty, tvPrice;

        public ItemVH(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            tvName = itemView.findViewById(R.id.tvName);
            tvBrand = itemView.findViewById(R.id.tvBrand);
            tvQty = itemView.findViewById(R.id.tvQty);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}