package mx.gob.cdmx.federal10_20210217;

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
import android.graphics.Color;
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
import mx.gob.cdmx.federal10_20210217.model.DatoContent;
import mx.gob.cdmx.federal10_20210217.model.Usuarios;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;
import static mx.gob.cdmx.federal10_20210217.Nombre.USUARIO;
import static mx.gob.cdmx.federal10_20210217.Nombre.customURL;
import static mx.gob.cdmx.federal10_20210217.Nombre.encuesta;

public class MainActivityPantalla1 extends Activity implements AdapterView.OnItemClickListener {


  private static final String LOG_TAG = "Grabadora";
  private static final String TAG = "Pantalla1";
  private View mProgressView;
  private Usuarios usuario;

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

  Random random = new Random();
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

  private Spinner spinner0;

  Timer timer;

  public String laDelegacion;
  public EditText txtSeccion;
  public String Alcalde;
  public String id_alcaldia;

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

  
public String op1="sin datos";   public RadioGroup rdPregunta1;   public EditText editPregunta1;   public String captura1;  LinearLayout lay1;  private Spinner spinner1;
private TextView textPregunta1;
public String op2="sin datos";   public RadioGroup rdPregunta2;   public EditText editPregunta2;   public String captura2;  LinearLayout lay2;  private Spinner spinner2;
private TextView textPregunta2;
public String op3="sin datos";   public RadioGroup rdPregunta3;   public EditText editPregunta3;   public String captura3;  LinearLayout lay3;  private Spinner spinner3;
private TextView textPregunta3;
public String op4="sin datos";   public RadioGroup rdPregunta4;   public EditText editPregunta4;   public String captura4;  LinearLayout lay4;  private Spinner spinner4;
private TextView textPregunta4;
public String op5="sin datos";   public RadioGroup rdPregunta5;   public EditText editPregunta5;   public String captura5;  LinearLayout lay5;  private Spinner spinner5;
private TextView textPregunta5;
public String op6="sin datos";   public RadioGroup rdPregunta6;   public EditText editPregunta6;   public String captura6;  LinearLayout lay6;  private Spinner spinner6;
private TextView textPregunta6;
public String op7="sin datos";   public RadioGroup rdPregunta7;   public EditText editPregunta7;   public String captura7;  LinearLayout lay7;  private Spinner spinner7;
private TextView textPregunta7;
public String opcovi="sin datos";   public RadioGroup rdPreguntacovi;   public EditText editPreguntacovi;   public String capturacovi;  LinearLayout laycovi;  private Spinner spinnercovi;
private TextView textPreguntacovi;
public String opcovi1="sin datos";   public RadioGroup rdPreguntacovi1;   public EditText editPreguntacovi1;   public String capturacovi1;  LinearLayout laycovi1;  private Spinner spinnercovi1;
private TextView textPreguntacovi1;
public String opcovi2="sin datos";   public RadioGroup rdPreguntacovi2;   public EditText editPreguntacovi2;   public String capturacovi2;  LinearLayout laycovi2;  private Spinner spinnercovi2;
private TextView textPreguntacovi2;
public String opcovi3="sin datos";   public RadioGroup rdPreguntacovi3;   public EditText editPreguntacovi3;   public String capturacovi3;  LinearLayout laycovi3;  private Spinner spinnercovi3;
private TextView textPreguntacovi3;
public String opcovi4="sin datos";   public RadioGroup rdPreguntacovi4;   public EditText editPreguntacovi4;   public String capturacovi4;  LinearLayout laycovi4;  private Spinner spinnercovi4;
private TextView textPreguntacovi4;
public String op8="sin datos";   public RadioGroup rdPregunta8;   public EditText editPregunta8;   public String captura8;  LinearLayout lay8;  private Spinner spinner8;
private TextView textPregunta8;
public String op9="sin datos";   public RadioGroup rdPregunta9;   public EditText editPregunta9;   public String captura9;  LinearLayout lay9;  private Spinner spinner9;
private TextView textPregunta9;
public String op10="sin datos";   public RadioGroup rdPregunta10;   public EditText editPregunta10;   public String captura10;  LinearLayout lay10;  private Spinner spinner10;
private TextView textPregunta10;
public String op11="sin datos";   public RadioGroup rdPregunta11;   public EditText editPregunta11;   public String captura11;  LinearLayout lay11;  private Spinner spinner11;
private TextView textPregunta11;
public String op12="sin datos";   public RadioGroup rdPregunta12;   public EditText editPregunta12;   public String captura12;  LinearLayout lay12;  private Spinner spinner12;
private TextView textPregunta12;
public String op13="sin datos";   public RadioGroup rdPregunta13;   public EditText editPregunta13;   public String captura13;  LinearLayout lay13;  private Spinner spinner13;
private TextView textPregunta13;
public String op14="sin datos";   public RadioGroup rdPregunta14;   public EditText editPregunta14;   public String captura14;  LinearLayout lay14;  private Spinner spinner14;
private TextView textPregunta14;
public String op15="sin datos";   public RadioGroup rdPregunta15;   public EditText editPregunta15;   public String captura15;  LinearLayout lay15;  private Spinner spinner15;
private TextView textPregunta15;
public String op16="sin datos";   public RadioGroup rdPregunta16;   public EditText editPregunta16;   public String captura16;  LinearLayout lay16;  private Spinner spinner16;
private TextView textPregunta16;
public String op16a="sin datos";   public RadioGroup rdPregunta16a;   public EditText editPregunta16a;   public String captura16a;  LinearLayout lay16a;  private Spinner spinner16a;
private TextView textPregunta16a;
public String op16b="sin datos";   public RadioGroup rdPregunta16b;   public EditText editPregunta16b;   public String captura16b;  LinearLayout lay16b;  private Spinner spinner16b;
private TextView textPregunta16b;
public String op17="sin datos";   public RadioGroup rdPregunta17;   public EditText editPregunta17;   public String captura17;  LinearLayout lay17;  private Spinner spinner17;
private TextView textPregunta17;
public String op18="sin datos";   public RadioGroup rdPregunta18;   public EditText editPregunta18;   public String captura18;  LinearLayout lay18;  private Spinner spinner18;
private TextView textPregunta18;
public String op19="sin datos";   public RadioGroup rdPregunta19;   public EditText editPregunta19;   public String captura19;  LinearLayout lay19;  private Spinner spinner19;
private TextView textPregunta19;
public String op19a="sin datos";   public RadioGroup rdPregunta19a;   public EditText editPregunta19a;   public String captura19a;  LinearLayout lay19a;  private Spinner spinner19a;
private TextView textPregunta19a;
public String op19b="sin datos";   public RadioGroup rdPregunta19b;   public EditText editPregunta19b;   public String captura19b;  LinearLayout lay19b;  private Spinner spinner19b;
private TextView textPregunta19b;
public String op20="sin datos";   public RadioGroup rdPregunta20;   public EditText editPregunta20;   public String captura20;  LinearLayout lay20;  private Spinner spinner20;
private TextView textPregunta20;
public String op21="sin datos";   public RadioGroup rdPregunta21;   public EditText editPregunta21;   public String captura21;  LinearLayout lay21;  private Spinner spinner21;
private TextView textPregunta21;
public String op22="sin datos";   public RadioGroup rdPregunta22;   public EditText editPregunta22;   public String captura22;  LinearLayout lay22;  private Spinner spinner22;
private TextView textPregunta22;
public String op23a="sin datos";   public RadioGroup rdPregunta23a;   public EditText editPregunta23a;   public String captura23a;  LinearLayout lay23a;  private Spinner spinner23a;
private TextView textPregunta23a;
public String op23b="sin datos";   public RadioGroup rdPregunta23b;   public EditText editPregunta23b;   public String captura23b;  LinearLayout lay23b;  private Spinner spinner23b;
private TextView textPregunta23b;
public String op23c="sin datos";   public RadioGroup rdPregunta23c;   public EditText editPregunta23c;   public String captura23c;  LinearLayout lay23c;  private Spinner spinner23c;
private TextView textPregunta23c;
public String op24="sin datos";   public RadioGroup rdPregunta24;   public EditText editPregunta24;   public String captura24;  LinearLayout lay24;  private Spinner spinner24;
private TextView textPregunta24;
public String op24_1="sin datos";   public RadioGroup rdPregunta24_1;   public EditText editPregunta24_1;   public String captura24_1;  LinearLayout lay24_1;  private Spinner spinner24_1;
private TextView textPregunta24_1;
public String op24_1a="sin datos";   public RadioGroup rdPregunta24_1a;   public EditText editPregunta24_1a;   public String captura24_1a;  LinearLayout lay24_1a;  private Spinner spinner24_1a;
private TextView textPregunta24_1a;
public String op24_1b="sin datos";   public RadioGroup rdPregunta24_1b;   public EditText editPregunta24_1b;   public String captura24_1b;  LinearLayout lay24_1b;  private Spinner spinner24_1b;
private TextView textPregunta24_1b;
public String op24_2="sin datos";   public RadioGroup rdPregunta24_2;   public EditText editPregunta24_2;   public String captura24_2;  LinearLayout lay24_2;  private Spinner spinner24_2;
private TextView textPregunta24_2;
public String op24_2a="sin datos";   public RadioGroup rdPregunta24_2a;   public EditText editPregunta24_2a;   public String captura24_2a;  LinearLayout lay24_2a;  private Spinner spinner24_2a;
private TextView textPregunta24_2a;
public String op24_2b="sin datos";   public RadioGroup rdPregunta24_2b;   public EditText editPregunta24_2b;   public String captura24_2b;  LinearLayout lay24_2b;  private Spinner spinner24_2b;
private TextView textPregunta24_2b;
public String op24_3="sin datos";   public RadioGroup rdPregunta24_3;   public EditText editPregunta24_3;   public String captura24_3;  LinearLayout lay24_3;  private Spinner spinner24_3;
private TextView textPregunta24_3;
public String op24_3a="sin datos";   public RadioGroup rdPregunta24_3a;   public EditText editPregunta24_3a;   public String captura24_3a;  LinearLayout lay24_3a;  private Spinner spinner24_3a;
private TextView textPregunta24_3a;
public String op24_3b="sin datos";   public RadioGroup rdPregunta24_3b;   public EditText editPregunta24_3b;   public String captura24_3b;  LinearLayout lay24_3b;  private Spinner spinner24_3b;
private TextView textPregunta24_3b;
public String op25="sin datos";   public RadioGroup rdPregunta25;   public EditText editPregunta25;   public String captura25;  LinearLayout lay25;  private Spinner spinner25;
private TextView textPregunta25;
public String op26="sin datos";   public RadioGroup rdPregunta26;   public EditText editPregunta26;   public String captura26;  LinearLayout lay26;  private Spinner spinner26;
private TextView textPregunta26;
public String op27="sin datos";   public RadioGroup rdPregunta27;   public EditText editPregunta27;   public String captura27;  LinearLayout lay27;  private Spinner spinner27;
private TextView textPregunta27;
public String op28="sin datos";   public RadioGroup rdPregunta28;   public EditText editPregunta28;   public String captura28;  LinearLayout lay28;  private Spinner spinner28;
private TextView textPregunta28;
public String op29="sin datos";   public RadioGroup rdPregunta29;   public EditText editPregunta29;   public String captura29;  LinearLayout lay29;  private Spinner spinner29;
private TextView textPregunta29;

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
int elMaximoConsecutivo;
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
//                    setContentView(R.layout.activity_pantalla1); // COMENTAR ESTA CUANDO ES ALEATORIO

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

// Carga las pantallas aleatoriamente
                      random = new Random();
//
                      /*DESCOMENTAR ESTAS 3 LINEAS CUANDO YA ESTA EL NUMERO DE HOJAS ALEATORIO */
        rand = random.nextInt(9);
        setContentView(mLinearLayoutIds[rand]);
        Log.i(null, "El aleatorio: " + rand); // si rand= 11 en el layoud corresponde a uno mas


