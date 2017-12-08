package ru.bmstu.iu3.totodo.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;

import ru.bmstu.iu3.totodo.R;
import ru.bmstu.iu3.totodo.data.db.TaskDb;
import ru.bmstu.iu3.totodo.data.models.Task;
import ru.bmstu.iu3.totodo.utils.ArrayUtils;

/**
 * Created by Icetroid on 18.11.2017.
 */

public class MainSlidePageFragment extends Fragment
{
    private static final String TAG = "MainSlidePageFragment";
    private static final String KEY_PRIORITY = "priority";

    private static final String KEY_TASK_STATE = "taskState";
    public static final String TASK_STATE_ALL = "taskAll";
    public static final String TASK_STATE_TODAY = "taskToday";
    public static final String TASK_STATE_TOMORROW = "taskTomorrow";
    public static final String TASK_STATE_THIS_WEEK = "taskThisWeek";
    public static final String TASK_STATE_DATE = "taskDate";
    private static final String KEY_TASK_CHOOSEN_DATE = "taskChoosenDate";
    private static final String KEY_TASK_TYPE = "taskType";
    private static final String KEY_SHOW_DATE = "taskShowDate";

    private String taskState;
    private Date choosenDate;
    private String taskType;
    private RecyclerView rvTasks;
    private TasksAdapter tasksAdapter;
    private int priority;
    private TaskEditor mTaskEditor;
    private TaskDb db;
    private Date showDate;
    public static MainSlidePageFragment newInstance(int priority, String taskType, Date date) {
        MainSlidePageFragment mainSlidePageFragment = new MainSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_PRIORITY, priority);
        if(taskType != null)
        {
            args.putString(KEY_TASK_TYPE, taskType);
        }
        if(date != null)
        {
            args.putLong(KEY_SHOW_DATE, date.getTime());
        }
        mainSlidePageFragment.setArguments(args);
        return mainSlidePageFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof TaskEditor)
        {
            mTaskEditor = (TaskEditor) context;

        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        priority = getArguments().getInt(KEY_PRIORITY, 0);
        taskType = getArguments().getString(KEY_TASK_TYPE, null);
        long dateLong = getArguments().getLong(KEY_SHOW_DATE, -1);
        if(dateLong != -1)
        {
            showDate = new Date(dateLong);
        }
        else
        {
            showDate = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main_slide, container, false);
        rootView.setBackgroundColor(ArrayUtils.getMainVpBackgroundColor(priority, getContext()));
        rvTasks = rootView.findViewById(R.id.rv_tasks);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvTasks.setLayoutManager(linearLayoutManager);
        rvTasks.setHasFixedSize(true);
        tasksAdapter = new TasksAdapter(this);
        rvTasks.setAdapter(tasksAdapter);

        db = new TaskDb(getContext());

        //TODO change data
//        setTasks(FakeDataUtils.getTasks(100));
//        FakeDataUtils.insertTasksIntoDb(getContext(), 100);
//           db.deleteAllTasks();
        Log.i(TAG, "taskState "+ taskState);

        if(savedInstanceState != null && savedInstanceState.containsKey(KEY_TASK_STATE))
        {

            taskState = savedInstanceState.getString(KEY_TASK_STATE);
//            Log.i(TAG, "restore taskstate " + taskState);
        }
        if(savedInstanceState != null && savedInstanceState.containsKey(KEY_TASK_CHOOSEN_DATE))
        {

            choosenDate = new Date(savedInstanceState.getLong(KEY_TASK_CHOOSEN_DATE));
//            Log.i(TAG, "restore taskstate " + taskState);
        }

        if(taskType != null)
        {
            taskState = taskType;
        }

        if(taskState == TASK_STATE_TODAY)
        {
            setTaskToday();
        }
        else if(taskState == TASK_STATE_TOMORROW)
        {
            setTaskTomorrow();
        }
        else if(taskState == TASK_STATE_THIS_WEEK)
        {
            setTaskThisWeek();
        }
        else if(taskState == TASK_STATE_DATE)
        {
            Log.i(TAG, "TASK STATE DATE " + choosenDate);
            if(showDate != null)
            {
                setTaskDate(showDate);
            }
            else if(choosenDate != null)
            {
                setTaskDate(choosenDate);
            }

        }
        else
        {

            setTasks();
        }
//        Log.i(TAG, "taskState "+ taskState);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TASK_STATE, taskState);
        if(choosenDate != null)
        {
            outState.putLong(KEY_TASK_CHOOSEN_DATE, choosenDate.getTime());
        }
    }



    public void setTasks(List<Task> tasks) {
//        Log.i(TAG, "SET TASKS " + priority + " tasks size = " + tasks.size());
        tasksAdapter.setTasks(tasks);
    }

    public void update()
    {
        setTasks();
    }

    private void setTasks()
    {
        taskState = TASK_STATE_ALL;
        setTasks(db.getTasksWithPriorityOrderByDate(Task.Priority.getPriority(priority), true));
    }

    public void editTask(Task task)
    {
        mTaskEditor.editTask(task);
    }

    public void setTaskToday() {
        List<Task> tasks = db.getTodayTasksWithPriorityOrderByDate(Task.Priority.getPriority(priority), true);
        setTasks(tasks);
        taskState = TASK_STATE_TODAY;
//        Log.i(TAG, priority + " LOL tasks size = " + tasks.size());
    }

    public void setTaskTomorrow() {
        List<Task> tasks = db.getTomorrowTasksWithPriorityOrderByDate(Task.Priority.getPriority(priority), true);
        setTasks(tasks);
        taskState = TASK_STATE_TOMORROW;
//        Log.i(TAG, priority + " LOL tasks size = " + tasks.size());
    }

    public void setTaskThisWeek() {
        List<Task> tasks = db.getThisWeekTasksWithPriorityOrderByDate(Task.Priority.getPriority(priority), true);
        setTasks(tasks);
        taskState = TASK_STATE_THIS_WEEK;
    }

    public void setTaskDate(Date date) {
        List<Task> tasks = db.getDateTasksWithPriorityOrderByDate(Task.Priority.getPriority(priority), true, date);
        setTasks(tasks);
        taskState = TASK_STATE_DATE;
        choosenDate = date;
        Log.i(TAG,"settaskdate " + priority + " " + tasks.size());
    }
}
