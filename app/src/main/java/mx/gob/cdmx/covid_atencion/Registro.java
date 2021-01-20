package mx.gob.cdmx.covid_atencion;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import cz.msebera.android.httpclient.Header;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;
import static mx.gob.cdmx.covid_atencion.Nombre.customURL;

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
    private View mProgressView;

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

    @SuppressLint("MissingPermission")
    public String sacaImei() {
        String szImei;
        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);//Telefono
        szImei = TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE
        if (szImei == null) {
            szImei = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);// Tableta
        }
        return szImei;
    }

    protected void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(this,this));

        Log.i(TAG, " =====> la latitud: " + latitude);

        mProgressView = findViewById(R.id.login_progressMain);
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


                         loginWS(elUsuario,elPassword);

//                         if (paso.matches("1")) {
//
//                             valores(elUsuario,elPassword);
//
//
//                         } else {
//                             showToast("Captura un usuario y/o contraseña válidos");
//                         }

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
            values.put("activo", "1");
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void loginWS(final String user, final String password) {

        showProgress(true);

        RequestParams params = new RequestParams();
        params.put("api", "loginSemanal");
        params.put("usuario", user);
        params.put("pass",password);
        params.put("imei",sacaImei());

        AsyncHttpClient client = new AsyncHttpClient();
        client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
        //client.addHeader("Authorization", "Bearer " + usuario.getToken());
        client.setTimeout(60000);

        RequestHandle requestHandle = client.post(customURL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String nombreStr = "";
                Log.d(TAG, "cqs ----------->> Respuesta OK ");
                Log.d(TAG, "cqs ----------->> ResponseBody" + new String(responseBody));
                try {


                    String json = new String(responseBody);

                    if (json != null && !json.isEmpty()) {

                        Gson gson = new Gson();
                        JSONObject jsonObject = new JSONObject(json);
                        Log.d(TAG, "cqs ----------->> Data: " + jsonObject.get("data"));

                        String login = jsonObject.getJSONObject("response").get("code").toString();
                        Log.d(TAG, "cqs ----------->> login: " + login);

                        String usuario = jsonObject.getJSONObject("data").getJSONObject("user").get("usuario").toString();
                        Log.d(TAG, "cqs ----------->> usuario: " + usuario);
                        String password = jsonObject.getJSONObject("data").getJSONObject("user").get("password").toString();
                        Log.d(TAG, "cqs ----------->> password: " + password);

                        if (Integer.valueOf(login) == 1) {
//                            Log.d(TAG, "cqs ----------->> login: " + "Entra");
//                            Log.d(TAG, "cqs ----------->> login: " + data);

                            if (!Utils.verificaConexion(Registro.this)) {
                                Toast.makeText(getBaseContext(),"Sin conexión inténtalo de nuevo",
                                        Toast.LENGTH_LONG).show();
                                //this.finish();
                            }
                            else{

                                valores(usuario,password);
                            }

                            showProgress(false);

                        } else {
//                            Toast.makeText(Registro.this, "Usuario y/o Contaseña no válidos", Toast.LENGTH_SHORT).show();
                            dialogoBaja();
                            Log.d(TAG, "cqs ----------->> Entrada: " + "No entra");
                        }
                    }

                } catch (Exception e) {
                    showProgress(false);
                    Log.e(TAG, e.getMessage());
//                    Toast.makeText(Registro.this, "Usuario y/o Contaseña no válidos", Toast.LENGTH_SHORT).show();
                    dialogoBaja();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showProgress(false);
                try {
                    Log.e(TAG, "cqs ----------------->> existe un error en la conexi?n -----> " + error.getMessage());
                    if (responseBody != null)
                        Log.d(TAG, "cqs ----------->> " + new String(responseBody));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (statusCode != 200) {
                    Log.e(TAG, "Existe un error en la conexión -----> " + error.getMessage());
                    if (responseBody != null)
                        Log.d(TAG, "pimc -----------> " + new String(responseBody));

                }

                Toast.makeText(Registro.this, "Error de conexión, intente de nuevo", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private String sacaUsuario(String user, String pass) {
        Set<String> set = new HashSet<String>();
        String acceso = null;
        final String F = "File dbfile";
        // Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh2 = new UsuariosSQLiteHelper2(this);
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

    public void dialogoBaja() {
        // timer.cancel();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Registro.this.runOnUiThread(new Runnable() {
            public void run() {
                builder.setMessage("Ponte en contacto con tu supervisor")
                        .setTitle("Usuario No Activo").setCancelable(false)
                        .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                String SQLFprint = "update fp set activo='0' where activo ='1' ";

                                try {

                                    db3.execSQL(SQLFprint);
                                    Log.i("cqs --->> Crea Tabla", "Se crea la tabla: " + "fp");
                                } catch (Exception e) {
                                    String stackTrace = Log.getStackTraceString(e);
                                    Log.i("cqs --->> Crea tabla", "Error al crear la tabla fp" + stackTrace);
                                }



                                finishAffinity();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mUsuario.setVisibility(show ? View.GONE : View.VISIBLE);
            mUsuario.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mUsuario.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mUsuario.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


}
