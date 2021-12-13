package usa.sesion1.restaurantappreto3;

import androidx.annotation.NonNull;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import usa.sesion1.restaurantappreto3.controller.AdaptadorProductos;
import usa.sesion1.restaurantappreto3.controller.DBLocal;
import usa.sesion1.restaurantappreto3.controller.MyOpenHelper;
import usa.sesion1.restaurantappreto3.controller.ProductoController;
import usa.sesion1.restaurantappreto3.model.Producto;
import usa.sesion1.restaurantappreto3.view.LoginActivity;
import usa.sesion1.restaurantappreto3.view.MenuActivity;
import usa.sesion1.restaurantappreto3.view.ServiciosActivity;

public class FavoritosActivity extends AppCompatActivity {

    ListView lvwFavoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        //-------- LOGO ----
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setLogo(R.mipmap.ic_chef);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        // ----------------------

        lvwFavoritos = (ListView)findViewById(R.id.lvwProductosFavoritos);

        ArrayList<Producto> productos = consultarProductosFavoritos(this);

        AdaptadorProductos adaptadorProductos = new AdaptadorProductos(getApplicationContext(), productos);
        lvwFavoritos.setAdapter(adaptadorProductos);

        lvwFavoritos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                Producto p = (Producto) adapterView.getItemAtPosition(posicion);
                dialogo(p.getId());


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
                Intent favoritos = new Intent(FavoritosActivity.this, FavoritosActivity.class);
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
                Intent servicios = new Intent(FavoritosActivity.this, ServiciosActivity.class);
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

    public ArrayList<Producto> consultarProductosFavoritos(Context context){
        ArrayList<Producto> productos = new ArrayList<>();

        final MyOpenHelper dataBase = new MyOpenHelper(FavoritosActivity.this);
        final SQLiteDatabase db = dataBase.getReadableDatabase();

        Cursor c = dataBase.leerFavoritos(db);

        while (c.moveToNext()){
            @SuppressLint("Range") int id = c.getInt(c.getColumnIndex(DBLocal.TB_PRODUCTOS.ID));
            @SuppressLint("Range") String nombre = c.getString(c.getColumnIndex(DBLocal.TB_PRODUCTOS.NOMBRE));
            @SuppressLint("Range") String precio = c.getString(c.getColumnIndex(DBLocal.TB_PRODUCTOS.PRECIO));
            @SuppressLint("Range") int imagen = c.getInt(c.getColumnIndex(DBLocal.TB_PRODUCTOS.IMAGEN));

            productos.add(new Producto(id, nombre, precio, imagen));


        }
        return productos;
    }

    // --------------------------- Metotodo para lanzar Dialogos ------------------------------------------

    private void dialogo(int id){
        final MyOpenHelper dataBase = new MyOpenHelper(FavoritosActivity.this);
        final SQLiteDatabase db = dataBase.getReadableDatabase();

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
            builder.setMessage("¿Usted está seguro de eliminar esta orden de su lista de favoritos?")
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Update
                            ProductoController.eliminarFavorito(idProducto, dataBase, db);
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