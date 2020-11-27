package mx.gob.cdmx.semanal_20201128;

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
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
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
import mx.gob.cdmx.semanal_20201128.model.DatoContent;
import mx.gob.cdmx.semanal_20201128.model.Usuario;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;
import static mx.gob.cdmx.semanal_20201128.Nombre.USUARIO;
import static mx.gob.cdmx.semanal_20201128.Nombre.customURL;
import static mx.gob.cdmx.semanal_20201128.Nombre.encuesta;

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

  double latitude;
  double longitude;

  Random random = new java.util.Random();
  public int rand;

  public RadioGroup rdPreguntaOcupacion, rdPreguntaFocos,   rdPreguntaCuantosCoches,rdPreguntaCuartos, rdPreguntaCuartosDormir,
          rdPreguntaBanos,rdPreguntaRegadera,
          rdPreguntaEstufa, rdPreguntaEdad, rdPreguntaGenero, rdPreguntaTipoVivienda, rdPreguntaTipoPiso;


  public RadioGroup  rdPreguntaAporta,  rdPreguntaAbandono;


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
  private Spinner spinner10;

  private Spinner spinner0;

  private Spinner spinner9;
  Timer timer;

  public EditText txtSeccion;

  public String opEstadoCivil="sin datos";
  public String opHijos="sin datos";
  public String opJefe="sin datos";
  public String opAporta="sin datos";
  public String opEstudio="sin datos";
  public String opAbandono="sin datos";
  public String opOcupacion="sin datos";
  public String opCoche="sin datos";
  public String opFocos="sin datos";
  public String opCuantosCoches="sin datos";

  public String opCuartos="sin datos";
  public String opCuartosDormir="sin datos";
  public String opBanos="sin datos";
  public String opRegadera="sin datos";
  public String opInternet="sin datos";
  public String opTrabajaron="sin datos";
  public String opEstufa="sin datos";
  public String opEdad="sin datos";
  public String opGenero="sin datos";
  public String opTipoVivienda="sin datos";
  public String opTipoPiso="sin datos";



  public String op1="sin datos";   public RadioGroup rdPregunta1;   public EditText editPregunta1;   public String captura1;  LinearLayout lay1;

  public String op2="sin datos";   public RadioGroup rdPregunta2;   public EditText editPregunta2;   public String captura2;  LinearLayout lay2;

  public String op3="sin datos";   public RadioGroup rdPregunta3;   public EditText editPregunta3;   public String captura3;  LinearLayout lay3;

  public String op4="sin datos";   public RadioGroup rdPregunta4;   public EditText editPregunta4;   public String captura4;  LinearLayout lay4;

  public String op5="sin datos";   public RadioGroup rdPregunta5;   public EditText editPregunta5;   public String captura5;  LinearLayout lay5;

  public String op6="sin datos";   public RadioGroup rdPregunta6;   public EditText editPregunta6;   public String captura6;  LinearLayout lay6;

  public String op7="sin datos";   public RadioGroup rdPregunta7;   public EditText editPregunta7;   public String captura7;  LinearLayout lay7;

  public String op8="sin datos";   public RadioGroup rdPregunta8;   public EditText editPregunta8;   public String captura8;  LinearLayout lay8;  private Spinner spinner8;

  public String op9="sin datos";   public RadioGroup rdPregunta9;   public EditText editPregunta9;   public String captura9;  LinearLayout lay9;

  public String opc1="sin datos";   public RadioGroup rdPreguntac1;   public EditText editPreguntac1;   public String capturac1;  LinearLayout layc1;

  public String opc2="sin datos";   public RadioGroup rdPreguntac2;   public EditText editPreguntac2;   public String capturac2;  LinearLayout layc2;

  public String opc2a="sin datos";   public RadioGroup rdPreguntac2a;   public EditText editPreguntac2a;   public String capturac2a;  LinearLayout layc2a;

  public String opc2b="sin datos";   public RadioGroup rdPreguntac2b;   public EditText editPreguntac2b;   public String capturac2b;  LinearLayout layc2b;

  public String opc2c="sin datos";   public RadioGroup rdPreguntac2c;   public EditText editPreguntac2c;   public String capturac2c;  LinearLayout layc2c;

  public String opc3="sin datos";   public RadioGroup rdPreguntac3;   public EditText editPreguntac3;   public String capturac3;  LinearLayout layc3;

  public String opc4="sin datos";   public RadioGroup rdPreguntac4;   public EditText editPreguntac4;   public String capturac4;  LinearLayout layc4;

  public String opc5="sin datos";   public RadioGroup rdPreguntac5;   public EditText editPreguntac5;   public String capturac5;  LinearLayout layc5;

  public String opc6="sin datos";   public RadioGroup rdPreguntac6;   public EditText editPreguntac6;   public String capturac6;  LinearLayout layc6;

  public String opc7="sin datos";   public RadioGroup rdPreguntac7;   public EditText editPreguntac7;   public String capturac7;  LinearLayout layc7;

  public String opc7a="sin datos";   public RadioGroup rdPreguntac7a;   public EditText editPreguntac7a;   public String capturac7a;  LinearLayout layc7a;

  public String opc7b="sin datos";   public RadioGroup rdPreguntac7b;   public EditText editPreguntac7b;   public String capturac7b;  LinearLayout layc7b;

  public String opc7c="sin datos";   public RadioGroup rdPreguntac7c;   public EditText editPreguntac7c;   public String capturac7c;  LinearLayout layc7c;

  public String opc7d="sin datos";   public RadioGroup rdPreguntac7d;   public EditText editPreguntac7d;   public String capturac7d;  LinearLayout layc7d;

  public String opc8="sin datos";   public RadioGroup rdPreguntac8;   public EditText editPreguntac8;   public String capturac8;  LinearLayout layc8;

  public String opc9="sin datos";   public RadioGroup rdPreguntac9;   public EditText editPreguntac9;   public String capturac9;  LinearLayout layc9;

  public String opc10="sin datos";   public RadioGroup rdPreguntac10;   public EditText editPreguntac10;   public String capturac10;  LinearLayout layc10;

  public String opc11="sin datos";   public RadioGroup rdPreguntac11;   public EditText editPreguntac11;   public String capturac11;  LinearLayout layc11;

  public String opc11a="sin datos";   public RadioGroup rdPreguntac11a;   public EditText editPreguntac11a;   public String capturac11a;  LinearLayout layc11a;

  public String opc11b="sin datos";   public RadioGroup rdPreguntac11b;   public EditText editPreguntac11b;   public String capturac11b;  LinearLayout layc11b;

  public String opc12="sin datos";   public RadioGroup rdPreguntac12;   public EditText editPreguntac12;   public String capturac12;  LinearLayout layc12;

  public String opc12a="sin datos";   public RadioGroup rdPreguntac12a;   public EditText editPreguntac12a;   public String capturac12a;  LinearLayout layc12a;

  public String opc13="sin datos";   public RadioGroup rdPreguntac13;   public EditText editPreguntac13;   public String capturac13;  LinearLayout layc13;

  public String opc14="sin datos";   public RadioGroup rdPreguntac14;   public EditText editPreguntac14;   public String capturac14;  LinearLayout layc14;


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




  public String capturaOcupacion,   capturaCuantosCoches,  capturaFocos, capturaCuartos, capturaCuartosDormir, capturaBanos;
  public String  capturaEstufa, capturaEdad, capturaGenero, capturaTipoVivienda, capturaTipoPiso;
  public String capturaJefe, capturaAporta;


  public String maximo = "";
  int elMaximo;
  String tipoEncuesta;

  public String tablet;

  public String tiempo;

  Calendar c = Calendar.getInstance();

  SimpleDateFormat df1  = new SimpleDateFormat("yyy-MM-dd");
  String formattedDate1 = df1.format(c.getTime());

  SimpleDateFormat df2  = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
  String formattedDate2 = df2.format(c.getTime());

  SimpleDateFormat df3  = new SimpleDateFormat("yyyMMdd");
  String formattedDate3 = df3.format(c.getTime());

  SimpleDateFormat df6  = new SimpleDateFormat("MM");
  String formattedDate6 = df6.format(c.getTime());

  SimpleDateFormat df7  = new SimpleDateFormat("dd");
  String formattedDate7 = df7.format(c.getTime());

  SimpleDateFormat df4  = new SimpleDateFormat("HH:mm:ss a");
  String formattedDate4 = df4.format(c.getTime());

  SimpleDateFormat df5  = new SimpleDateFormat("HH:mm:ss");
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
        i.putExtra(USUARIO,usuario);
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
        builder.setMessage("Excediste el tiempo máximo para realizar la encuesta \n"
                + "¡¡¡ Se detendrá la grabación y se reiniciará la Aplicación..!!!").setTitle("AVISO...!!!")
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
// Esto es lo que hace mi botón al pulsar ir a atrás
      Toast.makeText(getApplicationContext(), "No puedo ir hacia atrás!!\nEstoy grabando...", Toast.LENGTH_SHORT)
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
            R.layout.activity_pantalla1,
            R.layout.activity_pantalla2,
            R.layout.activity_pantalla3,
            R.layout.activity_pantalla4,
            R.layout.activity_pantalla5,
            R.layout.activity_pantalla6,
            R.layout.activity_pantalla7,
            R.layout.activity_pantalla8,
            R.layout.activity_pantalla9,
            R.layout.activity_pantalla10,
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
//    setContentView(R.layout.activity_pantalla1); // COMENTAR ESTA CUANDO ES ALEATORIO

    Intent startingIntent = getIntent();
    if (startingIntent == null) {
      Log.e(TAG, "No Intent?  We´re not supposed to be here...");
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
        rand = random.nextInt(9);
        setContentView(mLinearLayoutIds[rand]);
        Log.i(null, "El aleatorio: " + rand); // si rand= 11 en el layoud corresponde a uno mas


    /*activity_pantalla12*/

// Crea Log cuando falla la app
    Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(MainActivityPantalla1.this,this));


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
              Log.i(TAG, "Iniciando Grabación");
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

