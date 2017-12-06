package ru.bmstu.iu3.totodo.ui.createTask;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import ru.bmstu.iu3.totodo.R;
import ru.bmstu.iu3.totodo.data.models.Task;
import ru.bmstu.iu3.totodo.ui.main.CreateTaskInterface;
import ru.bmstu.iu3.totodo.ui.main.MainView;
import ru.bmstu.iu3.totodo.utils.DialogUtils;

/**
 * Created by Icetroid on 03.12.2017.
 */

public class CreateTaskFragment extends Fragment implements CreateTaskView
{


    private static final String TAG = "CreateTaskActivity";
    private static final String KEY_TASK_ID = "taskId";
    private static final String KEY_UPDATE_TASK = "updateTask";
    private static final String KEY_TASK_TEXT = "taskText";
    private static final String KEY_TASK_DATE = "taskDate";
    private static final String KEY_TASK_PRIORITY = "taskPriority";

    private CreateTaskPresenter presenter;

    private SetTaskPropertiesListener mSetTaskPropertiesListener = new SetTaskPropertiesListener();
    private SetTaskPropertyLongListener mSetTaskPropertyLongListener = new SetTaskPropertyLongListener();
    private DateTimePickListener mDateTimePickListener;

    private ImageButton btnSetPriority;
    private ImageButton btnSetDate;
    private ImageButton btnSetTime;

    private CreateTaskInterface mCreateTaskInterface;

    private Button btnCreateTask;

    private EditText etTaskText;

    private MainView mMainView;

    private boolean updateTask = false;


    public static CreateTaskFragment getInstanceEditTask(Task task)
    {
        CreateTaskFragment createTaskFragment = new CreateTaskFragment();
        Bundle args = new Bundle();
        long id = task.getId();
        args.putLong(KEY_TASK_ID, id);

        String text = task.getText();
        args.putString(KEY_TASK_TEXT, text);

        Date date = task.getDate();
        args.putLong(KEY_TASK_DATE,  date.getTime());

        int priority = task.getPriority().getPriority();
        args.putInt(KEY_TASK_PRIORITY, priority);

        args.putBoolean(KEY_UPDATE_TASK, true);

        createTaskFragment.setArguments(args);
        return createTaskFragment;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_create_task, container, false);

        presenter = new CreateTaskPresenterImpl(this, getContext());

        btnSetPriority = view.findViewById(R.id.btn_set_task_priority);
        btnSetPriority.setOnClickListener(mSetTaskPropertiesListener);
        btnSetPriority.setOnLongClickListener(mSetTaskPropertyLongListener);

        btnSetDate = view.findViewById(R.id.btn_set_task_date);
        btnSetDate.setOnClickListener(mSetTaskPropertiesListener);
        btnSetDate.setOnLongClickListener(mSetTaskPropertyLongListener);

        btnSetTime = view.findViewById(R.id.btn_set_task_time);
        btnSetTime.setOnClickListener(mSetTaskPropertiesListener);
        btnSetTime.setOnLongClickListener(mSetTaskPropertyLongListener);

        mDateTimePickListener = new DateTimePickListener(presenter);
        //Todo delete this button
        btnCreateTask = view.findViewById(R.id.btn_create_task);
        btnCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTask(view);
            }
        });

        etTaskText = view.findViewById(R.id.et_task_text);


        Bundle args = getArguments();

        if(args != null && args.containsKey(KEY_UPDATE_TASK))
        {
            if(args.getBoolean(KEY_UPDATE_TASK))
            {
                updateTask = true;

                long id = args.getLong(KEY_TASK_ID);
                String text = args.getString(KEY_TASK_TEXT);
                Task.Priority priority = Task.Priority.getPriority(args.getInt(KEY_TASK_PRIORITY));

                long dateLong = args.getLong(KEY_TASK_DATE);
                Date date = new Date(dateLong);

                presenter.setId(id);
                presenter.setText(text);
                presenter.setFullDate(date);
                presenter.setPriority(priority);

                etTaskText.setText(text);

                btnCreateTask.setText(getString(R.string.create_task_update_text));
            }
        }

        return view;
    }

    public void createTask(View view){
//        Log.i(TAG, presenter.getTask().toString());
        boolean success = false;
        if(updateTask)
        {
            success = presenter.updateTask();
            Log.i(TAG, "update" + presenter.getTask());
        }
        else
        {
            success = presenter.createTask();
        }
        if(success && mCreateTaskInterface != null)
        {
            mCreateTaskInterface.taskCreated(presenter.getTask().getPriority());
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CreateTaskInterface){
            mCreateTaskInterface = (CreateTaskInterface) context;
        }
    }

    @Override
    public void showChoosePriorityDialog() {
        Dialog dialog = DialogUtils.makeChoosePriorityDialog(getContext(), new ChoosePriorityListener(presenter));
        dialog.show();
    }

    @Override
    public void showCalendarDialog()
    {
        Calendar dateAndTime = Calendar.getInstance();
        new DatePickerDialog(getContext(), mDateTimePickListener,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }
    @Override
    public void showTimeDialog()
    {
        Calendar dateAndTime = Calendar.getInstance();
        new TimePickerDialog(getContext(), mDateTimePickListener,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    @Override
    public String getTaskText() {
        return etTaskText.getText().toString();
    }

    @Override
    public void showErrorDialog(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }


    /**
     * Слушает удерживание кнопок
     */
    private class SetTaskPropertyLongListener implements View.OnLongClickListener{

        @Override
        public boolean onLongClick(View view) {
            switch(view.getId())
            {
                case R.id.btn_set_task_priority:
//                    Log.i(TAG, "Set priority button long clicked");
                    presenter.choosePriority();
                    break;
                case R.id.btn_set_task_date:
                    presenter.chooseDate();
                    break;
                case R.id.btn_set_task_time:
                    break;
            }

            return true;
        }
    }



    /**
     * Слушает одно нажатие на кнопку
     */
    private class SetTaskPropertiesListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch(view.getId())
            {
                case R.id.btn_set_task_priority:
                    presenter.setHighestPriority();
                    break;
                case R.id.btn_set_task_date:
                    presenter.setCurrentDate();
                    break;
                case R.id.btn_set_task_time:
                    presenter.chooseTime();
                    break;
            }
        }
    }

}
