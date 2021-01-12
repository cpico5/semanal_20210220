

package mx.gob.cdmx.semanal_20210109;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
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

import cz.msebera.android.httpclient.Header;
import mx.gob.cdmx.semanal_20210109.model.DatoContent;
import mx.gob.cdmx.semanal_20210109.model.Usuario;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;
import static mx.gob.cdmx.semanal_20210109.Nombre.USUARIO;
import static mx.gob.cdmx.semanal_20210109.Nombre.customURL;
import static mx.gob.cdmx.semanal_20210109.Nombre.encuesta;

public class MainActivityPantalla1 extends Activity implements AdapterView.OnItemClickListener {


    private static final String LOG_TAG = "Grabadora";
    private static final String TAG = "Pantalla1";
    private View mProgressView;
    private Usuario usuario;

    final Context context = this;

    private ArrayList<CheckBox> mChecks;
    private ArrayList<CheckBox> mSelectedChecks;

    private ArrayList<CheckBox> mChecks2;
    private ArrayList<CheckBox> mSelectedChecks2;

    public MediaRecorder recorder = new MediaRecorder();
    private Handler handler;

    private Button btnGuardar;
    private Button btnSalir;

    private TextView textPreguntaEntrada;
    private TextView textPreguntaAporta;
    private TextView textPreguntaOcupacion;
    private TextView textPreguntaCuantosCoches;
    private TextView textPreguntaCuartos;
    private TextView textPreguntaCuartosDormir;
    private TextView textPreguntaFocos;
    private TextView textPreguntaBanos;
    private TextView textPreguntaRegadera;
    private TextView textPreguntaEstufa;
    private TextView textPreguntaEdad;
    private TextView textPreguntaTipoPiso;
    private TextView textPreguntaTipoVivienda;
    private TextView textPreguntaGenero;


    double latitude;
    double longitude;

    Random random = new java.util.Random();
    public int rand;

    public RadioGroup rdPreguntaOcupacion, rdPreguntaFocos, rdPreguntaCuantosCoches, rdPreguntaCuartos, rdPreguntaCuartosDormir,
            rdPreguntaBanos, rdPreguntaRegadera,
            rdPreguntaEstufa, rdPreguntaEdad, rdPreguntaGenero, rdPreguntaTipoVivienda, rdPreguntaTipoPiso;


    public RadioGroup rdPreguntaAporta, rdPreguntaAbandono;


    private static final int READ_BLOCK_SIZE = 100000;

    Nombre nom = new Nombre();
    String nombreEncuesta = nom.nombreEncuesta();

    UsuariosSQLiteHelper usdbh;
    private SQLiteDatabase db;

    UsuariosSQLiteHelper2 usdbh2;
    private SQLiteDatabase db2;

    private Spinner spinnerDelegaciones;
    private Spinner spinnerc3a;
    private Spinner spinnerMeses;
    private Spinner spinnerSemana;
    private Spinner spinnerCalifica;

    private Spinner spinner0;

    Timer timer;

    public String laDelegacion;
    public EditText txtSeccion;
    public String Alcalde;
    public String id_alcaldia;

    public String opEstadoCivil = "sin datos";
    public String opHijos = "sin datos";
    public String opJefe = "sin datos";
    public String opAporta = "sin datos";
    public String opEstudio = "sin datos";
    public String opAbandono = "sin datos";
    public String opOcupacion = "sin datos";
    public String opCoche = "sin datos";
    public String opFocos = "sin datos";
    public String opCuantosCoches = "sin datos";

    public String opCuartos = "sin datos";
    public String opCuartosDormir = "sin datos";
    public String opBanos = "sin datos";
    public String opRegadera = "sin datos";
    public String opInternet = "sin datos";
    public String opTrabajaron = "sin datos";
    public String opEstufa = "sin datos";
    public String opEdad = "sin datos";
    public String opGenero = "sin datos";
    public String opTipoVivienda = "sin datos";
    public String opTipoPiso = "sin datos";


    public String op1 = "sin datos";
    public RadioGroup rdPregunta1;
    public EditText editPregunta1;
    public String captura1;
    LinearLayout lay1;
    private Spinner spinner1;
    private TextView textPregunta1;
    public String op2 = "sin datos";
    public RadioGroup rdPregunta2;
    public EditText editPregunta2;
    public String captura2;
    LinearLayout lay2;
    private Spinner spinner2;
    private TextView textPregunta2;
    public String op3 = "sin datos";
    public RadioGroup rdPregunta3;
    public EditText editPregunta3;
    public String captura3;
    LinearLayout lay3;
    private Spinner spinner3;
    private TextView textPregunta3;
    public String op4 = "sin datos";
    public RadioGroup rdPregunta4;
    public EditText editPregunta4;
    public String captura4;
    LinearLayout lay4;
    private Spinner spinner4;
    private TextView textPregunta4;
    public String op5 = "sin datos";
    public RadioGroup rdPregunta5;
    public EditText editPregunta5;
    public String captura5;
    LinearLayout lay5;
    private Spinner spinner5;
    private TextView textPregunta5;
    public String op6 = "sin datos";
    public RadioGroup rdPregunta6;
    public EditText editPregunta6;
    public String captura6;
    LinearLayout lay6;
    private Spinner spinner6;
    private TextView textPregunta6;
    public String op7 = "sin datos";
    public RadioGroup rdPregunta7;
    public EditText editPregunta7;
    public String captura7;
    LinearLayout lay7;
    private Spinner spinner7;
    private TextView textPregunta7;
    public String op8 = "sin datos";
    public RadioGroup rdPregunta8;
    public EditText editPregunta8;
    public String captura8;
    LinearLayout lay8;
    private Spinner spinner8;
    private TextView textPregunta8;
    public String op9 = "sin datos";
    public RadioGroup rdPregunta9;
    public EditText editPregunta9;
    public String captura9;
    LinearLayout lay9;
    private Spinner spinner9;
    private TextView textPregunta9;
    public String opc1 = "sin datos";
    public RadioGroup rdPreguntac1;
    public EditText editPreguntac1;
    public String capturac1;
    LinearLayout layc1;
    private Spinner spinnerc1;
    private TextView textPreguntac1;
    public String opc2 = "sin datos";
    public RadioGroup rdPreguntac2;
    public EditText editPreguntac2;
    public String capturac2;
    LinearLayout layc2;
    private Spinner spinnerc2;
    private TextView textPreguntac2;
    public String opc2a = "sin datos";
    public RadioGroup rdPreguntac2a;
    public EditText editPreguntac2a;
    public String capturac2a;
    LinearLayout layc2a;
    private Spinner spinnerc2a;
    private TextView textPreguntac2a;
    public String opc2b = "sin datos";
    public RadioGroup rdPreguntac2b;
    public EditText editPreguntac2b;
    public String capturac2b;
    LinearLayout layc2b;
    private Spinner spinnerc2b;
    private TextView textPreguntac2b;
    public String opc2c = "sin datos";
    public RadioGroup rdPreguntac2c;
    public EditText editPreguntac2c;
    public String capturac2c;
    LinearLayout layc2c;
    private Spinner spinnerc2c;
    private TextView textPreguntac2c;
    public String opc3 = "sin datos";
    public RadioGroup rdPreguntac3;
    public EditText editPreguntac3;
    public String capturac3;
    LinearLayout layc3;
    private Spinner spinnerc3;
    private TextView textPreguntac3;
    public String opc4 = "sin datos";
    public RadioGroup rdPreguntac4;
    public EditText editPreguntac4;
    public String capturac4;
    LinearLayout layc4;
    private Spinner spinnerc4;
    private TextView textPreguntac4;
    public String opc4a = "sin datos";
    public RadioGroup rdPreguntac4a;
    public EditText editPreguntac4a;
    public String capturac4a;
    LinearLayout layc4a;
    private Spinner spinnerc4a;
    private TextView textPreguntac4a;
    public String opc5 = "sin datos";
    public RadioGroup rdPreguntac5;
    public EditText editPreguntac5;
    public String capturac5;
    LinearLayout layc5;
    private Spinner spinnerc5;
    private TextView textPreguntac5;
    public String opc6 = "sin datos";
    public RadioGroup rdPreguntac6;
    public EditText editPreguntac6;
    public String capturac6;
    LinearLayout layc6;
    private Spinner spinnerc6;
    private TextView textPreguntac6;
    public String opc7 = "sin datos";
    public RadioGroup rdPreguntac7;
    public EditText editPreguntac7;
    public String capturac7;
    LinearLayout layc7;
    private Spinner spinnerc7;
    private TextView textPreguntac7;
    public String opc7a = "sin datos";
    public RadioGroup rdPreguntac7a;
    public EditText editPreguntac7a;
    public String capturac7a;
    LinearLayout layc7a;
    private Spinner spinnerc7a;
    private TextView textPreguntac7a;
    public String opc7b = "sin datos";
    public RadioGroup rdPreguntac7b;
    public EditText editPreguntac7b;
    public String capturac7b;
    LinearLayout layc7b;
    private Spinner spinnerc7b;
    private TextView textPreguntac7b;
    public String opc7c = "sin datos";
    public RadioGroup rdPreguntac7c;
    public EditText editPreguntac7c;
    public String capturac7c;
    LinearLayout layc7c;
    private Spinner spinnerc7c;
    private TextView textPreguntac7c;
    public String opc8 = "sin datos";
    public RadioGroup rdPreguntac8;
    public EditText editPreguntac8;
    public String capturac8;
    LinearLayout layc8;
    private Spinner spinnerc8;
    private TextView textPreguntac8;
    public String opc9 = "sin datos";
    public RadioGroup rdPreguntac9;
    public EditText editPreguntac9;
    public String capturac9;
    LinearLayout layc9;
    private Spinner spinnerc9;
    private TextView textPreguntac9;
    public String opc10 = "sin datos";
    public RadioGroup rdPreguntac10;
    public EditText editPreguntac10;
    public String capturac10;
    LinearLayout layc10;
    private Spinner spinnerc10;
    private TextView textPreguntac10;
    public String opc11 = "sin datos";
    public RadioGroup rdPreguntac11;
    public EditText editPreguntac11;
    public String capturac11;
    LinearLayout layc11;
    private Spinner spinnerc11;
    private TextView textPreguntac11;
    public String opc12 = "sin datos";
    public RadioGroup rdPreguntac12;
    public EditText editPreguntac12;
    public String capturac12;
    LinearLayout layc12;
    private Spinner spinnerc12;
    private TextView textPreguntac12;
    public String opc13 = "sin datos";
    public RadioGroup rdPreguntac13;
    public EditText editPreguntac13;
    public String capturac13;
    LinearLayout layc13;
    private Spinner spinnerc13;
    private TextView textPreguntac13;
    public String opc14 = "sin datos";
    public RadioGroup rdPreguntac14;
    public EditText editPreguntac14;
    public String capturac14;
    LinearLayout layc14;
    private Spinner spinnerc14;
    private TextView textPreguntac14;
    public String opc15 = "sin datos";
    public RadioGroup rdPreguntac15;
    public EditText editPreguntac15;
    public String capturac15;
    LinearLayout layc15;
    private Spinner spinnerc15;
    private TextView textPreguntac15;
    public String opc16 = "sin datos";
    public RadioGroup rdPreguntac16;
    public EditText editPreguntac16;
    public String capturac16;
    LinearLayout layc16;
    private Spinner spinnerc16;
    private TextView textPreguntac16;
    public String opc16a = "sin datos";
    public RadioGroup rdPreguntac16a;
    public EditText editPreguntac16a;
    public String capturac16a;
    LinearLayout layc16a;
    private Spinner spinnerc16a;
    private TextView textPreguntac16a;
    public String opc16b = "sin datos";
    public RadioGroup rdPreguntac16b;
    public EditText editPreguntac16b;
    public String capturac16b;
    LinearLayout layc16b;
    private Spinner spinnerc16b;
    private TextView textPreguntac16b;
    public String opc17 = "sin datos";
    public RadioGroup rdPreguntac17;
    public EditText editPreguntac17;
    public String capturac17;
    LinearLayout layc17;
    private Spinner spinnerc17;
    private TextView textPreguntac17;

    LinearLayout laySocioE;
    LinearLayout layEst;
    LinearLayout layAporta;
    LinearLayout layOcupacion;
    LinearLayout layCuartos;
    LinearLayout layCuartosDormir;
    LinearLayout layFocos;
    LinearLayout layBanos;
    LinearLayout layRegadera;
    LinearLayout layEstufa;
    LinearLayout layEdad;
    LinearLayout layTipoPiso;
    LinearLayout layTipoVivienda;
    LinearLayout layGenero;

    public Resources res;

    UsuariosSQLiteHelper3 usdbh3;
    private SQLiteDatabase db3;

    LinearLayout layCuantosCoches;
    public RadioButton radio_abandono1;
    public RadioButton radio_abandono2;
    public RadioButton radio_abandono3;
    public RadioButton radio_abandono4;


    public String capturaOcupacion, capturaCuantosCoches, capturaFocos, capturaCuartos, capturaCuartosDormir, capturaBanos;
    public String capturaEstufa, capturaEdad, capturaGenero, capturaTipoVivienda, capturaTipoPiso;
    public String capturaJefe, capturaAporta;


