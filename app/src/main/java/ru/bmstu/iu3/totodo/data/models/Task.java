package ru.bmstu.iu3.totodo.data.models;

/**
 * Created by Icetroid on 15.11.2017.
 */

public class Task {
    private long id;
    private String title;
    private String text;

    public Task(long id, String title, String text)
    {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}

