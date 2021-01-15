package mx.gob.cdmx.semanal_20210116;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings.Secure;

import androidx.core.app.ActivityCompat;

import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import cz.msebera.android.httpclient.Header;
import mx.gob.cdmx.semanal_20210116.db.DaoManager;
import mx.gob.cdmx.semanal_20210116.model.DatoContent;
import mx.gob.cdmx.semanal_20210116.model.Datos;
import mx.gob.cdmx.semanal_20210116.model.Usuarios;
import mx.gob.cdmx.semanal_20210116.service.Alarm;
import mx.gob.cdmx.semanal_20210116.service.WifiState;

import static mx.gob.cdmx.semanal_20210116.Nombre.USUARIO;
import static mx.gob.cdmx.semanal_20210116.Nombre.customURL;
import static mx.gob.cdmx.semanal_20210116.Nombre.customURLcatalogos;
import static mx.gob.cdmx.semanal_20210116.Nombre.encuesta;

public class MainActivity extends Activity implements OnItemSelectedListener, AdapterView.OnItemClickListener {


    private static final String TAG = "MainActivity";
    Random random = new Random();
    public int rand;

    UsuariosSQLiteHelper2 sqlite_obj;
    List<String> listUsuario, listPassword;
    private Usuarios usuario;

    Nombre nom = new Nombre();
    String nombreEncuesta = nom.nombreEncuesta();
    String upLoadServerUriBase = "https://opinion.cdmx.gob.mx/cgi-bin/php/recibeBases" + nombreEncuesta + ".php?encuesta=" + nombreEncuesta + "";
    String upLoadServerUriAudio = "https://opinion.cdmx.gob.mx/cgi-bin/php/recibeAudios" + nombreEncuesta + ".php?encuesta=" + nombreEncuesta + "";
    int serverResponseCode = 0;
    private View mProgressView;
    private View mProgressView2;

    AutoCompleteTextView autoCompleteTextView;


    private DaoManager daoManager;
    private EditText txtCodigo;
    private EditText txtNombre;
    private TextView txtResultado;
    private TextView txtEncuesta;
    private TextView txtEquipo;

    private Button btnInsertar;
    private Button btnActualizar;
    private Button btnEliminar;
    private Button btnConsultar;
    private Button btnSiguiente;
    private Button btnEnviar;
    private Button btnRechazo;
    private Button btnRechazo2;
    private Button btnInformacion;
    public ProgressBar progressBar;
    private Button btnCatalogos;

    private SQLiteDatabase db;

    public String encuestador;
    public String credencial;
    public String seccion;
    public String equipo;

    UsuariosSQLiteHelper3 usdbh3;
    private SQLiteDatabase db3;

    UsuariosSQLiteHelper2 usdbh2;
    private SQLiteDatabase db2;

    public EditText Usuario;
    public String tablet;
    public String encuestaQuien;
    public String pasoUsuario;

    public String Secc;
    public String cuantasSecciones;
    public String quienEncuesta;
    final String path = "/mnt/sdcard/Mis_archivos/";
    final String pathZip = "/mnt/sdcard/";
    // final String pathAudio="/mnt/sdcard/Audio/";

    double latitude;
    double longitude;

    public String maximo = "";
    int elMaximo;

    private static final String LOG_TAG = "Grabadora";
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;

    private String audio;
    private Handler handler;

    private Button btnCargar;


    public static String getHostName(String defValue) {
        try {
            Method getString = Build.class.getDeclaredMethod("getString", String.class);
            getString.setAccessible(true);
            return getString.invoke(null, "net.hostname").toString();
        } catch (Exception ex) {
            return defValue;
        }
    }

    static String ID = getHostName(null);
    static String prefix = ID;

    //	static String prefix = ID;

    public EditText editUsuario;

    public String str;

    ProgressDialog dialog = null;

    private static final int READ_BLOCK_SIZE = 2000000;

    UsuariosSQLiteHelper usdbh;
    private Spinner spinner;
    private Spinner spinner2;
    private Spinner spinner_seccion;
    UsuariosSQLiteHelper Udb;
    List<String> list;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    private WifiState wifiState;

//    private Usuarios usuario;

    Calendar c = Calendar.getInstance();

    SimpleDateFormat df1 = new SimpleDateFormat("yyyMMdd");
    String formattedDate1 = df1.format(c.getTime());

    SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
    String formattedDate2 = df2.format(c.getTime());

    SimpleDateFormat df3 = new SimpleDateFormat("yyy-MM-dd");
    String formattedDate3 = df3.format(c.getTime());

//    SimpleDateFormat df4 = new SimpleDateFormat("HH:mm:ss a");
//    String formattedDate4 = df4.format(c.getTime());

    SimpleDateFormat df5 = new SimpleDateFormat("HH:mm:ss");
    String formattedDate5 = df5.format(c.getTime());

    SimpleDateFormat df4 = new SimpleDateFormat("yyy-MM-dd");
    String formattedDateFecha = df4.format(c.getTime());

    // Para calcular la diferencia entre horas se toma el tiempo en milisegundos
    Calendar t1 = Calendar.getInstance();
    Calendar t2 = Calendar.getInstance();
    Calendar t3 = Calendar.getInstance();
    // esta variable la paso hasta la última página
    long milis1 = t1.getTimeInMillis();

    public String nombreArchivo() {
//		String date = formattedDate3.toString();
//		String var2 = ".txt";
//		String var3 = date + var2;

//		final String nombre = date + "-" + tablet + "-" + nombreEncuesta + "-" + var2;
        final String nombre = nombreEncuesta + "_" + prefix;

        // String nombre="encuestas20140219.txt";
        return nombre;
    }

    public String nombreAudio() {

        elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;
        String date = formattedDate3.toString();
        String var2 = ".mp3";

        final String nombreAudio = "R" + elMaximo + "_T" + sacaImei() + "_E" + cachaNombre() + var2;
        // String nombre="encuestas20140219.txt";
        return nombreAudio;
    }

    public String nombreArchivoCSV() {
        String date = formattedDate3.toString();
        String var2 = ".csv";
        String var3 = date + var2;

        final String nombre = date + "-" + nombreEncuesta + "-" + prefix + var2;
        // String nombre="encuestas20140219.txt";
        return nombre;
    }

    public String cachaNombre2() {
        Bundle datos = this.getIntent().getExtras();
        encuestador = datos.getString("Nombre2");
        return encuestador;

    }

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

    public String sacaChip() {
        String deviceId = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
        tablet = deviceId;
        return tablet;
    }

    public String cachaNombre() {
        Bundle datos = this.getIntent().getExtras();
        encuestador = datos.getString("Nombre");
        return encuestador;
    }

