package usa.sesion1.restaurantappreto3.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

import usa.sesion1.restaurantappreto3.FavoritosActivity;
import usa.sesion1.restaurantappreto3.R;
import usa.sesion1.restaurantappreto3.SucursalesActivity;

public class MapActivity extends AppCompatActivity {

    private MapView myOpenMapView;
    private MapController myMapController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Bundle bundle = getIntent().getExtras();
        String nombre = bundle.getString("nombre");
        double latitud = bundle.getDouble("latitud");
        double longitud = bundle.getDouble("longitud");

        GeoPoint puntoReferente = new GeoPoint(latitud, longitud);

        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        myOpenMapView = (MapView) findViewById(R.id.mapview);
        myOpenMapView.setBuiltInZoomControls(true);
        myMapController = (MapController) myOpenMapView.getController();
        myMapController.setCenter(puntoReferente);
        myMapController.setZoom(16);

        myOpenMapView.setMultiTouchControls(true);

        final MyLocationNewOverlay myLocationoverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getApplicationContext()), myOpenMapView);
        myOpenMapView.getOverlays().add(myLocationoverlay); //No añadir si no quieres una marca
        myLocationoverlay.enableMyLocation();
        myLocationoverlay.runOnFirstFix(new Runnable() {
            public void run() {
                myMapController.animateTo(myLocationoverlay.getMyLocation());
            }
        });

        ArrayList<OverlayItem> puntos = new ArrayList<OverlayItem>();
        puntos.add(new OverlayItem("Bogotá", nombre, puntoReferente));

        ItemizedIconOverlay.OnItemGestureListener<OverlayItem> tap = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemLongPress(int arg0, OverlayItem arg1) {
                return false;
            }
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return true;
            }
        };

        ItemizedOverlayWithFocus<OverlayItem> capa = new ItemizedOverlayWithFocus<OverlayItem>(this, puntos, tap);
        capa.setFocusItemsOnTap(true);
        myOpenMapView.getOverlays().add(capa);
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
                Intent favoritos = new Intent(MapActivity.this, FavoritosActivity.class);
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
                Intent servicios = new Intent(MapActivity.this, ServiciosActivity.class);
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
    // -_____________________________________________________________
}
