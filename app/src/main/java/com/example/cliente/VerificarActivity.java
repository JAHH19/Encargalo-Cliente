package com.example.cliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cliente.adapter.IPConfig;

import java.util.HashMap;
import java.util.Map;

public class VerificarActivity extends AppCompatActivity {
    Dialog myDialog;
    String sexo= "";
    String foto= "";
    String descripcion="";
    String latitud= "0";
    String longitud= "0";
    EditText usuario,contraseña;
    String nombres,appaterno,apmaterno,numcredencial,numcelular,correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar);
        myDialog = new Dialog(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Crear Cuenta");
        setSupportActionBar(toolbar);


    }
    public void ShowDialog(View v){
        Button btnAceptar,btnCancelar;
        myDialog.setContentView(R.layout.pass);
        btnAceptar = (Button) myDialog.findViewById(R.id.btnAcep);
        btnCancelar = (Button) myDialog.findViewById(R.id.bntCancel);
        usuario = (EditText) myDialog.findViewById(R.id.edtUsuario);
        contraseña = (EditText) myDialog.findViewById(R.id.edtContraseña);
        nombres = getIntent().getStringExtra("nombres");
        appaterno = getIntent().getStringExtra("appaterno");
        apmaterno = getIntent().getStringExtra("apmaterno");
        numcredencial = getIntent().getStringExtra("numcredencial");
        numcelular = getIntent().getStringExtra("numcelular");
        correo = getIntent().getStringExtra("correo");
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Crear Cuenta");
        setSupportActionBar(toolbar);


        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ejecutarServicio(IPConfig.ipServidor +"cliente/registrarcliente.php");
                iraPrincipal();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void iraPrincipal (){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void ejecutarServicio(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("apPaterno",appaterno);
                parametros.put("apMaterno",apmaterno);
                parametros.put("nombres",nombres);
                parametros.put("nidentificacion",numcredencial);
                parametros.put("celular",numcelular);
                parametros.put("sexo",sexo);
                parametros.put("email",correo);
                parametros.put("foto",foto);
                parametros.put("descripcion",descripcion);
                parametros.put("longitud",longitud);
                parametros.put("latitud",latitud);
                parametros.put("usuario",usuario.getText().toString());
                parametros.put("password",contraseña.getText().toString());

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}