                      /*activity_pantalla12*/

// Crea Log cuando falla la app
                      Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(MainActivityPantalla1.this,this));


                      Alcalde = Utils.getAlcaldes(sacaAlcaldia(cachaSeccion()));
                      id_alcaldia = String.valueOf(Utils.getIdAlcaldia(sacaAlcaldia(cachaSeccion())));

                      Log.i(TAG,"cqs -->> Alcalde: "+Alcalde);
                      Log.i(TAG,"cqs -->> id_alcaldia: "+id_alcaldia);

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
textPregunta1 = (TextView) findViewById(R.id.textPregunta1);
textPregunta2 = (TextView) findViewById(R.id.textPregunta2);
textPregunta3 = (TextView) findViewById(R.id.textPregunta3);
textPregunta4 = (TextView) findViewById(R.id.textPregunta4);
textPregunta5 = (TextView) findViewById(R.id.textPregunta5);
textPregunta6 = (TextView) findViewById(R.id.textPregunta6);
textPregunta7 = (TextView) findViewById(R.id.textPregunta7);
textPreguntacovi = (TextView) findViewById(R.id.textPreguntacovi);
textPreguntacovi1 = (TextView) findViewById(R.id.textPreguntacovi1);
textPreguntacovi2 = (TextView) findViewById(R.id.textPreguntacovi2);
textPreguntacovi3 = (TextView) findViewById(R.id.textPreguntacovi3);
textPreguntacovi4 = (TextView) findViewById(R.id.textPreguntacovi4);
textPregunta8 = (TextView) findViewById(R.id.textPregunta8);
textPregunta9 = (TextView) findViewById(R.id.textPregunta9);
textPregunta10 = (TextView) findViewById(R.id.textPregunta10);
textPregunta11 = (TextView) findViewById(R.id.textPregunta11);
textPregunta12 = (TextView) findViewById(R.id.textPregunta12);
textPregunta13 = (TextView) findViewById(R.id.textPregunta13);
textPregunta14 = (TextView) findViewById(R.id.textPregunta14);
textPregunta15 = (TextView) findViewById(R.id.textPregunta15);
textPregunta16 = (TextView) findViewById(R.id.textPregunta16);
textPregunta16a = (TextView) findViewById(R.id.textPregunta16a);
textPregunta16b = (TextView) findViewById(R.id.textPregunta16b);
textPregunta17 = (TextView) findViewById(R.id.textPregunta17);
textPregunta18 = (TextView) findViewById(R.id.textPregunta18);
textPregunta19 = (TextView) findViewById(R.id.textPregunta19);
textPregunta19a = (TextView) findViewById(R.id.textPregunta19a);
textPregunta19b = (TextView) findViewById(R.id.textPregunta19b);
textPregunta20 = (TextView) findViewById(R.id.textPregunta20);
textPregunta21 = (TextView) findViewById(R.id.textPregunta21);
textPregunta22 = (TextView) findViewById(R.id.textPregunta22);
textPregunta23a = (TextView) findViewById(R.id.textPregunta23a);
textPregunta23b = (TextView) findViewById(R.id.textPregunta23b);
textPregunta23c = (TextView) findViewById(R.id.textPregunta23c);
textPregunta24 = (TextView) findViewById(R.id.textPregunta24);
textPregunta24_1 = (TextView) findViewById(R.id.textPregunta24_1);
textPregunta24_1a = (TextView) findViewById(R.id.textPregunta24_1a);
textPregunta24_1b = (TextView) findViewById(R.id.textPregunta24_1b);
textPregunta24_2 = (TextView) findViewById(R.id.textPregunta24_2);
textPregunta24_2a = (TextView) findViewById(R.id.textPregunta24_2a);
textPregunta24_2b = (TextView) findViewById(R.id.textPregunta24_2b);
textPregunta24_3 = (TextView) findViewById(R.id.textPregunta24_3);
textPregunta24_3a = (TextView) findViewById(R.id.textPregunta24_3a);
textPregunta24_3b = (TextView) findViewById(R.id.textPregunta24_3b);
textPregunta25 = (TextView) findViewById(R.id.textPregunta25);
textPregunta26 = (TextView) findViewById(R.id.textPregunta26);
textPregunta27 = (TextView) findViewById(R.id.textPregunta27);
textPregunta28 = (TextView) findViewById(R.id.textPregunta28);
textPregunta29 = (TextView) findViewById(R.id.textPregunta29);

    textPreguntaAporta= (TextView) findViewById(R.id.textPreguntaAporta);
    textPreguntaOcupacion= (TextView) findViewById(R.id.textPreguntaOcupacion);
    textPreguntaCuantosCoches= (TextView) findViewById(R.id.textPreguntaCuantosCoches);
    textPreguntaCuartos= (TextView) findViewById(R.id.textPreguntaCuartos);
    textPreguntaCuartosDormir= (TextView) findViewById(R.id.textPreguntaCuartosDormir);
    textPreguntaFocos= (TextView) findViewById(R.id.textPreguntaFocos);
    textPreguntaBanos= (TextView) findViewById(R.id.textPreguntaBanos);
    textPreguntaRegadera= (TextView) findViewById(R.id.textPreguntaRegadera);
    textPreguntaEstufa= (TextView) findViewById(R.id.textPreguntaEstufa);
    textPreguntaEdad= (TextView) findViewById(R.id.textPreguntaEdad);
    textPreguntaTipoPiso= (TextView) findViewById(R.id.textPreguntaTipoPiso);
    textPreguntaTipoVivienda= (TextView) findViewById(R.id.textPreguntaTipoVivienda);
    textPreguntaGenero= (TextView) findViewById(R.id.textPreguntaGenero);

                            // justificar el texto
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                              textPreguntaEntrada.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta1.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta2.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta3.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta4.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta5.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta6.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta7.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPreguntacovi.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPreguntacovi1.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPreguntacovi2.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPreguntacovi3.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPreguntacovi4.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta8.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta9.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta10.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta11.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta12.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta13.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta14.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta15.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta16.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta16a.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta16b.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta17.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta18.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta19.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta19a.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta19b.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta20.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta21.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta22.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta23a.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta23b.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta23c.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta24.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta24_1.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta24_1a.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta24_1b.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta24_2.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta24_2a.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta24_2b.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta24_3.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta24_3a.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta24_3b.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta25.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta26.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta27.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta28.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
textPregunta29.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);


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

                            
rdPregunta1 = (RadioGroup)findViewById(R.id.rdPregunta1); captura1 =res.getString(R.string.PREGUNTA1);  lay1 = (LinearLayout) findViewById(R.id.lay1);
rdPregunta2 = (RadioGroup)findViewById(R.id.rdPregunta2); captura2 =res.getString(R.string.PREGUNTA2);  lay2 = (LinearLayout) findViewById(R.id.lay2);
rdPregunta3 = (RadioGroup)findViewById(R.id.rdPregunta3); captura3 =res.getString(R.string.PREGUNTA3);  lay3 = (LinearLayout) findViewById(R.id.lay3);
rdPregunta4 = (RadioGroup)findViewById(R.id.rdPregunta4); captura4 =res.getString(R.string.PREGUNTA4);  lay4 = (LinearLayout) findViewById(R.id.lay4);
rdPregunta5 = (RadioGroup)findViewById(R.id.rdPregunta5); captura5 =res.getString(R.string.PREGUNTA5);  lay5 = (LinearLayout) findViewById(R.id.lay5);
rdPregunta6 = (RadioGroup)findViewById(R.id.rdPregunta6); captura6 =res.getString(R.string.PREGUNTA6);  lay6 = (LinearLayout) findViewById(R.id.lay6);
rdPregunta7 = (RadioGroup)findViewById(R.id.rdPregunta7); captura7 =res.getString(R.string.PREGUNTA7);  lay7 = (LinearLayout) findViewById(R.id.lay7);
rdPreguntacovi = (RadioGroup)findViewById(R.id.rdPreguntacovi); capturacovi =res.getString(R.string.PREGUNTAcovi);  laycovi = (LinearLayout) findViewById(R.id.laycovi);
rdPreguntacovi1 = (RadioGroup)findViewById(R.id.rdPreguntacovi1); capturacovi1 =res.getString(R.string.PREGUNTAcovi1);  laycovi1 = (LinearLayout) findViewById(R.id.laycovi1);
rdPreguntacovi2 = (RadioGroup)findViewById(R.id.rdPreguntacovi2); capturacovi2 =res.getString(R.string.PREGUNTAcovi2);  laycovi2 = (LinearLayout) findViewById(R.id.laycovi2);
rdPreguntacovi3 = (RadioGroup)findViewById(R.id.rdPreguntacovi3); capturacovi3 =res.getString(R.string.PREGUNTAcovi3);  laycovi3 = (LinearLayout) findViewById(R.id.laycovi3);
rdPreguntacovi4 = (RadioGroup)findViewById(R.id.rdPreguntacovi4); capturacovi4 =res.getString(R.string.PREGUNTAcovi4);  laycovi4 = (LinearLayout) findViewById(R.id.laycovi4);
rdPregunta8 = (RadioGroup)findViewById(R.id.rdPregunta8); captura8 =res.getString(R.string.PREGUNTA8);  lay8 = (LinearLayout) findViewById(R.id.lay8);
rdPregunta9 = (RadioGroup)findViewById(R.id.rdPregunta9); captura9 =res.getString(R.string.PREGUNTA9);  lay9 = (LinearLayout) findViewById(R.id.lay9);
rdPregunta10 = (RadioGroup)findViewById(R.id.rdPregunta10); captura10 =res.getString(R.string.PREGUNTA10);  lay10 = (LinearLayout) findViewById(R.id.lay10);
rdPregunta11 = (RadioGroup)findViewById(R.id.rdPregunta11); captura11 =res.getString(R.string.PREGUNTA11);  lay11 = (LinearLayout) findViewById(R.id.lay11);
rdPregunta12 = (RadioGroup)findViewById(R.id.rdPregunta12); captura12 =res.getString(R.string.PREGUNTA12);  lay12 = (LinearLayout) findViewById(R.id.lay12);
rdPregunta13 = (RadioGroup)findViewById(R.id.rdPregunta13); captura13 =res.getString(R.string.PREGUNTA13);  lay13 = (LinearLayout) findViewById(R.id.lay13);
rdPregunta14 = (RadioGroup)findViewById(R.id.rdPregunta14); captura14 =res.getString(R.string.PREGUNTA14);  lay14 = (LinearLayout) findViewById(R.id.lay14);
rdPregunta15 = (RadioGroup)findViewById(R.id.rdPregunta15); captura15 =res.getString(R.string.PREGUNTA15);  lay15 = (LinearLayout) findViewById(R.id.lay15);
rdPregunta16 = (RadioGroup)findViewById(R.id.rdPregunta16); captura16 =res.getString(R.string.PREGUNTA16);  lay16 = (LinearLayout) findViewById(R.id.lay16);
rdPregunta16a = (RadioGroup)findViewById(R.id.rdPregunta16a); captura16a =res.getString(R.string.PREGUNTA16a);  lay16a = (LinearLayout) findViewById(R.id.lay16a);
rdPregunta16b = (RadioGroup)findViewById(R.id.rdPregunta16b); captura16b =res.getString(R.string.PREGUNTA16b);  lay16b = (LinearLayout) findViewById(R.id.lay16b);
rdPregunta17 = (RadioGroup)findViewById(R.id.rdPregunta17); captura17 =res.getString(R.string.PREGUNTA17);  lay17 = (LinearLayout) findViewById(R.id.lay17);
rdPregunta18 = (RadioGroup)findViewById(R.id.rdPregunta18); captura18 =res.getString(R.string.PREGUNTA18);  lay18 = (LinearLayout) findViewById(R.id.lay18);
rdPregunta19 = (RadioGroup)findViewById(R.id.rdPregunta19); captura19 =res.getString(R.string.PREGUNTA19);  lay19 = (LinearLayout) findViewById(R.id.lay19);
rdPregunta19a = (RadioGroup)findViewById(R.id.rdPregunta19a); captura19a =res.getString(R.string.PREGUNTA19a);  lay19a = (LinearLayout) findViewById(R.id.lay19a);
rdPregunta19b = (RadioGroup)findViewById(R.id.rdPregunta19b); captura19b =res.getString(R.string.PREGUNTA19b);  lay19b = (LinearLayout) findViewById(R.id.lay19b);
rdPregunta20 = (RadioGroup)findViewById(R.id.rdPregunta20); captura20 =res.getString(R.string.PREGUNTA20);  lay20 = (LinearLayout) findViewById(R.id.lay20);
rdPregunta21 = (RadioGroup)findViewById(R.id.rdPregunta21); captura21 =res.getString(R.string.PREGUNTA21);  lay21 = (LinearLayout) findViewById(R.id.lay21);
rdPregunta22 = (RadioGroup)findViewById(R.id.rdPregunta22); captura22 =res.getString(R.string.PREGUNTA22);  lay22 = (LinearLayout) findViewById(R.id.lay22);
rdPregunta23a = (RadioGroup)findViewById(R.id.rdPregunta23a); captura23a =res.getString(R.string.PREGUNTA23a);  lay23a = (LinearLayout) findViewById(R.id.lay23a);
rdPregunta23b = (RadioGroup)findViewById(R.id.rdPregunta23b); captura23b =res.getString(R.string.PREGUNTA23b);  lay23b = (LinearLayout) findViewById(R.id.lay23b);
rdPregunta23c = (RadioGroup)findViewById(R.id.rdPregunta23c); captura23c =res.getString(R.string.PREGUNTA23c);  lay23c = (LinearLayout) findViewById(R.id.lay23c);
rdPregunta24 = (RadioGroup)findViewById(R.id.rdPregunta24); captura24 =res.getString(R.string.PREGUNTA24);  lay24 = (LinearLayout) findViewById(R.id.lay24);
rdPregunta24_1 = (RadioGroup)findViewById(R.id.rdPregunta24_1); captura24_1 =res.getString(R.string.PREGUNTA24_1);  lay24_1 = (LinearLayout) findViewById(R.id.lay24_1);
rdPregunta24_1a = (RadioGroup)findViewById(R.id.rdPregunta24_1a); captura24_1a =res.getString(R.string.PREGUNTA24_1a);  lay24_1a = (LinearLayout) findViewById(R.id.lay24_1a);
rdPregunta24_1b = (RadioGroup)findViewById(R.id.rdPregunta24_1b); captura24_1b =res.getString(R.string.PREGUNTA24_1b);  lay24_1b = (LinearLayout) findViewById(R.id.lay24_1b);
rdPregunta24_2 = (RadioGroup)findViewById(R.id.rdPregunta24_2); captura24_2 =res.getString(R.string.PREGUNTA24_2);  lay24_2 = (LinearLayout) findViewById(R.id.lay24_2);
rdPregunta24_2a = (RadioGroup)findViewById(R.id.rdPregunta24_2a); captura24_2a =res.getString(R.string.PREGUNTA24_2a);  lay24_2a = (LinearLayout) findViewById(R.id.lay24_2a);
rdPregunta24_2b = (RadioGroup)findViewById(R.id.rdPregunta24_2b); captura24_2b =res.getString(R.string.PREGUNTA24_2b);  lay24_2b = (LinearLayout) findViewById(R.id.lay24_2b);
rdPregunta24_3 = (RadioGroup)findViewById(R.id.rdPregunta24_3); captura24_3 =res.getString(R.string.PREGUNTA24_3);  lay24_3 = (LinearLayout) findViewById(R.id.lay24_3);
rdPregunta24_3a = (RadioGroup)findViewById(R.id.rdPregunta24_3a); captura24_3a =res.getString(R.string.PREGUNTA24_3a);  lay24_3a = (LinearLayout) findViewById(R.id.lay24_3a);
rdPregunta24_3b = (RadioGroup)findViewById(R.id.rdPregunta24_3b); captura24_3b =res.getString(R.string.PREGUNTA24_3b);  lay24_3b = (LinearLayout) findViewById(R.id.lay24_3b);
rdPregunta25 = (RadioGroup)findViewById(R.id.rdPregunta25); captura25 =res.getString(R.string.PREGUNTA25);  lay25 = (LinearLayout) findViewById(R.id.lay25);
rdPregunta26 = (RadioGroup)findViewById(R.id.rdPregunta26); captura26 =res.getString(R.string.PREGUNTA26);  lay26 = (LinearLayout) findViewById(R.id.lay26);
rdPregunta27 = (RadioGroup)findViewById(R.id.rdPregunta27); captura27 =res.getString(R.string.PREGUNTA27);  lay27 = (LinearLayout) findViewById(R.id.lay27);
rdPregunta28 = (RadioGroup)findViewById(R.id.rdPregunta28); captura28 =res.getString(R.string.PREGUNTA28);  lay28 = (LinearLayout) findViewById(R.id.lay28);
rdPregunta29 = (RadioGroup)findViewById(R.id.rdPregunta29); captura29 =res.getString(R.string.PREGUNTA29);  lay29 = (LinearLayout) findViewById(R.id.lay29);

