package ru.bmstu.iu3.totodo.ui.createTask;

import java.util.Date;

import ru.bmstu.iu3.totodo.data.models.Task;

/**
 * Created by Icetroid on 17.11.2017.
 */

public interface CreateTaskPresenter {
    void setPriority(Task.Priority priority);
    void setDate(Date date);
    void setTime();
    void setCurrentDate();
    void chooseTime();
    void setHighestPriority();
    void choosePriority();
    void chooseDate();
    Task getTask();
    Date getDate();
    void createTask();
}
