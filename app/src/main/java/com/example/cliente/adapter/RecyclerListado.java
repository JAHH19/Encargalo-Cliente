package com.example.cliente.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.cliente.Database.Database;
import com.example.cliente.Model.ItemListOrder;
import com.example.cliente.R;

import java.util.ArrayList;
import java.util.List;

class ListadoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txvnomProd,txvprecio,txvSubtotal,txvCantidad;
    public ImageView imgProducto, btnmas, btnmenos;
    int cantidadp;



    public ListadoViewHolder(@NonNull View itemView) {
        super(itemView);
        txvnomProd = itemView.findViewById(R.id.txvDescripcion);
        txvprecio = itemView.findViewById(R.id.textView7);
        txvSubtotal = itemView.findViewById(R.id.textView6);
        txvCantidad = itemView.findViewById(R.id.textView5);
        imgProducto = itemView.findViewById(R.id.imageView3);
        btnmas = itemView.findViewById(R.id.btnmas);
        btnmenos = itemView.findViewById(R.id.btnmenos);
    }

    @Override
    public void onClick(View v) {

    }
}

public class RecyclerListado extends RecyclerView.Adapter<ListadoViewHolder>{

    private List<ItemListOrder> listOrders;
    private Activity activity;

    public RecyclerListado(List<ItemListOrder> listOrders, Activity activity) {
        this.listOrders = listOrders;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ListadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemv = inflater.inflate(R.layout.list_listado,parent,false);
        return new ListadoViewHolder(itemv);
    }

    @Override
    public void onBindViewHolder(@NonNull ListadoViewHolder holder, int position) {
        ItemListOrder items = listOrders.get(position);
        holder.imgProducto.setImageResource(R.drawable.imgproductosdef);
        holder.txvCantidad.setText(items.getCant());
        holder.txvprecio.setText("$. "+items.getPrecio()+" MX");
        holder.cantidadp = Integer.parseInt(items.getCant());
        holder.txvnomProd.setText(items.getProDescripcion());
        holder.txvSubtotal.setText("$. "+String.valueOf(Math.round((Double.parseDouble(items.getCant())*Double.parseDouble(items.getPrecio()))*100.0)/100.0)+" MX");
        holder.btnmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cantidadp = holder.cantidadp+1;
                new Database(holder.itemView.getContext()).actualizarPedido(items.getId(),String.valueOf(holder.cantidadp));
            }
        });
        holder.btnmenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cantidadp = holder.cantidadp-1;
                if(holder.cantidadp==0){
                    new Database(holder.itemView.getContext()).deletePedido(items.getId());
                }else {
                    new Database(holder.itemView.getContext()).actualizarPedido(items.getId(),String.valueOf(holder.cantidadp));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listOrders.size();
    }
}