editPregunta6= (EditText)findViewById(R.id.editPregunta6);
editPregunta7= (EditText)findViewById(R.id.editPregunta7);

                    textPregunta23c.setText("Si su Alcalde "+Alcalde+" buscara la reelección en el 2021 por un periodo de tres años más en su puesto  ¿Qué tan dispuesto estaría a votar por él (ella)?");

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

radio_abandono1  = (RadioButton) findViewById(R.id.radio_abandono1);
radio_abandono2  = (RadioButton) findViewById(R.id.radio_abandono2);
radio_abandono3  = (RadioButton) findViewById(R.id.radio_abandono3);
radio_abandono4  = (RadioButton) findViewById(R.id.radio_abandono4);

    // spinnerCalifica =(Spinner) findViewById(R.id.spinnerCalifica);


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


capturaAporta           = res.getString(R.string.PREGUNTAAPORTA);
capturaOcupacion        = res.getString(R.string.PREGUNTAOCUPACION);
capturaCuantosCoches    = res.getString(R.string.PREGUNTACUANTOSCOCHES);
capturaFocos            = res.getString(R.string.PREGUNTAFOCOS);
capturaCuartos          = res.getString(R.string.PREGUNTACUARTOS);
capturaCuartosDormir    = res.getString(R.string.PREGUNTACUARTOSDORMIR);
capturaBanos            = res.getString(R.string.PREGUNTABANOS);
capturaEstufa           = res.getString(R.string.PREGUNTAESTUFA);
                        //capturaEdad = res.getString(R.string.PREGUNTAEDAD);
capturaGenero           = res.getString(R.string.PREGUNTAGENERO);
capturaTipoVivienda     = res.getString(R.string.PREGUNTA_TIPO_VIVIENDA);
                        //capturaTipoPiso = res.getString(R.string.PREGUNTA_TIPO_PISO);
