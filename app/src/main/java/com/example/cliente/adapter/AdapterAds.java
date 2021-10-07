package com.example.cliente.adapter;

import android.content.Context;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cliente.AnuncioDetallesFragment;
import com.example.cliente.PublicidadFragment;
import com.example.cliente.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AdapterAds extends RecyclerView.Adapter<AdapterAds.HolderAds>{

    private Context context;
    private ArrayList<ModelAds> adsArrayList;


    public AdapterAds(Context context, ArrayList<ModelAds> adsArrayList) {
        this.context = context;
        this.adsArrayList = adsArrayList;
    }

    @NonNull

    @Override
    public HolderAds onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_anuncio,parent,false);

        return new HolderAds(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterAds.HolderAds holder, int position) {

        ModelAds model = adsArrayList.get(position);

        String titulo = model.getTitulo();
        String descripcion = model.getDescripcion();
        String categoria = model.getCategoria();
        String id = model.getId();
        String fechainicio = model.getFechainicio();
        String fechafinal = model.getFechafinal();
        String montopagado = model.getMontopagado();
        String estado="";
        String imagen=model.getImagen();

        org.jsoup.nodes.Document document = (Document) Jsoup.parse(descripcion);
        try{

            Picasso.get().load(imagen).placeholder(R.drawable.ic_image_black).into(holder.imageads);
        }catch(Exception e){
            holder.imageads.setImageResource(R.drawable.ic_image_black);
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


            if(date.before(dateFormat.parse(todays)) && date2.after(dateFormat.parse(todays))){
                estado="Estado: Activo";
                holder.estado.setTextColor(Color.parseColor("#FF109A83"));

            }else if(date.after(dateFormat.parse(todays)) && date2.after(dateFormat.parse(todays)) ){
                estado="Estado: Proximo a Salir";
                holder.estado.setTextColor(Color.parseColor("#806600"));
            }

            else if(date.before(dateFormat.parse(todays)) && date2.before(dateFormat.parse(todays))){

                estado="Estado: Inactivo";
                holder.estado.setTextColor(Color.RED);
            }



        }catch (Exception e){
            formattedDate = fechainicio;
            formattedDate2 = fechafinal;
            e.printStackTrace();

        }


        holder.title.setText(titulo);
        holder.description.setText(document.text());
        holder.fecha.setText("Desde " + formattedDate + " hasta "+ formattedDate2);
        holder.estado.setText(estado);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("adsId", id);


                FragmentManager fm =   ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if(holder.estado.getText().toString()=="Estado: Activo" || holder.estado.getText().toString()== "Estado: Proximo a Salir"){
                    AnuncioDetallesFragment llf = new AnuncioDetallesFragment();
                    llf.setArguments(bundle);
                    ft.add(R.id.nav_host_fragment, llf);
                    ft.commit();
                }else{
                    PublicidadFragment llf = new PublicidadFragment();

                    llf.setArguments(bundle);
                    ft.add(R.id.nav_host_fragment, llf);
                    ft.commit();
                }





            }
        });
    }

    @Override
    public int getItemCount() {
        return adsArrayList.size();
    }


    class HolderAds extends RecyclerView.ViewHolder{

        ImageButton moreBtn;
        TextView title,description,fecha,estado;
        ImageView imageads;

        public HolderAds(@NonNull  View itemView) {
            super(itemView);

            moreBtn = itemView.findViewById(R.id.moreBtn);
            title = itemView.findViewById(R.id.titleads);
            description = itemView.findViewById(R.id.description);
            imageads = itemView.findViewById(R.id.imageads);
            fecha = itemView.findViewById(R.id.dateads);
            estado = itemView.findViewById(R.id.estadoads);
        }
    }


}
