package ru.bmstu.iu3.totodo.ui.main;

import java.util.Date;
import java.util.List;

import ru.bmstu.iu3.totodo.data.models.Task;

/**
 * Created by Icetroid on 15.11.2017.
 */

public interface MainView
{
    void openCreateTaskActivity();
    void onSwipeTasks(int position);
    void setPriorityTextView(String s);

    void changeBackgroundVpTasks(int color);

    void openNagivation();

    void showTodayTask();

    void showTomorrowTask();

    void showThisWeekTask();

    void showDateTask(Date date);

    void showSettings();

    void showAllTasks();
}
