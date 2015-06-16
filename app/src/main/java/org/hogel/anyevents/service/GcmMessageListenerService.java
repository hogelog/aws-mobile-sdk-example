package org.hogel.anyevents.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import org.hogel.anyevents.R;

public class GcmMessageListenerService extends com.google.android.gms.gcm.GcmListenerService {
    private static final String TAG = GcmMessageListenerService.class.getSimpleName();

    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();

        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        sendNotification(message);
    }

    private void sendNotification(final String message) {
        Notification notification = new NotificationCompat.Builder(this)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setTicker(message)
            .setWhen(System.currentTimeMillis())
            .setContentTitle("title: " + message)
            .setContentText("content: " + message)
            .build()
        ;
        notificationManager.notify(1, notification);
    }
}
