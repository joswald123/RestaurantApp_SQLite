package usa.sesion1.restaurantappreto3.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import usa.sesion1.restaurantappreto3.FavoritosActivity;
import usa.sesion1.restaurantappreto3.R;

public class ServiciosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        //-------- LOGO ----
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setLogo(R.mipmap.ic_chef);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        // ----------------------
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
                Intent favoritos = new Intent(ServiciosActivity.this, FavoritosActivity.class);
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
                Intent servicios = new Intent(ServiciosActivity.this, ServiciosActivity.class);
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
}
