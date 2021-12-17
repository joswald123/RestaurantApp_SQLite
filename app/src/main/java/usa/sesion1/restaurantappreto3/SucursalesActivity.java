package usa.sesion1.restaurantappreto3;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import usa.sesion1.restaurantappreto3.controller.AdaptadorSucursales;
import usa.sesion1.restaurantappreto3.controller.DBLocal;
//import usa.sesion1.restaurantappreto3.controller.MyOpenHelper;
import usa.sesion1.restaurantappreto3.controller.WEBSERVICE;
import usa.sesion1.restaurantappreto3.model.MySingleton;
import usa.sesion1.restaurantappreto3.model.Sucursal;
import usa.sesion1.restaurantappreto3.view.LoginActivity;
import usa.sesion1.restaurantappreto3.view.MapActivity;
import usa.sesion1.restaurantappreto3.view.MenuActivity;
import usa.sesion1.restaurantappreto3.view.ServiciosActivity;

public class SucursalesActivity extends AppCompatActivity {

    RecyclerView rcvSucursales;
    //ArrayList<Sucursal> sucursales;
    ProgressDialog barraProgreso;
    ArrayList<Sucursal> sucursales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucursales);

        //-------- LOGO ----
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setLogo(R.mipmap.ic_chef);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        // ----------------------

        rcvSucursales = (RecyclerView) findViewById(R.id.rcvSucursales);
        rcvSucursales.setLayoutManager(new LinearLayoutManager(this));



        getWebserviceSucursales();

        //new ConsultarSucursales().execute();

    }


    // Barra de menu --- navbar ----

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menudeopciones, menu);
        return true;

    }

    // --- Opciones de la barra de menu ------
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            /*case R.id.fav:
                Intent intent = new Intent(MenuActivity.this, FavoritosActivity.class);
                startActivity(intent);
                return  true;*/
            case R.id.itemFavoritos:
                Intent favoritos = new Intent(SucursalesActivity.this, FavoritosActivity.class);
                startActivity(favoritos);
                return  true;
            case R.id.itemCarrito:
                Toast.makeText(getApplicationContext(), "Pronto podrás agregar al carrito", Toast.LENGTH_LONG).show();
                return  true;
            case R.id.itemLogin:
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                return  true;
            case R.id.menu:
                Intent menu = new Intent(this, MenuActivity.class);
                startActivity(menu);
                //Toast.makeText(getApplicationContext(), "Acá podrás ver el Menú", Toast.LENGTH_LONG).show();
                return  true;
            case R.id.servicios:
                Intent servicios = new Intent(SucursalesActivity.this, ServiciosActivity.class);
                startActivity(servicios);
                return  true;
            case R.id.suc:
                Intent sucursales = new Intent(this, SucursalesActivity.class);
                startActivity(sucursales);
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // --------------------JsonArraySucursales Oracle DB-------------------------------
    public void getWebserviceSucursales(){
        String url = WEBSERVICE.GET_POST_PUT_DELETE_SUCURSALES;

        // --------------------- Barra de progreso ---------------------------------
        ProgressDialog barraProgreso = new ProgressDialog(SucursalesActivity.this);
        barraProgreso.setCancelable(true);
        barraProgreso.setTitle("Consultando Información...");
        //barraProgreso.setMessage("Cargando sucursales...");
        //barraProgreso.setIndeterminate(true);
        barraProgreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        barraProgreso.show();
        // --------------------- End Barra de progreso ---------------------------------

        JsonObjectRequest jsonObject = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.w("TAG", "RESPONSE: " + response.toString());
                        Sucursal sucTemp = null;
                        try {
                            JSONArray arraySucursal = response.getJSONArray("items");
                            Log.w("TAG", "RESPONSE SUC: " + arraySucursal.toString());
                            for (int i = 0; i < arraySucursal.length(); i++) {
                                JSONObject jsonObject = arraySucursal.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String nombre = jsonObject.getString("nombre");
                                String direccion = jsonObject.getString("direccion");
                                double latitud = jsonObject.getDouble("latitud");
                                double longitud = jsonObject.getDouble("longitud");
                                String imagen = jsonObject.getString("imagen");
                                sucTemp = new Sucursal(id, nombre, direccion, latitud, longitud, imagen);
                                sucursales.add(sucTemp);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AdaptadorSucursales adapter = new AdaptadorSucursales(sucursales);

                        adapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Sucursal s = sucursales.get(rcvSucursales.getChildAdapterPosition(view));
                                //editarSucursal(s);
                            }
                        });
                        rcvSucursales.setAdapter(adapter);
                        //textView.setText("Response: " + response.toString());
                        barraProgreso.cancel();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.w("TAG_ERR", "" + error.toString());

                        barraProgreso.cancel();
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObject);
    }


    //----------------------end JsonArray----------------------------------------------




    // -----------------------------------------------------

//    public class ConsultarSucursales extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            barraProgreso.show();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            try {
//                sucursales = new ArrayList<>();
//
//                final MyOpenHelper dataBase = new MyOpenHelper(SucursalesActivity.this);
//                final SQLiteDatabase db = dataBase.getReadableDatabase();
//
//                Cursor c = dataBase.leerSucursales(db);
//                Sucursal sucTemp = null;
//
//                while (c.moveToNext()){
//                    @SuppressLint("Range") int id = c.getInt(c.getColumnIndex(DBLocal.TB_SUCURSALES.ID));
//                    @SuppressLint("Range") String nombre = c.getString(c.getColumnIndex(DBLocal.TB_SUCURSALES.NOMBRE));
//                    @SuppressLint("Range") String direccion = c.getString(c.getColumnIndex(DBLocal.TB_SUCURSALES.DIRECCION));
//                    @SuppressLint("Range") double latitud = c.getDouble(c.getColumnIndex(DBLocal.TB_SUCURSALES.LATITUD));
//                    @SuppressLint("Range") double longitud = c.getDouble(c.getColumnIndex(DBLocal.TB_SUCURSALES.LONGITUD));
//                    @SuppressLint("Range") int imagen = c.getInt(c.getColumnIndex(DBLocal.TB_SUCURSALES.IMAGEN));
//
//                    sucTemp = new Sucursal(id, nombre, direccion, latitud, longitud, imagen);
//                    sucursales.add(sucTemp);
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            barraProgreso.cancel();
//
//            for(Sucursal s: sucursales){
//                Log.e("TAG_PRO", "SUCURSAL: " +s.getNombre());
//            }
//
//            AdaptadorSucursales adapSuc = new AdaptadorSucursales(sucursales);
//            adapSuc.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Sucursal s = sucursales.get(rcvSucursales.getChildAdapterPosition(view));
//
//                    Intent intent = new Intent(SucursalesActivity.this, MapActivity.class);
//                    intent.putExtra("nombre", s.getNombre());
//                    intent.putExtra("latitud", s.getLatitud());
//                    intent.putExtra("longitud", s.getLongitud());
//                    startActivity(intent);
//
//                    //Toast.makeText(getApplicationContext(), "Sucursal: " + s.getNombre(), Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            rcvSucursales.setAdapter(adapSuc);
//
//
//        }
//    }
}
