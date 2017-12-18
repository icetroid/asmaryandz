package ru.bmstu.iu3.totodo.ui.createTask;

/**
 * Created by Icetroid on 17.11.2017.
 */

public interface CreateTaskView {
    void showChoosePriorityDialog();
    void showCalendarDialog();
    void showTimeDialog();
    void showChooseNotifyTimeDialog();
    String getTaskText();

    void showErrorDialog(String error);

    void setSyncIconOn();
    void setSyncIconOff();

    void setNotifyIconOn();
    void setNotifyIconOff();
}
