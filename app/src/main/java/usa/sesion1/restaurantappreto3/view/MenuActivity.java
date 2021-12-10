package usa.sesion1.restaurantappreto3.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import usa.sesion1.restaurantappreto3.FavoritosActivity;
import usa.sesion1.restaurantappreto3.R;
import usa.sesion1.restaurantappreto3.controller.AdaptadorProductos;
import usa.sesion1.restaurantappreto3.controller.DBLocal;
import usa.sesion1.restaurantappreto3.controller.MyOpenHelper;
import usa.sesion1.restaurantappreto3.controller.ProductoController;
import usa.sesion1.restaurantappreto3.model.Producto;

public class MenuActivity extends AppCompatActivity {

    ListView listaProductos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //-------- LOGO ----
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setLogo(R.mipmap.ic_chef);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        // ----------------------

        listaProductos = (ListView)findViewById(R.id.lvwProductos);

        ArrayList<Producto> productos = consultarProductos(this);

        AdaptadorProductos adaptadorProductos = new AdaptadorProductos(getApplicationContext(), productos);
        listaProductos.setAdapter(adaptadorProductos);

        listaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                Producto p = (Producto) adapterView.getItemAtPosition(posicion);
                dialogo(p.getId());
                /*
                Intent intent = new Intent(MainActivity.this, DetalleProductoActivity.class);
                intent.putExtra("id", p.getId());
                intent.putExtra("nombre", p.getNombre());
                intent.putExtra("precio", p.getPrecio());
                startActivity(intent);

                 */

            }
        });

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
                Intent favoritos = new Intent(MenuActivity.this, FavoritosActivity.class);
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
                Intent servicios = new Intent(MenuActivity.this, ServiciosActivity.class);
                startActivity(servicios);
                return  true;
            case R.id.sucursales:
                Intent sucursales = new Intent(this, SucursalesActivity.class);
                startActivity(sucursales);
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Consultar productos desde la base de datos ------
    public ArrayList<Producto> consultarProductos(Context context){
        ArrayList<Producto> productos = new ArrayList<>();

        final MyOpenHelper dataBase = new MyOpenHelper(MenuActivity.this);
        final SQLiteDatabase db = dataBase.getReadableDatabase();

        Cursor c = dataBase.leerProductos(db);

        while (c.moveToNext()){
            @SuppressLint("Range") int id = c.getInt(c.getColumnIndex(DBLocal.TB_PRODUCTOS.ID));
            @SuppressLint("Range") String nombre = c.getString(c.getColumnIndex(DBLocal.TB_PRODUCTOS.NOMBRE));
            @SuppressLint("Range") int precio = c.getInt(c.getColumnIndex(DBLocal.TB_PRODUCTOS.PRECIO));
            @SuppressLint("Range") int imagen = c.getInt(c.getColumnIndex(DBLocal.TB_PRODUCTOS.IMAGEN));
            @SuppressLint("Range") int favorito = c.getInt(c.getColumnIndex(DBLocal.TB_PRODUCTOS.FAVORITO));

            if (favorito == 1){
                productos.add(new Producto(id, nombre, precio, imagen, true));
            }else{
                productos.add(new Producto(id, nombre, precio, imagen, false));
            }

        }
        return productos;
    }

    // --------------------------- Metotodo para lanzar Dialogos ------------------------------------------
    private void dialogo(int id){
        final MyOpenHelper dataBase = new MyOpenHelper(MenuActivity.this);
        final SQLiteDatabase db = dataBase.getWritableDatabase();

        DialogConfirmation dc = new DialogConfirmation();
        dc.setParametros(id, dataBase, db);
        dc.show(getSupportFragmentManager(), "DialogoDeConfirmacion");
    }

    public static class DialogConfirmation extends DialogFragment {

        int idProducto;
        MyOpenHelper dataBase;
        SQLiteDatabase db;

        public void setParametros(int idProducto, MyOpenHelper dataBase, SQLiteDatabase db){
            this.idProducto = idProducto;
            this.dataBase = dataBase;
            this.db = db;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("¿Usted está seguro de agregar esta orden a favoritos?")
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Update
                            ProductoController.anadirFavorito(idProducto, dataBase, db);
                        }
                    })
                    .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getActivity(), "Usted ha cancelado la operación", Toast.LENGTH_LONG).show();
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}