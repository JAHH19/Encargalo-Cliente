package com.example.cliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cliente.Database.Database;
import com.example.cliente.Model.ItemListOrder;
import com.example.cliente.adapter.IPConfig;
import com.example.cliente.ui.slideshow.SlideshowFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ConfirmarPedidoActivity extends AppCompatActivity {
    EditText txtDescripcion,txtComentario;
    TextView txtTienda,txtsubtotal,txtenvio,txtTotal;
    Button btnenviar;
    String usuario,idtienda_1,subtotal,idpedido,nomtienda;
    RequestQueue requestQueue,requestQueue2;
    Date date = new Date();
    List<ItemListOrder> cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_pedido);
        SharedPreferences preferencias = getSharedPreferences("ARCHIVOREG", Context.MODE_PRIVATE);
        usuario = preferencias.getString("usuario","");
        idtienda_1 = getIntent().getStringExtra("idtienda_1");
        subtotal = getIntent().getStringExtra("subtotal");
        nomtienda = getIntent().getStringExtra("descritienda");
        initvalues();
        initViews();
    }

    private void initViews() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String hora = simpleDateFormat.format(date);
        SimpleDateFormat fechac = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = fechac.format(date);
        txtsubtotal.setText("$. "+subtotal+" MX");
        txtenvio.setText("$. 0.0 MX");
        txtTienda.setText(nomtienda);
        txtTotal.setText("$. "+subtotal+" MX");
        btnenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL =  IPConfig.ipServidor +"cliente/obtenerIdPedido.php?total="+subtotal+"&hora="+hora+"&fecha="+fecha+"&comentario="+txtComentario.getText()+"&idusuario="+usuario+"&idtienda="+idtienda_1+"&descripcion="+txtDescripcion.getText();
                URL = URL.replaceAll(" ", "%20");
                StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray json = jsonObject.getJSONArray("consulta");
                            jsonObject = json.getJSONObject(0);
                            idpedido=jsonObject.getString("IdPedido");
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue = Volley.newRequestQueue(ConfirmarPedidoActivity.this);
                requestQueue.add(request);
                cart = new Database(ConfirmarPedidoActivity.this).getProductosPedidos();

                for (int i = 0; i<cart.size();i++){
                    Handler handler = new Handler();
                    int finalI = i;
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            String URL2 =  IPConfig.ipServidor +"cliente/ingresarlistapedido.php?idtiendaproducto="+cart.get(finalI).getIdProducto()+"&idpedido="+idpedido+"&cantidad="+cart.get(finalI).getCant()+"&preciouniMX="+cart.get(finalI).getPrecio()+"&subtotal="+String.valueOf((Double.parseDouble(cart.get(finalI).getCant())*Double.parseDouble(cart.get(finalI).getPrecio())*100)/100.0);
                            StringRequest request2 = new StringRequest(Request.Method.GET, URL2, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                }

                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ConfirmarPedidoActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                                }
                            });
                            requestQueue2 = Volley.newRequestQueue(ConfirmarPedidoActivity.this);
                            requestQueue2.add(request2);
                        }
                    };
                    handler.postDelayed(runnable,1000);

                }
                Toast.makeText(ConfirmarPedidoActivity.this,"Pedido Realizado!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MenuInicioActivity.class);
                intent.putExtra("idusuario",usuario);
                startActivity(intent);
            }
        });
    }

    private void initvalues() {
        txtDescripcion = findViewById(R.id.etxdireccion);
        txtComentario = findViewById(R.id.etxcomentario);
        txtTienda = findViewById(R.id.txtNombreTienda);
        txtsubtotal = findViewById(R.id.textView11);
        txtenvio = findViewById(R.id.textView10);
        txtTotal = findViewById(R.id.textView9);
        btnenviar = findViewById(R.id.btnenviarpedido);
    }
}