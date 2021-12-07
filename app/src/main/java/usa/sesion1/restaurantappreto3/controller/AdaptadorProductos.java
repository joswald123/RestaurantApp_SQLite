package usa.sesion1.restaurantappreto3.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import usa.sesion1.restaurantappreto3.R;
import usa.sesion1.restaurantappreto3.model.Producto;

public class AdaptadorProductos extends BaseAdapter {

    Context context;
    ArrayList<Producto> productos;

    public AdaptadorProductos(Context context, ArrayList<Producto> productos) {
        this.context = context;
        this.productos = productos;
    }

    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Object getItem(int posicion) {
        return productos.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return productos.get(posicion).getId();
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
        View vista = view;

        LayoutInflater inflater = LayoutInflater.from(context);
        vista = inflater.inflate(R.layout.item_de_producto, null);

        ImageView itemImagen = (ImageView) vista.findViewById(R.id.itemImage);
        TextView itemNombre = (TextView) vista.findViewById(R.id.itemnombre);
        TextView itemPrecio = (TextView) vista.findViewById(R.id.itemPrecio);

        itemImagen.setImageResource(productos.get(posicion).getImagen());
        itemNombre.setText(productos.get(posicion).getNombre());
        itemPrecio.setText("" + productos.get(posicion).getPrecio());

        return vista;
    }
}