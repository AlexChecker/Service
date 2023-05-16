package com.alexchecker.service.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexchecker.service.API.Models.OrderModel;
import com.alexchecker.service.API.Models.OrderStatusModel;
import com.alexchecker.service.R;
import com.alexchecker.service.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder>
{
    private List<OrderModel> dataset;

    public OrdersAdapter(List<OrderModel> data)
    {
        dataset = data;
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder
    {
        private final TextView order_type;
        private final TextView order_status;
        private final TextView order_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            order_type = itemView.findViewById(R.id.order_type_field);
            order_status = itemView.findViewById(R.id.order_status_field);
            order_price = itemView.findViewById(R.id.order_price_field);
        }

        public TextView getOrder_price() {
            return order_price;
        }

        public TextView getOrder_status() {
            return order_status;
        }

        public TextView getOrder_type() {
            return order_type;
        }
    }

    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
        return new ViewHolder(view);
    }

    private String findStatus(int statusId)
    {
        for (OrderStatusModel statusModel : Utils.OrderStatuses) {
            if(statusModel.getStatusId() == statusId) return statusModel.getStatusName();
        }
        return "Teapot";
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.ViewHolder holder, int position) {
        holder.getOrder_status().setText(findStatus(dataset.get(position).getStatus()));
        holder.getOrder_price().setText(dataset.get(position).getPrice().toString());
        holder.getOrder_type().setText(dataset.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
