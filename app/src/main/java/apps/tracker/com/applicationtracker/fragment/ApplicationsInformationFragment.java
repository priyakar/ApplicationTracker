package apps.tracker.com.applicationtracker.fragment;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import apps.tracker.com.applicationtracker.R;
import apps.tracker.com.applicationtracker.adapter.ApplicationListAdapter;
import apps.tracker.com.applicationtracker.adapter.RunningApplicationListAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;


public class ApplicationsInformationFragment extends Fragment {

    public static final String APPLICATION_DETAILS_KEY = "application_details";

    @Bind(R.id.list_of_apps)
    ListView listOfApps;

    protected int tabSelected;
    protected List<PackageInfo> listOfAllApps, listOfNonSystemApps;
    protected List<ActivityManager.RunningAppProcessInfo> listOfRunningApps;
    protected ApplicationListAdapter applicationListAdapter;
    protected RunningApplicationListAdapter runningApplicationListAdapter;

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
        listOfAllApps = getActivity().getPackageManager().getInstalledPackages(0);
        listOfNonSystemApps = new ArrayList<>();
        if (tabSelected == 0) {
            for (PackageInfo app : listOfAllApps) {
                if ((app.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    listOfNonSystemApps.add(app);
                }
            }
            applicationListAdapter = new ApplicationListAdapter(getActivity(), listOfNonSystemApps);
            listOfApps.setAdapter(applicationListAdapter);
        } else {
            ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
            listOfRunningApps = manager.getRunningAppProcesses();
            runningApplicationListAdapter = new RunningApplicationListAdapter(getActivity(), listOfRunningApps);
            listOfApps.setAdapter(runningApplicationListAdapter);
        }
        return view;
    }
}
