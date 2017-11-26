package ru.bmstu.iu3.totodo.ui.main;

import android.widget.CompoundButton;

/**
 * Created by Icetroid on 26.11.2017.
 */

public class RememberListener implements CompoundButton.OnCheckedChangeListener {
    private ChangeCalendarIdInterface mChangeCalendarIdInterface;
    public RememberListener(ChangeCalendarIdInterface changeCalendarIdInterface)
    {
        this.mChangeCalendarIdInterface = changeCalendarIdInterface;
    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mChangeCalendarIdInterface.setRemember(b);
    }
}
