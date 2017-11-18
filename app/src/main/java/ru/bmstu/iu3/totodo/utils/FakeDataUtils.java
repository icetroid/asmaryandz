package ru.bmstu.iu3.totodo.utils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ru.bmstu.iu3.totodo.data.models.Task;

/**
 * Created by Icetroid on 15.11.2017.
 */

public class FakeDataUtils {
    public static List<Task> getTasks(int length){
        List<Task> tasks = new LinkedList<>();
        for(int i = 0; i < length; i++){
            Task task = new Task(i, "title " + i, "Text " + i);
            task.setDate(new Date());
            Random random = new Random();
            int priority = random.nextInt();
            tasks.add(task);
        }

        return tasks;
    }
}