btnGuardar = (Button) findViewById(R.id.btnGuardar);
btnSalir   = (Button) findViewById(R.id.btnSalir);
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
textPregunta1.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op1 = "1";
textPregunta1.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op1 = "¿Cómo se entera Usted de las noticias regularmente?";
textPregunta1.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op1 = " Televisión ";
textPregunta1.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op1 = " Radio";
textPregunta1.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op1 = " Periódico";
textPregunta1.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op1 = " Redes sociales";
textPregunta1.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio8) {
op1 = " Internet";
textPregunta1.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio9) {
op1 = " Otra";
textPregunta1.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio10) {
op1 = " No sabe / No contestó";
textPregunta1.setTextColor(Color.parseColor("#008000"));
}

}

});


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op2 = "0";
textPregunta2.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op2 = "2";
textPregunta2.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op2 = "Sólo como información, NO le voy a solicitar datos o sus cuentas ¿Usted tiene cuenta personal en Facebook?";
textPregunta2.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op2 = " Sí";
textPregunta2.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op2 = " No";
textPregunta2.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op2 = " No sabe / No contestó";
textPregunta2.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op3 = "0";
textPregunta3.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op3 = "3";
textPregunta3.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op3 = "¿Tiene cuenta personal en Twitter?";
textPregunta3.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op3 = " Sí";
textPregunta3.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op3 = " No";
textPregunta3.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op3 = " No sabe / No contestó";
textPregunta3.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op4 = "0";
textPregunta4.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op4 = "4";
textPregunta4.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op4 = "¿Usted utiliza WhatsApp?";
textPregunta4.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op4 = " Sí";
textPregunta4.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op4 = " No";
textPregunta4.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op4 = " No sabe / No contestó";
textPregunta4.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta5.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op5 = "0";
textPregunta5.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op5 = "5";
textPregunta5.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op5 = "Comparada con la situación que tenía el país hace un año, ¿cómo diría usted que está la situación actual del país, mejor o peor? (no leer igual de bien, igual de mal)";
textPregunta5.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op5 = " Mejor";
textPregunta5.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op5 = " Igual de bien";
textPregunta5.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op5 = " Igual de mal";
textPregunta5.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op5 = " Peor";
textPregunta5.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio8) {
op5 = " No sabe / No contestó";
textPregunta5.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta6.setOnCheckedChangeListener(new OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(RadioGroup group, int checkedId) {

if (checkedId == R.id.radio1) {
op6 = "1";
textPregunta6.setTextColor(Color.parseColor("#008000"));
editPregunta6.setText("");
}

else if (checkedId == R.id.radio2) {
op6 = "6";
textPregunta6.setTextColor(Color.parseColor("#008000"));
editPregunta6.setText("");
}

else if (checkedId == R.id.radio3) {
op6 = "";
textPregunta6.setTextColor(Color.parseColor("#008000"));
editPregunta6.setText("");
}

else if (checkedId == R.id.radio4) {
op6 = " Inseguridad";
textPregunta6.setTextColor(Color.parseColor("#008000"));
editPregunta6.setText("");
}

else if (checkedId == R.id.radio5) {
op6 = " Desempleo";
textPregunta6.setTextColor(Color.parseColor("#008000"));
editPregunta6.setText("");
}

else if (checkedId == R.id.radio6) {
op6 = " Asesinatos/ Violencia";
textPregunta6.setTextColor(Color.parseColor("#008000"));
editPregunta6.setText("");
}

else if (checkedId == R.id.radio7) {
op6 = " Presidente/ gobierno";
textPregunta6.setTextColor(Color.parseColor("#008000"));
editPregunta6.setText("");
}

else if (checkedId == R.id.radio8) {
op6 = " Mala economía/ crisis";
textPregunta6.setTextColor(Color.parseColor("#008000"));
editPregunta6.setText("");
}

else if (checkedId == R.id.radio9) {
op6 = " Bajos salarios";
textPregunta6.setTextColor(Color.parseColor("#008000"));
editPregunta6.setText("");
}

else if (checkedId == R.id.radio10) {
op6 = " Inflación/ alza de precios";
textPregunta6.setTextColor(Color.parseColor("#008000"));
editPregunta6.setText("");
}

else if (checkedId == R.id.radio11) {
op6 = " Corrupción";
textPregunta6.setTextColor(Color.parseColor("#008000"));
editPregunta6.setText("");
}

else if (checkedId == R.id.radio12) {
op6 = " Pobreza";
textPregunta6.setTextColor(Color.parseColor("#008000"));
editPregunta6.setText("");
}

else if (checkedId == R.id.radio13) {
op6 = " Salud/ COVID";
textPregunta6.setTextColor(Color.parseColor("#008000"));
editPregunta6.setText("");
}

else if (checkedId == R.id.radio14) {
op6 = "Educación";
textPregunta6.setTextColor(Color.parseColor("#008000"));
editPregunta6.setText("");
}

else if (checkedId == R.id.radio15) {
op6 = "Otro (registrar)";
textPregunta6.setTextColor(Color.parseColor("#008000"));
editPregunta6.setText("");
}

else if (checkedId == R.id.radio16) {
op6 = "Ninguno";
textPregunta6.setTextColor(Color.parseColor("#008000"));
editPregunta6.setText("");
}

else if (checkedId == R.id.radio0) {
op6 = " No sabe / No contestó";
textPregunta6.setTextColor(Color.parseColor("#008000"));
editPregunta6.setText("");
}

}
});


editPregunta6.setOnTouchListener(new View.OnTouchListener() {
@Override
  public boolean onTouch(View v, MotionEvent event) {
  v.setFocusable(true);
  v.setFocusableInTouchMode(true);
  return false;
  }
});


editPregunta6.addTextChangedListener(new TextWatcher() {
@Override
  public void afterTextChanged(Editable s) {}
  @Override
  public void beforeTextChanged(CharSequence s, int start,int count, int after) {
  }
  @Override
  public void onTextChanged(CharSequence s, int start,int before, int count) {
      if(s.length() != 0){
         rdPregunta6.clearCheck();
         textPregunta6.setTextColor(Color.parseColor("#008000"));
      }
  }
});


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta7.setOnCheckedChangeListener(new OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(RadioGroup group, int checkedId) {

if (checkedId == R.id.radio1) {
op7 = "1";
textPregunta7.setTextColor(Color.parseColor("#008000"));
editPregunta7.setText("");
}

else if (checkedId == R.id.radio2) {
op7 = "7";
textPregunta7.setTextColor(Color.parseColor("#008000"));
editPregunta7.setText("");
}

else if (checkedId == R.id.radio3) {
op7 = "Pasando a otro tema ¿Cuál considera usted que es el principal problema de su colonia? (REGISTRAR PRIMER RESPUESTA ESPONTÁNEA, NO LEA OPCIONES) ";
textPregunta7.setTextColor(Color.parseColor("#008000"));
editPregunta7.setText("");
}

else if (checkedId == R.id.radio4) {
op7 = " Inseguridad";
textPregunta7.setTextColor(Color.parseColor("#008000"));
editPregunta7.setText("");
}

else if (checkedId == R.id.radio5) {
op7 = " Falta de agua";
textPregunta7.setTextColor(Color.parseColor("#008000"));
editPregunta7.setText("");
}

else if (checkedId == R.id.radio6) {
op7 = " Asaltos";
textPregunta7.setTextColor(Color.parseColor("#008000"));
editPregunta7.setText("");
}

else if (checkedId == R.id.radio7) {
op7 = " Alumbrado";
textPregunta7.setTextColor(Color.parseColor("#008000"));
editPregunta7.setText("");
}

else if (checkedId == R.id.radio8) {
op7 = " Narcomenudeo/ venta de drogas";
textPregunta7.setTextColor(Color.parseColor("#008000"));
editPregunta7.setText("");
}

else if (checkedId == R.id.radio9) {
op7 = " Pavimentación/ baches";
textPregunta7.setTextColor(Color.parseColor("#008000"));
editPregunta7.setText("");
}

else if (checkedId == R.id.radio10) {
op7 = " Falta de transporte";
textPregunta7.setTextColor(Color.parseColor("#008000"));
editPregunta7.setText("");
}

else if (checkedId == R.id.radio11) {
op7 = " Mala economía";
textPregunta7.setTextColor(Color.parseColor("#008000"));
editPregunta7.setText("");
}

else if (checkedId == R.id.radio12) {
op7 = " Pobreza";
textPregunta7.setTextColor(Color.parseColor("#008000"));
editPregunta7.setText("");
}

else if (checkedId == R.id.radio13) {
op7 = " Drenaje";
textPregunta7.setTextColor(Color.parseColor("#008000"));
editPregunta7.setText("");
}

else if (checkedId == R.id.radio14) {
op7 = "Educación";
textPregunta7.setTextColor(Color.parseColor("#008000"));
editPregunta7.setText("");
}

else if (checkedId == R.id.radio15) {
op7 = "Otro (registrar)";
textPregunta7.setTextColor(Color.parseColor("#008000"));
editPregunta7.setText("");
}

else if (checkedId == R.id.radio16) {
op7 = "Ninguno";
textPregunta7.setTextColor(Color.parseColor("#008000"));
editPregunta7.setText("");
}

else if (checkedId == R.id.radio0) {
op7 = " No sabe / No contestó";
textPregunta7.setTextColor(Color.parseColor("#008000"));
editPregunta7.setText("");
}

}
});


editPregunta7.setOnTouchListener(new View.OnTouchListener() {
@Override
  public boolean onTouch(View v, MotionEvent event) {
  v.setFocusable(true);
  v.setFocusableInTouchMode(true);
  return false;
  }
});


editPregunta7.addTextChangedListener(new TextWatcher() {
@Override
  public void afterTextChanged(Editable s) {}
  @Override
  public void beforeTextChanged(CharSequence s, int start,int count, int after) {
  }
  @Override
  public void onTextChanged(CharSequence s, int start,int before, int count) {
      if(s.length() != 0){
         rdPregunta7.clearCheck();
         textPregunta7.setTextColor(Color.parseColor("#008000"));
      }
  }
});


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPreguntacovi.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                  
if (checkedId == R.id.radio1) {
opcovi = "3";
textPreguntacovi.setTextColor(Color.parseColor("#008000"));
spinnercovi.setSelection(0);
}

else if (checkedId == R.id.radio2) {
opcovi = "covi";
textPreguntacovi.setTextColor(Color.parseColor("#008000"));
spinnercovi.setSelection(0);
}

