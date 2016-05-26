package apps.tracker.com.applicationtracker.fragment;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import apps.tracker.com.applicationtracker.R;
import apps.tracker.com.applicationtracker.adapter.RunningApplicationListAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class RunningApplicationsFragment extends Fragment {

    public static final String APPLICATION_DETAILS_KEY = "application_details";

    @Bind(R.id.list_of_apps)
    ListView listOfApps;

    protected int tabSelected;
    protected List<UsageStats> listOfRunningApps, refinedList;
    protected RunningApplicationListAdapter runningApplicationListAdapter;
    protected HashMap<String, UsageStats> modelMap;

    public RunningApplicationsFragment() {
        // Required empty public constructor
    }

    public static RunningApplicationsFragment newInstance(int tabSelected) {

        Bundle args = new Bundle();
        args.putInt(APPLICATION_DETAILS_KEY, tabSelected);
        RunningApplicationsFragment fragment = new RunningApplicationsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabSelected = getArguments().getInt(APPLICATION_DETAILS_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_running_applications, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void getAppsInfo() {
        modelMap = new HashMap<>();
        UsageStatsManager manager = (UsageStatsManager) getActivity().getSystemService(Context.USAGE_STATS_SERVICE);
        listOfRunningApps = manager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, System.currentTimeMillis() - 10000, System.currentTimeMillis());

        for (UsageStats usageStats : listOfRunningApps) {
            if (!modelMap.containsKey(usageStats.getPackageName())) {
                modelMap.put(usageStats.getPackageName(), usageStats);
            }
        }

        refinedList = new ArrayList<>(modelMap.values());
        runningApplicationListAdapter = new RunningApplicationListAdapter(getActivity(), refinedList);
        listOfApps.setAdapter(runningApplicationListAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        getAppsInfo();
    }
}
