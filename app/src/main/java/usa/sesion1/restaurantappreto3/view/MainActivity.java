package usa.sesion1.restaurantappreto3.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import usa.sesion1.restaurantappreto3.R;

public class MainActivity extends AppCompatActivity {

    ProgressBar barra;
    ImageView splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        barra = (ProgressBar)findViewById(R.id.barraProgreso);
        barra.setProgress(0);

        hiloBarra.start();

        try{
            splash = (ImageView)findViewById(R.id.splash);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                }
            },5000);
        }catch(Exception e){
            Log.e("ERROR DE APP", e.getMessage());
            e.printStackTrace();
        }

    }

    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int progreso = msg.getData().getInt("progreso");
            int total = msg.getData().getInt("total");

            barra.setProgress((progreso * 100) / total);
        }
    };


    Thread hiloBarra = new Thread(){
        @Override
        public void run() {
            try {

                int total = 5;
                Bundle dato = new Bundle();
                for (int i = 1; i <= total; i++){
                    Thread.sleep(1000);

                    dato = new Bundle();
                    dato.putInt("progreso", i);
                    dato.putInt("total", total);


                    Message msg = myHandler.obtainMessage();
                    msg.setData(dato);
                    myHandler.sendMessage(msg);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                Log.e("TAG PROCESO", "LA TAREA SE EJECUTÓ DE MANERA EFECTIVA");
            }
        }
    };

}