package ru.bmstu.iu3.totodo.ui.chooseCalendar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import ru.bmstu.iu3.totodo.R;
import ru.bmstu.iu3.totodo.data.models.Task;
import ru.bmstu.iu3.totodo.utils.CalendarUtils;
import ru.bmstu.iu3.totodo.utils.DialogUtils;

/**
 * Created by Icetroid on 27.11.2017.
 */

public class ChooseCalendar implements DialogInterface.OnClickListener, CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener
{
    private static final String TAG = "ChooseCalendar";
    private int calendarId;
    private Activity mActivity;
    private Context mContext;
    private boolean mRemember;

    public ChooseCalendar(Activity activity, Context context)
    {
        this.mActivity = activity;
        this.mContext = context;
        mRemember = false;
        calendarId = -1;

    }

    public void chooseCalendar()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        boolean askCalendarId = sharedPreferences.getBoolean(mActivity.getString(R.string.pref_ask_for_calendar_id_key), mActivity.getResources().getBoolean(R.bool.ask_for_calendar_id_default));

        if(askCalendarId)
        {
            showChooseCalendarIdDialog();
        }
        else
        {
            int id = sharedPreferences.getInt(mActivity.getString(R.string.pref_calendar_id), -1);
            if(id == -1)
            {
                showChooseCalendarIdDialog();
            }
            else
            {
                calendarId = id;
                //syncTask();
            }
        }

    }

    public void syncTask(Task task) {
        if(mRemember && calendarId != -1)
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(mActivity.getString(R.string.pref_ask_for_calendar_id_key), false);
            editor.putInt(mActivity.getString(R.string.pref_calendar_id), calendarId);
            editor.apply();
        }

        if(calendarId != -1)
        {
            CalendarUtils.insertTaskIntoCalendar(mContext, mActivity, task, calendarId);
        }
    }

    private void showChooseCalendarIdDialog()
    {
        Dialog dialog = DialogUtils.makeChooseCalendarId(mContext, mActivity, this);
        dialog.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mRemember = b;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = radioGroup.findViewById(radioButtonID);
        calendarId = Integer.parseInt("" +radioButton.getTag());
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int button) {
        if(button == DialogInterface.BUTTON_POSITIVE)
        {
            //syncTask();
        }
        else if(button == DialogInterface.BUTTON_NEGATIVE)
        {
            calendarId = -1;
            mRemember = false;
        }
    }
}