// justificar el texto
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      textPreguntaEntrada.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
    }



    res = getResources();



    rdPregunta1 = (RadioGroup)findViewById(R.id.rdPregunta1); captura1 =res.getString(R.string.PREGUNTA1);  lay1 = (LinearLayout) findViewById(R.id.lay1);

    rdPregunta2 = (RadioGroup)findViewById(R.id.rdPregunta2); captura2 =res.getString(R.string.PREGUNTA2);  lay2 = (LinearLayout) findViewById(R.id.lay2);

    rdPregunta3 = (RadioGroup)findViewById(R.id.rdPregunta3); captura3 =res.getString(R.string.PREGUNTA3);  lay3 = (LinearLayout) findViewById(R.id.lay3);

    rdPregunta4 = (RadioGroup)findViewById(R.id.rdPregunta4); captura4 =res.getString(R.string.PREGUNTA4);  lay4 = (LinearLayout) findViewById(R.id.lay4);

    rdPregunta5 = (RadioGroup)findViewById(R.id.rdPregunta5); captura5 =res.getString(R.string.PREGUNTA5);  lay5 = (LinearLayout) findViewById(R.id.lay5);

    rdPregunta6 = (RadioGroup)findViewById(R.id.rdPregunta6); captura6 =res.getString(R.string.PREGUNTA6);  lay6 = (LinearLayout) findViewById(R.id.lay6);

    rdPregunta7 = (RadioGroup)findViewById(R.id.rdPregunta7); captura7 =res.getString(R.string.PREGUNTA7);  lay7 = (LinearLayout) findViewById(R.id.lay7);

    rdPregunta8 = (RadioGroup)findViewById(R.id.rdPregunta8); captura8 =res.getString(R.string.PREGUNTA8);  lay8 = (LinearLayout) findViewById(R.id.lay8);

    rdPregunta9 = (RadioGroup)findViewById(R.id.rdPregunta9); captura9 =res.getString(R.string.PREGUNTA9);  lay9 = (LinearLayout) findViewById(R.id.lay9);

    rdPreguntac1 = (RadioGroup)findViewById(R.id.rdPreguntac1); capturac1 =res.getString(R.string.PREGUNTAc1);  layc1 = (LinearLayout) findViewById(R.id.layc1);

    rdPreguntac2 = (RadioGroup)findViewById(R.id.rdPreguntac2); capturac2 =res.getString(R.string.PREGUNTAc2);  layc2 = (LinearLayout) findViewById(R.id.layc2);

    rdPreguntac2a = (RadioGroup)findViewById(R.id.rdPreguntac2a); capturac2a =res.getString(R.string.PREGUNTAc2a);  layc2a = (LinearLayout) findViewById(R.id.layc2a);

    rdPreguntac2b = (RadioGroup)findViewById(R.id.rdPreguntac2b); capturac2b =res.getString(R.string.PREGUNTAc2b);  layc2b = (LinearLayout) findViewById(R.id.layc2b);

    rdPreguntac2c = (RadioGroup)findViewById(R.id.rdPreguntac2c); capturac2c =res.getString(R.string.PREGUNTAc2c);  layc2c = (LinearLayout) findViewById(R.id.layc2c);

    rdPreguntac3 = (RadioGroup)findViewById(R.id.rdPreguntac3); capturac3 =res.getString(R.string.PREGUNTAc3);  layc3 = (LinearLayout) findViewById(R.id.layc3);

    rdPreguntac4 = (RadioGroup)findViewById(R.id.rdPreguntac4); capturac4 =res.getString(R.string.PREGUNTAc4);  layc4 = (LinearLayout) findViewById(R.id.layc4);

    rdPreguntac5 = (RadioGroup)findViewById(R.id.rdPreguntac5); capturac5 =res.getString(R.string.PREGUNTAc5);  layc5 = (LinearLayout) findViewById(R.id.layc5);

    rdPreguntac6 = (RadioGroup)findViewById(R.id.rdPreguntac6); capturac6 =res.getString(R.string.PREGUNTAc6);  layc6 = (LinearLayout) findViewById(R.id.layc6);

    rdPreguntac7 = (RadioGroup)findViewById(R.id.rdPreguntac7); capturac7 =res.getString(R.string.PREGUNTAc7);  layc7 = (LinearLayout) findViewById(R.id.layc7);

    rdPreguntac7a = (RadioGroup)findViewById(R.id.rdPreguntac7a); capturac7a =res.getString(R.string.PREGUNTAc7a);  layc7a = (LinearLayout) findViewById(R.id.layc7a);

    rdPreguntac7b = (RadioGroup)findViewById(R.id.rdPreguntac7b); capturac7b =res.getString(R.string.PREGUNTAc7b);  layc7b = (LinearLayout) findViewById(R.id.layc7b);

    rdPreguntac7c = (RadioGroup)findViewById(R.id.rdPreguntac7c); capturac7c =res.getString(R.string.PREGUNTAc7c);  layc7c = (LinearLayout) findViewById(R.id.layc7c);

    rdPreguntac7d = (RadioGroup)findViewById(R.id.rdPreguntac7d); capturac7d =res.getString(R.string.PREGUNTAc7d);  layc7d = (LinearLayout) findViewById(R.id.layc7d);

    rdPreguntac8 = (RadioGroup)findViewById(R.id.rdPreguntac8); capturac8 =res.getString(R.string.PREGUNTAc8);  layc8 = (LinearLayout) findViewById(R.id.layc8);

    rdPreguntac9 = (RadioGroup)findViewById(R.id.rdPreguntac9); capturac9 =res.getString(R.string.PREGUNTAc9);  layc9 = (LinearLayout) findViewById(R.id.layc9);

    rdPreguntac10 = (RadioGroup)findViewById(R.id.rdPreguntac10); capturac10 =res.getString(R.string.PREGUNTAc10);  layc10 = (LinearLayout) findViewById(R.id.layc10);

    rdPreguntac11 = (RadioGroup)findViewById(R.id.rdPreguntac11); capturac11 =res.getString(R.string.PREGUNTAc11);  layc11 = (LinearLayout) findViewById(R.id.layc11);

    rdPreguntac11a = (RadioGroup)findViewById(R.id.rdPreguntac11a); capturac11a =res.getString(R.string.PREGUNTAc11a);  layc11a = (LinearLayout) findViewById(R.id.layc11a);

    rdPreguntac11b = (RadioGroup)findViewById(R.id.rdPreguntac11b); capturac11b =res.getString(R.string.PREGUNTAc11b);  layc11b = (LinearLayout) findViewById(R.id.layc11b);

    rdPreguntac12 = (RadioGroup)findViewById(R.id.rdPreguntac12); capturac12 =res.getString(R.string.PREGUNTAc12);  layc12 = (LinearLayout) findViewById(R.id.layc12);

    rdPreguntac12a = (RadioGroup)findViewById(R.id.rdPreguntac12a); capturac12a =res.getString(R.string.PREGUNTAc12a);  layc12a = (LinearLayout) findViewById(R.id.layc12a);

    rdPreguntac13 = (RadioGroup)findViewById(R.id.rdPreguntac13); capturac13 =res.getString(R.string.PREGUNTAc13);  layc13 = (LinearLayout) findViewById(R.id.layc13);

    rdPreguntac14 = (RadioGroup)findViewById(R.id.rdPreguntac14); capturac14 =res.getString(R.string.PREGUNTAc14);  layc14 = (LinearLayout) findViewById(R.id.layc14);

    editPreguntac8= (EditText)findViewById(R.id.editPreguntac8);



    spinner8 =(Spinner) findViewById(R.id.spinner8);

    spinner9 =(Spinner) findViewById(R.id.spinner9);


    laySocioE        = (LinearLayout) findViewById(R.id.laySocioE);
    layEst           = (LinearLayout) findViewById(R.id.layEst);
    layAporta        = (LinearLayout) findViewById(R.id.layAporta);
    layOcupacion     = (LinearLayout) findViewById(R.id.layOcupacion);
    layCuartos       = (LinearLayout) findViewById(R.id.layCuartos);
    layCuartosDormir = (LinearLayout) findViewById(R.id.layCuartosDormir);
    layFocos         = (LinearLayout) findViewById(R.id.layFocos);
    layBanos         = (LinearLayout) findViewById(R.id.layBanos);
    layRegadera      = (LinearLayout) findViewById(R.id.layRegadera);
    layEstufa        = (LinearLayout) findViewById(R.id.layEstufa);
    //layEdad = (LinearLayout) findViewById(R.id.layEdad);
    //layTipoPiso = (LinearLayout) findViewById(R.id.layTipoPiso);
    layTipoVivienda  = (LinearLayout) findViewById(R.id.layTipoVivienda);
    layGenero        = (LinearLayout) findViewById(R.id.layGenero);

    radio_abandono1 = (RadioButton) findViewById(R.id.radio_abandono1);
    radio_abandono2 = (RadioButton) findViewById(R.id.radio_abandono2);
    radio_abandono3 = (RadioButton) findViewById(R.id.radio_abandono3);
    radio_abandono4 = (RadioButton) findViewById(R.id.radio_abandono4);

    spinnerCalifica =(Spinner) findViewById(R.id.spinner8);


    rdPreguntaAporta        = (RadioGroup) findViewById(R.id.rdPreguntaAporta);
    rdPreguntaAbandono      = (RadioGroup) findViewById(R.id.rdPreguntaAbandono);
    rdPreguntaOcupacion     = (RadioGroup) findViewById(R.id.rdPreguntaOcupacion);
    rdPreguntaCuantosCoches = (RadioGroup) findViewById(R.id.rdPreguntaCuantosCoches);
    rdPreguntaCuartos       = (RadioGroup) findViewById(R.id.rdPreguntaCuartos);
    rdPreguntaCuartosDormir = (RadioGroup) findViewById(R.id.rdPreguntaCuartosDormir);
    rdPreguntaFocos         = (RadioGroup) findViewById(R.id.rdPreguntaFocos);
    rdPreguntaBanos         = (RadioGroup) findViewById(R.id.rdPreguntaBanos);
    rdPreguntaRegadera      = (RadioGroup) findViewById(R.id.rdPreguntaRegadera);
    rdPreguntaEstufa        = (RadioGroup) findViewById(R.id.rdPreguntaEstufa);
    rdPreguntaEdad          = (RadioGroup) findViewById(R.id.rdPreguntaEdad);
    rdPreguntaGenero        = (RadioGroup) findViewById(R.id.rdPreguntaGenero);
    rdPreguntaTipoVivienda  = (RadioGroup) findViewById(R.id.rdPreguntaTipoVivienda);
    rdPreguntaTipoPiso      = (RadioGroup) findViewById(R.id.rdPreguntaTipoPiso);


    capturaAporta        = res.getString(R.string.PREGUNTAAPORTA);
    capturaOcupacion     = res.getString(R.string.PREGUNTAOCUPACION);
    capturaCuantosCoches = res.getString(R.string.PREGUNTACUANTOSCOCHES);
    capturaFocos         = res.getString(R.string.PREGUNTAFOCOS);
    capturaCuartos       = res.getString(R.string.PREGUNTACUARTOS);
    capturaCuartosDormir = res.getString(R.string.PREGUNTACUARTOSDORMIR);
    capturaBanos         = res.getString(R.string.PREGUNTABANOS);
    capturaEstufa        = res.getString(R.string.PREGUNTAESTUFA);
    //capturaEdad = res.getString(R.string.PREGUNTAEDAD);
    capturaGenero        = res.getString(R.string.PREGUNTAGENERO);
    capturaTipoVivienda  = res.getString(R.string.PREGUNTA_TIPO_VIVIENDA);
    //capturaTipoPiso = res.getString(R.string.PREGUNTA_TIPO_PISO);




    btnGuardar = (Button) findViewById(R.id.btnGuardar);
    btnSalir   = (Button) findViewById(R.id.btnSalir);
    btnSalir.setEnabled(false);
    btnSalir.setVisibility(View.GONE);