    public String maximo = "";
    int elMaximo;
    int elMaximoConsecutivo;
    String tipoEncuesta;

    public String tablet;

    public String tiempo;

    Calendar c = Calendar.getInstance();

    SimpleDateFormat df1 = new SimpleDateFormat("yyy-MM-dd");
    String formattedDate1 = df1.format(c.getTime());

    SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
    String formattedDate2 = df2.format(c.getTime());

    SimpleDateFormat df3 = new SimpleDateFormat("yyyMMdd");
    String formattedDate3 = df3.format(c.getTime());

    SimpleDateFormat df6 = new SimpleDateFormat("MM");
    String formattedDate6 = df6.format(c.getTime());

    SimpleDateFormat df7 = new SimpleDateFormat("dd");
    String formattedDate7 = df7.format(c.getTime());

    SimpleDateFormat df4 = new SimpleDateFormat("HH:mm:ss a");
    String formattedDate4 = df4.format(c.getTime());

    SimpleDateFormat df5 = new SimpleDateFormat("HH:mm:ss");
    String formattedDate5 = df5.format(c.getTime());

    Calendar t1 = Calendar.getInstance();
    long milis1 = t1.getTimeInMillis();

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

    public String cachaNombre() {
        Bundle datos = this.getIntent().getExtras();
        String Nombre = datos.getString("Nombre");
        return Nombre;
    }


    public String cachaSeccion() {
        Bundle datos = this.getIntent().getExtras();
        String Seccion = datos.getString("Seccion");
        return Seccion;
    }

    public String cachaDelegacion() {
        Bundle datos = this.getIntent().getExtras();
        String delegacion = datos.getString("delegacion");
        return delegacion;
    }

    public String cachaEquipo() {
        Bundle datos = this.getIntent().getExtras();
        String equipo = datos.getString("equipo");
        return equipo;
    }


    @SuppressLint("MissingPermission")
    public String sacaChip() {
        String szImei;
        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);//Telefono
        szImei = TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE
        if (szImei == null) {
            szImei = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);// Tableta
        }
        return szImei;
    }

    @SuppressLint("MissingPermission")
    public String sacaImei() {
        String szImei;
        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);//Telefono
        szImei = TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE
        if (szImei == null) {
            szImei = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);// Tableta
        }
        return szImei;
    }


    public void dialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Desea continuar Encuestando..?").setTitle("IMPORTANTE").setCancelable(false)
                .setNegativeButton("SALIR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        detenerGrabacion();

                        Intent i = new Intent(MainActivityPantalla1.this, Bienvenida.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        System.exit(0); // metodo que se debe implementar
                    }
                }).setPositiveButton("CONTINUAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                detenerGrabacion();

                Intent i = new Intent(MainActivityPantalla1.this, Bienvenida.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("Nombre", cachaNombre());
                i.putExtra(USUARIO, usuario);
                startActivity(i);
                System.exit(0); // metodo que se debe implementar
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void dialogoCierraEncuesta() {
        timer.cancel();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        MainActivityPantalla1.this.runOnUiThread(new Runnable() {
            public void run() {
                builder.setMessage("Excediste el tiempo m�ximo para realizar la encuesta \n"
                        + "��� Se detendr� la grabaci�n y se reiniciar� la Aplicaci�n..!!!").setTitle("AVISO...!!!")
                        .setCancelable(false).setNegativeButton("SALIR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        detenerGrabacion();

                        Intent i = new Intent(MainActivityPantalla1.this, Entrada.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        System.exit(0); // metodo que se debe
// implementar
                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
            }
        });

    }


// EVENTO AL PULSAR EL BOTON ATRAS

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
// Esto es lo que hace mi bot�n al pulsar ir a atr�s
            Toast.makeText(getApplicationContext(), "No puedo ir hacia atr�s!!\nEstoy grabando...", Toast.LENGTH_SHORT)
                    .show();

// dialogoAbandono();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public String nombreAudio() {
        elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;
        String date = formattedDate3.toString();
        String var2 = ".mp3";
        int consecutivo = Integer.parseInt(sacaConsecutivo().toString()) + 1;
        String elConsecutivo = String.valueOf(consecutivo);
        int Cons = elConsecutivo.length();
        if (Cons == 1) {
            elConsecutivo = "00" + elConsecutivo;
        } else if (Cons == 2) {
            elConsecutivo = "0" + elConsecutivo;
        } else {
            elConsecutivo = elConsecutivo;
        }
        String usuario;
        int tamanoUsuario = cachaNombre().length();
        if (tamanoUsuario == 1) {
            usuario = "00" + cachaNombre();
        } else if (tamanoUsuario == 2) {
            usuario = "0" + cachaNombre();
        } else {
            usuario = cachaNombre();
        }
        final String nombreAudio = nombreEncuesta + "_" + date + "_" + sacaImei() + "_" + cachaNombre() + "_" + elConsecutivo + ".mp3";
        return nombreAudio;
    }

    public String elTiempo() {
// Para la diferenci entre tiempos
        Calendar t2 = Calendar.getInstance();
        long milis2 = t2.getTimeInMillis();
//    long diff = milis2 - t1();
        long diff = milis2 - milis1;
        long diffSegundos = diff / 1000;
        long diffMinutos = diffSegundos / 60;
        long residuo = diffSegundos % 60;
        System.out.println(diffSegundos);
        System.out.println(diffMinutos);
        System.out.println(residuo);
        tiempo = diffMinutos + ":" + residuo;
        return tiempo;
    }


    private Integer[] mLinearLayoutIds = {
//            R.layout.activity_pantalla1,
//            R.layout.activity_pantalla2,
//            R.layout.activity_pantalla3,
//            R.layout.activity_pantalla4,
//            R.layout.activity_pantalla5,
//            R.layout.activity_pantalla6,
//            R.layout.activity_pantalla7,
//            R.layout.activity_pantalla8,
//            R.layout.activity_pantalla9,
//            R.layout.activity_pantalla10,
////// R.layout.activity_pantalla11,
//// R.layout.activity_pantalla12,
//// R.layout.activity_pantalla13,
//// R.layout.activity_pantalla14,
//// R.layout.activity_pantalla15,
//// R.layout.activity_pantalla16,
//// R.layout.activity_pantalla17,
//// R.layout.activity_pantalla18,
//// R.layout.activity_pantalla19,
    };

//    int[] layouts_c1 = new int[] {
//            R.layout.pregc1a,
//            R.layout.pregc1b,
//            R.layout.pregc1c,
//            R.layout.pregc1d,
//            R.layout.pregc1e,
//            R.layout.pregc1f,
//            R.layout.pregc1g,
//            R.layout.pregc1h,
//              };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla1); // COMENTAR ESTA CUANDO ES ALEATORIO

        Intent startingIntent = getIntent();
        if (startingIntent == null) {
            Log.e(TAG, "No Intent?  We�re not supposed to be here...");
            finish();
            return;
        }

        if (savedInstanceState != null) {
            usuario = (Usuario) savedInstanceState.getSerializable(USUARIO);
        } else {
            usuario = (Usuario) startingIntent.getSerializableExtra(USUARIO);
        }

// Carga las pantallas aleatoriamente
        random = new java.util.Random();
//
        /*DESCOMENTAR ESTAS 3 LINEAS CUANDO YA ESTA EL NUMERO DE HOJAS ALEATORIO */
//        rand = random.nextInt(9);
//        setContentView(mLinearLayoutIds[rand]);
//        Log.i(null, "El aleatorio: " + rand); // si rand= 11 en el layoud corresponde a uno mas


        /*activity_pantalla12*/

// Crea Log cuando falla la app
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(MainActivityPantalla1.this, this));


        Alcalde = Utils.getAlcaldes(sacaAlcaldia(cachaSeccion()));
        id_alcaldia = String.valueOf(Utils.getIdAlcaldia(sacaAlcaldia(cachaSeccion())));

        Log.i(TAG, "cqs -->> Alcalde: " + Alcalde);
        Log.i(TAG, "cqs -->> id_alcaldia: " + id_alcaldia);


        cachaNombre(); // llamado al metodo para obtener el numero del
// encuestador

        try {

            handler = new Handler();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(TAG, "Iniciando Grabaci�n");
                            grabar();
                        }

                    });

                }
            }).start();

        } catch (Exception e) {

        }

///////////// EL TIMER PARA PARAR LA ENCUESTA /////////////////

        timer = new Timer();
//    timer.schedule(new CierraEncuesta(), 1800000); // 8 Minutos 480000

