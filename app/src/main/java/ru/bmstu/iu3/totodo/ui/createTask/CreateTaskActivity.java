package ru.bmstu.iu3.totodo.ui.createTask;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

import ru.bmstu.iu3.totodo.R;
import ru.bmstu.iu3.totodo.data.models.Task;
import ru.bmstu.iu3.totodo.ui.main.MainActivity;
import ru.bmstu.iu3.totodo.utils.DialogUtils;

public class CreateTaskActivity extends AppCompatActivity implements CreateTaskView{

    private static final String TAG = "CreateTaskActivity";

    private CreateTaskPresenter presenter;

    private SetTaskPropertiesListener mSetTaskPropertiesListener = new SetTaskPropertiesListener();
    private SetTaskPropertyLongListener mSetTaskPropertyLongListener = new SetTaskPropertyLongListener();
    private DateTimePickListener mDateTimePickListener;

    private Button btnSetPriority;
    private Button btnSetDate;
    private Button btnSetTime;

    private EditText etTaskText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        presenter = new CreateTaskPresenterImpl(this, this);

        btnSetPriority = findViewById(R.id.btn_set_task_priority);
        btnSetPriority.setOnClickListener(mSetTaskPropertiesListener);
        btnSetPriority.setOnLongClickListener(mSetTaskPropertyLongListener);

        btnSetDate = findViewById(R.id.btn_set_task_date);
        btnSetDate.setOnClickListener(mSetTaskPropertiesListener);
        btnSetDate.setOnLongClickListener(mSetTaskPropertyLongListener);

        btnSetTime = findViewById(R.id.btn_set_task_time);
        btnSetTime.setOnClickListener(mSetTaskPropertiesListener);
        btnSetTime.setOnLongClickListener(mSetTaskPropertyLongListener);

        mDateTimePickListener = new DateTimePickListener(presenter);

        etTaskText = findViewById(R.id.et_task_text);
    }


    public void createTask(View view){
//        Log.i(TAG, presenter.getTask().toString());
        presenter.createTask();
        Intent intent = new Intent(CreateTaskActivity.this, MainActivity.class);
        startActivity(intent);
    }



    @Override
    public void showChoosePriorityDialog() {
        Dialog dialog = DialogUtils.makeChoosePriorityDialog(this, new ChoosePriorityListener(presenter));
        dialog.show();
    }

    @Override
    public void showCalendarDialog()
    {
        Calendar dateAndTime = Calendar.getInstance();
        new DatePickerDialog(CreateTaskActivity.this, mDateTimePickListener,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }
    @Override
    public void showTimeDialog()
    {
        Calendar dateAndTime = Calendar.getInstance();
        new TimePickerDialog(CreateTaskActivity.this, mDateTimePickListener,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    @Override
    public String getTaskText() {
        return etTaskText.getText().toString();
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
