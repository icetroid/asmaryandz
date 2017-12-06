package ru.bmstu.iu3.totodo.ui.test;

import java.util.Map;

/**
 * Created by Icetroid on 06.12.2017.
 */

public class Question
{
    private String question;
    private Map<String, Boolean> options;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Map<String, Boolean> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Boolean> options) {
        this.options = options;
    }

    public Question()
    {


    }

}
