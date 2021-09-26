package com.example.cliente.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cliente.Model.ItemListDetallePedido;
import com.example.cliente.R;

import java.util.List;

public class RecyclerDetallePedido extends RecyclerView.Adapter<RecyclerDetallePedido.RecyclerHolder> {
    private List<ItemListDetallePedido> items;

    public RecyclerDetallePedido(List<ItemListDetallePedido> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_detallepedido,parent,false);

        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        ItemListDetallePedido item = items.get(position);
        holder.cant.setText("x"+item.getCantidad());
        holder.descrip.setText(item.getDescripcion());
        holder.precio.setText("$. "+item.getPrecio()+" MX");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        private TextView cant,descrip,precio;

        public RecyclerHolder(@NonNull View itemView){
            super(itemView);
            cant = itemView.findViewById(R.id.tvCantidad);
            descrip = itemView.findViewById(R.id.tvDescrip);
            precio = itemView.findViewById(R.id.tvprecio);
        }

    }
}
