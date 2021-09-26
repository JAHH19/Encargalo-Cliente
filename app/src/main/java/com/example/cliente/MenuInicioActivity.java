package com.example.cliente;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
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

public class MenuInicioActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    String idusu;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    TextView navUsername, navUbicacion;


    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuinicio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_comprar, R.id.nav_mis_pedidos,R.id.nav_notificaciones,R.id.nav_ajustes,R.id.nav_calificacion,R.id.nav_mi_perfil)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headView = navigationView.getHeaderView(0);
        ImageView imgperfil = headView.findViewById(R.id.btn_perfil);
        navUsername = headView.findViewById(R.id.txtUsuario);
        navUbicacion = headView.findViewById(R.id.txtUbicacion);
        /*imgperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_mi_perfil);
                drawer.closeDrawer(GravityCompat.START);
            }
        });*/
        updateNavHeader();
    }

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
        String URL = "http://40.124.98.26/APIS/cliente/consultarNombreUbicacionusuario.php?idusuario="+idusu;
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
}