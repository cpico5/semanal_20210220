package mx.gob.cdmx.biometrico_semanal;

import android.Manifest;
import android.content.Context;
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
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Bienvenida extends AppCompatActivity {

    private static final String TAG = Bienvenida.class.getName();
    UsuariosSQLiteHelper3 usdbh3;
    private SQLiteDatabase db3;

    Nombre nom = new Nombre();
    String nombreEncuesta = nom.nombreEncuesta();
    String upLoadServerUriBase = "https://opinion.cdmx.gob.mx/cgi-bin/php/recibeBases" + nombreEncuesta + ".php?encuesta=" + nombreEncuesta + "";
    String upLoadServerUriAudio = "https://opinion.cdmx.gob.mx/cgi-bin/php/recibeAudios" + nombreEncuesta + ".php?encuesta=" + nombreEncuesta + "";
    int serverResponseCode = 0;

    Calendar c = Calendar.getInstance();

    SimpleDateFormat df1 = new SimpleDateFormat("yyyMMdd");
    String formattedDate1 = df1.format(c.getTime());

    SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
    String formattedDate2 = df2.format(c.getTime());

    SimpleDateFormat df3 = new SimpleDateFormat("yyy-MM-dd");
    String formattedDate3 = df3.format(c.getTime());

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

        }else{

            usdbh3 = new UsuariosSQLiteHelper3(this);
            db3 = usdbh3.getReadableDatabase();
        }


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


        if (!verificaConexion(this)) {
            Toast.makeText(getBaseContext(),"Sin conexión",
                    Toast.LENGTH_LONG).show();
            //this.finish();
        }
        else{

            new uploadData.UpdateBases().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            new uploadData.UpdateAudios().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }



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

        }
        else
        {
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

                Log.i("TAG", "HTTP Response is : "+ serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){

                    runOnUiThread(new Runnable() {
                        public void run() {

                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                    +" http://www.androidexample.com/media/uploads/"
                                    +"20161124_002_359083065132816_1.jpg";

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



                Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + "Upload file to server "+ "error: " + ex.getMessage());

//				                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

//				                dialog.dismiss();
                e.printStackTrace();

                Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + "Upload file to server Exception "+ "Exception : "+ e.getMessage());

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
            final String pathAudios = sdCard.getAbsolutePath() + "/" + nombreEncuesta +"-Audio" + formattedDate3 + "/";

            String sDirectorio = pathAudios;
            final File f = new File(sDirectorio);
            Log.i(TAG,"lista"+pathAudios);

//						final String customURL = "https://opinion.cdmx.gob.mx/cgi-bin/fotos/programas_sociales/";
            final String customURL = "https://opinion.cdmx.gob.mx/audios/"+nombreEncuesta+ "/";

            Log.i(TAG, " =====> URL audios: " + customURL);
            Log.i(TAG, " =====> lista audios 1: " + pathAudios);

            File F = new File(pathAudios);

            try {

                if (F.exists()) {

                    File[] ficheros = F.listFiles();

                    for (int i = 0; i <ficheros.length; i++) {
                        //Simulamos cierto retraso
                        try {Thread.sleep(500); }
                        catch (InterruptedException e) {}

                        publishProgress(i/(float)(ficheros.length)); //Actualizamos los valores
                    }



                    String[] s = new String[ficheros.length];
                    String[] t = new String[ficheros.length];
                    for (int x = 0; x < ficheros.length; x++) {
                        Log.i(TAG, " =====> lista audios: " + ficheros[x].getName());
                        s[x] = pathAudios + "/" + ficheros[x].getName();
                        t[x] = ficheros[x].getName();

//								 uploadFotos(s[x],date2);


                        URL u = new URL (customURL+t[x]);
                        Log.i(TAG, " =====> Archivo Audios custom: "+customURL+t[x] );
                        HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection ();
                        huc.setRequestMethod ("GET");  //OR  huc.setRequestMethod ("HEAD");
                        huc.connect () ;
                        huc.getResponseCode();
                        Log.i(TAG, " =====> Archivo:  lista De Audios ==>" + huc.getResponseCode());
                        if(huc.getResponseCode()==200){

                            //moveFile(pathFotosN, t[x], pathFotosF);
                            Log.i(TAG, " =====> Archivo:  En el servidor custom no hace nada==>" + t[x] );

                        }else if(huc.getResponseCode()==404){

                            uploadAudios(s[x]);
                            Log.i(TAG, " =====> Archivo:  Enviado al servidor custom==>" + t[x] );


                        }

                    }
                    // first parameter is d files second parameter is zip file name

                } else {
                    Log.i(TAG, " =====> lista 2: " + "No existe el directorio");
                }
                // first parameter is d files second parameter is zip file name

            } catch (Exception e) {
                String stackTrace = Log.getStackTraceString(e);
                Log.i("Manda Audios","Error Manda Audios"+ stackTrace);
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

    public int uploadAudios(String sourceFileUri) {

        File sdCard;
        sdCard = Environment.getExternalStorageDirectory();
        //final String pathFotos = sdCard.getAbsolutePath() + "/"+ nombreEncuesta+"-Audio"+fech;
        final String pathAudios = sdCard.getAbsolutePath() + nombreEncuesta +"-Audio" + formattedDate3 + "/";

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

        }
        else
        {
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

                Log.i("uploadFile", "HTTP Response is : "+ serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){

                    runOnUiThread(new Runnable() {
                        public void run() {

                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                    +" http://www.androidexample.com/media/uploads/"
                                    +"20161124_002_359083065132816_1.jpg";

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

                Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + "Upload file to server "+ "error: " + ex.getMessage());

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
                Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + "Upload file to server Exception "+ "Exception : "+ e.getMessage());

//			        Log.e("Upload file to server Exception", "Exception : "
//			                                         + e.getMessage(), e);
            }
            return serverResponseCode;

        } // End else block
    }




}
