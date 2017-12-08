package ru.bmstu.iu3.totodo.ui.main;

import java.util.Date;
import java.util.List;

import ru.bmstu.iu3.totodo.data.models.Task;

/**
 * Created by Icetroid on 15.11.2017.
 */

public interface MainPresenter {
    void createTask();
    void onSwipeTasks(int position);

    void openNavigationDrawer();


    void showTodayTask();

    void showTomorrowTask();

    void showThisWeekTask();

    void showDateTask();
    Date getShowDate();
}
