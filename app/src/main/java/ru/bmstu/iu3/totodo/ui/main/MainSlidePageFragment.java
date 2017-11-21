package ru.bmstu.iu3.totodo.ui.main;

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
import ru.bmstu.iu3.totodo.utils.FakeDataUtils;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main_slide, container, false);


        rvTasks = rootView.findViewById(R.id.rv_tasks);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvTasks.setLayoutManager(linearLayoutManager);
        rvTasks.setHasFixedSize(true);
        tasksAdapter = new TasksAdapter();
        rvTasks.setAdapter(tasksAdapter);

        //TODO change data
//        setTasks(FakeDataUtils.getTasks(100));
        TaskDb db = new TaskDb(getContext());
//        FakeDataUtils.insertTasksIntoDb(getContext(), 100);
//        db.deleteAllTasks();
        setTasks(db.getAllTasks());
        return rootView;
    }

    public void setTasks(List<Task> tasks) {
        Log.i(TAG, "tasks size = " + tasks.size() + " tasks.get(0) " + tasks.get(0).toString());
        tasksAdapter.setTasks(tasks);
    }
}
