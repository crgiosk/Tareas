package com.example.tareas.Actions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.example.tareas.Utilidades.Utilidades;
import com.example.tareas.AdminDb.AdminSQL;


public class Actions {
    private AdminSQL admind;
    private SQLiteDatabase db;
    private Context context;

    /**
     * Clase que contiene los metodos para insertar, actualizar o borrar datos de la bd
     */
    public Actions(Context context) {
        this.context = context;
        setConexion();
    }

    private void setConexion() {
        try {
            admind = new AdminSQL(context, Utilidades.nameDatabase, null, Utilidades.versionDatabase);
            db = admind.getWritableDatabase();
        } catch (SQLiteException ex) {
            showAlerts("Error al conectar con la base de datos.\n" + ex.getMessage());
            return;
        }
    }

    protected long save(String nameTable, ContentValues values) {
        try {
            return db.insert(nameTable, null, values);
        } catch (SQLiteException ex) {
            showAlerts("Error al insertar los datos.\n" + ex.getMessage());
            return 666;
        }
    }

    protected Cursor consult(String sql) {
        try {
            return db.rawQuery(sql, null);
        } catch (SQLiteException ex) {
            showAlerts("Error en su consulta " + ex.getMessage());
            return null;
        }
    }

    protected void showAlerts(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    protected boolean insert(String sql) {
        try {
            db.execSQL(sql);
            return true;
        } catch (SQLiteException ex) {
            showAlerts("Error en su consulta " + ex.getMessage());
            return false;
        }
    }


    public void closeConexion() {
        this.db.close();
    }


}
