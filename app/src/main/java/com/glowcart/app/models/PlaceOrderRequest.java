package com.glowcart.app.models;

import java.util.List;

public class PlaceOrderRequest {
    public Address address;
    public List<OrderItem> items;
    public int totalAmount;

    public static class Address {
        public String fullName;
        public String mobile;
        public String line1;
        public String line2;
        public String city;
        public String state;
        public String pincode;
    }

    public static class OrderItem {
        public Long productId;
        public String name;
        public String brand;
        public int price;
        public int qty;
        public String imageUrl;
    }
}