package usa.sesion1.restaurantappreto3.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import usa.sesion1.restaurantappreto3.model.Producto;

public class MyOpenHelper extends SQLiteOpenHelper {

    public static int VERSION = 9;

    public MyOpenHelper(@Nullable Context context) {
        super(context, DBLocal.DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        crearTablaProductos(db);
        ArrayList <Producto> productos = Container.getProductos();
        for(Producto p : productos){
            insertarProducto(p, db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + DBLocal.TABLE_PRODUCTOS);
        onCreate(db);
    }

    public void crearTablaProductos(SQLiteDatabase db){
        db.execSQL("CREATE TABLE productos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, precio INTEGER, imagen INTEGER, favorito BOOLEAN)");
        //db.execSQL("CREATE TABLE " +DBLocal.TABLE_PRODUCTOS + " (" + DBLocal.TB_PRODUCTOS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DBLocal.TB_PRODUCTOS.ID +  " TEXT)");
    }

    public void insertarProducto(Producto p, SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put(DBLocal.TB_PRODUCTOS.NOMBRE, p.getNombre());
        cv.put(DBLocal.TB_PRODUCTOS.PRECIO, p.getPrecio());
        cv.put(DBLocal.TB_PRODUCTOS.IMAGEN, p.getImagen());
        cv.put(DBLocal.TB_PRODUCTOS.FAVORITO, p.isFavorito());

        db.insert(DBLocal.TABLE_PRODUCTOS, null, cv);
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

    public Cursor leerFavoritos(SQLiteDatabase db){
        return db.rawQuery("SELECT * FROM productos WHERE favorito = true", null);
    }


}

