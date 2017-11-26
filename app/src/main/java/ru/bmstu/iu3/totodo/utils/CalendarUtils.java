package ru.bmstu.iu3.totodo.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import ru.bmstu.iu3.totodo.data.models.Task;

import static android.provider.CalendarContract.*;

/**
 * Created by Icetroid on 26.11.2017.
 */

public class CalendarUtils
{
    private static final String TAG = "CalendarUtils";
    public static final String[] EVENT_PROJECTION = new String[] {
            Calendars._ID,                           // 0
            Calendars.ACCOUNT_NAME,                  // 1
            Calendars.CALENDAR_DISPLAY_NAME,         // 2
            Calendars.OWNER_ACCOUNT                  // 3
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
    private static final int MY_PERMISSION_ACCESS_CALENDAR_READ = 1;


    public static Map<Long, String> getCalendars(Context context, Activity activity)
    {
        Map<Long, String> calendars = new HashMap<>();
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_CALENDAR ) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(activity, new String[] {android.Manifest.permission.READ_CALENDAR}, MY_PERMISSION_ACCESS_CALENDAR_READ );
        }
        ContentResolver contentResolver = activity.getContentResolver();
        Uri uri = Calendars.CONTENT_URI;
        String selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND ("
                + Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + Calendars.OWNER_ACCOUNT + " = ?))";
        Cursor cursor = contentResolver.query(uri, EVENT_PROJECTION, null, null, null);


        while (cursor.moveToNext()) {
            long calID = 0;
            String displayName = null;
            String accountName = null;
            String ownerName = null;

            // Get the field values
            calID = cursor.getLong(PROJECTION_ID_INDEX);
            displayName = cursor.getString(PROJECTION_DISPLAY_NAME_INDEX);
            accountName = cursor.getString(PROJECTION_ACCOUNT_NAME_INDEX);
            ownerName = cursor.getString(PROJECTION_OWNER_ACCOUNT_INDEX);

            calendars.put(calID, displayName + "/" + accountName);
        }
        return calendars;
    }

    //TODO do not use this
    public static void test(Context context, Activity activity)
    {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_CALENDAR ) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(activity, new String[] {android.Manifest.permission.READ_CALENDAR}, MY_PERMISSION_ACCESS_CALENDAR_READ );
        }
        ContentResolver contentResolver = activity.getContentResolver();
        Uri uri = Calendars.CONTENT_URI;
        String selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND ("
                + Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + Calendars.OWNER_ACCOUNT + " = ?))";
        Cursor cursor = contentResolver.query(uri, EVENT_PROJECTION, null, null, null);


        while (cursor.moveToNext()) {
            long calID = 0;
            String displayName = null;
            String accountName = null;
            String ownerName = null;

            // Get the field values
            calID = cursor.getLong(PROJECTION_ID_INDEX);
            displayName = cursor.getString(PROJECTION_DISPLAY_NAME_INDEX);
            accountName = cursor.getString(PROJECTION_ACCOUNT_NAME_INDEX);
            ownerName = cursor.getString(PROJECTION_OWNER_ACCOUNT_INDEX);



        }


    }



    public static long insertTaskIntoCalendar(Context context, Activity activity, Task task, long calendarId)
    {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_CALENDAR ) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(activity, new String[] {android.Manifest.permission.WRITE_CALENDAR}, MY_PERMISSION_ACCESS_CALENDAR_READ );
        }
        Calendar beginTime = Calendar.getInstance();
        TimeZone tz = beginTime.getTimeZone();
        String timezone = tz.getID();
        beginTime.setTime(task.getDate());
        long startMillis = beginTime.getTimeInMillis();
        String description = task.getText();
        ContentResolver contentResolver = activity.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(Events.DTSTART, startMillis);
        //values.put(Events.DTEND, endMillis);
        //TODO Change event title
        values.put(Events.TITLE, "Totodo");
        values.put(Events.DESCRIPTION, description);
        values.put(Events.CALENDAR_ID, calendarId);
        values.put(Events.EVENT_TIMEZONE, timezone);
        values.put(Events.DURATION, "PT1H");

        Uri uri = contentResolver.insert(Events.CONTENT_URI, values);

        long eventId = Long.parseLong(uri.getLastPathSegment());
        Log.i(TAG, "event = " + eventId);

        return eventId;
    }
}
