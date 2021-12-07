package usa.sesion1.restaurantappreto3.controller;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ProductoController {

    public static void anadirFavorito(int id, MyOpenHelper dataBase, SQLiteDatabase db){

        try {
            dataBase.seleccionarFavorito(id, db);
            Log.e("TAG_NOMBRE", "Se ha agregado su orden!");

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            dataBase.close();
        }
    }

    public static void eliminarFavorito(int id, MyOpenHelper dataBase, SQLiteDatabase db){

        try {
            dataBase.eliminarFavorito(id, db);
            Log.e("TAG_NOMBRE", "Se ha eliminado la orden");

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            dataBase.close();
        }
    }
}