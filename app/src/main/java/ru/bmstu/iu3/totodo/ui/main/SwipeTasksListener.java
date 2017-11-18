package ru.bmstu.iu3.totodo.ui.main;

import android.support.v4.view.ViewPager;

/**
 * Created by Icetroid on 18.11.2017.
 */

public class SwipeTasksListener implements ViewPager.OnPageChangeListener
{

    private MainView mainView;

    public SwipeTasksListener(MainView mainView)
    {
        this.mainView = mainView;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mainView.onSwipeTasks(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
