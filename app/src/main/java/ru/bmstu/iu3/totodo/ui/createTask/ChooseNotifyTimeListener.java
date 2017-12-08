package ru.bmstu.iu3.totodo.ui.createTask;

import android.content.DialogInterface;

import ru.bmstu.iu3.totodo.R;
import ru.bmstu.iu3.totodo.data.models.Task;

/**
 * Created by Icetroid on 08.12.2017.
 */

public class ChooseNotifyTimeListener implements DialogInterface.OnClickListener
{
    private CreateTaskPresenter mPresenter;

    public ChooseNotifyTimeListener(CreateTaskPresenter presenter){
        mPresenter = presenter;
    }
    public void onClick(DialogInterface dialog, int option) {
        int notifyTimeSec = 0;
        switch(option)
        {
            case 0:
                notifyTimeSec = 0;
                break;
            case 1:
                notifyTimeSec = 15 * 60;
                break;
            case 2:
                notifyTimeSec = 30 * 60;
                break;
            case 3:
                notifyTimeSec = 60 * 60;
                break;
            default:
                notifyTimeSec = 0;
        }
        mPresenter.setNotifyTime(notifyTimeSec);
    }
}
