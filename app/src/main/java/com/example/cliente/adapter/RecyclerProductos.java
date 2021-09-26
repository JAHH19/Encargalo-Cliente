package com.example.cliente.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cliente.DetalleProductoActivity;
import com.example.cliente.Model.ItemListProductos;
import com.example.cliente.MostrarProductosTienda;
import com.example.cliente.R;

import java.util.List;

public class RecyclerProductos extends RecyclerView.Adapter<RecyclerProductos.RecyclerHolder> {
    private List<ItemListProductos> items;


    public RecyclerProductos(List<ItemListProductos> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_productos,parent,false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        ItemListProductos item = items.get(position);
        holder.imgproducto.setImageResource(item.getImgProducto());
        holder.tvDescripcionP.setText(item.getDescripcion());
        holder.tvPrecioP.setText(item.getPrecio());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetalleProductoActivity.class);
                intent.putExtra("itemProducto",item);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        private ImageView imgproducto;
        private TextView tvDescripcionP;
        private TextView tvPrecioP;

        public RecyclerHolder(@NonNull View itemView){
            super(itemView);
            imgproducto = itemView.findViewById(R.id.imgProducto);
            tvDescripcionP = itemView.findViewById(R.id.txtDescripcionProducto);
            tvPrecioP = itemView.findViewById(R.id.txtPrecio);
        }
    }

}
