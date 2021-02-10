package mx.gob.cdmx.semanal_20210206;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService extends FirebaseMessagingService {

    public static final String TAG="NotificationService";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

//        String message = remoteMessage.getString("message");
//        Log.d(TAG, "From: " + from);
//        Log.d(TAG, "Message: " + message);

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if(remoteMessage.getNotification().getBody()!=null){
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }else{
            Log.d(TAG, "Message Notification Body: " + "Sin cuerpo de mensaje");
        }



        Intent i=new Intent(this,Bienvenida.class);
        i.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
        i.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo_completo_cdmx)
                .setAutoCancel(true)
                .setContentText(remoteMessage.getNotification().getBody())
                .setSound(alarmSound)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());

    }

//    public void lanzarNotificacion(View v,RemoteMessage remoteMessage){
//        Intent i=new Intent(this,Bienvenida.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);
//        Uri sonido= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//        .setSmallIcon(R.drawable.logo_completo_cdmx)
//                .setAutoCancel(true)
//                .setContentText(remoteMessage.getNotification().getBody())
//                .setSound(sonido)
//                .setContentIntent(pendingIntent);
//        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0,builder.build());
//    }
}