    private ArrayList<String> SeccionesArray() {

        usdbh2 = new UsuariosSQLiteHelper2(this);
        ArrayList<String> set = new ArrayList<String>();
        db2 = usdbh2.getWritableDatabase();
        String selectQuery1 = "SELECT DISTINCT seccion FROM datos";
        Cursor c = db2.rawQuery(selectQuery1, null);
        if (c.moveToFirst()) {
            do {
                set.add(c.getString(0));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return set;
    }


    public void startAlarm() {
        EditText text = findViewById(R.id.time);
        int i = 2;
        Intent intent = new Intent(this, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (2000), pendingIntent);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i * 1000), pendingIntent);
//        Toast.makeText(this, "Alarm set in " + i + " seconds", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(MainActivity.this,
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
        setContentView(R.layout.activity_main);

        if (cachaNombre() == null) {

            encuestaQuien = cachaNombre2();

        } else {

            cachaNombre();

            encuestaQuien = cachaNombre();

        }

        Intent startingIntent = getIntent();
        if (startingIntent == null) {
            Log.e(TAG, "No Intent?  We´re not supposed to be here...");
            finish();
            return;
        }

        if (savedInstanceState != null) {
            usuario = (Usuarios) savedInstanceState.getSerializable(USUARIO);
        } else {
            usuario = (Usuarios) startingIntent.getSerializableExtra(USUARIO);
        }

        Utils.info(TAG,"usuario: ",encuestaQuien);

        sqlite_obj = new UsuariosSQLiteHelper2(MainActivity.this);

        File directory;
        File file;
        File sdCard;
        sdCard = Environment.getExternalStorageDirectory();
        FileOutputStream fout = null;
        try {
            directory = new File(sdCard.getAbsolutePath() + "/Mis_archivos");
            directory.mkdirs();
            Nombre nom = new Nombre();
            String nombreE = nom.nombreEncuesta();
            directory = new File(sdCard.getAbsolutePath() + "/" + nombreE + "-Audio" + formattedDate1);
            directory.mkdirs();


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;
        String elMaximoFecha = String.valueOf(elMaximo);

        Utils.info(TAG,"El numero inicial antes del if",String.valueOf(elMaximoFecha));

        if (elMaximoFecha.matches("1")) {
            Log.i(TAG, " =====> El numero inicial: " + elMaximoFecha);
            Log.i(TAG, " =====> El nombre de la encuesta: " + nombreEncuesta);
            catalogoUsuariosWS();
//            catalogoSeccionesWS(nombreEncuesta);
            dialogoFecha();
        }

//        if (!verificaConexion(this)) {
//            Toast.makeText(getBaseContext(),"Sin conexión",
//                    Toast.LENGTH_LONG).show();
//            //this.finish();
//        }
//        else{
//            new MainActivity.UpdateBases().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//            new MainActivity.UpdateAudios().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        }


        GPSTracker gps = new GPSTracker(this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        if (latitude == 0.0) {
            if (Utils.sacaLatitud(this) != null) {
                latitude = Double.valueOf(Utils.sacaLatitud(this));
            } else {
                latitude = 0.0;
            }
        }

        if (longitude == 0.0) {
            if (Utils.sacaLongitud(this) != null) {
                longitude = Double.valueOf(Utils.sacaLongitud(this));
            } else {
                longitude = 0.0;
            }
        }

        Log.i("GPS", "Latitud sin GPS: " + latitude);
        Log.i("GPS", "Longitud sin GPS: " + longitude);

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.actv);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, SeccionesArray());

        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(this);

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    btnSiguiente.setEnabled(false);
                    btnRechazo.setEnabled(false);
                    btnRechazo2.setEnabled(false);
                }
            }
        });

//		if(gps.canGetLocation()){
//		    double latitude = gps.getLatitude();
//		    double longitude = gps.getLongitude();
//		}else{
//		// can't get location
//		// GPS or Network is not enabled
//		// Ask user to enable GPS/network in settings
//		    gps.showSettingsAlert();
//			Log.i("GPS", "Latitud sin GPS: "+latitude);
//			Log.i("GPS", "Longitud sin GPS: "+longitude);
//		}
//


        sacaChip();


        sdCard = Environment.getExternalStorageDirectory();
        // directory = new File(sdCard.getAbsolutePath() +
        // "/Audio"+formattedDate3+"");

        // directory.mkdirs();//CREA EL DIRECTORIO SI NO EXISTE

        // Obtenemos las referencias a los controles
        // txtCodigo = (EditText)findViewById(R.id.txtReg);
        // txtNombre = (EditText)findViewById(R.id.txtVal);
        txtResultado = (TextView) findViewById(R.id.txtResultado);
        txtEncuesta = (TextView) findViewById(R.id.txtEncuesta);
        txtEquipo = (TextView) findViewById(R.id.txtEquipo);

        spinner2 = (Spinner) findViewById(R.id.spinner2);
//        spinner_seccion = (Spinner) findViewById(R.id.spinner_seccion);

        // btnInsertar = (Button)findViewById(R.id.btnInsertar);
        // btnActualizar = (Button)findViewById(R.id.btnActualizar);
        // btnEliminar = (Button)findViewById(R.id.btnEliminar);
        btnConsultar = (Button) findViewById(R.id.btnConsultar);
        btnCatalogos = (Button) findViewById(R.id.btnActualiza);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        btnSiguiente.setEnabled(false);


        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        mProgressView = findViewById(R.id.login_progressMain);
        mProgressView2 = findViewById(R.id.login_progressMain2);


