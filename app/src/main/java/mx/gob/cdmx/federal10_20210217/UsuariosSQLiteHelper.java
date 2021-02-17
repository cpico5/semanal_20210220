
package mx.gob.cdmx.federal10_20210217;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.UUID;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

public class UsuariosSQLiteHelper extends SQLiteOpenHelper {

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


    UUID uuid = UUID.randomUUID();

    public String tablet;
    InputStream datos, usuarios, nofue, acambio,prd, pri, pan, morena, independiente= null;

    static Nombre nom= new Nombre();
    static String nombreE = nom.nombreEncuesta();



    static String ID = getHostName(null);
    static String prefix = ID;

    // private static final String DATABASE_NAME = Environment.getExternalStorageDirectory() +"/Mis_archivos/" +nombreE+"_"+prefix+"";
    private static final int DATABASE_VERSION = 2;

    public UsuariosSQLiteHelper(Context context, String name,CursorFactory factory, int version, String DATABASE_NAME) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
// TODO Auto-generated constructor stub
        try {
            datos = context.getAssets().open("datos.sql");
            usuarios = context.getAssets().open("usuarios.sql");

        } catch (Exception ex) {
            Log.i(null, "HORROR-1: " + ex.fillInStackTrace());
        }

    }

/////////////////////  TABLA ENCUESTAS  ///////////////////////////////////////////////

    public static class TablaEncuestas{
        public static String TABLA_ENCUESTAS            = "encuestas";
        public static String COLUMNA_CONSECUTIVO_DIARIO = "consecutivo_diario";
        public static String COLUMNA_EQUIPO             = "equipo";
        public static String COLUMNA_USUARIO            = "usuario";
        public static String COLUMNA_NOMBRE_ENCUESTA    = "nombre_encuesta";
        public static String COLUMNA_FECHA              = "fecha";
        public static String COLUMNA_HORA               = "hora";
        public static String COLUMNA_imei               = "imei";
        public static String COLUMNA_SECCION            = "seccion";
        public static String COLUMNA_latitud            = "latitud";
        public static String COLUMNA_longitud           = "longitud";

        public static String COLUMNA_pregunta_1="pregunta_1";
        public static String COLUMNA_pregunta_2="pregunta_2";
        public static String COLUMNA_pregunta_3="pregunta_3";
        public static String COLUMNA_pregunta_4="pregunta_4";
        public static String COLUMNA_pregunta_5="pregunta_5";
        public static String COLUMNA_pregunta_6="pregunta_6";
        public static String COLUMNA_pregunta_7="pregunta_7";
        public static String COLUMNA_pregunta_covi="pregunta_covi";
        public static String COLUMNA_pregunta_Covi1="pregunta_Covi1";
        public static String COLUMNA_pregunta_Covi2="pregunta_Covi2";
        public static String COLUMNA_pregunta_Covi3="pregunta_Covi3";
        public static String COLUMNA_pregunta_Covi4="pregunta_Covi4";
        public static String COLUMNA_pregunta_8="pregunta_8";
        public static String COLUMNA_pregunta_9="pregunta_9";
        public static String COLUMNA_pregunta_10="pregunta_10";
        public static String COLUMNA_pregunta_11="pregunta_11";
        public static String COLUMNA_pregunta_12="pregunta_12";
        public static String COLUMNA_pregunta_13="pregunta_13";
        public static String COLUMNA_pregunta_14="pregunta_14";
        public static String COLUMNA_pregunta_15="pregunta_15";
        public static String COLUMNA_pregunta_16="pregunta_16";
        public static String COLUMNA_pregunta_16a="pregunta_16a";
        public static String COLUMNA_pregunta_16b="pregunta_16b";
        public static String COLUMNA_pregunta_17="pregunta_17";
        public static String COLUMNA_pregunta_18="pregunta_18";
        public static String COLUMNA_pregunta_19="pregunta_19";
        public static String COLUMNA_pregunta_19a="pregunta_19a";
        public static String COLUMNA_pregunta_19b="pregunta_19b";
        public static String COLUMNA_pregunta_20="pregunta_20";
        public static String COLUMNA_pregunta_21="pregunta_21";
        public static String COLUMNA_pregunta_22="pregunta_22";
        public static String COLUMNA_pregunta_23a="pregunta_23a";
        public static String COLUMNA_pregunta_23b="pregunta_23b";
        public static String COLUMNA_pregunta_23c="pregunta_23c";
        public static String COLUMNA_pregunta_24="pregunta_24";
        public static String COLUMNA_pregunta_24_1="pregunta_24_1";
        public static String COLUMNA_pregunta_24_1a="pregunta_24_1a";
        public static String COLUMNA_pregunta_24_1b="pregunta_24_1b";
        public static String COLUMNA_pregunta_24_2="pregunta_24_2";
        public static String COLUMNA_pregunta_24_2a="pregunta_24_2a";
        public static String COLUMNA_pregunta_24_2b="pregunta_24_2b";
        public static String COLUMNA_pregunta_24_3="pregunta_24_3";
        public static String COLUMNA_pregunta_24_3a="pregunta_24_3a";
        public static String COLUMNA_pregunta_24_3b="pregunta_24_3b";
        public static String COLUMNA_pregunta_25="pregunta_25";
        public static String COLUMNA_pregunta_26="pregunta_26";
        public static String COLUMNA_pregunta_27="pregunta_27";
        public static String COLUMNA_pregunta_28="pregunta_28";
        public static String COLUMNA_pregunta_29="pregunta_29";


        public static String COLUMNA_aporta         ="aporta";
        public static String COLUMNA_ocupacion      ="ocupacion";
        public static String COLUMNA_cuantos_coches ="cuantos_coches";
        public static String COLUMNA_cuartos        ="cuartos";
        public static String COLUMNA_cuartos_dormir ="cuartos_dormir";
        public static String COLUMNA_focos          ="focos";
        public static String COLUMNA_banos          ="banos";
        public static String COLUMNA_regadera       ="regadera";
        public static String COLUMNA_estufa         ="estufa";
        public static String COLUMNA_edad           ="edad";
        public static String COLUMNA_genero         ="genero";
        public static String COLUMNA_tipo_vivienda  ="tipo_vivienda";
        public static String COLUMNA_tipo_piso      ="tipo_piso";


        public static String COLUMNA_abandono     ="abandono";
        public static String COLUMNA_suma         ="suma";
        public static String COLUMNA_status       ="status";
        // FINALIZAN PREGUNTAS
        public static String COLUMNA_TIEMPO       = "tiempo";
        public static String COLUMNA_TIPO_CAPTURA = "tipo_captura";
        public static String COLUMNA_enviado      = "enviado";
    }




    private static final String DATABASE_ENCUESTA= "create table "
            + TablaEncuestas.TABLA_ENCUESTAS + "("
            + "id integer primary key autoincrement,"
            + TablaEncuestas.COLUMNA_CONSECUTIVO_DIARIO + " text not null, "
            + TablaEncuestas.COLUMNA_EQUIPO + " text not null, "
            + TablaEncuestas.COLUMNA_USUARIO + " text not null, "
            + TablaEncuestas.COLUMNA_NOMBRE_ENCUESTA + " text not null, "
            + TablaEncuestas.COLUMNA_FECHA + " date not null, "
            + TablaEncuestas.COLUMNA_HORA + " text not null, "
            + TablaEncuestas.COLUMNA_imei + " text not null, "
            + TablaEncuestas.COLUMNA_SECCION + " INTEGER not null, "
            + TablaEncuestas.COLUMNA_latitud + " text, "
            + TablaEncuestas.COLUMNA_longitud + " text, "


            + TablaEncuestas.COLUMNA_pregunta_1 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_2 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_3 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_4 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_5 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_6 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_7 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_covi +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_Covi1 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_Covi2 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_Covi3 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_Covi4 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_8 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_9 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_10 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_11 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_12 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_13 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_14 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_15 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_16 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_16a +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_16b +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_17 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_18 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_19 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_19a +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_19b +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_20 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_21 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_22 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_23a +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_23b +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_23c +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_24 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_24_1 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_24_1a +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_24_1b +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_24_2 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_24_2a +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_24_2b +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_24_3 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_24_3a +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_24_3b +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_25 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_26 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_27 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_28 +  " text, "
            + TablaEncuestas.COLUMNA_pregunta_29 +  " text, "

            + TablaEncuestas.COLUMNA_aporta  +  " text, "
            + TablaEncuestas.COLUMNA_ocupacion  +  " text, "
            + TablaEncuestas.COLUMNA_cuantos_coches  +  " text, "
            + TablaEncuestas.COLUMNA_cuartos  +  " text, "
            + TablaEncuestas.COLUMNA_cuartos_dormir  +  " text, "
            + TablaEncuestas.COLUMNA_focos  +  " text, "
            + TablaEncuestas.COLUMNA_banos  +  " text, "
            + TablaEncuestas.COLUMNA_regadera  +  " text, "
            + TablaEncuestas.COLUMNA_estufa  +  " text, "
            + TablaEncuestas.COLUMNA_edad  +  " text, "
            + TablaEncuestas.COLUMNA_genero  +  " text, "
            + TablaEncuestas.COLUMNA_tipo_vivienda  +  " text, "
            + TablaEncuestas.COLUMNA_tipo_piso  +  " text, "

            + TablaEncuestas.COLUMNA_abandono +  " text, "

            + TablaEncuestas.COLUMNA_suma +  " text, "
            + TablaEncuestas.COLUMNA_status +  " text, "
            + TablaEncuestas.COLUMNA_TIEMPO + " text, "
            + TablaEncuestas.COLUMNA_TIPO_CAPTURA + " text, "
            + TablaEncuestas.COLUMNA_enviado + " text not null); ";

