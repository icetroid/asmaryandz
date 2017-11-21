package ru.bmstu.iu3.totodo.utils;

import android.content.Context;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ru.bmstu.iu3.totodo.data.db.TaskDb;
import ru.bmstu.iu3.totodo.data.models.Task;

/**
 * Created by Icetroid on 15.11.2017.
 */

public class FakeDataUtils {
    public static List<Task> getTasks(int length){
        List<Task> tasks = new LinkedList<>();
        for(int i = 0; i < length; i++){
            Task task = new Task(i, "Text " + i);
            task.setDate(new Date());
            Random random = new Random();
            int priority = random.nextInt(Task.Priority.values().length);
            task.setPriority(Task.Priority.getPriority(priority));
            tasks.add(task);
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
