package ru.bmstu.iu3.totodo.ui.main;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.bmstu.iu3.totodo.data.models.Task;
import ru.bmstu.iu3.totodo.utils.ArrayUtils;
import ru.bmstu.iu3.totodo.utils.FakeDataUtils;

/**
 * Created by Icetroid on 15.11.2017.
 */

public class MainPresenterImpl implements MainPresenter, DatePickerDialog.OnDateSetListener  {

    public static final String TAG = "MainPresenterImpl";
    private MainView mainView;
    private Context context;
    private Date showDate;
    public MainPresenterImpl(MainView mainView, Context context){
        this.mainView = mainView;
        this.context = context;
    }

    @Override
    public void createTask() {
        mainView.openCreateTaskActivity();
    }

    @Override
    public void onSwipeTasks(int position) {
        mainView.setPriorityTextView("Приоритет " + Task.Priority.getPriority(position));


        //changeBackgroundVpTasks(position);
    }

    @Override
    public void openNavigationDrawer() {
        mainView.openNagivation();
    }

    @Override
    public void showTodayTask() {
        mainView.showTodayTask();
    }

    @Override
    public void showTomorrowTask() {
        mainView.showTomorrowTask();
    }

    @Override
    public void showThisWeekTask() {
        mainView.showThisWeekTask();
    }

    @Override
    public void showDateTask() {
        Calendar dateAndTime = Calendar.getInstance();
        new DatePickerDialog(context, this,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();

    }

    private void changeBackgroundVpTasks(int position)
    {
        int color = ArrayUtils.getMainVpBackgroundColor(position, context);
        Log.i(TAG, "Color " + color);
        mainView.changeBackgroundVpTasks(color);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        showDate = calendar.getTime();
        if(showDate != null)
        {
            mainView.showDateTask(showDate);
        }
    }
    @Override
    public Date getShowDate() {
        return showDate;
    }
}
