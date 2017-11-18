package ru.bmstu.iu3.totodo.ui.main;

import java.util.List;

import ru.bmstu.iu3.totodo.data.models.Task;
import ru.bmstu.iu3.totodo.utils.FakeDataUtils;

/**
 * Created by Icetroid on 15.11.2017.
 */

public class MainPresenterImpl implements MainPresenter {

    private MainView mainView;
    public MainPresenterImpl(MainView mainView){
        this.mainView = mainView;
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
