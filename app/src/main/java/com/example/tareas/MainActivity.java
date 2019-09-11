package com.example.tareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tareas.Actions.CrudUser;
import com.example.tareas.Utilidades.Utilidades;
import com.example.tareas.Vistas.NewTask;
import com.example.tareas.Vistas.Tasks;

public class MainActivity extends AppCompatActivity {
    private TextView textViewUserName;
    private TextView textViewUserPassword;
    private Button buttonNewAccount;
    private Button buttonLog;
    private CrudUser crud;
    private Intent intent;

    private String user, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setValues();
        //crea nueva cuenta al presionar el boton nueva cuenta
        buttonNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCuenta();
            }
        });
        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log();
            }
        });

    }

    private void log() {
        if (emptyFields() && lenghtRequireed()) {
            try {
                user = textViewUserName.getText().toString().toLowerCase();

                password = textViewUserPassword.getText().toString().toLowerCase();

                crud = new CrudUser(getApplicationContext(), user, password);

                if (crud.getNickName().isEmpty()) {
                    showMessage("No se encontro usuario en la base de datos. Intente crear una cuenta.");
                } else {
                    if (crud.getPassword().equals(password)) {
                        intent = new Intent(this, Tasks.class);
                        intent.putExtra("user_log", user);
                        startActivity(intent);
                        cleanFields();
                    } else {
                        textViewUserPassword.setError("Incorrect password");
                        return;
                    }
                }
            } catch (Exception ex) {
                showMessage("Error " + ex.getMessage());
                return;
            }
        }
    }

    //crea un nuevo usuario
    private void createCuenta() {
        //validadcion de que todos los campos cumplan con las restricciones establecidas
        if (emptyFields() && lenghtRequireed()) {

            user = textViewUserName.getText().toString().toLowerCase();

            password = textViewUserPassword.getText().toString();

            crud = new CrudUser(getApplicationContext(), user, password);

            if (crud.getNickName().isEmpty()) {
                if (crud.saveUser()!= 666) showMessage("Usuario creado.");
                else{
                    showMessage("Error al guardar el usuario.");
                    return;
                }
            } else {
                textViewUserName.setError("Este usuario ya existe.");
                return;
            }
        }
    }

    //Establece relaciones entre los elementos de la activity
    private void setValues() {
        buttonNewAccount = findViewById(R.id.buttonNewAccount);
        buttonLog = findViewById(R.id.buttonLog);
        textViewUserName = findViewById(R.id.editTextUser);
        textViewUserPassword = findViewById(R.id.editTextPassword);
    }
    private void cleanFields(){
        textViewUserName.setText("");
        textViewUserPassword.setText("");

    }

    //only show messages
    private void showMessage(String message) {
        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    //Valida solo si los campos estan vacios; todo debe estar bien para que de true
    private boolean emptyFields() {

        if (textViewUserName.getText().toString().isEmpty() && textViewUserPassword.getText().toString().isEmpty()) {

            textViewUserName.setError("Required for this action");

            textViewUserPassword.setError("Required for this action");

            showMessage("Datos obligatorios");
            return false;

        } else if (textViewUserName.getText().toString().isEmpty()) {
            textViewUserName.setError("Required for this action");
            return false;

        } else if (textViewUserPassword.getText().toString().isEmpty()) {
            textViewUserPassword.setError("Required for this action");
            return false;
        } else {
            return true;
        }
    }

    //Valida que los caracteres en los campos sean mayor de 5
    private boolean lenghtRequireed() {
        //length mayor de 5 caracterres para los dos inputs
        if (textViewUserName.getText().length() < 3) {
            textViewUserName.setError("Debe tener minimo 3 caracteres.");
            return false;

        } else if (textViewUserPassword.getText().length() < 5) {
            textViewUserPassword.setError("Debe tener minimo 5 caracteres.");
            return false;
        } else {
            return true;
        }
    }

}
