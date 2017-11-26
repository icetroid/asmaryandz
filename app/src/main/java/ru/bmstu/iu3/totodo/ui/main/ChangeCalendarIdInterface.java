package ru.bmstu.iu3.totodo.ui.main;

import android.app.Activity;

/**
 * Created by Icetroid on 26.11.2017.
 */

public interface ChangeCalendarIdInterface {
    void setCalendarId(int id);
    void syncTask();
    void notSyncTask();
    Activity getActivity();
    void setRemember(boolean remember);
}
