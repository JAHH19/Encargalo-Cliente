package com.example.cliente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cliente.adapter.FragmentosFun;
import com.example.cliente.adapter.IPConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CategoriaFragment extends Fragment {


    private ListView list;
    private EditText filter;
    ArrayAdapter<String> adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentosFun fragmentosFun = new FragmentosFun();
        PublicidadFragment publicidadFragment =new PublicidadFragment();
        View view = inflater.inflate(R.layout.fragment_categoria, container, false);
        list = (ListView) view.findViewById(R.id.listcategory);
        filter = (EditText)view.findViewById(R.id.searchcategory);



        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (CategoriaFragment.this).adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cat = (String) parent.getItemAtPosition(position);


                fragmentosFun.Setdato(cat);



                fragmentosFun.hideFragment(CategoriaFragment.this,getContext());

            }
        });

        listarcategorias(IPConfig.ipServidor +"cliente/consultarcategorias.php");
        // Inflate the layout for this fragment
        return view;
    }

    private void listarcategorias(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response);
                    ArrayList<String> arraycat = new ArrayList<String>();
                    Log.d("jahh20","Resp"+jsonArray.length());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String id= jsonObject1.getString("IdCategoria");
                            String categoria = jsonObject1.getString("nombre");

                            Log.d("jahh20","Resp"+id+ " "+categoria);
                            arraycat.add(id+"| "+categoria);




                        } catch (Exception e) {

                            Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("jahh20","Resp"+e.getMessage());

                        }
                    }
                    adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, arraycat);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    list.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("jahh20","Resp"+e.getMessage());
                }


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

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(hidden){
            Log.d("hola","sdasd sdsa asd");


        }

    }
}