package com.example.samsungsupabase;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samsungsupabase.databinding.FragmentOrdersBinding;
import com.example.samsungsupabase.databinding.FragmentStartBinding;

public class OrdersFragment extends Fragment {
    private FragmentOrdersBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrdersBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}