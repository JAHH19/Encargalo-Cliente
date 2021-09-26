package com.example.cliente.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cliente.DetallePedidoActivity;
import com.example.cliente.Model.ItemListHistorialPedidos;
import com.example.cliente.R;

import java.util.List;

public class RecyclerHistorialPedidos extends RecyclerView.Adapter<RecyclerHistorialPedidos.RecyclerHolder> {
    private List<ItemListHistorialPedidos> items;

    public RecyclerHistorialPedidos(List<ItemListHistorialPedidos> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerHistorialPedidos.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_hispedidos,parent,false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHistorialPedidos.RecyclerHolder holder, int position) {
        ItemListHistorialPedidos item = items.get(position);
        holder.tvfecha.setText(item.getFecha());
        holder.tvtienda.setText(item.getNomTienda());
        holder.tvnpedido.setText(item.getNpedido());
        holder.tvtotal.setText("$. "+item.getTotal()+" MX");
        holder.tvestado.setText(item.getEstado());
        holder.imgTienda.setImageResource(item.getImgTienda());
        if (item.getEstado().equals("SOLICITADO")){
            holder.tvestado.setBackgroundColor(Color.parseColor("#FFFF00"));
        }else{
            if (item.getEstado().equals("ACEPTADO")){
                holder.tvestado.setBackgroundColor(Color.parseColor("#CC9900"));
            }else {
                if (item.getEstado().equals("EN CAMINO")){
                    holder.tvestado.setBackgroundColor(Color.parseColor("#99FFFF"));
                }else {
                    if (item.getEstado().equals("ENTREGADO")){
                        holder.tvestado.setBackgroundColor(Color.parseColor("#66FF66"));
                    }else {
                        if (item.getEstado().equals("RECHAZADO")){
                            holder.tvestado.setBackgroundColor(Color.parseColor("#CC3333"));
                        }
                    }
                }
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetallePedidoActivity.class);
                intent.putExtra("itemDetail",item);
                intent.putExtra("idpedido",item.getIdpedido());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        private ImageView imgTienda;
        private TextView tvfecha,tvtienda,tvnpedido,tvtotal,tvestado;

        public RecyclerHolder (@NonNull View itemView){
            super(itemView);
            imgTienda = itemView.findViewById(R.id.imgTienda);
            tvfecha = itemView.findViewById(R.id.tvfecha);
            tvtienda = itemView.findViewById(R.id.tvNomTienda);
            tvnpedido = itemView.findViewById(R.id.tvNpedido);
            tvtotal = itemView.findViewById(R.id.txtTotalPedido);
            tvestado = itemView.findViewById(R.id.txtEstadoPedido);

        }

    }
}
