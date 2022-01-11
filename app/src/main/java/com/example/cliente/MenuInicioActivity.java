package com.example.cliente;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cliente.adapter.FragmentosFun;
import com.example.cliente.adapter.IPConfig;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuInicioActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    String idusu;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    TextView navUsername, navUbicacion,iduser;
    ImageView adimage;
    Button btncerrarad;
    RelativeLayout adcontainer;

    private AppBarConfiguration mAppBarConfiguration;
    int i=0;

    RequestQueue requestQueue;
    String idusuario;

    ArrayList<String> anuncios = new ArrayList<String>();
    ArrayList<String> urls = new ArrayList<String>();
    FragmentosFun fragmentosFun = new FragmentosFun();
    String TabFragmentPublicidad;

    public void setTabFragmentPublicidad(String t){
        TabFragmentPublicidad=t;
    }

    public String getTabFragmentPublicidad(){
        return TabFragmentPublicidad;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuinicio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Fragment_perfil_menu fragment_perfil_menu = new Fragment_perfil_menu();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_comprar, R.id.nav_mis_pedidos,R.id.nav_notificaciones,R.id.nav_menupublicidad,R.id.nav_ajustes,R.id.nav_calificacion,R.id.nav_mi_perfil)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headView = navigationView.getHeaderView(0);
        ImageView imgperfil = headView.findViewById(R.id.btn_perfil);
        navUsername = headView.findViewById(R.id.txtUsuario);
        iduser = headView.findViewById(R.id.txtidcliente);

        Intent intent = new Intent(getApplicationContext(), PublicidadFragment.class);
        intent.putExtra("idusuario",iduser.getText().toString());

        navUbicacion = headView.findViewById(R.id.txtUbicacion);
        idusuario = getIntent().getExtras().getString("idusuario");
        iduser.setText(idusuario);
        adimage=findViewById(R.id.imagead);
        adcontainer = findViewById(R.id.adcontainer);
        btncerrarad = findViewById(R.id.btn_cerrarad);



        btncerrarad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adcontainer.setVisibility(View.GONE);
            }
        });





            buscarAnuncio( IPConfig.ipServidor +"patrocinador/consultaranuncio.php?idusuario="+idusuario);



            final Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                int i=0;
                public void run() {

                    if(anuncios.size()>0 && urls.size()>0){
                        String uri = anuncios.get(i);
                        String url = urls.get(i);

                        adimage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent viewIntent4 =
                                        new Intent("android.intent.action.VIEW",
                                                Uri.parse(url));
                                startActivity(viewIntent4);
                            }
                        });
                        Picasso.get().load(uri).into(adimage);
                        Picasso.get().setLoggingEnabled(true);
                       // Toast.makeText(getApplicationContext(), "" + i, Toast.LENGTH_SHORT).show();
                        i++;
                        if(i>anuncios.size()-1) {
                            i=0;
                        }
                        handler.postDelayed(this, 4000);  //for interval...
                    }
                }
            };
            handler.postDelayed(runnable, 4000);






    }





        //updateNavHeader();





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuinicio, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public  void updateNavHeader(){
        idusu = getIntent().getStringExtra("idusuario");
        String URL =IPConfig.ipServidor +"cliente/consultarNombreUbicacionusuario.php?idusuario="+idusu;
        URL = URL.replace(" ", "%20");
        request = Volley.newRequestQueue(this);
        jsonObjectRequest = new  JsonObjectRequest(Request.Method.GET,URL,null,this,this);
        request.add(jsonObjectRequest);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"Ha ocurrido un error"+error.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("consulta");
        JSONObject jsonObject = null;
        try {
            jsonObject = json.getJSONObject(0);
            navUsername.setText(jsonObject.getString("nombres"));
            navUbicacion.setText(jsonObject.getString("ubicacion"));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void buscarAnuncio(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                Log.d("HOLA",response+"");
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        anuncios.add(jsonObject.getString("imagen"));
                        urls.add(jsonObject.getString("url"));

                    } catch (JSONException ex) {
                        Toast.makeText(getApplicationContext(), "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error de conexion"+error,Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    protected void onResume() {
        buscarAnuncio( IPConfig.ipServidor +"patrocinador/consultaranuncio.php?idusuario="+idusuario);
        adcontainer.setVisibility(View.VISIBLE);
        super.onResume();
    }
}