package com.example.samsungsupabase;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.samsungsupabase.databinding.FragmentOrdersBinding;
import com.example.samsungsupabase.databinding.FragmentStartBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersFragment extends Fragment {
    private FragmentOrdersBinding binding;
    private API api;
    OrderAdapter orderAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        binding.userId.setText(Utils.USER_ID);
        binding.userEmail.setText(Utils.USER_EMAIL);

        //получим данные о заказах пользователя с сервера
        api = RetrofitClient.getInstance().create(API.class);
        Call<List<Order>> call = api.getOrdersByUser(Utils.APIKEY, Utils.CONTENT_TYPE, "eq." + Utils.USER_ID, "*");
        Toast.makeText(getContext(), "eq." + Utils.USER_ID, Toast.LENGTH_SHORT).show();
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()){
                    //создадим адаптер и передадим в него данные
                    Toast.makeText(getContext(), "Super", Toast.LENGTH_SHORT).show();
                    orderAdapter = new OrderAdapter(response.body(), getContext());
                    binding.list.setAdapter(orderAdapter);
                }else {
                    Toast.makeText(getContext(), call.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable throwable) {

            }
        });





        return binding.getRoot();
    }
}