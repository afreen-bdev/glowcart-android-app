package com.glowcart.app.models;

public class ApiOrderItemModel {

    private Long id;
    private Long productId;
    private String name;
    private String brand;
    private int price;
    private int qty;
    private String imageUrl;

    public Long getId() { return id; }

    public Long getProductId() { return productId; }

    public String getName() { return name; }

    public String getBrand() { return brand; }

    public int getPrice() { return price; }

    public int getQty() { return qty; }

    public String getImageUrl() { return imageUrl; }
}
