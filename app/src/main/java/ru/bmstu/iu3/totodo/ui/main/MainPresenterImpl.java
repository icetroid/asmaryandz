package ru.bmstu.iu3.totodo.ui.main;

import java.util.List;

import ru.bmstu.iu3.totodo.data.models.Task;
import ru.bmstu.iu3.totodo.utils.FakeData;

/**
 * Created by Icetroid on 15.11.2017.
 */

public class MainPresenterImpl implements MainPresenter {

    private MainView mainView;
    public MainPresenterImpl(MainView mainView){
        this.mainView = mainView;
    }

    //TODO вернуть данные из БД
    @Override
    public void getAllTasks() {
        List<Task> tasks = FakeData.getTasks(100);
        mainView.setTasks(tasks);
    }

    @Override
    public void createTask() {
        mainView.openCreateTaskActivity();
    }
}
