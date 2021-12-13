package usa.sesion1.restaurantappreto3;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import usa.sesion1.restaurantappreto3.controller.AdaptadorSucursales;
import usa.sesion1.restaurantappreto3.controller.DBLocal;
import usa.sesion1.restaurantappreto3.controller.MyOpenHelper;
import usa.sesion1.restaurantappreto3.model.Sucursal;
import usa.sesion1.restaurantappreto3.view.MapActivity;

public class SucursalesActivity extends AppCompatActivity {

    RecyclerView rcvSucursales;
    ArrayList<Sucursal> sucursales;
    ProgressDialog barraProgreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucursales);

        rcvSucursales = (RecyclerView) findViewById(R.id.rcvSucursales);
        rcvSucursales.setLayoutManager(new LinearLayoutManager(this));

        barraProgreso = new ProgressDialog(SucursalesActivity.this);
        barraProgreso.setTitle("Consultando Información");
        barraProgreso.setMessage("Cargando sucursales...");
        barraProgreso.setIndeterminate(true);
        barraProgreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        new ConsultarSucursales().execute();

    }

    public class ConsultarSucursales extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            barraProgreso.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                sucursales = new ArrayList<>();

                final MyOpenHelper dataBase = new MyOpenHelper(SucursalesActivity.this);
                final SQLiteDatabase db = dataBase.getReadableDatabase();

                Cursor c = dataBase.leerSucursales(db);
                Sucursal sucTemp = null;

                while (c.moveToNext()){
                    @SuppressLint("Range") int id = c.getInt(c.getColumnIndex(DBLocal.TB_SUCURSALES.ID));
                    @SuppressLint("Range") String nombre = c.getString(c.getColumnIndex(DBLocal.TB_SUCURSALES.NOMBRE));
                    @SuppressLint("Range") String direccion = c.getString(c.getColumnIndex(DBLocal.TB_SUCURSALES.DIRECCION));
                    @SuppressLint("Range") double latitud = c.getDouble(c.getColumnIndex(DBLocal.TB_SUCURSALES.LATITUD));
                    @SuppressLint("Range") double longitud = c.getDouble(c.getColumnIndex(DBLocal.TB_SUCURSALES.LONGITUD));
                    @SuppressLint("Range") int imagen = c.getInt(c.getColumnIndex(DBLocal.TB_SUCURSALES.IMAGEN));

                    sucTemp = new Sucursal(id, nombre, direccion, latitud, longitud, imagen);
                    sucursales.add(sucTemp);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            barraProgreso.cancel();

            for(Sucursal s: sucursales){
                Log.e("TAG_PRO", "SUCURSAL: " +s.getNombre());
            }

            AdaptadorSucursales adapSuc = new AdaptadorSucursales(sucursales);
            adapSuc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Sucursal s = sucursales.get(rcvSucursales.getChildAdapterPosition(view));

                    Intent intent = new Intent(SucursalesActivity.this, MapActivity.class);
                    intent.putExtra("nombre", s.getNombre());
                    intent.putExtra("latitud", s.getLatitud());
                    intent.putExtra("longitud", s.getLongitud());
                    startActivity(intent);

                    //Toast.makeText(getApplicationContext(), "Sucursal: " + s.getNombre(), Toast.LENGTH_SHORT).show();
                }
            });

            rcvSucursales.setAdapter(adapSuc);


        }
    }
}
