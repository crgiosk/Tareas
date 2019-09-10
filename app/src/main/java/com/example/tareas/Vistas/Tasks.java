package com.example.tareas.Vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    ArrayList<Task> ListaTareas;
    ArrayList<String> ListaInfo;
    Task classTask;

    protected EditText editexUser;
    protected String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        setValues();
        tex();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTask();
            }
        });

    }

    private void newTask() {
        Intent newtask = new Intent(this, NewTask.class);
        newtask.putExtra("user_log", user);
        startActivity(newtask);
    }

    private void tex() {
        editexUser.setText(user);


        try {

            CrudTask crudTask = new CrudTask(this);
            Cursor tareas = crudTask.showTasks(user);
            Log.e("test  ", "  testiiing  ;" + tareas.getCount());

            //muestra daots por consola; ordenar
            if (tareas.getCount() != 0) {
                classTask = null;
                ListaTareas = new ArrayList<Task>();

                for (tareas.moveToFirst(); !tareas.isAfterLast(); tareas.moveToNext()) {
                    classTask = new Task();
                    classTask.setObject(tareas.getString(1));
                    classTask.setDescription(tareas.getString(2));
                    classTask.setPoints(tareas.getString(3));
                    classTask.setDelivery(tareas.getString(4));

                    ListaTareas.add(classTask);

                    Log.e("test  ", "  testiiing  ;" + tareas.getString(1));


                }
                tareas.close();

                getLista();

                ArrayAdapter adp=new ArrayAdapter(this,android.R.layout.simple_list_item_1,ListaInfo);
                listView_Tasks.setAdapter(adp);

            } else {
                Toast.makeText(this, "No se encontraron tareas", Toast.LENGTH_LONG).show();
                return;
            }
        } catch (
                SQLiteException e) {
            Toast.makeText(this, e.getMessage().toUpperCase(), Toast.LENGTH_LONG).show();
            return;
        }

    }

    private void getLista() {
        ListaInfo = new ArrayList<String>();
        for (int i = 0; i < ListaTareas.size(); i++) {
            ListaInfo.add(ListaTareas.get(i).getObject() + " - " + ListaTareas.get(i).getDescription() + " - " + ListaTareas.get(i).getPoints() + " - " + ListaTareas.get(i).getDelivery());
        }
    }


    private void setValues() {
        listView_Tasks = findViewById(R.id.listViewTasks);
        editexUser = findViewById(R.id.editTextUSerListTasks);
        button = findViewById(R.id.buttonNewTasActivity);
        user = getIntent().getStringExtra("user_log");

    }
}
