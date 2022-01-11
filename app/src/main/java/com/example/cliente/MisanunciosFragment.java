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
import com.example.cliente.adapter.AdapterAds;
import com.example.cliente.adapter.IPConfig;
import com.example.cliente.adapter.ModelAds;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class MisanunciosFragment extends Fragment {

    private RecyclerView adsRv;
    private Button loadMoreBtn,btnadsdetail;
    String idusuario;
    private String url;
    private String nextToken="";

    private ArrayList<ModelAds> adsArrayList;
    private AdapterAds adapterAds;

    private ProgressDialog progressDialog;
    AnuncioDetallesFragment anuncioDetallesFragment = new AnuncioDetallesFragment();

    private static final String TAG = "MAIN_TAG";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_misanuncios, container, false);
        idusuario= getActivity().getIntent().getExtras().getString("idusuario");

        adsRv = view.findViewById(R.id.adsRv);
        loadMoreBtn = view.findViewById(R.id.btnmore);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Espere por favor");


        adsArrayList = new ArrayList<>();
        adsArrayList.clear();


        url=IPConfig.ipServidor +"patrocinador/consultaanuncioespf.php?idusuario="+idusuario;
        loadAds();
        loadMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAds();
            }
        });



        return view;
    }

    private void loadAds() {
        progressDialog.show();
        adsArrayList.clear();

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
                            String id = jsonObject1.getString("IdAnuncio");
                            String titulo= jsonObject1.getString("titulo");
                            String descripcion = jsonObject1.getString("descripcion");
                            String categoria = jsonObject1.getString("categoria");
                            String url = jsonObject1.getString("url");
                            String fechainicio = jsonObject1.getString("fechaInicio");
                            String fechafinal = jsonObject1.getString("fechaFinal");
                            String montopagado = jsonObject1.getString("montoPagado");

                            String image = jsonObject1.getString("imagen");

                            ModelAds modelAds = new ModelAds( ""+titulo,
                                    "" + descripcion, "" + image,
                                    "" + categoria,
                                    "" + url,
                                    "" + fechainicio,
                                    "" + fechafinal,
                                    "" + montopagado,
                                    ""+id
                                    );
                            adsArrayList.add(modelAds);


                        } catch (Exception e) {
                            Log.d(TAG, "Reponse 1:" + e.getMessage());
                            Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }




                    }

                    adapterAds = new AdapterAds(getContext(), adsArrayList);

                    adsRv.setAdapter(adapterAds);

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