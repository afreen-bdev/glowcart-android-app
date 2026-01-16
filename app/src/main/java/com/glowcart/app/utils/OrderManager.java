package com.glowcart.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.glowcart.app.models.OrderModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {

    private static final String PREF = "orders_pref";
    private static final String KEY_ORDERS = "orders_list";

    public static void saveOrder(Context context, OrderModel order) {
        List<OrderModel> orders = getOrders(context);
        orders.add(0, order); // newest first

        SharedPreferences prefs = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_ORDERS, new Gson().toJson(orders)).apply();
    }

    public static List<OrderModel> getOrders(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_ORDERS, null);

        if (json == null) return new ArrayList<>();

        Type type = new TypeToken<List<OrderModel>>(){}.getType();
        return new Gson().fromJson(json, type);
    }

    public static void clearOrders(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_ORDERS).apply();
    }
}