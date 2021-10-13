package com.example.cliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cliente.Database.Database;
import com.example.cliente.Model.ItemListCategoria;
import com.example.cliente.Model.ItemListEsta;
import com.example.cliente.Model.ItemListOrder;
import com.example.cliente.adapter.RecyclerCategoria;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MostrarProductosTienda extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {
    private TextView tvNombre, tvubicacion, tvcalificacion,tvcantpedido,tvsumatotal;
    private ItemListEsta itemDetail;
    private String idtienda;
    private RecyclerView rvListaCategoria;
    private RecyclerCategoria adapter;
    private List<ItemListCategoria> itemListCategorias;
    List<ItemListOrder> cart1;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Button btnVerListadoPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_productos_tienda);

        initViews();
        initValues();
        cargarCantPrecio();


        btnVerListadoPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MostrarProductosTienda.this,MiPedidoActivity.class);
                i.putExtra("nombreTienda", itemDetail.getNom_tienda());
                i.putExtra("IdTienda",itemDetail.getId_tienda());
                startActivity(i);
            }
        });
    }

    private void cargarCantPrecio(){
        cart1 = new Database(this).getProductosPedidos();
        Double subtotal2 = 0.0;
        int i = 0;
        for (ItemListOrder order:cart1){
            subtotal2+=(Math.round(Double.parseDouble(order.getCant())*Double.parseDouble(order.getPrecio())*100.0)/100.0);
            i=i+1;
        }
        tvcantpedido.setText(String.valueOf(i));
        tvsumatotal.setText("$. "+String.valueOf(subtotal2)+" MX");
        refresh(500);
    }
    private void refresh(int milli){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                cargarCantPrecio();
            }
        };
        handler.postDelayed(runnable,milli);
    }


    private void initViews(){
        tvNombre = findViewById(R.id.txtNombreTienda);
        tvubicacion = findViewById(R.id.txtUbicacion);
        tvcalificacion = findViewById(R.id.tvCalific);
        rvListaCategoria = findViewById(R.id.rvCategorias);
        rvListaCategoria.setHasFixedSize(true);
        btnVerListadoPedidos = findViewById(R.id.btnconfirmar);
        tvcantpedido = findViewById(R.id.txtcantproductos);
        tvsumatotal = findViewById(R.id.txtsumatotal);
    }
    private void initValues(){
        itemDetail = (ItemListEsta) getIntent().getExtras().getSerializable("item");
        tvNombre.setText(itemDetail.getNom_tienda());
        tvubicacion.setText(itemDetail.getUbicacion());
        tvcalificacion.setText(itemDetail.getCalific());
        idtienda = itemDetail.getId_tienda();


        String URL = "http://192.168.1.125:2020/APIS/cliente/ListarCategoriaporID.php?idtienda="+idtienda;
        request = Volley.newRequestQueue(this);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL,null,this,this);
        request.add(jsonObjectRequest);
    }
    private List<ItemListCategoria> getItemListCategorias(JSONObject response){
        List<ItemListCategoria> itemListCategoria = new ArrayList<>();
        JSONArray json = response.optJSONArray("consulta");
        try {
            for (int i=0;i<json.length();i++){
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                itemListCategoria.add(new ItemListCategoria(idtienda,jsonObject.getString("categoria") ));
            }
        }catch (JSONException e){

        }
        return itemListCategoria;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvListaCategoria.setLayoutManager(manager);
        itemListCategorias = getItemListCategorias(response);
        adapter = new RecyclerCategoria(MostrarProductosTienda.this,itemListCategorias);
        rvListaCategoria.setAdapter(adapter);
    }
}