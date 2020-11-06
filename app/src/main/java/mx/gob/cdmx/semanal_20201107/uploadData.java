package mx.gob.cdmx.semanal_20201107;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class uploadData {

	private static final String TAG="UploadData";

	static Nombre nom = new Nombre();
	static String nombreEncuesta = nom.nombreEncuesta();
	static String upLoadServerUriBase = "https://opinion.cdmx.gob.mx/cgi-bin/php/recibeBases" + nombreEncuesta + ".php?encuesta=" + nombreEncuesta + "";
	static String upLoadServerUriAudio = "https://opinion.cdmx.gob.mx/cgi-bin/php/recibeAudios" + nombreEncuesta + ".php?encuesta=" + nombreEncuesta + "";
	static int serverResponseCode = 0;

	static Calendar c = Calendar.getInstance();

	static SimpleDateFormat df1 = new SimpleDateFormat("yyyMMdd");
	static String formattedDate1 = df1.format(c.getTime());

	static SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
	static String formattedDate2 = df2.format(c.getTime());

	static SimpleDateFormat df3 = new SimpleDateFormat("yyy-MM-dd");
	static String formattedDate3 = df3.format(c.getTime());

	static SimpleDateFormat df4 = new SimpleDateFormat("yyyMMdd");
	static String formattedDateFecha = df4.format(c.getTime());

	static Context ctx;

//	public static String sacaImei() {
//		TelephonyManager TelephonyMgr = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
//		if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//			String szImei = TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE
//			System.out.println("Mi Número: " + szImei);
//			return szImei;
//		}
//		String szImei = TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE
//		System.out.println("Mi Número: " + szImei);
//		return szImei;
//	}



	//Enviar Base
	public static int uploadBase(String sourceFileUri) {

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

	static class UpdateBases extends AsyncTask<String, Float, String> {

		protected void onPreExecute() {
			super.onPreExecute();
		}


		@Override
		protected String doInBackground(String... params) {

			String imei=params[0];
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
						s[x] = pathBase + "/" + nombreEncuesta + "_" + imei;
//						s[x] = pathBase + "/" + nombreEncuesta;
						Log.i(TAG, " =====> Nombre del Archivo: " + s[x]);
						uploadBase(s[x]);
					}
				} else {
					Log.i(TAG, " =====> lista 2: " + "No existe el directorio");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.i(TAG, " =====> error lista 3: " + "_" + e.getMessage()+" "+imei);
			}
			return null;
		}

		//tomo
		protected void onPostExecute(String date2) {
			super.onPostExecute(date2);
//			Toast.makeText(ctx, "Archivo Enviado", Toast.LENGTH_LONG).show();
		}
	}

	//Enviar Audios
	static class UpdateAudios extends AsyncTask<String, Float, String> {

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
			final String pathAudios = sdCard.getAbsolutePath() + "/" + nombreEncuesta +"-Audio" + formattedDateFecha + "/";

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

	public static int uploadAudios(String sourceFileUri) {

		File sdCard;
		sdCard = Environment.getExternalStorageDirectory();
		//final String pathFotos = sdCard.getAbsolutePath() + "/"+ nombreEncuesta+"-Audio"+fech;
		final String pathAudios = sdCard.getAbsolutePath() + nombreEncuesta +"-Audio" + formattedDateFecha + "/";

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

				}

				//close the streams //
				fileInputStream.close();
				dos.flush();
				dos.close();

			} catch (MalformedURLException ex) {

//			        dialog.dismiss();
				ex.printStackTrace();

				Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + "Upload file to server "+ "error: " + ex.getMessage());

//			        Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
			} catch (Exception e) {

//			        dialog.dismiss();
				e.printStackTrace();
				Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + "Upload file to server Exception "+ "Exception : "+ e.getMessage());

//			        Log.e("Upload file to server Exception", "Exception : "
//			                                         + e.getMessage(), e);
			}
			return serverResponseCode;

		} // End else block
	}

}	 