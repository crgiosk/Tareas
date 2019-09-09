package com.example.tareas.Actions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.example.tareas.Utilidades.Utilidades;

public class CrudUser extends Actions {
    private ContentValues values;
    private String consulta;
    private String nickName;
    private String password;
    private String passworddbUser="";
    /**
     * Clase que contiene los metodos para insertar, actualizar o borrar datos de la bd
     *
     * @param context
     */
    public CrudUser(Context context,String nickName,String password) {

        super(context);
        this.nickName=nickName;
        this.password=password;
    }

    public long saveUser(){

        values=new ContentValues();
        values.put(Utilidades.nameFieldsTableUser[0],this.nickName);
        values.put(Utilidades.nameFieldsTableUser[1],this.password);
        return save(Utilidades.nameTables[0],values);
    }

    private String passwordUser(){
        consulta="SELECT "+Utilidades.nameFieldsTableUser[1]+" FROM "+ Utilidades.nameTables[0]+ " WHERE "+Utilidades.nameFieldsTableUser[0]+"='"+nickName+"';";
        Cursor cursor=consult(consulta);
        if (cursor.moveToNext()){
            return cursor.getString(0);

        }else{
            return "";
        }

    }

    private String  existUser(){
        consulta="SELECT "+Utilidades.nameFieldsTableUser[0]+" FROM "+ Utilidades.nameTables[0]+ " WHERE "+Utilidades.nameFieldsTableUser[0]+"='"+nickName+"';";
        Cursor cursor=consult(consulta);
        if (cursor.moveToNext()){
            return cursor.getString(0);

        }else{
            return "";
        }
    }

    /**
     * Metodo que valida la existencia del usuario
     * @return
     */
    public String getNickName() {
        return existUser() ;
    }

    public String getPassword() {
        return passwordUser();
    }
}
