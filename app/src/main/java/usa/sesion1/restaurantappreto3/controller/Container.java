package usa.sesion1.restaurantappreto3.controller;

import java.util.ArrayList;

import usa.sesion1.restaurantappreto3.R;
import usa.sesion1.restaurantappreto3.model.Producto;

public class Container {

    public static ArrayList<Producto> iniciarProductos = getProductos();

    public static ArrayList<Producto> getProductos(){
        ArrayList<Producto> productos = new ArrayList<>();
        productos.add(new Producto("Cassoulet", 27000, R.drawable.cassoulet));
        productos.add(new Producto("Gratén Delfinés", 32500, R.drawable.graten));
        productos.add(new Producto("Ratatouille", 19800, R.drawable.ratatouille));
        productos.add(new Producto("Bullabesa", 35000, R.drawable.bullabesa));
        productos.add(new Producto("La Mouclade de Charente", 39500, R.drawable.mouclade));

        return productos;

    }
}