////////////////////////
        mProgressView = findViewById(R.id.login_progressMain);

        txtSeccion = (EditText) findViewById(R.id.txtSeccion);

        txtSeccion.setText(cachaSeccion());
        txtSeccion.setEnabled(false);

        textPreguntaEntrada = (TextView) findViewById(R.id.textPreguntaEntrada);
        textPregunta1 = (TextView) findViewById(R.id.textPregunta1);
        textPregunta2 = (TextView) findViewById(R.id.textPregunta2);
        textPregunta3 = (TextView) findViewById(R.id.textPregunta3);
        textPregunta4 = (TextView) findViewById(R.id.textPregunta4);
        textPregunta5 = (TextView) findViewById(R.id.textPregunta5);
        textPregunta6 = (TextView) findViewById(R.id.textPregunta6);
        textPregunta7 = (TextView) findViewById(R.id.textPregunta7);
        textPregunta8 = (TextView) findViewById(R.id.textPregunta8);
        textPregunta9 = (TextView) findViewById(R.id.textPregunta9);
        textPreguntac1 = (TextView) findViewById(R.id.textPreguntac1);
        textPreguntac2 = (TextView) findViewById(R.id.textPreguntac2);
        textPreguntac2a = (TextView) findViewById(R.id.textPreguntac2a);
        textPreguntac2b = (TextView) findViewById(R.id.textPreguntac2b);
        textPreguntac2c = (TextView) findViewById(R.id.textPreguntac2c);
        textPreguntac3 = (TextView) findViewById(R.id.textPreguntac3);
        textPreguntac4 = (TextView) findViewById(R.id.textPreguntac4);
        textPreguntac4a = (TextView) findViewById(R.id.textPreguntac4a);
        textPreguntac5 = (TextView) findViewById(R.id.textPreguntac5);
        textPreguntac6 = (TextView) findViewById(R.id.textPreguntac6);
        textPreguntac7 = (TextView) findViewById(R.id.textPreguntac7);
        textPreguntac7a = (TextView) findViewById(R.id.textPreguntac7a);
        textPreguntac7b = (TextView) findViewById(R.id.textPreguntac7b);
        textPreguntac7c = (TextView) findViewById(R.id.textPreguntac7c);
        textPreguntac8 = (TextView) findViewById(R.id.textPreguntac8);
        textPreguntac9 = (TextView) findViewById(R.id.textPreguntac9);
        textPreguntac10 = (TextView) findViewById(R.id.textPreguntac10);
        textPreguntac11 = (TextView) findViewById(R.id.textPreguntac11);
        textPreguntac12 = (TextView) findViewById(R.id.textPreguntac12);
        textPreguntac13 = (TextView) findViewById(R.id.textPreguntac13);
        textPreguntac14 = (TextView) findViewById(R.id.textPreguntac14);
        textPreguntac15 = (TextView) findViewById(R.id.textPreguntac15);
        textPreguntac16 = (TextView) findViewById(R.id.textPreguntac16);
        textPreguntac16a = (TextView) findViewById(R.id.textPreguntac16a);
        textPreguntac16b = (TextView) findViewById(R.id.textPreguntac16b);
        textPreguntac17 = (TextView) findViewById(R.id.textPreguntac17);

        textPreguntaAporta = (TextView) findViewById(R.id.textPreguntaAporta);
        textPreguntaOcupacion = (TextView) findViewById(R.id.textPreguntaOcupacion);
        textPreguntaCuantosCoches = (TextView) findViewById(R.id.textPreguntaCuantosCoches);
        textPreguntaCuartos = (TextView) findViewById(R.id.textPreguntaCuartos);
        textPreguntaCuartosDormir = (TextView) findViewById(R.id.textPreguntaCuartosDormir);
        textPreguntaFocos = (TextView) findViewById(R.id.textPreguntaFocos);
        textPreguntaBanos = (TextView) findViewById(R.id.textPreguntaBanos);
        textPreguntaRegadera = (TextView) findViewById(R.id.textPreguntaRegadera);
        textPreguntaEstufa = (TextView) findViewById(R.id.textPreguntaEstufa);
        textPreguntaEdad = (TextView) findViewById(R.id.textPreguntaEdad);
        textPreguntaTipoPiso = (TextView) findViewById(R.id.textPreguntaTipoPiso);
        textPreguntaTipoVivienda = (TextView) findViewById(R.id.textPreguntaTipoVivienda);
        textPreguntaGenero = (TextView) findViewById(R.id.textPreguntaGenero);

        // justificar el texto
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textPreguntaEntrada.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
//            textPregunta1.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPregunta2.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPregunta3.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPregunta4.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPregunta5.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPregunta6.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPregunta7.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPregunta8.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPregunta9.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac1.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac2.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac2a.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac2b.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac2c.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac3.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac4.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac4a.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac5.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac6.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac7.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac7a.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac7b.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac7c.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac8.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac9.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac10.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac11.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac12.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac13.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac14.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac15.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac16.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac16a.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac16b.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntac17.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);


            textPreguntaAporta.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntaOcupacion.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntaCuantosCoches.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntaCuartos.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntaCuartosDormir.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntaFocos.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntaBanos.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntaRegadera.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntaEstufa.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntaEdad.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntaTipoPiso.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntaTipoVivienda.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            textPreguntaGenero.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);


        }
        res = getResources();


        rdPregunta1 = (RadioGroup) findViewById(R.id.rdPregunta1);
        captura1 = res.getString(R.string.PREGUNTA1);
        lay1 = (LinearLayout) findViewById(R.id.lay1);
        rdPregunta2 = (RadioGroup) findViewById(R.id.rdPregunta2);
        captura2 = res.getString(R.string.PREGUNTA2);
        lay2 = (LinearLayout) findViewById(R.id.lay2);
        rdPregunta3 = (RadioGroup) findViewById(R.id.rdPregunta3);
        captura3 = res.getString(R.string.PREGUNTA3);
        lay3 = (LinearLayout) findViewById(R.id.lay3);
        rdPregunta4 = (RadioGroup) findViewById(R.id.rdPregunta4);
        captura4 = res.getString(R.string.PREGUNTA4);
        lay4 = (LinearLayout) findViewById(R.id.lay4);
        rdPregunta5 = (RadioGroup) findViewById(R.id.rdPregunta5);
        captura5 = res.getString(R.string.PREGUNTA5);
        lay5 = (LinearLayout) findViewById(R.id.lay5);
        rdPregunta6 = (RadioGroup) findViewById(R.id.rdPregunta6);
        captura6 = res.getString(R.string.PREGUNTA6);
        lay6 = (LinearLayout) findViewById(R.id.lay6);
        rdPregunta7 = (RadioGroup) findViewById(R.id.rdPregunta7);
        captura7 = res.getString(R.string.PREGUNTA7);
        lay7 = (LinearLayout) findViewById(R.id.lay7);
        rdPregunta8 = (RadioGroup) findViewById(R.id.rdPregunta8);
        captura8 = res.getString(R.string.PREGUNTA8);
        lay8 = (LinearLayout) findViewById(R.id.lay8);
        rdPregunta9 = (RadioGroup) findViewById(R.id.rdPregunta9);
        captura9 = res.getString(R.string.PREGUNTA9);
        lay9 = (LinearLayout) findViewById(R.id.lay9);
        rdPreguntac1 = (RadioGroup) findViewById(R.id.rdPreguntac1);
        capturac1 = res.getString(R.string.PREGUNTAc1);
        layc1 = (LinearLayout) findViewById(R.id.layc1);
        rdPreguntac2 = (RadioGroup) findViewById(R.id.rdPreguntac2);
        capturac2 = res.getString(R.string.PREGUNTAc2);
        layc2 = (LinearLayout) findViewById(R.id.layc2);
        rdPreguntac2a = (RadioGroup) findViewById(R.id.rdPreguntac2a);
        capturac2a = res.getString(R.string.PREGUNTAc2a);
        layc2a = (LinearLayout) findViewById(R.id.layc2a);
        rdPreguntac2b = (RadioGroup) findViewById(R.id.rdPreguntac2b);
        capturac2b = res.getString(R.string.PREGUNTAc2b);
        layc2b = (LinearLayout) findViewById(R.id.layc2b);
        rdPreguntac2c = (RadioGroup) findViewById(R.id.rdPreguntac2c);
        capturac2c = res.getString(R.string.PREGUNTAc2c);
        layc2c = (LinearLayout) findViewById(R.id.layc2c);
        rdPreguntac3 = (RadioGroup) findViewById(R.id.rdPreguntac3);
        capturac3 = res.getString(R.string.PREGUNTAc3);
        layc3 = (LinearLayout) findViewById(R.id.layc3);
        rdPreguntac4 = (RadioGroup) findViewById(R.id.rdPreguntac4);
        capturac4 = res.getString(R.string.PREGUNTAc4);
        layc4 = (LinearLayout) findViewById(R.id.layc4);
        rdPreguntac4a = (RadioGroup) findViewById(R.id.rdPreguntac4a);
        capturac4a = res.getString(R.string.PREGUNTAc4a);
        layc4a = (LinearLayout) findViewById(R.id.layc4a);
        rdPreguntac5 = (RadioGroup) findViewById(R.id.rdPreguntac5);
        capturac5 = res.getString(R.string.PREGUNTAc5);
        layc5 = (LinearLayout) findViewById(R.id.layc5);
        rdPreguntac6 = (RadioGroup) findViewById(R.id.rdPreguntac6);
        capturac6 = res.getString(R.string.PREGUNTAc6);
        layc6 = (LinearLayout) findViewById(R.id.layc6);
        rdPreguntac7 = (RadioGroup) findViewById(R.id.rdPreguntac7);
        capturac7 = res.getString(R.string.PREGUNTAc7);
        layc7 = (LinearLayout) findViewById(R.id.layc7);
        rdPreguntac7a = (RadioGroup) findViewById(R.id.rdPreguntac7a);
        capturac7a = res.getString(R.string.PREGUNTAc7a);
        layc7a = (LinearLayout) findViewById(R.id.layc7a);
        rdPreguntac7b = (RadioGroup) findViewById(R.id.rdPreguntac7b);
        capturac7b = res.getString(R.string.PREGUNTAc7b);
        layc7b = (LinearLayout) findViewById(R.id.layc7b);
        rdPreguntac7c = (RadioGroup) findViewById(R.id.rdPreguntac7c);
        capturac7c = res.getString(R.string.PREGUNTAc7c);
        layc7c = (LinearLayout) findViewById(R.id.layc7c);
        rdPreguntac8 = (RadioGroup) findViewById(R.id.rdPreguntac8);
        capturac8 = res.getString(R.string.PREGUNTAc8);
        layc8 = (LinearLayout) findViewById(R.id.layc8);
        rdPreguntac9 = (RadioGroup) findViewById(R.id.rdPreguntac9);
        capturac9 = res.getString(R.string.PREGUNTAc9);
        layc9 = (LinearLayout) findViewById(R.id.layc9);
        rdPreguntac10 = (RadioGroup) findViewById(R.id.rdPreguntac10);
        capturac10 = res.getString(R.string.PREGUNTAc10);
        layc10 = (LinearLayout) findViewById(R.id.layc10);
        rdPreguntac11 = (RadioGroup) findViewById(R.id.rdPreguntac11);
        capturac11 = res.getString(R.string.PREGUNTAc11);
        layc11 = (LinearLayout) findViewById(R.id.layc11);
        rdPreguntac12 = (RadioGroup) findViewById(R.id.rdPreguntac12);
        capturac12 = res.getString(R.string.PREGUNTAc12);
        layc12 = (LinearLayout) findViewById(R.id.layc12);
        rdPreguntac13 = (RadioGroup) findViewById(R.id.rdPreguntac13);
        capturac13 = res.getString(R.string.PREGUNTAc13);
        layc13 = (LinearLayout) findViewById(R.id.layc13);
        rdPreguntac14 = (RadioGroup) findViewById(R.id.rdPreguntac14);
        capturac14 = res.getString(R.string.PREGUNTAc14);
        layc14 = (LinearLayout) findViewById(R.id.layc14);
        rdPreguntac15 = (RadioGroup) findViewById(R.id.rdPreguntac15);
        capturac15 = res.getString(R.string.PREGUNTAc15);
        layc15 = (LinearLayout) findViewById(R.id.layc15);
        rdPreguntac16 = (RadioGroup) findViewById(R.id.rdPreguntac16);
        capturac16 = res.getString(R.string.PREGUNTAc16);
        layc16 = (LinearLayout) findViewById(R.id.layc16);
        rdPreguntac16a = (RadioGroup) findViewById(R.id.rdPreguntac16a);
        capturac16a = res.getString(R.string.PREGUNTAc16a);
        layc16a = (LinearLayout) findViewById(R.id.layc16a);
        rdPreguntac16b = (RadioGroup) findViewById(R.id.rdPreguntac16b);
        capturac16b = res.getString(R.string.PREGUNTAc16b);
        layc16b = (LinearLayout) findViewById(R.id.layc16b);
        rdPreguntac17 = (RadioGroup) findViewById(R.id.rdPreguntac17);
        capturac17 = res.getString(R.string.PREGUNTAc17);
        layc17 = (LinearLayout) findViewById(R.id.layc17);
        editPreguntac10 = (EditText) findViewById(R.id.editPreguntac10);
        spinner8 = (Spinner) findViewById(R.id.spinner8);

        laySocioE = (LinearLayout) findViewById(R.id.laySocioE);
        layEst = (LinearLayout) findViewById(R.id.layEst);
        layAporta = (LinearLayout) findViewById(R.id.layAporta);
        layOcupacion = (LinearLayout) findViewById(R.id.layOcupacion);
        layCuartos = (LinearLayout) findViewById(R.id.layCuartos);
        layCuartosDormir = (LinearLayout) findViewById(R.id.layCuartosDormir);
        layFocos = (LinearLayout) findViewById(R.id.layFocos);
        layBanos = (LinearLayout) findViewById(R.id.layBanos);
        layRegadera = (LinearLayout) findViewById(R.id.layRegadera);
        layEstufa = (LinearLayout) findViewById(R.id.layEstufa);
//layEdad = (LinearLayout) findViewById(R.id.layEdad);
//layTipoPiso = (LinearLayout) findViewById(R.id.layTipoPiso);
        layTipoVivienda = (LinearLayout) findViewById(R.id.layTipoVivienda);
        layGenero = (LinearLayout) findViewById(R.id.layGenero);

        radio_abandono1 = (RadioButton) findViewById(R.id.radio_abandono1);
        radio_abandono2 = (RadioButton) findViewById(R.id.radio_abandono2);
        radio_abandono3 = (RadioButton) findViewById(R.id.radio_abandono3);
        radio_abandono4 = (RadioButton) findViewById(R.id.radio_abandono4);

        // spinnerCalifica =(Spinner) findViewById(R.id.spinnerCalifica);


        rdPreguntaAporta = (RadioGroup) findViewById(R.id.rdPreguntaAporta);
        rdPreguntaAbandono = (RadioGroup) findViewById(R.id.rdPreguntaAbandono);
        rdPreguntaOcupacion = (RadioGroup) findViewById(R.id.rdPreguntaOcupacion);
        rdPreguntaCuantosCoches = (RadioGroup) findViewById(R.id.rdPreguntaCuantosCoches);
        rdPreguntaCuartos = (RadioGroup) findViewById(R.id.rdPreguntaCuartos);
        rdPreguntaCuartosDormir = (RadioGroup) findViewById(R.id.rdPreguntaCuartosDormir);
        rdPreguntaFocos = (RadioGroup) findViewById(R.id.rdPreguntaFocos);
        rdPreguntaBanos = (RadioGroup) findViewById(R.id.rdPreguntaBanos);
        rdPreguntaRegadera = (RadioGroup) findViewById(R.id.rdPreguntaRegadera);
        rdPreguntaEstufa = (RadioGroup) findViewById(R.id.rdPreguntaEstufa);
        rdPreguntaEdad = (RadioGroup) findViewById(R.id.rdPreguntaEdad);
        rdPreguntaGenero = (RadioGroup) findViewById(R.id.rdPreguntaGenero);
        rdPreguntaTipoVivienda = (RadioGroup) findViewById(R.id.rdPreguntaTipoVivienda);
        rdPreguntaTipoPiso = (RadioGroup) findViewById(R.id.rdPreguntaTipoPiso);


        capturaAporta = res.getString(R.string.PREGUNTAAPORTA);
        capturaOcupacion = res.getString(R.string.PREGUNTAOCUPACION);
        capturaCuantosCoches = res.getString(R.string.PREGUNTACUANTOSCOCHES);
        capturaFocos = res.getString(R.string.PREGUNTAFOCOS);
        capturaCuartos = res.getString(R.string.PREGUNTACUARTOS);
        capturaCuartosDormir = res.getString(R.string.PREGUNTACUARTOSDORMIR);
        capturaBanos = res.getString(R.string.PREGUNTABANOS);
        capturaEstufa = res.getString(R.string.PREGUNTAESTUFA);
        //capturaEdad = res.getString(R.string.PREGUNTAEDAD);
        capturaGenero = res.getString(R.string.PREGUNTAGENERO);
        capturaTipoVivienda = res.getString(R.string.PREGUNTA_TIPO_VIVIENDA);
        //capturaTipoPiso = res.getString(R.string.PREGUNTA_TIPO_PISO);


        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnSalir = (Button) findViewById(R.id.btnSalir);
        btnSalir.setEnabled(false);
        btnSalir.setVisibility(View.GONE);


        layCuantosCoches = (LinearLayout) findViewById(R.id.layCuantosCoches);
