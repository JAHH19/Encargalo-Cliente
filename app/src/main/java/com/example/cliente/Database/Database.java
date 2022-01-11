package com.example.cliente.Database;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cliente.ConfirmarPedidoActivity;
import com.example.cliente.Model.ItemListOrder;
import com.example.cliente.adapter.IPConfig;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME="DetailDB.db";
    private static final int DB_VER=1;


    private static Database sInstance;
    public static synchronized Database getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new Database(context.getApplicationContext());
        }
        return sInstance;
    }
    public Database(Context context) {
        super(context, DB_NAME,null, DB_VER);
    }
    RequestQueue requestQueue;
    List<ItemListOrder> cart;

    public List<ItemListOrder> getProductosPedidos(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ID","IdProducto","proDescripcion","cantidad","precio"};
        String sqlTable = "OrderDetail";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);
        final List<ItemListOrder> result = new ArrayList<>();
        if (c.moveToFirst())
        {
            do{
                result.add(new ItemListOrder(c.getInt(c.getColumnIndex("ID")),
                        c.getString(c.getColumnIndex("IdProducto")),
                        c.getString(c.getColumnIndex("proDescripcion")),
                        c.getString(c.getColumnIndex("cantidad")),
                        c.getString(c.getColumnIndex("precio"))
                        ));
            }while (c.moveToNext());
        }

        return result;
    }
    public void  addLista(ItemListOrder order){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail (IdProducto,proDescripcion,cantidad,precio) VALUES ('%s','%s','%s','%s');",
                order.getIdProducto(),
                order.getProDescripcion(),
                order.getCant(),
                order.getPrecio());
        db.execSQL(query);
    }
    public void  cleanLista(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }
    public void  deletePedido(int id){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail WHERE ID ="+id);
        db.execSQL(query);
    }
    public void  actualizarPedido(int id,String cantidad){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("UPDATE OrderDetail SET cantidad="+cantidad+" WHERE ID="+id);
        db.execSQL(query);
    }
    public void realizarPedido(String idpedido, Activity activity){

        cart = new Database(activity).getProductosPedidos();
        for (ItemListOrder order:cart){
            String URL2 =  IPConfig.ipServidor +"cliente/ingresarlistapedido.php?idtiendaproducto="+order.getIdProducto()+"&idpedido="+idpedido+"&cantidad="+order.getCant()+"&preciouniMX="+order.getPrecio()+"&subtotal="+String.valueOf((Double.parseDouble(order.getCant())*Double.parseDouble(order.getPrecio())*100)/100.0);
            StringRequest request2 = new StringRequest(Request.Method.GET, URL2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(activity,error.toString(),Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue = Volley.newRequestQueue(activity);
            requestQueue.add(request2);
        }
    }
}
