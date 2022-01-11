package com.example.cliente;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cliente.adapter.IPConfig;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static javax.xml.transform.OutputKeys.ENCODING;


public class AnuncioDetallesFragment extends Fragment {

    private String adsId;
    private TextView titulotx,descripciontx,periodotx,urltx;
    private ImageView img;
    private Button btnrenovar;
    PublicidadFragment publicidadFragment = new PublicidadFragment();
    private TextView cat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anuncio_detalles, container, false);

        titulotx = view.findViewById(R.id.txt_tituloads);
        descripciontx = view.findViewById(R.id.txt_descripcionads);
        cat = view.findViewById(R.id.txtcategoria);
        periodotx = view.findViewById(R.id.txt_dateads);
        urltx = view.findViewById(R.id.txt_urlads);
        img=view.findViewById(R.id.imgdetailad);





        Bundle bundle = this.getArguments();
        if (bundle != null) {
            adsId = bundle.getString("adsId", "defaulValue");
        }
        loadAdsDetail();



        return view;
    }




    private void loadAdsDetail() {
        String url=IPConfig.ipServidor +"patrocinador/consultaanuncioid.php?idanuncio="+ adsId;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONArray jsonarray = new JSONArray(response);

                    JSONObject jsonObject =  jsonarray.getJSONObject(0);

                    String titulo = jsonObject.getString("titulo");
                    String descripcion = jsonObject.getString("descripcion");
                    String categoria = jsonObject.getString("nombcat");
                    String url = jsonObject.getString("url");
                    String imagen= jsonObject.getString("imagen");
                    String fecha = "Desde "+ jsonObject.getString("fechaInicio")+" hasta "+jsonObject.getString("fechaFinal");



                    //actionBar.setSubtitle(title);
                    titulotx.setText(titulo);
                    descripciontx.setText(descripcion);

                    cat.setText(categoria);
                    urltx.setText(url);
                    periodotx.setText(fecha);
                    Picasso.get().load(imagen).into(img);




                }catch (Exception e){
                    Log.d("HOLA",e+"");
                    Toast.makeText(getContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(),""+error,Toast.LENGTH_SHORT).show();
                Log.d("HOLA",error+"");
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }



}