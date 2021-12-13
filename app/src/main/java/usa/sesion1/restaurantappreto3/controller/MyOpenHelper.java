package usa.sesion1.restaurantappreto3.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import usa.sesion1.restaurantappreto3.model.Producto;
import usa.sesion1.restaurantappreto3.model.Sucursal;

public class MyOpenHelper extends SQLiteOpenHelper {

    public static int VERSION = 13;

    public MyOpenHelper(@Nullable Context context) {
        super(context, DBLocal.DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        crearTablaProductos(db);
        crearTablaSucursales(db);
        ArrayList <Producto> productos = Container.getProductos();
        for(Producto p : productos){
            insertarProducto(p, db);
        }

        ArrayList<Sucursal> sucursales = Container.getSucursales();
        for(Sucursal s:sucursales){
            insertarSucursales(s, db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + DBLocal.TABLE_PRODUCTOS);
        db.execSQL(" DROP TABLE IF EXISTS " + DBLocal.TABLE_SUCURSAL);
        onCreate(db);
    }

    public void crearTablaProductos(SQLiteDatabase db){
        db.execSQL("CREATE TABLE productos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, precio INTEGER, imagen INTEGER, favorito BOOLEAN)");
        //db.execSQL("CREATE TABLE " +DBLocal.TABLE_PRODUCTOS + " (" + DBLocal.TB_PRODUCTOS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DBLocal.TB_PRODUCTOS.ID +  " TEXT)");
    }

    //int id, String nombre, String direccion, double latitud, double longitud, int imagen
    public void crearTablaSucursales(SQLiteDatabase db){
        db.execSQL("CREATE TABLE sucursales " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, direccion TEXT, latitud REAL, longitud REAL, imagen INTEGER)");
    }

    public void insertarProducto(Producto p, SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put(DBLocal.TB_PRODUCTOS.NOMBRE, p.getNombre());
        cv.put(DBLocal.TB_PRODUCTOS.PRECIO, p.getPrecio());
        cv.put(DBLocal.TB_PRODUCTOS.IMAGEN, p.getImagen());
        cv.put(DBLocal.TB_PRODUCTOS.FAVORITO, p.isFavorito());

        db.insert(DBLocal.TABLE_PRODUCTOS, null, cv);
    }

    public void insertarSucursales(Sucursal s, SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put(DBLocal.TB_SUCURSALES.NOMBRE, s.getNombre());
        cv.put(DBLocal.TB_SUCURSALES.DIRECCION, s.getDireccion());
        cv.put(DBLocal.TB_SUCURSALES.LATITUD, s.getLatitud());
        cv.put(DBLocal.TB_SUCURSALES.LONGITUD, s.getLongitud());
        cv.put(DBLocal.TB_SUCURSALES.IMAGEN, s.getImagen());
        db.insert(DBLocal.TABLE_SUCURSAL, null, cv);
    }

    public void seleccionarFavorito(int id, SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put(DBLocal.TB_PRODUCTOS.FAVORITO, true);
        cv.put(DBLocal.TB_PRODUCTOS.ID, id);
        String[] arg = new String[]{""+id};
        db.update(DBLocal.TABLE_PRODUCTOS, cv, "id=?", arg);
        //db.insert(DBLocal.TABLE_PRODUCTOS, null, cv);
    }

    public void eliminarFavorito(int id, SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put(DBLocal.TB_PRODUCTOS.FAVORITO, false);
        cv.put(DBLocal.TB_PRODUCTOS.ID, id);
        String[] arg = new String[]{""+id};
        db.update(DBLocal.TABLE_PRODUCTOS, cv,"id=?", arg);
        //db.insert(DBLocal.TABLE_PRODUCTOS, null, cv);
    }

    public Cursor leerProductos(SQLiteDatabase db){
        return db.query(DBLocal.TABLE_PRODUCTOS, null, null, null, null, null, null);
    }

    public Cursor leerSucursales(SQLiteDatabase db){
        return db.query(DBLocal.TABLE_SUCURSAL, null, null, null, null, null, null);
    }

    public Cursor leerFavoritos(SQLiteDatabase db){
        return db.rawQuery("SELECT * FROM productos WHERE favorito = true", null);
    }


}

