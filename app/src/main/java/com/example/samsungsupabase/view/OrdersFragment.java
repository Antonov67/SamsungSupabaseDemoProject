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
import com.example.samsungsupabase.databinding.FragmentOrdersBinding;
import com.example.samsungsupabase.model.retrofit.API;
import com.example.samsungsupabase.model.Order;
import com.example.samsungsupabase.viewmodel.ViewModel;

import java.util.List;


public class OrdersFragment extends Fragment {
    private FragmentOrdersBinding binding;
    private API api;
    OrderAdapter orderAdapter;
    ViewModel viewModel;
    OrderAdapter.LongClickItemListener longClickItemListener;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        binding.userId.setText(Utils.USER_ID);
        binding.userEmail.setText(Utils.USER_EMAIL);

        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        //получим данные о заказах пользователя с сервера

        viewModel.getOrdersByUser().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                if (orders != null){
                    //обработчик длинного нажатия в адаптере, но обработка удаления записи из базы данных на стороне фрагмента
                    longClickItemListener = new OrderAdapter.LongClickItemListener() {
                        @Override
                        public void deleteOrder(String id, int position) {
                            viewModel.deleteOrder(id).observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                                @Override
                                public void onChanged(Boolean aBoolean) {
                                    if (aBoolean){
                                        Toast.makeText(getContext(), R.string.the_record_has_been_deleted, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    };
                    //создадим адаптер и передадим в него данные
                    orderAdapter = new OrderAdapter(orders, getContext(), longClickItemListener);
                    binding.list.setAdapter(orderAdapter);
                }else {
                    Toast.makeText(getContext(), R.string.no_data, Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.floatingActionButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_ordersFragment_to_addOrderFragment));

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (orderAdapter != null){
            orderAdapter.notifyDataSetChanged();
        }
    }
}