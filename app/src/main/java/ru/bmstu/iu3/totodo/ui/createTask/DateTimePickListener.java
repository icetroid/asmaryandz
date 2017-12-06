package ru.bmstu.iu3.totodo.ui.createTask;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Icetroid on 17.11.2017.
 */

public class DateTimePickListener implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private CreateTaskPresenter mCreateTaskPresenter;

    public DateTimePickListener(CreateTaskPresenter createTaskPresenter)
    {
        mCreateTaskPresenter = createTaskPresenter;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mCreateTaskPresenter.getDate());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Date date = calendar.getTime();
        mCreateTaskPresenter.setDate(date);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mCreateTaskPresenter.getDate());
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        Date date = calendar.getTime();
        mCreateTaskPresenter.setTime(date);
    }
}