//layCuantosCoches.setVisibility(View.GONE);

//    checkcseis_1=(CheckBox)findViewById(R.id.checkcseis_1);
//    checkcseis_2=(CheckBox)findViewById(R.id.checkcseis_2);
//    checkcseis_3=(CheckBox)findViewById(R.id.checkcseis_3);
//    checkcseis_4=(CheckBox)findViewById(R.id.checkcseis_4);
//    checkcseis_5=(CheckBox)findViewById(R.id.checkcseis_5);
//    checkcseis_6=(CheckBox)findViewById(R.id.checkcseis_6);
//    checkcseis_7=(CheckBox)findViewById(R.id.checkcseis_7);


//        mChecks = new ArrayList();
//        mSelectedChecks = new ArrayList();
//
//        mChecks.add(checkochoa_1);
//        mChecks.add(checkochoa_2);
//        mChecks.add(checkochoa_3);
//        mChecks.add(checkochoa_4);
//        mChecks.add(checkochoa_5);
//        mChecks.add(checkochoa_6);
//        mChecks.add(checkochoa_7);
//        mChecks.add(checkochoa_8);
//        mChecks.add(checkochoa_9);
//        mChecks.add(checkochoa_10);
//    mChecks.add(checkochoa_11);// este no porque es el que debe limpiar los otros

//Add Click listener
//        for (CheckBox c : mChecks) {
//            c.setOnClickListener((new OnClickListener() {
//                @Override
//                public void onClick(View arg0) {
//                    CheckBox c = (CheckBox) arg0;
//
//
//                    if (mSelectedChecks.contains(c)) {
//                        mSelectedChecks.remove(c);
//                    } else {
//                        if (mSelectedChecks.size() < 3) {
//                            mSelectedChecks.add(c);
//                        } else {
//                            mSelectedChecks.remove(0);
//                            mSelectedChecks.add(c);
//                        }
//                    }
//
//                    drawResults();
//                }
//            }));
//
//
//        }

        CargaSpinnerEscala();

//CargaSpinner9();