//		btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnRechazo = (Button) findViewById(R.id.btnRechazo);
        btnRechazo2 = (Button) findViewById(R.id.btnRechazo2);
        btnInformacion = (Button) findViewById(R.id.btnInformacion);
        btnRechazo.setEnabled(false);
        btnRechazo2.setEnabled(false);




        startAlarm();


        cargaUsuario();
        // SeccionesSpinner();

        final String F = "File dbfile";

        String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_" + sacaImei() + "";

        // Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

        db = usdbh.getWritableDatabase();

        usdbh2 = new UsuariosSQLiteHelper2(this);

        db2 = usdbh2.getWritableDatabase();

        btnConsultar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                txtResultado.setText("");

                try {

                    Log.i(TAG, "cqs ---------->> delegacion: " + delegacion());

                } catch (Exception e) {
                    String stackTrace = Log.getStackTraceString(e);
                    Log.i(TAG, "Error al sacar la delegación" + stackTrace);
                    Toast toast2 = Toast.makeText(getApplicationContext(), "No existe esta sección en la muestra", Toast.LENGTH_LONG);
                    toast2.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                    toast2.show();

                    autoCompleteTextView.setText("");
                    btnSiguiente.setEnabled(false);
                    btnRechazo.setEnabled(false);
                    btnRechazo2.setEnabled(false);

                }

                if (autoCompleteTextView.getText().toString().trim().length() == 0) {
                    btnSiguiente.setEnabled(false);
                    btnRechazo.setEnabled(false);
                    btnRechazo2.setEnabled(false);

                    Toast toast2 = Toast.makeText(getApplicationContext(), "No existe esta sección en la muestra", Toast.LENGTH_LONG);
                    toast2.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                    toast2.show();

                } else {
                    btnSiguiente.setEnabled(true);
                    btnRechazo.setEnabled(true);
                    btnRechazo2.setEnabled(true);
                }
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    class UpdateProgress extends AsyncTask<Void, Integer, Void> {
        int progress;

        @Override
        protected void onPostExecute(Void result) {

        }

        @Override
        protected void onPreExecute() {
            progress = 0;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            while (progress < 100) {
                progress++;
                publishProgress(progress);
                SystemClock.sleep(100);
            }
            return null;
        }
    }


    private void storeUsuarios() {
        // TODO Auto-generated method stub

        sqlite_obj.open();

        sqlite_obj.deleteAll();

        for (int i = 0; i < listUsuario.size(); i++) {

            sqlite_obj.insert(listUsuario.get(i).toString(), listPassword.get(i).toString());
        }

        sqlite_obj.close();
    }


    public void catalogos(View view) {

        Log.i(TAG, " =====> El nombre de la encuesta: " + nombreEncuesta);
        catalogoSeccionesWS(nombreEncuesta);
        finishAffinity();
    }


    public void Siguiente(View view) {

        if (cachaNombre() == null) {

            encuestaQuien = cachaNombre2().toUpperCase();

        } else {

            cachaNombre();

            encuestaQuien = cachaNombre().toUpperCase();

        }

        String equipo = spinner2.getSelectedItem().toString();

        System.out.println("El ID: " + ID);
        System.out.println("El prefix: " + prefix);

        String seg = formattedDate5.substring(7);
        System.out.println("El segundo: " + seg);
        System.out.println("El ID: " + prefix);

        System.out.println("El equipo:  " + equipo);
        System.out.println("equipo:  " + equipo().toString().toUpperCase());

        dialogo();
    }

    public void Salir(View v) {

        finishAffinity();
    }


    public void Rechazo(View view) {

        dialogoRechazo();

    }

    public void Rechazo2(View view) {

        dialogoRechazo2();

    }

    public void Info(View view) {

        Intent intent = new Intent(getApplicationContext(), Menu_Principal.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Seccion", autoCompleteTextView.getText().toString());
        intent.putExtra("Nombre", encuestaQuien);
        intent.putExtra("equipo", equipo());// para pasar la variable a la otra
        intent.putExtra("t1", milis1);
        startActivity(intent);
        finish();

    }

    public void dialogo() {

        seccion = autoCompleteTextView.getText().toString();
//        seccion = spinner_seccion.getSelectedItem().toString().toUpperCase();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // builder.setMessage(cuentaArchivos())
        builder.setMessage("Seccion " + seccion + ": \n\n" + "Encuestas: \n" + "Normal:   " + " " + dameSecciones()
                + "\n" + "Abandono: " + " " + dameAbandonos() + "\n" + "Rechazos: " + dameRechazos() + "\n\n")
//				+ "Sexo: \n" + "Masculino: " + dameHombres() + "\n" + "Femenino:  " + dameMujeres() + "\n\n"
//                + "Rechazos Totales: " + dameRechazosTodos() + "\n\n")

                .setTitle("AVISO...!!!").setCancelable(false)
                .setNegativeButton("SALIR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//						Intent i = new Intent(MainActivity.this, Entrada.class);
//						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//						startActivity(i);
//						finish();
//						; // metodo que se debe implementar
                    }
                }).setPositiveButton("CONTINUAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                System.out.println(cachaNombre());


                pasaDatos();


            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    // RECUPERAR UN DATO DE LA BASE DE DATOS

    public String delegacion() {

//        try {

        seccion = autoCompleteTextView.getText().toString();
        equipo = spinner2.getSelectedItem().toString().toLowerCase();

        db2 = usdbh2.getWritableDatabase();
        String selectQuery1 = " SELECT delegacion FROM datos  WHERE seccion='" + seccion + "'";
        Cursor c = db2.rawQuery(selectQuery1, null);

        c.moveToFirst();
        // Recorremos el cursor hasta que no haya más registros
        String delegacion = c.getString(0);
        c.close();
        db2.close();

        return delegacion;
//        }catch (Exception e){
//            String stackTrace = Log.getStackTraceString(e);
//            Log.i(TAG,"Error al sacar la delegación"+ stackTrace);
//            Toast toast2 = Toast.makeText(getApplicationContext(), "No existe esta sección en la muestra",Toast.LENGTH_LONG);
//            toast2.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
//            toast2.show();
//        }
//
//        return null;
    }


    public String equipo() {


//        seccion = spinner_seccion.getSelectedItem().toString().toUpperCase();
        seccion = autoCompleteTextView.getText().toString();
        equipo = spinner2.getSelectedItem().toString().toLowerCase();

        return equipo;
    }

    public void cargaEncuestador() {

        db = usdbh.getWritableDatabase();
        String selectQuery1 = " SELECT usuario, count(*) as total from encuestas where fecha='" + formattedDate3
                + "' group by usuario";
        Cursor c = db.rawQuery(selectQuery1, null);

        if (c.moveToFirst())
            do {

                String enc = c.getString(0);
                String tot = c.getString(1);
                // Recorremos el cursor hasta que no haya más registros
                txtEncuesta.append("- Encuestador: " + enc + "  " + "\tTotal de encuestas: " + tot + "\n");

            } while (c.moveToNext());

        c.close();
        db.close();
    }

    public String dameSecciones() {

//        seccion = spinner_seccion.getSelectedItem().toString().toUpperCase();
        seccion = autoCompleteTextView.getText().toString();
        String cuantasSecciones_base;

        db = usdbh.getWritableDatabase();
        String selectQuery1 = "select seccion, count(*) as total from encuestas where seccion='" + seccion
                + "' and fecha='" + formattedDate3 + "'  and tipo_captura='NORMAL' group by seccion";
        Cursor c = db.rawQuery(selectQuery1, null);

        if (c.getCount() > 0) {
            // Recorremos el cursor hasta que no haya más registros
            c.moveToFirst();
            cuantasSecciones_base = c.getString(1);
        } else {

            cuantasSecciones_base = "0";

        }

        c.close();
        db.close();
        return cuantasSecciones_base;

    }

    public String dameAbandonos() {
        seccion = autoCompleteTextView.getText().toString();
//        seccion = spinner_seccion.getSelectedItem().toString().toUpperCase();
        String cuantasSecciones_base;
        db = usdbh.getWritableDatabase();
        String selectQuery1 = "select seccion, count(*) as total from encuestas where seccion='" + seccion
                + "' and fecha='" + formattedDate3 + "'  and (tipo_captura='ABANDONO' or tipo_captura='ABANDONO TEMOR A CONTAGIO')  group by seccion";
        Cursor c = db.rawQuery(selectQuery1, null);
        if (c.getCount() > 0) {
            // Recorremos el cursor hasta que no haya más registros
            c.moveToFirst();
            cuantasSecciones_base = c.getString(1);
        } else {
            cuantasSecciones_base = "0";
        }
        c.close();
        db.close();
        return cuantasSecciones_base;
    }

    public String dameHombres() {

        seccion = autoCompleteTextView.getText().toString();
//        seccion = spinner_seccion.getSelectedItem().toString().toUpperCase();
        String masculino;

        db = usdbh.getWritableDatabase();
        // String selectQuery1 = "select seccion, count(*) as total from
        // encuestas where seccion='"+seccion+"' and fecha='"+formattedDate1+"'
        // group by seccion";
        String selectQuery1 = "select socioeconomico_12, count(*) as total from encuestas where seccion='" + seccion
                + "' and fecha='" + formattedDate3 + "'  and socioeconomico_12='MASCULINO' and tipo_captura='NORMAL' group by socioeconomico_12"; // total
        // de
        // encuestas
        Cursor c = db.rawQuery(selectQuery1, null);

        if (c.getCount() > 0) {
            // Recorremos el cursor hasta que no haya más registros
            c.moveToFirst();
            masculino = c.getString(1);
        } else {

            masculino = "0";

        }

        c.close();
        db.close();
        return masculino;

    }

    public String dameMujeres() {

        seccion = autoCompleteTextView.getText().toString();
//        seccion = spinner_seccion.getSelectedItem().toString().toUpperCase();
        String femenino;

        db = usdbh.getWritableDatabase();
        // String selectQuery1 = "select seccion, count(*) as total from
        // encuestas where seccion='"+seccion+"' and fecha='"+formattedDate1+"'
        // group by seccion";
        String selectQuery1 = "select socioeconomico_12, count(*) as total from encuestas where seccion='" + seccion
                + "' and fecha='" + formattedDate3 + "'  and socioeconomico_12='FEMENINO' and tipo_captura='NORMAL' group by socioeconomico_12"; // total
        // de
        // encuestas
        Cursor c = db.rawQuery(selectQuery1, null);

        if (c.getCount() > 0) {
            // Recorremos el cursor hasta que no haya más registros
            c.moveToFirst();
            femenino = c.getString(1);
        } else {

            femenino = "0";

        }

        c.close();
        db.close();
        return femenino;

    }

    public String dameRechazosTodos() {
        seccion = autoCompleteTextView.getText().toString();
//        seccion = spinner_seccion.getSelectedItem().toString().toUpperCase();
        String cuantasSecciones_base;
        db = usdbh.getWritableDatabase();
        String selectQuery1 = "select seccion, count(*) as total from encuestas where fecha='" + formattedDate3 + "' and (tipo_captura='RECHAZO' or tipo_captura='RECHAZO TEMOR A CONTAGIO')  group by seccion";
        Cursor c = db.rawQuery(selectQuery1, null);
        if (c.getCount() > 0) {
            // Recorremos el cursor hasta que no haya más registros
            c.moveToFirst();
            cuantasSecciones_base = c.getString(1);
        } else {
            cuantasSecciones_base = "0";
        }
        c.close();
        db.close();
        return cuantasSecciones_base;
    }

    public String dameRechazos() {
        seccion = autoCompleteTextView.getText().toString();
//        seccion = spinner_seccion.getSelectedItem().toString().toUpperCase();
        String cuantasSecciones_base;
        db = usdbh.getWritableDatabase();
        String selectQuery1 = "select seccion, count(*) as total from encuestas where seccion='" + seccion + "' and fecha='" + formattedDate3 + "'  and (tipo_captura='RECHAZO' or tipo_captura='RECHAZO TEMOR A CONTAGIO') group by seccion";
        Cursor c = db.rawQuery(selectQuery1, null);
        if (c.getCount() > 0) {
            // Recorremos el cursor hasta que no haya más registros
            c.moveToFirst();
            cuantasSecciones_base = c.getString(1);
        } else {
            cuantasSecciones_base = "0";
        }
        c.close();
        db.close();
        return cuantasSecciones_base;
    }

    public void pasaDatos() {

        String seg = formattedDate5.substring(7);

        Intent intent = new Intent(getApplicationContext(), MainActivityPantalla1.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Seccion", autoCompleteTextView.getText().toString());
        intent.putExtra("Nombre", encuestaQuien);
        intent.putExtra(USUARIO, usuario);//PASO TODO EL OBJETO
        intent.putExtra("equipo", equipo());// para pasar la variable a la otra
        intent.putExtra("t1", milis1);
        startActivity(intent);
        finish();

    }


    //COORDINADORES

    private void cargaUsuario() {

        final String[] datos = new String[]{
                "ALAN",
                "ARREDONDO",
                "BERNACHI",
                "DANIEL",
                "EDUARDO S",
                "ENRIQUE",
                "IRIS",
                "LUJANO",
                "OSCAR",
                "POLANCO",
                "HUGO",
                "SAUL",
                "TABLA"
        };

        // Alternativa 1: Array java
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(adaptador);

        // ACCIÓN QUE SE REALIZA CUANDO ES SELECCIOANDO UN ELEMENTO DEL SPINNER
        spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
//                SeccionesSpinner();
                txtEquipo.setText("Seleccionado: " + datos[position]);
                btnSiguiente.setEnabled(false);// desactiva el botón siguiente
                txtResultado.setText("");// limpia el cuadro de texto
            }

            public void onNothingSelected(AdapterView<?> parent) {
                txtEquipo.setText("");
            }
        });

    }

    private void SeccionesSpinner() {

        usdbh2 = new UsuariosSQLiteHelper2(this);

        Set<String> set = new HashSet<String>();

        equipo = spinner2.getSelectedItem().toString().toLowerCase();
        System.out.println(equipo);

        db2 = usdbh2.getWritableDatabase();

        String selectQuery1 = "SELECT * FROM datos WHERE equipo='" + equipo + "'";

        Cursor c = db2.rawQuery(selectQuery1, null);

        if (c.moveToFirst()) {
            do {

                set.add(c.getString(0));

                String secc = c.getString(0);

            } while (c.moveToNext());
        } else {
            Toast toast2 = Toast.makeText(getApplicationContext(), "NO CORRESPONDE ESTE NÚMERO DE SECCIÓN...!",
                    Toast.LENGTH_LONG);

            toast2.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);

            toast2.show();

        }

        c.close();
        db.close();
        // here i used Set Because Set doesn't allow duplicates.
        Set<String> set1 = set;
        List<String> list = new ArrayList<String>(set1);

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Collections.sort(list);
        spinner_seccion.setAdapter(adapter);

        // ACCIÓN QUE SE REALIZA CUANDO ES SELECCIOANDO UN ELEMENTO DEL SPINNER
        spinner_seccion.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                btnSiguiente.setEnabled(false);// desactiva el botón siguiente
                txtResultado.setText("");// limpia el cuadro de texto
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // txtEquipo.setText("");
            }
        });
        spinner_seccion.setWillNotDraw(false);

    }

    private String sacaMaximo() {

        Set<String> set = new HashSet<String>();

        final String F = "File dbfile";

        // Abrimos la base de datos 'DBUsuarios' en modo escritura

        String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_" + sacaImei() + "";
        usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

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

    private String sacaMaximo2() {

        Set<String> set = new HashSet<String>();

        final String F = "File dbfile";

        // Abrimos la base de datos 'DBUsuarios' en modo escritura

        String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_" + sacaImei() + "";
        usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

        db = usdbh.getReadableDatabase();

        String selectQuery = "SELECT count(*) FROM usuario";

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


    public void valoresRechazoMasculino() {

//        String strSecc = spinner_seccion.getSelectedItem().toString();
        String strSecc = autoCompleteTextView.getText().toString();
        equipo = spinner2.getSelectedItem().toString().toLowerCase();

        String seg = formattedDate5.substring(7);
        System.out.println("El segundo: " + seg);
        System.out.println("El ID" + prefix);

        cachaNombre();

        String strId = String.valueOf(rand + 1);

        GPSTracker gps = new GPSTracker(this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        if (latitude == 0.0) {
            if (Utils.sacaLatitud(this) != null) {
                latitude = Double.valueOf(Utils.sacaLatitud(this));
            } else {
                latitude = 0.0;
            }
        }

        if (longitude == 0.0) {
            if (Utils.sacaLongitud(this) != null) {
                longitude = Double.valueOf(Utils.sacaLongitud(this));
            } else {
                longitude = 0.0;
            }
        }

        String strLatitud = String.valueOf(latitude);
        String strLongitud = String.valueOf(longitude);


        elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;

        // Clase que permite grabar texto en un archivo
        FileOutputStream fout = null;
        try {
            // INSERTA EN LA BASE DE DATOS //

            String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_" + sacaImei() + "";
            usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

            db = usdbh.getWritableDatabase();

            // NORMAL
            Nombre nom = new Nombre();
            String nombreE = nom.nombreEncuesta();
            long consecutivoObtenido = 0;
            ContentValues values = new ContentValues();

            if (db != null) {
                values.put("consecutivo_diario", elMaximo);
                values.put("usuario", cachaNombre().toUpperCase());
                values.put("equipo", equipo.toUpperCase());
                values.put("nombre_encuesta", nombreE.toUpperCase());
                values.put("fecha", formattedDate3);
                values.put("hora", formattedDate5);
                values.put("imei", sacaImei());
                values.put("seccion", strSecc);
                values.put("latitud", strLatitud);
                values.put("longitud", strLongitud);

                values.put("genero", "Masculino");
                values.put("suma", "0");
                values.put("status", "0");

                values.put("tiempo", "00:00");
                values.put("tipo_captura", "RECHAZO");

                if (!Utils.verificaConexion(this)) {
                    Toast.makeText(getBaseContext(), "Sin conexion", Toast.LENGTH_LONG).show();
                    values.put("enviado", "0");
                    db.insert("encuestas", null, values);
                } else {
                    values.put("enviado", "1");
                    consecutivoObtenido = db.insert("encuestas", null, values);
                }
            }
            db.close();

            values.put("consecutivo", consecutivoObtenido);
//
            guardaEncuestaWS(values);
            // FIN INSERTA BASE DE DATOS //

            txtEquipo.setText("");
            btnSiguiente.setEnabled(false);// desactiva el botón siguiente
            btnRechazo.setEnabled(false);
            btnRechazo2.setEnabled(false);
            txtResultado.setText("");// limpia el cuadro de texto

        } catch (Exception e) {
            System.out.println("algo pasó...1");
            Log.i("Guardar", e.getMessage());
        }

    }

    public void valoresRechazoFemenino() {

        String strSecc = autoCompleteTextView.getText().toString();
//        String strSecc = spinner_seccion.getSelectedItem().toString();
        equipo = spinner2.getSelectedItem().toString().toLowerCase();

        GPSTracker gps = new GPSTracker(this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        if (latitude == 0.0) {
            if (Utils.sacaLatitud(this) != null) {
                latitude = Double.valueOf(Utils.sacaLatitud(this));
            } else {
                latitude = 0.0;
            }
        }

        if (longitude == 0.0) {
            if (Utils.sacaLongitud(this) != null) {
                longitude = Double.valueOf(Utils.sacaLongitud(this));
            } else {
                longitude = 0.0;
            }
        }

        String strLatitud = String.valueOf(latitude);
        String strLongitud = String.valueOf(longitude);

        String seg = formattedDate5.substring(7);
        System.out.println("El segundo: " + seg);
        System.out.println("El Imei" + sacaImei());

        cachaNombre();

        String strId = String.valueOf(rand + 1);

        elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;

        // Clase que permite grabar texto en un archivo
        FileOutputStream fout = null;
        try {
            // INSERTA EN LA BASE DE DATOS //

            final String F = "File dbfile";

            // Abrimos la base de datos 'DBUsuarios' en modo escritura
            String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_" + sacaImei() + "";
            usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

            db = usdbh.getWritableDatabase();

            // NORMAL
            Nombre nom = new Nombre();
            String nombreE = nom.nombreEncuesta();
            long consecutivoObtenido = 0;
            ContentValues values = new ContentValues();
            if (db != null) {

                values.put("consecutivo_diario", elMaximo);
                values.put("usuario", cachaNombre().toUpperCase());
                values.put("equipo", equipo.toUpperCase());
                values.put("nombre_encuesta", nombreE.toUpperCase());
                values.put("fecha", formattedDate3);
                values.put("hora", formattedDate5);
                values.put("imei", sacaImei());
                values.put("seccion", strSecc);
                values.put("latitud", strLatitud);
                values.put("longitud", strLongitud);

                values.put("genero", "Femenino");
                values.put("suma", "0");
                values.put("status", "0");

                values.put("tiempo", "00:00");
                values.put("tipo_captura", "RECHAZO");

                if (!Utils.verificaConexion(this)) {
                    Toast.makeText(getBaseContext(), "Sin conexión", Toast.LENGTH_LONG).show();
                    values.put("enviado", "0");
                    db.insert("encuestas", null, values);
                } else {
                    values.put("enviado", "1");
                    consecutivoObtenido = db.insert("encuestas", null, values);
                }
            }
            db.close();

            values.put("consecutivo", consecutivoObtenido);
//
            guardaEncuestaWS(values);
            // FIN INSERTA BASE DE DATOS //

            txtEquipo.setText("");
            btnSiguiente.setEnabled(false);// desactiva el botón siguiente
            btnRechazo.setEnabled(false);
            btnRechazo2.setEnabled(false);
            txtResultado.setText("");// limpia el cuadro de texto

        } catch (Exception e) {
            System.out.println("algo pasó...1");
            Log.i("Guardar", e.getMessage());
        }

    }


    public void valoresRechazoMasculino2() {

        String strSecc = autoCompleteTextView.getText().toString();
//        String strSecc = spinner_seccion.getSelectedItem().toString();
        equipo = spinner2.getSelectedItem().toString().toLowerCase();

        String seg = formattedDate5.substring(7);
        System.out.println("El segundo: " + seg);
        System.out.println("El ID" + prefix);

        cachaNombre();

        String strId = String.valueOf(rand + 1);

        GPSTracker gps = new GPSTracker(this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        if (latitude == 0.0) {
            if (Utils.sacaLatitud(this) != null) {
                latitude = Double.valueOf(Utils.sacaLatitud(this));
            } else {
                latitude = 0.0;
            }
        }

        if (longitude == 0.0) {
            if (Utils.sacaLongitud(this) != null) {
                longitude = Double.valueOf(Utils.sacaLongitud(this));
            } else {
                longitude = 0.0;
            }
        }

        String strLatitud = String.valueOf(latitude);
        String strLongitud = String.valueOf(longitude);


        elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;

        // Clase que permite grabar texto en un archivo
        FileOutputStream fout = null;
        try {
            // INSERTA EN LA BASE DE DATOS //

            String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_" + sacaImei() + "";
            usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

            db = usdbh.getWritableDatabase();

            // NORMAL
            Nombre nom = new Nombre();
            String nombreE = nom.nombreEncuesta();
            long consecutivoObtenido = 0;
            ContentValues values = new ContentValues();
            if (db != null) {

                values.put("consecutivo_diario", elMaximo);
                values.put("usuario", cachaNombre().toUpperCase());
                values.put("equipo", equipo.toUpperCase());
                values.put("nombre_encuesta", nombreE.toUpperCase());
                values.put("fecha", formattedDate3);
                values.put("hora", formattedDate5);
                values.put("imei", sacaImei());
                values.put("seccion", strSecc);
                values.put("latitud", strLatitud);
                values.put("longitud", strLongitud);

                values.put("genero", "Masculino");
                values.put("suma", "0");
                values.put("status", "0");

                values.put("tiempo", "00:00");
                values.put("tipo_captura", "RECHAZO TEMOR A CONTAGIO");

                if (!Utils.verificaConexion(this)) {
                    Toast.makeText(getBaseContext(), "Sin conexión", Toast.LENGTH_LONG).show();
                    values.put("enviado", "0");
                    db.insert("encuestas", null, values);
                } else {
                    values.put("enviado", "1");
                    consecutivoObtenido = db.insert("encuestas", null, values);
                }
            }
            db.close();

            values.put("consecutivo", consecutivoObtenido);
//
            guardaEncuestaWS(values);
            // FIN INSERTA BASE DE DATOS //

            txtEquipo.setText("");
            btnSiguiente.setEnabled(false);// desactiva el botón siguiente
            btnRechazo.setEnabled(false);
            btnRechazo2.setEnabled(false);
            txtResultado.setText("");// limpia el cuadro de texto

        } catch (Exception e) {
            System.out.println("algo pasó...1");
            Log.i("Guardar", e.getMessage());
        }

    }

    public void valoresRechazoFemenino2() {

        String strSecc = autoCompleteTextView.getText().toString();
//        String strSecc = spinner_seccion.getSelectedItem().toString();
        equipo = spinner2.getSelectedItem().toString().toLowerCase();

        GPSTracker gps = new GPSTracker(this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        if (latitude == 0.0) {
            if (Utils.sacaLatitud(this) != null) {
                latitude = Double.valueOf(Utils.sacaLatitud(this));
            } else {
                latitude = 0.0;
            }
        }

        if (longitude == 0.0) {
            if (Utils.sacaLongitud(this) != null) {
                longitude = Double.valueOf(Utils.sacaLongitud(this));
            } else {
                longitude = 0.0;
            }
        }

        String strLatitud = String.valueOf(latitude);
        String strLongitud = String.valueOf(longitude);

        String seg = formattedDate5.substring(7);
        System.out.println("El segundo: " + seg);
        System.out.println("El Imei" + sacaImei());

        cachaNombre();

        String strId = String.valueOf(rand + 1);

        elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;

        // Clase que permite grabar texto en un archivo
        FileOutputStream fout = null;
        try {
            // INSERTA EN LA BASE DE DATOS //

            final String F = "File dbfile";

            // Abrimos la base de datos 'DBUsuarios' en modo escritura
            String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_" + sacaImei() + "";
            usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

            db = usdbh.getWritableDatabase();

            // NORMAL
            Nombre nom = new Nombre();
            String nombreE = nom.nombreEncuesta();
            long consecutivoObtenido = 0;
            ContentValues values = new ContentValues();
            if (db != null) {
                values.put("consecutivo_diario", elMaximo);
                values.put("usuario", cachaNombre().toUpperCase());
                values.put("equipo", equipo.toUpperCase());
                values.put("nombre_encuesta", nombreE.toUpperCase());
                values.put("fecha", formattedDate3);
                values.put("hora", formattedDate5);
                values.put("imei", sacaImei());
                values.put("seccion", strSecc);
                values.put("latitud", strLatitud);
                values.put("longitud", strLongitud);

                values.put("genero", "Femenino");
                values.put("suma", "0");
                values.put("status", "0");

                values.put("tiempo", "00:00");
                values.put("tipo_captura", "RECHAZO TEMOR A CONTAGIO");

                if (!Utils.verificaConexion(this)) {
                    Toast.makeText(getBaseContext(), "Sin conexión", Toast.LENGTH_LONG).show();
                    values.put("enviado", "0");
                    db.insert("encuestas", null, values);
                } else {
                    values.put("enviado", "1");
                    consecutivoObtenido = db.insert("encuestas", null, values);
                }


            }
            db.close();

            values.put("consecutivo", consecutivoObtenido);
//
            guardaEncuestaWS(values);

            // FIN INSERTA BASE DE DATOS //

            txtEquipo.setText("");
            btnSiguiente.setEnabled(false);// desactiva el botón siguiente
            btnRechazo.setEnabled(false);
            btnRechazo2.setEnabled(false);
            txtResultado.setText("");// limpia el cuadro de texto

        } catch (Exception e) {
            String stackTrace = Log.getStackTraceString(e);
            Log.i(TAG, "Error inserta rechazo contagio femenino" + stackTrace);
        }

    }


    private void guardaEncuestaWS(ContentValues values) {

        showProgress(true);

        //RECORRE CONTENT VALUES
        DatoContent datoContent = new DatoContent();
        List<DatoContent> listaContenido = new ArrayList();
        Set<Map.Entry<String, Object>> s = values.valueSet();
        Iterator itr = s.iterator();
        while (itr.hasNext()) {
            Map.Entry me = (Map.Entry) itr.next();
            String key = me.getKey().toString();
            Object value = me.getValue();

            datoContent = new DatoContent();
            datoContent.setKey(key);
            datoContent.setValue(String.valueOf(value));

            listaContenido.add(datoContent);
        }

        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<DatoContent>>() {
        }.getType();
        String json = gson.toJson(listaContenido, collectionType);

        RequestParams params = new RequestParams();
        params.put("api", "guarda_encuesta");
        params.put("encuesta", encuesta);
        params.put("data", json);

        Log.d(TAG, "pimc -----------> " + json);

        AsyncHttpClient client = new AsyncHttpClient();
        client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
//client.addHeader("Authorization", "Bearer " + usuario.getToken());
        client.setTimeout(60000);

        RequestHandle requestHandle = client.post(customURL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                showProgress(false);
                Log.d(TAG, "pimc -----------> Respuesta OK ");
                Log.d(TAG, "pimc -----------> " + new String(responseBody));
                try {


                    String json = new String(responseBody);

                    if (json != null && !json.isEmpty()) {

                        Gson gson = new Gson();
                        JSONObject jsonObject = new JSONObject(json);
                        Log.d(TAG, "pimc -----------> Data: " + jsonObject.get("data"));

                        String login = jsonObject.getJSONObject("response").get("code").toString();
                        if (Integer.valueOf(login) == 1) {

/*JSONObject jsonUser = jsonObject.getJSONObject("data").getJSONObject("respuesta");
Type collectionType = new TypeToken<Usuario>() {}.getType();
usuario = gson.fromJson(jsonUser.toString(), collectionType);*/

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("Nombre", encuestaQuien);
                            intent.putExtra(USUARIO, usuario);
                            startActivity(intent);
                            finish();


                        } else {
                            Toast.makeText(MainActivity.this, "Error al subir los datos", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    showProgress(false);
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(MainActivity.this, "Error al subir los datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showProgress(false);
                try {
                    Log.e(TAG, "PIMC-----------------> existe un error en la conexion -----> " + error.getMessage());
                    if (responseBody != null)
                        Log.d(TAG, "pimc -----------> " + new String(responseBody));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (statusCode != 200) {
                    Log.e(TAG, "Existe un error en la conexion -----> " + error.getMessage());
                    if (responseBody != null)
                        Log.d(TAG, "pimc -----------> " + new String(responseBody));

                }

                Toast.makeText(MainActivity.this, "Error de conexion, se guarda en la base interna", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void catalogoUsuariosWS() {

        RequestParams params = new RequestParams();
        params.put("api", "usuarios");
        params.put("encuesta", encuesta);

        AsyncHttpClient client = new AsyncHttpClient();
        client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
        //client.addHeader("Authorization", "Bearer " + usuario.getToken());
        client.setTimeout(60000);

        RequestHandle requestHandle = client.post(customURLcatalogos, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                showProgress(false);
                Log.d(TAG, "cqs -----------> Respuesta catalogos OK ");
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

                            Log.d(TAG, "cqs ----------->> Code estoy dentro:  " + login);
                            //obtiene usuarios
                            String jsonUsuarios = jsonObject.getJSONObject("data").getJSONArray("encuestadores").toString();
                            Type collectionType = new TypeToken<List<Usuarios>>() {
                            }.getType();
                            List<Usuarios> listaUsuarios = gson.fromJson(jsonUsuarios, collectionType);

                            Log.d(TAG, "cqs ----------->> listaUsuarios:  " + listaUsuarios);

                            daoManager = new DaoManager(db2);
                            if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                                daoManager.delete(Usuarios.class);
                                for (Usuarios usuarios : listaUsuarios) {
                                    daoManager.insert(usuarios);
                                }
                            }


            /*
            //obtiene alcaldias
            jsonStatus = jsonObject.getJSONObject("data").getJSONArray("alcaldias").toString();
            collectionType = new TypeToken<List<Alcaldia>>() {}.getType();
            List<Alcaldia> listaAlcaldias = gson.fromJson(jsonStatus, collectionType);

            if(listaAlcaldias != null && !listaAlcaldias.isEmpty()){
            daoManager.delete(Alcaldia.class);
            for(Alcaldia alcaldia : listaAlcaldias ){
            daoManager.insert(alcaldia);
            }
            }

            //obtiene colonia
            jsonStatus = jsonObject.getJSONObject("data").getJSONArray("colonias").toString();
            collectionType = new TypeToken<List<Colonia>>() {}.getType();
            List<Colonia> listaColonias = gson.fromJson(jsonStatus, collectionType);

            if(listaColonias != null && !listaColonias.isEmpty()){
            daoManager.delete(Colonia.class);
            for(Colonia colonia : listaColonias){
            daoManager.insert(colonia);
            }
            }
            */


                        } else {
                            Toast.makeText(MainActivity.this, "Consulta incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    showProgress(false);
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(MainActivity.this, "Response Incorrecto", Toast.LENGTH_SHORT).show();
                }

//                try {
//
//                    String result = null;
//                    JSONArray ja = new JSONArray(result);
//                    JSONObject jo = null;
//
//                    listUsuario = new ArrayList<String>();
//                    listPassword = new ArrayList<String>();
//
//                    for(int i=0; i<ja.length(); i++) {
//
//                        jo = ja.getJSONObject(i);
//                        listUsuario.add(jo.getString("usuario"));
//                        listPassword.add(jo.getString("password"));
//                    }
//                }catch (Exception e) {
//                    Log.e("Webservice usuarios", e.toString());
//                }

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

                Toast.makeText(MainActivity.this, "Error de conexion al servidor", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void catalogoSeccionesWS(String laEncuesta) {

        showProgress2(true);

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
                            Type collectionType = new TypeToken<List<Datos>>() {
                            }.getType();
                            List<Datos> listaSecciones = gson.fromJson(jsonSecciones, collectionType);

                            Log.d(TAG, "cqs ----------->> listaSecciones:  " + listaSecciones);

                            daoManager = new DaoManager(db2);
                            if (listaSecciones != null && !listaSecciones.isEmpty()) {
                                daoManager.delete(Datos.class);
                                for (Datos secciones : listaSecciones) {
                                    daoManager.insert(secciones);
                                }
                            }


            /*
            //obtiene alcaldias
            jsonStatus = jsonObject.getJSONObject("data").getJSONArray("alcaldias").toString();
            collectionType = new TypeToken<List<Alcaldia>>() {}.getType();
            List<Alcaldia> listaAlcaldias = gson.fromJson(jsonStatus, collectionType);

            if(listaAlcaldias != null && !listaAlcaldias.isEmpty()){
            daoManager.delete(Alcaldia.class);
            for(Alcaldia alcaldia : listaAlcaldias ){
            daoManager.insert(alcaldia);
            }
            }

            //obtiene colonia
            jsonStatus = jsonObject.getJSONObject("data").getJSONArray("colonias").toString();
            collectionType = new TypeToken<List<Colonia>>() {}.getType();
            List<Colonia> listaColonias = gson.fromJson(jsonStatus, collectionType);

            if(listaColonias != null && !listaColonias.isEmpty()){
            daoManager.delete(Colonia.class);
            for(Colonia colonia : listaColonias){
            daoManager.insert(colonia);
            }
            }
            */


                        } else {
                            Toast.makeText(MainActivity.this, "Consulta incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
//                    showProgress(false);
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(MainActivity.this, "Response Incorrecto", Toast.LENGTH_SHORT).show();
                    String stackTrace = Log.getStackTraceString(e);
                    Log.i(TAG, "cqs ----------->> Response incorrecto: " + stackTrace);
                }

                showProgress2(false);
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Nombre", encuestaQuien);
                finish();
                startActivity(intent);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showProgress2(false);
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

                Toast.makeText(MainActivity.this, "Error de conexion al servidor\n inténtelo de nuevo", Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void dialogoRechazo() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                builder.setMessage("Sexo de quien rechaza")
                        .setTitle("Rechazo de Encuesta").setCancelable(false)
                        .setNegativeButton("Masculino", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                valoresRechazoMasculino();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("Nombre", encuestaQuien);
                                // actividad
                                startActivity(intent);
                                finish();

                            }
                        }).setPositiveButton("Femenino", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        valoresRechazoFemenino();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("Nombre", encuestaQuien);
                        // actividad
                        startActivity(intent);
                        finish();

                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
            }
        });

    }

    public void dialogoRechazo2() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                builder.setMessage("Sexo de quien rechaza")
                        .setTitle("Rechazo por temor de Contagio").setCancelable(false)
                        .setNegativeButton("Masculino", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                valoresRechazoMasculino2();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("Nombre", encuestaQuien);
                                // actividad
                                startActivity(intent);
                                finish();

                            }
                        }).setPositiveButton("Femenino", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        valoresRechazoFemenino2();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("Nombre", encuestaQuien);
                        // actividad
                        startActivity(intent);
                        finish();

                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
            }
        });

    }

    public void dameTodo() throws IOException {

        File myFile;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String TimeStampDB = sdf.format(cal.getTime());

        try {

            File sdCard = null, directory, file = null;

            // validamos si se encuentra montada nuestra memoria externa

            if (Environment.getExternalStorageState().equals("mounted")) {

                // Obtenemos el directorio de la memoria externa
                sdCard = Environment.getExternalStorageDirectory();

            }

            // Obtenemos el direcorio donde se encuentra nuestro
            // archivo a leer
            directory = new File(sdCard.getAbsolutePath() + "/Mis_archivos");

            // Creamos un objeto File de nuestro archivo a leer
            file = new File(directory, nombreArchivoCSV());

            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append("diario,seccion,usuario,fecha,hora,tablet");

            myOutWriter.append("\n");

            String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_" + sacaImei() + "";
            usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

            db = usdbh.getWritableDatabase();

            Cursor c = db.rawQuery("SELECT * FROM locatario", null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        String diario = c.getString(c.getColumnIndex("diario"));
                        String usuario = c.getString(c.getColumnIndex("usuario"));
                        String fecha = c.getString(c.getColumnIndex("fecha"));
                        String hora = c.getString(c.getColumnIndex("hora"));
                        String tablet = c.getString(c.getColumnIndex("tableta"));
                        String seccion = c.getString(c.getColumnIndex("seccion"));

                        myOutWriter.append(
                                diario + "," + seccion + "," + usuario + "," + fecha + "," + hora + "," + tablet);
                        myOutWriter.append("\n");

                    }

                    while (c.moveToNext());
                }

                c.close();
                myOutWriter.close();
                fOut.close();

            }
        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        } finally {

            db.close();

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress2(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView2.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView2.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView2.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView2.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    public void dialogoFecha() {
        // timer.cancel();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                builder.setMessage("La fecha del dispositivo es \n" + formattedDateFecha + "\n" + "Es correcto?")
                        .setTitle("IMPORTANTE...!!!").setCancelable(false)
                        .setNegativeButton("CONFIGURAR", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
                                System.exit(0);
                            }
                        }).setPositiveButton("CORRECTO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

//    //Enviar Base
//    public int uploadBase(String sourceFileUri) {
//
//        File sdCard;
//        sdCard = Environment.getExternalStorageDirectory();
//        final String pathBase = sdCard.getAbsolutePath() + "/Mis_archivos";
//
//        String fileName = sourceFileUri;
//
//        HttpURLConnection conn = null;
//        DataOutputStream dos = null;
//        String lineEnd = "\r\n";
//        String twoHyphens = "--";
//        String boundary = "*****";
//        int bytesRead, bytesAvailable, bufferSize;
//        byte[] buffer;
//        int maxBufferSize = 1 * 1024 * 1024;
//        File sourceFile = new File(sourceFileUri);
//
//        if (!sourceFile.isFile()) {
//
////				             dialog.dismiss();
//            Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + pathBase + "" + "/" + "20161124_002_359083065132816_1.jpg");
//            runOnUiThread(new Runnable() {
//                public void run() {
//
//                }
//            });
//
//            return 0;
//
//        }
//        else
//        {
//            try {
//                // open a URL connection to the Servlet
//                FileInputStream fileInputStream = new FileInputStream(sourceFile);
//                URL url = new URL(upLoadServerUriBase);
//                // Open a HTTP  connection to  the URL
//                conn = (HttpURLConnection) url.openConnection();
//                conn.setDoInput(true); // Allow Inputs
//                conn.setDoOutput(true); // Allow Outputs
//                conn.setUseCaches(false); // Don't use a Cached Copy
//                conn.setRequestMethod("POST");
//                conn.setRequestProperty("Connection", "Keep-Alive");
//                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
//                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//                conn.setRequestProperty("uploaded_file", fileName);
//
//                dos = new DataOutputStream(conn.getOutputStream());
//
//                dos.writeBytes(twoHyphens + boundary + lineEnd);
//                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\""
//                        + lineEnd);
//
//                dos.writeBytes(lineEnd);
//
//                // create a buffer of  maximum size
//                bytesAvailable = fileInputStream.available();
//
//                bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                buffer = new byte[bufferSize];
//                // read file and write it into form...
//                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//                while (bytesRead > 0) {
//                    dos.write(buffer, 0, bufferSize);
//                    bytesAvailable = fileInputStream.available();
//                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//                }
//                // send multipart form data necesssary after file data...
//                dos.writeBytes(lineEnd);
//                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//                // Responses from the server (code and message)
//                serverResponseCode = conn.getResponseCode();
//                String serverResponseMessage = conn.getResponseMessage();
//
//                Log.i("TAG", "HTTP Response is : "+ serverResponseMessage + ": " + serverResponseCode);
//
//                if(serverResponseCode == 200){
//
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//
//                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
//                                    +" http://www.androidexample.com/media/uploads/"
//                                    +"20161124_002_359083065132816_1.jpg";
//
////				                              Toast.makeText(Entrada.this, "File Upload Complete."+msg,Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//
//                //close the streams //
//                fileInputStream.close();
//                dos.flush();
//                dos.close();
//
//            } catch (MalformedURLException ex) {
//
////				                dialog.dismiss();
//                ex.printStackTrace();
//
//
//
//                Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + "Upload file to server "+ "error: " + ex.getMessage());
//
////				                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
//            } catch (Exception e) {
//
////				                dialog.dismiss();
//                e.printStackTrace();
//
//                Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + "Upload file to server Exception "+ "Exception : "+ e.getMessage());
//
////				                Log.e("Upload file to server Exception", "Exception : "
////				                                                 + e.getMessage(), e);
//            }
//            return serverResponseCode;
//
//        } // End else block
//    }
//
//    class UpdateBases extends AsyncTask<String, Float, String> {
//
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//
//        @Override
//        protected String doInBackground(String... params) {
//            File sdCard;
//            sdCard = Environment.getExternalStorageDirectory();
//            final String pathBase = sdCard.getAbsolutePath() + "/Mis_archivos";
//            String sDirectorio = pathBase;
//            final File f = new File(sDirectorio);
//            Log.i(TAG, "lista" + pathBase);
//            final String customURL = "https://opinion.cdmx.gob.mx/cgi-bin/bases/";
//            Log.i(TAG, " =====> lista 1: " + pathBase);
//            File F = new File(pathBase);
//            try {
//                if (F.exists()) {
//                    File[] ficheros = F.listFiles();
//                    for (int i = 0; i < ficheros.length; i++) {
//                        //Simulamos cierto retraso
//                        try {
//                            Thread.sleep(500);
//                        } catch (InterruptedException e) {
//                        }
//                        publishProgress(i / (float) (ficheros.length)); //Actualizamos los valores
//                    }
//                    String[] s = new String[ficheros.length];
//                    String[] t = new String[ficheros.length];
//                    for (int x = 0; x < ficheros.length; x++) {
//                        Log.i(TAG, " =====> lista: " + ficheros[x].getName());
//                        s[x] = pathBase + "/" + nombreEncuesta + "_" + sacaImei();
//                        Log.i(TAG, " =====> Nombre del Archivo: " + s[x]);
//                        uploadBase(s[x]);
//                    }
//                } else {
//                    Log.i(TAG, " =====> lista 2: " + "No existe el directorio");
//                }
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                Log.i(TAG, " =====> error zip: " + "_" + e.getMessage());
//            }
//            return null;
//        }
//
//        //tomo
//        protected void onPostExecute(String date2) {
//            super.onPostExecute(date2);
//            Toast.makeText(getApplicationContext(), "Archivo Enviado", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    //Enviar Audios
//    class UpdateAudios extends AsyncTask<String, Float, String> {
//
//        protected void onPreExecute() {
//            super.onPreExecute();
//
////					dialog = new ProgressDialog(CalendarViewFotos.this);
////			        dialog.setMessage("Enviando Fotografías...");
////			        dialog.setTitle("Progreso");
////			        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
////			        dialog.setCancelable(false);
////			        dialog.setProgress(0);
////			        dialog.setMax(100);
////			        dialog.show(); //Mostramos el diálogo antes de comenzar
//        }
//
//
//        @Override
//        protected String doInBackground(String... params) {
//
//
//            File sdCard;
//            sdCard = Environment.getExternalStorageDirectory();
////						final String pathAudios = sdCard.getAbsolutePath() + "/" + nombreEncuesta+"-Audio"+date2;
//            final String pathAudios = sdCard.getAbsolutePath() + "/" + nombreEncuesta +"-Audio" + formattedDate3 + "/";
//
//            String sDirectorio = pathAudios;
//            final File f = new File(sDirectorio);
//            Log.i(TAG,"lista"+pathAudios);
//
////						final String customURL = "https://opinion.cdmx.gob.mx/cgi-bin/fotos/programas_sociales/";
//            final String customURL = "https://opinion.cdmx.gob.mx/audios/"+nombreEncuesta+ "/";
//
//            Log.i(TAG, " =====> URL audios: " + customURL);
//            Log.i(TAG, " =====> lista audios 1: " + pathAudios);
//
//            File F = new File(pathAudios);
//
//            try {
//
//                if (F.exists()) {
//
//                    File[] ficheros = F.listFiles();
//
//                    for (int i = 0; i <ficheros.length; i++) {
//                        //Simulamos cierto retraso
//                        try {Thread.sleep(500); }
//                        catch (InterruptedException e) {}
//
//                        publishProgress(i/(float)(ficheros.length)); //Actualizamos los valores
//                    }
//
//
//
//                    String[] s = new String[ficheros.length];
//                    String[] t = new String[ficheros.length];
//                    for (int x = 0; x < ficheros.length; x++) {
//                        Log.i(TAG, " =====> lista audios: " + ficheros[x].getName());
//                        s[x] = pathAudios + "/" + ficheros[x].getName();
//                        t[x] = ficheros[x].getName();
//
////								 uploadFotos(s[x],date2);
//
//
//                        URL u = new URL (customURL+t[x]);
//                        Log.i(TAG, " =====> Archivo Audios custom: "+customURL+t[x] );
//                        HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection ();
//                        huc.setRequestMethod ("GET");  //OR  huc.setRequestMethod ("HEAD");
//                        huc.connect () ;
//                        huc.getResponseCode();
//                        Log.i(TAG, " =====> Archivo:  lista De Audios ==>" + huc.getResponseCode());
//                        if(huc.getResponseCode()==200){
//
//                            //moveFile(pathFotosN, t[x], pathFotosF);
//                            Log.i(TAG, " =====> Archivo:  En el servidor custom no hace nada==>" + t[x] );
//
//                        }else if(huc.getResponseCode()==404){
//
//                            uploadAudios(s[x]);
//                            Log.i(TAG, " =====> Archivo:  Enviado al servidor custom==>" + t[x] );
//
//
//                        }
//
//                    }
//                    // first parameter is d files second parameter is zip file name
//
//                } else {
//                    Log.i(TAG, " =====> lista 2: " + "No existe el directorio");
//                }
//                // first parameter is d files second parameter is zip file name
//
//            } catch (Exception e) {
//                String stackTrace = Log.getStackTraceString(e);
//                Log.i("Manda Audios","Error Manda Audios"+ stackTrace);
//            }
//
//
//            return null;
//        }
//
//
////				protected void onProgressUpdate (Float... valores) {
////			          int p = Math.round(100*valores[0]);
////			          dialog.setProgress(p);
////			      }
//
//
//        //tomo
//        protected void onPostExecute(String date2) {
//            super.onPostExecute(date2);
////					dialog.dismiss();
//
//            //	Toast.makeText(CalendarViewFotos.this, "Envío de Fotografias completo ",Toast.LENGTH_LONG).show();
//
////					correo(date2, prefix);
////					correo(date2, sacaChip());
//
//        }
//
//    }
//
//    public int uploadAudios(String sourceFileUri) {
//
//        File sdCard;
//        sdCard = Environment.getExternalStorageDirectory();
//        //final String pathFotos = sdCard.getAbsolutePath() + "/"+ nombreEncuesta+"-Audio"+fech;
//        final String pathAudios = sdCard.getAbsolutePath() + nombreEncuesta +"-Audio" + formattedDate3 + "/";
//
//        String fileName = sourceFileUri;
//
//        HttpURLConnection conn = null;
//        DataOutputStream dos = null;
//        String lineEnd = "\r\n";
//        String twoHyphens = "--";
//        String boundary = "*****";
//        int bytesRead, bytesAvailable, bufferSize;
//        byte[] buffer;
//        int maxBufferSize = 1 * 1024 * 1024;
//        File sourceFile = new File(sourceFileUri);
//
//        if (!sourceFile.isFile()) {
//
////			     dialog.dismiss();
//            Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + pathAudios + "" + "/" + "20161124_002_359083065132816_1.jpg");
//            runOnUiThread(new Runnable() {
//                public void run() {
//
//                }
//            });
//
//            return 0;
//
//        }
//        else
//        {
//            try {
//                // open a URL connection to the Servlet
//                FileInputStream fileInputStream = new FileInputStream(sourceFile);
//                URL url = new URL(upLoadServerUriAudio);
//                // Open a HTTP  connection to  the URL
//                conn = (HttpURLConnection) url.openConnection();
//                conn.setDoInput(true); // Allow Inputs
//                conn.setDoOutput(true); // Allow Outputs
//                conn.setUseCaches(false); // Don't use a Cached Copy
//                conn.setRequestMethod("POST");
//                conn.setRequestProperty("Connection", "Keep-Alive");
//                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
//                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//                conn.setRequestProperty("uploaded_file", fileName);
//
//                dos = new DataOutputStream(conn.getOutputStream());
//
//                dos.writeBytes(twoHyphens + boundary + lineEnd);
//                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\""
//                        + lineEnd);
//
//                dos.writeBytes(lineEnd);
//
//                // create a buffer of  maximum size
//                bytesAvailable = fileInputStream.available();
//
//                bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                buffer = new byte[bufferSize];
//                // read file and write it into form...
//                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//                while (bytesRead > 0) {
//                    dos.write(buffer, 0, bufferSize);
//                    bytesAvailable = fileInputStream.available();
//                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//                }
//                // send multipart form data necesssary after file data...
//                dos.writeBytes(lineEnd);
//                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//                // Responses from the server (code and message)
//                serverResponseCode = conn.getResponseCode();
//                String serverResponseMessage = conn.getResponseMessage();
//
//                Log.i("uploadFile", "HTTP Response is : "+ serverResponseMessage + ": " + serverResponseCode);
//
//                if(serverResponseCode == 200){
//
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//
//                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
//                                    +" http://www.androidexample.com/media/uploads/"
//                                    +"20161124_002_359083065132816_1.jpg";
//
////			                      Toast.makeText(Entrada.this, "File Upload Complete."+msg,Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//
//                //close the streams //
//                fileInputStream.close();
//                dos.flush();
//                dos.close();
//
//            } catch (MalformedURLException ex) {
//
////			        dialog.dismiss();
//                ex.printStackTrace();
//
//                runOnUiThread(new Runnable() {
//                    public void run() {
////			                messageText.setText("MalformedURLException Exception : check script url.");
////			                Toast.makeText(CalendarViewFotos.this, "MalformedURLException",
////			                                                    Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + "Upload file to server "+ "error: " + ex.getMessage());
//
////			        Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
//            } catch (Exception e) {
//
////			        dialog.dismiss();
//                e.printStackTrace();
//
//                runOnUiThread(new Runnable() {
//                    public void run() {
////			                messageText.setText("Error de Internet");
////			                Toast.makeText(CalendarViewFotos.this, "Error de Internet",
////			                        Toast.LENGTH_SHORT).show();
//                    }
//                });
//                Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + "Upload file to server Exception "+ "Exception : "+ e.getMessage());
//
////			        Log.e("Upload file to server Exception", "Exception : "
////			                                         + e.getMessage(), e);
//            }
//            return serverResponseCode;
//
//        } // End else block
//    }
//

}


