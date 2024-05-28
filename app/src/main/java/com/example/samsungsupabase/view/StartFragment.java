package com.example.samsungsupabase.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.samsungsupabase.R;
import com.example.samsungsupabase.Utils;
import com.example.samsungsupabase.databinding.FragmentStartBinding;
import com.example.samsungsupabase.model.retrofit.API;
import com.example.samsungsupabase.model.Account;
import com.example.samsungsupabase.model.retrofit.ResponseSignUser;
import com.example.samsungsupabase.model.retrofit.RetrofitClientAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartFragment extends Fragment {

    private FragmentStartBinding binding;
    private API api;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStartBinding.inflate(inflater, container, false);

        api = RetrofitClientAuth.getInstance().create(API.class);

        //Нажатие на кнопку авторизации
        binding.signInButton.setOnClickListener(view12 -> {
            String email = binding.emailField.getText().toString();
            String password = binding.passwordField.getText().toString();
            if (!email.equals("") && !password.equals("")) {
                enter(email, password, "signIn");
            }
        });

        //Нажатие на кнопку регистрации
        binding.signUpButton.setOnClickListener(view1 -> {
            String email = binding.emailField.getText().toString();
            String password = binding.passwordField.getText().toString();
            if (!email.equals("") && !password.equals("")) {
                enter(email, password, "signUp");
            }
        });

        //Нажатие на кнопку выхода из аккаунта
        binding.logoutButton.setOnClickListener(view1 -> {
            if (!Utils.USER_TOKEN.equals("")) {
                Call<Void> call = api.userLogout("Bearer " + Utils.USER_TOKEN, Utils.APIKEY, Utils.CONTENT_TYPE);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getContext(), R.string.successful_exit, Toast.LENGTH_LONG).show();
                            //очистим поля ввода и данные пользователя
                            Utils.USER_TOKEN = "";
                            Utils.USER_EMAIL = "";
                            Utils.USER_ID = "";
                            binding.emailField.setText("");
                            binding.passwordField.setText("");
                        }
                        else {
                            Toast.makeText(getContext(), call.toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }else {
                Toast.makeText(getContext(), R.string.you_are_not_logged_in_to_your_account, Toast.LENGTH_LONG).show();
            }
        });
        return binding.getRoot();
    }



    //метод обработки входа и регистрации
    private void enter(String email, String password, String type) {

        Account account = new Account(email, password);

        if (type.equals("signIn")) {
            Call<ResponseSignUser> call = api.signInByEmailAndPswrd("password", Utils.APIKEY, Utils.CONTENT_TYPE, account);
            call.enqueue(new Callback<ResponseSignUser>() {
                @Override
                public void onResponse(Call<ResponseSignUser> call, Response<ResponseSignUser> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getContext(), R.string.successful_authorization, Toast.LENGTH_SHORT).show();
                        //запомним данные пользователя
                        Utils.USER_ID = response.body().getUser().getId();
                        Utils.USER_TOKEN = response.body().getAccessToken();
                        Utils.USER_EMAIL = email;
                        Log.d("777", Utils.USER_TOKEN);
                        //программно перейдем на фрагмент с заказами пользователя
                        binding.signInButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_startFragment_to_ordersFragment));
                        binding.signInButton.performClick();



                    }else {
                        Toast.makeText(getContext(), R.string.authorization_error, Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseSignUser> call, Throwable throwable) {

                }
            });
        }

        if (type.equals("signUp")){
            Call<ResponseSignUser> call = api.signUpByEmailAndPswrd(Utils.APIKEY, Utils.CONTENT_TYPE, account);
            call.enqueue(new Callback<ResponseSignUser>() {
                @Override
                public void onResponse(Call<ResponseSignUser> call, Response<ResponseSignUser> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getContext(), R.string.the_user_has_been_created, Toast.LENGTH_SHORT).show();
                        Utils.USER_ID = response.body().getUser().getId();
                        Utils.USER_TOKEN = response.body().getAccessToken();
                        Utils.USER_EMAIL = email;
                    }else {
                        Toast.makeText(getContext(), R.string.error_in_creating_a_user, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseSignUser> call, Throwable throwable) {

                }
            });
        }



    }
}