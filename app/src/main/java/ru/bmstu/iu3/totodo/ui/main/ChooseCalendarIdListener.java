package ru.bmstu.iu3.totodo.ui.main;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

/**
 * Created by Icetroid on 26.11.2017.
 */

public class ChooseCalendarIdListener implements DialogInterface.OnClickListener {
    private static final String TAG = "ChooseCalendarId";
    private ChangeCalendarIdInterface mChangeCalendarIdInterface;
    public ChooseCalendarIdListener(ChangeCalendarIdInterface changeCalendarIdInterface)
    {
        this.mChangeCalendarIdInterface = changeCalendarIdInterface;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int button) {
        if(button == DialogInterface.BUTTON_POSITIVE)
        {
            mChangeCalendarIdInterface.syncTask();
        }
        else if(button == DialogInterface.BUTTON_NEGATIVE)
        {
            mChangeCalendarIdInterface.notSyncTask();
        }
    }
}
