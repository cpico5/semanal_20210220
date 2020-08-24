package mx.gob.cdmx.biometrico_semanal;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Bienvenida extends AppCompatActivity {

    private static final String TAG = Bienvenida.class.getName();
    UsuariosSQLiteHelper3 usdbh3;
    private SQLiteDatabase db3;


    protected void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bienvenida);


        usdbh3 = new UsuariosSQLiteHelper3(this);
        db3 = usdbh3.getReadableDatabase();

        String SQLFprint = "CREATE TABLE fp (" +
                "id integer primary key autoincrement," +
                "user TEXT NOT NULL," +
                "pass TEXT NOT NULL );";

        try {

            db3.execSQL(SQLFprint);
            Log.i("cqs --->> Crea Tabla", "Se crea la tabla: " + "fp");
        } catch (Exception e) {
            String stackTrace = Log.getStackTraceString(e);
            Log.i("cqs --->> Crea tabla", "Error al crear la tabla fp" + stackTrace);
        }


        sacaUsuario();
        Log.i(TAG,"cqs ------------->> Número de usuarios: "+sacaUsuario());





            Executor newExecutor = Executors.newSingleThreadExecutor();


            FragmentActivity activity = this;

            final BiometricPrompt myBiometricPrompt = new BiometricPrompt(activity, newExecutor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    } else {
                        Log.d(TAG, "A ocurrido un error");
                    }
                }


                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    Log.d(TAG, "Reconocimiento exitoso");
//                    Intent intent = new Intent(getApplicationContext(), Entrada.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                    finish();
                    if(Integer.parseInt(sacaUsuario().toString())==0){

                        Log.i(TAG,"cqs ------------->> Número de usuarios: "+sacaUsuario());
                        Intent intent = new Intent(getApplicationContext(), Registro.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    }else {

                        Intent intent = new Intent(Bienvenida.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Nombre", sacaUsr());
                        intent.putExtras(bundle);
                        startActivity(intent);

                        runOnUiThread(new Runnable() {
                            public void run() {
                                showToast("Acceso OK");
                            }
                        });
                    }


                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    Log.d(TAG, "Huella no reconocida");
                }


            });

            final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Aplicación con biometría")
                    .setSubtitle("toca el sensor de huellas digitales")
                    //.setDescription("This is the description")
                    .setNegativeButtonText("Cancelar")
                    .build();


            myBiometricPrompt.authenticate(promptInfo);

//        findViewById(R.id.launchAuthentication).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myBiometricPrompt.authenticate(promptInfo);
//            }
//        });


    }


    private String sacaUsuario() {
        String acceso = null;
        // Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh3 = new UsuariosSQLiteHelper3(this);
        db3 = usdbh3.getReadableDatabase();
        String selectQuery = "select count(*) from fp";
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

    private String sacaUsr() {
        String usr = null;
        final String F = "File dbfile";
        // Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh3 = new UsuariosSQLiteHelper3(this);
        db3 = usdbh3.getReadableDatabase();
        String selectQuery = "select user from fp limit 1";
        Cursor cursor = db3.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                usr = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db3.close();
        return usr;
    }

    private String sacaPss() {
        String pass = null;
        final String F = "File dbfile";
        // Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh3 = new UsuariosSQLiteHelper3(this);
        db3 = usdbh3.getReadableDatabase();
        String selectQuery = "select pass from fp limit 1";
        Cursor cursor = db3.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                pass = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db3.close();
        return pass;
    }


}
