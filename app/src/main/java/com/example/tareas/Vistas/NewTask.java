package com.example.tareas.Vistas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tareas.Actions.CrudTask;
import com.example.tareas.R;

import java.util.Calendar;

public class NewTask extends AppCompatActivity {
    private EditText subject, description, points;
    private TextView delivery;
    private int pointTask = 0;
    private Button buttonNewTask;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(R.string.TittleNewTaskActivity);
        setContentView(R.layout.activity_new_task);
        setValues();
        buttonNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }
        });
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tes();
            }
        });
        user = getIntent().getStringExtra("user_log");
    }

    private void tes() {
        calendar = Calendar.getInstance();

        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                delivery.setText(year + "-" + month + "-" + day);
            }
        }, y, m, d);
        datePickerDialog.show();


        //yyyy-mm-dd
        Toast.makeText(this, "mostrar el data", Toast.LENGTH_LONG).show();

    }


    private void saveTask() {


        if (emptyValues()) {
            Toast.makeText(this, "Datos requeridos.", Toast.LENGTH_LONG).show();
            subject.setError("Obligatorio.");
            description.setError("Obligatorio.");
            points.setError("Obligatorio.");
            delivery.setError("Obligatorio.");
            return;
        } else if (validateFields()) {
            //Aqui funciona bien
            //Toast.makeText(this.getApplicationContext(), "Tarea guardada.", Toast.LENGTH_LONG).show();
            if (!(delivery.getText().toString().isEmpty() || delivery.getText().equals("")))
                Task();
            else
                Toast.makeText(this, "Elija una fecha de entrega.", Toast.LENGTH_LONG).show();
            delivery.setError("Elija una fecha de entrega.");
            return;
        } else {
            Toast.makeText(this, "Falta informacion requerida.", Toast.LENGTH_LONG).show();
            return;
        }


    }


    private void Task() {
        CrudTask task = new CrudTask(this.getApplicationContext());
        String task_test = task.SaveTask(user, subject.getText().toString(), description.getText().toString(), points.getText().toString(), delivery.getText().toString());
        if (task_test.equals("fail")) {


            //Toast.makeText(this.getApplicationContext(),"sql : "+user+" "+subject.getText().toString()+" "+description.getText().toString()+" "+points.getText().toString()
            //       +" "+delivery.getText().toString() +task_test,Toast.LENGTH_LONG).show();

            return;
        } else {

            //Toast.makeText(this.getApplicationContext(),"success : "+task_test,Toast.LENGTH_LONG).show();
            Intent taskView = new Intent(this, Tasks.class);
            taskView.putExtra("user_log", user);
            startActivity(taskView);
            cleanFields();
        }
    }

    private void cleanFields() {
        subject.setText("");
        description.setText("");
        points.setText("");
        delivery.setText("");
    }

    private boolean emptyValues() {
        return subject.getText().toString().isEmpty()
                && description.getText().toString().isEmpty()
                && points.getText().toString().isEmpty()
                && delivery.getText().toString().isEmpty();
    }

    private boolean validateFields() {
        if (subject.getText().toString().length() < 4) {
            subject.setError("Debe tener minimo 4 caracteres.");
            return false;
        } else if (description.getText().toString().length() < 4) {
            description.setError("Debe tener minimo 4 caracteres.");
            return false;
        } else if (points.length() > 4) {
            points.setError("Numero de puntos no validos. Debe ser <9999");
            return false;
        } else {
            try {
                this.pointTask = Integer.parseInt(points.getText().toString());
                if (pointTask <= 0) {
                    points.setError("Solo debe contener numeros positivos (mayores que cero).");
                    return false;
                } else if (pointTask == 0) {
                    points.setError("La tarea no puede tener 0 puntos.");
                    return false;
                } else {
                    return true;
                }
            } catch (NumberFormatException ex) {
                points.setError("Solo debe contener numeros.");
                return false;
            }
        }
    }

    private void setValues() {
        subject = findViewById(R.id.editTextTaskSubject);
        description = findViewById(R.id.editTextTaskDesc);
        points = findViewById(R.id.editTextTaskPoints);
        delivery = findViewById(R.id.editTextTaskDelivery);
        buttonNewTask = findViewById(R.id.buttonTaskNew);
    }
}
