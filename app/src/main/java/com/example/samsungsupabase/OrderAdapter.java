package com.example.samsungsupabase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungsupabase.databinding.ListItemBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<Order> orders = new ArrayList<>();
    private Context context;
    ListItemBinding binding;

    public OrderAdapter(List<Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = ListItemBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        binding.product.setText(orders.get(position).product);
        binding.data.setText(orders.get(position).createdAt);
        binding.cost.setText(String.valueOf(orders.get(position).cost));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //удалим запись по длинному нажатию
                deleteOrder(orders.get(position).id, position);
                return true;
            }
        });
    }

    private void deleteOrder(String id, int position) {
        API api = RetrofitClientRest.getInstance().create(API.class);
        Call<Void> call = api.deleteOrder(Utils.APIKEY, Utils.CONTENT_TYPE, "eq." + id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, "Запись удалена", Toast.LENGTH_SHORT).show();
                    orders.remove(position);
                    notifyItemRemoved(position);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(ListItemBinding itemBinding) {
            super(itemBinding.getRoot());
        }
    }
}
