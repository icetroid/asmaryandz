package ru.bmstu.iu3.totodo.ui.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Date;

import ru.bmstu.iu3.totodo.R;
import ru.bmstu.iu3.totodo.data.db.TaskDb;
import ru.bmstu.iu3.totodo.data.models.Task;
import ru.bmstu.iu3.totodo.ui.createTask.CreateTaskFragment;
import ru.bmstu.iu3.totodo.ui.notification.Notification;
import ru.bmstu.iu3.totodo.ui.settings.SettingsActivity;
import ru.bmstu.iu3.totodo.ui.test.Test;

public class MainActivity extends AppCompatActivity implements TaskEditor, MainView, NavigationView.OnNavigationItemSelectedListener, CreateTaskInterface{

    private static final String TAG = "MainActivity";
    private static final Task.Priority INITIAL_PAGE_PRIORITY = Task.Priority.A;
    private static final String CREATE_FRAGMENT_TAG = "createFragment";
    private MainPresenter presenter;

    private ImageButton btnNavigationOpen;
    private DrawerLayout drawer;

    private Button btnCreateTask;
    private TextView tvPriority;



    private ViewPager vpTasks;
    private TasksSlideAdapter tasksPagerAdapter;
    private SwipeTasksListener mSwipeTasksListener;

    private CreateTaskListener createTaskListener = new CreateTaskListener();
    private NavigationButtonListener mNagivationButtonListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenterImpl(this, this);

        mNagivationButtonListener = new NavigationButtonListener(presenter);

        drawer = findViewById(R.id.drawer_layout);

        btnNavigationOpen = findViewById(R.id.toolbar);
        btnNavigationOpen.setOnClickListener(mNagivationButtonListener);

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //инициализация кнопки создания нового задания
        btnCreateTask = findViewById(R.id.btn_open_create_task);
        btnCreateTask.setOnClickListener(createTaskListener);

        //инициализация text view
        tvPriority = findViewById(R.id.tv_main_priority);


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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        Fragment createFragment = getSupportFragmentManager().findFragmentById(R.id.create_task_fragment);

        if (findViewById(R.id.create_task_fragment) != null && createFragment == null) {
            Log.i(TAG, "opencreatetaskactivity" + (createFragment == null));
            CreateTaskFragment firstFragment = new CreateTaskFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.create_task_fragment, firstFragment, CREATE_FRAGMENT_TAG).commit();
        }

//        Log.i(TAG, "create task");
//        Task task = new Task();
//        task.setFullDate(new Date());
//        task.setText("sdfsdaf");
//        CalendarUtils.insertTaskIntoCalendar(this, this, task, 2);
    }

    @Override
    public void onSwipeTasks(int position)
    {
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

    @Override
    public void openNagivation() {
        drawer.openDrawer(Gravity.START);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch(id)
        {
            case R.id.nav_eisenhower:
                Intent intent = new Intent(MainActivity.this, Test.class);
                startActivity(intent);
                break;
            case R.id.nav_today:
                presenter.showTodayTask();
                break;
            case R.id.nav_tomorrow:
                presenter.showTomorrowTask();
                break;
            case R.id.nav_this_week:
                presenter.showThisWeekTask();
                break;
            case R.id.nav_choose_date:
                presenter.showDateTask();
                break;
            case R.id.nav_all:
                presenter.showAllTasks();
                break;
            case R.id.nav_settings:
                presenter.showSettings();
                break;
//            case R.id.nav_about:
//                Notification notification = new Notification(this);
//                notification.setNotify();
//                break;
            default:

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void showDateTask(Date date) {
        Log.i(TAG, "showdatetask");
        tasksPagerAdapter.setTaskType(MainSlidePageFragment.TASK_STATE_DATE);
        tasksPagerAdapter.setShowDate(presenter.getShowDate());
        for(Task.Priority priority : Task.Priority.values())
        {
            Fragment fragment = tasksPagerAdapter.getRegisteredFragment(priority.getPriority());
            if (fragment instanceof MainSlidePageFragment)
            {
                MainSlidePageFragment mainSlidePageFragment = (MainSlidePageFragment) fragment;
                mainSlidePageFragment.setTaskDate(date);
                vpTasks.getAdapter().notifyDataSetChanged();
            }
        }
    }

    @Override
    public void showAllTasks() {
        tasksPagerAdapter.setTaskType(MainSlidePageFragment.TASK_STATE_ALL);
        for(Task.Priority priority : Task.Priority.values())
        {
            Fragment fragment = tasksPagerAdapter.getRegisteredFragment(priority.getPriority());
            if (fragment instanceof MainSlidePageFragment)
            {
                MainSlidePageFragment mainSlidePageFragment = (MainSlidePageFragment) fragment;
                mainSlidePageFragment.update();
                vpTasks.getAdapter().notifyDataSetChanged();
            }
        }
    }

    @Override
    public void showThisWeekTask() {
        tasksPagerAdapter.setTaskType(MainSlidePageFragment.TASK_STATE_THIS_WEEK);
        for(Task.Priority priority : Task.Priority.values())
        {
            Fragment fragment = tasksPagerAdapter.getRegisteredFragment(priority.getPriority());
            if (fragment instanceof MainSlidePageFragment)
            {
                MainSlidePageFragment mainSlidePageFragment = (MainSlidePageFragment) fragment;
                mainSlidePageFragment.setTaskThisWeek();
                vpTasks.getAdapter().notifyDataSetChanged();
            }
        }
    }

    @Override
    public void taskCreated(Task.Priority priority) {
        Fragment fragment = tasksPagerAdapter.getRegisteredFragment(priority.getPriority());
        if (fragment instanceof MainSlidePageFragment)
        {
            MainSlidePageFragment mainSlidePageFragment = (MainSlidePageFragment) fragment;
            mainSlidePageFragment.update();
            vpTasks.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void showTodayTask() {
        tasksPagerAdapter.setTaskType(MainSlidePageFragment.TASK_STATE_TODAY);

        for(Task.Priority priority : Task.Priority.values())
        {
            Fragment fragment = tasksPagerAdapter.getRegisteredFragment(priority.getPriority());
            if (fragment instanceof MainSlidePageFragment)
            {
                MainSlidePageFragment mainSlidePageFragment = (MainSlidePageFragment) fragment;
                mainSlidePageFragment.setTaskToday();
                vpTasks.getAdapter().notifyDataSetChanged();
            }
        }

    }

    @Override
    public void showTomorrowTask() {
        tasksPagerAdapter.setTaskType(MainSlidePageFragment.TASK_STATE_TOMORROW);

        for(Task.Priority priority : Task.Priority.values())
        {
            Fragment fragment = tasksPagerAdapter.getRegisteredFragment(priority.getPriority());
            if (fragment instanceof MainSlidePageFragment)
            {
                MainSlidePageFragment mainSlidePageFragment = (MainSlidePageFragment) fragment;
                mainSlidePageFragment.setTaskTomorrow();
                vpTasks.getAdapter().notifyDataSetChanged();
            }
        }
    }

    @Override
    public void editTask(Task task)
    {
        Fragment updateFragment = getSupportFragmentManager().findFragmentById(R.id.create_task_fragment);
        if (findViewById(R.id.create_task_fragment) != null && updateFragment == null) {
            CreateTaskFragment editTaskFragment = CreateTaskFragment.getInstanceEditTask(task);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.create_task_fragment, editTaskFragment).commit();
        }
    }

    private class CreateTaskListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            presenter.createTask();
        }
    }

}
