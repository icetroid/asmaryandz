package ru.bmstu.iu3.totodo.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import ru.bmstu.iu3.totodo.R;
import ru.bmstu.iu3.totodo.data.models.Task;
import ru.bmstu.iu3.totodo.utils.CalendarUtils;


/**
 * Created by Icetroid on 26.11.2017.
 */

public class TasksAdapterListener implements TasksAdapter.onClickListener {
    private static final String TAG = "TasksAdapterListener";
    private TasksAdapter tasksAdapter;
    private MainSlidePageFragment mMainSlidePageFragment;
    public TasksAdapterListener(TasksAdapter tasksAdapter, MainSlidePageFragment mainSlidePageFragment)
    {
        this.tasksAdapter = tasksAdapter;
        this.mMainSlidePageFragment = mainSlidePageFragment;
    }

    @Override
    public void syncTask(int position) {
        Task task = tasksAdapter.getTask(position);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mMainSlidePageFragment.getContext());
        boolean askCalendarId = sharedPreferences.getBoolean(mMainSlidePageFragment.getActivity().getString(R.string.pref_ask_for_calendar_id_key), mMainSlidePageFragment.getActivity().getResources().getBoolean(R.bool.ask_for_calendar_id_default));
        boolean as = sharedPreferences.contains(mMainSlidePageFragment.getActivity().getString(R.string.pref_ask_for_calendar_id_key));

        Log.i(TAG, "" + as);
        mMainSlidePageFragment.setSelectedTask(task);
        if(askCalendarId)
        {
            mMainSlidePageFragment.showChooseCalendarIdDialog();
        }
        else
        {
            int id = sharedPreferences.getInt(mMainSlidePageFragment.getActivity().getString(R.string.pref_calendar_id), -1);
            if(id == -1)
            {
                mMainSlidePageFragment.showChooseCalendarIdDialog();
            }
            else
            {
                mMainSlidePageFragment.setCalendarId(id);
                mMainSlidePageFragment.syncTask();
            }
        }

    }
}
