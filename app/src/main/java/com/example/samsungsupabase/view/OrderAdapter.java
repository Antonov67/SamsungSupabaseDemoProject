package com.example.samsungsupabase.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.samsungsupabase.databinding.ListItemBinding;
import com.example.samsungsupabase.model.Order;

import java.util.ArrayList;
import java.util.List;



public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<Order> orders = new ArrayList<>();
    private Context context;
    private ListItemBinding binding;
    private LongClickItemListener longClickItemListener;

    public OrderAdapter(List<Order> orders, Context context, LongClickItemListener longClickItemListener) {
        this.orders = orders;
        this.context = context;
        this.longClickItemListener = longClickItemListener;
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
                longClickItemListener.deleteOrder(orders.get(position).id, position);
                notifyDataSetChanged();
                orders.remove(position);
                return true;
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

    public interface LongClickItemListener{
        void deleteOrder(String id, int position);
    }
}