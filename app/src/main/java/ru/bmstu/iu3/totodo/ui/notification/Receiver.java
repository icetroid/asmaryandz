package ru.bmstu.iu3.totodo.ui.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;

import ru.bmstu.iu3.totodo.R;
import ru.bmstu.iu3.totodo.ui.main.MainActivity;

/**
 * Created by Icetroid on 06.12.2017.
 */

public class Receiver extends BroadcastReceiver {

    public static final String CHANNEL_ID = "ru.bmstu.iu3.totodo.NOTIFICATION";
    public static final String TAG = "Receiver";
    private static final int TASK_REMINDER_NOTIFICATION_ID = 1138;

    @Override
    public void onReceive(Context context, Intent intent) {

//        Log.i(TAG, "intent = " + intent.getStringExtra("text"));
        String text = intent.getStringExtra(Notification.EXTRA_TASK_TEXT);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_option_bar_button)
                        .setContentTitle(context.getResources().getString(R.string.notification_title))
                        .setContentText(text);

        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(TASK_REMINDER_NOTIFICATION_ID, mBuilder.build());

    }


}