//
//lay17 .setVisibility(View.GONE);
//lay18 .setVisibility(View.GONE);
//lay20 .setVisibility(View.GONE);
//
//editPregunta17.clearFocus();



        rdPregunta1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    op1 = "0";
                } else if (checkedId == R.id.radio2) {
                    op1 = "1";
                } else if (checkedId == R.id.radio3) {
                    op1 = "�C�mo se entera Usted de las noticias regularmente?";
                } else if (checkedId == R.id.radio4) {
                    op1 = "Televisi�n ";
                } else if (checkedId == R.id.radio5) {
                    op1 = "Radio";
                } else if (checkedId == R.id.radio6) {
                    op1 = "Peri�dico";
                } else if (checkedId == R.id.radio7) {
                    op1 = "Redes sociales";
                } else if (checkedId == R.id.radio8) {
                    op1 = "Internet";
                } else if (checkedId == R.id.radio9) {
                    op1 = "Otra";
                } else if (checkedId == R.id.radio10) {
                    op1 = "No sabe / No contest�";
                }

            }

        });


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPregunta2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    op2 = "0";
                } else if (checkedId == R.id.radio2) {
                    op2 = "2";
                } else if (checkedId == R.id.radio3) {
                    op2 = "S�lo como informaci�n, NO le voy a solicitar datos o sus cuentas �Usted tiene cuenta personal en Facebook?";
                } else if (checkedId == R.id.radio4) {
                    op2 = "S�";
                } else if (checkedId == R.id.radio5) {
                    op2 = "No";
                } else if (checkedId == R.id.radio6) {
                    op2 = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPregunta3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    op3 = "0";
                } else if (checkedId == R.id.radio2) {
                    op3 = "3";
                } else if (checkedId == R.id.radio3) {
                    op3 = "�Tiene cuenta personal en Twitter?";
                } else if (checkedId == R.id.radio4) {
                    op3 = "S�";
                } else if (checkedId == R.id.radio5) {
                    op3 = "No";
                } else if (checkedId == R.id.radio6) {
                    op3 = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPregunta4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    op4 = "0";
                } else if (checkedId == R.id.radio2) {
                    op4 = "4";
                } else if (checkedId == R.id.radio3) {
                    op4 = "�Usted utiliza WhatsApp?";
                } else if (checkedId == R.id.radio4) {
                    op4 = "S�";
                } else if (checkedId == R.id.radio5) {
                    op4 = "No";
                } else if (checkedId == R.id.radio6) {
                    op4 = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPregunta5.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    op5 = "0";
                } else if (checkedId == R.id.radio2) {
                    op5 = "5";
                } else if (checkedId == R.id.radio3) {
                    op5 = "Con lo que usted sabe �est� de acuerdo, o en desacuerdo con el trabajo realizado hasta ahora por el Presidente de la Rep�blica, Andr�s Manuel L�pez Obrador?";
                } else if (checkedId == R.id.radio4) {
                    op5 = "De acuerdo";
                } else if (checkedId == R.id.radio5) {
                    op5 = "Ni de acuerdo, ni en desacuerdo";
                } else if (checkedId == R.id.radio6) {
                    op5 = "En desacuerdo";
                } else if (checkedId == R.id.radio7) {
                    op5 = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPregunta6.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    op6 = "0";
                } else if (checkedId == R.id.radio2) {
                    op6 = "6";
                } else if (checkedId == R.id.radio3) {
                    op6 = "Con lo que usted sabe, �piensa que el presidente Andr�s Manuel L�pez Obrador tiene las riendas del pa�s en la mano?";
                } else if (checkedId == R.id.radio4) {
                    op6 = "S�";
                } else if (checkedId == R.id.radio5) {
                    op6 = "No";
                } else if (checkedId == R.id.radio6) {
                    op6 = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPregunta7.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    op7 = "0";
                } else if (checkedId == R.id.radio2) {
                    op7 = "7";
                } else if (checkedId == R.id.radio3) {
                    op7 = "Hablando ahora de la Ciudad de M�xico y de su gobierno, con lo que Ud. sabe, �est� de acuerdo o en desacuerdo con el trabajo realizado hasta ahora por la Jefa de Gobierno, Claudia Sheinbaum?";
                } else if (checkedId == R.id.radio4) {
                    op7 = "De acuerdo";
                } else if (checkedId == R.id.radio5) {
                    op7 = "Ni de acuerdo, ni en desacuerdo";
                } else if (checkedId == R.id.radio6) {
                    op7 = "En desacuerdo";
                } else if (checkedId == R.id.radio7) {
                    op7 = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPregunta8.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    op8 = "2";
                    spinner8.setSelection(0);
                } else if (checkedId == R.id.radio2) {
                    op8 = "8";
                    spinner8.setSelection(0);
                } else if (checkedId == R.id.radio3) {
                    op8 = "En una escala del 1 al 10, donde 1 es muy mala y 10 muy buena, �c�mo califica la labor realizada hasta hoy por la Jefa de Gobierno, Claudia Sheinbaum, en la Ciudad de M�xico?";
                    spinner8.setSelection(0);
                } else if (checkedId == R.id.radio4) {
                    op8 = "No sabe / No contest�";
                    spinner8.setSelection(0);
                }

            }

        });


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPregunta9.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    op9 = "0";
                } else if (checkedId == R.id.radio2) {
                    op9 = "9";
                } else if (checkedId == R.id.radio3) {
                    op9 = "En su opini�n, �la Jefa de Gobierno Claudia Sheinbaum tiene mucha, poca o ninguna capacidad para resolver los problemas de la Ciudad de M�xico?";
                } else if (checkedId == R.id.radio4) {
                    op9 = "Mucha";
                } else if (checkedId == R.id.radio5) {
                    op9 = "Poca";
                } else if (checkedId == R.id.radio6) {
                    op9 = "Ninguna";
                } else if (checkedId == R.id.radio7) {
                    op9 = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc1 = "0";
                } else if (checkedId == R.id.radio2) {
                    opc1 = "c1";
                } else if (checkedId == R.id.radio3) {
                    opc1 = "�Se enter� usted de que la Ciudad de M�xico y el Estado de M�xico continuar�n en Sem�foro Rojo hasta el 24 de enero?";
                } else if (checkedId == R.id.radio4) {
                    opc1 = "S�";
                } else if (checkedId == R.id.radio5) {
                    opc1 = "No";
                } else if (checkedId == R.id.radio6) {
                    opc1 = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc2 = "3";
                    spinnerc2.setSelection(0);
                } else if (checkedId == R.id.radio2) {
                    opc2 = "c2";
                    spinnerc2.setSelection(0);
                } else if (checkedId == R.id.radio3) {
                    opc2 = "Usted cree que esta medida es?";
                    spinnerc2.setSelection(0);
                }

            }

        });


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac2a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc2a = "0";
                } else if (checkedId == R.id.radio2) {
                    opc2a = "c2a";
                } else if (checkedId == R.id.radio3) {
                    opc2a = "Usted cree que esta medida es?";
                } else if (checkedId == R.id.radio4) {
                    opc2a = "Adecuada";
                } else if (checkedId == R.id.radio5) {
                    opc2a = "Inadecuada";
                } else if (checkedId == R.id.radio6) {
                    opc2a = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac2b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc2b = "0";
                } else if (checkedId == R.id.radio2) {
                    opc2b = "c2b";
                } else if (checkedId == R.id.radio3) {
                    opc2b = "Usted cree que esta medida es?";
                } else if (checkedId == R.id.radio4) {
                    opc2b = "Oportuna";
                } else if (checkedId == R.id.radio5) {
                    opc2b = "Tard� mucho";
                } else if (checkedId == R.id.radio6) {
                    opc2b = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac2c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc2c = "0";
                } else if (checkedId == R.id.radio2) {
                    opc2c = "c2c";
                } else if (checkedId == R.id.radio3) {
                    opc2c = "Usted cree que esta medida es?";
                } else if (checkedId == R.id.radio4) {
                    opc2c = "Suficiente";
                } else if (checkedId == R.id.radio5) {
                    opc2c = "Insuficiente";
                } else if (checkedId == R.id.radio6) {
                    opc2c = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc3 = "0";
                } else if (checkedId == R.id.radio2) {
                    opc3 = "c3";
                } else if (checkedId == R.id.radio3) {
                    opc3 = "�Usted esta de acuerdo o en desacuerdo que se contin�e el Sem�foro rojo en la Ciudad de M�xico? (no se lee en parte)";
                } else if (checkedId == R.id.radio4) {
                    opc3 = "Acuerdo";
                } else if (checkedId == R.id.radio5) {
                    opc3 = "Desacuerdo";
                } else if (checkedId == R.id.radio6) {
                    opc3 = "En parte";
                } else if (checkedId == R.id.radio7) {
                    opc3 = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc4 = "0";
                } else if (checkedId == R.id.radio2) {
                    opc4 = "c4";
                } else if (checkedId == R.id.radio3) {
                    opc4 = "�A usted y su familia le afecta directamente el sem�foro rojo?";
                } else if (checkedId == R.id.radio4) {
                    opc4 = "Si";
                } else if (checkedId == R.id.radio5) {
                    opc4 = "No";
                } else if (checkedId == R.id.radio6) {
                    opc4 = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac4a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc4a = "0";
                } else if (checkedId == R.id.radio2) {
                    opc4a = "c4a";
                } else if (checkedId == R.id.radio3) {
                    opc4a = "�En qu�? (registrar primera actividad/ situaci�n que le afecta)";
                } else if (checkedId == R.id.radio4) {
                    opc4a = "Registrar";
                } else if (checkedId == R.id.radio5) {
                    opc4a = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac5.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc5 = "0";
                } else if (checkedId == R.id.radio2) {
                    opc5 = "c5";
                } else if (checkedId == R.id.radio3) {
                    opc5 = "�Usted cree que hay personas infectadas de COVID-19 cerca de su entorno o por los lugares donde se mueve?";
                } else if (checkedId == R.id.radio4) {
                    opc5 = "Si";
                } else if (checkedId == R.id.radio5) {
                    opc5 = "No";
                } else if (checkedId == R.id.radio6) {
                    opc5 = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac6.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc6 = "0";
                } else if (checkedId == R.id.radio2) {
                    opc6 = "c6";
                } else if (checkedId == R.id.radio3) {
                    opc6 = "�Qu� tanto miedo tiene de contagiarse de COVID-19  usted o su familia?";
                } else if (checkedId == R.id.radio4) {
                    opc6 = "Mucho";
                } else if (checkedId == R.id.radio5) {
                    opc6 = "Algo";
                } else if (checkedId == R.id.radio6) {
                    opc6 = "Poco";
                } else if (checkedId == R.id.radio7) {
                    opc6 = "Nada";
                } else if (checkedId == R.id.radio8) {
                    opc6 = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac7.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc7 = "3";
                    spinnerc7.setSelection(0);
                } else if (checkedId == R.id.radio2) {
                    opc7 = "c7";
                    spinnerc7.setSelection(0);
                } else if (checkedId == R.id.radio3) {
                    opc7 = "Por favor digame, si tuviera la posibilidad de ponerse la vacuna contra COVID-19 usted...";
                    spinnerc7.setSelection(0);
                }

            }

        });


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac7a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc7a = "0";
                } else if (checkedId == R.id.radio2) {
                    opc7a = "c7a";
                } else if (checkedId == R.id.radio3) {
                    opc7a = "Por favor digame, si tuviera la posibilidad de ponerse la vacuna contra COVID-19 usted...";
                } else if (checkedId == R.id.radio4) {
                    opc7a = "Se la pondr�a de inmediato o";
                } else if (checkedId == R.id.radio5) {
                    opc7a = "Esperar�a un tiempo para ver como reaccionan otras personas";
                } else if (checkedId == R.id.radio6) {
                    opc7a = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac7b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc7b = "0";
                } else if (checkedId == R.id.radio2) {
                    opc7b = "c7b";
                } else if (checkedId == R.id.radio3) {
                    opc7b = "Por favor digame, si tuviera la posibilidad de ponerse la vacuna contra COVID-19 usted...";
                } else if (checkedId == R.id.radio4) {
                    opc7b = "Se pondr�a la que aplique el sector salud ";
                } else if (checkedId == R.id.radio5) {
                    opc7b = "Preferir�a aplicarla con un m�dico particular";
                } else if (checkedId == R.id.radio6) {
                    opc7b = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac7c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc7c = "0";
                } else if (checkedId == R.id.radio2) {
                    opc7c = "c7c";
                } else if (checkedId == R.id.radio3) {
                    opc7c = "Por favor digame, si tuviera la posibilidad de ponerse la vacuna contra COVID-19 usted...";
                } else if (checkedId == R.id.radio4) {
                    opc7c = "Se vacunar�a usted y su familia";
                } else if (checkedId == R.id.radio5) {
                    opc7c = "No piensa vacunarse";
                } else if (checkedId == R.id.radio6) {
                    opc7c = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac8.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc8 = "0";
                } else if (checkedId == R.id.radio2) {
                    opc8 = "c8";
                } else if (checkedId == R.id.radio3) {
                    opc8 = "En los �ltimos seis meses, �ha visitado su hogar personal del Gobierno de la Ciudad para informarle a usted o alg�n familiar sobre medidas de prevenci�n y cuidado durante la pandemia de Coronavirus?";
                } else if (checkedId == R.id.radio4) {
                    opc8 = "Si";
                } else if (checkedId == R.id.radio5) {
                    opc8 = "No";
                } else if (checkedId == R.id.radio6) {
                    opc8 = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac9.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc9 = "0";
                } else if (checkedId == R.id.radio2) {
                    opc9 = "c9";
                } else if (checkedId == R.id.radio3) {
                    opc9 = "Estar�a usted de acuerdo o en desacuerdo que el Sem�foro rojo en la Ciudad de M�xico continuara hasta finales de enero? (no se lee en parte)";
                } else if (checkedId == R.id.radio4) {
                    opc9 = "Acuerdo";
                } else if (checkedId == R.id.radio5) {
                    opc9 = "Desacuerdo";
                } else if (checkedId == R.id.radio6) {
                    opc9 = "En parte";
                } else if (checkedId == R.id.radio7) {
                    opc9 = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac10.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc10 = "1";
                    editPreguntac10.setText("");
                } else if (checkedId == R.id.radio2) {
                    opc10 = "c10";
                    editPreguntac10.setText("");
                } else if (checkedId == R.id.radio3) {
                    opc10 = "�A usted y a su familia en que les afectar�a que el sem�foro rojo contin�e hasta finales de enero?";
                    editPreguntac10.setText("");
                } else if (checkedId == R.id.radio4) {
                    opc10 = "Registrar";
                    editPreguntac10.setText("");
                } else if (checkedId == R.id.radio5) {
                    opc10 = "En nada/ no me afectar�a";
                    editPreguntac10.setText("");
                } else if (checkedId == R.id.radio6) {
                    opc10 = "No sabe / No contest�";
                    editPreguntac10.setText("");
                }

            }
        });


        editPreguntac10.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });


        editPreguntac10.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    rdPreguntac10.clearCheck();
                }
            }
        });


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac11.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc11 = "0";
                } else if (checkedId == R.id.radio2) {
                    opc11 = "c11";
                } else if (checkedId == R.id.radio3) {
                    opc11 = "Con lo que usted sabe o ha escuchado �usted cree que la Jefa de Gobierno deber�a de extender el Sem�foro rojo hasta finales de enero?";
                } else if (checkedId == R.id.radio4) {
                    opc11 = "Si";
                } else if (checkedId == R.id.radio5) {
                    opc11 = "No";
                } else if (checkedId == R.id.radio6) {
                    opc11 = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac12.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc12 = "0";
                } else if (checkedId == R.id.radio2) {
                    opc12 = "c12";
                } else if (checkedId == R.id.radio3) {
                    opc12 = "En los �ltimos seis meses �Usted ha visto en su colonia tr�pticos, folletos o carteles del Gobierno de la Ciudad para informar sobre medidas de prevenci�n y cuidado durante la pandemia de Coronavirus?";
                } else if (checkedId == R.id.radio4) {
                    opc12 = "Si";
                } else if (checkedId == R.id.radio5) {
                    opc12 = "No";
                } else if (checkedId == R.id.radio6) {
                    opc12 = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac13.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc13 = "0";
                } else if (checkedId == R.id.radio2) {
                    opc13 = "c13";
                } else if (checkedId == R.id.radio3) {
                    opc13 = "Antes de que yo se lo mencionara, �usted sab�a que ya se permite que haya reelecci�n de cargos populares?";
                } else if (checkedId == R.id.radio4) {
                    opc13 = "Si sab�a";
                } else if (checkedId == R.id.radio5) {
                    opc13 = "No sabia";
                } else if (checkedId == R.id.radio6) {
                    opc13 = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac14.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc14 = "0";
                } else if (checkedId == R.id.radio2) {
                    opc14 = "c14";
                } else if (checkedId == R.id.radio3) {
                    opc14 = "En general �est� usted de acuerdo o no con la reelecci�n de alcaldes y diputados? (No leer ni de acuerdo ni en desacuerdo)";
                } else if (checkedId == R.id.radio4) {
                    opc14 = "Acuerdo";
                } else if (checkedId == R.id.radio5) {
                    opc14 = "Ni de acuerdo ni en desacuerdo";
                } else if (checkedId == R.id.radio6) {
                    opc14 = "Desacuerdo";
                } else if (checkedId == R.id.radio7) {
                    opc14 = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac15.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc15 = "0";
                } else if (checkedId == R.id.radio2) {
                    opc15 = "c15";
                } else if (checkedId == R.id.radio3) {
                    opc15 = "�Usted votar�a por alg�n candidato que buscara la reelecci�n para alcalde o diputado: Si, sin importar el candidato, Si, dependiendo del candidato, No, nunca votar�a para que alguien se reeligiera?";
                } else if (checkedId == R.id.radio4) {
                    opc15 = "Si, sin importar el candidato";
                } else if (checkedId == R.id.radio5) {
                    opc15 = "Depende del candidato";
                } else if (checkedId == R.id.radio6) {
                    opc15 = "Nunca votar�a para que alguien se reeligiera";
                } else if (checkedId == R.id.radio7) {
                    opc15 = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac16.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc16 = "0";
                } else if (checkedId == R.id.radio2) {
                    opc16 = "c16";
                } else if (checkedId == R.id.radio3) {
                    opc16 = "Solo para estad�stica, �me podr�a decir por qui�n vot� para Jefe de Gobierno en las elecciones de 2018? Espont�neo";
                } else if (checkedId == R.id.radio4) {
                    opc16 = "Claudia Sheinbaum/ Morena/ PT/ Encuentro social";
                } else if (checkedId == R.id.radio5) {
                    opc16 = "Alejandra Barrales/ PAN/ PRD/  MC";
                } else if (checkedId == R.id.radio6) {
                    opc16 = "Mikel Arriola/ PRI";
                } else if (checkedId == R.id.radio7) {
                    opc16 = "Mariana Boy/ PVEM";
                } else if (checkedId == R.id.radio8) {
                    opc16 = "Lorena Osornio/ Independiente";
                } else if (checkedId == R.id.radio9) {
                    opc16 = "Marco Rasc�n/ PHCDMX";
                } else if (checkedId == R.id.radio10) {
                    opc16 = "Purificaci�n Carpinteyro/ PANAL";
                } else if (checkedId == R.id.radio11) {
                    opc16 = "No voto";
                } else if (checkedId == R.id.radio12) {
                    opc16 = "Anul� su voto";
                } else if (checkedId == R.id.radio13) {
                    opc16 = "No sabe/ no contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac16a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc16a = "0";
                } else if (checkedId == R.id.radio2) {
                    opc16a = "c16a";
                } else if (checkedId == R.id.radio3) {
                    opc16a = "A dos a�os de la elecci�n �sigue pensando que fue la mejor elecci�n para la Jefatura de Gobierno? (espont�neo)";
                } else if (checkedId == R.id.radio4) {
                    opc16a = "Si, fue la mejor";
                } else if (checkedId == R.id.radio5) {
                    opc16a = "En parte";
                } else if (checkedId == R.id.radio6) {
                    opc16a = "No fue la mejor";
                } else if (checkedId == R.id.radio7) {
                    opc16a = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac16b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc16b = "0";
                } else if (checkedId == R.id.radio2) {
                    opc16b = "c16b";
                } else if (checkedId == R.id.radio3) {
                    opc16b = "Despu�s de dos a�os de la elecci�n  �c�mo cree que est� haciendo su trabajo la Jefa de Gobierno, Claudia Sheinbaum, bien o mal? (no leer regular)";
                } else if (checkedId == R.id.radio4) {
                    opc16b = "Bien";
                } else if (checkedId == R.id.radio5) {
                    opc16b = "Regular";
                } else if (checkedId == R.id.radio6) {
                    opc16b = "Mal";
                } else if (checkedId == R.id.radio7) {
                    opc16b = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac17.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc17 = "0";
                } else if (checkedId == R.id.radio2) {
                    opc17 = "c17";
                } else if (checkedId == R.id.radio3) {
                    opc17 = "Si hoy fueran las elecciones para elegir alcaldes y diputados �por qu� partido votar�a usted? (espont�neo)";
                } else if (checkedId == R.id.radio4) {
                    opc17 = "Morena";
                } else if (checkedId == R.id.radio5) {
                    opc17 = "PRI";
                } else if (checkedId == R.id.radio6) {
                    opc17 = "PAN";
                } else if (checkedId == R.id.radio7) {
                    opc17 = "PRD";
                } else if (checkedId == R.id.radio8) {
                    opc17 = "PT";
                } else if (checkedId == R.id.radio9) {
                    opc17 = "PVEM";
                } else if (checkedId == R.id.radio10) {
                    opc17 = "Movimiento Ciudadano";
                } else if (checkedId == R.id.radio11) {
                    opc17 = "Candidato Independiente";
                } else if (checkedId == R.id.radio12) {
                    opc17 = "Otro";
                } else if (checkedId == R.id.radio13) {
                    opc17 = "No votar�a";
                } else if (checkedId == R.id.radio14) {
                    opc17 = "Anular�a su voto";
                } else if (checkedId == R.id.radio0) {
                    opc17 = "No sabe / No contest�";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////// SOCIOECONOMICOS  /////////////////////////////////////

        rdPreguntaAporta.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opAporta = "No complet� primaria";
                } else if (checkedId == R.id.radio2) {
                    opAporta = "Primaria o secundaria";
                } else if (checkedId == R.id.radio3) {
                    opAporta = "Preparatoria o carrera t�cnica";
                } else if (checkedId == R.id.radio4) {
                    opAporta = "Licenciatura";
                } else if (checkedId == R.id.radio5) {
                    opAporta = "Posgrado";
                } else if (checkedId == R.id.radio0) {
                    opAporta = "No contest�";
                } else {
                    opAporta = "";

                }

            }
        });

        rdPreguntaOcupacion.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opOcupacion = "Hogar";
                } else if (checkedId == R.id.radio2) {
                    opOcupacion = "Estudiante";
                } else if (checkedId == R.id.radio3) {
                    opOcupacion = "Profesionista";
                } else if (checkedId == R.id.radio4) {
                    opOcupacion = "Empleado";
                } else if (checkedId == R.id.radio5) {
                    opOcupacion = "Obrero / oficio";
                } else if (checkedId == R.id.radio6) {
                    opOcupacion = "Comerciante";
                } else if (checkedId == R.id.radio7) {
                    opOcupacion = "Jubilado";
                } else if (checkedId == R.id.radio8) {
                    opOcupacion = "Otro";
                } else if (checkedId == R.id.radio9) {
                    opOcupacion = "Desempleado";
                } else if (checkedId == R.id.radio0) {
                    opOcupacion = "No contest�";
                } else {
                    opOcupacion = "";

                }

            }
        });


        rdPreguntaCuantosCoches.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opCuantosCoches = "Ninguno";
                } else if (checkedId == R.id.radio2) {
                    opCuantosCoches = "Uno";
                } else if (checkedId == R.id.radio3) {
                    opCuantosCoches = "Dos";
                } else if (checkedId == R.id.radio4) {
                    opCuantosCoches = "Tres";
                } else if (checkedId == R.id.radio5) {
                    opCuantosCoches = "Cuatro o m�s";
                } else if (checkedId == R.id.radio0) {
                    opCuantosCoches = "No contest�";
                } else {
                    opCuantosCoches = "";

                }

            }
        });

        rdPreguntaCuartos.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opCuartos = "1";
                } else if (checkedId == R.id.radio2) {
                    opCuartos = "2";
                } else if (checkedId == R.id.radio3) {
                    opCuartos = "3";
                } else if (checkedId == R.id.radio4) {
                    opCuartos = "4";
                } else if (checkedId == R.id.radio5) {
                    opCuartos = "5";
                } else if (checkedId == R.id.radio6) {
                    opCuartos = "6";
                } else if (checkedId == R.id.radio7) {
                    opCuartos = "7";
                } else if (checkedId == R.id.radio8) {
                    opCuartos = "8";
                } else if (checkedId == R.id.radio9) {
                    opCuartos = "9";
                } else if (checkedId == R.id.radio10) {
                    opCuartos = "10";
                } else if (checkedId == R.id.radio0) {
                    opCuartos = "No contest�";
                } else {
                    opCuartos = "";

                }

            }
        });

        rdPreguntaCuartosDormir.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opCuartosDormir = "1";
                } else if (checkedId == R.id.radio2) {
                    opCuartosDormir = "2";
                } else if (checkedId == R.id.radio3) {
                    opCuartosDormir = "3";
                } else if (checkedId == R.id.radio4) {
                    opCuartosDormir = "4";
                } else if (checkedId == R.id.radio5) {
                    opCuartosDormir = "5";
                } else if (checkedId == R.id.radio6) {
                    opCuartosDormir = "6";
                } else if (checkedId == R.id.radio7) {
                    opCuartosDormir = "7";
                } else if (checkedId == R.id.radio8) {
                    opCuartosDormir = "8";
                } else if (checkedId == R.id.radio9) {
                    opCuartosDormir = "9";
                } else if (checkedId == R.id.radio10) {
                    opCuartosDormir = "10";
                } else if (checkedId == R.id.radio0) {
                    opCuartosDormir = "No contest�";
                } else {
                    opCuartosDormir = "";

                }

            }
        });

        rdPreguntaFocos.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opFocos = "0 a 5";
                } else if (checkedId == R.id.radio2) {
                    opFocos = "6 a 10";
                } else if (checkedId == R.id.radio3) {
                    opFocos = "11 a 15";
                } else if (checkedId == R.id.radio4) {
                    opFocos = "16 a 20";
                } else if (checkedId == R.id.radio5) {
                    opFocos = "21 o m�s";
                } else if (checkedId == R.id.radio0) {
                    opFocos = "No contest�";
                } else {
                    opFocos = "";

                }

            }
        });

        rdPreguntaBanos.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opBanos = "Ninguno";
                } else if (checkedId == R.id.radio2) {
                    opBanos = "Uno";
                } else if (checkedId == R.id.radio3) {
                    opBanos = "Dos o Tres";
                } else if (checkedId == R.id.radio4) {
                    opBanos = "Cuatro o m�s";
                } else if (checkedId == R.id.radio0) {
                    opBanos = "No contest�";
                } else {
                    opBanos = "";

                }

            }
        });

        rdPreguntaRegadera.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opRegadera = "Si";

                } else if (checkedId == R.id.radio2) {
                    opRegadera = "No";
                } else if (checkedId == R.id.radio0) {
                    opRegadera = "No contest�";
                } else {
                    opRegadera = "";

                }

            }
        });

        rdPreguntaEstufa.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opEstufa = "Si";

                } else if (checkedId == R.id.radio2) {
                    opEstufa = "No";
                } else if (checkedId == R.id.radio0) {
                    opEstufa = "No contest�";
                } else {
                    opEstufa = "";

                }

            }
        });

        rdPreguntaEdad.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opEdad = "Menor de 18 a�os";
                } else if (checkedId == R.id.radio2) {
                    opEdad = "18 a 29";
                } else if (checkedId == R.id.radio3) {
                    opEdad = "30 a 39";
                } else if (checkedId == R.id.radio4) {
                    opEdad = "40 a 49";
                } else if (checkedId == R.id.radio5) {
                    opEdad = "50 a 59";
                } else if (checkedId == R.id.radio6) {
                    opEdad = "60 a 69";
                } else if (checkedId == R.id.radio7) {
                    opEdad = "70 o m�s";
                } else if (checkedId == R.id.radio0) {
                    opEdad = "No contest�";
                } else {
                    opEdad = "";

                }

            }
        });

        rdPreguntaGenero.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opGenero = "Masculino";


                } else if (checkedId == R.id.radio2) {
                    opGenero = "Femenino";


                }

            }
        });

        rdPreguntaTipoVivienda.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opTipoVivienda = "Casa";
                } else if (checkedId == R.id.radio2) {
                    opTipoVivienda = "Condominio";
                } else if (checkedId == R.id.radio0) {
                    opTipoVivienda = "No contest�";
                } else {
                    opTipoVivienda = "";

                }

            }
        });
