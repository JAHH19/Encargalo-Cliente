package com.example.cliente;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cliente.adapter.AdaptadorPromos;
import com.example.cliente.adapter.AdapterAds;
import com.example.cliente.adapter.IPConfig;
import com.example.cliente.adapter.ModelAds;
import com.example.cliente.adapter.ModelPromos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class PromocionesFragment extends Fragment {

    private RecyclerView adsRv;
    private Button loadMoreBtn,btnadsdetail;
    String idusuario;
    private String url;
    private String nextToken="";

    private ArrayList<ModelPromos> promoArrayList;
    private AdaptadorPromos adaptadorPromos;

    private ProgressDialog progressDialog;
    AnuncioDetallesFragment anuncioDetallesFragment = new AnuncioDetallesFragment();

    private static final String TAG = "MAIN_TAG";
    public static String idtiendas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promociones, container, false);
        idusuario= getActivity().getIntent().getExtras().getString("idusuario");
        loadMoreBtn = view.findViewById(R.id.btnmorePromos);
        adsRv = view.findViewById(R.id.promoRv);


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Espere por favor");


        promoArrayList = new ArrayList<>();
        promoArrayList.clear();


        url= IPConfig.ipServidor +"tienda/consultaPromocion.php";
        loadPromos();
        loadMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPromos();
            }
        });



        return view;

    }

    private void  loadPromos() {
        progressDialog.show();
        promoArrayList.clear();

        Log.d(TAG,"loadads URL: "+url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                Log.d(TAG, "onReposnse " + response);

                try {


                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String id = jsonObject1.getString("id");
                            String titulo= jsonObject1.getString("Titulo");
                            String estado = jsonObject1.getString("Estado");
                            String idtienda = jsonObject1.getString("idTienda");
                            String url = jsonObject1.getString("imagen");
                            String fechainicio = jsonObject1.getString("fecha");
                            String fechafinal = jsonObject1.getString("fechafin");
                            String idcategoria = jsonObject1.getString("idCategoria");
                            String idproducto = jsonObject1.getString("idProducto");

                            idtiendas=idtienda;
                            Log.d(TAG, "onReposnse99 " + idtiendas);

                            ModelPromos modelPromo = new ModelPromos( ""+id,
                                    "" + titulo, "" + fechainicio,
                                    "" + fechafinal,
                                    "" + url,
                                    "" + estado,
                                    "" + idtienda,
                                    "" + idcategoria,
                                    ""+idproducto
                            );
                            promoArrayList.add(modelPromo);


                        } catch (Exception e) {
                            Log.d(TAG, "Reponse 1:" + e.getMessage());
                            Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }




                    }

                    adaptadorPromos = new AdaptadorPromos(getContext(), promoArrayList);

                    adsRv.setAdapter(adaptadorPromos);

                    progressDialog.dismiss();

                } catch (Exception e) {
                    Log.d(TAG, "Reponse 2:" + e.getMessage());
                    Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"OnErrorResponse: "+error.getMessage());
                Toast.makeText(getContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}