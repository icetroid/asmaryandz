package ru.bmstu.iu3.totodo.utils;

import android.content.Context;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ru.bmstu.iu3.totodo.data.db.TaskDb;
import ru.bmstu.iu3.totodo.data.models.Task;

/**
 * Created by Icetroid on 15.11.2017.
 */

public class FakeDataUtils {
    private static final String TAG = "FakeDataUtils";
    private static final String LOREM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.";
    public static List<Task> getTasks(int length){
        List<Task> tasks = new LinkedList<>();
        for(int i = 0; i < length; i++){
            Task task = new Task();
            task.setText(i + " " + LOREM);
            Random random = new Random();
            int priority = random.nextInt(Task.Priority.values().length);
            task.setPriority(Task.Priority.getPriority(priority));

            Calendar cal = Calendar.getInstance();
            int year = random.nextInt(10) + 2000;
            int month = random.nextInt(10) + 1;
            int day = random.nextInt(25) + 1;
            int hour = random.nextInt(20) + 1;
            int min = random.nextInt(50) + 1;
            int sec = random.nextInt(50) + 1;
            cal.set(year, month, day, hour, min, sec);
            task.setFullDate(cal.getTime());
            tasks.add(task);
//            Log.i(TAG, "Priority " + task.getPriority());
        }

        return tasks;
    }

    public static void insertTasksIntoDb(Context context, int length)
    {
        TaskDb db = new TaskDb(context);
        List<Task> tasks = getTasks(length);
        for(int i = 0;i<length;i++)
        {
            db.insertTask(tasks.get(i));
        }
    }
}
