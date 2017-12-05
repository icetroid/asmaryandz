package ru.bmstu.iu3.totodo.ui.main;

import android.content.Context;
import android.util.Log;

import java.util.List;

import ru.bmstu.iu3.totodo.data.models.Task;
import ru.bmstu.iu3.totodo.utils.ArrayUtils;
import ru.bmstu.iu3.totodo.utils.FakeDataUtils;

/**
 * Created by Icetroid on 15.11.2017.
 */

public class MainPresenterImpl implements MainPresenter {

    public static final String TAG = "MainPresenterImpl";
    private MainView mainView;
    private Context context;
    public MainPresenterImpl(MainView mainView, Context context){
        this.mainView = mainView;
        this.context = context;
    }

    @Override
    public void createTask() {
        mainView.openCreateTaskActivity();
    }

    @Override
    public void onSwipeTasks(int position) {
        mainView.setPriorityTextView("Приоритет " + Task.Priority.getPriority(position));


        //changeBackgroundVpTasks(position);
    }

    @Override
    public void openNavigationDrawer() {
        mainView.openNagivation();
    }

    private void changeBackgroundVpTasks(int position)
    {
        int color = ArrayUtils.getMainVpBackgroundColor(position, context);
        Log.i(TAG, "Color " + color);
        mainView.changeBackgroundVpTasks(color);
    }
}
