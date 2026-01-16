package com.glowcart.app.models;

public class OrderItemModel {
    private String name;
    private String brand;
    private int price;
    private int qty;

    public OrderItemModel(String name, String brand, int price, int qty) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.qty = qty;
    }

    public String getName() { return name; }
    public String getBrand() { return brand; }
    public int getPrice() { return price; }
    public int getQty() { return qty; }
}