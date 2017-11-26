package ru.bmstu.iu3.totodo.ui.main;

import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import ru.bmstu.iu3.totodo.R;

/**
 * Created by Icetroid on 26.11.2017.
 */

public class CalendarIdRadioButtonListener implements RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "CalendarIdRadioButton";
    private ChangeCalendarIdInterface mChangeCalendarIdInterface;
    public CalendarIdRadioButtonListener(ChangeCalendarIdInterface changeCalendarIdInterface)
    {
        this.mChangeCalendarIdInterface = changeCalendarIdInterface;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonID);
        int id = Integer.parseInt("" +radioButton.getTag());
        mChangeCalendarIdInterface.setCalendarId(id);
    }
}
