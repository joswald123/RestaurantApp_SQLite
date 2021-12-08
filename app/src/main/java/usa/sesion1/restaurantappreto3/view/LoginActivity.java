package usa.sesion1.restaurantappreto3.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
}
