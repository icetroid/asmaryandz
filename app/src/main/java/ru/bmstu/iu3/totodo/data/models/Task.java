package ru.bmstu.iu3.totodo.data.models;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Icetroid on 15.11.2017.
 */

public class Task
{
    private static final String TAG = "Task";
    private static final String FIELD_TEXT = "text";
    private static final String FIELD_PRIORITY = "priority";
    private static final String FIELD_DATE = "date";
    private static final String FIELD_TIME = "time";
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");



    public static final String FIELD_ID = "id";

    private long id;
    private String text;
    private Priority priority;
    private Date date;
    private boolean dateSet;
    private boolean timeSet;
    private boolean isAddedToCalendar;
    private int notifyTime;

    public boolean isAddedToCalendar() {
        return isAddedToCalendar;
    }

    public void setAddedToCalendar(boolean addedToCalendar) {
        isAddedToCalendar = addedToCalendar;
    }

    public int getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(int notifyTime) {
        this.notifyTime = notifyTime;
    }

    public Task()
    {
        id = -1;
        text = null;
        priority = Priority.A;
        dateSet = true;
        timeSet = false;
        date = new Date();
        notifyTime = 0;
    }

    public boolean getDateSet()
    {
        return dateSet;
    }

    public boolean getTimeSet()
    {
        return timeSet;
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



    public List<String> getEmptyFields()
    {
        List<String> emptyFields = new LinkedList<>();
        if(text == null)
        {
            emptyFields.add(FIELD_TEXT);
        }
        if(priority == null)
        {
            emptyFields.add(FIELD_PRIORITY);
        }
        if(!dateSet)
        {
            emptyFields.add(FIELD_DATE);
        }
        if(!timeSet)
        {
            emptyFields.add(FIELD_TIME);
        }
        return emptyFields;
    }

    public Date getDate() {
        return date;
    }

    public void setFullDate(Date date)
    {
        dateSet = true;
        timeSet = true;
        this.date = date;
    }

    public void setTime(Date date)
    {
        this.date = date;
        timeSet = true;
    }

    public void setDate(Date date)
    {
        this.date = date;
        dateSet = true;
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
        Log.i(TAG, "text = " + text + ".");
        text = text.trim();
        if(text.isEmpty()) {
            this.text = null;
            return;
        }
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