else if (checkedId == R.id.radio3) {
opcovi = "¿Cómo evalúa usted a? en relación a la atención de la pandemia de Coronavirus, bien o mal?  (no leer regular)";
textPreguntacovi.setTextColor(Color.parseColor("#008000"));
spinnercovi.setSelection(0);
}

}

});


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPreguntacovi1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
opcovi1 = "0";
textPreguntacovi1.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
opcovi1 = "covi1";
textPreguntacovi1.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
opcovi1 = "Médicos y hospitales";
textPreguntacovi1.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
opcovi1 = " Bien";
textPreguntacovi1.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
opcovi1 = " Regular";
textPreguntacovi1.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
opcovi1 = "Mal";
textPreguntacovi1.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
opcovi1 = " No sabe / No contestó";
textPreguntacovi1.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPreguntacovi2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
opcovi2 = "0";
textPreguntacovi2.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
opcovi2 = "covi2";
textPreguntacovi2.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
opcovi2 = "Autoridades de Salud";
textPreguntacovi2.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
opcovi2 = " Bien";
textPreguntacovi2.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
opcovi2 = " Regular";
textPreguntacovi2.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
opcovi2 = "Mal";
textPreguntacovi2.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
opcovi2 = " No sabe / No contestó";
textPreguntacovi2.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPreguntacovi3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
opcovi3 = "0";
textPreguntacovi3.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
opcovi3 = "covi3";
textPreguntacovi3.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
opcovi3 = "Gobierno de la Ciudad de México";
textPreguntacovi3.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
opcovi3 = " Bien";
textPreguntacovi3.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
opcovi3 = " Regular";
textPreguntacovi3.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
opcovi3 = "Mal";
textPreguntacovi3.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
opcovi3 = " No sabe / No contestó";
textPreguntacovi3.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPreguntacovi4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
opcovi4 = "0";
textPreguntacovi4.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
opcovi4 = "covi4";
textPreguntacovi4.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
opcovi4 = "Su diputado(a) local";
textPreguntacovi4.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
opcovi4 = " Bien";
textPreguntacovi4.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
opcovi4 = " Regular";
textPreguntacovi4.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
opcovi4 = "Mal";
textPreguntacovi4.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
opcovi4 = " No sabe / No contestó";
textPreguntacovi4.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta8.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op8 = "0";
textPregunta8.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op8 = "8";
textPregunta8.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op8 = "Con lo que usted sabe ¿está de acuerdo, o en desacuerdo con el trabajo realizado hasta ahora por el Presidente de la República, Andrés Manuel López Obrador? (no leer ni de acuerdo ni en desacuerdo)";
textPregunta8.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op8 = " De acuerdo";
textPregunta8.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op8 = " Ni de acuerdo, ni en desacuerdo";
textPregunta8.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op8 = " En desacuerdo";
textPregunta8.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op8 = " No sabe / No contestó";
textPregunta8.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta9.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op9 = "0";
textPregunta9.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op9 = "9";
textPregunta9.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op9 = "Hablando ahora de la Ciudad de México y de su gobierno, con lo que Ud. sabe, ¿está de acuerdo o en desacuerdo con el trabajo realizado hasta ahora por la Jefa de Gobierno, Claudia Sheinbaum?  (no leer ni de acuerdo ni en desacuerdo)";
textPregunta9.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op9 = " De acuerdo";
textPregunta9.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op9 = " Ni de acuerdo, ni en desacuerdo";
textPregunta9.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op9 = " En desacuerdo";
textPregunta9.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op9 = " No sabe / No contestó";
textPregunta9.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta10.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op10 = "0";
textPregunta10.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op10 = "10";
textPregunta10.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op10 = "Con lo que Ud. sabe, ¿está de acuerdo o en desacuerdo con el trabajo realizado hasta ahora por su  Alcalde? (no leer ni de acuerdo ni en desacuerdo)";
textPregunta10.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op10 = " De acuerdo";
textPregunta10.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op10 = " Ni de acuerdo, ni en desacuerdo";
textPregunta10.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op10 = " En desacuerdo";
textPregunta10.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op10 = " No sabe / No contestó";
textPregunta10.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta11.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op11 = "0";
textPregunta11.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op11 = "11";
textPregunta11.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op11 = "Con lo que Ud. sabe, ¿está de acuerdo o en desacuerdo con el trabajo realizado hasta ahora por su Diputado(a) local en este distrito electoral? (no leer ni de acuerdo ni en desacuerdo)";
textPregunta11.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op11 = " De acuerdo";
textPregunta11.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op11 = " Ni de acuerdo, ni en desacuerdo";
textPregunta11.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op11 = " En desacuerdo";
textPregunta11.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op11 = " No sabe / No contestó";
textPregunta11.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta12.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op12 = "0";
textPregunta12.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op12 = "12";
textPregunta12.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op12 = "Con lo que Ud. sabe, ¿está de acuerdo o en desacuerdo con el trabajo realizado hasta ahora por su Diputado(a) Federal en este distrito electoral? (no leer ni de acuerdo ni en desacuerdo)";
textPregunta12.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op12 = " De acuerdo";
textPregunta12.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op12 = " Ni de acuerdo, ni en desacuerdo";
textPregunta12.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op12 = " En desacuerdo";
textPregunta12.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op12 = " No sabe / No contestó";
textPregunta12.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta13.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op13 = "0";
textPregunta13.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op13 = "13";
textPregunta13.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op13 = "Actualmente ¿tiene usted credencial de elector vigente?";
textPregunta13.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op13 = " Sí";
textPregunta13.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op13 = " No";
textPregunta13.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op13 = " No sabe / No contestó";
textPregunta13.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta14.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op14 = "0";
textPregunta14.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op14 = "14";
textPregunta14.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op14 = "En junio próximo habrá elecciones para elegir alcaldes, diputados locales y diputados federales. ¿Qué tan interesado está usted de ir a votar en estas elecciones: totalmente interesado, bastante interesado, poco interesado o nada interesado?";
textPregunta14.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op14 = " Totalmente";
textPregunta14.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op14 = " Bastante ";
textPregunta14.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op14 = " Poco";
textPregunta14.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op14 = " Nada/ no va a votar";
textPregunta14.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio8) {
op14 = " No sabe / No contestó";
textPregunta14.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta15.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op15 = "0";
textPregunta15.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op15 = "15";
textPregunta15.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op15 = "Y dígame, ¿qué tan seguro está usted de ir a votar en estas elecciones: totalmente seguro, bastante seguro, poco seguro o nada seguro?";
textPregunta15.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op15 = " Totalmente";
textPregunta15.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op15 = " Bastante ";
textPregunta15.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op15 = " Poco";
textPregunta15.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op15 = " Nada/ no va a votar";
textPregunta15.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio8) {
op15 = " No sabe / No contestó";
textPregunta15.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta16.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op16 = "0";
textPregunta16.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op16 = "16";
textPregunta16.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op16 = " Solamente para las estadísticas ¿Usted votó en las pasadas elecciones del 2018?";
textPregunta16.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op16 = " Sí";
textPregunta16.setTextColor(Color.parseColor("#008000"));
lay16a .setVisibility(View.VISIBLE); rdPregunta16a.clearCheck();  op16a="sin datos";
  lay16b.setVisibility(View.GONE); rdPregunta16b.clearCheck();  op16b="No aplica";

}

else if (checkedId == R.id.radio5) {
op16 = " No";
textPregunta16.setTextColor(Color.parseColor("#008000"));
  lay16a.setVisibility(View.GONE); rdPregunta16a.clearCheck();  op16a="No aplica";
  lay16b .setVisibility(View.VISIBLE); rdPregunta16b.clearCheck();  op16b="sin datos";
}

else if (checkedId == R.id.radio6) {
op16 = " No sabe / No contestó";
textPregunta16.setTextColor(Color.parseColor("#008000"));
  lay16a.setVisibility(View.GONE); rdPregunta16a.clearCheck();  op16a="No aplica";
  lay16b.setVisibility(View.GONE); rdPregunta16b.clearCheck();  op16b="No aplica";
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta16a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op16a = "0";
textPregunta16a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op16a = "16a";
textPregunta16a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op16a = "¿Y en esas las elecciones del año 2018, por cuál partido votó para Diputado Fedaral del distrito 10? (ESPONTÁNEA) ";
textPregunta16a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op16a = " Morena";
textPregunta16a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op16a = " PRI";
textPregunta16a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op16a = " PAN";
textPregunta16a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op16a = " PRD";
textPregunta16a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio8) {
op16a = " P.Verde";
textPregunta16a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio9) {
op16a = " Otro";
textPregunta16a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio10) {
op16a = " No sabe / No contestó";
textPregunta16a.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta16b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op16b = "0";
textPregunta16b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op16b = "16b";
textPregunta16b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op16b = " Y si hubiera podido votar, ¿por cuál partido hubiera votado usted para Diputado federal del distrito 10 en julio del 2018?";
textPregunta16b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op16b = " Morena";
textPregunta16b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op16b = " PRI";
textPregunta16b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op16b = " PAN";
textPregunta16b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op16b = " PRD";
textPregunta16b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio8) {
op16b = " P.Verde";
textPregunta16b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio9) {
op16b = " Otro";
textPregunta16b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio10) {
op16b = " No sabe / No contestó";
textPregunta16b.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta17.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op17 = "0";
textPregunta17.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op17 = "17";
textPregunta17.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op17 = "Independientemente del partido por el que usted vota, ¿usted normalmente se considera panista, priísta, perredista, morenista o de otro partido?";
textPregunta17.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op17 = " Priista";
textPregunta17.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op17 = " Morenista";
textPregunta17.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op17 = " Panista";
textPregunta17.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op17 = " Perredista";
textPregunta17.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio8) {
op17 = " PT";
textPregunta17.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio9) {
op17 = " Partido Verde";
textPregunta17.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio10) {
op17 = " Movimiento Ciudadano";
textPregunta17.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio11) {
op17 = " De ningún partido";
textPregunta17.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio12) {
op17 = " No contestó";
textPregunta17.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta18.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op18 = "0";
textPregunta18.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op18 = "18";
textPregunta18.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op18 = "¿Por cuál partido nunca votaría? (espontánea, registrar primera mención)";
textPregunta18.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op18 = " Morena";
textPregunta18.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op18 = " PRI";
textPregunta18.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op18 = " PAN";
textPregunta18.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op18 = " PRD";
textPregunta18.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio8) {
op18 = " PT";
textPregunta18.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio9) {
op18 = " P.Verde";
textPregunta18.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio10) {
op18 = " Movimiento Ciudadano";
textPregunta18.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio11) {
op18 = " No sabe / No contestó";
textPregunta18.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta19.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op19 = "0";
textPregunta19.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op19 = "19";
textPregunta19.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op19 = "Solo para estadística, ¿me podría decir por quién votó para Jefe de Gobierno en las elecciones de 2018? (espontánea)";
textPregunta19.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op19 = " Claudia Sheinbaum/ Morena/ PT/ Encuentro social";
textPregunta19.setTextColor(Color.parseColor("#008000"));
  lay19a .setVisibility(View.VISIBLE); rdPregunta19a.clearCheck();  op19a="sin datos";
  lay19b.setVisibility(View.VISIBLE); rdPregunta19b.clearCheck();  op19b="sin datos";
}

else if (checkedId == R.id.radio5) {
op19 = " Alejandra Barrales/ PAN/ PRD/ MC";
textPregunta19.setTextColor(Color.parseColor("#008000"));
lay19a.setVisibility(View.GONE); rdPregunta19a.clearCheck();  op19a="No aplica";
lay19b.setVisibility(View.VISIBLE); rdPregunta19b.clearCheck();  op19b="sin datos";
}

else if (checkedId == R.id.radio6) {
op19 = " Mikel Arriola/ PRI";
textPregunta19.setTextColor(Color.parseColor("#008000"));
lay19a.setVisibility(View.GONE); rdPregunta19a.clearCheck();  op19a="No aplica";
lay19b.setVisibility(View.VISIBLE); rdPregunta19b.clearCheck();  op19b="sin datos";
}

else if (checkedId == R.id.radio7) {
op19 = " Mariana Boy/ PVEM";
textPregunta19.setTextColor(Color.parseColor("#008000"));
lay19a.setVisibility(View.GONE); rdPregunta19a.clearCheck();  op19a="No aplica";
lay19b.setVisibility(View.VISIBLE); rdPregunta19b.clearCheck();  op19b="sin datos";
}

else if (checkedId == R.id.radio8) {
op19 = " Lorena Osornio/ Independiente";
textPregunta19.setTextColor(Color.parseColor("#008000"));
lay19a.setVisibility(View.GONE); rdPregunta19a.clearCheck();  op19a="No aplica";
lay19b.setVisibility(View.VISIBLE); rdPregunta19b.clearCheck();  op19b="sin datos";
}

else if (checkedId == R.id.radio9) {
op19 = " Marco Rascón/ PHCDMX";
textPregunta19.setTextColor(Color.parseColor("#008000"));
lay19a.setVisibility(View.GONE); rdPregunta19a.clearCheck();  op19a="No aplica";
lay19b.setVisibility(View.VISIBLE); rdPregunta19b.clearCheck();  op19b="sin datos";
}

else if (checkedId == R.id.radio10) {
op19 = " Purificación Carpinteyro/ PANAL";
textPregunta19.setTextColor(Color.parseColor("#008000"));
lay19a.setVisibility(View.GONE); rdPregunta19a.clearCheck();  op19a="No aplica";
lay19b.setVisibility(View.VISIBLE); rdPregunta19b.clearCheck();  op19b="sin datos";
}

else if (checkedId == R.id.radio11) {
op19 = " No voto";
textPregunta19.setTextColor(Color.parseColor("#008000"));
lay19a.setVisibility(View.GONE); rdPregunta19a.clearCheck();  op19a="No aplica";
lay19b.setVisibility(View.VISIBLE); rdPregunta19b.clearCheck();  op19b="sin datos";
}

