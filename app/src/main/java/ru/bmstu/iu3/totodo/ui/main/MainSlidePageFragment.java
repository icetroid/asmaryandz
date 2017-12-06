package ru.bmstu.iu3.totodo.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private RecyclerView rvTasks;
    private TasksAdapter tasksAdapter;
    private int priority;
    private TaskEditor mTaskEditor;

    public static MainSlidePageFragment newInstance(int priority) {
        MainSlidePageFragment mainSlidePageFragment = new MainSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_PRIORITY, priority);
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

        //TODO change data
//        setTasks(FakeDataUtils.getTasks(100));
//        FakeDataUtils.insertTasksIntoDb(getContext(), 100);
//           db.deleteAllTasks();
        setTasks();
        return rootView;
    }

    public void setTasks(List<Task> tasks) {
        Log.i(TAG, priority + " tasks size = " + tasks.size());
        tasksAdapter.setTasks(tasks);
    }

    public void update()
    {
        setTasks();
    }

    private void setTasks()
    {
        TaskDb db = new TaskDb(getContext());
        setTasks(db.getTasksWithPriorityOrderByDate(Task.Priority.getPriority(priority), true));
    }

    public void editTask(Task task)
    {
        mTaskEditor.editTask(task);
    }
}