//
//

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
        }

        else if (checkedId == R.id.radio2) {
          op1 = "1";
        }

        else if (checkedId == R.id.radio3) {
          op1 = "Televisión ";
        }

        else if (checkedId == R.id.radio4) {
          op1 = "Radio";
        }

        else if (checkedId == R.id.radio5) {
          op1 = "Periódico";
        }

        else if (checkedId == R.id.radio6) {
          op1 = "Redes sociales";
        }

        else if (checkedId == R.id.radio7) {
          op1 = "Internet";
        }

        else if (checkedId == R.id.radio8) {
          op1 = "Otra";
        }

        else if (checkedId == R.id.radio9) {
          op1 = "No sabe / No contestó";
        }

      }

    });


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPregunta2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          op2 = "0";
        }

        else if (checkedId == R.id.radio2) {
          op2 = "2";
        }

        else if (checkedId == R.id.radio3) {
          op2 = "Si";
        }

        else if (checkedId == R.id.radio4) {
          op2 = "No";
        }

        else if (checkedId == R.id.radio5) {
          op2 = "No sabe / No contestó";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPregunta3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          op3 = "0";
        }

        else if (checkedId == R.id.radio2) {
          op3 = "3";
        }

        else if (checkedId == R.id.radio3) {
          op3 = "Si";
        }

        else if (checkedId == R.id.radio4) {
          op3 = "No";
        }

        else if (checkedId == R.id.radio5) {
          op3 = "No sabe / No contestó";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPregunta4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          op4 = "0";
        }

        else if (checkedId == R.id.radio2) {
          op4 = "4";
        }

        else if (checkedId == R.id.radio3) {
          op4 = "Si";
        }

        else if (checkedId == R.id.radio4) {
          op4 = "No";
        }

        else if (checkedId == R.id.radio5) {
          op4 = "No sabe / No contestó";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPregunta5.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          op5 = "0";
        }

        else if (checkedId == R.id.radio2) {
          op5 = "5";
        }

        else if (checkedId == R.id.radio3) {
          op5 = "De acuerdo";
        }

        else if (checkedId == R.id.radio4) {
          op5 = "Ni de acuerdo, ni en desacuerdo";
        }

        else if (checkedId == R.id.radio5) {
          op5 = "En desacuerdo";
        }

        else if (checkedId == R.id.radio6) {
          op5 = "No sabe / No contestó";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPregunta6.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          op6 = "0";
        }

        else if (checkedId == R.id.radio2) {
          op6 = "6";
        }

        else if (checkedId == R.id.radio3) {
          op6 = "Si";
        }

        else if (checkedId == R.id.radio4) {
          op6 = "No";
        }

        else if (checkedId == R.id.radio5) {
          op6 = "No sabe / No contestó";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPregunta7.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          op7 = "0";
        }

        else if (checkedId == R.id.radio2) {
          op7 = "7";
        }

        else if (checkedId == R.id.radio3) {
          op7 = "De acuerdo";
        }

        else if (checkedId == R.id.radio4) {
          op7 = "Ni de acuerdo, ni en desacuerdo";
        }

        else if (checkedId == R.id.radio5) {
          op7 = "En desacuerdo";
        }

        else if (checkedId == R.id.radio6) {
          op7 = "No sabe / No contestó";
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
        }

        else if (checkedId == R.id.radio2) {
          op8 = "8";
          spinner8.setSelection(0);
        }

        else if (checkedId == R.id.radio3) {
          op8 = "Combo 1 al 10";
          spinner8.setSelection(0);
        }

        else if (checkedId == R.id.radio4) {
          op8 = "No sabe / No contestó";
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
        }

        else if (checkedId == R.id.radio2) {
          op9 = "9";
        }

        else if (checkedId == R.id.radio3) {
          op9 = "Mucha";
        }

        else if (checkedId == R.id.radio4) {
          op9 = "Poca";
        }

        else if (checkedId == R.id.radio5) {
          op9 = "Ninguna";
        }

        else if (checkedId == R.id.radio6) {
          op9 = "No sabe / No contestó";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc1 = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc1 = "c1";
        }

        else if (checkedId == R.id.radio3) {
          opc1 = "Si";
          layc2 .setVisibility(View.VISIBLE);	rdPreguntac2.clearCheck();	opc2="sin datos";
          layc2a .setVisibility(View.VISIBLE);	rdPreguntac2a.clearCheck();	opc2a="sin datos";
          layc2b .setVisibility(View.VISIBLE);	rdPreguntac2b.clearCheck();	opc2b="sin datos";
          layc2c .setVisibility(View.VISIBLE);	rdPreguntac2c.clearCheck();	opc2c="sin datos";

        }

        else if (checkedId == R.id.radio4) {
          opc1 = "No";
          layc2 .setVisibility(View.GONE);	rdPreguntac2.clearCheck();	opc2="No aplica";
          layc2a .setVisibility(View.GONE);	rdPreguntac2a.clearCheck();	opc2a="No aplica";
          layc2b .setVisibility(View.GONE);	rdPreguntac2b.clearCheck();	opc2b="No aplica";
          layc2c .setVisibility(View.GONE);	rdPreguntac2c.clearCheck();	opc2c="No aplica";

        }

        else if (checkedId == R.id.radio5) {
          opc1 = "No sabe/ no contesto";
          layc2 .setVisibility(View.GONE);	rdPreguntac2.clearCheck();	opc2="No aplica";
          layc2a .setVisibility(View.GONE);	rdPreguntac2a.clearCheck();	opc2a="No aplica";
          layc2b .setVisibility(View.GONE);	rdPreguntac2b.clearCheck();	opc2b="No aplica";
          layc2c .setVisibility(View.GONE);	rdPreguntac2c.clearCheck();	opc2c="No aplica";

        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc2 = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc2 = "c2";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac2a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc2a = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc2a = "c2a";
        }

        else if (checkedId == R.id.radio3) {
          opc2a = "Si";
        }

        else if (checkedId == R.id.radio4) {
          opc2a = "No";
        }

        else if (checkedId == R.id.radio5) {
          opc2a = "No sabe / No contestó";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac2b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc2b = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc2b = "c2b";
        }

        else if (checkedId == R.id.radio3) {
          opc2b = "Si";
        }

        else if (checkedId == R.id.radio4) {
          opc2b = "No";
        }

        else if (checkedId == R.id.radio5) {
          opc2b = "No sabe / No contestó";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac2c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc2c = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc2c = "c2c";
        }

        else if (checkedId == R.id.radio3) {
          opc2c = "Si";
        }

        else if (checkedId == R.id.radio4) {
          opc2c = "No";
        }

        else if (checkedId == R.id.radio5) {
          opc2c = "No sabe / No contestó";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc3 = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc3 = "c3";
        }

        else if (checkedId == R.id.radio3) {
          opc3 = "Si";
        }

        else if (checkedId == R.id.radio4) {
          opc3 = "No";
        }

        else if (checkedId == R.id.radio5) {
          opc3 = "No sabe/ no contesto";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc4 = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc4 = "c4";
        }

        else if (checkedId == R.id.radio3) {
          opc4 = "Adecuada";
        }

        else if (checkedId == R.id.radio4) {
          opc4 = "Inadecuada";
        }

        else if (checkedId == R.id.radio5) {
          opc4 = "No sabe/ no contesto";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac5.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc5 = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc5 = "c5";
        }

        else if (checkedId == R.id.radio3) {
          opc5 = "Si";
          layc6 .setVisibility(View.VISIBLE);	rdPreguntac6.clearCheck();	opc6="sin datos";
          layc7 .setVisibility(View.VISIBLE);	rdPreguntac7.clearCheck();	opc7="sin datos";
          layc7a .setVisibility(View.VISIBLE);	rdPreguntac7a.clearCheck();	opc7a="sin datos";
          layc7b .setVisibility(View.VISIBLE);	rdPreguntac7b.clearCheck();	opc7b="sin datos";
          layc7c .setVisibility(View.VISIBLE);	rdPreguntac7c.clearCheck();	opc7c="sin datos";
          layc7d .setVisibility(View.VISIBLE);	rdPreguntac7d.clearCheck();	opc7d="sin datos";

        }

        else if (checkedId == R.id.radio4) {
          opc5 = "No";
          layc6 .setVisibility(View.GONE);	rdPreguntac6.clearCheck();	opc6="No aplica";
          layc7 .setVisibility(View.GONE);	rdPreguntac7.clearCheck();	opc7="No aplica";
          layc7a .setVisibility(View.GONE);	rdPreguntac7a.clearCheck();	opc7a="No aplica";
          layc7b .setVisibility(View.GONE);	rdPreguntac7b.clearCheck();	opc7b="No aplica";
          layc7c .setVisibility(View.GONE);	rdPreguntac7c.clearCheck();	opc7c="No aplica";
          layc7d .setVisibility(View.GONE);	rdPreguntac7d.clearCheck();	opc7d="No aplica";


        }

        else if (checkedId == R.id.radio5) {
          opc5 = "No sabe/ no contesto";
          layc6 .setVisibility(View.GONE);	rdPreguntac6.clearCheck();	opc6="No aplica";
          layc7 .setVisibility(View.GONE);	rdPreguntac7.clearCheck();	opc7="No aplica";
          layc7a .setVisibility(View.GONE);	rdPreguntac7a.clearCheck();	opc7a="No aplica";
          layc7b .setVisibility(View.GONE);	rdPreguntac7b.clearCheck();	opc7b="No aplica";
          layc7c .setVisibility(View.GONE);	rdPreguntac7c.clearCheck();	opc7c="No aplica";
          layc7d .setVisibility(View.GONE);	rdPreguntac7d.clearCheck();	opc7d="No aplica";

        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac6.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc6 = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc6 = "c6";
        }

        else if (checkedId == R.id.radio3) {
          opc6 = "Si";
          layc7 .setVisibility(View.VISIBLE);	rdPreguntac7.clearCheck();	opc7="sin datos";
          layc7a .setVisibility(View.VISIBLE);	rdPreguntac7a.clearCheck();	opc7a="sin datos";
          layc7b .setVisibility(View.VISIBLE);	rdPreguntac7b.clearCheck();	opc7b="sin datos";
          layc7c .setVisibility(View.VISIBLE);	rdPreguntac7c.clearCheck();	opc7c="sin datos";
          layc7d .setVisibility(View.VISIBLE);	rdPreguntac7d.clearCheck();	opc7d="sin datos";

        }

        else if (checkedId == R.id.radio4) {
          opc6 = "No";
          layc7 .setVisibility(View.GONE);	rdPreguntac7.clearCheck();	opc7="No aplica";
          layc7a .setVisibility(View.GONE);	rdPreguntac7a.clearCheck();	opc7a="No aplica";
          layc7b .setVisibility(View.GONE);	rdPreguntac7b.clearCheck();	opc7b="No aplica";
          layc7c .setVisibility(View.GONE);	rdPreguntac7c.clearCheck();	opc7c="No aplica";
          layc7d .setVisibility(View.GONE);	rdPreguntac7d.clearCheck();	opc7d="No aplica";


        }

        else if (checkedId == R.id.radio5) {
          opc6 = "No sabe/ no contesto";
          layc7 .setVisibility(View.GONE);	rdPreguntac7.clearCheck();	opc7="No aplica";
          layc7a .setVisibility(View.GONE);	rdPreguntac7a.clearCheck();	opc7a="No aplica";
          layc7b .setVisibility(View.GONE);	rdPreguntac7b.clearCheck();	opc7b="No aplica";
          layc7c .setVisibility(View.GONE);	rdPreguntac7c.clearCheck();	opc7c="No aplica";
          layc7d .setVisibility(View.GONE);	rdPreguntac7d.clearCheck();	opc7d="No aplica";

        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac7.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc7 = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc7 = "c7";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac7a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc7a = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc7a = "Buena";
        }

        else if (checkedId == R.id.radio3) {
          opc7a = "Mala";
        }

        else if (checkedId == R.id.radio4) {
          opc7a = "No sabe/ no contesto";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac7b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc7b = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc7b = "Suficiente";
        }

        else if (checkedId == R.id.radio3) {
          opc7b = "Insuficiente";
        }

        else if (checkedId == R.id.radio4) {
          opc7b = "No sabe/ no contesto";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac7c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc7c = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc7c = "Aclaró sus dudas";
        }

        else if (checkedId == R.id.radio3) {
          opc7c = "Lo dejo con dudas";
        }

        else if (checkedId == R.id.radio4) {
          opc7c = "No sabe/ no contesto";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac7d.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc7d = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc7d = "Útil";
        }

        else if (checkedId == R.id.radio3) {
          opc7d = "Perdida de tiempo";
        }

        else if (checkedId == R.id.radio4) {
          opc7d = "No sabe/ no contesto";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




    rdPreguntac8.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc8 = "1";
          editPreguntac8.setText("");
        }

        else if (checkedId == R.id.radio2) {
          opc8 = "c8";
          editPreguntac8.setText("");
        }

        else if (checkedId == R.id.radio3) {
          opc8 = "No sabe/ no contesto";
          editPreguntac8.setText("");
        }

      }
    });


    editPreguntac8.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        v.setFocusable(true);
        v.setFocusableInTouchMode(true);
        return false;
      }
    });


    editPreguntac8.addTextChangedListener(new TextWatcher() {
      @Override
      public void afterTextChanged(Editable s) {}
      @Override
      public void beforeTextChanged(CharSequence s, int start,int count, int after) {
      }
      @Override
      public void onTextChanged(CharSequence s, int start,int before, int count) {
        if(s.length() != 0){
          rdPreguntac8.clearCheck();
        }
      }
    });


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac9.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc9 = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc9 = "c9";
        }

        else if (checkedId == R.id.radio3) {
          opc9 = "Si";
        }

        else if (checkedId == R.id.radio4) {
          opc9 = "No";
        }

        else if (checkedId == R.id.radio5) {
          opc9 = "No sabe / No contestó";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac10.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc10 = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc10 = "c10";
        }

        else if (checkedId == R.id.radio3) {
          opc10 = "Si";
        }

        else if (checkedId == R.id.radio4) {
          opc10 = "No";
        }

        else if (checkedId == R.id.radio5) {
          opc10 = "No sabe / No contestó";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac11.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc11 = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc11 = "c11";
        }

        else if (checkedId == R.id.radio3) {
          opc11 = "Si sabía";
        }

        else if (checkedId == R.id.radio4) {
          opc11 = "No sabia";
        }

        else if (checkedId == R.id.radio5) {
          opc11 = "No sabe / No contestó";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac11a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc11a = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc11a = "c11a";
        }

        else if (checkedId == R.id.radio3) {
          opc11a = "Acuerdo";
        }

        else if (checkedId == R.id.radio4) {
          opc11a = "Ni de acuerdo ni en desacuerdo";
        }

        else if (checkedId == R.id.radio5) {
          opc11a = "Desacuerdo";
        }

        else if (checkedId == R.id.radio6) {
          opc11a = "No sabe / No contestó";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac11b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc11b = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc11b = "c11b";
        }

        else if (checkedId == R.id.radio3) {
          opc11b = "Si, sin importar el candidato";
        }

        else if (checkedId == R.id.radio4) {
          opc11b = "Depende del candidato";
        }

        else if (checkedId == R.id.radio5) {
          opc11b = "Nunca votaría para que alguien se reeligiera";
        }

        else if (checkedId == R.id.radio6) {
          opc11b = "No sabe / No contestó";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac12.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc12 = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc12 = "c12";
        }

        else if (checkedId == R.id.radio3) {
          opc12 = "Claudia Sheinbaum/ Morena/ PT/ Encuentro social";
          layc12a .setVisibility(View.VISIBLE);	rdPreguntac12a.clearCheck();	opc12a="sin datos";

        }

        else if (checkedId == R.id.radio4) {
          opc12 = "Alejandra Barrales/ PAN/ PRD/  MC";
          layc12a .setVisibility(View.GONE);	rdPreguntac12a.clearCheck();	opc12a="No aplica";
        }

        else if (checkedId == R.id.radio5) {
          opc12 = "Mikel Arriola/ PRI";
          layc12a .setVisibility(View.GONE);	rdPreguntac12a.clearCheck();	opc12a="No aplica";
        }

        else if (checkedId == R.id.radio6) {
          opc12 = "Mariana Boy/ PVEM";
          layc12a .setVisibility(View.GONE);	rdPreguntac12a.clearCheck();	opc12a="No aplica";
        }

        else if (checkedId == R.id.radio7) {
          opc12 = "Lorena Osornio/ Independiente";
          layc12a .setVisibility(View.GONE);	rdPreguntac12a.clearCheck();	opc12a="No aplica";
        }

        else if (checkedId == R.id.radio8) {
          opc12 = "Marco Rascón/ PHCDMX";
          layc12a .setVisibility(View.GONE);	rdPreguntac12a.clearCheck();	opc12a="No aplica";
        }

        else if (checkedId == R.id.radio9) {
          opc12 = "Purificación Carpinteyro/ PANAL";
          layc12a .setVisibility(View.GONE);	rdPreguntac12a.clearCheck();	opc12a="No aplica";
        }

        else if (checkedId == R.id.radio10) {
          opc12 = "No voto";
          layc12a .setVisibility(View.GONE);	rdPreguntac12a.clearCheck();	opc12a="No aplica";
        }

        else if (checkedId == R.id.radio11) {
          opc12 = "Anuló su voto";
          layc12a .setVisibility(View.GONE);	rdPreguntac12a.clearCheck();	opc12a="No aplica";
        }

        else if (checkedId == R.id.radio12) {
          opc12 = "No sabe/ no contestó";
          layc12a .setVisibility(View.GONE);	rdPreguntac12a.clearCheck();	opc12a="No aplica";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac12a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc12a = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc12a = "c12a";
        }

        else if (checkedId == R.id.radio3) {
          opc12a = "Si, fue la mejor";
        }

        else if (checkedId == R.id.radio4) {
          opc12a = "En parte";
        }

        else if (checkedId == R.id.radio5) {
          opc12a = "No fue la mejor";
        }

        else if (checkedId == R.id.radio6) {
          opc12a = "No sabe / No contestó";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac13.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc13 = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc13 = "c13";
        }

        else if (checkedId == R.id.radio3) {
          opc13 = "Bien";
        }

        else if (checkedId == R.id.radio4) {
          opc13 = "Regular";
        }

        else if (checkedId == R.id.radio5) {
          opc13 = "Mal";
        }

        else if (checkedId == R.id.radio6) {
          opc13 = "No sabe / No contestó";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    rdPreguntac14.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opc14 = "0";
        }

        else if (checkedId == R.id.radio2) {
          opc14 = "c14";
        }

        else if (checkedId == R.id.radio3) {
          opc14 = "Morena";
        }

        else if (checkedId == R.id.radio4) {
          opc14 = "PRI";
        }

        else if (checkedId == R.id.radio5) {
          opc14 = "PAN";
        }

        else if (checkedId == R.id.radio6) {
          opc14 = "PRD";
        }

        else if (checkedId == R.id.radio7) {
          opc14 = "PT";
        }

        else if (checkedId == R.id.radio8) {
          opc14 = "PVEM";
        }

        else if (checkedId == R.id.radio9) {
          opc14 = "Movimiento Ciudadano";
        }

        else if (checkedId == R.id.radio10) {
          opc14 = "Candidato Independiente";
        }

        else if (checkedId == R.id.radio11) {
          opc14 = "Otro";
        }

        else if (checkedId == R.id.radio12) {
          opc14 = "No votaría";
        }

        else if (checkedId == R.id.radio13) {
          opc14 = "Anularía su voto";
        }

        else if (checkedId == R.id.radio0) {
          opc14 = "No sabe / No contestó";
        }

      }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





