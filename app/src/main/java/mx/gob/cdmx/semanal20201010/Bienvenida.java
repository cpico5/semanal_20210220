package mx.gob.cdmx.semanal20201010;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import cz.msebera.android.httpclient.Header;
import mx.gob.cdmx.semanal20201010.db.DaoManager;
import mx.gob.cdmx.semanal20201010.model.Datos;

import static mx.gob.cdmx.semanal20201010.Nombre.customURL;
import static mx.gob.cdmx.semanal20201010.Nombre.ALCALDIA;
import static mx.gob.cdmx.semanal20201010.Nombre.customURLcatalogos;

public class Bienvenida extends AppCompatActivity {

    private static final String TAG = Bienvenida.class.getName();
    UsuariosSQLiteHelper usdbh;
    private SQLiteDatabase db;
    UsuariosSQLiteHelper2 usdbh2;
    private SQLiteDatabase db2;
    UsuariosSQLiteHelper3 usdbh3;
    private SQLiteDatabase db3;
    double latitude;
    double longitude;
    private DaoManager daoManager;

    public String maximo = "";
    int elMaximo;

    private View mProgressView;

    Nombre nom = new Nombre();
    String nombreEncuesta = nom.nombreEncuesta();
    String upLoadServerUriBase = "https://opinion.cdmx.gob.mx/cgi-bin/php/recibeBases" + nombreEncuesta + ".php?encuesta=" + nombreEncuesta + "";
    String upLoadServerUriAudio = "https://opinion.cdmx.gob.mx/cgi-bin/php/recibeAudios" + nombreEncuesta + ".php?encuesta=" + nombreEncuesta + "";
    int serverResponseCode = 0;

    String token;

    Calendar c = Calendar.getInstance();

    SimpleDateFormat df1 = new SimpleDateFormat("yyyMMdd");
    String formattedDate1 = df1.format(c.getTime());

    SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
    String formattedDate2 = df2.format(c.getTime());

    SimpleDateFormat df3 = new SimpleDateFormat("yyy-MM-dd");
    String formattedDate3 = df3.format(c.getTime());

    SimpleDateFormat df4 = new SimpleDateFormat("yyy-MM-dd");
    String formattedDateFecha = df4.format(c.getTime());

    public String sacaImei() {
        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            String szImei = TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE
            System.out.println("Mi Número: " + szImei);
            return szImei;
        }
        String szImei = TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE
        System.out.println("Mi Número: " + szImei);
        return szImei;
    }


    protected void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

    }

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bienvenida);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= 23) {
                ActivityCompat.requestPermissions(Bienvenida.this,
                        new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.INTERNET,
                                Manifest.permission.ACCESS_NETWORK_STATE,
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.LOCATION_HARDWARE,
                                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                                Manifest.permission.MODIFY_PHONE_STATE,
                                Manifest.permission.SYSTEM_ALERT_WINDOW,
                                Manifest.permission.PROCESS_OUTGOING_CALLS,
                                Manifest.permission.CALL_PHONE,
                                Manifest.permission.ACCESS_WIFI_STATE},
                        1);
            }

        } else {

            usdbh3 = new UsuariosSQLiteHelper3(this);
            db3 = usdbh3.getReadableDatabase();
        }

        mProgressView = findViewById(R.id.login_progressMain);

//        showProgress(true);

        String SQLFprint = "CREATE TABLE fp (" +
                "id integer primary key autoincrement," +
                "user TEXT NOT NULL," +
                "pass TEXT NOT NULL," +
                "activo INTEGER NOT NULL );";

        try {

            db3.execSQL(SQLFprint);
            Log.i("cqs --->> Crea Tabla", "Se crea la tabla: " + "fp");
        } catch (Exception e) {
            String stackTrace = Log.getStackTraceString(e);
            Log.i("cqs --->> Crea tabla", "Error al crear la tabla fp" + stackTrace);
        }


        sacaUsuario();
        Log.i(TAG, "cqs ------------->> Número de usuarios onCreate: " + sacaUsuario());

        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(this,this));

        ///////////////// actualiza catalogos
        elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;
        String elMaximoFecha = String.valueOf(elMaximo);

        if (elMaximoFecha.matches("1")) {
            Log.i(TAG, " =====> El numero inicial: " + elMaximoFecha);
            Log.i(TAG, " =====> El nombre de la encuesta: " + nombreEncuesta);
            Log.i(TAG, " =====> Cuantas secciones hay: " + sacaCuantosSecciones());
            int haySecciones = Integer.parseInt(sacaCuantosSecciones());
            if (haySecciones == 0) {
                catalogoSeccionesWS(nombreEncuesta);
            }
        }

        ////////// finaliza actualiza catalogos


        if (!verificaConexion(this)) {
            Toast.makeText(getBaseContext(), "Sin conexión",
                    Toast.LENGTH_LONG).show();
            //this.finish();
        } else {

            new uploadData.UpdateBases().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, sacaImei());
            new uploadData.UpdateAudios().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//            Log.i(TAG,"cqs ------------>> TOKEN A PASAR: "+ObtenerToken());
