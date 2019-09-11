package com.example.tareas.Vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tareas.Actions.CrudTask;
import com.example.tareas.R;
import com.example.tareas.Utilidades.Task;

import java.util.ArrayList;

public class Tasks extends AppCompatActivity {
    private Button button;
    protected ListView listView_Tasks;
    private ArrayList<Task> ListaTareas;
    private ArrayList<String> ListaInfo;
    private Task classTask;
    private Intent newtask;

    protected String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(R.string.TittleTasks);
        setContentView(R.layout.activity_tasks);
        setValues();
        tex();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTask();
            }
        });
        listView_Tasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectTask(i);
            }
        });

    }

    private void selectTask(int row){
        //Toast.makeText(this,"Clickeo una tarea id tarea es: "+ListaTareas.get(row).getId(),Toast.LENGTH_LONG).show();
        //Toast.makeText(this,"La tarea contiene "+ListaTareas.get(row).getPoints(),Toast.LENGTH_LONG).show();
        newtask= new Intent(this, NewTask.class);
        newtask.putExtra("user_log", user);
        newtask.putExtra("action","Update");
        newtask.putExtra("object",ListaTareas.get(row).getObject());
        newtask.putExtra("description",ListaTareas.get(row).getDescription());
        newtask.putExtra("points",ListaTareas.get(row).getPoints());
        newtask.putExtra("delivery",ListaTareas.get(row).getDelivery());
        startActivity(newtask);
    }

    private void newTask() {
         newtask= new Intent(this, NewTask.class);
        newtask.putExtra("user_log", user);
        newtask.putExtra("action","Save");
        startActivity(newtask);
    }

    private void tex() {
        try {

            CrudTask crudTask = new CrudTask(this);
            Cursor tareas = crudTask.showTasks(user);
            Log.e("CuentaDeFilas  ", "  getCount: " + tareas.getCount());

            //muestra daots por consola; ordenar
            if (tareas.getCount() != 0) {
                classTask = null;
                ListaTareas = new ArrayList<Task>();

                for (tareas.moveToFirst(); !tareas.isAfterLast(); tareas.moveToNext()) {
                    classTask = new Task();
                    classTask.setId(tareas.getString(5));
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
                ArrayAdapter adp=new ArrayAdapter(this,android.R.layout.simple_list_item_1,ListaInfo);
                listView_Tasks.setAdapter(adp);

            } else {
                Toast.makeText(this, "No se encontraron tareas. Registre una.", Toast.LENGTH_LONG).show();
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
            ListaInfo.add("\nAsunto: "+ListaTareas.get(i).getObject() + "\nDescripcion: " + ListaTareas.get(i).getDescription() +
                          "\nPuntos: " + ListaTareas.get(i).getPoints() + "\nEntrega: " + ListaTareas.get(i).getDelivery()+"\n");
        }
    }


    private void setValues() {
        listView_Tasks = findViewById(R.id.listViewTasks);
        button = findViewById(R.id.buttonNewTasActivity);
        user = getIntent().getStringExtra("user_log");

    }
}
