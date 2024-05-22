package com.example.samsungsupabase.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.samsungsupabase.Utils;
import com.example.samsungsupabase.model.Order;
import com.example.samsungsupabase.model.retrofit.API;
import com.example.samsungsupabase.model.retrofit.RetrofitClientAuth;
import com.example.samsungsupabase.model.retrofit.RetrofitClientRest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewModel extends AndroidViewModel {

    MutableLiveData<List<Order>> orders = new MutableLiveData<>();
    MutableLiveData<Boolean> isOrderDelete = new MutableLiveData<>();
    API api;

    public ViewModel(@NonNull Application application) {
        super(application);
        api = RetrofitClientAuth.getInstance().create(API.class);
    }

    public LiveData<List<Order>> getOrdersByUser() {
        api = RetrofitClientRest.getInstance().create(API.class);
        Call<List<Order>> call = api.getOrdersByUser(Utils.APIKEY, Utils.CONTENT_TYPE, "eq." + Utils.USER_ID, "*");
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    orders.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable throwable) {

            }
        });
        return orders;
    }

    public LiveData<Boolean> deleteOrder(String id) {
        Call<Void> call = api.deleteOrder(Utils.APIKEY, Utils.CONTENT_TYPE, "eq." + id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    isOrderDelete.postValue(true);
                } else {
                    isOrderDelete.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                isOrderDelete.postValue(false);
            }
        });
        return isOrderDelete;
    }
}
