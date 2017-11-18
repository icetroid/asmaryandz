package ru.bmstu.iu3.totodo.ui.createTask;

import android.content.DialogInterface;

import ru.bmstu.iu3.totodo.data.models.Task;
import ru.bmstu.iu3.totodo.ui.main.MainPresenter;

/**
 * Created by Icetroid on 17.11.2017.
 */

public class ChoosePriorityListener implements DialogInterface.OnClickListener{
    private CreateTaskPresenter mPresenter;

    public ChoosePriorityListener(CreateTaskPresenter presenter){
        mPresenter = presenter;
    }
    public void onClick(DialogInterface dialog, int priority) {
        mPresenter.setPriority(Task.Priority.getPriority(priority));
    }
}
