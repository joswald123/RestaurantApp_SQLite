package usa.sesion1.restaurantappreto3.controller;

import android.provider.BaseColumns;

public class DBLocal {

    public static final String DATABASE_NAME = "productos.db";
    public static final String TABLE_PRODUCTOS = "productos";
    public static final String TABLE_SUCURSAL = "sucursales";

    public static class TB_PRODUCTOS implements BaseColumns {
        public  static String ID = "id";
        public static String NOMBRE = "nombre";
        public static String PRECIO = "precio";
        public static String IMAGEN = "imagen";
        public static String FAVORITO = "favorito";
    }

    public static class TB_SUCURSALES implements BaseColumns {
        public  static String ID = "id";
        public static String NOMBRE = "nombre";
        public static String DIRECCION = "direccion";
        public static String LATITUD = "latitud";
        public static String LONGITUD = "longitud";
        public static String IMAGEN = "imagen";
    }
}