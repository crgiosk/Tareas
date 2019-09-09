package com.example.tareas.Vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    protected ListView listTasks;
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

            while (tareas.moveToNext()) {
                //muestra daots por consola; ordenar
                Log.e("TESTING     ", tareas.getString(0).toUpperCase());
                Log.e("TESTING     ", tareas.getString(1).toUpperCase());
                Log.e("TESTING     ", tareas.getString(2).toUpperCase());
                Log.e("TESTING     ", tareas.getString(3).toUpperCase());
                Log.e("TESTING     ", tareas.getString(4).toUpperCase());
            }
        } catch (Exception e) {
            Toast.makeText(this,e.getMessage().toUpperCase(), Toast.LENGTH_LONG).show();
        }

        Log.e("m", "testeeeeeeeeeeeeeeeeeeeEEEEEEEEEEEEEEESSSSSSSSS  fails");


    }

    private void setValues() {
        listTasks = findViewById(R.id.listViewTasks);
        editexUser = findViewById(R.id.editTextUSerListTasks);
        button = findViewById(R.id.buttonNewTasActivity);
        user = getIntent().getStringExtra("user_log");

    }
}
