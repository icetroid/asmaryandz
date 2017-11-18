package ru.bmstu.iu3.totodo.ui.createTask;

import java.util.Date;

import ru.bmstu.iu3.totodo.data.models.Task;

/**
 * Created by Icetroid on 17.11.2017.
 */

public class CreateTaskPresenterImpl implements CreateTaskPresenter {
    private CreateTaskView createTaskView;
    private Task mTask;

    public CreateTaskPresenterImpl(CreateTaskView createTaskView){
        this.createTaskView = createTaskView;
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
