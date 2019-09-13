package com.example.tareas.Vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tareas.Actions.CrudTask;
import com.example.tareas.MainActivity;
import com.example.tareas.R;
import com.example.tareas.Utilidades.Task;
import com.example.tareas.Utilidades.Utilidades;

import java.util.ArrayList;

public class Tasks extends AppCompatActivity {
    protected ListView listView_Tasks;
    private ArrayList<Task> ListaTareas;
    private ArrayList<String> ListaInfo;
    private Task classTask;
    private Intent intent;
    private SharedPreferences preferences;

    protected String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);



        if (exisPreferences()) {
            this.finish();
            intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        setValues();

        tex();
        listView_Tasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectTask(i);
            }
        });

        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.mipmap.ic_toolbar);
    }

    private boolean exisPreferences() {
        preferences = getSharedPreferences("Data" + Utilidades.nameTables[0], Context.MODE_PRIVATE);
        user = preferences.getString(Utilidades.nameTables[0], null);

        return user == null;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuoverflow, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.idItemNewTask) {
            newTask();
        } else if (id == R.id.itemCerrarSesion) {
            this.finish();
            this.getSharedPreferences("Data" + Utilidades.nameTables[0], 0).edit().clear().commit();
            intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Toast.makeText(this, "See you later " + user.toUpperCase(), Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectTask(int row) {
        intent = new Intent(this, NewTask.class);
        intent.putExtra("user_log", user);
        intent.putExtra("action", "Update");
        intent.putExtra("object", ListaTareas.get(row).getObject());
        intent.putExtra("description", ListaTareas.get(row).getDescription());
        intent.putExtra("points", ListaTareas.get(row).getPoints());
        intent.putExtra("delivery", ListaTareas.get(row).getDelivery());
        intent.putExtra("idTask", String.valueOf(ListaTareas.get(row).getId()));

        Log.e("id TAREA ", String.valueOf(ListaTareas.get(row).getId()));
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }

    private void newTask() {
        intent = new Intent(this, NewTask.class);
        intent.putExtra("user_log", user);
        intent.putExtra("action", "Save");
        startActivity(intent);
    }

    private void tex() {
        try {
            preferences = getSharedPreferences("Data" + Utilidades.nameTables[0], Context.MODE_PRIVATE);

            //preferences.edit().clear();
            user = preferences.getString(Utilidades.nameTables[0], null);

            CrudTask crudTask = new CrudTask(this);
            Cursor tareas = crudTask.showTasks(user);
            Log.e("CuentaDeFilas  ", "  getCount: " + tareas.getCount());

            //muestra daots por consola; ordenar
            if (tareas.getCount() != 0) {
                classTask = null;
                ListaTareas = new ArrayList<Task>();

                for (tareas.moveToFirst(); !tareas.isAfterLast(); tareas.moveToNext()) {
                    classTask = new Task();
                    classTask.setId(tareas.getInt(5));
                    classTask.setObject(tareas.getString(1));
                    classTask.setDescription(tareas.getString(2));
                    classTask.setPoints(tareas.getString(3));
                    classTask.setDelivery(tareas.getString(4));

                    ListaTareas.add(classTask);

                    //Mensajes por consola guias en Logcat
                    Log.e("0  ", "  result  ;" + tareas.getString(0));
                    Log.e("1  ", "  result  ;" + tareas.getString(1));
                    Log.e("2  ", "  result  ;" + tareas.getString(2));
                    Log.e("3  ", "  result  ;" + tareas.getString(3));
                    Log.e("4  ", "  result  ;" + tareas.getString(4));
                    Log.e("supuestoID  ", "  testiiing  ;" + tareas.getString(5));
                }
                tareas.close();
                getLista();
                ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ListaInfo);
                listView_Tasks.setAdapter(adp);

            } else {
                Toast.makeText(this, "No task, Try create  it.", Toast.LENGTH_LONG).show();
                return;
            }
        } catch (SQLiteException e) {
            Toast.makeText(this, e.getMessage().toUpperCase(), Toast.LENGTH_LONG).show();
            return;
        }

    }

    private void getLista() {
        ListaInfo = new ArrayList<String>();
        for (int i = 0; i < ListaTareas.size(); i++) {
            ListaInfo.add("\nAsunto: " + ListaTareas.get(i).getObject() + "\nDescripcion: " + ListaTareas.get(i).getDescription() +
                    "\nPuntos: " + ListaTareas.get(i).getPoints() + "\nEntrega: " + ListaTareas.get(i).getDelivery() + "\n");
        }
    }

    private void setValues() {
        listView_Tasks = findViewById(R.id.listViewTasks);
    }
}
