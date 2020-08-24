package mx.gob.cdmx.biometrico_semanal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class Registro extends Activity {

    private final String TAG = "Entrada";

    public Button Salir;
    double latitude;

    public EditText mUsuario;
    public EditText mPass;
    private TextView textSpeetch;

    UsuariosSQLiteHelper2 usdbh2;
    private SQLiteDatabase db2;
    UsuariosSQLiteHelper3 usdbh3;
    private SQLiteDatabase db3;

    public Button registrar;

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    Calendar c = Calendar.getInstance();

    SimpleDateFormat df1 = new SimpleDateFormat("yyy-MM-dd");
    String formattedDate1 = df1.format(c.getTime());
    SimpleDateFormat df3 = new SimpleDateFormat("yyyyMMdd");
    String formattedDate3 = df3.format(c.getTime());
    SimpleDateFormat df4 = new SimpleDateFormat("yyy-MM-dd");
    String formattedDateFecha = df4.format(c.getTime());
    SimpleDateFormat df5 = new SimpleDateFormat("yyyyMMdd");
    String formattedDateAyer = df5.format(yesterday());

    Date ayer = yesterday();

    protected void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(this,this));

        Log.i(TAG, " =====> la latitud: " + latitude);

        mUsuario = (EditText) findViewById(R.id.editUsuario);
        mPass = (EditText) findViewById(R.id.editPass);

        textSpeetch = (TextView) findViewById(R.id.textSpeetch);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textSpeetch.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

        /////////////////////////////////////////////////////////////////////// 7

        registrar = (Button) findViewById(R.id.btnE1);

        registrar.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressWarnings("null")
            @Override
            public void onClick(View v) {
//					// TODO Auto-generated method stub Aqui para generar el archivo vacio// hasta la 112
//                final EditText usuario = (EditText) findViewById(R.id.editUsuario);
//                final EditText pass = (EditText) findViewById(R.id.editPass);
                try {
                    String elUsuario= mUsuario.getText().toString();
                    String elPassword= mPass.getText().toString();

                     if (mUsuario.getText().toString().trim().length()==0){Toast.makeText(getBaseContext(),"El campo Usuario es obligatorio",Toast.LENGTH_LONG).show();}
                    else if (mPass.getText().toString().trim().length()==0){Toast.makeText(getBaseContext(),"El campo de la contraseña es obligatorio",Toast.LENGTH_LONG).show();}
                    else {

                         sacaUsuario(elUsuario, elPassword);
                         String paso = sacaUsuario(elUsuario, elPassword).toString();
                         Log.i(TAG, "cqs ------------->> sacaUsuario: " + paso);

                         if (paso.matches("1")) {

                             valores(elUsuario,elPassword);


                         } else {
                             showToast("Captura un usuario y/o contraseña válidos");
                         }

                     }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }
        });



    }

    public void valores(String user,String pass){
        ContentValues values = new ContentValues();
        usdbh3 = new UsuariosSQLiteHelper3(this);
        db3 = usdbh3.getReadableDatabase();
        if (db3 != null) {
            values.put("user", user);
            values.put("pass", pass);
            db3.insert("fp", null, values);
        }
        db3.close();

        dialogo();

    }

    public void salir() {
        Salir = (Button) findViewById(R.id.btnSalir);
    }

    public void salir(View view) {
        finish();
    }

    private String sacaUsuario(String user, String pass) {
        Set<String> set = new HashSet<String>();
        String acceso = null;
        final String F = "File dbfile";
        // Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh2 = new UsuariosSQLiteHelper2(this, "F", null, 1);
        db2 = usdbh2.getReadableDatabase();
        String selectQuery = "select count(*) from usuarios where usuario='" + user + "' and password='" + pass + "';";
        Log.i(TAG, "cqs ------------->> Query sacaUsuario: " + selectQuery);
        Cursor cursor = db2.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                acceso = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db2.close();

        return acceso;
    }

    public void dialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Se ha registrado correctamente").setTitle("Registro").setCancelable(false)
                .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        Intent i = new Intent(Registro.this, Bienvenida.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        System.exit(0); // metodo que se debe implementar
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }


}
