package ru.bmstu.iu3.totodo.ui.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Date;

import ru.bmstu.iu3.totodo.R;
import ru.bmstu.iu3.totodo.data.models.Task;
import ru.bmstu.iu3.totodo.ui.main.MainActivity;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Icetroid on 06.12.2017.
 */

public class Notification
{
    private static final String TAG = "Notification";
    public static final String NOTIFY_CHANNEL_NAME = "TASK NOTIFICATION";
    private static final int TASK_REMINDER_PENDING_INTENT_ID = 3417;

    public static final String EXTRA_TASK_TEXT = "ru.bmstu.iu3.totodo.taskText";

    private static final int MAX_TEXT_LENGTH = 20;

    //    private Context context;
//    public Notification(Context context)
//    {
//        this.context = context;
//    }
//    public void buildNotification()
//    {
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(context, "totodo")
//                        .setSmallIcon(R.drawable.ic_option_bar_button)
//                        .setContentTitle("My notification")
//                        .setContentText("Hello World!");
//
//        Intent resultIntent = new Intent(context, MainActivity.class);
//
//        // Because clicking the notification opens a new ("special") activity, there's
//        // no need to create an artificial back stack.
//        PendingIntent resultPendingIntent =
//                PendingIntent.getActivity(
//                        context,
//                        0,
//                        resultIntent,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//
//        mBuilder.setContentIntent(resultPendingIntent);
//
//
//        int mNotificationId = 001;
//// Gets an instance of the NotificationManager service
//        NotificationManager mNotifyMgr =
//                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//// Builds the notification and issues it.
//        mNotifyMgr.notify(mNotificationId, mBuilder.build());
//        Log.i(TAG, "notify");
//
//    }
//
//    public static final int ACTION_ONE_INTENT_ID = 4;
//
//
    public static void setNotify(Context context, Task task)
    {
        Intent intent = new Intent(context, Receiver.class);
        intent.putExtra(EXTRA_TASK_TEXT, formatTaskText(task.getText()));

        long notifyTime = task.getNotifyTime() * 1000;
        long date = task.getDate().getTime();
        long time = date - notifyTime;
//        Log.i(TAG, "date" + date2 + " " + current);
        if(time <= System.currentTimeMillis())
        {
            return;
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 12, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time , pendingIntent);
    }
//
//    private static NotificationCompat.Action actionOne(Context context)
//    {
//        Intent intent = new Intent(context, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getService(
//                context,
//                ACTION_ONE_INTENT_ID,
//                intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Action action = new NotificationCompat.Action(R.drawable.ic_option_bar_button, "One", pendingIntent);
//        return action;
//    }
//
//    public static void remind(Context context, Task task)
//    {
//        NotificationManager notificationManager = (NotificationManager)
//                context.getSystemService(Context.NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel mChannel = new NotificationChannel(
//                    CHANNEL_ID,
//                    NOTIFY_CHANNEL_NAME,
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            notificationManager.createNotificationChannel(mChannel);
//        }
//
//        String text = task.getText();
//        text = formatTaskText(text);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
//                .setContentTitle(context.getResources().getString(R.string.notification_title))
//                .setSmallIcon(R.drawable.ic_option_bar_button)
//                .setContentText(text)
//                .setDefaults(android.app.Notification.DEFAULT_VIBRATE)
//                .setContentIntent(contentIntent(context, task))
////                .addAction(actionOne(context))
//                .setAutoCancel(true);
//        notificationManager.notify(TASK_REMINDER_NOTIFICATION_ID, builder.build());
//
//    }

    private static String formatTaskText(String text)
    {
        if(text == null)
        {
            return "";
        }
        if(text.length() > MAX_TEXT_LENGTH)
        {
            text = text.substring(0, MAX_TEXT_LENGTH);
            text = text.substring(0, text.lastIndexOf(" "));
            text += "...";
        }
        return text;
    }

//    private static PendingIntent contentIntent(Context context, Task task) {
//        Intent startActivityIntent = new Intent(context, MainActivity.class);
//
//        return PendingIntent.getActivity(
//                context,
//                TASK_REMINDER_PENDING_INTENT_ID,
//                startActivityIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//    }
}
