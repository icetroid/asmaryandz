package ru.bmstu.iu3.totodo.ui.main;

import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import ru.bmstu.iu3.totodo.R;
import ru.bmstu.iu3.totodo.data.models.Task;
import ru.bmstu.iu3.totodo.ui.chooseCalendar.ChooseCalendar;


/**
 * Created by Icetroid on 26.11.2017.
 */

public class TasksAdapterListener implements TasksAdapter.onClickListener {
    private static final String TAG = "TasksAdapterListener";
    private MainSlidePageFragment fragment;
    public TasksAdapterListener(MainSlidePageFragment fragment)
    {

        this.fragment = fragment;
    }

    @Override
    public void syncTask(Task task) {
        ChooseCalendar chooseCalendar = new ChooseCalendar(fragment.getActivity(), fragment.getContext());
        chooseCalendar.chooseCalendar();
    }

    @Override
    public void removeTask(Task task) {
        fragment.removeTask(task.getId());
    }


}
