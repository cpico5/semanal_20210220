package mx.gob.cdmx.semanal_20210220.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class Alarm extends BroadcastReceiver
{

  public final String TAG="Alarma";
  private WifiState wifiState;
  Context context;
    @Override
    public void onReceive(Context context, Intent intent) {

      wifiState = new WifiState(context);

      if (wifiState.haveNetworkConnection()) {
        Log.i(TAG,"cqs ==>> entra: "+" Repitiendo");
        new SignupActivity(context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
      }else{
        Log.i(TAG,"cqs ==>> Sin conexión");
      }

  }




  }

