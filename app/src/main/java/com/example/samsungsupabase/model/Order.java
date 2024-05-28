package com.example.samsungsupabase.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("cost")
    @Expose
    private double cost;

    public Order(String userId, String product, Double cost) {
        this.userId = userId;
        this.product = product;
        this.cost = cost;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getProduct() {
        return product;
    }

    public double getCost() {
        return cost;
    }

    public String getId() {
        return id;
    }
}