//
        rdPreguntaTipoPiso.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opTipoPiso = "Tierra o cemento";
                } else if (checkedId == R.id.radio2) {
                    opTipoPiso = "Cualquier otro";
                } else if (checkedId == R.id.radio0) {
                    opTipoPiso = "No contest�";
                } else {
                    opTipoPiso = "";

                }

            }
        });

        rdPreguntaAbandono.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio_abandono1) {
                    opAbandono = "1";
                    tipoEncuesta = "NORMAL";
                    btnGuardar.setText("Guardar Normal");
                } else if (checkedId == R.id.radio_abandono2) {
                    opAbandono = "2";
                    tipoEncuesta = "ABANDONO";
                    btnGuardar.setText("Guardar Abandono");
                } else if (checkedId == R.id.radio_abandono3) {
                    opAbandono = "3";
                    tipoEncuesta = "RECHAZO";
                    btnGuardar.setText("Guardar Rechazo");
                } else if (checkedId == R.id.radio_abandono4) {
                    opAbandono = "4";
                    tipoEncuesta = "FILTRO";
                    btnGuardar.setText("Guardar Filtro");
                }


            }
        });

    }

////// FIN ONCREATE/////////////////////////////


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    class CierraEncuesta extends TimerTask {

        @Override
        public void run() {

            dialogoCierraEncuesta();

        }

    }

    public void drawResults() {
        for (CheckBox c : mChecks) {
            c.setChecked(mSelectedChecks.contains(c));
        }
    }

    public void drawResults2() {
        for (CheckBox d : mChecks2) {
            d.setChecked(mSelectedChecks2.contains(d));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void valores() {

        String str = "";

        String seg = formattedDate5.substring(7);
        System.out.println("El segundo: " + seg);
        System.out.println("El IMEI" + sacaImei());

        String mes = formattedDate6.toString();
        System.out.println("El mes" + mes);

        String dia = formattedDate7.toString();
        System.out.println("El dia" + dia);

        sacaChip();

        cachaNombre();

        txtSeccion.setText(cachaSeccion());

        String strSecc = txtSeccion.getText().toString();
        String strId = String.valueOf(rand + 1);


        elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;
        elMaximoConsecutivo = Integer.parseInt(sacaMaximoConsecutivo().toString()) + 1;


        String strText8;
        if (spinner8.getSelectedItem().toString().equals("Selecciona")) {
            strText8 = op8;
        } else {
            strText8 = spinner8.getSelectedItem().toString();
            rdPregunta8.clearCheck();
        }

        String strTextc10;
        if (editPreguntac10.getText().toString().trim().length() == 0) {
            strTextc10 = opc10;
        } else {
            strTextc10 = editPreguntac10.getText().toString().trim();
            rdPreguntac10.clearCheck();
        }


        String str1 = op1;
        String str2 = op2;
        String str3 = op3;
        String str4 = op4;
        String str5 = op5;
        String str6 = op6;
        String str7 = op7;
        String str8 = strText8;
        String str9 = op9;
        String strc1 = opc1;
        String strc2 = "pregunta";
        String strc2a = opc2a;
        String strc2b = opc2b;
        String strc2c = opc2c;
        String strc3 = opc3;
        String strc4 = opc4;
        String strc4a = opc4a;
        String strc5 = opc5;
        String strc6 = opc6;
        String strc7 = "pregunta";
        String strc7a = opc7a;
        String strc7b = opc7b;
        String strc7c = opc7c;
        String strc8 = opc8;
        String strc9 = opc9;
        String strc10 = strTextc10;
        String strc11 = opc11;
        String strc12 = opc12;
        String strc13 = opc13;
        String strc14 = opc14;
        String strc15 = opc15;
        String strc16 = opc16;
        String strc16a = opc16a;
        String strc16b = opc16b;
        String strc17 = opc17;

        String strAporta = opAporta;
        String strOcupacion = opOcupacion;
        String strCuantosCoches = opCuantosCoches;
        String strCuartos = opCuartos;
        String strCuartosDormir = opCuartosDormir;
        String strFocos = opFocos;
        String strBanos = opBanos;
        String strRegadera = opRegadera;
        String strEstufa = opEstufa;
        String strEdad = opEdad;
        String strGenero = opGenero;
        String strTipoVivienda = opTipoVivienda;
        String strTipoPiso = opTipoPiso;
        String strAbandono = opAbandono;

        if (strAbandono.matches("1")) {
            tipoEncuesta = "NORMAL";
        }

        String strEstudios = opAporta;
        String strCocheCuantos = opCuantosCoches;
        String strFoco = opFocos;
        String strCuarto = opCuartos;
        String strBano = opBanos;
        String strRega = opRegadera;
        String strEstu = opEstufa;
        String strPiso = opTipoPiso;

// estudios
        if (strEstudios.matches("sin datos")) {
            strEstudios = "0";
        } else if (strEstudios.matches("No complet� primaria")) {
            strEstudios = "0";
        } else if (strEstudios.matches("Primaria o secundaria")) {
            strEstudios = "22";
        } else if (strEstudios.matches("Preparatoria o carrera t�cnica")) {
            strEstudios = "38";
        } else if (strEstudios.matches("Licenciatura")) {
            strEstudios = "52";
        } else if (strEstudios.matches("Posgrado")) {
            strEstudios = "72";
        } else if (strEstudios.matches("No contest�")) {
            strEstudios = "0";
        } else if (strEstudios.matches("No aplica")) {
            strEstudios = "0";
        }
// coches
        if (strCocheCuantos.matches("sin datos")) {
            strCocheCuantos = "0";
        } else if (strCocheCuantos.matches("Ninguno")) {
            strCocheCuantos = "0";
        } else if (strCocheCuantos.matches("Uno")) {
            strCocheCuantos = "32";
        } else if (strCocheCuantos.matches("Dos")) {
            strCocheCuantos = "41";
        } else if (strCocheCuantos.matches("Tres")) {
            strCocheCuantos = "58";
        } else if (strCocheCuantos.matches("Cuatro o m�s")) {
            strCocheCuantos = "58";
        } else if (strCocheCuantos.matches("No aplica")) {
            strCocheCuantos = "0";
        } else if (strCocheCuantos.matches("No contest�")) {
            strCocheCuantos = "0";
        } else if (strCocheCuantos.matches("No aplica")) {
            strCocheCuantos = "0";
        }
// Focos
        if (strFoco.matches("sin datos")) {
            strFoco = "0";
        } else if (strFoco.matches("0 a 5")) {
            strFoco = "0";
        } else if (strFoco.matches("6 a 10")) {
            strFoco = "15";
        } else if (strFoco.matches("11 a 15")) {
            strFoco = "27";
        } else if (strFoco.matches("16 a 20")) {
            strFoco = "32";
        } else if (strFoco.matches("21 o m�s")) {
            strFoco = "46";
        } else if (strFoco.matches("No contest�")) {
            strFoco = "0";
        } else if (strFoco.matches("No aplica")) {
            strFoco = "0";
        }
// Cuartos
        if (strCuarto.matches("sin datos")) {
            strCuarto = "0";
        } else if (strCuarto.matches("1")) {
            strCuarto = "0";
        } else if (strCuarto.matches("2")) {
            strCuarto = "0";
        } else if (strCuarto.matches("3")) {
            strCuarto = "0";
        } else if (strCuarto.matches("4")) {
            strCuarto = "0";
        } else if (strCuarto.matches("5")) {
            strCuarto = "8";
        } else if (strCuarto.matches("6")) {
            strCuarto = "8";
        } else if (strCuarto.matches("7")) {
            strCuarto = "14";
        } else if (strCuarto.matches("8")) {
            strCuarto = "14";
        } else if (strCuarto.matches("9")) {
            strCuarto = "14";
        } else if (strCuarto.matches("10")) {
            strCuarto = "14";
        } else if (strCuarto.matches("No contest�")) {
            strCuarto = "0";
        } else if (strCuarto.matches("No aplica")) {
            strCuarto = "0";
        }
// Banos
        if (strBano.matches("sin datos")) {
            strBano = "0";
        } else if (strBano.matches("Ninguno")) {
            strBano = "0";
        } else if (strBano.matches("Uno")) {
            strBano = "13";
        } else if (strBano.matches("Dos o Tres")) {
            strBano = "31";
        } else if (strBano.matches("Cuatro o m�s")) {
            strBano = "48";
        } else if (strBano.matches("No contest�")) {
            strBano = "0";
        } else if (strBano.matches("No aplica")) {
            strBano = "0";
        }
// Regadera
        if (strRega.matches("sin datos")) {
            strRega = "0";
        } else if (strRega.matches("Si")) {
            strRega = "10";
        } else if (strRega.matches("No")) {
            strRega = "0";
        } else if (strRega.matches("No contest�")) {
            strRega = "0";
        } else if (strRega.matches("No aplica")) {
            strRega = "0";
        }
// Estufa
        if (strEstu.matches("sin datos")) {
            strEstu = "0";
        } else if (strEstu.matches("Si")) {
            strEstu = "20";
        } else if (strEstu.matches("No")) {
            strEstu = "0";
        } else if (strEstu.matches("No contest�")) {
            strEstu = "0";
        } else if (strEstu.matches("No aplica")) {
            strEstu = "0";
        }
// Piso
        if (strPiso.matches("sin datos")) {
            strPiso = "0";
        } else if (strPiso.matches("Tierra o cemento")) {
            strPiso = "0";
        } else if (strPiso.matches("Cualquier otro")) {
            strPiso = "11";
        } else if (strPiso.matches("No contest�")) {
            strPiso = "0";
        } else if (strPiso.matches("No aplica")) {
            strPiso = "0";
        } else if (strPiso.matches("No sabe / No contest�")) {
            strPiso = "0";
        }

        Integer estudios = Integer.valueOf(strEstudios);
        Integer coches = Integer.valueOf(strCocheCuantos);
        Integer focos = Integer.valueOf(strFoco);
        Integer cuartos = Integer.valueOf(strCuarto);
        Integer banos = Integer.valueOf(strBano);
        Integer regadera = Integer.valueOf(strRega);
        Integer estufa = Integer.valueOf(strEstu);
        Integer piso = Integer.valueOf(strPiso);

        Integer suma = (estudios + coches + focos + cuartos + banos + regadera + estufa + piso);

        String status = null;

        if (suma >= 0 && suma <= 32) {
            status = "E";
        } else if (suma >= 33 && suma <= 79) {
            status = "D";
        } else if (suma >= 80 && suma <= 104) {
            status = "D+";
        } else if (suma >= 105 && suma <= 127) {
            status = "C-";
        } else if (suma >= 128 && suma <= 154) {
            status = "C";
        } else if (suma >= 155 && suma <= 192) {
            status = "C+";
        } else if (suma >= 193) {
            status = "AB";
        }

        String strFinal = "\n";

// Clase que permite grabar texto en un archivo
        FileOutputStream fout = null;
        try {
// INSERTA EN LA BASE DE DATOS //

            final String F = "File dbfile";

            String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_"
                    + sacaImei() + "";

// Abrimos la base de datos 'DBUsuarios\ en modo escritura
            usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

            db = usdbh.getWritableDatabase();

// NORMAL
            Nombre nom = new Nombre();
            String nombreE = nom.nombreEncuesta();

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
            long consecutivoObtenido = elMaximoConsecutivo;
            ContentValues values = new ContentValues();
            if (db != null) {

                values.put("consecutivo_diario", elMaximo);
                values.put("equipo", cachaEquipo().toUpperCase());
                values.put("usuario", cachaNombre().toUpperCase());
                values.put("nombre_encuesta", nombreE.toUpperCase());
                values.put("fecha", formattedDate1);
                values.put("hora", formattedDate5);
                values.put("imei", sacaImei());
                values.put("seccion", strSecc);
                values.put("latitud", strLatitud);
                values.put("longitud", strLongitud);
                values.put("pregunta_1", str1);
                values.put("pregunta_2", str2);
                values.put("pregunta_3", str3);
                values.put("pregunta_4", str4);
                values.put("pregunta_5", str5);
                values.put("pregunta_6", str6);
                values.put("pregunta_7", str7);
                values.put("pregunta_8", str8);
                values.put("pregunta_9", str9);
                values.put("pregunta_c1", strc1);
                values.put("pregunta_c2", strc2);
                values.put("pregunta_c2a", strc2a);
                values.put("pregunta_c2b", strc2b);
                values.put("pregunta_c2c", strc2c);
                values.put("pregunta_c3", strc3);
                values.put("pregunta_c4", strc4);
                values.put("pregunta_c4a", strc4a);
                values.put("pregunta_c5", strc5);
                values.put("pregunta_c6", strc6);
                values.put("pregunta_c7", strc7);
                values.put("pregunta_c7a", strc7a);
                values.put("pregunta_c7b", strc7b);
                values.put("pregunta_c7c", strc7c);
                values.put("pregunta_c8", strc8);
                values.put("pregunta_c9", strc9);
                values.put("pregunta_c10", strc10);
                values.put("pregunta_c11", strc11);
                values.put("pregunta_c12", strc12);
                values.put("pregunta_c13", strc13);
                values.put("pregunta_c14", strc14);
                values.put("pregunta_c15", strc15);
                values.put("pregunta_c16", strc16);
                values.put("pregunta_c16a", strc16a);
                values.put("pregunta_c16b", strc16b);
                values.put("pregunta_c17", strc17);
                values.put("aporta", strAporta);
                values.put("ocupacion", strOcupacion);
                values.put("cuantos_coches", strCuantosCoches);
                values.put("cuartos", strCuartos);
                values.put("cuartos_dormir", strCuartosDormir);
                values.put("focos", strFocos);
                values.put("banos", strBanos);
                values.put("regadera", strRegadera);
                values.put("estufa", strEstufa);
                values.put("edad", strEdad);
                values.put("genero", strGenero);
                values.put("tipo_vivienda", strTipoVivienda);
                values.put("tipo_piso", strTipoPiso);

                values.put("abandono", strAbandono.toUpperCase());

                values.put("suma", suma);
                values.put("status", status);

                values.put("tiempo", elTiempo());
                values.put("tipo_captura", tipoEncuesta);

                if (!verificaConexion(this)) {
                    Toast.makeText(getBaseContext(), "Sin conexi�n", Toast.LENGTH_LONG).show();
                    values.put("enviado", "0");
                    db.insert("encuestas", null, values);
                } else {
                    values.put("enviado", "1");
                    consecutivoObtenido = db.insert("encuestas", null, values);
                }
            }
            db.close();

            values.put("consecutivo", consecutivoObtenido);

            guardaEncuestaWS(values);


            System.out.println("consecutivo_diario " + elMaximo);
            System.out.println("usuario " + cachaNombre().toUpperCase());
            System.out.println("nombre_encuesta " + nombreE.toUpperCase());
            System.out.println("fecha " + formattedDate1);
            System.out.println("hora " + formattedDate5);
            System.out.println("imei " + sacaImei());
            System.out.println("Seccion " + str);
            System.out.println("Latitud  " + strLatitud);
            System.out.println("Longitud  " + strLongitud);
            System.out.println("pregunta_1  " + str1);
            System.out.println("pregunta_2  " + str2);
            System.out.println("pregunta_3  " + str3);
            System.out.println("pregunta_4  " + str4);
            System.out.println("pregunta_5  " + str5);
            System.out.println("pregunta_6  " + str6);
            System.out.println("pregunta_7  " + str7);
            System.out.println("pregunta_8  " + str8);
            System.out.println("pregunta_9  " + str9);
            System.out.println("pregunta_c1  " + strc1);
            System.out.println("pregunta_c2  " + strc2);
            System.out.println("pregunta_c2a  " + strc2a);
            System.out.println("pregunta_c2b  " + strc2b);
            System.out.println("pregunta_c2c  " + strc2c);
            System.out.println("pregunta_c3  " + strc3);
            System.out.println("pregunta_c4  " + strc4);
            System.out.println("pregunta_c4a  " + strc4a);
            System.out.println("pregunta_c5  " + strc5);
            System.out.println("pregunta_c6  " + strc6);
            System.out.println("pregunta_c7  " + strc7);
            System.out.println("pregunta_c7a  " + strc7a);
            System.out.println("pregunta_c7b  " + strc7b);
            System.out.println("pregunta_c7c  " + strc7c);
            System.out.println("pregunta_c8  " + strc8);
            System.out.println("pregunta_c9  " + strc9);
            System.out.println("pregunta_c10  " + strc10);
            System.out.println("pregunta_c11  " + strc11);
            System.out.println("pregunta_c12  " + strc12);
            System.out.println("pregunta_c13  " + strc13);
            System.out.println("pregunta_c14  " + strc14);
            System.out.println("pregunta_c15  " + strc15);
            System.out.println("pregunta_c16  " + strc16);
            System.out.println("pregunta_c16a  " + strc16a);
            System.out.println("pregunta_c16b  " + strc16b);
            System.out.println("pregunta_c17  " + strc17);

            System.out.println(" aporta   " + strAporta);
            System.out.println(" ocupacion   " + strOcupacion);
            System.out.println(" cuantos_coches   " + strCuantosCoches);
            System.out.println(" cuartos   " + strCuartos);
            System.out.println(" cuartos_dormir   " + strCuartosDormir);
            System.out.println(" focos   " + strFocos);

            System.out.println(" ba�os   " + strBanos);
            System.out.println(" regadera   " + strRegadera);
            System.out.println(" estufa   " + strEstufa);
            System.out.println(" edad   " + strEdad);
            System.out.println(" genero   " + strGenero);
            System.out.println(" tipo_vivienda   " + strTipoVivienda);
            System.out.println(" tipo_piso   " + strTipoPiso);

            System.out.println("abandono  " + strAbandono);

            System.out.println("suma  " + suma);
            System.out.println("status  " + status);

// FIN INSERTA BASE DE DATOS //

        } catch (Exception e) {
            System.out.println("algo pas�...1");
        }

    }

    private void guardaEncuestaWS(ContentValues values) {

        showProgress(true);

//RECORRE CONTENTVALUES
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

        Log.d(TAG, "cqs -----------> " + json);

        AsyncHttpClient client = new AsyncHttpClient();
        client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
//client.addHeader("Authorization", "Bearer " + usuario.getToken());
        client.setTimeout(60000);

        RequestHandle requestHandle = client.post(customURL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                showProgress(false);
                Log.d(TAG, "cqs -----------> Respuesta OK ");
                Log.d(TAG, "cqs -----------> " + new String(responseBody));
                try {


                    String json = new String(responseBody);

                    if (json != null && !json.isEmpty()) {

                        Gson gson = new Gson();
                        JSONObject jsonObject = new JSONObject(json);
                        Log.d(TAG, "cqs -----------> Data: " + jsonObject.get("data"));

                        String login = jsonObject.getJSONObject("response").get("code").toString();
                        if (Integer.valueOf(login) == 1) {

/*JSONObject jsonUser = jsonObject.getJSONObject("data").getJSONObject("respuesta");
Type collectionType = new TypeToken<Usuario>() {}.getType();
usuario = gson.fromJson(jsonUser.toString(), collectionType);*/
//
//if(!opAbandono.equals("5")){
//    dialogo();
//}
/*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
intent.putExtra("Nombre", encuestaQuien);
startActivity(intent);
finish();*/


                            dialogo();


                        } else {
                            Toast.makeText(MainActivityPantalla1.this, "Error al subir los datos", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    showProgress(false);
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(MainActivityPantalla1.this, "Error al subir los datos", Toast.LENGTH_SHORT).show();
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
                    Log.e(TAG, "Existe un error en la conexi?n -----> " + error.getMessage());
                    if (responseBody != null)
                        Log.d(TAG, "cqs -----------> " + new String(responseBody));

                }

                Toast.makeText(MainActivityPantalla1.this, "Error de conexion, Se guarda en la base interna", Toast.LENGTH_SHORT).show();
                btnGuardar.setEnabled(true);

                dialogo();

            }
        });


    }

    public void guardar(View v) {
        System.out.println(cachaDelegacion());

        timer.cancel();

        String str = "";

        if (opAbandono.matches("sin datos")) {
            opAbandono = "1";
        }

        int tipo = Integer.parseInt(opAbandono);

        switch (tipo) {
            case 1:


                if (lay1.getVisibility() == View.VISIBLE && op1.matches("sin datos")) {
                    lay1.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + captura1, Toast.LENGTH_LONG).show();
                } else if (lay2.getVisibility() == View.VISIBLE && op2.matches("sin datos")) {
                    lay2.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + captura2, Toast.LENGTH_LONG).show();
                } else if (lay3.getVisibility() == View.VISIBLE && op3.matches("sin datos")) {
                    lay3.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + captura3, Toast.LENGTH_LONG).show();
                } else if (lay4.getVisibility() == View.VISIBLE && op4.matches("sin datos")) {
                    lay4.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + captura4, Toast.LENGTH_LONG).show();
                } else if (lay5.getVisibility() == View.VISIBLE && op5.matches("sin datos")) {
                    lay5.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + captura5, Toast.LENGTH_LONG).show();
                } else if (lay6.getVisibility() == View.VISIBLE && op6.matches("sin datos")) {
                    lay6.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + captura6, Toast.LENGTH_LONG).show();
                } else if (lay7.getVisibility() == View.VISIBLE && op7.matches("sin datos")) {
                    lay7.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + captura7, Toast.LENGTH_LONG).show();
                } else if (lay8.getVisibility() == View.VISIBLE && op8.matches("sin datos") && spinner8.getSelectedItem().toString().equals("Selecciona")) {
                    lay8.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + captura8, Toast.LENGTH_LONG).show();
                } else if (lay9.getVisibility() == View.VISIBLE && op9.matches("sin datos")) {
                    lay9.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + captura9, Toast.LENGTH_LONG).show();
                } else if (layc1.getVisibility() == View.VISIBLE && opc1.matches("sin datos")) {
                    layc1.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac1, Toast.LENGTH_LONG).show();
                }
