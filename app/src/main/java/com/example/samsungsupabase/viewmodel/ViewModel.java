package com.example.samsungsupabase.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.example.samsungsupabase.R;
import com.example.samsungsupabase.Utils;
import com.example.samsungsupabase.model.Account;
import com.example.samsungsupabase.model.ResponseSignUser;
import com.example.samsungsupabase.model.retrofit.API;
import com.example.samsungsupabase.model.retrofit.RetrofitClientAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewModel extends AndroidViewModel {

    private API api;
    private MutableLiveData<ResponseSignUser> responseSignInUser = new MutableLiveData<>();
    private MutableLiveData<ResponseSignUser> responseSignUpUser = new MutableLiveData<>();

    public ViewModel(@NonNull Application application) {
        super(application);
    }

    //авторизация пользователя
    public LiveData<ResponseSignUser> signIn(String email, String password) {
        Account account = new Account(email, password);
        api = RetrofitClientAuth.getInstance().create(API.class);
        Call<ResponseSignUser> call = api.signInByEmailAndPswrd("password", Utils.APIKEY, Utils.CONTENT_TYPE, account);
        call.enqueue(new Callback<ResponseSignUser>() {
            @Override
            public void onResponse(Call<ResponseSignUser> call, Response<ResponseSignUser> response) {

                if (response.body() != null) {
                    Log.d("777", response.body().getUser().getId());
                    responseSignInUser.postValue(response.body());
                } else {
                    responseSignInUser.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseSignUser> call, Throwable throwable) {
                responseSignInUser.postValue(null);
            }
        });
        return responseSignInUser;
    }


    //регистрация пользователя
    public LiveData<ResponseSignUser> signUp(String email, String password) {
        Account account = new Account(email, password);
        api = RetrofitClientAuth.getInstance().create(API.class);
        Call<ResponseSignUser> call = api.signUpByEmailAndPswrd(Utils.APIKEY, Utils.CONTENT_TYPE, account);
        call.enqueue(new Callback<ResponseSignUser>() {
            @Override
            public void onResponse(Call<ResponseSignUser> call, Response<ResponseSignUser> response) {

                if (response.body() != null) {
                    Log.d("777", response.body().getUser().getId());
                    responseSignUpUser.postValue(response.body());
                } else {
                    responseSignUpUser.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseSignUser> call, Throwable throwable) {
                responseSignUpUser.postValue(null);
            }
        });
        return responseSignUpUser;
    }

}