else if (checkedId == R.id.radio12) {
op19 = " Anuló su voto";
textPregunta19.setTextColor(Color.parseColor("#008000"));
lay19a.setVisibility(View.GONE); rdPregunta19a.clearCheck();  op19a="No aplica";
lay19b.setVisibility(View.VISIBLE); rdPregunta19b.clearCheck();  op19b="sin datos";
}

else if (checkedId == R.id.radio13) {
op19 = " No sabe / No contestó";
textPregunta19.setTextColor(Color.parseColor("#008000"));
lay19a.setVisibility(View.GONE); rdPregunta19a.clearCheck();  op19a="No aplica";
lay19b.setVisibility(View.VISIBLE); rdPregunta19b.clearCheck();  op19b="sin datos";
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta19a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op19a = "0";
textPregunta19a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op19a = "19a";
textPregunta19a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op19a = "A dos años de la elección ¿sigue pensando que Claudia Sheinbaum fue la mejor elección para la Jefatura de Gobierno? (espontáneo)";
textPregunta19a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op19a = " Si, fue la mejor";
textPregunta19a.setTextColor(Color.parseColor("#008000"));
lay19b.setVisibility(View.GONE); rdPregunta19b.clearCheck();
}

else if (checkedId == R.id.radio5) {
op19a = " En parte";
textPregunta19a.setTextColor(Color.parseColor("#008000"));
lay19b.setVisibility(View.GONE); rdPregunta19b.clearCheck();
}

else if (checkedId == R.id.radio6) {
op19a = " No fue la mejor";
textPregunta19a.setTextColor(Color.parseColor("#008000"));
lay19b.setVisibility(View.GONE); rdPregunta19b.clearCheck();
}

else if (checkedId == R.id.radio7) {
op19a = " No sabe / No contestó";
textPregunta19a.setTextColor(Color.parseColor("#008000"));
lay19b.setVisibility(View.GONE); rdPregunta19b.clearCheck();
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta19b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op19b = "0";
textPregunta19b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op19b = "19b";
textPregunta19b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op19b = "Después de dos años de la elección ¿cómo cree que está haciendo su trabajo la Jefa de Gobierno, Claudia Sheinbaum, bien o mal? (no leer regular)";
textPregunta19b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op19b = " Bien";
textPregunta19b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op19b = " Regular";
textPregunta19b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op19b = " Mal";
textPregunta19b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op19b = " No sabe / No contestó";
textPregunta19b.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta20.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op20 = "0";
textPregunta20.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op20 = "20";
textPregunta20.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op20 = "A usted que cree que beneficie más a su distrito federal 10, ¿qué continúe el mismo partido tres años más o que cambie el partido que gobierna el Distrito Federal 10?";
textPregunta20.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op20 = " Que continúe el mismo partido";
textPregunta20.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op20 = " Que se alterne el partido que gobierna";
textPregunta20.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op20 = " No sabe / No contestó";
textPregunta20.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta21.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op21 = "0";
textPregunta21.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op21 = "21";
textPregunta21.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op21 = "La ley electoral vigente actualmente permite que alcaldes y/o diputados tengan oportunidad de reelegirse. Antes de que se lo mencionara ¿Usted ya lo sabía?";
textPregunta21.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op21 = " Sí";
textPregunta21.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op21 = " No";
textPregunta21.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op21 = " No sabe / No contestó";
textPregunta21.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta22.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op22 = "0";
textPregunta22.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op22 = "22";
textPregunta22.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op22 = "¿Qué tan de acuerdo está usted con esta disposición que permite que alcaldes y/o diputados  tengan oportunidad de reelegirse?";
textPregunta22.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op22 = " Muy de acuerdo";
textPregunta22.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op22 = " Algo de acuerdo";
textPregunta22.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op22 = " Poco de acuerdo";
textPregunta22.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op22 = " Nada de acuerdo";
textPregunta22.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio8) {
op22 = " No sabe / No contestó";
textPregunta22.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta23a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op23a = "0";
textPregunta23a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op23a = "23a";
textPregunta23a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op23a = "Si su Diputado local buscara la reelección en el 2021 por un periodo de tres años más en su puesto  ¿Qué tan dispuesto estaría a votar por él (ella)?";
textPregunta23a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op23a = " Muy dispuesto";
textPregunta23a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op23a = " Dispuesto";
textPregunta23a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op23a = " Poco dispuesto";
textPregunta23a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op23a = " Nada dispuesto";
textPregunta23a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio8) {
op23a = " No sabe / No contestó";
textPregunta23a.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta23b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op23b = "0";
textPregunta23b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op23b = "23b";
textPregunta23b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op23b = "Si su Diputado federal buscara la reelección en el 2021 por un periodo de tres años más en su puesto  ¿Qué tan dispuesto estaría a votar por él (ella)?";
textPregunta23b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op23b = " Muy dispuesto";
textPregunta23b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op23b = " Dispuesto";
textPregunta23b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op23b = " Poco dispuesto";
textPregunta23b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op23b = " Nada dispuesto";
textPregunta23b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio8) {
op23b = " No sabe / No contestó";
textPregunta23b.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta23c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op23c = "0";
textPregunta23c.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op23c = "23c";
textPregunta23c.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op23c = "Si su Alcalde (Insertar nombre por sistema) buscara la reelección en el 2021 por un periodo de tres años más en su puesto  ¿Qué tan dispuesto estaría a votar por él (ella)?";
textPregunta23c.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op23c = " Muy dispuesto";
textPregunta23c.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op23c = " Dispuesto";
textPregunta23c.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op23c = " Poco dispuesto";
textPregunta23c.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op23c = " Nada dispuesto";
textPregunta23c.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio8) {
op23c = " No sabe / No contestó";
textPregunta23c.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta24.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                  
if (checkedId == R.id.radio1) {
op24 = "3";
textPregunta24.setTextColor(Color.parseColor("#008000"));
spinner24.setSelection(0);
}

else if (checkedId == R.id.radio2) {
op24 = "24";
textPregunta24.setTextColor(Color.parseColor("#008000"));
spinner24.setSelection(0);
}

else if (checkedId == R.id.radio3) {
op24 = "Le voy a leer los nombres de varias personas. ¿Ha oído mencionar a...? (ENCUESTADOR: SI NO HA OÍDO MENCIONAR AL CANDIDATO, CONTINÚE CON LA PERSONA SIGUEINTE) ";
textPregunta24.setTextColor(Color.parseColor("#008000"));
spinner24.setSelection(0);
}

}

});


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta24_1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op24_1 = "0";
textPregunta24_1.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op24_1 = "24_1";
textPregunta24_1.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op24_1 = "Margarita Zavala Gómez del Campo ";
textPregunta24_1.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op24_1 = "No";
textPregunta24_1.setTextColor(Color.parseColor("#008000"));
  lay24_1a.setVisibility(View.GONE); rdPregunta24_1a.clearCheck();   op24_1a="No aplica";
  lay24_1b.setVisibility(View.GONE); rdPregunta24_1b.clearCheck();   op24_1b="No aplica";
}

else if (checkedId == R.id.radio5) {
op24_1 = "Si";
textPregunta24_1.setTextColor(Color.parseColor("#008000"));
lay24_1a .setVisibility(View.VISIBLE); rdPregunta24_1a.clearCheck();  op24_1a="sin datos";
lay24_1b .setVisibility(View.VISIBLE); rdPregunta24_1b.clearCheck();  op24_1b="sin datos";
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta24_1a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op24_1a = "0";
textPregunta24_1a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op24_1a = "24_1a";
textPregunta24_1a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op24_1a = "¿Qué opinión tiene de esta persona muy buena, buena, mala o muy mala? ";
textPregunta24_1a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op24_1a = "Muy buena";
textPregunta24_1a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op24_1a = "buena";
textPregunta24_1a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op24_1a = "regular";
textPregunta24_1a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op24_1a = "mala";
textPregunta24_1a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio8) {
op24_1a = "muy mala";
textPregunta24_1a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio9) {
op24_1a = "conoce sin opinión";
textPregunta24_1a.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta24_1b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op24_1b = "0";
textPregunta24_1b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op24_1b = "24_1b";
textPregunta24_1b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op24_1b = "¿Estaría usted dispuesto o no a votar por Margarita Zavala Gómez del Campo para diputado federal del distrito 10 ?";
textPregunta24_1b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op24_1b = "Sí";
textPregunta24_1b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op24_1b = "No";
textPregunta24_1b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op24_1b = "No sabe / No contestó";
textPregunta24_1b.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta24_2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op24_2 = "0";
textPregunta24_2.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op24_2 = "24_2";
textPregunta24_2.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op24_2 = "Gabriela Cuevas Barron";
textPregunta24_2.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op24_2 = "No";
textPregunta24_2.setTextColor(Color.parseColor("#008000"));
  lay24_2a.setVisibility(View.GONE); rdPregunta24_2a.clearCheck(); op24_2a="No aplica";
  lay24_2b.setVisibility(View.GONE); rdPregunta24_2b.clearCheck(); op24_2b="No aplica";
}

else if (checkedId == R.id.radio5) {
op24_2 = "Si";
textPregunta24_2.setTextColor(Color.parseColor("#008000"));
  lay24_2a .setVisibility(View.VISIBLE); rdPregunta24_2a.clearCheck();  op24_2a="sin datos";
  lay24_2b .setVisibility(View.VISIBLE); rdPregunta24_2b.clearCheck();  op24_2b="sin datos";
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta24_2a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op24_2a = "0";
textPregunta24_2a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op24_2a = "24_2a";
textPregunta24_2a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op24_2a = "¿Qué opinión tiene de esta persona muy buena, buena, mala o muy mala? ";
textPregunta24_2a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op24_2a = "Muy buena";
textPregunta24_2a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op24_2a = "buena";
textPregunta24_2a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op24_2a = "regular";
textPregunta24_2a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op24_2a = "mala";
textPregunta24_2a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio8) {
op24_2a = "muy mala";
textPregunta24_2a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio9) {
op24_2a = "conoce sin opinión";
textPregunta24_2a.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta24_2b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op24_2b = "0";
textPregunta24_2b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op24_2b = "24_2b";
textPregunta24_2b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op24_2b = "¿Estaría usted dispuesto o no a votar por Gabriela Cuevas Barron para diputado federal del distrito 10 ?";
textPregunta24_2b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op24_2b = "Sí";
textPregunta24_2b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op24_2b = "No";
textPregunta24_2b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op24_2b = "No sabe / No contestó";
textPregunta24_2b.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta24_3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op24_3 = "0";
textPregunta24_3.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op24_3 = "24_3";
textPregunta24_3.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op24_3 = "Javier Hidalgo Ponce";
textPregunta24_3.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op24_3 = "No";
textPregunta24_3.setTextColor(Color.parseColor("#008000"));
  lay24_3a.setVisibility(View.GONE); rdPregunta24_3a.clearCheck(); op24_3a="No aplica";
  lay24_3b.setVisibility(View.GONE); rdPregunta24_3b.clearCheck(); op24_3b="No aplica";
}