///////////////////////////////////// SOCIOECONOMICOS  /////////////////////////////////////
//        rdPreguntaHijos.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                if (checkedId == R.id.radio1) {
//                    opHijos = "Si";
//                } else if (checkedId == R.id.radio2) {
//                    opHijos = "No";
//                } else if (checkedId == R.id.radio0) {
//                    opHijos = "No sabe / No contestó";
//                } else {
//                    opHijos = "";
//
//                }
//
//            }
//        });

    rdPreguntaAporta.setOnCheckedChangeListener(new OnCheckedChangeListener() {

      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opAporta = "No completó primaria";
        } else if (checkedId == R.id.radio2) {
          opAporta = "Primaria o secundaria";
        } else if (checkedId == R.id.radio3) {
          opAporta = "Preparatoria o carrera técnica";
        } else if (checkedId == R.id.radio4) {
          opAporta = "Licenciatura";
        } else if (checkedId == R.id.radio5) {
          opAporta = "Posgrado";
        } else if (checkedId == R.id.radio0) {
          opAporta = "No contestó";
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
          opOcupacion = "No contestó";
        } else {
          opOcupacion = "";

        }

      }
    });

//    rdPreguntaCoche.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//        @Override
//        public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//            if (checkedId == R.id.radio1) {
//                opCoche = "Si";
//                layCuantosCoches.setVisibility(View.VISIBLE);
//                opCuantosCoches = "sin datos";
//            } else if (checkedId == R.id.radio2) {
//                opCoche = "No";
//                layCuantosCoches.setVisibility(View.GONE);
//                rdPreguntaCuantosCoches.clearCheck();
//                opCuantosCoches = "Ninguno";
//
//            } else if (checkedId == R.id.radio0) {
//                opCoche = "No contestó";
//                layCuantosCoches.setVisibility(View.GONE);
//                rdPreguntaCuantosCoches.clearCheck();
//                opCuantosCoches = "Ninguno";
//
//            } else {
//                opCoche = "";
//
//            }
//
//        }
//    });


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
          opCuantosCoches = "Cuatro o más";
        } else if (checkedId == R.id.radio0) {
          opCuantosCoches = "No contestó";
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
          opCuartos = "No contestó";
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
          opCuartosDormir = "No contestó";
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
          opFocos = "21 o más";
        } else if (checkedId == R.id.radio0) {
          opFocos = "No contestó";
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
          opBanos = "Cuatro o más";
        } else if (checkedId == R.id.radio0) {
          opBanos = "No contestó";
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
          opRegadera = "No contestó";
        } else {
          opRegadera = "";

        }

      }
    });

