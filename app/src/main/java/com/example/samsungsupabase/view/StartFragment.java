package com.example.samsungsupabase.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.samsungsupabase.R;
import com.example.samsungsupabase.Utils;
import com.example.samsungsupabase.databinding.FragmentStartBinding;
import com.example.samsungsupabase.model.retrofit.API;
import com.example.samsungsupabase.model.Account;
import com.example.samsungsupabase.model.ResponseLogoutUser;
import com.example.samsungsupabase.model.ResponseSignUser;
import com.example.samsungsupabase.model.retrofit.RetrofitClientAuth;
import com.example.samsungsupabase.viewmodel.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartFragment extends Fragment {

    private FragmentStartBinding binding;
    private API api;
    private ViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStartBinding.inflate(inflater, container, false);

        api = RetrofitClientAuth.getInstance().create(API.class);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);



        //Нажатие на кнопку авторизации
        binding.signInButton.setOnClickListener(view12 -> {
            String email = binding.emailField.getText().toString();
            String password = binding.passwordField.getText().toString();

            if (!email.equals("") && !password.equals("")) {
                viewModel.signIn(email, password).observe(getViewLifecycleOwner(), new Observer<ResponseSignUser>() {
                    @Override
                    public void onChanged(ResponseSignUser responseSignUser) {
                        if (responseSignUser != null) {
                            //запомним данные пользователя
                            Utils.USER_ID = responseSignUser.getUser().getId();
                            Utils.USER_TOKEN = responseSignUser.getAccessToken();
                            Utils.USER_EMAIL = email;
                            Toast.makeText(getContext(), "Успешный вход", Toast.LENGTH_SHORT).show();
                            //программно перейдем на фрагмент с заказами пользователя
                            binding.signInButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_startFragment_to_ordersFragment));
                            binding.signInButton.performClick();
                        } else {
                            Toast.makeText(getContext(), "Ошибка входа", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //Нажатие на кнопку регистрации
        binding.signUpButton.setOnClickListener(view1 -> {
            String email = binding.emailField.getText().toString();
            String password = binding.passwordField.getText().toString();
            if (!email.equals("") && !password.equals("")) {
                viewModel.signUp(email, password).observe(getViewLifecycleOwner(), new Observer<ResponseSignUser>() {
                    @Override
                    public void onChanged(ResponseSignUser responseSignUser) {
                        if (responseSignUser != null) {
                            //запомним данные пользователя
                            Utils.USER_ID = responseSignUser.getUser().getId();
                            Utils.USER_TOKEN = responseSignUser.getAccessToken();
                            Utils.USER_EMAIL = email;
                            Toast.makeText(getContext(), "Успешная регистрация", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Ошибка регистрации", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //Нажатие на кнопку выхода из аккаунта
        binding.logoutButton.setOnClickListener(view1 -> {
            if (!Utils.USER_TOKEN.equals("")) {
                Call<ResponseLogoutUser> call = api.userLogout("Bearer " + Utils.USER_TOKEN, Utils.APIKEY, Utils.CONTENT_TYPE);
                call.enqueue(new Callback<ResponseLogoutUser>() {
                    @Override
                    public void onResponse(Call<ResponseLogoutUser> call, Response<ResponseLogoutUser> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Успешный выход", Toast.LENGTH_LONG).show();
                            //очистим поля ввода и данные пользователя
                            Utils.USER_TOKEN = "";
                            Utils.USER_EMAIL = "";
                            Utils.USER_ID = "";
                            binding.emailField.setText("");
                            binding.passwordField.setText("");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseLogoutUser> call, Throwable throwable) {
                        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), "Вы не вошли в аккаунт", Toast.LENGTH_LONG).show();
            }
        });
        return binding.getRoot();
    }
}