package com.example.cliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cliente.Database.Database;
import com.example.cliente.Model.ItemListEsta;
import com.example.cliente.Model.ItemListProductos;
import com.example.cliente.adapter.Adaptador;
import com.example.cliente.adapter.IPConfig;
import com.example.cliente.adapter.RecyclerProductos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EsctablecimientosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, Response.ErrorListener, Response.Listener<JSONObject>{

    private RecyclerView rvLista;
    private androidx.appcompat.widget.SearchView svSearch;
    private Adaptador adapter;
    private List<ItemListEsta> items;
    String establecimiento,valor;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establecimiento);
        establecimiento = getIntent().getStringExtra("establecimiento");
        TextView tvestablecimiento = findViewById(R.id.tvEstablecimiento);
        tvestablecimiento.setText(establecimiento);
        if(establecimiento.equals("FARMACIAS")){
            valor="2";
        }else{
            if (establecimiento.equals("BODEGAS")){
                valor="3";
            }else{
                if (establecimiento.equals("FERRETERIAS")){
                    valor="1";
                }else {
                    if (establecimiento.equals("RESTAURANTES")){
                        valor="4";
                    }
                }
            }
        }
        initViews();
        initValues(valor);
        initListener();

    }

    private void initViews(){
        rvLista = findViewById(R.id.rvEstablecimiento);
        svSearch = findViewById(R.id.svBuscarProducto);
    }
    private void initValues(String valor){

        String URL =  IPConfig.ipServidor +"cliente/mostrarTiendasporID.php?idrubro="+valor;
        request = Volley.newRequestQueue(this);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL,null,this,this);
        request.add(jsonObjectRequest);

    }
    private void initListener(){
        svSearch.setOnQueryTextListener(this);
    }

    private List<ItemListEsta> getItems(JSONObject response){
        List<ItemListEsta> itemLists = new ArrayList<>();
        JSONArray json = response.optJSONArray("consulta");
        try {
            for (int i=0;i<json.length();i++){
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                itemLists.add(new ItemListEsta(jsonObject.getString("idtienda") ,jsonObject.getString("nombre"),jsonObject.getString("ubicacion"),R.drawable.tienda,jsonObject.getString("calificacion")));
            }
        }catch (JSONException e){

        }
        return itemLists;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return false;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvLista.setLayoutManager(manager);
        items = getItems(response);
        adapter = new Adaptador(items);
        rvLista.setAdapter(adapter);
    }

}