//    rdPreguntaInternet.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//        @Override
//        public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//
//            if (checkedId == R.id.radio1) {
//                opInternet="Si";
//
//            }
//            else if (checkedId == R.id.radio2) {
//                opInternet="No";
//            }
//            else if (checkedId == R.id.radio0) {
//                opInternet="No sabe / no contestó";
//            }
//            else{
//                opInternet="";
//
//            }
//
//
//        }
//    });
//
//
//
//    rdPreguntaTrabajaron.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//        @Override
//        public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//
//            if (checkedId == R.id.radio1) {
//                opTrabajaron="Ninguno";
//
//            }
//            else if (checkedId == R.id.radio2) {
//                opTrabajaron="Uno";
//            }
//            else if (checkedId == R.id.radio3) {
//                opTrabajaron="Dos";
//            }
//            else if (checkedId == R.id.radio4) {
//                opTrabajaron="Tres";
//            }
//            else if (checkedId == R.id.radio5) {
//                opTrabajaron="Cuatro o más";
//            }
//            else if (checkedId == R.id.radio0) {
//                opTrabajaron="No sabe / no contestó";
//            }
//            else{
//                opTrabajaron="";
//
//            }
//
//
//        }
//    });


    rdPreguntaEstufa.setOnCheckedChangeListener(new OnCheckedChangeListener() {

      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opEstufa = "Si";

        } else if (checkedId == R.id.radio2) {
          opEstufa = "No";
        } else if (checkedId == R.id.radio0) {
          opEstufa = "No contestó";
        } else {
          opEstufa = "";

        }

      }
    });

    rdPreguntaEdad.setOnCheckedChangeListener(new OnCheckedChangeListener() {

      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio1) {
          opEdad = "Menor de 18 años";
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
          opEdad = "70 o más";
        } else if (checkedId == R.id.radio0) {
          opEdad = "No contestó";
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
          opTipoVivienda = "No contestó";
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
          opTipoPiso = "No contestó";
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







    String strText8;
    if(spinner8.getSelectedItem().toString().equals("Selecciona")){
      strText8=op8;
    }else{
      strText8=spinner8.getSelectedItem().toString();
      rdPregunta8.clearCheck();
    }















    String strTextc8;
    if (editPreguntac8.getText().toString().trim().length() == 0) {
      strTextc8 = opc8;
    } else {
      strTextc8 = editPreguntac8.getText().toString().trim();
      rdPreguntac8.clearCheck();
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
    String strc5 = opc5;
    String strc6 = opc6;
    String strc7 = "pregunta";
    String strc7a = opc7a;
    String strc7b = opc7b;
    String strc7c = opc7c;
    String strc7d = opc7d;
    String strc8 = strTextc8;
    String strc9 = opc9;
    String strc10 = opc10;
    String strc11 = opc11;
    String strc11a = opc11a;
    String strc11b = opc11b;
    String strc12 = opc12;
    String strc12a = opc12a;
    String strc13 = opc13;
    String strc14 = opc14;


    String strAporta        = opAporta;
    String strOcupacion     = opOcupacion;
    String strCuantosCoches = opCuantosCoches;
    String strCuartos       = opCuartos;
    String strCuartosDormir = opCuartosDormir;
    String strFocos         = opFocos;
    String strBanos         = opBanos;
    String strRegadera      = opRegadera;
    String strEstufa        = opEstufa;
    String strEdad          = opEdad;
    String strGenero        = opGenero;
    String strTipoVivienda  = opTipoVivienda;
    String strTipoPiso      = opTipoPiso;
    String strAbandono      = opAbandono;

    if (strAbandono.matches("1")) {
      tipoEncuesta = "NORMAL";
    }

    String strEstudios     = opAporta;
    String strCocheCuantos = opCuantosCoches;
    String strFoco         = opFocos;
    String strCuarto       = opCuartos;
    String strBano         = opBanos;
    String strRega         = opRegadera;
    String strEstu         = opEstufa;
    String strPiso         = opTipoPiso;

// estudios
    if (strEstudios.matches("sin datos")) {
      strEstudios = "0";
    } else if (strEstudios.matches("No completó primaria")) {
      strEstudios = "0";
    } else if (strEstudios.matches("Primaria o secundaria")) {
      strEstudios = "22";
    } else if (strEstudios.matches("Preparatoria o carrera técnica")) {
      strEstudios = "38";
    } else if (strEstudios.matches("Licenciatura")) {
      strEstudios = "52";
    } else if (strEstudios.matches("Posgrado")) {
      strEstudios = "72";
    } else if (strEstudios.matches("No contestó")) {
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
    } else if (strCocheCuantos.matches("Cuatro o más")) {
      strCocheCuantos = "58";
    } else if (strCocheCuantos.matches("No aplica")) {
      strCocheCuantos = "0";
    } else if (strCocheCuantos.matches("No contestó")) {
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
    } else if (strFoco.matches("21 o más")) {
      strFoco = "46";
    } else if (strFoco.matches("No contestó")) {
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
    } else if (strCuarto.matches("No contestó")) {
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
    } else if (strBano.matches("Cuatro o más")) {
      strBano = "48";
    } else if (strBano.matches("No contestó")) {
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
    } else if (strRega.matches("No contestó")) {
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
    } else if (strEstu.matches("No contestó")) {
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
    } else if (strPiso.matches("No contestó")) {
      strPiso = "0";
    } else if (strPiso.matches("No aplica")) {
      strPiso = "0";
    }else if (strPiso.matches("No sabe / No contestó")) {
      strPiso = "0";
    }

    Integer estudios = Integer.valueOf(strEstudios);
    Integer coches   = Integer.valueOf(strCocheCuantos);
    Integer focos    = Integer.valueOf(strFoco);
    Integer cuartos  = Integer.valueOf(strCuarto);
    Integer banos    = Integer.valueOf(strBano);
    Integer regadera = Integer.valueOf(strRega);
    Integer estufa   = Integer.valueOf(strEstu);
    Integer piso     = Integer.valueOf(strPiso);

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
      long consecutivoObtenido = 0;
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
        values.put("pregunta_1",str1);
        values.put("pregunta_2",str2);
        values.put("pregunta_3",str3);
        values.put("pregunta_4",str4);
        values.put("pregunta_5",str5);
        values.put("pregunta_6",str6);
        values.put("pregunta_7",str7);
        values.put("pregunta_8",str8);
        values.put("pregunta_9",str9);
        values.put("pregunta_c1",strc1);
        values.put("pregunta_c2",strc2);
        values.put("pregunta_c2a",strc2a);
        values.put("pregunta_c2b",strc2b);
        values.put("pregunta_c2c",strc2c);
        values.put("pregunta_c3",strc3);
        values.put("pregunta_c4",strc4);
        values.put("pregunta_c5",strc5);
        values.put("pregunta_c6",strc6);
        values.put("pregunta_c7",strc7);
        values.put("pregunta_c7a",strc7a);
        values.put("pregunta_c7b",strc7b);
        values.put("pregunta_c7c",strc7c);
        values.put("pregunta_c7d",strc7d);
        values.put("pregunta_c8",strc8);
        values.put("pregunta_c9",strc9);
        values.put("pregunta_c10",strc10);
        values.put("pregunta_c11",strc11);
        values.put("pregunta_c11a",strc11a);
        values.put("pregunta_c11b",strc11b);
        values.put("pregunta_c12",strc12);
        values.put("pregunta_c12a",strc12a);
        values.put("pregunta_c13",strc13);
        values.put("pregunta_c14",strc14);
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
          Toast.makeText(getBaseContext(),"Sin conexión",Toast.LENGTH_LONG).show();
          values.put("enviado", "0");
          db.insert("encuestas", null, values);
        }else{
          values.put("enviado", "1");
          consecutivoObtenido = db.insert("encuestas", null, values);
        }
      }
      db.close();

      values.put("consecutivo", consecutivoObtenido);

      guardaEncuestaWS(values);


      System.out.println("consecutivo_diario "+ elMaximo);
      System.out.println("usuario "           + cachaNombre().toUpperCase());
      System.out.println("nombre_encuesta "   + nombreE.toUpperCase());
      System.out.println("fecha "             + formattedDate1);
      System.out.println("hora "              + formattedDate5);
      System.out.println("imei "              + sacaImei());
      System.out.println("Seccion "           + str);
      System.out.println("Latitud  "          + strLatitud);
      System.out.println("Longitud  "         + strLongitud);
      System.out.println("pregunta_1  " +   str1);
      System.out.println("pregunta_2  " +   str2);
      System.out.println("pregunta_3  " +   str3);
      System.out.println("pregunta_4  " +   str4);
      System.out.println("pregunta_5  " +   str5);
      System.out.println("pregunta_6  " +   str6);
      System.out.println("pregunta_7  " +   str7);
      System.out.println("pregunta_8  " +   str8);
      System.out.println("pregunta_9  " +   str9);
      System.out.println("pregunta_c1  " +   strc1);
      System.out.println("pregunta_c2  " +   strc2);
      System.out.println("pregunta_c2a  " +   strc2a);
      System.out.println("pregunta_c2b  " +   strc2b);
      System.out.println("pregunta_c2c  " +   strc2c);
      System.out.println("pregunta_c3  " +   strc3);
      System.out.println("pregunta_c4  " +   strc4);
      System.out.println("pregunta_c5  " +   strc5);
      System.out.println("pregunta_c6  " +   strc6);
      System.out.println("pregunta_c7  " +   strc7);
      System.out.println("pregunta_c7a  " +   strc7a);
      System.out.println("pregunta_c7b  " +   strc7b);
      System.out.println("pregunta_c7c  " +   strc7c);
      System.out.println("pregunta_c7d  " +   strc7d);
      System.out.println("pregunta_c8  " +   strc8);
      System.out.println("pregunta_c9  " +   strc9);
      System.out.println("pregunta_c10  " +   strc10);
      System.out.println("pregunta_c11  " +   strc11);
      System.out.println("pregunta_c11a  " +   strc11a);
      System.out.println("pregunta_c11b  " +   strc11b);
      System.out.println("pregunta_c12  " +   strc12);
      System.out.println("pregunta_c12a  " +   strc12a);
      System.out.println("pregunta_c13  " +   strc13);
      System.out.println("pregunta_c14  " +   strc14);


      System.out.println(" aporta   "        + strAporta);
      System.out.println(" ocupacion   "     + strOcupacion);
      System.out.println(" cuantos_coches   "+ strCuantosCoches);
      System.out.println(" cuartos   "       + strCuartos);
      System.out.println(" cuartos_dormir   "+ strCuartosDormir);
      System.out.println(" focos   "         + strFocos);

      System.out.println(" baños   "         + strBanos);
      System.out.println(" regadera   "      + strRegadera);
      System.out.println(" estufa   "        + strEstufa);
      System.out.println(" edad   "          + strEdad);
      System.out.println(" genero   "        + strGenero);
      System.out.println(" tipo_vivienda   " + strTipoVivienda);
      System.out.println(" tipo_piso   "     + strTipoPiso);

      System.out.println("abandono  "        + strAbandono);

      System.out.println("suma  "            + suma);
      System.out.println("status  "          + status);

// FIN INSERTA BASE DE DATOS //

    } catch (Exception e) {
      System.out.println("algo pasó...1");
    }

  }

  private void guardaEncuestaWS(ContentValues values){

    showProgress(true);

//RECORRE CONTENTVALUES
    DatoContent datoContent = new DatoContent();
    List<DatoContent> listaContenido = new ArrayList();
    Set<Map.Entry<String, Object>> s=values.valueSet();
    Iterator itr = s.iterator();
    while(itr.hasNext()) {
      Map.Entry me = (Map.Entry)itr.next();
      String key = me.getKey().toString();
      Object value =  me.getValue();

      datoContent = new DatoContent();
      datoContent.setKey(key);
      datoContent.setValue(String.valueOf(value));

      listaContenido.add(datoContent);
    }

    Gson gson  = new Gson();
    Type collectionType = new TypeToken<List<DatoContent>>() { }.getType();
    String json = gson.toJson(listaContenido,collectionType);

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


           if (lay1.getVisibility() == View.VISIBLE && op1.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura1,Toast.LENGTH_LONG).show();}
      else if (lay2.getVisibility() == View.VISIBLE && op2.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura2,Toast.LENGTH_LONG).show();}
      else if (lay3.getVisibility() == View.VISIBLE && op3.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura3,Toast.LENGTH_LONG).show();}
      else if (lay4.getVisibility() == View.VISIBLE && op4.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura4,Toast.LENGTH_LONG).show();}
      else if (lay5.getVisibility() == View.VISIBLE && op5.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura5,Toast.LENGTH_LONG).show();}
      else if (lay6.getVisibility() == View.VISIBLE && op6.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura6,Toast.LENGTH_LONG).show();}
      else if (lay7.getVisibility() == View.VISIBLE && op7.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura7,Toast.LENGTH_LONG).show();}
      else if (lay8.getVisibility() == View.VISIBLE && op8.matches("sin datos") && spinner8.getSelectedItem().toString().equals("Selecciona")) {Toast.makeText(getBaseContext(), "CAPTURA:  " + captura8, Toast.LENGTH_LONG).show();}
      else if (lay9.getVisibility() == View.VISIBLE && op9.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura9,Toast.LENGTH_LONG).show();}
      else if (layc1.getVisibility() == View.VISIBLE && opc1.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac1,Toast.LENGTH_LONG).show();}
