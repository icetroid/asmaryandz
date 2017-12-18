package ru.bmstu.iu3.totodo.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ru.bmstu.iu3.totodo.R;
import ru.bmstu.iu3.totodo.data.models.Task;


/**
 * Created by Icetroid on 15.11.2017.
 */

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskHolder> implements SharedPreferences.OnSharedPreferenceChangeListener{
    private static final String TAG = "TasksAdapter";
    private static final int MAX_TEXT_LENGTH = 50;
    private List<Task> tasks;
    private MainSlidePageFragment mMainSlidePageFragment;
    private TasksAdapterListener mTasksAdapterListener;
    public TasksAdapter(MainSlidePageFragment mainSlidePageFragment){
        mTasksAdapterListener = new TasksAdapterListener(mainSlidePageFragment);
        this.mMainSlidePageFragment = mainSlidePageFragment;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        this.notifyDataSetChanged();
    }

    public interface onClickListener
    {
        void syncTask(Task task);
        void removeTask(Task task);
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
//        if(position >= getItemCount())
//        {
//            //holder.itemView.findViewById(R.id.rv_task_item).setBackgroundResource(R.drawable.task_item_background_none);
//            Log.i(TAG, "none " + position);
//        }
//        else
//        {
//            //holder.itemView.findViewById(R.id.rv_task_item).setBackgroundResource(R.drawable.task_item_background);
//            Log.i(TAG, "background " + position);
//        }

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
        private DateFormat dateFormatTwentyFour = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        private DateFormat dateFormatTwelve = new SimpleDateFormat("dd/MM/yyyy KK:mm a", Locale.US);

        private TextView tvTitle;
        private TextView tvDate;
        private ImageButton btnSyncTask;
        private ImageButton btnDeleteTask;
        private Task task;
        public TaskHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_rv_main_task_title);
            tvDate = itemView.findViewById(R.id.tv_rv_main_task_date);
            btnSyncTask = itemView.findViewById(R.id.btn_sync_task);
            btnSyncTask.setOnClickListener(this);
            btnDeleteTask = itemView.findViewById(R.id.btn_delete_task);
            btnDeleteTask.setOnClickListener(this);
            itemView.findViewById(R.id.rv_task_item).setOnClickListener(this);
            Log.i(TAG, "con");
        }



        public void bind(Task task) {
            this.task = task;
            //tvTitle.setText(task.getId() + " " + task.getText() + " "  + dateFormat.format(task.getDate()) + " " + task.getPriority());
            tvTitle.setText(getFormattedTaskText());

            boolean isTwenty = PreferenceManager.getDefaultSharedPreferences(mMainSlidePageFragment.getContext()).getBoolean(mMainSlidePageFragment.getContext().getString(R.string.pref_time_format), true);

            updateDate(isTwenty);
        }

        public void updateDate(boolean isTwentyFour)
        {

            if(isTwentyFour)
            {
                tvDate.setText(dateFormatTwentyFour.format(task.getDate()));
            }
            else
            {
                tvDate.setText(dateFormatTwelve.format(task.getDate()));
            }

        }


        private String getFormattedTaskText()
        {
            String text = task.getText();
            if(text == null)
            {
                return "";
            }
            if(text.length() > MAX_TEXT_LENGTH)
            {
                text = text.substring(0, MAX_TEXT_LENGTH);
                text = text.substring(0, text.lastIndexOf(" "));
                text += "...";
            }
            return text;
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch(id)
            {
                case R.id.btn_delete_task:
                    mTasksAdapterListener.removeTask(task);
                    Log.i(TAG, "delete");
                    break;
                case R.id.rv_task_item:
                    mMainSlidePageFragment.editTask(task);
                    break;

                default:
                    Log.i(TAG, "no such id");
            }
        }
    }
}
