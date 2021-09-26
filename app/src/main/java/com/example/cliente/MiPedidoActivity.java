package com.example.cliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cliente.Database.Database;
import com.example.cliente.Model.ItemListOrder;
import com.example.cliente.adapter.RecyclerListado;

import java.util.List;

public class MiPedidoActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView tvBodega,subtotal;
    Button btnAgregarmas, btnContinuar;
    List<ItemListOrder> cart;
    RecyclerListado adapter;
    String nomTienda,idTienda;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_pedido);


        recyclerView = findViewById(R.id.rvListaProductosPedido);
        recyclerView.setHasFixedSize(true);
        tvBodega = findViewById(R.id.txtNomTienda);
        subtotal = findViewById(R.id.txtSubtotal);
        btnAgregarmas = findViewById(R.id.btnaddmore);
        btnContinuar = findViewById(R.id.btnVerificarCompra);
        nomTienda = getIntent().getStringExtra("nombreTienda");
        idTienda = getIntent().getStringExtra("IdTienda");
        tvBodega.setText(nomTienda);
        cargarListado();
        btnAgregarmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });
        /*btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MiPedidoActivity.this,ConfirmarPedidoActivity.class);
                intent.putExtra("subtotal",String.valueOf(subtotal1));
                intent.putExtra("idtienda_1",idTienda);
                startActivity(intent);
            }
        });*/

    }
    private void cargarListado(){
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        cart = new Database(this).getProductosPedidos();
        adapter = new RecyclerListado(cart,MiPedidoActivity.this);
        recyclerView.setAdapter(adapter);
        Double subtotal1 = 0.0;
        for (ItemListOrder order:cart){
            subtotal1+=(Math.round(Double.parseDouble(order.getCant())*Double.parseDouble(order.getPrecio())*100.0)/100.0);
        }
        subtotal.setText("$. "+String.valueOf(subtotal1)+" MX");
        Double finalSubtotal = subtotal1;
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MiPedidoActivity.this,ConfirmarPedidoActivity.class);
                intent.putExtra("subtotal",String.valueOf(finalSubtotal));
                intent.putExtra("idtienda_1",idTienda);
                intent.putExtra("descritienda",nomTienda);
                startActivity(intent);
            }
        });
        refresh(500);
    }
    private void refresh(int milli){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                cargarListado();
            }
        };
        handler.postDelayed(runnable,milli);
    }
}