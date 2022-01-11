package com.example.cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private Button authBtn;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    String usuario,contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = this;
        SharedPreferences sharpref = getSharedPreferences("ARCHIVOBIO",context.MODE_PRIVATE);
        SharedPreferences preferencias = getSharedPreferences("ARCHIVOREG",Context.MODE_PRIVATE);
        usuario = preferencias.getString("usuario","");
        contraseña = preferencias.getString("pass","");
        authBtn = findViewById(R.id.btnIniciarSesion);
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                iralogin();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(MainActivity.this, "¡Autenticación exitosa!", Toast.LENGTH_SHORT).show();
                validarUsuario( IPConfig.ipServidor +"cliente/validarusuario.php");
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(MainActivity.this, "Ups!, ha ocurrido un fallo en la autenticacion...", Toast.LENGTH_SHORT).show();

            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticacion Biometrica")
                .setSubtitle("Inicia usando tu huella")
                .setNegativeButtonText("Iniciar con contraseña")
                .build();

        authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valor=sharpref.getString("BIO","0");
                if (valor.equals("1")){
                    biometricPrompt.authenticate(promptInfo);
                }else {
                    iralogin();
                }

            }
        });
    }
    public void iralogin (){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void iraregistro (View View){

        Intent i = new Intent(this, RegistroActivity.class);
        startActivity(i);
    }
    public void iraMenu (){
        Intent i = new Intent(this, MenuInicioActivity.class);
        i.putExtra("idusuario",usuario);
        startActivity(i);}

    private  void validarUsuario(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    iraMenu();
                    Toast.makeText(MainActivity.this,"¡Bienvenido!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Usuario o contraseña incorrecta",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("usuario",usuario);
                parametros.put("password",contraseña);
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}