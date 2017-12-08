package ru.bmstu.iu3.totodo.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ru.bmstu.iu3.totodo.data.models.Task;

import static ru.bmstu.iu3.totodo.data.db.TaskContract.*;

/**
 * Created by Icetroid on 21.11.2017.
 */

public class TaskDb
{
    private static final String TAG = "TaskDb";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private TaskDbHelper mTaskDbHelper;
    private SQLiteDatabase db;
    public TaskDb(Context context)
    {
        mTaskDbHelper = new TaskDbHelper(context);
    }

    public long insertTask(Task task)
    {
        db = mTaskDbHelper.getWritableDatabase();
//        Log.i(TAG, task.toString());
        String fullText = task.getText();
        String priority = String.valueOf(getPriorityId(task.getPriority()));
        Date date = task.getDate();
        String formatDate = new SimpleDateFormat(DATE_FORMAT).format(date);
        boolean addToCalendar = task.isAddedToCalendar();
        int notifyTime = task.getNotifyTime();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskEntry.COLUMN_FULL_TEXT, fullText);
        contentValues.put(TaskEntry.COLUMN_PRIORITY, priority);
        contentValues.put(TaskEntry.COLUMN_DATE, formatDate);
        contentValues.put(TaskEntry.COLUMN_CALENDAR, addToCalendar);
        contentValues.put(TaskEntry.COLUMN_NOTIFY_TIME, notifyTime);

        long row = db.insert(TaskEntry.TABLE_NAME, null, contentValues);
//        Log.i(TAG, "row added " + row);
        return row;

    }

    public List<Task> getTasksWithPriorityOrderByDate(Task.Priority priority, boolean desc)
    {
        String order = TaskEntry.TABLE_NAME + "." + TaskEntry.COLUMN_DATE;
        if(desc)
        {
            order += " DESC";
        }
        List<Task> tasks = getTasksWithPriority(priority, order);
        return tasks;
    }

    public List<Task> getTasksWithPriority(Task.Priority priority, String orderBy)
    {
        String selection = PriorityEntry.TABLE_NAME + "." + PriorityEntry.COLUMN_PRIORITY + " = ?";
        String[] selectionArgs = new String[]{priority.name()};
        List<Task> tasks = getTasks(selection,selectionArgs, null,null,orderBy);
        return tasks;
    }

    public List<Task> getTasksWithPriorityNoOrder(Task.Priority priority)
    {
        List<Task> tasks = getTasksWithPriority(priority, null);
        return tasks;
    }

    public int updateTask(Task task)
    {
        db = mTaskDbHelper.getReadableDatabase();
        String fullText = task.getText();
        String priority = String.valueOf(getPriorityId(task.getPriority()));
        Date date = task.getDate();
        String formatDate = new SimpleDateFormat(DATE_FORMAT).format(date);
        boolean addToCalendar = task.isAddedToCalendar();
        int notifyTime = task.getNotifyTime();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskEntry.COLUMN_FULL_TEXT, fullText);
        contentValues.put(TaskEntry.COLUMN_PRIORITY, priority);
        contentValues.put(TaskEntry.COLUMN_DATE, formatDate);
        contentValues.put(TaskEntry.COLUMN_CALENDAR, addToCalendar);
        contentValues.put(TaskEntry.COLUMN_NOTIFY_TIME, notifyTime);

        String selection = TaskEntry._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(task.getId()) };

        int count = db.update(
                TaskEntry.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs);
        Log.i(TAG, "count " + count + " text " + fullText);
        return count;
    }

    public List<Task> getTasks(String selection, String[] selectionsArgs, String groupBy, String having, String orderBy)
    {
        db = mTaskDbHelper.getReadableDatabase();
        String[] projection = {
                TaskEntry.TABLE_NAME + "." + TaskEntry._ID,
                TaskEntry.TABLE_NAME + "." + TaskEntry.COLUMN_FULL_TEXT,
                TaskEntry.TABLE_NAME + "." + TaskEntry.COLUMN_DATE,
                TaskEntry.TABLE_NAME + "." + TaskEntry.COLUMN_CALENDAR,
                TaskEntry.TABLE_NAME + "." + TaskEntry.COLUMN_NOTIFY_TIME,
                PriorityEntry.TABLE_NAME + "." + PriorityEntry.COLUMN_PRIORITY
        };
        Cursor cursor = db.query(TaskEntry.TABLE_NAME + " LEFT OUTER JOIN " + PriorityEntry.TABLE_NAME + " ON " + TaskEntry.TABLE_NAME + "." + TaskEntry.COLUMN_PRIORITY + "=" + PriorityEntry.TABLE_NAME + "." + PriorityEntry._ID,
                projection, selection, selectionsArgs, groupBy, having, orderBy);
        List<Task> tasks = new LinkedList<>();

        while(cursor.moveToNext())
        {
            Task task = new Task();
            long id = cursor.getLong(0);
            String text = cursor.getString(1);
            String date = cursor.getString(2);
            boolean addToCalendar = cursor.getInt(3) == 1 ? true : false;
            int notifyTime = cursor.getInt(4);
            String priority = cursor.getString(5);

//            Log.i(TAG, "1 = " + cursor.getString(1) + " 2 = " + cursor.getString(2));
//            String priority = cursor.getString(cursor.getColumnIndex())
            Date formatDate = null;
            try {
                formatDate = new SimpleDateFormat(DATE_FORMAT).parse(date);
            } catch (ParseException e) {
                Log.e(TAG, e.getMessage());

            }
            task.setId(id);
            task.setText(text);
            task.setPriority(priority);
            task.setFullDate(formatDate);
            task.setAddedToCalendar(addToCalendar);
            task.setNotifyTime(notifyTime);
            tasks.add(task);

//            Log.i(TAG, "Task " + task.getText() + " " + task.getPriority());

        }
        cursor.close();
//        Log.i(TAG, "found " + tasks.size());
        return tasks;


    }

    public List<Task> getAllTasks()
    {
        List<Task> tasks = getTasks(null,null,null,null,null);
        return tasks;
    }

    public void deleteAllTasks()
    {
        db = mTaskDbHelper.getWritableDatabase();
        db.delete(TaskEntry.TABLE_NAME, null, null);
    }

    public long getPriorityId(Task.Priority priority)
    {
        db = mTaskDbHelper.getReadableDatabase();
        String selection = PriorityEntry.COLUMN_PRIORITY + " = ?";
        String[] selectionArgs = new String[]{priority.name()};
        Cursor cursor = db.query(PriorityEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if(!cursor.moveToFirst())
        {
            return -1;
        }
        long id = cursor.getLong(cursor.getColumnIndex(PriorityEntry._ID));
        cursor.close();
        return id;
    }


}