//      else if (layc2.getVisibility() == View.VISIBLE && opc2.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac2,Toast.LENGTH_LONG).show();}
      else if (layc2a.getVisibility() == View.VISIBLE && opc2a.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac2a,Toast.LENGTH_LONG).show();}
      else if (layc2b.getVisibility() == View.VISIBLE && opc2b.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac2b,Toast.LENGTH_LONG).show();}
      else if (layc2c.getVisibility() == View.VISIBLE && opc2c.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac2c,Toast.LENGTH_LONG).show();}
      else if (layc3.getVisibility() == View.VISIBLE && opc3.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac3,Toast.LENGTH_LONG).show();}
      else if (layc4.getVisibility() == View.VISIBLE && opc4.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac4,Toast.LENGTH_LONG).show();}
      else if (layc5.getVisibility() == View.VISIBLE && opc5.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac5,Toast.LENGTH_LONG).show();}
      else if (layc6.getVisibility() == View.VISIBLE && opc6.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac6,Toast.LENGTH_LONG).show();}
//      else if (layc7.getVisibility() == View.VISIBLE && opc7.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac7,Toast.LENGTH_LONG).show();}
      else if (layc7a.getVisibility() == View.VISIBLE && opc7a.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac7a,Toast.LENGTH_LONG).show();}
      else if (layc7b.getVisibility() == View.VISIBLE && opc7b.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac7b,Toast.LENGTH_LONG).show();}
      else if (layc7c.getVisibility() == View.VISIBLE && opc7c.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac7c,Toast.LENGTH_LONG).show();}
      else if (layc7d.getVisibility() == View.VISIBLE && opc7d.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac7d,Toast.LENGTH_LONG).show();}
      else if (layc8.getVisibility() == View.VISIBLE && opc8.matches("sin datos") && editPreguntac8.getText().toString().trim().length() == 0) {Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac8, Toast.LENGTH_LONG).show();}
      else if (layc9.getVisibility() == View.VISIBLE && opc9.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac9,Toast.LENGTH_LONG).show();}
      else if (layc10.getVisibility() == View.VISIBLE && opc10.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac10,Toast.LENGTH_LONG).show();}
      else if (layc11.getVisibility() == View.VISIBLE && opc11.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac11,Toast.LENGTH_LONG).show();}
      else if (layc11a.getVisibility() == View.VISIBLE && opc11a.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac11a,Toast.LENGTH_LONG).show();}
      else if (layc11b.getVisibility() == View.VISIBLE && opc11b.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac11b,Toast.LENGTH_LONG).show();}
      else if (layc12.getVisibility() == View.VISIBLE && opc12.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac12,Toast.LENGTH_LONG).show();}
      else if (layc12a.getVisibility() == View.VISIBLE && opc12a.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac12a,Toast.LENGTH_LONG).show();}
      else if (layc13.getVisibility() == View.VISIBLE && opc13.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac13,Toast.LENGTH_LONG).show();}
      else if (layc14.getVisibility() == View.VISIBLE && opc14.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac14,Toast.LENGTH_LONG).show();}

      else if (opAporta.matches("sin datos")) {Toast.makeText(getBaseContext(), "CAPTURA         :  " + capturaAporta, Toast.LENGTH_LONG).show();
      } else if (opOcupacion.matches("sin datos")) {Toast.makeText(getBaseContext(), "CAPTURA    :  " + capturaOcupacion, Toast.LENGTH_LONG).show();
      } else if (layCuantosCoches.getVisibility()                                                == View.VISIBLE && opCuantosCoches.matches("sin datos")) {Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaCuantosCoches, Toast.LENGTH_LONG).show();
      } else if (opCuartos.matches("sin datos")) {Toast.makeText(getBaseContext(), "CAPTURA      :  " + capturaCuartos, Toast.LENGTH_LONG).show();
      } else if (opCuartosDormir.matches("sin datos")) {Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaCuartosDormir, Toast.LENGTH_LONG).show();
      } else if (opFocos.matches("sin datos")) {Toast.makeText(getBaseContext(), "CAPTURA        :  " + capturaFocos, Toast.LENGTH_LONG).show();
      } else if (opBanos.matches("sin datos")) {Toast.makeText(getBaseContext(), "CAPTURA        :  " + capturaBanos, Toast.LENGTH_LONG).show();
      } else if (opEstufa.matches("sin datos")) {Toast.makeText(getBaseContext(), "CAPTURA       :  " + capturaEstufa, Toast.LENGTH_LONG).show();
      } else if (opEdad.matches("sin datos")) {Toast.makeText(getBaseContext(), "CAPTURA         :  " + capturaEdad, Toast.LENGTH_LONG).show();
      } else if (opGenero.matches("sin datos")) {Toast.makeText(getBaseContext(), "CAPTURA       :  " + capturaGenero, Toast.LENGTH_LONG).show();
      } else if (opTipoVivienda.matches("sin datos")) {Toast.makeText(getBaseContext(), "CAPTURA :  " + capturaTipoVivienda, Toast.LENGTH_LONG).show();
      } else if (opTipoPiso.matches("sin datos")) {Toast.makeText(getBaseContext(), "CAPTURA     :  " + capturaTipoPiso, Toast.LENGTH_LONG).show();
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
        }
        else {
          valores();
          btnGuardar.setEnabled(false);
//                dialogo();
        }

        break;

      case 3:

        if (opGenero.matches("sin datos")) {
          Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaGenero, Toast.LENGTH_LONG).show();
        }
        else {
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

//  public void CargaSpinnerMeses() {
//    String var = "Selecciona";
//    if (var == null) {
//      var = "";
//    }
//    final String[] datos = new String[] { "" + var + "",
//        "Enero 2019",
//        "Febrero 2019",
//        "Marzo 2019",
//        "Abril 2019",
//        "Mayo 2019",
//        "Junio 2019",
//        "Julio 2019",
//        "Agosto 2019",
//        "Septiembre 2019",
//        "Octubre 2019",
//        "Noviembre 2019",
//        "Diciembre 2019",
//        "Enero 2020",
//        "Febrero 20"
//    };
//    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
//    adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//    spinnerMeses.setAdapter(adaptador);
//    spinnerMeses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//      public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
//        rdPregunta42.clearCheck();
//
//      }
//
//      public void onNothingSelected(AdapterView<?> parent) {
//      }
//    });
//  }

  public void CargaSpinnerEscala() {
    String var = "Selecciona";
    if (var == null) {
      var = "";
    }
    final String[] datos = new String[]{"" + var + "", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "No sabe / No contestó"};
    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item,
            datos);
    adaptador.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
    spinner8.setAdapter(adaptador);
    spinner8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {

      }

      public void onNothingSelected(AdapterView<?> parent) {
      }
    });
  }

  public void CargaSpinnerc3a() {
    String var = "Selecciona";
    if (var == null) {
      var = "";
    }
    final String[] datos = new String[]{"" + var + "", "1", "2", "3", "4", "5", "6", "7","No sabe / No contestó"};
    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item,
            datos);
    adaptador.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
    spinnerc3a.setAdapter(adaptador);
    spinnerc3a.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