else if (checkedId == R.id.radio5) {
op24_3 = "Si";
textPregunta24_3.setTextColor(Color.parseColor("#008000"));
  lay24_3a .setVisibility(View.VISIBLE); rdPregunta24_3a.clearCheck();  op24_3a="sin datos";
  lay24_3b .setVisibility(View.VISIBLE); rdPregunta24_3b.clearCheck();  op24_3b="sin datos";
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta24_3a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op24_3a = "0";
textPregunta24_3a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op24_3a = "24_3a";
textPregunta24_3a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op24_3a = "¿Qué opinión tiene de esta persona muy buena, buena, mala o muy mala? ";
textPregunta24_3a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op24_3a = "Muy buena";
textPregunta24_3a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op24_3a = "buena";
textPregunta24_3a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op24_3a = "regular";
textPregunta24_3a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op24_3a = "mala";
textPregunta24_3a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio8) {
op24_3a = "muy mala";
textPregunta24_3a.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio9) {
op24_3a = "conoce sin opinión";
textPregunta24_3a.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta24_3b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op24_3b = "0";
textPregunta24_3b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op24_3b = "24_3b";
textPregunta24_3b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op24_3b = "¿Estaría usted dispuesto o no a votar por Javier Hidalgo Ponce para diputado federal del distrito 10 ?";
textPregunta24_3b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op24_3b = "Sí";
textPregunta24_3b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op24_3b = "No";
textPregunta24_3b.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op24_3b = "No sabe / No contestó";
textPregunta24_3b.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta25.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op25 = "0";
textPregunta25.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op25 = "25";
textPregunta25.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op25 = "Le voy a leer una lista de personas ¿Si hoy fueran las elecciones, de entre estos candidatos por cual votaría? (LEER CANDIDATOS, NO LEER NINGUNO Y NO SABE NO CONTESTÓ)";
textPregunta25.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op25 = "Margarita Zavala Gómez del Campo (Alianza PRI, PAN PRD)";
textPregunta25.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op25 = "Gabriela Cuevas Barron (MORENA)";
textPregunta25.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op25 = "Ninguno";
textPregunta25.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op25 = " No sabe / No contestó";
textPregunta25.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta26.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op26 = "0";
textPregunta26.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op26 = "26";
textPregunta26.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op26 = "y ¿Si hoy fueran las elecciones, de entre estos candidatos por cual votaría? (LEER CANDIDATOS, NO LEER NINGUNO Y NO SABE NO CONTESTÓ)";
textPregunta26.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op26 = "Margarita Zavala Gómez del Campo (Alianza PRI, PAN PRD)";
textPregunta26.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op26 = "Javier Hidalgo Ponce (MORENA)";
textPregunta26.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op26 = "Ninguno";
textPregunta26.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op26 = " No sabe / No contestó";
textPregunta26.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta27.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op27 = "0";
textPregunta27.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op27 = "27";
textPregunta27.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op27 = "Y entre estos candidatos ¿Si hoy fueran las elecciones, de entre estos candidatos de MORENA por cual votaría? (LEER CANDIDATOS, NO LEER NINGUNO Y NO SABE NO CONTESTÓ)";
textPregunta27.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op27 = "Javier Hidalgo Ponce ";
textPregunta27.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op27 = "Gabriela Cuevas Barron ";
textPregunta27.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op27 = "Ninguno";
textPregunta27.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op27 = " No sabe / No contestó";
textPregunta27.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta28.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op28 = "0";
textPregunta28.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op28 = "28";
textPregunta28.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op28 = "";
textPregunta28.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op28 = " Morena";
textPregunta28.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op28 = " PRI";
textPregunta28.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op28 = " PAN";
textPregunta28.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op28 = " PRD";
textPregunta28.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio8) {
op28 = " PT";
textPregunta28.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio9) {
op28 = " P.Verde";
textPregunta28.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio10) {
op28 = " Movimiento Ciudadano";
textPregunta28.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio11) {
op28 = " No sabe / No contestó";
textPregunta28.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



rdPregunta29.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              
if (checkedId == R.id.radio1) {
op29 = "0";
textPregunta29.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio2) {
op29 = "29";
textPregunta29.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio3) {
op29 = "";
textPregunta29.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio4) {
op29 = " Morena";
textPregunta29.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio5) {
op29 = " PRI";
textPregunta29.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio6) {
op29 = " PAN";
textPregunta29.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio7) {
op29 = " PRD";
textPregunta29.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio8) {
op29 = " PT";
textPregunta29.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio9) {
op29 = " P.Verde";
textPregunta29.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio10) {
op29 = " Movimiento Ciudadano";
textPregunta29.setTextColor(Color.parseColor("#008000"));
}

else if (checkedId == R.id.radio11) {
op29 = " No sabe / No contestó";
textPregunta29.setTextColor(Color.parseColor("#008000"));
}

}
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




///////////////////////////////////// SOCIOECONOMICOS  /////////////////////////////////////

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
    elMaximoConsecutivo = Integer.parseInt(sacaMaximoConsecutivo().toString()) + 1;





String strText6;
  if (editPregunta6.getText().toString().trim().length() == 0) {
    strText6 = op6;
    } else {
      strText6 = editPregunta6.getText().toString().trim();
      rdPregunta6.clearCheck();
    }
String strText7;
  if (editPregunta7.getText().toString().trim().length() == 0) {
    strText7 = op7;
    } else {
      strText7 = editPregunta7.getText().toString().trim();
      rdPregunta7.clearCheck();
    }

String str1 = op1;
String str2 = op2;
String str3 = op3;
String str4 = op4;
String str5 = op5;
String str6 = strText6;
String str7 = strText7;
String strcovi = "pregunta";
String strcovi1 = opcovi1;
String strcovi2 = opcovi2;
String strcovi3 = opcovi3;
String strcovi4 = opcovi4;
String str8 = op8;
String str9 = op9;
String str10 = op10;
String str11 = op11;
String str12 = op12;
String str13 = op13;
String str14 = op14;
String str15 = op15;
String str16 = op16;
String str16a = op16a;
String str16b = op16b;
String str17 = op17;
String str18 = op18;
String str19 = op19;
String str19a = op19a;
String str19b = op19b;
String str20 = op20;
String str21 = op21;
String str22 = op22;
String str23a = op23a;
String str23b = op23b;
String str23c = op23c;
String str24 = "pregunta";
String str24_1 = op24_1;
String str24_1a = op24_1a;
String str24_1b = op24_1b;
String str24_2 = op24_2;
String str24_2a = op24_2a;
String str24_2b = op24_2b;
String str24_3 = op24_3;
String str24_3a = op24_3a;
String str24_3b = op24_3b;
String str25 = op25;
String str26 = op26;
String str27 = op27;
String str28 = op28;
String str29 = op29;

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
          values.put("pregunta_covi", strcovi);
          values.put("pregunta_covi1", strcovi1);
          values.put("pregunta_covi2", strcovi2);
          values.put("pregunta_covi3", strcovi3);
          values.put("pregunta_covi4", strcovi4);
          values.put("pregunta_8", str8);
          values.put("pregunta_9", str9);
          values.put("pregunta_10", str10);
          values.put("pregunta_11", str11);
          values.put("pregunta_12", str12);
          values.put("pregunta_13", str13);
          values.put("pregunta_14", str14);
          values.put("pregunta_15", str15);
          values.put("pregunta_16", str16);
          values.put("pregunta_16a", str16a);
          values.put("pregunta_16b", str16b);
          values.put("pregunta_17", str17);
          values.put("pregunta_18", str18);
          values.put("pregunta_19", str19);
          values.put("pregunta_19a", str19a);
          values.put("pregunta_19b", str19b);
          values.put("pregunta_20", str20);
          values.put("pregunta_21", str21);
          values.put("pregunta_22", str22);
          values.put("pregunta_23a", str23a);
          values.put("pregunta_23b", str23b);
          values.put("pregunta_23c", str23c);
          values.put("pregunta_24", str24);
          values.put("pregunta_24_1", str24_1);
          values.put("pregunta_24_1a", str24_1a);
          values.put("pregunta_24_1b", str24_1b);
          values.put("pregunta_24_2", str24_2);
          values.put("pregunta_24_2a", str24_2a);
          values.put("pregunta_24_2b", str24_2b);
          values.put("pregunta_24_3", str24_3);
          values.put("pregunta_24_3a", str24_3a);
          values.put("pregunta_24_3b", str24_3b);
          values.put("pregunta_25", str25);
          values.put("pregunta_26", str26);
          values.put("pregunta_27", str27);
          values.put("pregunta_28", str28);
          values.put("pregunta_29", str29);
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
      System.out.println("pregunta_covi  " + strcovi);
      System.out.println("pregunta_covi1  " + strcovi1);
      System.out.println("pregunta_covi2  " + strcovi2);
      System.out.println("pregunta_covi3  " + strcovi3);
      System.out.println("pregunta_covi4  " + strcovi4);
      System.out.println("pregunta_8  " + str8);
      System.out.println("pregunta_9  " + str9);
      System.out.println("pregunta_10  " + str10);
      System.out.println("pregunta_11  " + str11);
      System.out.println("pregunta_12  " + str12);
      System.out.println("pregunta_13  " + str13);
      System.out.println("pregunta_14  " + str14);
      System.out.println("pregunta_15  " + str15);
      System.out.println("pregunta_16  " + str16);
      System.out.println("pregunta_16a  " + str16a);
      System.out.println("pregunta_16b  " + str16b);
      System.out.println("pregunta_17  " + str17);
      System.out.println("pregunta_18  " + str18);
      System.out.println("pregunta_19  " + str19);
      System.out.println("pregunta_19a  " + str19a);
      System.out.println("pregunta_19b  " + str19b);
      System.out.println("pregunta_20  " + str20);
      System.out.println("pregunta_21  " + str21);
      System.out.println("pregunta_22  " + str22);
      System.out.println("pregunta_23a  " + str23a);
      System.out.println("pregunta_23b  " + str23b);
      System.out.println("pregunta_23c  " + str23c);
      System.out.println("pregunta_24  " + str24);
      System.out.println("pregunta_24_1  " + str24_1);
      System.out.println("pregunta_24_1a  " + str24_1a);
      System.out.println("pregunta_24_1b  " + str24_1b);
      System.out.println("pregunta_24_2  " + str24_2);
      System.out.println("pregunta_24_2a  " + str24_2a);
      System.out.println("pregunta_24_2b  " + str24_2b);
      System.out.println("pregunta_24_3  " + str24_3);
      System.out.println("pregunta_24_3a  " + str24_3a);
      System.out.println("pregunta_24_3b  " + str24_3b);
      System.out.println("pregunta_25  " + str25);
      System.out.println("pregunta_26  " + str26);
      System.out.println("pregunta_27  " + str27);
      System.out.println("pregunta_28  " + str28);
      System.out.println("pregunta_29  " + str29);

      System.out.println(" aporta   " + strAporta);
      System.out.println(" ocupacion   " + strOcupacion);
      System.out.println(" cuantos_coches   " + strCuantosCoches);
      System.out.println(" cuartos   " + strCuartos);
      System.out.println(" cuartos_dormir   " + strCuartosDormir);
      System.out.println(" focos   " + strFocos);

      System.out.println(" baños   " + strBanos);
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



