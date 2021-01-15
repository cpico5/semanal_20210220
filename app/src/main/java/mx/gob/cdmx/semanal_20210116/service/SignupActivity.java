package mx.gob.cdmx.semanal_20210116.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mx.gob.cdmx.semanal_20210116.Nombre;
import mx.gob.cdmx.semanal_20210116.UsuariosSQLiteHelper;
import mx.gob.cdmx.semanal_20210116.Utils;
import mx.gob.cdmx.semanal_20210116.db.DaoManager;
import mx.gob.cdmx.semanal_20210116.model.Usuario;
import mx.gob.cdmx.semanal_20210116.UsuariosSQLiteHelper3;
import mx.gob.cdmx.semanal_20210116.model.encuestas;


public class SignupActivity extends AsyncTask<String, Void, String> {

    final static String TAG = "SignupActivity";
    private Context context;
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    String imei_num;
    UsuariosSQLiteHelper3 usdbh3;
    private SQLiteDatabase db3;

    UsuariosSQLiteHelper usdbh;
    private SQLiteDatabase db;
    private encuestas semanal_20201031 = new encuestas();

    public static final int PERMISSION_REQUEST_CODE = 1;
    private WifiState wifiState;
    private Imei imei;

    private encuestas estudios_cdmxs = new encuestas();

    private String latitud;
    private String longitud;
    SimpleDateFormat sdFecha = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat sdHora = new SimpleDateFormat("HH:mm:ss");
    String fechaStr = "";
    String horaStr = "";

    DaoManager daoManager;

    private Usuario usuario;

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    Calendar c = Calendar.getInstance();
    SimpleDateFormat df3 = new SimpleDateFormat("yyyMMdd");
    String formattedDateFecha = df3.format(c.getTime());
    SimpleDateFormat df5 = new SimpleDateFormat("HH:mm");
    String formattedDateHora = df5.format(c.getTime());
    SimpleDateFormat ayer = new SimpleDateFormat("yyyyMMdd");
    String formattedDateAyer = ayer.format(yesterday());

    int serverResponseCode = 0;

    static InputStream is2 = null;