// else if (layc2.getVisibility() == View.VISIBLE && ) {layc2.requestFocus(); Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac2, Toast.LENGTH_LONG).show();}
                else if (layc2a.getVisibility() == View.VISIBLE && opc2a.matches("sin datos")) {
                    layc2a.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac2a, Toast.LENGTH_LONG).show();
                } else if (layc2b.getVisibility() == View.VISIBLE && opc2b.matches("sin datos")) {
                    layc2b.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac2b, Toast.LENGTH_LONG).show();
                } else if (layc2c.getVisibility() == View.VISIBLE && opc2c.matches("sin datos")) {
                    layc2c.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac2c, Toast.LENGTH_LONG).show();
                } else if (layc3.getVisibility() == View.VISIBLE && opc3.matches("sin datos")) {
                    layc3.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac3, Toast.LENGTH_LONG).show();
                } else if (layc4.getVisibility() == View.VISIBLE && opc4.matches("sin datos")) {
                    layc4.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac4, Toast.LENGTH_LONG).show();
                } else if (layc4a.getVisibility() == View.VISIBLE && opc4a.matches("sin datos")) {
                    layc4a.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac4a, Toast.LENGTH_LONG).show();
                } else if (layc5.getVisibility() == View.VISIBLE && opc5.matches("sin datos")) {
                    layc5.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac5, Toast.LENGTH_LONG).show();
                } else if (layc6.getVisibility() == View.VISIBLE && opc6.matches("sin datos")) {
                    layc6.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac6, Toast.LENGTH_LONG).show();
                }
