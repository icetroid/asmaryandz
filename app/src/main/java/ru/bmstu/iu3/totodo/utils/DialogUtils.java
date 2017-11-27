package ru.bmstu.iu3.totodo.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Map;

import ru.bmstu.iu3.totodo.R;
import ru.bmstu.iu3.totodo.ui.chooseCalendar.ChooseCalendar;

/**
 * Created by Icetroid on 17.11.2017.
 */

public class DialogUtils {
    public static Dialog makeChoosePriorityDialog(Context context, DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.choose_priority_dialog_title).setItems(R.array.priorities, listener);
        AlertDialog dialog = builder.create();
        return dialog;
    }

    public static Dialog makeChooseCalendarId(Context context, Activity activity, ChooseCalendar chooseCalendar){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.choose_calendar_id_title);

        final View choose_calendar_id_layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_choose_calendar_id, null);
        final RadioGroup radioGroup = choose_calendar_id_layout.findViewById(R.id.rg_calendar_id);

        Map<Long, String> calendars = CalendarUtils.getCalendars(context, activity);
        for(Long id : calendars.keySet())
        {
            final RadioButton button = new RadioButton(context);
            String calendarName = calendars.get(id);
            button.setText(calendarName);
            button.setTag(id);
            radioGroup.addView(button);
        }

        radioGroup.setOnCheckedChangeListener(chooseCalendar);
        if(radioGroup.getChildCount() > 0)
        {
            radioGroup.check(radioGroup.getChildAt(0).getId());
        }

        CheckBox remember = choose_calendar_id_layout.findViewById(R.id.cb_remember_calendar_id);
        remember.setOnCheckedChangeListener(chooseCalendar);

        builder.setView(choose_calendar_id_layout)
                .setPositiveButton(R.string.choose_calendar_id_ok, chooseCalendar)
                .setNegativeButton(R.string.choose_calendar_id_cancel, chooseCalendar);
        AlertDialog dialog =  builder.create();
        return dialog;
    }
}