    public SignupActivity(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onPreExecute() {

        wifiState = new WifiState(context);

        try {
            imei = new Imei(this.context);
            imei_num = imei.getImei().toString();
            Log.i(null, "El chip: " + imei_num);

            usdbh3 = new UsuariosSQLiteHelper3(this.context);
            db3 = usdbh3.getWritableDatabase();
            latitud = "";
            longitud = "";

            usdbh = new UsuariosSQLiteHelper(this.context, null, null, 0, Nombre.encuesta);
            db = usdbh.getWritableDatabase();


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected String doInBackground(String... arg0) {


        if (wifiState.haveNetworkConnection()) {


            fechaStr = sdFecha.format(new Date());
            horaStr = sdHora.format(new Date());


            imei = new Imei(this.context);
            Nombre nom = new Nombre();
            String nombreEncuesta = nom.nombreEncuesta();

            Utils.info(TAG,"Entro a SignupActivity","Repitiendo");

//            String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_"+ imei.getImei() + "";
//
//            usdbh = new UsuariosSQLiteHelper(context, "F", null, 1, DATABASE_NAME);
//            db = usdbh.getReadableDatabase();
//
//            final DaoManager daoManager = new DaoManager(db);
//            semanal_20201031 = (encuestas) daoManager.findByNoSend(encuestas.class, "0", null) ;

//            if (semanal_20201031 != null){
//
//                ContentValues values = new ContentValues();
//
//                values.put("consecutivo",semanal_20201031.getId());
//                values.put("consecutivo_diario",semanal_20201031.getConsecutivo_diario());
//                values.put("equipo",semanal_20201031.getEquipo());
//                values.put("usuario",semanal_20201031.getUsuario());
//                values.put("nombre_encuesta",semanal_20201031.getNombre_encuesta());
//                values.put("fecha",semanal_20201031.getFecha());
//                values.put("hora",semanal_20201031.getHora());
//                values.put("imei",semanal_20201031.getImei());
//                values.put("seccion",semanal_20201031.getSeccion());
//                values.put("latitud",semanal_20201031.getLatitud());
//                values.put("longitud",semanal_20201031.getLongitud());
//
//
//                values.put("pregunta_1",semanal_20201031.getPregunta_1());
//
//
//                values.put("pregunta_2",semanal_20201031.getPregunta_2());
//
//
//                values.put("pregunta_3",semanal_20201031.getPregunta_3());
//
//
//                values.put("pregunta_4",semanal_20201031.getPregunta_4());
//
//
//                values.put("pregunta_5",semanal_20201031.getPregunta_5());
//
//
//                values.put("pregunta_6",semanal_20201031.getPregunta_6());
//
//
//                values.put("pregunta_7",semanal_20201031.getPregunta_7());
//
//
//                values.put("pregunta_8",semanal_20201031.getPregunta_8());
//
//
//                values.put("pregunta_9",semanal_20201031.getPregunta_9());
//
//
//                values.put("pregunta_c1",semanal_20201031.getPregunta_c1());
//
//
//                values.put("pregunta_c2",semanal_20201031.getPregunta_c2());
//
//
//                values.put("pregunta_c3",semanal_20201031.getPregunta_c3());
//
//
//                values.put("pregunta_c3a",semanal_20201031.getPregunta_c3a());
//
//
//                values.put("pregunta_c4",semanal_20201031.getPregunta_c4());
//
//
//                values.put("pregunta_c4a",semanal_20201031.getPregunta_c4a());
//
//
//                values.put("pregunta_c4b",semanal_20201031.getPregunta_c4b());
//
//
//                values.put("pregunta_c4c",semanal_20201031.getPregunta_c4c());
//
//
//                values.put("pregunta_c4d",semanal_20201031.getPregunta_c4d());
//
//
//                values.put("pregunta_c5",semanal_20201031.getPregunta_c5());
//
//
//                values.put("pregunta_c5a",semanal_20201031.getPregunta_c5a());
//
//
//                values.put("pregunta_c5b",semanal_20201031.getPregunta_c5b());
//
//
//                values.put("pregunta_c5c",semanal_20201031.getPregunta_c5c());
//
//
//                values.put("pregunta_c5d",semanal_20201031.getPregunta_c5d());
//
//
//                values.put("pregunta_c5e",semanal_20201031.getPregunta_c5e());
//
//
//                values.put("pregunta_c5f",semanal_20201031.getPregunta_c5f());
//
//
//                values.put("pregunta_c5g",semanal_20201031.getPregunta_c5g());
//
//
//                values.put("pregunta_c6",semanal_20201031.getPregunta_c6());
//
//
//                values.put("pregunta_c6a",semanal_20201031.getPregunta_c6a());
//
//
//                values.put("pregunta_c7",semanal_20201031.getPregunta_c7());
//
//
//                values.put("pregunta_c8",semanal_20201031.getPregunta_c8());
//
//
//                values.put("pregunta_c9",semanal_20201031.getPregunta_c9());
//
//
//                values.put("pregunta_c9a",semanal_20201031.getPregunta_c9a());
//
//
//                values.put("pregunta_c9b",semanal_20201031.getPregunta_c9b());
//
//
//                values.put("pregunta_c10",semanal_20201031.getPregunta_c10());
//
//
//                values.put("pregunta_c10a",semanal_20201031.getPregunta_c10a());
//
//
//                values.put("pregunta_c10b",semanal_20201031.getPregunta_c10b());
//
//
//                values.put("pregunta_c11",semanal_20201031.getPregunta_c11());
//
//
//                values.put("aporta", semanal_20201031.getAporta());
//                values.put("ocupacion", semanal_20201031.getOcupacion());
//                values.put("cuantos_coches", semanal_20201031.getCuantos_coches());
//                values.put("cuartos", semanal_20201031.getCuartos());
//                values.put("cuartos_dormir", semanal_20201031.getCuartos_dormir());
//                values.put("focos", semanal_20201031.getFocos());
//                values.put("banos", semanal_20201031.getBanos());
//                values.put("regadera", semanal_20201031.getRegadera());
//                values.put("estufa", semanal_20201031.getEstufa());
//                values.put("edad", semanal_20201031.getEdad());
//                values.put("genero", semanal_20201031.getGenero());
//                values.put("tipo_vivienda", semanal_20201031.getTipo_vivienda());
//                values.put("tipo_piso", semanal_20201031.getTipo_piso());
//                values.put("abandono", semanal_20201031.getAbandono());
//                values.put("suma", semanal_20201031.getSuma());
//                values.put("status", semanal_20201031.getStatus());
//                values.put("tiempo", semanal_20201031.getTiempo());
//                values.put("tipo_captura", semanal_20201031.getTipo_captura());
//
//
//                DatoContent datoContent = new DatoContent();
//                List<DatoContent> listaContenido = new ArrayList();
//                Set<Map.Entry<String, Object>> s = values.valueSet();
//                Iterator itr = s.iterator();
//                while (itr.hasNext()) {
//                    Map.Entry me = (Map.Entry) itr.next();
//                    String key = me.getKey().toString();
//                    Object value = me.getValue();
//
//                    datoContent = new DatoContent();
//                    datoContent.setKey(key);
//                    datoContent.setValue(String.valueOf(value));
//
//                    listaContenido.add(datoContent);
//                }
//
//                Gson gson = new Gson();
//                Type collectionType = new TypeToken<List<DatoContent>>() {
//                }.getType();
//                String json = gson.toJson(listaContenido, collectionType);
//
//                RequestParams params = new RequestParams();
//                params.put("api", "guarda_encuesta");
//                params.put("encuestas", encuesta);
//                params.put("data", json);
//
//
//                AsyncHttpClient client = new AsyncHttpClient();
//                client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
//                //client.addHeader("Authorization", "Bearer " + usuario.getToken());
//                client.setTimeout(60000);
//
//                RequestHandle requestHandle = client.post(customURL, params, new AsyncHttpResponseHandler(Looper.getMainLooper()) {
//
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                        Log.d(TAG, "cqs -----------> Respuesta OK ");
//                        Log.d(TAG, "cqs -----------> " + new String(responseBody));
//                        try {
//
//
//                            String json = new String(responseBody);
//
//                            if (json != null && !json.isEmpty()) {
//
//                                Gson gson = new Gson();
//                                JSONObject jsonObject = new JSONObject(json);
//                                Log.d(TAG, "cqs -----------> Data: " + jsonObject.get("data"));
//
//                                String login = jsonObject.getJSONObject("response").get("code").toString();
//                                if (Integer.valueOf(login) == 1) {
//                                    semanal_20201031.setEnviado("1");
//                                    daoManager.update(semanal_20201031, "id=?", new String[]{String.valueOf(semanal_20201031.getId())});
//
//                                } else {
//                                }
//                            }
//
//
//                        } catch (Exception e) {
//                            Log.e(TAG, e.getMessage());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                        try {
//                            Log.e(TAG, "cqs-----------------> existe un error en la conexi?n -----> " + error.getMessage());
//                            if (responseBody != null)
//                                Log.d(TAG, "cqs -----------> " + new String(responseBody));
//
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//
//                        if (statusCode != 200) {
//                            Log.e(TAG, "Existe un error en la conexi?n -----> " + error.getMessage());
//                            if (responseBody != null)
//                                Log.d(TAG, "cqs -----------> " + new String(responseBody));
//
//                        }
//
//                    }
//                });
//
//
//            } else {
//            }


        }

        return null;

    }


    @Override
    protected void onPostExecute(String result) {
        //db.close();
        System.gc();
    }


}

