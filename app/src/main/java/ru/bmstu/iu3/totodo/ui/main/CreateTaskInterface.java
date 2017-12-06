package ru.bmstu.iu3.totodo.ui.main;

import ru.bmstu.iu3.totodo.data.models.Task;

/**
 * Created by Icetroid on 05.12.2017.
 */

public interface CreateTaskInterface {
    void taskCreated(Task.Priority priority);
}
