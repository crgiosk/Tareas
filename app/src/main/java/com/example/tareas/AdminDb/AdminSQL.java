package com.example.tareas.AdminDb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import com.example.tareas.Utilidades.Utilidades;

public class AdminSQL extends SQLiteOpenHelper {
    private Context context;

    public AdminSQL(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase myDb) {
        try {
            myDb.execSQL(Utilidades.createTableUser);
            myDb.execSQL(Utilidades.createTableTask);
        }catch (SQLiteException ex){
            Toast.makeText(context,"Error al crear las tablas\n"+ex.getMessage(),Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
