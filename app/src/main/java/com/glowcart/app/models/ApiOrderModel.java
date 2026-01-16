package com.glowcart.app.models;

import java.util.List;

public class ApiOrderModel {

    private Long id;
    private int totalAmount;
    private String status;
    private String createdAt;
    private Address address;
    private List<OrderItem> items;

    public Long getId() { return id; }
    public int getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public String getCreatedAt() { return createdAt; }
    public Address getAddress() { return address; }
    public List<OrderItem> getItems() { return items; }

    public static class Address {
        private String fullName;
        private String mobile;
        private String line1;
        private String line2;
        private String city;
        private String state;
        private String pincode;

        public String getFullName() { return fullName; }
        public String getMobile() { return mobile; }
        public String getLine1() { return line1; }
        public String getLine2() { return line2; }
        public String getCity() { return city; }
        public String getState() { return state; }
        public String getPincode() { return pincode; }
    }

    public static class OrderItem {
        private Long productId;
        private String name;
        private String brand;
        private int price;
        private int qty;
        private String imageUrl;

        public Long getProductId() { return productId; }
        public String getName() { return name; }
        public String getBrand() { return brand; }
        public int getPrice() { return price; }
        public int getQty() { return qty; }
        public String getImageUrl() { return imageUrl; }
    }
}
