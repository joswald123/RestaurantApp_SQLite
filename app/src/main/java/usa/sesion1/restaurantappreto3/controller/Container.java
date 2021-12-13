package usa.sesion1.restaurantappreto3.controller;

import java.util.ArrayList;

import usa.sesion1.restaurantappreto3.R;
import usa.sesion1.restaurantappreto3.model.Producto;
import usa.sesion1.restaurantappreto3.model.Sucursal;

public class Container {

    public static ArrayList<Producto> iniciarProductos = getProductos();

    public static ArrayList<Producto> getProductos(){
        ArrayList<Producto> productos = new ArrayList<>();
        productos.add(new Producto("Cassoulet", "$27,000", R.drawable.cassoulet));
        productos.add(new Producto("Gratén Delfinés", "$32,500", R.drawable.graten));
        productos.add(new Producto("Ratatouille", "$19,800", R.drawable.ratatouille));
        productos.add(new Producto("Bullabesa", "$35,000", R.drawable.bullabesa));
        productos.add(new Producto("La Mouclade de Charente", "$39,500", R.drawable.mouclade));

        return productos;
    }

    //String nombre, String direccion, double latitud, double longitud, int imagen
    public static ArrayList<Sucursal> getSucursales(){
        ArrayList<Sucursal> sucursales = new ArrayList<>();
        sucursales.add(new Sucursal("Sede Norte", "Autopista norte #98-24", 4.755544297684242, -74.04486403659091, R.drawable.sucursal1));
        sucursales.add(new Sucursal("Sede Centro", "Calle 26 #28-16", 4.622001062256143, -74.07729417494562, R.drawable.sucursal2));
        sucursales.add(new Sucursal("Sede Sur", "Centro Comercial Centro Mayor, local 350", 4.593469427335188, -74.12385857828532, R.drawable.sucursal3));
        sucursales.add(new Sucursal("Sede Occidente", "Avenida Boyacá #54-17", 4.671511942139668, -74.10617398928935, R.drawable.sucursal4));

        return sucursales;
    }

}
