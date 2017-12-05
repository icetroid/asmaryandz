package ru.bmstu.iu3.totodo.ui.main;

import android.view.View;

/**
 * Created by Icetroid on 05.12.2017.
 */

public class NavigationButtonListener implements View.OnClickListener {

    private MainPresenter presenter;
    public NavigationButtonListener(MainPresenter presenter)
    {
        this.presenter = presenter;
    }

    @Override
    public void onClick(View view) {
        presenter.openNavigationDrawer();
    }
}
