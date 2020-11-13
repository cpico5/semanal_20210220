package mx.gob.cdmx.semanal_20201114;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;


import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import mx.gob.cdmx.semanal_20201114.db.DaoManager;
import mx.gob.cdmx.semanal_20201114.model.Datos;

import static mx.gob.cdmx.semanal_20201114.UsuariosSQLiteHelper2.TablaUsuarios.TABLA_USUARIOS;

public class UsuariosSQLiteHelper2 extends SQLiteOpenHelper {

    private static final String ENCODING = "ISO-8859-1";


    public static String getHostName(String defValue) {
        try {
            Method getString = Build.class.getDeclaredMethod("getString", String.class);
            getString.setAccessible(true);
            return getString.invoke(null, "net.hostname").toString();
        } catch (Exception ex) {
            return defValue;
        }
    }

    public String tablet;
    InputStream datos, usuarios, nofue, acambio, prd, pri, pan, morena, independiente = null;

    static Nombre nom = new Nombre();
    static String nombreD = nom.nombreDatos();

    UsuariosSQLiteHelper2 usdbh2;
    private SQLiteDatabase db2;

    public static final String KEY_USUARIO = "usuario";
    public static final String KEY_PASSWORD = "password";

    static String ID = getHostName(null);
    static String prefix = "listado";

    private static final String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreD + "_" + prefix + "";
    private static final int DATABASE_VERSION = 8;

    public UsuariosSQLiteHelper2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
// TODO Auto-generated constructor stub
        try {
            datos = context.getAssets().open("datos.sql");
            usuarios = context.getAssets().open("usuarios.sql");

        } catch (Exception ex) {
            Log.i(null, "HORROR-1: " + ex.fillInStackTrace());
        }

    }

//////////////////////   TABLA DATOS  //////////////////////////////////////////////////////////////


//////////////////////TABLA DATOS  //////////////////////////////////////////////////////////////


    public static class TablaDatos {
        public static String TABLA_DATOS = "datos";
        public static String COLUMNA_SECCION = "seccion";
        public static String COLUMNA_DISTRITO = "distrito";
        public static String COLUMNA_MUNICIPIO = "municipio";
        public static String COLUMNA_DELEGACION = "delegacion";
        public static String COLUMNA_EQUIPOS = "equipo";
        public static String COLUMNA_COORDINADOR = "coordinador";
        public static String COLUMNA_nombre_encuesta= "nombre_encuesta";

    }

    private static final String DATABASE_DATOS = "create table "
    + TablaDatos.TABLA_DATOS + "("
    + TablaDatos.COLUMNA_SECCION + " INTEGER not null, "
    + TablaDatos.COLUMNA_DISTRITO + " text not null, "
    + TablaDatos.COLUMNA_MUNICIPIO + " text not null, "
    + TablaDatos.COLUMNA_DELEGACION + " integer not null, "
    + TablaDatos.COLUMNA_EQUIPOS + " text not null, "
    + TablaDatos.COLUMNA_COORDINADOR + " text not null, "
    + TablaDatos.COLUMNA_nombre_encuesta + " text not null); ";

///////////////////////////  TABLA USUARIOS	 /////////////////////////////////////////////////////////

    public static class TablaUsuarios {
        public static String TABLA_USUARIOS = "usuarios";
        public static String COLUMNA_USUARIO = "usuario";
        public static String COLUMNA_PASSWORD = "password";

    }

    private static final String DATABASE_USUARIOS = "create table "
    + TABLA_USUARIOS + "("
    + TablaUsuarios.COLUMNA_USUARIO + " text not null, "
    + TablaUsuarios.COLUMNA_PASSWORD + " text not null); ";


    @Override
    public void onCreate(SQLiteDatabase db) {
// TODO Auto-generated method stub
//        db.execSQL(DATABASE_DATOS);
        db.execSQL(DATABASE_USUARIOS);


//            db.execSQL(DaoManager.generateCreateQueryString(Usuarios.class));
            db.execSQL(DaoManager.generateCreateQueryString(Datos.class));


//        db.execSQL(DaoManager.generateCreateQueryString(Datos.class));

//        cargaDatos(db);
        cargaUsuarios(db);

    }


    public void cargaDatos(SQLiteDatabase db) {
        InputStream tabla = datos;
        try {

            if (tabla != null) {
                db.beginTransaction();
                BufferedReader reader = new BufferedReader(new InputStreamReader(tabla, ENCODING));
                String line = reader.readLine();
                while (!TextUtils.isEmpty(line)) {
                    db.execSQL(line);
                    line = reader.readLine();
                }
                db.setTransactionSuccessful();
            }
        } catch (Exception ex) {
            Log.i(null, "HORROR-2: " + ex.getMessage());
        } finally {
            db.endTransaction();
            if (tabla != null) {
                try {
                    tabla.close();
                } catch (IOException e) {
                    Log.i(null, "HORROR-3; " + e.getMessage());
                }
            }
        }

    }

    public void cargaUsuarios(SQLiteDatabase db) {
        InputStream tabla = usuarios;
        try {

            if (tabla != null) {
                db.beginTransaction();
                BufferedReader reader = new BufferedReader(new InputStreamReader(tabla, ENCODING));
                String line = reader.readLine();
                while (!TextUtils.isEmpty(line)) {
                    db.execSQL(line);
                    line = reader.readLine();
                }
                db.setTransactionSuccessful();
            }
        } catch (Exception ex) {
            Log.i(null, "HORROR-2: " + ex.getMessage());
        } finally {
            db.endTransaction();
            if (tabla != null) {
                try {
                    tabla.close();
                } catch (IOException e) {
                    Log.i(null, "HORROR-3; " + e.getMessage());
                }
            }
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// TODO Auto-generated method stub
        db.execSQL("DROP table if exists " + TablaDatos.TABLA_DATOS);
        db.execSQL("DROP table if exists " + TABLA_USUARIOS);
//        db.execSQL(DaoManager.generateDropIfExistsQueryString(Usuarios.class));
        onCreate(db);
    }

    //---open SQLite DB---
    public UsuariosSQLiteHelper2 open() throws SQLException {
        db2 = usdbh2.getWritableDatabase();
        return this;
    }

    //---close SQLite DB---
    public void close() {
        usdbh2.close();
    }

    //---insert data into SQLite DB---
    public long insert(String usuario, String password) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_USUARIO, usuario);
        initialValues.put(KEY_PASSWORD, password);

        return db2.insert(TABLA_USUARIOS, null, initialValues);
    }

    //---Delete All Data from table in SQLite DB---
    public void deleteAll() {

        db2.delete(TABLA_USUARIOS, null, null);
    }
}
