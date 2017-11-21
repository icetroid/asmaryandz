package ru.bmstu.iu3.totodo.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.bmstu.iu3.totodo.data.models.Task;

import static ru.bmstu.iu3.totodo.data.db.TaskContract.*;

/**
 * Created by Icetroid on 21.11.2017.
 */

public class TaskDbHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    public TaskDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_PRIORITY_TABLE = "CREATE TABLE " +
                PriorityEntry.TABLE_NAME + " (" +
                PriorityEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PriorityEntry.COLUMN_PRIORITY + " TEXT NOT NULL" +
                ");";

        final String SQL_CREATE_TASK_TABLE = "CREATE TABLE " +
                TaskEntry.TABLE_NAME + " (" +
                TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TaskEntry.COLUMN_FULL_TEXT + " TEXT NOT NULL," +
                TaskEntry.COLUMN_PRIORITY + " INTEGER NOT NULL," +
                TaskEntry.COLUMN_DATE + " TIMESTAMP NOT NULL," +
                "FOREIGN KEY(" + TaskEntry.COLUMN_PRIORITY + ") REFERENCES " + PriorityEntry.TABLE_NAME + "(" + PriorityEntry._ID + ")" +
                ");";

        final String SQL_INSERT_PRIORITY_A = sqlInsertPriority(1, Task.Priority.A.name());
        final String SQL_INSERT_PRIORITY_B = sqlInsertPriority(2, Task.Priority.B.name());
        final String SQL_INSERT_PRIORITY_C = sqlInsertPriority(3, Task.Priority.C.name());
        final String SQL_INSERT_PRIORITY_D = sqlInsertPriority(4, Task.Priority.D.name());

        sqLiteDatabase.execSQL(SQL_CREATE_PRIORITY_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TASK_TABLE);

        sqLiteDatabase.execSQL(SQL_INSERT_PRIORITY_A);
        sqLiteDatabase.execSQL(SQL_INSERT_PRIORITY_B);
        sqLiteDatabase.execSQL(SQL_INSERT_PRIORITY_C);
        sqLiteDatabase.execSQL(SQL_INSERT_PRIORITY_D);
    }

    private static String sqlInsertPriority(int id, String priority)
    {
         String SQL_INSERT_PRIORITY = "INSERT INTO " + PriorityEntry.TABLE_NAME +
            "(" + PriorityEntry._ID + "," + PriorityEntry.COLUMN_PRIORITY + ")" +
            "VALUES " +
            "(" + id + ", '" + priority + "');";
         return SQL_INSERT_PRIORITY;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TaskEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PriorityEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
