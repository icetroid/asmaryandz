package ru.bmstu.iu3.totodo.ui.main;

import android.content.Context;

import java.util.List;

import ru.bmstu.iu3.totodo.data.models.Task;
import ru.bmstu.iu3.totodo.utils.FakeDataUtils;

/**
 * Created by Icetroid on 15.11.2017.
 */

public class MainPresenterImpl implements MainPresenter {

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
    }
}