//            ObtenerToken();
//            NotificacionIDTokenService notificacionIDTokenService = new NotificacionIDTokenService();
//            notificacionIDTokenService.onTokenRefresh();
        }


        Executor newExecutor = Executors.newSingleThreadExecutor();


        FragmentActivity activity = this;

        final BiometricPrompt myBiometricPrompt = new BiometricPrompt(activity, newExecutor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {

                    Log.i(TAG, "cqs ------->>  Pulse el botón cancelar");
                    finishAffinity();

                } else {
                    Log.i(TAG, "cqs ------->> A ocurrido un error");
                    finishAffinity();
                }

//                    if (errorCode == BiometricPrompt.ERROR_USER_CANCELED) {
//                        Log.i(TAG, "cqs ------->>  Pulse afuera del cuadro");
//                    }

            }


            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Log.d(TAG, "Reconocimiento exitoso");
//                    Intent intent = new Intent(getApplicationContext(), Entrada.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                    finish();

                /*Si tiene internet y si es activo, pasa a sacar usuario, si no tiene internet, sigue a sacar usuario, si tine internet, verifica que este activo*/

                if (!verificaConexion(Bienvenida.this)) {
//                        Toast.makeText(getBaseContext(), "Sin conexión", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "cqs ------------->> Sin conexión: " + sacaUsuario());
//                    showProgress(false);
                    dialogoConexion();
                } else {

                    Log.i(TAG, "cqs ------------->> Con conexión: " + sacaUsuario());

                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    Runnable myRunnable = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                usuarioWS(sacaUsr().toString(), sacaPss().toString());
                            } catch (Exception e) {
                                Log.i(TAG, "cqs ------------->> Error usuarioWS va para registro: " + sacaUsuario());
                                Intent intent = new Intent(getApplicationContext(), Registro.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }

                        }
                    };
                    mainHandler.post(myRunnable);
                }


