package com.example.samsungsupabase;

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
    Call<ResponseSignupUser> signUpByEmailAndPswrd(@Header("apikey") String apikey, @Header("Content-Type") String contentType, @Body Account account);

    //авторизация пользователя
    @POST("token")
    Call<ResponseSigninUser> signInByEmailAndPswrd(@Query ("grant_type") String grantType, @Header("apikey") String apikey, @Header("Content-Type") String contentType, @Body com.example.samsungsupabase.Account account);

    //выход пользователя из аккаунта
    @POST("logout")
    Call<ResponseLogoutUser> userLogout(@Header("Authorization") String token, @Header("apikey") String apikey, @Header("Content-Type") String contentType);

    //выбор заказов пользователя
    @GET("orders")
    Call<List<Order>> getOrdersByUser(@Header("apikey") String apikey, @Header("Content-Type") String contentType, @Query("user_id") String usetId, @Query("select") String select);

    //добавление заказа пользователя
    @POST("orders")
    Call<Order> addOrderByUser(@Header("apikey") String apikey, @Header("Content-Type") String contentType, @Body Order order);

    //удаление заказа по id заказа
    @DELETE("orders")
    Call<Void> deleteOrder(@Header("apikey") String apikey, @Header("Content-Type") String contentType, @Query("id") String id);

}
