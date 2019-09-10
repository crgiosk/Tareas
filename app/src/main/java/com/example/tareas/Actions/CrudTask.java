package com.example.tareas.Actions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tareas.Utilidades.Task;
import com.example.tareas.Utilidades.Utilidades;

import java.util.ArrayList;

public class CrudTask extends Actions {
    private ArrayList<String> Listinfo;
    private ArrayList<Task> ListaTareas;
    private Context context;
    private ContentValues values;
    private String sql;

    /**
     * Clase que contiene los metodos para insertar, actualizar o borrar datos de la bd
     *
     * @param context
     */
    public CrudTask(Context context) {
        super(context);
        this.context = context;
    }

    public String SaveTask(String user, String subject, String description, String points, String entrega) {

        try {
        /*
            values = new ContentValues();
            values.put("user", user);
            values.put(Utilidades.nameFieldsTableTask[1], subject);
            values.put(Utilidades.nameFieldsTableTask[2], description);
            values.put(Utilidades.nameFieldsTableTask[3], points);
            values.put(Utilidades.nameFieldsTableTask[4], entrega);

         */
            sql = "INSERT INTO task VALUES ('" + user + "' ,'" + subject + "' ,'" + description + "' ,'" + points + "' ,'" + entrega + "')";
            Toast.makeText(context, "sql : " + insert(sql), Toast.LENGTH_LONG).show();
            return "success";
        } catch (SQLiteException ex) {
            Toast.makeText(context, "Error al ingresar los datos " + ex.getMessage(), Toast.LENGTH_LONG).show();
            return "fail";
        }

    }

    public Cursor showTasks(String user) {
        sql = "SELECT * FROM " + Utilidades.nameTables[1]+";" ;
        // " WHERE " + Utilidades.nameFieldsTableTask[0] + "='" + user + "';show tables;"
        Log.e("TESTING     ",sql);
        return consult(sql);

    }


}