////////////////////////////  TABLA USUARIOS   /////////////////////////////////////////////////////////

    public static class TablaUsuarios{
        public static String TABLA_USUARIOS   = "usuarios";
        public static String COLUMNA_USUARIO  = "usuario";
        public static String COLUMNA_PASSWORD = "password";

    }

    private static final String DATABASE_USUARIOS = "create table "
            + TablaUsuarios.TABLA_USUARIOS + "("
            + TablaUsuarios.COLUMNA_USUARIO + " text not null, "
            + TablaUsuarios.COLUMNA_PASSWORD+ " text not null); ";


    @Override
    public void onCreate(SQLiteDatabase db) {
// TODO Auto-generated method stub
         db.execSQL(DATABASE_ENCUESTA);

//        db.execSQL(DaoManager.generateCreateQueryString(encuestas.class));



    }


    public void cargaDatos(SQLiteDatabase db){
        InputStream tabla=datos;
        try {

            if (tabla != null) {
                db.beginTransaction();
                BufferedReader reader = new BufferedReader(new InputStreamReader(tabla,ENCODING));
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

    public void cargaUsuarios(SQLiteDatabase db){
        InputStream tabla=usuarios;
        try {

            if (tabla != null) {
                db.beginTransaction();
                BufferedReader reader = new BufferedReader(new InputStreamReader(tabla,ENCODING));
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
        db.execSQL("drop table if exists " + TablaEncuestas.TABLA_ENCUESTAS);

//        db.execSQL(DaoManager.generateDropIfExistsQueryString(encuestas.class));

        onCreate(db);
    }
}

