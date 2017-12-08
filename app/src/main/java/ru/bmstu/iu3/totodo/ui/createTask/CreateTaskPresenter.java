package ru.bmstu.iu3.totodo.ui.createTask;

import java.util.Date;

import ru.bmstu.iu3.totodo.data.models.Task;

/**
 * Created by Icetroid on 17.11.2017.
 */

public interface CreateTaskPresenter {
    void setPriority(Task.Priority priority);
    void setFullDate(Date date);
    void setTime();
    void setCurrentDate();
    void chooseTime();
    void setHighestPriority();
    void choosePriority();
    void chooseDate();
    Task getTask();
    Date getDate();
    boolean createTask();
    void setId(long id);
    void setText(String text);
    boolean updateTask();

    void setDate(Date date);
    void setTime(Date date);

    void setNotifyTime(int notifyTimeSec);

    void chooseNotifyTime();
}