//
//                rdPreguntac3a.clearCheck();
//
//                String dia=spinnerc3a.getSelectedItem().toString();
//
//                if(dia.equals("Selecciona")){
//                    layc3b .setVisibility(View.GONE); rdPreguntac3b.clearCheck();
//                }else{
//                    layc3b .setVisibility(View.VISIBLE);  rdPreguntac3b.clearCheck();
//
//                }
//


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
            "Se presenta plan de acción para rescatar PEMEX", "Se harán subastas de joyas incautadas",
            "Derrame de ácido en el Mar de Cortés",
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
            "XOCHIMILCO", "No sabe / No contestó"};
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


//public void CargaSpinner9() {
//    String var = "Selecciona";
//    if (var == null) {
//        var = "";
//    }
//    final String[] datos = new String[]{"" + var + "",
//    "1", "2", "3",
//    "4", "5", "6", "7", "8", "9",
//    "10"
//};
//ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
//adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//spinner9.setAdapter(adaptador);
//spinner9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//    public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
//
//        rdPregunta9.clearCheck();
//        op9 = "sin datos";
//    }
//
//    public void onNothingSelected(AdapterView<?> parent) {
//    }
//});
//}

//
//public void CargaSpinner0() {
//    String var = "Selecciona";
//    if (var == null) {
//        var = "";
//    }
//    final String[] datos = new String[]{"" + var + "", "ALVARO OBREGON", "AZCAPOTZALCO", "BENITO JUAREZ",
//    "COYOACAN", "CUAJIMALPA DE MORELOS", "CUAUHTEMOC", "GUSTAVO A. MADERO", "IZTACALCO", "IZTAPALAPA",
//    "MAGDALENA CONTRERAS", "MIGUEL HIDALGO", "MILPA ALTA", "TLAHUAC", "TLALPAN", "VENUSTIANO CARRANZA",
//    "XOCHIMILCO"};
//    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
//    adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//    spinner0.setAdapter(adaptador);
//    spinner0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//        public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
//
//
//            rdPregunta0.clearCheck();
//            op0 = "sin datos";
//
//            radio_abandono1.setVisibility(View.VISIBLE);
//            radio_abandono2.setVisibility(View.VISIBLE);
//            radio_abandono3.setVisibility(View.VISIBLE);
//            radio_abandono4.setVisibility(View.GONE);
//            radio_abandono4.setChecked(false);
//            radio_abandono1.setChecked(true);
//
//            lay1 .setVisibility(View.VISIBLE);
//            lay2 .setVisibility(View.VISIBLE);
//            lay3 .setVisibility(View.VISIBLE);
//            lay4 .setVisibility(View.VISIBLE);
//            lay5 .setVisibility(View.VISIBLE);
//            lay6 .setVisibility(View.VISIBLE);
//            lay7 .setVisibility(View.VISIBLE);
//            layc1 .setVisibility(View.VISIBLE);
//            layc2 .setVisibility(View.VISIBLE);
//            layc3 .setVisibility(View.VISIBLE);
//            layc4 .setVisibility(View.VISIBLE);
//            layc5 .setVisibility(View.VISIBLE);
//            layc6 .setVisibility(View.VISIBLE);
//            layc6a .setVisibility(View.VISIBLE);
//            layc6b .setVisibility(View.VISIBLE);
//            layc7 .setVisibility(View.VISIBLE);
//            layc7a .setVisibility(View.VISIBLE);
//            layc8 .setVisibility(View.VISIBLE);
//            layc8a .setVisibility(View.VISIBLE);
//            layc9 .setVisibility(View.VISIBLE);
//            layc9a .setVisibility(View.VISIBLE);
//            layc10 .setVisibility(View.VISIBLE);
//            layc10a .setVisibility(View.VISIBLE);
//            layc11 .setVisibility(View.VISIBLE);
//            layc11a .setVisibility(View.VISIBLE);
//            layc12 .setVisibility(View.VISIBLE);
//            layc12a .setVisibility(View.VISIBLE);
//            layc13 .setVisibility(View.VISIBLE);
//            layc13a .setVisibility(View.VISIBLE);
//
//            laySocioE.setVisibility(View.VISIBLE);
//            layEst.setVisibility(View.VISIBLE);
//            layAporta.setVisibility(View.VISIBLE);
//            layOcupacion.setVisibility(View.VISIBLE);
//            layCuantosCoches.setVisibility(View.VISIBLE);
//            layCuartos.setVisibility(View.VISIBLE);
//            layCuartosDormir.setVisibility(View.VISIBLE);
//            layFocos.setVisibility(View.VISIBLE);
//            layBanos.setVisibility(View.VISIBLE);
//            layRegadera.setVisibility(View.VISIBLE);
//            layEstufa.setVisibility(View.VISIBLE);
//            layEdad.setVisibility(View.VISIBLE);
//            layTipoPiso.setVisibility(View.VISIBLE);
//            layTipoVivienda.setVisibility(View.VISIBLE);
//            layGenero.setVisibility(View.VISIBLE);
//
//        }
//
//        public void onNothingSelected(AdapterView<?> parent) {
//        }
//    });
//}

//  public void CargaSpinner63() {
//    String var = "Selecciona";
//    if (var == null) {
//      var = "";
//    }
//    final String[] datos = new String[] { "" + var + "", "ÁLVARO OBREGÓN", "AZCAPOTZALCO", "BENITO JUÁREZ",
//        "COYOACÁN", "CUAJIMALPA DE MORELOS", "CUAUHTÉMOC", "GUSTAVO A. MADERO", "IZTACALCO", "IZTAPALAPA",
//        "MAGDALENA CONTRERAS", "MIGUEL HIDALGO", "MILPA ALTA", "TLÁHUAC", "TLALPAN", "VENUSTIANO CARRANZA",
//        "XOCHIMILCO" };
//    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
//    adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//    spinner63.setAdapter(adaptador);
//    spinner63.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//      public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
//
//        rdPregunta63.clearCheck();
//
//      }
//
//      public void onNothingSelected(AdapterView<?> parent) {
//      }
//    });
//  }

///////////// FIN SPINNER /////////////

  private String sacaDelegacion(String seccion) {
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



