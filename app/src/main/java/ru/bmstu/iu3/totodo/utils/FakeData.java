package ru.bmstu.iu3.totodo.utils;

import java.util.LinkedList;
import java.util.List;

import ru.bmstu.iu3.totodo.data.models.Task;

/**
 * Created by Icetroid on 15.11.2017.
 */

public class FakeData {
    public static List<Task> getTasks(int length){
        List<Task> tasks = new LinkedList<>();
        for(int i = 0; i < length; i++){
            Task task = new Task(i, "title " + i, "Text " + i);
            tasks.add(task);
        }

        return tasks;
    }
}
