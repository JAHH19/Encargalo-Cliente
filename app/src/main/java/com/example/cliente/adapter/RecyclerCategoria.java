package com.example.cliente.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cliente.Model.ItemListCategoria;
import com.example.cliente.Model.ItemListProductos;
import com.example.cliente.MostrarProductosTienda;
import com.example.cliente.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecyclerCategoria extends RecyclerView.Adapter<RecyclerCategoria.RecyclerHolder> {

    private List<ItemListCategoria> items;
    public RecyclerProductos adapater;
    public List<ItemListProductos> itemListProductos;
    private Activity activity;
    RequestQueue request;
    String idtienda, categoria;

    public RecyclerCategoria(Activity activity,List<ItemListCategoria> items) {
        this.items = items;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_list_categoria, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {

        ItemListCategoria item = items.get(position);
        holder.tvCategoria.setText(item.getCategoria());
        idtienda = item.getIdTienda();
        categoria=item.getCategoria();
        String URL = IPConfig.ipServidor +"cliente/ListarProductosporCat.php?idtienda="+idtienda+"&categoria="+categoria;
        URL = URL.replaceAll(" ", "%20");
        StringRequest requesty = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LinearLayoutManager manager = new LinearLayoutManager(activity);
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                holder.rvProductos.setLayoutManager(manager);
                List<ItemListProductos> itemListProductos = new ArrayList<>();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray json = jsonObject.getJSONArray("consulta");
                    for (int i=0;i<json.length();i++){
                        jsonObject = json.getJSONObject(i);
                        itemListProductos.add(new ItemListProductos(jsonObject.getString("idtiendaproducto"),jsonObject.getString("descripcion"),jsonObject.getString("precioventa"),R.drawable.imgproductosdef));
                    }
                }catch (JSONException e){

                }
                adapater = new RecyclerProductos(itemListProductos);
                holder.rvProductos.setAdapter(adapater);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request = Volley.newRequestQueue(activity);
        request.add(requesty);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class RecyclerHolder extends RecyclerView.ViewHolder{
        private TextView tvCategoria;
        RecyclerView rvProductos;
        public RecyclerHolder(@NonNull View itemView){
            super(itemView);
            tvCategoria = itemView.findViewById(R.id.txtCategoria);
            rvProductos = itemView.findViewById(R.id.rvProductos);
        }
    }
}
