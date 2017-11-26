package ru.bmstu.iu3.totodo.ui.main;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.List;

import ru.bmstu.iu3.totodo.R;
import ru.bmstu.iu3.totodo.data.db.TaskDb;
import ru.bmstu.iu3.totodo.data.models.Task;
import ru.bmstu.iu3.totodo.ui.createTask.ChoosePriorityListener;
import ru.bmstu.iu3.totodo.utils.CalendarUtils;
import ru.bmstu.iu3.totodo.utils.DialogUtils;

/**
 * Created by Icetroid on 18.11.2017.
 */

public class MainSlidePageFragment extends Fragment implements ChangeCalendarIdInterface, ShowChooseCalendarIdInterface
{
    private static final String TAG = "MainSlidePageFragment";
    private static final String KEY_PRIORITY = "priority";

    private RecyclerView rvTasks;
    private TasksAdapter tasksAdapter;
    private int priority;
    private int calendarId;
    private Task mSelectedTask;
    private CalendarIdRadioButtonListener mCalendarIdRadioButtonListener;
    private ChooseCalendarIdListener mChooseCalendarIdListener;
    private RememberListener mRememberListener;
    private boolean mRemember;
    // newInstance constructor for creating fragment with arguments
    public static MainSlidePageFragment newInstance(int priority) {
        MainSlidePageFragment mainSlidePageFragment = new MainSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_PRIORITY, priority);
        mainSlidePageFragment.setArguments(args);
        return mainSlidePageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        priority = getArguments().getInt(KEY_PRIORITY, 0);
        mCalendarIdRadioButtonListener = new CalendarIdRadioButtonListener(this);
        mChooseCalendarIdListener = new ChooseCalendarIdListener(this);
        mRememberListener = new RememberListener(this);
        mRemember = false;
        calendarId = -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main_slide, container, false);


        rvTasks = rootView.findViewById(R.id.rv_tasks);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvTasks.setLayoutManager(linearLayoutManager);
        rvTasks.setHasFixedSize(true);
        tasksAdapter = new TasksAdapter(this);
        rvTasks.setAdapter(tasksAdapter);

        //TODO change data
//        setTasks(FakeDataUtils.getTasks(100));
        TaskDb db = new TaskDb(getContext());
//        FakeDataUtils.insertTasksIntoDb(getContext(), 100);
//           db.deleteAllTasks();
        setTasks(db.getTasksWithPriorityOrderByDate(Task.Priority.getPriority(priority), true));
        return rootView;
    }

    public void setTasks(List<Task> tasks) {
        Log.i(TAG, priority + " tasks size = " + tasks.size());
        tasksAdapter.setTasks(tasks);
    }

    public void showChooseCalendarIdDialog()
    {
        Dialog dialog = DialogUtils.makeChooseCalendarId(getContext(), getActivity(), this);
        dialog.show();
    }



    @Override
    public void setCalendarId(int id) {
        calendarId = id;
    }

    @Override
    public void syncTask() {
        if(mRemember && calendarId != -1)
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(getString(R.string.pref_ask_for_calendar_id_key), false);
            editor.putInt(getString(R.string.pref_calendar_id), calendarId);
            editor.apply();
        }
        Log.i(TAG, "calendar id " + calendarId);
        if(calendarId != -1)
        {
            CalendarUtils.insertTaskIntoCalendar(getContext(), getActivity(), mSelectedTask, calendarId);
        }
    }

    @Override
    public void notSyncTask() {
        calendarId = -1;
        mRemember = false;
    }

    @Override
    public void setRemember(boolean remember) {
        mRemember = remember;
    }

    @Override
    public CalendarIdRadioButtonListener getRadioButtonListener() {
        return mCalendarIdRadioButtonListener;
    }

    @Override
    public ChooseCalendarIdListener getChooseCalendarIdListener() {
        return mChooseCalendarIdListener;
    }

    @Override
    public CompoundButton.OnCheckedChangeListener getRememberListener() {
        return mRememberListener;
    }

    public void setSelectedTask(Task selectedTask) {
        mSelectedTask = selectedTask;
    }
}
