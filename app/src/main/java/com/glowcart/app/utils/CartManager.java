package com.glowcart.app.utils;

import com.glowcart.app.models.CartItemModel;

import java.util.ArrayList;
import java.util.List;

public class CartManager {

    private static final List<CartItemModel> cartList = new ArrayList<>();

    public static List<CartItemModel> getCartList() {
        return cartList;
    }

    public static void addToCart(CartItemModel newItem) {

        // if product already in cart, increase qty
        for (CartItemModel item : cartList) {
            if (item.getProductId() != null && item.getProductId().equals(newItem.getProductId())) {
                item.setQty(item.getQty() + newItem.getQty());
                return;
            }
        }

        cartList.add(newItem);
    }

    public static void removeFromCart(int position) {
        if (position >= 0 && position < cartList.size()) {
            cartList.remove(position);
        }
    }

    public static void clearCart() {
        cartList.clear();
    }

    public static int getTotalCartItems() {
        int total = 0;
        for (CartItemModel item : cartList) {
            total += item.getQty();
        }
        return total;
    }
}