if (lay1.getVisibility() == View.VISIBLE && op1.matches("sin datos")){ lay1.requestFocus(); textPregunta1.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura1,Toast.LENGTH_LONG).show();}
else if (lay2.getVisibility() == View.VISIBLE && op2.matches("sin datos")){ lay2.requestFocus(); textPregunta2.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura2,Toast.LENGTH_LONG).show();}
else if (lay3.getVisibility() == View.VISIBLE && op3.matches("sin datos")){ lay3.requestFocus(); textPregunta3.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura3,Toast.LENGTH_LONG).show();}
else if (lay4.getVisibility() == View.VISIBLE && op4.matches("sin datos")){ lay4.requestFocus(); textPregunta4.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura4,Toast.LENGTH_LONG).show();}
else if (lay5.getVisibility() == View.VISIBLE && op5.matches("sin datos")){ lay5.requestFocus(); textPregunta5.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura5,Toast.LENGTH_LONG).show();}
else if (lay6.getVisibility() == View.VISIBLE && op6.matches("sin datos") && editPregunta6.getText().toString().trim().length() == 0) { lay6.requestFocus(); textPregunta6.setTextColor(Color.RED); Toast.makeText(getBaseContext(), "CAPTURA:  " + captura6, Toast.LENGTH_LONG).show();}
else if (lay7.getVisibility() == View.VISIBLE && op7.matches("sin datos") && editPregunta7.getText().toString().trim().length() == 0) { lay7.requestFocus(); textPregunta7.setTextColor(Color.RED); Toast.makeText(getBaseContext(), "CAPTURA:  " + captura7, Toast.LENGTH_LONG).show();}
// else if (laycovi.getVisibility() == View.VISIBLE && ) {laycovi.requestFocus(); textPreguntacovi.setTextColor(Color.RED); Toast.makeText(getBaseContext(), "CAPTURA:  " + capturacovi, Toast.LENGTH_LONG).show();}
else if (laycovi1.getVisibility() == View.VISIBLE && opcovi1.matches("sin datos")){ laycovi1.requestFocus(); textPreguntacovi1.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturacovi1,Toast.LENGTH_LONG).show();}
else if (laycovi2.getVisibility() == View.VISIBLE && opcovi2.matches("sin datos")){ laycovi2.requestFocus(); textPreguntacovi2.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturacovi2,Toast.LENGTH_LONG).show();}
else if (laycovi3.getVisibility() == View.VISIBLE && opcovi3.matches("sin datos")){ laycovi3.requestFocus(); textPreguntacovi3.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturacovi3,Toast.LENGTH_LONG).show();}
else if (laycovi4.getVisibility() == View.VISIBLE && opcovi4.matches("sin datos")){ laycovi4.requestFocus(); textPreguntacovi4.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturacovi4,Toast.LENGTH_LONG).show();}
else if (lay8.getVisibility() == View.VISIBLE && op8.matches("sin datos")){ lay8.requestFocus(); textPregunta8.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura8,Toast.LENGTH_LONG).show();}
else if (lay9.getVisibility() == View.VISIBLE && op9.matches("sin datos")){ lay9.requestFocus(); textPregunta9.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura9,Toast.LENGTH_LONG).show();}
else if (lay10.getVisibility() == View.VISIBLE && op10.matches("sin datos")){ lay10.requestFocus(); textPregunta10.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura10,Toast.LENGTH_LONG).show();}
else if (lay11.getVisibility() == View.VISIBLE && op11.matches("sin datos")){ lay11.requestFocus(); textPregunta11.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura11,Toast.LENGTH_LONG).show();}
else if (lay12.getVisibility() == View.VISIBLE && op12.matches("sin datos")){ lay12.requestFocus(); textPregunta12.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura12,Toast.LENGTH_LONG).show();}
else if (lay13.getVisibility() == View.VISIBLE && op13.matches("sin datos")){ lay13.requestFocus(); textPregunta13.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura13,Toast.LENGTH_LONG).show();}
else if (lay14.getVisibility() == View.VISIBLE && op14.matches("sin datos")){ lay14.requestFocus(); textPregunta14.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura14,Toast.LENGTH_LONG).show();}
else if (lay15.getVisibility() == View.VISIBLE && op15.matches("sin datos")){ lay15.requestFocus(); textPregunta15.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura15,Toast.LENGTH_LONG).show();}
else if (lay16.getVisibility() == View.VISIBLE && op16.matches("sin datos")){ lay16.requestFocus(); textPregunta16.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura16,Toast.LENGTH_LONG).show();}
else if (lay16a.getVisibility() == View.VISIBLE && op16a.matches("sin datos")){ lay16a.requestFocus(); textPregunta16a.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura16a,Toast.LENGTH_LONG).show();}
else if (lay16b.getVisibility() == View.VISIBLE && op16b.matches("sin datos")){ lay16b.requestFocus(); textPregunta16b.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura16b,Toast.LENGTH_LONG).show();}
else if (lay17.getVisibility() == View.VISIBLE && op17.matches("sin datos")){ lay17.requestFocus(); textPregunta17.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura17,Toast.LENGTH_LONG).show();}
else if (lay18.getVisibility() == View.VISIBLE && op18.matches("sin datos")){ lay18.requestFocus(); textPregunta18.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura18,Toast.LENGTH_LONG).show();}
else if (lay19.getVisibility() == View.VISIBLE && op19.matches("sin datos")){ lay19.requestFocus(); textPregunta19.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura19,Toast.LENGTH_LONG).show();}
else if (lay19a.getVisibility() == View.VISIBLE && op19a.matches("sin datos")){ lay19a.requestFocus(); textPregunta19a.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura19a,Toast.LENGTH_LONG).show();}
else if (lay19b.getVisibility() == View.VISIBLE && op19b.matches("sin datos")){ lay19b.requestFocus(); textPregunta19b.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura19b,Toast.LENGTH_LONG).show();}
else if (lay20.getVisibility() == View.VISIBLE && op20.matches("sin datos")){ lay20.requestFocus(); textPregunta20.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura20,Toast.LENGTH_LONG).show();}
else if (lay21.getVisibility() == View.VISIBLE && op21.matches("sin datos")){ lay21.requestFocus(); textPregunta21.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura21,Toast.LENGTH_LONG).show();}
else if (lay22.getVisibility() == View.VISIBLE && op22.matches("sin datos")){ lay22.requestFocus(); textPregunta22.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura22,Toast.LENGTH_LONG).show();}
else if (lay23a.getVisibility() == View.VISIBLE && op23a.matches("sin datos")){ lay23a.requestFocus(); textPregunta23a.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura23a,Toast.LENGTH_LONG).show();}
else if (lay23b.getVisibility() == View.VISIBLE && op23b.matches("sin datos")){ lay23b.requestFocus(); textPregunta23b.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura23b,Toast.LENGTH_LONG).show();}
else if (lay23c.getVisibility() == View.VISIBLE && op23c.matches("sin datos")){ lay23c.requestFocus(); textPregunta23c.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura23c,Toast.LENGTH_LONG).show();}
// else if (lay24.getVisibility() == View.VISIBLE && ) {lay24.requestFocus(); textPregunta24.setTextColor(Color.RED); Toast.makeText(getBaseContext(), "CAPTURA:  " + captura24, Toast.LENGTH_LONG).show();}
else if (lay24_1.getVisibility() == View.VISIBLE && op24_1.matches("sin datos")){ lay24_1.requestFocus(); textPregunta24_1.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura24_1,Toast.LENGTH_LONG).show();}
else if (lay24_1a.getVisibility() == View.VISIBLE && op24_1a.matches("sin datos")){ lay24_1a.requestFocus(); textPregunta24_1a.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura24_1a,Toast.LENGTH_LONG).show();}
else if (lay24_1b.getVisibility() == View.VISIBLE && op24_1b.matches("sin datos")){ lay24_1b.requestFocus(); textPregunta24_1b.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura24_1b,Toast.LENGTH_LONG).show();}
else if (lay24_2.getVisibility() == View.VISIBLE && op24_2.matches("sin datos")){ lay24_2.requestFocus(); textPregunta24_2.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura24_2,Toast.LENGTH_LONG).show();}
else if (lay24_2a.getVisibility() == View.VISIBLE && op24_2a.matches("sin datos")){ lay24_2a.requestFocus(); textPregunta24_2a.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura24_2a,Toast.LENGTH_LONG).show();}
else if (lay24_2b.getVisibility() == View.VISIBLE && op24_2b.matches("sin datos")){ lay24_2b.requestFocus(); textPregunta24_2b.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura24_2b,Toast.LENGTH_LONG).show();}
else if (lay24_3.getVisibility() == View.VISIBLE && op24_3.matches("sin datos")){ lay24_3.requestFocus(); textPregunta24_3.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura24_3,Toast.LENGTH_LONG).show();}
else if (lay24_3a.getVisibility() == View.VISIBLE && op24_3a.matches("sin datos")){ lay24_3a.requestFocus(); textPregunta24_3a.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura24_3a,Toast.LENGTH_LONG).show();}
else if (lay24_3b.getVisibility() == View.VISIBLE && op24_3b.matches("sin datos")){ lay24_3b.requestFocus(); textPregunta24_3b.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura24_3b,Toast.LENGTH_LONG).show();}
else if (lay25.getVisibility() == View.VISIBLE && op25.matches("sin datos")){ lay25.requestFocus(); textPregunta25.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura25,Toast.LENGTH_LONG).show();}
else if (lay26.getVisibility() == View.VISIBLE && op26.matches("sin datos")){ lay26.requestFocus(); textPregunta26.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura26,Toast.LENGTH_LONG).show();}
else if (lay27.getVisibility() == View.VISIBLE && op27.matches("sin datos")){ lay27.requestFocus(); textPregunta27.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura27,Toast.LENGTH_LONG).show();}
else if (lay28.getVisibility() == View.VISIBLE && op28.matches("sin datos")){ lay28.requestFocus(); textPregunta28.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura28,Toast.LENGTH_LONG).show();}
else if (lay29.getVisibility() == View.VISIBLE && op29.matches("sin datos")){ lay29.requestFocus(); textPregunta29.setTextColor(Color.RED); Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura29,Toast.LENGTH_LONG).show();}

            else if (opAporta.matches("sin datos")) {Toast.makeText(getBaseContext(), "CAPTURA         :  " + capturaAporta, Toast.LENGTH_LONG).show();
            } else if (opOcupacion.matches("sin datos")) {Toast.makeText(getBaseContext(), "CAPTURA    :  " + capturaOcupacion, Toast.LENGTH_LONG).show();
            } else if (layCuantosCoches.getVisibility() == View.VISIBLE && opCuantosCoches.matches("sin datos")) {Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaCuantosCoches, Toast.LENGTH_LONG).show();
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
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

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
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
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