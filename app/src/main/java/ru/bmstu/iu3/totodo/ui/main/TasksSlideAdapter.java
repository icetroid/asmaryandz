package ru.bmstu.iu3.totodo.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ru.bmstu.iu3.totodo.data.models.Task;

/**
 * Created by Icetroid on 18.11.2017.
 */

public class TasksSlideAdapter extends FragmentStatePagerAdapter {

    public static final int NUM_PAGES = Task.Priority.values().length;

    public TasksSlideAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MainSlidePageFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
