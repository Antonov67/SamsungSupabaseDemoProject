package com.example.samsungsupabase.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.samsungsupabase.R;
import com.example.samsungsupabase.Utils;
import com.example.samsungsupabase.databinding.FragmentAddOrderBinding;
import com.example.samsungsupabase.model.Order;
import com.example.samsungsupabase.viewmodel.ViewModel;


public class AddOrderFragment extends Fragment {
    FragmentAddOrderBinding binding;
    ViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddOrderBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        //добавление заказа по нажатию на кнопку
        binding.submitFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String product = binding.productTextField.getText().toString();
                String d = binding.costTextField.getText().toString();
                if (!product.equals("") && !d.equals("")) {
                    double cost = Double.parseDouble(d);
                    Order order = new Order(Utils.USER_ID, product, cost);
                    viewModel.submitOrder(order).observe(getViewLifecycleOwner(), aBoolean -> {
                        if (aBoolean) {
                            Toast.makeText(getContext(), R.string.order_submit, Toast.LENGTH_SHORT).show();
                            binding.costTextField.setText("");
                            binding.productTextField.setText("");
                        }
                    });
                }

            }
        });
        return binding.getRoot();
    }
}