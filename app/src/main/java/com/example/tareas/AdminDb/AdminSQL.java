package com.example.tareas.AdminDb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import com.example.tareas.Utilidades.Utilidades;

public class AdminSQL extends SQLiteOpenHelper {
    private Context context;
    private SQLiteDatabase myDb;

    public AdminSQL(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase myDb) {
        this.myDb=myDb;
        try {
            crearTablas();
        }catch (SQLiteException ex){
            Toast.makeText(context,"Error al crear las tablas\n"+ex.getMessage(),Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
        eliminarTablas();
        crearTablas();
    }

    private void crearTablas(){
        myDb.execSQL(Utilidades.createTableUser);
        myDb.execSQL(Utilidades.createTableTask);
        Log.e("table user ",Utilidades.createTableUser.toUpperCase());
        Log.e("table task ",Utilidades.createTableTask.toUpperCase());
    }

    private void eliminarTablas(){
        myDb.execSQL(Utilidades.deleteTableUser);
        myDb.execSQL(Utilidades.deleteTableTask);
        Log.e("del table user ",Utilidades.createTableUser.toUpperCase());
        Log.e("del table task ",Utilidades.createTableTask.toUpperCase());
    }
}
