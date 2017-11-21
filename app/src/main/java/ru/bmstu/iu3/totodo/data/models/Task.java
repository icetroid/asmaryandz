package ru.bmstu.iu3.totodo.data.models;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Icetroid on 15.11.2017.
 */

public class Task
{
    private static final String TAG = "Task";
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private long id;
    private String text;
    private Priority priority;
    private Date date;

    public Task()
    {

    }

    public Task(long id, String text)
    {
        this.id = id;
        this.text = text;
    }


    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setPriority(String priority)
    {
        Log.i(TAG, "Priority = " + priority);
        this.priority = Priority.valueOf(priority);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format("id: %d, text: %s, priority: %s, due date: %s", getId(), getText(), getPriority(), dateFormat.format(date));
    }

    public enum Priority{
        A(0),
        B(1),
        C(2),
        D(3);

        private int priority;
        Priority(int priority){
            this.priority = priority;
        }

        public int getPriority(){
            return priority;
        }

        public static Priority getPriority(int priorityValue)
        {
            for(Priority priority : Priority.values())
            {
                if(priority.getPriority() == priorityValue)
                {
                    return priority;
                }
            }
            return null;
        }

    }
}

