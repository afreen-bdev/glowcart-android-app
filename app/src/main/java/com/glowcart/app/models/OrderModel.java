package com.glowcart.app.models;

import java.util.List;

public class OrderModel {
    private String orderId;
    private String orderDate;
    private int totalAmount;
    private List<OrderItemModel> items;

    public OrderModel(String orderId, String orderDate, int totalAmount, List<OrderItemModel> items) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public String getOrderId() { return orderId; }
    public String getOrderDate() { return orderDate; }
    public int getTotalAmount() { return totalAmount; }
    public List<OrderItemModel> getItems() { return items; }
}