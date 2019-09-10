package com.example.tareas.Utilidades;

public class Utilidades {
    public static String nameDatabase="tasktodo";

    public static int versionDatabase=1;

    public static String[] nameTables={"user","task"};


    public static String[] nameFieldsTableUser={"nickname","password"};
    public static String[] nameFieldsTableTask={nameTables[0],"subject","description","points","delivery"};

    public static String createTableUser="CREATE TABLE "+nameTables[0]+" (" +
            nameFieldsTableUser[0]+" varchar(20) PRIMARY KEY NOT NULL," +
            nameFieldsTableUser[1]+" text NOT NULL);";

    public static String createTableTask="CREATE TABLE "+nameTables[1]+" (" +
            nameFieldsTableTask[0]+" varchar(20) NOT NULL," +
            nameFieldsTableTask[1]+" varchar(15) NOT NULL," +
            nameFieldsTableTask[2]+" text NOT NULL," +
            nameFieldsTableTask[3]+" varchar(4) NOT NULL," +
            nameFieldsTableTask[4]+" varchar(15) NOT NULL);"+
            //Linea 20 creacion de la clave foranea
            "ALTER TABLE "+nameTables[1]+" ADD CONSTRAINT foreignK_"+nameTables[0]+" FOREIGN KEY ("+nameFieldsTableTask[0]+") REFERENCES "+nameTables[0]+ " ("+nameFieldsTableUser[0]+") " +
            "ON UPDATE CASCADE ON DELETE CASCADE;";


    public static String deleteTableUser="DROP TABLE IF EXIST "+Utilidades.createTableUser;
    public static String deleteTableTask="DROP TABLE IF EXIST "+Utilidades.createTableTask;
}
