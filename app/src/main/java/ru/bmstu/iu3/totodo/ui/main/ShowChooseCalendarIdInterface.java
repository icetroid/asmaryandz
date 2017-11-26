package ru.bmstu.iu3.totodo.ui.main;

import android.widget.CompoundButton;

/**
 * Created by Icetroid on 26.11.2017.
 */

public interface ShowChooseCalendarIdInterface {
    CalendarIdRadioButtonListener getRadioButtonListener();
    ChooseCalendarIdListener getChooseCalendarIdListener();
    CompoundButton.OnCheckedChangeListener getRememberListener();
}
