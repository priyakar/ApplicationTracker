package apps.tracker.com.applicationtracker.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import apps.tracker.com.applicationtracker.R;
import apps.tracker.com.applicationtracker.fragment.ApplicationsInformationFragment;

public class CustomPagerAdapter extends FragmentPagerAdapter {
    public static final int NUM_TABS = 2;

    Context context;
    ApplicationsInformationFragment fragment;

    public CustomPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (fragment == null) {
            return ApplicationsInformationFragment.newInstance(position);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.apps_installed);
        } else {
            return context.getString(R.string.apps_running);
        }
    }
}
