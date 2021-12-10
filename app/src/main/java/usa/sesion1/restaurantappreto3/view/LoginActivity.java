package usa.sesion1.restaurantappreto3.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import usa.sesion1.restaurantappreto3.FavoritosActivity;
import usa.sesion1.restaurantappreto3.R;

public class LoginActivity extends AppCompatActivity {
    //private TextView tvwMenu;
    private EditText edtUsuario;
    private EditText edtClave;
    private Button btnLogin;

    private Button btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //-------- LOGO ----
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setLogo(R.mipmap.ic_chef);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        // ----------------------

        //tvwMenu =(TextView)findViewById(tvwMenu);
        edtUsuario =(EditText)findViewById(R.id.edtUsuario);
        edtClave =(EditText)findViewById(R.id.edtClave);
        btnLogin =(Button) findViewById(R.id.btnLogin);

        btnRegistro =(Button)findViewById(R.id.btnRegistro);

        btnLogin.setOnClickListener(new View.OnClickListener() {
             @Override
                public void onClick(View v) {
                    String usuario = edtUsuario.getText().toString();
                    String clave = edtClave.getText().toString();
                    Toast.makeText(getApplicationContext(), "Usuario: "+ usuario + " - Clave: " + clave, Toast.LENGTH_SHORT).show();
            }
        });
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Pronto podrás registrarte", Toast.LENGTH_SHORT).show();

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
                Intent favoritos = new Intent(LoginActivity.this, FavoritosActivity.class);
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
                Intent servicios = new Intent(LoginActivity.this, ServiciosActivity.class);
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
