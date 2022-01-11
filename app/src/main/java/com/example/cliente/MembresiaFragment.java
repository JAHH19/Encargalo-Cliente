package com.example.cliente;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cliente.adapter.FragmentosFun;
import com.example.cliente.adapter.IPConfig;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class MembresiaFragment extends Fragment {

   Button btnbasico,btnmedio,btnpremium,btnpagar,btnregistrar;
   Double precio;
   Integer nivel;
   String nivelsel;
   TextView txt1,txt2,txt3;
    Calendar calendar = Calendar.getInstance();
    String today,idusuario;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentosFun fragmentosFun = new FragmentosFun();
        PagoPublicidadFragment pagoPublicidadFragment= new PagoPublicidadFragment();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

         today= year+"-"+(month+1)+"-"+day;
        idusuario= getActivity().getIntent().getExtras().getString("idusuario");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_membresia, container, false);
        precio=0.0;
        btnbasico = (Button) view.findViewById(R.id.btnbasico);
        btnmedio = (Button) view.findViewById(R.id.btnmedio);
        btnpremium = (Button) view.findViewById(R.id.btnpremium);
        btnregistrar=(Button)view.findViewById(R.id.btnmreg);
        btnpagar=(Button)view.findViewById(R.id.btnmpago);
        txt1=(TextView)view.findViewById(R.id.txtsuscrito);
        txt2=(TextView)view.findViewById(R.id.txtsuscrito2);
        txt3=(TextView)view.findViewById(R.id.txtsuscrito3);

        btnbasico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                precio=25.0;
                nivel=1;

                btnbasico.setBackgroundResource(R.color.btnselected);
                btnmedio.setBackgroundResource(R.color.btnblue);
                btnpremium.setBackgroundResource(R.color.btngreen);
                if(nivelsel.equals("1")){
                    btnpagar.setEnabled(false);
                    btnregistrar.setEnabled(false);
                }else{
                    btnpagar.setEnabled(true);
                    btnregistrar.setEnabled(true);
                }
            }
        });

        btnmedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                precio=25.0;
                nivel=2;
                btnbasico.setBackgroundResource(R.color.btnred);
                btnmedio.setBackgroundResource(R.color.btnselected);
                btnpremium.setBackgroundResource(R.color.btngreen);
                if(nivelsel.equals("2")){
                    btnpagar.setEnabled(false);
                    btnregistrar.setEnabled(false);
                }else{
                    btnpagar.setEnabled(true);
                    btnregistrar.setEnabled(true);
                }
            }
        });

        btnpremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                precio=25.0;
                nivel=3;
                btnbasico.setBackgroundResource(R.color.btnred);
                btnmedio.setBackgroundResource(R.color.btnblue);
                btnpremium.setBackgroundResource(R.color.btnselected);
                if(nivelsel.equals("3")){
                    btnpagar.setEnabled(false);
                    btnregistrar.setEnabled(false);
                }else{
                    btnpagar.setEnabled(true);
                    btnregistrar.setEnabled(true);
                }
            }
        });

        btnpagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pagoPublicidadFragment.isAdded()){
                    fragmentosFun.showFragment(pagoPublicidadFragment,getContext());
                }else {
                    fragmentosFun.createFragment(pagoPublicidadFragment,getContext());
                }

            }
        });

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarMembresia( IPConfig.ipServidor +"patrocinador/registrarMembresia.php");
            }
        });

        ConsultarMembresia( IPConfig.ipServidor +"patrocinador/consultarMembresia.php?usuario="+idusuario);


        return view;
    }

    private void RegistrarMembresia(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("Ya tienes este plan")){
                    new AlertDialog.Builder(getContext())
                            .setTitle("Espera")
                            .setPositiveButton("ok", null)
                            .setMessage( "Ya tienes este plan contratado. \n")
                            .show();
                }else if(response.equals("Se actualizo el plan")){
                    new AlertDialog.Builder(getContext())
                            .setTitle("Registro exitoso")
                            .setPositiveButton("ok", null)
                            .setMessage( "Gracias! Acabas de actualizar tu membresia. \n")
                            .show();
                }
                else{
                    new AlertDialog.Builder(getContext())
                            .setTitle("Registro exitoso")
                            .setPositiveButton("ok", null)
                            .setMessage( "Gracias! Acabas de unirte a nuestra membresia. \n" )
                            .show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){



            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> parametros = new HashMap<String, String>();




                parametros.put("nivel",nivel+"");
                parametros.put("usuario",idusuario);
                parametros.put("fecha",today);

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void ConsultarMembresia(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                nivelsel=response;

                if(response.equals("1")){
                    btnbasico.setBackgroundResource(R.color.btnselected);
                    btnmedio.setBackgroundResource(R.color.btnblue);
                    btnpremium.setBackgroundResource(R.color.btngreen);
                    txt1.setVisibility(View.VISIBLE);
                    btnpagar.setEnabled(false);
                    btnregistrar.setEnabled(false);
                }else if(response.equals("2")){
                    btnbasico.setBackgroundResource(R.color.btnred);
                    btnmedio.setBackgroundResource(R.color.btnselected);
                    btnpremium.setBackgroundResource(R.color.btngreen);
                    txt2.setVisibility(View.VISIBLE);
                    btnpagar.setEnabled(false);
                    btnregistrar.setEnabled(false);
                }
                else if(response.equals("3")){
                    btnbasico.setBackgroundResource(R.color.btnred);
                    btnmedio.setBackgroundResource(R.color.btnblue);
                    btnpremium.setBackgroundResource(R.color.btnselected);
                    txt3.setVisibility(View.VISIBLE);
                    btnpagar.setEnabled(false);
                    btnregistrar.setEnabled(false);
                }


                Log.d("HOLA",response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){



        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

}