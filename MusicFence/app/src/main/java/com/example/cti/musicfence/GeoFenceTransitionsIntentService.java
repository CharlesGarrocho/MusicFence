package com.example.cti.musicfence;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laboratorio on 04/12/17.
 */

public class GeoFenceTransitionsIntentService extends IntentService{

    private static final String TAG = GeoFenceTransitionsIntentService.class.getSimpleName();

    public GeoFenceTransitionsIntentService() {
        super(TAG);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        Location location = geofencingEvent.getTriggeringLocation();
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        int geoFenceTransition = geofencingEvent.getGeofenceTransition();

        Log.i("teste", "teste");

        if (geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            MainActivity.entradaGeofence(latLng);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void sendNotification(String msg){
        Log.i("Notificao", "Geofence " + msg);
        //Intent notificationIntent = MainActivity.makeNotificationIntent(getApplicationContext(),msg);

        //TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        //stackBuilder.addParentStack(MainActivity.class);
        //stackBuilder.addNextIntent(notificationIntent);
        /*PendingIntent notificationPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,criarNotificacao(msg,notificationPendingIntent));*/
    }

    private Notification criarNotificacao(String msg, PendingIntent notifyPendingIntent){
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setColor(Color.BLUE)
                .setContentTitle(msg)
                .setContentText("Notificacao Geofence!!")
                .setContentIntent(notifyPendingIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setAutoCancel(true);
        return notificationBuilder.build();
    }
}
