package com.glowcart.app.models;

public class ProductModel {

    private String name;
    private String brand;
    private String price;
    private String oldPrice;
    private String rating;
    private String description;
    private int image;

    public ProductModel(String name, String brand, String price, String oldPrice,
                        String rating, String description, int image) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.oldPrice = oldPrice;
        this.rating = rating;
        this.description = description;
        this.image = image;
    }

    public String getName() { return name; }
    public String getBrand() { return brand; }
    public String getPrice() { return price; }
    public String getOldPrice() { return oldPrice; }
    public String getRating() { return rating; }
    public String getDescription() { return description; }
    public int getImage() { return image; }
}