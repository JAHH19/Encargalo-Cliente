package com.example.cliente.ui.gallery;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cliente.R;
import com.example.cliente.adapter.IPConfig;

public class GalleryFragment extends Fragment {

    String level,idusuario;
    TextView nivelM;
    Button btncancelar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        idusuario= getActivity().getIntent().getExtras().getString("idusuario");
        nivelM=(TextView)root.findViewById(R.id.txtmembresia);

        btncancelar = (Button)root.findViewById(R.id.btncancelarm);

        btncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getContext()).setTitle("Cancelar Membresia")
                        .setMessage("¿Estas seguro que quieres cancelar tu membresia?")
                        .setPositiveButton("Si",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        CancelarMembresia( IPConfig.ipServidor +"patrocinador/cancelarMembresia.php?usuario="+idusuario);
                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();

            }
        });
        ObtenerMembresia( IPConfig.ipServidor +"patrocinador/consultarNivelMembresia.php?usuario="+idusuario);


        return root;
    }

    private void CancelarMembresia(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Solicitud concedida")
                        .setPositiveButton("ok", null)
                        .setMessage( "Has cancelado tu plan de membresia. \n")
                        .show();


                Log.d("HOLA",response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){



        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private String ObtenerMembresia(String URL){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                level =response;
                nivelM.setText(level);
                if(level.equals("-")){
                    btncancelar.setEnabled(false);
                }
                Log.d("HOLA",response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                level="-";
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){



        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

        return level;

    }

}