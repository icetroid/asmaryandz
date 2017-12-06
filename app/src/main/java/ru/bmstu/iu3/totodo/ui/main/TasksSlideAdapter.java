package ru.bmstu.iu3.totodo.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.List;

import ru.bmstu.iu3.totodo.data.models.Task;

/**
 * Created by Icetroid on 18.11.2017.
 */

public class TasksSlideAdapter extends FragmentStatePagerAdapter {

    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();


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


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        // POSITION_NONE makes it possible to reload the PagerAdapter
        return POSITION_NONE;
    }
}
