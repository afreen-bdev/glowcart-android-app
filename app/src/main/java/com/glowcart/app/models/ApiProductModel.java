package com.glowcart.app.models;

public class ApiProductModel {

    private Long id;
    private String name;
    private String brand;
    private int price;
    private int oldPrice;
    private String rating;
    private String description;
    private String imageUrl;

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public int getPrice() { return price; }
    public int getOldPrice() { return oldPrice; }
    public String getRating() { return rating; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    private String category;
    public String getCategory() { return category; }

}
