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

    public static final String EXTRA_TASK_TEXT = "ru.bmstu.iu3.totodo.taskText";

    private static final int MAX_TEXT_LENGTH = 20;

    public static void setNotify(Context context, Task task)
    {
        if(task.getNotifyTime() == 0)
        {
            return;
        }
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
}
