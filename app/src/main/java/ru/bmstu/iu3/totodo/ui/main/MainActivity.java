package ru.bmstu.iu3.totodo.ui.main;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import ru.bmstu.iu3.totodo.R;
import ru.bmstu.iu3.totodo.data.db.TaskDb;
import ru.bmstu.iu3.totodo.data.models.Task;
import ru.bmstu.iu3.totodo.ui.createTask.CreateTaskActivity;
import ru.bmstu.iu3.totodo.utils.FakeDataUtils;

public class MainActivity extends AppCompatActivity implements MainView{

    private static final Task.Priority INITIAL_PAGE_PRIORITY = Task.Priority.A;
    private MainPresenter presenter;

    private Button btnCreateTask;
    private TextView tvDate;
    private TextView tvPriority;
    private TextView tvStats;

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
        tvDate = findViewById(R.id.tv_main_date);
        tvPriority = findViewById(R.id.tv_main_priority);
        tvStats = findViewById(R.id.tv_main_tasks_stats);

        //инициализация view pager и pager adapter
        vpTasks = findViewById(R.id.vp_main_tasks);
        tasksPagerAdapter = new TasksSlideAdapter(getSupportFragmentManager());
        vpTasks.setAdapter(tasksPagerAdapter);
        mSwipeTasksListener = new SwipeTasksListener(this);
//        FakeDataUtils.insertTasksIntoDb(this, 100);

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
        Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
        startActivity(intent);
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

    private class CreateTaskListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            presenter.createTask();
        }
    }

}
