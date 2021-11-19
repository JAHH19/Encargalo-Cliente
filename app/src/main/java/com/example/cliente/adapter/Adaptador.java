package com.example.cliente.adapter;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cliente.Database.Database;
import com.example.cliente.Model.ItemListEsta;
import com.example.cliente.MostrarProductosTienda;
import com.example.cliente.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Adaptador extends RecyclerView.Adapter<Adaptador.RecyclerHolder> {
    private List<ItemListEsta> items;
    private List<ItemListEsta> originalItems;

    public Adaptador(List<ItemListEsta> items) {
        this.items = items;
        this.originalItems = new ArrayList<>();
        originalItems.addAll(items);
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_list_esta,parent,false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {

            ItemListEsta item = items.get(position);
            holder.imagEsta.setImageResource(item.getImag_tienda());
            holder.tvNomEsta.setText(item.getNom_tienda());
            holder.tvUbicacion.setText(item.getUbicacion());
            holder.tvCalific.setText(item.getCalific()+"");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), MostrarProductosTienda.class);
                    intent.putExtra("item",item);
                    holder.itemView.getContext().startActivity(intent);
                    new Database(holder.itemView.getContext()).cleanLista();
                }
            });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void filter(final String strSearch){
        if(strSearch.length()==0){
            items.clear();
            items.addAll(originalItems);
        }
        else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                items.clear();
                List<ItemListEsta> collect = originalItems.stream()
                        .filter(i -> i.getNom_tienda().toLowerCase().contains(strSearch))
                        .collect(Collectors.toList());
                items.addAll(collect);
            }
            else {
                items.clear();
                for (ItemListEsta i : originalItems){
                    if (i.getNom_tienda().toLowerCase().contains(strSearch)){
                        items.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        private ImageView imagEsta;
        private TextView tvNomEsta, tvUbicacion, tvCalific;

        public RecyclerHolder(@NonNull View itemView){
            super(itemView);
            imagEsta = itemView.findViewById(R.id.imgEstablecimiento);
            tvNomEsta = itemView.findViewById(R.id.tvprecio);
            tvUbicacion=itemView.findViewById(R.id.tvubica);
            tvCalific=itemView.findViewById(R.id.tvCalific);


        }


    }


}
