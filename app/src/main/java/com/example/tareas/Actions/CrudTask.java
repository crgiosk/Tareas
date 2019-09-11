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

    public String SaveTask(String user, String subject, String description, String points, String entrega,String id,String action) {
        try {
            if (action.toLowerCase().equals("update")) {
                //Actualizar
                sql = "UPDATE " + Utilidades.nameTables[1] + " SET " +
                        Utilidades.nameFieldsTableTask[1] + "='" + subject +" ', "+
                        Utilidades.nameFieldsTableTask[2] + "='" + description +" ', "+
                        Utilidades.nameFieldsTableTask[3] + "='" + points +" ', "+
                        Utilidades.nameFieldsTableTask[4] + "='" + entrega +" ' " +
                        "WHERE rowid='"+id+"';";

                        Log.e("r   ", "sql : " + sql.toUpperCase());
            } else if (action.toLowerCase().equals("save")) {
                sql = "INSERT INTO " + Utilidades.nameTables[1] + " (" +
                        Utilidades.nameFieldsTableTask[0] + "," +
                        Utilidades.nameFieldsTableTask[1] + "," +
                        Utilidades.nameFieldsTableTask[2] + "," +
                        Utilidades.nameFieldsTableTask[3] + "," +
                        Utilidades.nameFieldsTableTask[4] + ")   VALUES ('" +
                        user + "' ,'" + subject + "' ,'" + description + "' ,'" + points + "' ,'" + entrega + "');";

                Log.e("r   ", "sql : " + sql.toUpperCase());
            } else {
                Toast.makeText(context, "Elija una opcion valida", Toast.LENGTH_LONG).show();
                return "fail";
            }

            return String.valueOf(insert(sql));
        } catch (SQLiteException ex) {
            //Toast.makeText(context, "Error al ingresar los datos " + ex.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("e   ", ex.getMessage().toUpperCase());
            return "fail";
        }
    }

    public Cursor showTasks(String user) {
        sql = "SELECT " + Utilidades.nameTables[1] + ".*,rowid FROM " + Utilidades.nameTables[1] + " WHERE " + Utilidades.nameFieldsTableTask[0] + "='" + user + "';";
        // " WHERE " + Utilidades.nameFieldsTableTask[0] + "='" + user + "';show tables;"
        Log.e("TESTINGShowtask    ", sql);
        return consult(sql);

    }


}
