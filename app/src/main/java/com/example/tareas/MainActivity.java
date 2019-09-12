package com.example.tareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tareas.Actions.CrudUser;
import com.example.tareas.Utilidades.Utilidades;
import com.example.tareas.Vistas.NewTask;
import com.example.tareas.Vistas.Tasks;

public class MainActivity extends AppCompatActivity {
    private EditText editTextUserName,editTextUserPassword;
    private Button buttonNewAccount,buttonLog;
    private CrudUser crud;
    private Intent intent;
    private SharedPreferences preferences;

    private String user, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setValues();

        if (exisPreferences()){
            intent = new Intent(this, Tasks.class);
            startActivity(intent);
            cleanFields();
            this.finish();
        }
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

        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

    }

    private boolean exisPreferences(){
        preferences=getSharedPreferences("Data"+Utilidades.nameTables[0], Context.MODE_PRIVATE);
        user=preferences.getString(Utilidades.nameTables[0],null);

        return user!=null;
    }



    private void log() {
        if (emptyFields() && lenghtRequireed()) {
            try {
                user = editTextUserName.getText().toString().toLowerCase();

                password = editTextUserPassword.getText().toString().toLowerCase();

                crud = new CrudUser(getApplicationContext(), user, password);

                if (crud.getNickName().isEmpty()) {
                    showMessage("You're new here? create account.");
                } else {
                    if (crud.getPassword().equals(password)) {
                        savePreferences();
                        intent = new Intent(this, Tasks.class);
                        intent.putExtra("user_log", user);
                        startActivity(intent);
                        cleanFields();
                        this.finish();
                    } else {
                        editTextUserPassword.setError("Incorrect password");
                        return;
                    }
                }
            } catch (Exception ex) {
                showMessage("Error " + ex.getMessage());
                return;
            }
        }
    }
//Establece las preferencias del usuario; en este caso el nombre de usuario
    private void savePreferences() {
        preferences=getSharedPreferences("Data"+Utilidades.nameTables[0], Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(Utilidades.nameTables[0],editTextUserName.getText().toString().toLowerCase());
        editor.commit();
    }

    //crea un nuevo usuario
    private void createCuenta() {
        //validadcion de que todos los campos cumplan con las restricciones establecidas
        if (emptyFields() && lenghtRequireed()) {

            user = editTextUserName.getText().toString().toLowerCase();

            password = editTextUserPassword.getText().toString();

            crud = new CrudUser(getApplicationContext(), user, password);

            if (crud.getNickName().isEmpty()) {
                if (crud.saveUser()!= 666) showMessage("Usuario creado.");
                else{
                    showMessage("Error al guardar el usuario.");
                    return;
                }
            } else {
                editTextUserName.setError("Este usuario ya existe.");
                return;
            }
        }
    }

    //Establece relaciones entre los elementos de la activity
    private void setValues() {
        buttonNewAccount = findViewById(R.id.buttonNewAccount);
        buttonLog = findViewById(R.id.buttonLog);
        editTextUserName = findViewById(R.id.editTextUser);
        editTextUserPassword = findViewById(R.id.editTextPassword);
    }
    private void cleanFields(){
        editTextUserName.setText("");
        editTextUserPassword.setText("");

    }

    //only show messages
    private void showMessage(String message) {
        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    //Valida solo si los campos estan vacios; todo debe estar bien para que de true
    private boolean emptyFields() {

        if (editTextUserName.getText().toString().isEmpty() && editTextUserPassword.getText().toString().isEmpty()) {

            editTextUserName.setError("Required for this action");

            editTextUserPassword.setError("Required for this action");

            showMessage("Datos obligatorios");
            return false;

        } else if (editTextUserName.getText().toString().isEmpty()) {
            editTextUserName.setError("Required for this action");
            return false;

        } else if (editTextUserPassword.getText().toString().isEmpty()) {
            editTextUserPassword.setError("Required for this action");
            return false;
        } else {
            return true;
        }
    }

    //Valida que los caracteres en los campos sean mayor de 5
    private boolean lenghtRequireed() {
        //length mayor de 5 caracterres para los dos inputs
        if (editTextUserName.getText().length() < 3) {
            editTextUserName.setError("Debe tener minimo 3 caracteres.");
            return false;

        } else if (editTextUserPassword.getText().length() < 5) {
            editTextUserPassword.setError("Debe tener minimo 5 caracteres.");
            return false;
        } else {
            return true;
        }
    }

}
