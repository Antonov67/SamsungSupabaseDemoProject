package com.example.samsungsupabase.model.retrofit;

import com.example.samsungsupabase.model.Account;
import com.example.samsungsupabase.model.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {
    //регистрация пользователя
    @POST("signup")
    Call<ResponseSignUser> signUpByEmailAndPswrd(@Header("apikey") String apikey, @Header("Content-Type") String contentType, @Body Account account);

    //авторизация пользователя
    @POST("token")
    Call<ResponseSignUser> signInByEmailAndPswrd(@Query ("grant_type") String grantType, @Header("apikey") String apikey, @Header("Content-Type") String contentType, @Body Account account);

    //выход пользователя из аккаунта
    @POST("logout")
    Call<Void> userLogout(@Header("Authorization") String token, @Header("apikey") String apikey, @Header("Content-Type") String contentType);

    //получение всех заказов
    @GET("orders")
    Call<List<Order>> getAllOrders(@Header("apikey") String apikey, @Header("Content-Type") String contentType, @Query("select") String select);

    //выбор заказов пользователя
    @GET("orders")
    Call<List<Order>> getOrdersByUser(@Header("apikey") String apikey, @Header("Content-Type") String contentType, @Query("user_id") String userId, @Query("select") String select);

    //добавление заказа пользователя
    @POST("orders")
    Call<Void> addOrderByUser(@Header("apikey") String apikey, @Header("Content-Type") String contentType, @Body Order order);

    //удаление заказа по id заказа
    @DELETE("orders")
    Call<Void> deleteOrder(@Header("apikey") String apikey, @Header("Content-Type") String contentType, @Query("id") String id);
}
