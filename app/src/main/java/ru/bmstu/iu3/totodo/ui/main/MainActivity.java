package ru.bmstu.iu3.totodo.ui.main;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import ru.bmstu.iu3.totodo.R;
import ru.bmstu.iu3.totodo.data.models.Task;
import ru.bmstu.iu3.totodo.ui.createTask.CreateTaskFragment;

public class MainActivity extends AppCompatActivity implements MainView{

    private static final String TAG = "MainActivity";
    private static final Task.Priority INITIAL_PAGE_PRIORITY = Task.Priority.A;
    private MainPresenter presenter;

    private Button btnCreateTask;
    private TextView tvPriority;
    private TextView tvStats;

    private ImageButton btnOpenOptionBar;

    private ViewPager vpTasks;
    private TasksSlideAdapter tasksPagerAdapter;
    private SwipeTasksListener mSwipeTasksListener;

    private CreateTaskListener createTaskListener = new CreateTaskListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenterImpl(this, this);

        //инициализация кнопки создания нового задания
        btnCreateTask = findViewById(R.id.btn_open_create_task);
        btnCreateTask.setOnClickListener(createTaskListener);

        //инициализация text view
        tvPriority = findViewById(R.id.tv_main_priority);
        tvStats = findViewById(R.id.tv_main_tasks_stats);

        btnOpenOptionBar = findViewById(R.id.btn_open_option_bar);

        //инициализация view pager и pager adapter
        vpTasks = findViewById(R.id.vp_main_tasks);
        tasksPagerAdapter = new TasksSlideAdapter(getSupportFragmentManager());
        vpTasks.setAdapter(tasksPagerAdapter);
        mSwipeTasksListener = new SwipeTasksListener(this);
        //FakeDataUtils.insertTasksIntoDb(this, 100);

        vpTasks.setCurrentItem(INITIAL_PAGE_PRIORITY.getPriority());
        onSwipeTasks(INITIAL_PAGE_PRIORITY.getPriority());
    }



    @Override
    protected void onResume()
    {
        super.onResume();
        vpTasks.addOnPageChangeListener(mSwipeTasksListener);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        vpTasks.removeOnPageChangeListener(mSwipeTasksListener);

    }

    @Override
    public void openCreateTaskActivity()
    {
        //TODO change back
        //Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
        //startActivity(intent);
        if (findViewById(R.id.create_task_fragment) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
//            if (savedInstanceState != null) {
//                return;
//            }

            // Create a new Fragment to be placed in the activity layout
            CreateTaskFragment firstFragment = new CreateTaskFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.create_task_fragment, firstFragment).commit();
        }

//        Log.i(TAG, "create task");
//        Task task = new Task();
//        task.setDate(new Date());
//        task.setText("sdfsdaf");
//        CalendarUtils.insertTaskIntoCalendar(this, this, task, 2);
    }

    @Override
    public void onSwipeTasks(int position)
    {
        //TODO implements on swipe tasks
        presenter.onSwipeTasks(position);
    }

    @Override
    public void setPriorityTextView(String text)
    {
        tvPriority.setText(text);
    }

    @Override
    public void changeBackgroundVpTasks(int color) {
        vpTasks.setBackgroundColor(color);
    }

    private class CreateTaskListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            presenter.createTask();
        }
    }

}
