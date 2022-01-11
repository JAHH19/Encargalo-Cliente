package com.example.cliente;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cliente.Model.ItemListEsta;
import com.example.cliente.Model.ItemListHistorialPedidos;
import com.example.cliente.adapter.Adaptador;
import com.example.cliente.adapter.IPConfig;
import com.example.cliente.adapter.RecyclerHistorialPedidos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MisPedidosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MisPedidosFragment extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {

    private RecyclerView rvListHistorialPedido;
    private RecyclerHistorialPedidos adapater;
    private List<ItemListHistorialPedidos> items;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MisPedidosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nav_mis_pedidos.
     */
    // TODO: Rename and change types and number of parameters
    public static MisPedidosFragment newInstance(String param1, String param2) {
        MisPedidosFragment fragment = new MisPedidosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mis_pedidos, container, false);
        rvListHistorialPedido = view.findViewById(R.id.rvHistorialPedidos);

        SharedPreferences preferencia = this.getContext().getSharedPreferences("ARCHIVOREG", Context.MODE_PRIVATE);
        String usuario = preferencia.getString("usuario","");
        String URL = IPConfig.ipServidor +"/cliente/listarHistorialPedidos.php?idusuario="+usuario;
        request = Volley.newRequestQueue(getContext());
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL,null,this,this);
        request.add(jsonObjectRequest);

        return view;
    }
    private List<ItemListHistorialPedidos> getItems (JSONObject response){
        List<ItemListHistorialPedidos> itemListHistorialPedidos = new ArrayList<>();
        JSONArray json = response.optJSONArray("consulta");
        try {
            for (int i=0;i<json.length();i++){
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                itemListHistorialPedidos.add(new ItemListHistorialPedidos(jsonObject.getString("Num") ,jsonObject.getString("fecha"),jsonObject.getString("nombre"),jsonObject.getString("importetotalMX"),jsonObject.getString("nombreEstado"),R.drawable.tienda,jsonObject.getString("IdPedido")));
            }
        }catch (JSONException e){

        }
        return itemListHistorialPedidos;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvListHistorialPedido.setLayoutManager(manager);
        items = getItems(response);
        adapater=new RecyclerHistorialPedidos(items);
        rvListHistorialPedido.setAdapter(adapater);
    }
}