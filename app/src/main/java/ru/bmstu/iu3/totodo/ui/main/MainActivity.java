package ru.bmstu.iu3.totodo.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

import ru.bmstu.iu3.totodo.R;
import ru.bmstu.iu3.totodo.data.models.Task;
import ru.bmstu.iu3.totodo.ui.createTask.CreateTaskActivity;

public class MainActivity extends AppCompatActivity implements MainView{

    private MainPresenter presenter;
    private RecyclerView rvTasks;
    private TasksAdapter tasksAdapter;
    private Button btnCreateTask;

    private CreateTaskListener createTaskListener = new CreateTaskListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenterImpl(this);

        //инициализация recycle view
        rvTasks = findViewById(R.id.rv_tasks);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvTasks.setLayoutManager(linearLayoutManager);
        rvTasks.setHasFixedSize(true);
        tasksAdapter = new TasksAdapter();
        rvTasks.setAdapter(tasksAdapter);

        //инициализация кнопки создания нового задания
        btnCreateTask = findViewById(R.id.btn_create_task);
        btnCreateTask.setOnClickListener(createTaskListener);

        presenter.getAllTasks();
    }

    @Override
    public void setTasks(List<Task> tasks) {
        tasksAdapter.setTasks(tasks);
    }

    @Override
    public void openCreateTaskActivity() {
        Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
        startActivity(intent);
    }

    private class CreateTaskListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            presenter.createTask();
        }
    }

}