//                    if(Integer.parseInt(sacaUsuario().toString())==0){
//
//                        Log.i(TAG,"cqs ------------->> Número de usuarios: "+sacaUsuario());
//                        Intent intent = new Intent(getApplicationContext(), Registro.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                        finish();
//
//                    }else {
//
//                        Intent intent = new Intent(Bienvenida.this, MainActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("Nombre", sacaUsr());
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//
//                        runOnUiThread(new Runnable() {
//                            public void run() {
//                                showToast("Acceso OK");
//                            }
//                        });
//                    }


            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.d(TAG, "Huella no reconocida");
            }


        });

        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Use su huella para acceder")
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

    private Integer sacaActivo() {
        Integer activo = null;
        final String F = "File dbfile";
        // Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh3 = new UsuariosSQLiteHelper3(this);
        db3 = usdbh3.getReadableDatabase();
        String selectQuery = "select activo from fp limit 1";
        Cursor cursor = db3.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                activo = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db3.close();
        return activo;
    }

    /////// METODO PARA VERIFICAR LA CONEXIÓN A INTERNET
    public static boolean verificaConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        // No sólo wifi, también GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        // este bucle debería no ser tan ñapa
        for (int i = 0; i < 2; i++) {
            // ¿Tenemos conexión? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }

    /*Obtener el ID Firebase*/

//    public String ObtenerToken(){
//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @RequiresApi(api = Build.VERSION_CODES.O)
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "getInstanceId failed", task.getException());
//                            return;
//                        }
//
//                        token= task.getResult().getToken();
//
//                        if(token==null){
//                            token="sin token";
//                        }else {
//                            token = task.getResult().getToken();
//                            Log.i(TAG,"cqs -------------->> El token Bienvenida: "+ token);
//                            tokenWS(sacaUsr().toString(),sacaPss().toString(),token);
//
//                            // Log and toast
//                            String msg = getString(R.string.msg_token_fmt, token);
//                            Log.d(TAG, msg);
//                            Toast.makeText(Bienvenida.this, msg, Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//        return token;
//
//    }

    /*Saca usuario WebService*/

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void usuarioWS(final String user, final String password ) {

//        showProgress(true);

        RequestParams params = new RequestParams();
        params.put("api", "loginSemanal");
        params.put("usuario", user);
        params.put("pass", password);
        params.put("imei", sacaImei());

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
                            Log.d(TAG, "cqs ----------->> login: " + "Entra");
                            if (Integer.parseInt(sacaUsuario().toString()) == 0) {

                                Log.i(TAG, "cqs ------------->> Número de usuarios: " + sacaUsuario());
                                Intent intent = new Intent(getApplicationContext(), Registro.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();

                            } else if (Integer.parseInt(sacaUsuario().toString()) != 0) {

                                Integer activo = sacaActivo();

                                if (activo == 0) {
                                    activ();

                                    /*
                                    /*si el usuario el igual con 1 de pruebas va directo a MainActivity
                                    /*si no, verifica que esté en el poligono de la alcaldia
                                    */

                                    if(user.equals("1")){
                                        pasaEncuesta();
                                    }else {
                                        if(ALCALDIA.matches("Todas")){
                                            pasaEncuesta();
                                        }else{
                                            seccionWS(user);
                                        }
                                    }
                                    /*ESTAS PARTE PARA PROBAR TODAS LAS ALCALDIAS*/
//                                    if (ALCALDIA.matches("Todas")) {
//                                        pasaEncuesta();
//                                    } else {
//                                        seccionWS(user);
//                                    }
                                } else {
                                    /*
                                    /*si el usuario el igual con 1 de pruebas va directo a MainActivity
                                    /*si no, verifica que esté en el poligono de la alcaldia
                                    */
                                    if(user.equals("1")){
                                        pasaEncuesta();
                                    }else {
                                        if(ALCALDIA.matches("Todas")){
                                            pasaEncuesta();
                                        }else{
                                            seccionWS(user);
                                        }
                                    }

                                    /*ESTAS PARTE PARA PROBAR TODAS LAS ALCALDIAS*/
//                                    if (ALCALDIA.matches("Todas")) {
//                                        pasaEncuesta();
//                                    } else {
//                                        seccionWS(user);
//                                    }
                                }


                            }
                        } else {
                            dialogoBaja();
                            Log.d(TAG, "cqs ----------->> Entrada: " + "No entra");
                        }
                    }

                } catch (Exception e) {
//                    showProgress(false);
                    String stackTrace = Log.getStackTraceString(e);
                    Log.i("cqs ---------->> FALLA","FALLA: "+ stackTrace);
                    dialogoBaja();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                showProgress(false);
                try {
                    Log.e(TAG, "cqs ----------------->> existe un error en la conexión -----> " + error.getMessage());
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

                Toast.makeText(Bienvenida.this, "Error de conexión, intente de nuevo", Toast.LENGTH_SHORT).show();
//                showProgress(true);
                dialogoErrorConexion();
//                finishAffinity();

            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void seccionWS(final String user) {

//        showProgress(true);

        GPSTracker gps = new GPSTracker(this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        if (latitude == 0.0) {
            if (sacaLatitud() != null) {
                latitude = Double.valueOf(sacaLatitud());
            } else {
                latitude = 0.0;
            }
        }

        if (longitude == 0.0) {
            if (sacaLongitud() != null) {
                longitude = Double.valueOf(sacaLongitud());
            } else {
                longitude = 0.0;
            }
        }

        String strLatitud = String.valueOf(latitude);
        String strLongitud = String.valueOf(longitude);
        String laAlcaldia=ALCALDIA;

        if (ALCALDIA == "Álvaro Obregón") {
            laAlcaldia = "10";
        } else if (ALCALDIA == "Azcapotzalco") {
            laAlcaldia = "2";
        } else if (ALCALDIA == "Benito Juárez") {
            laAlcaldia = "14";
        } else if (ALCALDIA == "Coyoacán") {
            laAlcaldia = "3";
        } else if (ALCALDIA == "Cuajimalpa de Morelos") {
            laAlcaldia = "4";
        } else if (ALCALDIA == "Cuauhtémoc") {
            laAlcaldia = "15";
        } else if (ALCALDIA == "Gustavo A. Madero") {
            laAlcaldia = "5";
        } else if (ALCALDIA == "Iztacalco") {
            laAlcaldia = "6";
        } else if (ALCALDIA == "Iztapalapa") {
            laAlcaldia = "7";
        } else if (ALCALDIA == "La Magdalena Contreras") {
            laAlcaldia = "8";
        } else if (ALCALDIA == "Miguel Hidalgo") {
            laAlcaldia = "16";
        } else if (ALCALDIA == "Milpa Alta") {
            laAlcaldia = "9";
        } else if (ALCALDIA == "Tláhuac") {
            laAlcaldia = "11";
        } else if (ALCALDIA == "Tlalpan") {
            laAlcaldia = "12";
        } else if (ALCALDIA == "Venustiano Carranza") {
            laAlcaldia = "17";
        } else if (ALCALDIA == "Xochimilco") {
            laAlcaldia = "13";
        }

        RequestParams params = new RequestParams();
        params.put("api", "dentroSeccion");
        params.put("usuario",user);
        params.put("alcaldia",laAlcaldia);
        params.put("latitud", strLatitud);
        params.put("longitud", strLongitud);
        params.put("imei", sacaImei());

        AsyncHttpClient client = new AsyncHttpClient();
        client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
        //client.addHeader("Authorization", "Bearer " + usuario.getToken());
        client.setTimeout(60000);

        RequestHandle requestHandle = client.post(customURL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String nombreStr = "";
                Log.d(TAG, "cqs ----------->> Respuesta OK ");
                Log.d(TAG, "cqs ----------->> ALCALDIA: "+ALCALDIA);
                Log.d(TAG, "cqs ----------->> ResponseBody" + new String(responseBody));
                try {


                    String json = new String(responseBody);

                    if (json != null && !json.isEmpty()) {

                        Gson gson = new Gson();
                        JSONObject jsonObject = new JSONObject(json);
                        Log.d(TAG, "cqs ----------->> Data: " + jsonObject.get("data"));

                        String code = jsonObject.getJSONObject("response").get("code").toString();
                        Log.d(TAG, "cqs ----------->> code alcaldia: " + code);

                        String esta = jsonObject.getJSONObject("data").get("esta").toString();
                        Log.d(TAG, "cqs ----------->> esta: " + esta);
//                        String password = jsonObject.getJSONObject("data").getJSONObject("user").get("password").toString();
//                        Log.d(TAG, "cqs ----------->> password: " + password);

                        if (Integer.valueOf(code) == 1) {
                            Log.d(TAG, "cqs ----------->> Entrada: " + "Entra a seccion");

                            pasaEncuesta();

                        } else {
                            dialogoFueraLugar();
                            Log.d(TAG, "cqs ----------->> Entrada: " + "No entra a seccion");
                        }
                    }

                } catch (Exception e) {
//                    showProgress(false);
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(Bienvenida.this, "Usuario y/o Contaseña no válidos", Toast.LENGTH_SHORT).show();
                    dialogoFueraLugar();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                showProgress(false);
                try {
                    Log.e(TAG, "cqs ----------------->> existe un error en la conexión secciones -----> " + error.getMessage());
                    if (responseBody != null)
                        Log.d(TAG, "cqs ----------->> " + new String(responseBody));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (statusCode != 200) {
                    Log.e(TAG, "Existe un error en la conexión secciones-----> " + error.getMessage());
                    if (responseBody != null)
                        Log.d(TAG, "cqs ----------->> " + new String(responseBody));

                }

                Toast.makeText(Bienvenida.this, "Error de conexión, intente de nuevo", Toast.LENGTH_SHORT).show();
                finishAffinity();

            }
        });
    }

    /*Saca usuario WebService*/

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void tokenWS(final String user, final String password,final String token_firebase ) {

//        showProgress(true);

        RequestParams params = new RequestParams();
        params.put("api", "token_firebase");
        params.put("usuario", user);
        params.put("pass", password);
        params.put("imei", sacaImei());
        params.put("token_firebase", token_firebase);

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
                        String token_fire = jsonObject.getJSONObject("data").getJSONObject("user").get("token_firebase").toString();
                        Log.d(TAG, "cqs ----------->> Token Firebase: " + token_fire);

                        if (Integer.valueOf(login) == 1) {
                            Log.d(TAG, "cqs ----------->> login: " + "Entra");
                            if (Integer.parseInt(sacaUsuario().toString()) == 0) {


                            } else if (Integer.parseInt(sacaUsuario().toString()) != 0) {

                            }
                        } else {

                            Log.d(TAG, "cqs ----------->> Entrada: " + "No entra");
                        }
                    }

                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                showProgress(false);
                try {
                    Log.e(TAG, "cqs ----------------->> existe un error en la conexión -----> " + error.getMessage());
                    if (responseBody != null)
                        Log.d(TAG, "cqs ----------->> " + new String(responseBody));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (statusCode != 200) {
                    Log.e(TAG, "Existe un error en la conexión -----> " + error.getMessage());
                    if (responseBody != null)
                        Log.d(TAG, "cqs -----------> " + new String(responseBody));

                }

                Toast.makeText(Bienvenida.this, "Error de conexión, intente de nuevo", Toast.LENGTH_SHORT).show();
                finishAffinity();

            }
        });
    }

    public void dialogoBaja() {
        // timer.cancel();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Bienvenida.this.runOnUiThread(new Runnable() {
            public void run() {
                builder.setMessage("Ponte en contacto con tu supervisor")
                        .setTitle("El usuario no esta Activo").setCancelable(false)
                        .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Log.i("cqs --->> Actualiza", "Entra P: " + "dialogo");
                                noActiv();
                                finishAffinity();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }

    public void dialogoFueraLugar() {
        // timer.cancel();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Bienvenida.this.runOnUiThread(new Runnable() {
            public void run() {
                builder.setMessage("Ponte en contacto con tu supervisor")
                        .setTitle("No te encuentras en la alcadia que corresponde").setCancelable(false)
                        .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                finishAffinity();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }


    public void dialogoConexion() {
        // timer.cancel();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Bienvenida.this.runOnUiThread(new Runnable() {
            public void run() {
                builder.setMessage("Sin conexiòn a internet")
                        .setTitle("Importante").setCancelable(false)
                        .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                if (Integer.parseInt(sacaUsuario().toString()) == 0) {

                                    Log.i(TAG, "cqs ------------->> Número de usuarios: " + sacaUsuario());
                                    Intent intent = new Intent(getApplicationContext(), Registro.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();

                                } else {

                                    pasaEncuesta();
                                }

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }

    public void dialogoErrorConexion() {
        // timer.cancel();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Bienvenida.this.runOnUiThread(new Runnable() {
            public void run() {
                builder.setMessage("Existe un error en la conexión \n" +
                        "se trabajará localmente")
                        .setTitle("Aviso").setCancelable(false)
                        .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                if (Integer.parseInt(sacaUsuario().toString()) == 0) {

                                    Log.i(TAG, "cqs ------------->> Número de usuarios: " + sacaUsuario());
                                    Intent intent = new Intent(getApplicationContext(), Registro.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();

                                } else {

                                    pasaEncuesta();
                                }

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }

    public void pasaEncuesta() {
        Intent intent = new Intent(Bienvenida.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    private void showProgress(final boolean show) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            mUsuario.setVisibility(show ? View.GONE : View.VISIBLE);
//            mUsuario.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mUsuario.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });
//
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mProgressView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mUsuario.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
//    }

    public void activ() {
        String SQLFprint = "update fp set activo='1' where activo ='0' ";

        try {

            usdbh3 = new UsuariosSQLiteHelper3(Bienvenida.this);
            db3 = usdbh3.getReadableDatabase();

            db3.execSQL(SQLFprint);
            Log.i("cqs --->> Actualiza", "Se Actualiza el usuario: " + "fp");
        } catch (Exception e) {
            String stackTrace = Log.getStackTraceString(e);
            Log.i("cqs --->>", "Error al actualizar el usuario" + stackTrace);
        }
    }

    public void noActiv() {
        String SQLFprint = "update fp set activo='0' where activo ='1' ";

        try {

            usdbh3 = new UsuariosSQLiteHelper3(Bienvenida.this);
            db3 = usdbh3.getReadableDatabase();

            db3.execSQL(SQLFprint);
            Log.i("cqs --->> Actualiza", "Se Actualiza el usuario: " + "fp");
        } catch (Exception e) {
            String stackTrace = Log.getStackTraceString(e);
            Log.i("cqs --->>", "Error al actualizar el usuario" + stackTrace);
        }
    }


    //Enviar Base
    public int uploadBase(String sourceFileUri) {

        File sdCard;
        sdCard = Environment.getExternalStorageDirectory();
        final String pathBase = sdCard.getAbsolutePath() + "/Mis_archivos";

        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

//				             dialog.dismiss();
            Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + pathBase + "" + "/" + "20161124_002_359083065132816_1.jpg");
            runOnUiThread(new Runnable() {
                public void run() {

                }
            });

            return 0;

        } else {
            try {
                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUriBase);
                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\""
                        + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("TAG", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {

                    runOnUiThread(new Runnable() {
                        public void run() {

                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                    + " http://www.androidexample.com/media/uploads/"
                                    + "20161124_002_359083065132816_1.jpg";

//				                              Toast.makeText(Entrada.this, "File Upload Complete."+msg,Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

//				                dialog.dismiss();
                ex.printStackTrace();


                Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + "Upload file to server " + "error: " + ex.getMessage());

//				                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

//				                dialog.dismiss();
                e.printStackTrace();

                Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + "Upload file to server Exception " + "Exception : " + e.getMessage());

//				                Log.e("Upload file to server Exception", "Exception : "
//				                                                 + e.getMessage(), e);
            }
            return serverResponseCode;

        } // End else block
    }

    class UpdateBases extends AsyncTask<String, Float, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... params) {
            File sdCard;
            sdCard = Environment.getExternalStorageDirectory();
            final String pathBase = sdCard.getAbsolutePath() + "/Mis_archivos";
            String sDirectorio = pathBase;
            final File f = new File(sDirectorio);
            Log.i(TAG, "lista" + pathBase);
            final String customURL = "https://opinion.cdmx.gob.mx/cgi-bin/bases/";
            Log.i(TAG, " =====> lista 1: " + pathBase);
            File F = new File(pathBase);
            try {
                if (F.exists()) {
                    File[] ficheros = F.listFiles();
                    for (int i = 0; i < ficheros.length; i++) {
                        //Simulamos cierto retraso
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                        }
                        publishProgress(i / (float) (ficheros.length)); //Actualizamos los valores
                    }
                    String[] s = new String[ficheros.length];
                    String[] t = new String[ficheros.length];
                    for (int x = 0; x < ficheros.length; x++) {
                        Log.i(TAG, " =====> lista: " + ficheros[x].getName());
                        s[x] = pathBase + "/" + nombreEncuesta + "_" + sacaImei();
                        Log.i(TAG, " =====> Nombre del Archivo: " + s[x]);
                        uploadBase(s[x]);
                    }
                } else {
                    Log.i(TAG, " =====> lista 2: " + "No existe el directorio");
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.i(TAG, " =====> error zip: " + "_" + e.getMessage());
            }
            return null;
        }

        //tomo
        protected void onPostExecute(String date2) {
            super.onPostExecute(date2);
            Toast.makeText(getApplicationContext(), "Archivo Enviado", Toast.LENGTH_LONG).show();
        }
    }

    //Enviar Audios
    class UpdateAudios extends AsyncTask<String, Float, String> {

        protected void onPreExecute() {
            super.onPreExecute();

//					dialog = new ProgressDialog(CalendarViewFotos.this);
//			        dialog.setMessage("Enviando Fotografías...");
//			        dialog.setTitle("Progreso");
//			        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//			        dialog.setCancelable(false);
//			        dialog.setProgress(0);
//			        dialog.setMax(100);
//			        dialog.show(); //Mostramos el diálogo antes de comenzar
        }


        @Override
        protected String doInBackground(String... params) {


            File sdCard;
            sdCard = Environment.getExternalStorageDirectory();
//						final String pathAudios = sdCard.getAbsolutePath() + "/" + nombreEncuesta+"-Audio"+date2;
            final String pathAudios = sdCard.getAbsolutePath() + "/" + nombreEncuesta + "-Audio" + formattedDate3 + "/";

            String sDirectorio = pathAudios;
            final File f = new File(sDirectorio);
            Log.i(TAG, "lista" + pathAudios);

//						final String customURL = "https://opinion.cdmx.gob.mx/cgi-bin/fotos/programas_sociales/";
            final String customURL = "https://opinion.cdmx.gob.mx/audios/" + nombreEncuesta + "/";

            Log.i(TAG, " =====> URL audios: " + customURL);
            Log.i(TAG, " =====> lista audios 1: " + pathAudios);

            File F = new File(pathAudios);

            try {

                if (F.exists()) {

                    File[] ficheros = F.listFiles();

                    for (int i = 0; i < ficheros.length; i++) {
                        //Simulamos cierto retraso
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                        }

                        publishProgress(i / (float) (ficheros.length)); //Actualizamos los valores
                    }


                    String[] s = new String[ficheros.length];
                    String[] t = new String[ficheros.length];
                    for (int x = 0; x < ficheros.length; x++) {
                        Log.i(TAG, " =====> lista audios: " + ficheros[x].getName());
                        s[x] = pathAudios + "/" + ficheros[x].getName();
                        t[x] = ficheros[x].getName();

//								 uploadFotos(s[x],date2);


                        URL u = new URL(customURL + t[x]);
                        Log.i(TAG, " =====> Archivo Audios custom: " + customURL + t[x]);
                        HttpURLConnection huc = (HttpURLConnection) u.openConnection();
                        huc.setRequestMethod("GET");  //OR  huc.setRequestMethod ("HEAD");
                        huc.connect();
                        huc.getResponseCode();
                        Log.i(TAG, " =====> Archivo:  lista De Audios ==>" + huc.getResponseCode());
                        if (huc.getResponseCode() == 200) {

                            //moveFile(pathFotosN, t[x], pathFotosF);
                            Log.i(TAG, " =====> Archivo:  En el servidor custom no hace nada==>" + t[x]);

                        } else if (huc.getResponseCode() == 404) {

                            uploadAudios(s[x]);
                            Log.i(TAG, " =====> Archivo:  Enviado al servidor custom==>" + t[x]);


                        }

                    }
                    // first parameter is d files second parameter is zip file name

                } else {
                    Log.i(TAG, " =====> lista 2: " + "No existe el directorio");
                }
                // first parameter is d files second parameter is zip file name

            } catch (Exception e) {
                String stackTrace = Log.getStackTraceString(e);
                Log.i("Manda Audios", "Error Manda Audios" + stackTrace);
            }


            return null;
        }


//				protected void onProgressUpdate (Float... valores) {
//			          int p = Math.round(100*valores[0]);
//			          dialog.setProgress(p);
//			      }


        //tomo
        protected void onPostExecute(String date2) {
            super.onPostExecute(date2);
//					dialog.dismiss();

            //	Toast.makeText(CalendarViewFotos.this, "Envío de Fotografias completo ",Toast.LENGTH_LONG).show();

//					correo(date2, prefix);
//					correo(date2, sacaChip());

        }

    }

    private String sacaLatitud() {
        Set<String> set = new HashSet<String>();
        String acceso = null;
        final String F = "File dbfile";
// Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh3 = new UsuariosSQLiteHelper3(this);
        db3 = usdbh3.getReadableDatabase();
        String selectQuery = "select latitud from ubicacion order by id desc limit 1";
        Cursor cursor = db3.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                acceso = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
// db.close();

        return acceso;
    }

    private String sacaLongitud() {
        Set<String> set = new HashSet<String>();
        String acceso = null;
        final String F = "File dbfile";
// Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh3 = new UsuariosSQLiteHelper3(this);
        db3 = usdbh3.getReadableDatabase();
        String selectQuery = "select longitud from ubicacion order by id desc limit 1";
        Cursor cursor = db3.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                acceso = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
// db.close();

        return acceso;
    }

    public int uploadAudios(String sourceFileUri) {

        File sdCard;
        sdCard = Environment.getExternalStorageDirectory();
        //final String pathFotos = sdCard.getAbsolutePath() + "/"+ nombreEncuesta+"-Audio"+fech;
        final String pathAudios = sdCard.getAbsolutePath() + nombreEncuesta + "-Audio" + formattedDate3 + "/";

        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

//			     dialog.dismiss();
            Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + pathAudios + "" + "/" + "20161124_002_359083065132816_1.jpg");
            runOnUiThread(new Runnable() {
                public void run() {

                }
            });

            return 0;

        } else {
            try {
                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUriAudio);
                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\""
                        + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {

                    runOnUiThread(new Runnable() {
                        public void run() {

                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                    + " http://www.androidexample.com/media/uploads/"
                                    + "20161124_002_359083065132816_1.jpg";

//			                      Toast.makeText(Entrada.this, "File Upload Complete."+msg,Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

//			        dialog.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
//			                messageText.setText("MalformedURLException Exception : check script url.");
//			                Toast.makeText(CalendarViewFotos.this, "MalformedURLException",
//			                                                    Toast.LENGTH_SHORT).show();
                    }
                });

                Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + "Upload file to server " + "error: " + ex.getMessage());

//			        Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

//			        dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
//			                messageText.setText("Error de Internet");
//			                Toast.makeText(CalendarViewFotos.this, "Error de Internet",
//			                        Toast.LENGTH_SHORT).show();
                    }
                });
                Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + "Upload file to server Exception " + "Exception : " + e.getMessage());

//			        Log.e("Upload file to server Exception", "Exception : "
//			                                         + e.getMessage(), e);
            }
            return serverResponseCode;

        } // End else block
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

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
        }
    }

    ////////////// ´para actulizal los catalogo por fecha y si están vacios

    private String sacaMaximo() {
        Set<String> set = new HashSet<String>();
        final String F = "File dbfile";
        // Abrimos la base de datos 'DBUsuarios' en modo escritura
        String DATABASE_NAME = Environment.getExternalStorageDirectory() +"/Mis_archivos/" +nombreEncuesta+"_"+sacaImei()+"";
        usdbh = new UsuariosSQLiteHelper(this, "F", null, 1,DATABASE_NAME);
        db = usdbh.getReadableDatabase();
        String selectQuery = "SELECT count(*) FROM encuestas where fecha='" + formattedDate3 + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                maximo = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return maximo;
    }

    private String sacaCuantosSecciones() {
        String cuantos = null;
        // Abrimos la base de datos en modo lectura
        usdbh2 = new UsuariosSQLiteHelper2(this);
        db2 = usdbh2.getReadableDatabase();
        String selectQuery = "SELECT count(*) FROM datos";
        Cursor cursor = db2.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                cuantos = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db2.close();
        return cuantos;
    }


    private void catalogoSeccionesWS(String laEncuesta){

        RequestParams params = new RequestParams();
        params.put("api", "secciones");
        params.put("encuesta", laEncuesta);

        AsyncHttpClient client = new AsyncHttpClient();
        client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
        //client.addHeader("Authorization", "Bearer " + usuario.getToken());
        client.setTimeout(60000);

        RequestHandle requestHandle = client.post(customURLcatalogos, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                showProgress(false);
                Log.d(TAG, "cqs -----------> Respuesta catalogo secciones OK ");
                Log.d(TAG, "cqs -----------> " + new String(responseBody));

                try {

                    String json = new String(responseBody);

                    if (json != null && !json.isEmpty()) {

                        Gson gson = new Gson();
                        JSONObject jsonObject = new JSONObject(json);
                        Log.d(TAG, "cqs ----------->> Data: " + jsonObject.get("data"));

                        String login = jsonObject.getJSONObject("response").get("code").toString();
                        Log.d(TAG, "cqs ----------->> Code: " + login);
                        if (Integer.valueOf(login) == 1) {

                            Log.d(TAG, "cqs ----------->> Code estoy dentro secciones:  " + login);
                            //obtiene usuarios
                            String jsonSecciones = jsonObject.getJSONObject("data").getJSONArray("secciones").toString();
                            Type collectionType = new TypeToken<List<Datos>>() {}.getType();
                            List<Datos> listaSecciones = gson.fromJson(jsonSecciones, collectionType);

                            Log.d(TAG, "cqs ----------->> listaSecciones:  " + listaSecciones);

                            usdbh2 = new UsuariosSQLiteHelper2(Bienvenida.this);
                            db2 = usdbh2.getReadableDatabase();
                            daoManager = new DaoManager(db2);
                            if(listaSecciones != null && !listaSecciones.isEmpty()){
                                daoManager.delete(Datos.class);
                                for(Datos secciones :listaSecciones ){
                                    daoManager.insert(secciones);
                                }
                            }

                        } else {
                            Toast.makeText(Bienvenida.this, "Consulta incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
//                    showProgress(false);
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(Bienvenida.this, "Response Incorrecto", Toast.LENGTH_SHORT).show();
                    String stackTrace = Log.getStackTraceString(e);
                    Log.i(TAG,"cqs ----------->> Response incorrecto: "+ stackTrace);
                }



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showProgress(false);
                try {
                    Log.e(TAG, "cqs-----------------> existe un error en la conexion -----> " + error.getMessage());
                    if (responseBody != null)
                        Log.d(TAG, "cqs -----------> " + new String(responseBody));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (statusCode != 200) {
                    Log.e(TAG, "Existe un error en la conexion -----> " + error.getMessage());
                    if (responseBody != null)
                        Log.d(TAG, "cqs -----------> " + new String(responseBody));

                }

                Toast.makeText(Bienvenida.this, "Error de conexion al servidor\n inténtelo de nuevo", Toast.LENGTH_SHORT).show();

            }
        });

    }



}
