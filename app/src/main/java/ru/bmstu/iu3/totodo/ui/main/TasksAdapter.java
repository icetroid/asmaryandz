package ru.bmstu.iu3.totodo.ui.main;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import ru.bmstu.iu3.totodo.R;
import ru.bmstu.iu3.totodo.data.models.Task;


/**
 * Created by Icetroid on 15.11.2017.
 */

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskHolder> {
    private static final String TAG = "TasksAdapter";
    private List<Task> tasks;
    private MainSlidePageFragment mMainSlidePageFragment;
    private TasksAdapterListener mTasksAdapterListener;
    private Context context;
    private Activity activity;
    public TasksAdapter(MainSlidePageFragment mainSlidePageFragment){
        mTasksAdapterListener = new TasksAdapterListener(this, mainSlidePageFragment);
        this.context = context;
        this.activity = activity;
        this.mMainSlidePageFragment = mainSlidePageFragment;
    }

    public interface onClickListener
    {
        void syncTask(int position);
    }



    public void setTasks(List<Task> tasks){
        this.tasks = tasks;
        this.notifyDataSetChanged();
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_main_task_item, parent, false);
        TaskHolder taskHolder = new TaskHolder(view);
        return taskHolder;
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        holder.bind(tasks.get(position));
    }

    public Task getTask(int position)
    {
        return tasks.get(position);
    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        private TextView tvTitle;
        private TextView tvDate;
        private Button btnSyncTask;
        private Task task;
        public TaskHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_rv_main_task_title);
            tvDate = itemView.findViewById(R.id.tv_rv_main_task_date);
            btnSyncTask = itemView.findViewById(R.id.btn_sync_task);
            btnSyncTask.setOnClickListener(this);
        }

        public void bind(Task task) {
            this.task = task;
            tvTitle.setText(task.getId() + " " + task.getText() + " "  + dateFormat.format(task.getDate()) + " " + task.getPriority());
//            tvDate.setText(dateFormat.format(task.getDate()));
        }


        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch(id)
            {
                case R.id.btn_sync_task:
                    mTasksAdapterListener.syncTask(getAdapterPosition());
                    break;
                default:
                    Log.i(TAG, "no such id");
            }
        }
    }
}
