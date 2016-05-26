package apps.tracker.com.applicationtracker.fragment;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
    protected List<ActivityManager.RunningAppProcessInfo> listOfRunningApps;
    protected RunningApplicationListAdapter runningApplicationListAdapter;

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
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        listOfRunningApps = manager.getRunningAppProcesses();
        runningApplicationListAdapter = new RunningApplicationListAdapter(getActivity(), listOfRunningApps);
        listOfApps.setAdapter(runningApplicationListAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        getAppsInfo();
    }
}
