package apps.tracker.com.applicationtracker.fragment;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import apps.tracker.com.applicationtracker.R;
import apps.tracker.com.applicationtracker.adapter.ApplicationListAdapter;
import apps.tracker.com.applicationtracker.adapter.RunningApplicationListAdapter;
import apps.tracker.com.applicationtracker.model.AppsInstalledModel;
import butterknife.Bind;
import butterknife.ButterKnife;


public class ApplicationsInformationFragment extends Fragment {

    public static final String APPLICATION_DETAILS_KEY = "application_details";

    @Bind(R.id.list_of_apps)
    ListView listOfApps;

    protected int tabSelected;
    protected List<PackageInfo> listOfAllApps, listOfNonSystemApps;
    protected ArrayList<AppsInstalledModel> appsInstalledModel;
    protected List<ActivityManager.RunningAppProcessInfo> listOfRunningApps;
    protected List<UsageStats> usageStats;
    protected HashMap<String, AppsInstalledModel> modelMap;
    protected ApplicationListAdapter applicationListAdapter;
    protected RunningApplicationListAdapter runningApplicationListAdapter;
    protected UsageStatsManager usageStatsManager;

    public ApplicationsInformationFragment() {
        // Required empty public constructor
    }

    public static ApplicationsInformationFragment newInstance(int tabSelected) {

        Bundle args = new Bundle();
        args.putInt(APPLICATION_DETAILS_KEY, tabSelected);
        ApplicationsInformationFragment fragment = new ApplicationsInformationFragment();
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
        View view = inflater.inflate(R.layout.fragment_applications_information, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void getAppsInfo() {
        listOfAllApps = getActivity().getPackageManager().getInstalledPackages(0);
        listOfNonSystemApps = new ArrayList<>();
        usageStats = new ArrayList<>();
        modelMap = new HashMap<>();
        if (tabSelected == 0) {
            for (PackageInfo app : listOfAllApps) {
                if ((app.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    listOfNonSystemApps.add(app);
                }
            }
            usageStats = getUsageStatistics(UsageStatsManager.INTERVAL_YEARLY);
            for (PackageInfo nonSystemApp : listOfNonSystemApps) {
                String appName = nonSystemApp.packageName;
                for (int i = 0; i < usageStats.size(); i++) {
                    String statsAppName = usageStats.get(i).getPackageName();
                    if (statsAppName.equals(appName) && !modelMap.containsKey(appName)) {
                        modelMap.put(appName, new AppsInstalledModel(nonSystemApp, usageStats.get(i).getFirstTimeStamp(), usageStats.get(i).getLastTimeUsed()));
                    }
                }
            }
            appsInstalledModel = new ArrayList<>(modelMap.values());
            applicationListAdapter = new ApplicationListAdapter(getActivity(), appsInstalledModel);
            listOfApps.setAdapter(applicationListAdapter);
        } else {
            ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
            listOfRunningApps = manager.getRunningAppProcesses();
            runningApplicationListAdapter = new RunningApplicationListAdapter(getActivity(), listOfRunningApps);
            listOfApps.setAdapter(runningApplicationListAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getAppsInfo();

        listOfApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (tabSelected == 0) {
                    ApplicationDetailsFragment fragment = ApplicationDetailsFragment.newInstance();
                    fragment.setPackageInfo(appsInstalledModel.get(position));
                    getChildFragmentManager().beginTransaction().add(fragment, null).commit();
                }
            }
        });
    }

    public List<UsageStats> getUsageStatistics(int intervalType) {
        // Get the app statistics since one year ago from the current time.


        Calendar endCal = Calendar.getInstance();
        endCal.add(Calendar.YEAR, -1);
        usageStatsManager = (UsageStatsManager) getActivity().getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(intervalType, endCal.getTimeInMillis(), System.currentTimeMillis());

        if (queryUsageStats.size() == 0) {
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        }
        return queryUsageStats;
    }
}
