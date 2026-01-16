package com.glowcart.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.glowcart.app.R;
import com.glowcart.app.activities.OrderDetailsActivity;
import com.glowcart.app.models.ApiOrderModel;
import com.google.gson.Gson;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderVH> {

    private Context context;
    private List<ApiOrderModel> list;

    public OrdersAdapter(Context context, List<ApiOrderModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<ApiOrderModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderVH holder, int position) {

        ApiOrderModel order = list.get(position);

        holder.tvOrderId.setText("Order ID: GC-" + order.getId());
        holder.tvOrderDate.setText(order.getCreatedAt() == null ? "Date: --" : "Date: " + order.getCreatedAt());
        holder.tvOrderAmount.setText("₹" + order.getTotalAmount());

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, OrderDetailsActivity.class);

            // ✅ pass complete order json
            i.putExtra("order", new Gson().toJson(order));

            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class OrderVH extends RecyclerView.ViewHolder {

        TextView tvOrderId, tvOrderDate, tvOrderAmount;

        public OrderVH(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvOrderAmount = itemView.findViewById(R.id.tvOrderAmount);
        }
    }
}
