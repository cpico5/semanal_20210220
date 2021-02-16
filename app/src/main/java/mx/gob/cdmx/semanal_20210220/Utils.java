package mx.gob.cdmx.semanal_20210220;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class Utils {

    static UsuariosSQLiteHelper3 usdbh3;
    private static SQLiteDatabase db3;

    public static String getAlcaldia(int idAlcaldia){
        String nombre = "";

        switch(idAlcaldia){
            case 2:
                nombre = "Azcapotzalco";
                break;
            case 3:
                nombre = "Coyoac�n";
                break;
            case 4:
                nombre = "Cuajimalpa de Morelos";
                break;
            case 5:
                nombre = "Gustavo A. Madero";
                break;
            case 6:
                nombre = "Iztacalco";
                break;
            case 7:
                nombre = "Iztapalapa";
                break;
            case 8:
                nombre = "La Magdalena Contreras";
                break;
            case 9:
                nombre = "Milpa Alta";
                break;
            case 10:
                nombre = "�lvaro Obreg�n";
                break;
            case 11:
                nombre = "Tl�huac";
                break;
            case 12:
                nombre = "Tlalpan";
                break;
            case 13:
                nombre = "Xochimilco";
                break;
            case 14:
                nombre = "Benito Ju�rez";
                break;
            case 15:
                nombre = "Cuauht�moc";
                break;
            case 16:
                nombre = "Miguel Hidalgo";
                break;
            case 17:
                nombre = "Venustiano Carranza";
                break;
            default:
                nombre = "";
                break;
        }

        return nombre;
    }

    public static String getAlcaldes(String Alcaldia){
        String nombre = "";

        switch(Alcaldia){
            case "Azcapotzalco":
                nombre = "Vidal Llerenas";
                break;
            case "Coyoac�n":
                nombre = "Manuel Negrete";
                break;
            case "Cuajimalpa de Morelos":
                nombre = "Adri�n Rubalcava";
                break;
            case "Gustavo A. Madero":
                nombre = "Francisco Chiguil";
                break;
            case "Iztacalco":
                nombre = "Armando Quintero";
                break;
            case "Iztapalapa":
                nombre = "Clara Brugada";
                break;
            case "La Magdalena Contreras":
                nombre = "Patricia Ximena Ortiz Couturier";
                break;
            case "Milpa Alta":
                nombre = "Octavio Rivero Villase�or";
                break;
            case "�lvaro Obreg�n":
                nombre = "Layda Sansores";
                break;
            case "Tl�huac":
                nombre = "Raymundo Mart�nez Vite";
                break;
            case "Tlalpan":
                nombre = "Patricia Elena Aceves Pastrana";
                break;
            case "Xochimilco":
                nombre = "Jos� Carlos Acosta Ruiz";
                break;
            case "Benito Ju�rez":
                nombre = "Santiago Taboada";
                break;
            case "Cuauht�moc":
                nombre = "N�stor L�pez N��ez";
                break;
            case "Miguel Hidalgo":
                nombre = "V�ctor Hugo Romo";
                break;
            case "Venustiano Carranza":
                nombre = "Julio C�sar Moreno";
                break;
            default:
                nombre = "";
                break;
        }

        return nombre;
    }

    public static Integer getIdAlcaldia(String Alcaldia){
        Integer id_alcaldia;

        switch(Alcaldia){
            case "Azcapotzalco":
                id_alcaldia = 2;
                break;
            case "Coyoac�n":
                id_alcaldia = 3;
                break;
            case "Cuajimalpa de Morelos":
                id_alcaldia = 4;
                break;
            case "Gustavo A. Madero":
                id_alcaldia = 5;
                break;
            case "Iztacalco":
                id_alcaldia = 6;
                break;
            case "Iztapalapa":
                id_alcaldia = 7;
                break;
            case "La Magdalena Contreras":
                id_alcaldia = 8;
                break;
            case "Milpa Alta":
                id_alcaldia = 9;
                break;
            case "�lvaro Obreg�n":
                id_alcaldia = 10;
                break;
            case "Tl�huac":
                id_alcaldia = 11;
                break;
            case "Tlalpan":
                id_alcaldia = 12;
                break;
            case "Xochimilco":
                id_alcaldia = 13;
                break;
            case "Benito Ju�rez":
                id_alcaldia = 14;
                break;
            case "Cuauht�moc":
                id_alcaldia = 15;
                break;
            case "Miguel Hidalgo":
                id_alcaldia = 16;
                break;
            case "Venustiano Carranza":
                id_alcaldia = 17;
                break;
            default:
                id_alcaldia = 0;
                break;
        }

        return id_alcaldia;
    }

    public static boolean verificaConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        for (int i = 0; i < 2; i++) {
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }

    public static Void info(String TAG,String mensaje,String aMostrar){
        Log.e(TAG, "cqs -->>  "+ mensaje +": "+ String.valueOf(aMostrar));
        return null;
    }

    public static Void toast(Context ctx,String mensaje){
        Toast toast=Toast.makeText(ctx, mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
        toast.show();
        return null;
    }

    public static String sacaLatitud(Context ctx) {
        Set<String> set = new HashSet<String>();
        String acceso = null;
        final String F = "File dbfile";
        // Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh3 = new UsuariosSQLiteHelper3(ctx);
        db3 = usdbh3.getReadableDatabase();
        String selectQuery = "select latitud from ubicacion order by id desc limit 1";
        Cursor cursor = db3.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                acceso = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db3.close();

        return acceso;
    }

    public static String sacaLongitud(Context ctx) {
        Set<String> set = new HashSet<String>();
        String acceso = null;
        final String F = "File dbfile";
        // Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh3 = new UsuariosSQLiteHelper3(ctx);
        db3 = usdbh3.getReadableDatabase();
        String selectQuery = "select longitud from ubicacion order by id desc limit 1";
        Cursor cursor = db3.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                acceso = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db3.close();

        return acceso;
    }





}
