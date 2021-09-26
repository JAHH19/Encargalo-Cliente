package com.example.cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cliente.Database.Database;
import com.example.cliente.Model.ItemListOrder;
import com.example.cliente.Model.ItemListProductos;

import java.util.List;

import static java.sql.Types.NULL;

public class DetalleProductoActivity extends AppCompatActivity {
    private TextView tvDescripcionProducto, tvPrecioProducto, tvUnidadesProducto, tvprecioxcantidad;
    private ImageView imgProducto, btnagregar, btnquitar;
    private Button btnagregarProducto;
    private ItemListProductos items;
    private Double precioTotal,precioproducto;
    private int cantOrden=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);
        initViews();
        getBundle();
    }
    private void initViews(){
        btnagregarProducto = findViewById(R.id.btnagregarproducto);
        tvDescripcionProducto = findViewById(R.id.txtDescriDetalleProducto);
        tvPrecioProducto = findViewById(R.id.txtPrecioDetalleProducto);
        tvUnidadesProducto = findViewById(R.id.txtUnidadesDetalleProducto);
        tvprecioxcantidad = findViewById(R.id.txtprecioxcantidadproducto);
        imgProducto = findViewById(R.id.ImgDetalleProducto);
        btnagregar = findViewById(R.id.btnagregar);
        btnquitar = findViewById(R.id.btnquitar);
    }
    private void getBundle(){

        items = (ItemListProductos) getIntent().getExtras().getSerializable("itemProducto");
        tvDescripcionProducto.setText(items.getDescripcion());
        tvUnidadesProducto.setText(String.valueOf(cantOrden));
        precioproducto = Math.round((Double.parseDouble(items.getPrecio()))*100)/100.0;
        tvPrecioProducto.setText("$. "+precioproducto+" MX");
        precioTotal = Math.round(precioproducto*cantOrden*100)/100.0;
        tvprecioxcantidad.setText("$."+String.valueOf(precioTotal)+" MX");
        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cantOrden = cantOrden + 1;
                tvUnidadesProducto.setText(String.valueOf(cantOrden));
                precioTotal = Math.round(precioproducto*cantOrden*100)/100.0;
                tvprecioxcantidad.setText("$."+String.valueOf(precioTotal)+" MX");
            }
        });
        btnquitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cantOrden>1){
                    cantOrden = cantOrden-1;
                }
                tvUnidadesProducto.setText(String.valueOf(cantOrden));
                precioTotal = Math.round(precioproducto*cantOrden*100)/100.0;
                tvprecioxcantidad.setText("$."+String.valueOf(precioTotal)+" MX");
            }
        });


        btnagregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Database(DetalleProductoActivity.this).addLista(new ItemListOrder(
                        NULL,items.getIdproducto(),items.getDescripcion(),String.valueOf(cantOrden),items.getPrecio()
                ));
                finish();
                //return;
            }
        });


    }
}