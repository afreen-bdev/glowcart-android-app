package com.glowcart.app.models;

public class CartItemModel {

    private Long productId;      // ✅ required for orders
    private String name;
    private String brand;
    private int price;
    private int qty;
    private String imageUrl;

    public CartItemModel(Long productId, String name, String brand, int price, int qty, String imageUrl) {
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.qty = qty;
        this.imageUrl = imageUrl;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public int getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}