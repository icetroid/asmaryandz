package ru.bmstu.iu3.totodo.ui.createTask;

/**
 * Created by Icetroid on 17.11.2017.
 */

public interface CreateTaskView {
    void showChoosePriorityDialog();
    void showCalendarDialog();
    void showTimeDialog();
    String getTaskText();
}
