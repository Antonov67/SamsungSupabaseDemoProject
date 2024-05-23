package com.example.samsungsupabase.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("product")
    @Expose
    public String product;
    @SerializedName("cost")
    @Expose
    public double cost;

    public Order(String userId, String product, Double cost) {
        this.userId = userId;
        this.product = product;
        this.cost = cost;
    }
}
