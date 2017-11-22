package ru.bmstu.iu3.totodo.ui.createTask;

import android.content.Context;

import java.util.Date;

import ru.bmstu.iu3.totodo.data.db.TaskDb;
import ru.bmstu.iu3.totodo.data.models.Task;

/**
 * Created by Icetroid on 17.11.2017.
 */

public class CreateTaskPresenterImpl implements CreateTaskPresenter {
    private CreateTaskView createTaskView;
    private Task mTask;
    private TaskDb mTaskDb;

    public CreateTaskPresenterImpl(CreateTaskView createTaskView, Context context){
        this.createTaskView = createTaskView;
        mTaskDb = new TaskDb(context);
        mTask = new Task();
        mTask.setDate(new Date());
    }

    @Override
    public void setPriority(Task.Priority priority)
    {
        mTask.setPriority(priority);
    }

    @Override
    public void setDate(Date date) {
        mTask.setDate(date);
    }

    @Override
    public Date getDate()
    {
        return mTask.getDate();
    }

    @Override
    public void createTask() {
        String taskText = createTaskView.getTaskText();
        mTask.setText(taskText);
        mTaskDb.insertTask(mTask);
    }

    @Override
    public void setTime() {

    }

    @Override
    public void setCurrentDate() {
        Date date = new Date();
        mTask.setDate(date);
    }

    @Override
    public void chooseTime() {
        createTaskView.showTimeDialog();
    }

    @Override
    public void setHighestPriority() {
        mTask.setPriority(Task.Priority.A);
    }

    @Override
    public void choosePriority() {
        createTaskView.showChoosePriorityDialog();
    }

    @Override
    public void chooseDate() {
        createTaskView.showCalendarDialog();
    }

    @Override
    public Task getTask() {
        return mTask;
    }
}
