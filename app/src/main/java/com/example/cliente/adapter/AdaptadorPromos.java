package com.example.cliente.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cliente.AnuncioDetallesFragment;
import com.example.cliente.MiPedidoActivity;
import com.example.cliente.MostrarProductosTienda;
import com.example.cliente.PublicidadFragment;
import com.example.cliente.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AdaptadorPromos extends RecyclerView.Adapter<AdaptadorPromos.HolderPromo> {

    private Context context;
    private ArrayList<ModelPromos> promosArrayList;
    public static String idt;


    public AdaptadorPromos(Context context, ArrayList<ModelPromos> promosArrayList) {
        this.context = context;
        this.promosArrayList = promosArrayList;
    }

    @NonNull

    @Override
    public AdaptadorPromos.HolderPromo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_promo,parent,false);

        return new AdaptadorPromos.HolderPromo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPromos.HolderPromo holder, int position) {

        ModelPromos model = promosArrayList.get(position);

        String titulo = model.getTitulo();

        String id = model.getIdtienda();
        String fechainicio = model.getFechaini();
        String fechafinal = model.getFechafin();

        String imagen=model.getImagen();

        try{

            Picasso.get().load(imagen).placeholder(R.drawable.ic_image_black).into(holder.image);
        }catch(Exception e){
            holder.image.setImageResource(R.drawable.ic_image_black);
        }

        String gmtDate = fechainicio;
        String gmtDate2= fechafinal;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String formattedDate="";
        String formattedDate2="";
        Date today = Calendar.getInstance().getTime();
        String todays = dateFormat2.format(today);
        try{
            Date date = dateFormat.parse(gmtDate);
            formattedDate=dateFormat2.format(date);
            Date date2 = dateFormat.parse(gmtDate2);
            formattedDate2=dateFormat2.format(date2);





        }catch (Exception e){
            formattedDate = fechainicio;
            formattedDate2 = fechafinal;
            e.printStackTrace();

        }


        holder.title.setText(titulo);
        holder.idtienda.setText(id);
        holder.fecha.setText("Desde " + formattedDate + " hasta "+ formattedDate2);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MostrarProductosTienda.class);
                context.startActivity(i);
                idt=id;


            }
        });
    }

    @Override
    public int getItemCount() {
        return promosArrayList.size();
    }


    class HolderPromo extends RecyclerView.ViewHolder{


        TextView title,fecha,idtienda;
        ImageView image;

        public HolderPromo(@NonNull  View itemView) {
            super(itemView);


            title = itemView.findViewById(R.id.titlepromo);
            idtienda=itemView.findViewById(R.id.idtiendatxt);
            image = itemView.findViewById(R.id.imagepromo);
            fecha = itemView.findViewById(R.id.datepromo);

        }
    }
}
