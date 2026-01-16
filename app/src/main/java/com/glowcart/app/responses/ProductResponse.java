package com.glowcart.app.responses;

public class ProductResponse {

    private long id;
    private String name;
    private String brand;
    private int price;
    private int oldPrice;
    private String description;
    private String imageUrl;
    private double rating;

    public long getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public int getPrice() { return price; }
    public int getOldPrice() { return oldPrice; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public double getRating() { return rating; }
}