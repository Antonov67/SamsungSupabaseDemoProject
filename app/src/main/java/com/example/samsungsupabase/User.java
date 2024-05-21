package com.example.samsungsupabase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }
}
