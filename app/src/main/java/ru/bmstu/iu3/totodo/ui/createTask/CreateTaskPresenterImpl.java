package ru.bmstu.iu3.totodo.ui.createTask;

import android.app.Activity;
import android.content.Context;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ru.bmstu.iu3.totodo.data.db.TaskDb;
import ru.bmstu.iu3.totodo.data.models.Task;
import ru.bmstu.iu3.totodo.ui.chooseCalendar.ChooseCalendar;
import ru.bmstu.iu3.totodo.ui.notification.Notification;

/**
 * Created by Icetroid on 17.11.2017.
 */

public class CreateTaskPresenterImpl implements CreateTaskPresenter {
    private CreateTaskView createTaskView;
    private Task mTask;
    private TaskDb mTaskDb;
    private Context mContext;
    private Activity mActivity;
    private ChooseCalendar chooseCalendar;

    public CreateTaskPresenterImpl(CreateTaskView createTaskView, Context context, Activity activity){
        this.createTaskView = createTaskView;
        mTaskDb = new TaskDb(context);
        mContext = context;
        mTask = new Task();
        mActivity = activity;

    }

    @Override
    public void setPriority(Task.Priority priority)
    {
        mTask.setPriority(priority);
    }

    @Override
    public void setFullDate(Date date) {
        mTask.setFullDate(date);
    }

    @Override
    public Date getDate()
    {
        return mTask.getDate();
    }

    @Override
    public void setId(long id) {
        mTask.setId(id);
    }

    @Override
    public boolean updateTask() {
        //TODO if priority is changed changed, task doesn't change location
        String taskText = createTaskView.getTaskText();
        mTask.setText(taskText);
        String error = validateTask();
        if(error == null)
        {
            mTaskDb.updateTask(mTask);
            if(chooseCalendar != null)
            {
                chooseCalendar.syncTask(mTask);
            }
            return true;
        }
        else
        {
            createTaskView.showErrorDialog(error);
            return false;
        }
    }

    @Override
    public void setDate(Date date) {
        mTask.setDate(date);
    }

    @Override
    public void setTime(Date date) {
        mTask.setTime(date);
    }

    @Override
    public void setNotifyTime(int notifyTimeSec) {
        mTask.setNotifyTime(notifyTimeSec);
    }

    @Override
    public void setText(String text) {
        mTask.setText(text);
    }

    @Override
    public boolean createTask() {
        String taskText = createTaskView.getTaskText();
        mTask.setText(taskText);
        String error = validateTask();
        if(error == null)
        {
            mTaskDb.insertTask(mTask);
            Notification.setNotify(mContext, mTask);
            if(chooseCalendar != null)
            {
                chooseCalendar.syncTask(mTask);
            }
            return true;
        }
        else
        {
            createTaskView.showErrorDialog(error);
            return false;
        }
    }

    //TODO validate task
    private String validateTask()
    {
        List<String> emptyFields = new LinkedList<>();
        if(mTask.getText() == null)
        {
            emptyFields.add("текст задачи");
        }
        if(mTask.getPriority() == null)
        {
            emptyFields.add("приоритет");
        }
        if(mTask.getTimeSet() == false)
        {
            emptyFields.add("время");
        }
        if(mTask.getDateSet() == false)
        {
            emptyFields.add("дата");
        }
        if(emptyFields.isEmpty())
        {
            return null;
        }

        String error = "Заполните поля: ";
        for(String field : emptyFields)
        {
            error+=field + ", ";
        }
        error = error.substring(0, error.length()-2) + ".";
        return error;
    }


    @Override
    public void setTime() {

    }

    @Override
    public void setCurrentDate() {
        Date date = new Date();
        mTask.setFullDate(date);
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

    @Override
    public void chooseNotifyTime() {
        createTaskView.showChooseNotifyTimeDialog();
    }

    @Override
    public void chooseSyncCalendar() {
        chooseCalendar = new ChooseCalendar(mActivity, mContext);
        chooseCalendar.chooseCalendar();

    }
}
