package usa.sesion1.restaurantappreto3;

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
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import usa.sesion1.restaurantappreto3.controller.AdaptadorProductos;
import usa.sesion1.restaurantappreto3.controller.DBLocal;
import usa.sesion1.restaurantappreto3.controller.MyOpenHelper;
import usa.sesion1.restaurantappreto3.controller.ProductoController;
import usa.sesion1.restaurantappreto3.model.Producto;
import usa.sesion1.restaurantappreto3.view.MenuActivity;

public class FavoritosActivity extends AppCompatActivity {

    ListView lvwFavoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menudeopciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu:
                Intent intent = new Intent(FavoritosActivity.this, MenuActivity.class);
                startActivity(intent);
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
            @SuppressLint("Range") int precio = c.getInt(c.getColumnIndex(DBLocal.TB_PRODUCTOS.PRECIO));
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