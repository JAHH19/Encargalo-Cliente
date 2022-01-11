package com.example.cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cliente.Model.ItemListDetallePedido;
import com.example.cliente.Model.ItemListHistorialPedidos;
import com.example.cliente.adapter.IPConfig;
import com.example.cliente.adapter.RecyclerDetallePedido;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetallePedidoActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    GoogleMap gmap;
    private ImageView imgTienda;
    private TextView nomTienda, estadoPedido, subtotal, total, repartidor, tiempoentrega;
    private ItemListHistorialPedidos itemDetail;
    private RecyclerView rvDetalle;
    private RecyclerDetallePedido adapter;
    private List<ItemListDetallePedido> items;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    SupportMapFragment supportMapFragment;
    //FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);
        initviews();
        initcomponents();
    }

    private void initviews() {
        imgTienda = findViewById(R.id.imageView2);
        nomTienda = findViewById(R.id.txtNombreTienda);
        estadoPedido = findViewById(R.id.textView13);
        subtotal = findViewById(R.id.txvSubtotal);
        total = findViewById(R.id.txvTotal);
        repartidor = findViewById(R.id.textView25);
        tiempoentrega = findViewById(R.id.textView27);
        rvDetalle = findViewById(R.id.rvDetallePedido);
    }

    private void initcomponents() {
        itemDetail = (ItemListHistorialPedidos) getIntent().getExtras().getSerializable("itemDetail");
        String idpedido = getIntent().getStringExtra("idpedido");
        imgTienda.setBackgroundResource(itemDetail.getImgTienda());
        imgTienda.setImageResource(R.drawable.gradient_back);
        nomTienda.setText(itemDetail.getNomTienda());
        estadoPedido.setText(itemDetail.getEstado());
        subtotal.setText("$. " + itemDetail.getTotal() + " MX");
        total.setText("$. " + itemDetail.getTotal() + " MX");
        if (itemDetail.getEstado().equals("SOLICITADO")) {
            estadoPedido.setBackgroundColor(Color.parseColor("#FFFF00"));
        } else {
            if (itemDetail.getEstado().equals("ACEPTADO")) {
                estadoPedido.setBackgroundColor(Color.parseColor("#CC9900"));
            } else {
                if (itemDetail.getEstado().equals("EN CAMINO")) {
                    estadoPedido.setBackgroundColor(Color.parseColor("#99FFFF"));
                } else {
                    if (itemDetail.getEstado().equals("ENTREGADO")) {
                        estadoPedido.setBackgroundColor(Color.parseColor("#66FF66"));
                    } else {
                        if (itemDetail.getEstado().equals("RECHAZADO")) {
                            estadoPedido.setBackgroundColor(Color.parseColor("#CC3333"));
                        }
                    }
                }
            }
        }
        String URL = IPConfig.ipServidor +"cliente/mostrarDetallePedido.php?idpedido=" + idpedido;
        request = Volley.newRequestQueue(this);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, this, this);
        request.add(jsonObjectRequest);

        /*supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mape);

        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(DetallePedidoActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(DetallePedidoActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }*/
    }

    /*private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                            MarkerOptions options = new MarkerOptions().position(latLng).title("Ubicacion");
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                            googleMap.addMarker(options);
                        }
                    });
                }
            }
        });
    }*/


    private List<ItemListDetallePedido> getItems (JSONObject response){
        List<ItemListDetallePedido> itemListDetallePedidos = new ArrayList<>();
        JSONArray json = response.optJSONArray("consulta");
        try {
            for (int i=0;i<json.length();i++){
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                itemListDetallePedidos.add(new ItemListDetallePedido(jsonObject.getString("cantidad") ,jsonObject.getString("descripcion"),jsonObject.getString("subtotal")));
            }
        }catch (JSONException e){

        }
        return itemListDetallePedidos;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvDetalle.setLayoutManager(manager);
        items = getItems(response);
        adapter=new RecyclerDetallePedido(items);
        rvDetalle.setAdapter(adapter);
    }
/*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 44){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }*/
}