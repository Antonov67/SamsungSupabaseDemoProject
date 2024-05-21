package com.example.samsungsupabase;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.samsungsupabase.databinding.FragmentStartBinding;

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

        api = RetrofitClient.getInstance().create(API.class);

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
                Call<ResponseLogoutUser> call = api.userLogout("Bearer " + Utils.USER_TOKEN, Utils.APIKEY, Utils.CONTENT_TYPE);
                call.enqueue(new Callback<ResponseLogoutUser>() {
                    @Override
                    public void onResponse(Call<ResponseLogoutUser> call, Response<ResponseLogoutUser> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getContext(), "Успешный выход", Toast.LENGTH_LONG).show();
                            //очистим поля ввода и токен пользователя
                            Utils.USER_TOKEN = "";
                            binding.emailField.setText("");
                            binding.passwordField.setText("");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseLogoutUser> call, Throwable throwable) {
                        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }else {
                Toast.makeText(getContext(), "Вы не вошли в аккаунт", Toast.LENGTH_LONG).show();
            }
        });
        return binding.getRoot();
    }




    private void enter(String email, String password, String type) {

        Account account = new Account(email, password);

        if (type.equals("signIn")) {
            Call<ResponseSigninUser> call = api.signInByEmailAndPswrd("password", Utils.APIKEY, Utils.CONTENT_TYPE, account);
            call.enqueue(new Callback<ResponseSigninUser>() {
                @Override
                public void onResponse(Call<ResponseSigninUser> call, Response<ResponseSigninUser> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getContext(), "Успешная авторизация", Toast.LENGTH_SHORT).show();
                        //запомним данные пользователя
                        Utils.USER_ID = response.body().getUser().getId();
                        Utils.USER_TOKEN = response.body().getAccessToken();
                        //перейдем на фрагмент с заказами пользователя
                        binding.signInButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_startFragment_to_ordersFragment));
                        binding.signInButton.performClick();



                    }else {
                        Toast.makeText(getContext(), "Ошибка авторизации", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseSigninUser> call, Throwable throwable) {

                }
            });
        }

        if (type.equals("signUp")){
            Call<ResponseSignupUser> call = api.signUpByEmailAndPswrd(Utils.APIKEY, Utils.CONTENT_TYPE, account);
            call.enqueue(new Callback<ResponseSignupUser>() {
                @Override
                public void onResponse(Call<ResponseSignupUser> call, Response<ResponseSignupUser> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getContext(), "Пользователь создан", Toast.LENGTH_SHORT).show();
                        Utils.USER_ID = response.body().
                                getUser().getId();
                        Utils.USER_TOKEN = response.body().getAccessToken();
                    }else {
                        Toast.makeText(getContext(), "Ошибка при создании пользователя", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseSignupUser> call, Throwable throwable) {

                }
            });
        }



    }
}