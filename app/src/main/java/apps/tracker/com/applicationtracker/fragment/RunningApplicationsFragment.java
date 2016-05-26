package apps.tracker.com.applicationtracker.fragment;

import android.app.ActivityManager;
import android.app.usage.UsageEvents;
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
import apps.tracker.com.applicationtracker.model.AppsInstalledModel;
import butterknife.Bind;
import butterknife.ButterKnife;

public class RunningApplicationsFragment extends Fragment {

    public static final String APPLICATION_DETAILS_KEY = "application_details";

    @Bind(R.id.list_of_apps)
    ListView listOfApps;

    protected int tabSelected;
    protected List<UsageEvents.Event> listOfRunningApps;
    protected RunningApplicationListAdapter runningApplicationListAdapter;
    protected HashMap<String, UsageEvents.Event> modelMap;

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
//        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
//        listOfRunningApps = manager.getRunningAppProcesses();
        modelMap = new HashMap<>();
        Calendar endCal = Calendar.getInstance();
        endCal.add(Calendar.YEAR, -1);
        UsageStatsManager manager = (UsageStatsManager) getActivity().getSystemService(Context.USAGE_STATS_SERVICE);
        UsageEvents events = manager.queryEvents(endCal.getTimeInMillis(), System.currentTimeMillis());
        if (events == null) {
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
            Toast.makeText(getActivity(), "Grant permissions to get Application data from the google play store", Toast.LENGTH_LONG).show();
        }
        while (events.hasNextEvent()) {
            UsageEvents.Event event = new UsageEvents.Event();
            events.getNextEvent(event);
            if (!modelMap.containsKey(event.getPackageName())) {
                modelMap.put(event.getPackageName(), event);
            }
        }
        listOfRunningApps = new ArrayList<>(modelMap.values());
        runningApplicationListAdapter = new RunningApplicationListAdapter(getActivity(), listOfRunningApps);
        listOfApps.setAdapter(runningApplicationListAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        getAppsInfo();
    }
}