// else if (layc7.getVisibility() == View.VISIBLE && ) {layc7.requestFocus(); Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac7, Toast.LENGTH_LONG).show();}
                else if (layc7a.getVisibility() == View.VISIBLE && opc7a.matches("sin datos")) {
                    layc7a.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac7a, Toast.LENGTH_LONG).show();
                } else if (layc7b.getVisibility() == View.VISIBLE && opc7b.matches("sin datos")) {
                    layc7b.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac7b, Toast.LENGTH_LONG).show();
                } else if (layc7c.getVisibility() == View.VISIBLE && opc7c.matches("sin datos")) {
                    layc7c.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac7c, Toast.LENGTH_LONG).show();
                } else if (layc8.getVisibility() == View.VISIBLE && opc8.matches("sin datos")) {
                    layc8.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac8, Toast.LENGTH_LONG).show();
                } else if (layc9.getVisibility() == View.VISIBLE && opc9.matches("sin datos")) {
                    layc9.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac9, Toast.LENGTH_LONG).show();
                } else if (layc10.getVisibility() == View.VISIBLE && opc10.matches("sin datos") && editPreguntac10.getText().toString().trim().length() == 0) {
                    layc10.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac10, Toast.LENGTH_LONG).show();
                } else if (layc11.getVisibility() == View.VISIBLE && opc11.matches("sin datos")) {
                    layc11.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac11, Toast.LENGTH_LONG).show();
                } else if (layc12.getVisibility() == View.VISIBLE && opc12.matches("sin datos")) {
                    layc12.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac12, Toast.LENGTH_LONG).show();
                } else if (layc13.getVisibility() == View.VISIBLE && opc13.matches("sin datos")) {
                    layc13.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac13, Toast.LENGTH_LONG).show();
                } else if (layc14.getVisibility() == View.VISIBLE && opc14.matches("sin datos")) {
                    layc14.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac14, Toast.LENGTH_LONG).show();
                } else if (layc15.getVisibility() == View.VISIBLE && opc15.matches("sin datos")) {
                    layc15.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac15, Toast.LENGTH_LONG).show();
                } else if (layc16.getVisibility() == View.VISIBLE && opc16.matches("sin datos")) {
                    layc16.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac16, Toast.LENGTH_LONG).show();
                } else if (layc16a.getVisibility() == View.VISIBLE && opc16a.matches("sin datos")) {
                    layc16a.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac16a, Toast.LENGTH_LONG).show();
                } else if (layc16b.getVisibility() == View.VISIBLE && opc16b.matches("sin datos")) {
                    layc16b.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac16b, Toast.LENGTH_LONG).show();
                } else if (layc17.getVisibility() == View.VISIBLE && opc17.matches("sin datos")) {
                    layc17.requestFocus();
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac17, Toast.LENGTH_LONG).show();
                } else if (opAporta.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA         :  " + capturaAporta, Toast.LENGTH_LONG).show();
                } else if (opOcupacion.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA    :  " + capturaOcupacion, Toast.LENGTH_LONG).show();
                } else if (layCuantosCoches.getVisibility() == View.VISIBLE && opCuantosCoches.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaCuantosCoches, Toast.LENGTH_LONG).show();
                } else if (opCuartos.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA      :  " + capturaCuartos, Toast.LENGTH_LONG).show();
                } else if (opCuartosDormir.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaCuartosDormir, Toast.LENGTH_LONG).show();
                } else if (opFocos.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA        :  " + capturaFocos, Toast.LENGTH_LONG).show();
                } else if (opBanos.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA        :  " + capturaBanos, Toast.LENGTH_LONG).show();
                } else if (opEstufa.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA       :  " + capturaEstufa, Toast.LENGTH_LONG).show();
                } else if (opEdad.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA         :  " + capturaEdad, Toast.LENGTH_LONG).show();
                } else if (opGenero.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA       :  " + capturaGenero, Toast.LENGTH_LONG).show();
                } else if (opTipoVivienda.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA :  " + capturaTipoVivienda, Toast.LENGTH_LONG).show();
                } else if (opTipoPiso.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA     :  " + capturaTipoPiso, Toast.LENGTH_LONG).show();
                } else {

// para valor por default
                    if (opAbandono.matches("sin datos")) {
                        opAbandono = "1";
                    }

                    valores();
                    btnGuardar.setEnabled(false);
//            dialogo();

                } // Finaliza else
                break;

            case 2:

                if (opGenero.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaGenero, Toast.LENGTH_LONG).show();
                } else {
                    valores();
                    btnGuardar.setEnabled(false);
//                dialogo();
                }

                break;

            case 3:

                if (opGenero.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaGenero, Toast.LENGTH_LONG).show();
                } else {
                    valores();
                    btnGuardar.setEnabled(false);
//                dialogo();
                }
                break;
        }

    }

    public void Salir(View view) {
        finish();
    }

    private String sacaMaximo() {

        Set<String> set = new HashSet<String>();

        final String F = "File dbfile";

// Abrimos la base de datos 'DBUsuarios' en modo escritura
        String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_"
                + sacaImei() + "";
        usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

        db = usdbh.getReadableDatabase();

        String selectQuery = "SELECT count(*) FROM encuestas where fecha='" + formattedDate1 + "'";

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


    private String sacaMaximoConsecutivo() {

        Set<String> set = new HashSet<String>();

        final String F = "File dbfile";

// Abrimos la base de datos 'DBUsuarios' en modo escritura
        String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_"
                + sacaImei() + "";
        usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

        db = usdbh.getReadableDatabase();

        String selectQuery = "SELECT count(*) FROM encuestas";

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


    private String sacaConsecutivo() {

        String consecutivo = null;

        Set<String> set = new HashSet<String>();

        final String F = "File dbfile";

// Abrimos la base de datos 'DBUsuarios' en modo escritura

        String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_"
                + sacaImei() + "";
        usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

        db = usdbh.getReadableDatabase();

        String selectQuery = "SELECT count(*) FROM encuestas order by id desc limit 1";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                consecutivo = cursor.getString(0);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return consecutivo;
    }


    public void CargaSpinnerEscala() {
        String var = "Selecciona";
        if (var == null) {
            var = "";
        }
        final String[] datos = new String[]{"" + var + "", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "No sabe / No contest�"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item,
                datos);
        adaptador.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
        spinner8.setAdapter(adaptador);
        spinner8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {

                //       Log.i(TAG ,"cqs ----------->>"+spinnerCalifica.getSelectedItem().toString());
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void CargaSpinnerSemana() {
        String var = "Selecciona";
        if (var == null) {
            var = "";
        }
        final String[] datos = new String[]{"" + var + "", "Dictan sentencia de cadena perpetua al Chapo en EU",
                "Se presenta plan de acci�n para rescatar PEMEX", "Se har�n subastas de joyas incautadas",
                "Derrame de �cido en el Mar de Cort�s",
                "Detienen a presuntos culpables del asesinato de Norberto Ronquillo",
                "Asesinatos/ muertos/ secuestros sin especificar", "Robos/ asaltos/ inseguridad sin especificar"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item,
                datos);
        adaptador.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
        spinnerSemana.setAdapter(adaptador);
        spinnerSemana.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {

//        rdPregunta10.clearCheck();
//        editPregunta10.setText("");

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /////////////// SPINNER /////////////////
    public void CargaSpinnerAlcaldia() {
        String var = "Selecciona";
        if (var == null) {
            var = "";
        }
        final String[] datos = new String[]{"" + var + "", "ALVARO OBREGON", "AZCAPOTZALCO", "BENITO JUAREZ",
                "COYOACAN", "CUAJIMALPA DE MORELOS", "CUAUHTEMOC", "GUSTAVO A. MADERO", "IZTACALCO", "IZTAPALAPA",
                "MAGDALENA CONTRERAS", "MIGUEL HIDALGO", "MILPA ALTA", "TLAHUAC", "TLALPAN", "VENUSTIANO CARRANZA",
                "XOCHIMILCO", "No sabe / No contest�"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDelegaciones.setAdapter(adaptador);
        spinnerDelegaciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


///////////// FIN SPINNER /////////////

    private String sacaAlcaldia(String seccion) {
        Set<String> set = new HashSet<String>();
        final String F = "File dbfile";
        String Dele = "";
// Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh2 = new UsuariosSQLiteHelper2(this);
        db2 = usdbh2.getReadableDatabase();
        String selectQuery = "SELECT delegacion FROM datos where seccion='" + seccion + "'";
        Cursor cursor = db2.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Dele = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db2.close();
        return Dele;
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

    public void grabar() {
        try {
// sacaMaximo();
            String pathAudio = "/mnt/sdcard/Audio1" + formattedDate3 + "";

            Nombre nom = new Nombre();
            String nombreEncuesta = nom.nombreEncuesta();

            File sdCard = null, directory, file = null;
            if (Environment.getExternalStorageState().equals("mounted")) {
// Obtenemos el directorio de la memoria externa
                sdCard = Environment.getExternalStorageDirectory();
            }
            directory = new File(sdCard.getAbsolutePath() + "/" + nombreEncuesta + "-Audio" + formattedDate3 + "");
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            recorder.setOutputFile(
                    "/mnt/sdcard/" + nombreEncuesta + "-Audio" + formattedDate3 + "/" + nombreAudio() + "");

            try {
                recorder.prepare();
                recorder.start();
            } catch (IOException e) {
                String stackTrace = Log.getStackTraceString(e);
                Log.i(TAG, String.valueOf("Fallo en grabacion: " + e.getMessage()));
            }
        } catch (Exception e) {
            String stackTrace = Log.getStackTraceString(e);
            Log.i(TAG, "Fallo en grabando" + stackTrace);
        }

    }

    public void detenerGrabacion() {
        Thread thread = new Thread() {
            public void run() {
                if (recorder != null) {

                    try {
                        Log.i(TAG, String.valueOf("Grabadora detenida correctamente "));
                        recorder.stop();
                        recorder.reset(); // You can reuse the object by going back to
// setAudioSource() step
                        recorder.release();

                    } catch (Exception e) {
                        String stackTrace = Log.getStackTraceString(e);
                        Log.i("Manda Audios", "Al detener grabacion" + stackTrace);
                    }

                }
            }
        };
        thread.start();
    }

    /////// METODO PARA VERIFICAR LA CONEXI�N A INTERNET
    public static boolean verificaConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        // No s�lo wifi, tambi�n GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        // este bucle deber�a no ser tan �apa
        for (int i = 0; i < 2; i++) {
            // �Tenemos conexi�n? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
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
}



