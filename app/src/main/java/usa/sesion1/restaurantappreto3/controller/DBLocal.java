package usa.sesion1.restaurantappreto3.controller;

import android.provider.BaseColumns;

public class DBLocal {

    public static final String DATABASE_NAME = "productos.db";
    public static final String TABLE_PRODUCTOS = "productos";

    public static class TB_PRODUCTOS implements BaseColumns {
        public  static String ID = "id";
        public static String NOMBRE = "nombre";
        public static String PRECIO = "precio";
        public static String IMAGEN = "imagen";
        public static String FAVORITO = "favorito";
    }
}