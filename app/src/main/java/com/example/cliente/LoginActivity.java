package com.example.cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    EditText edtUsuario, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnIngresar = findViewById(R.id.btn_ingresar);
        Button btnCrearCuenta = findViewById(R.id.btn_crear_cuenta);
        edtUsuario=findViewById(R.id.editTextTelefono);
        edtPassword=findViewById(R.id.editTextclave);
        SharedPreferences shpreferences = getSharedPreferences("ARCHIVOREG", Context.MODE_PRIVATE);
        edtUsuario.setText(shpreferences.getString("usuario",""));
        //edtPassword.setText(shpreferences.getString("pass",""));

        btnIngresar.setOnClickListener(v -> {
            validarUsuario( IPConfig.ipServidor +"cliente/validarusuario.php");
            SharedPreferences shpreferencias = getSharedPreferences("ARCHIVOREG",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = shpreferencias.edit();
            editor.putString("usuario", edtUsuario.getText().toString());
            editor.putString("pass", edtPassword.getText().toString());
            editor.commit();
        });

        btnCrearCuenta.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegistroActivity.class);
            startActivity(intent);

        });
    }

    private  void validarUsuario(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Intent intent = new Intent(getApplicationContext(), MenuInicioActivity.class);
                    intent.putExtra("idusuario",edtUsuario.getText().toString());
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this,"¡Bienvenido!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this,"Usuario o contraseña incorrecta",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("usuario",edtUsuario.getText().toString());
                parametros.put("password",edtPassword.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}

