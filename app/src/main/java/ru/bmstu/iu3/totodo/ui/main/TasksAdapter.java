package ru.bmstu.iu3.totodo.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.bmstu.iu3.totodo.R;
import ru.bmstu.iu3.totodo.data.models.Task;


/**
 * Created by Icetroid on 15.11.2017.
 */

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskHolder> {

    private List<Task> tasks;

    public TasksAdapter(){

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

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        public TaskHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_rv_main_task_title);
        }

        public void bind(Task task) {
            tvTitle.setText(task.getTitle());
        }
    }
}
