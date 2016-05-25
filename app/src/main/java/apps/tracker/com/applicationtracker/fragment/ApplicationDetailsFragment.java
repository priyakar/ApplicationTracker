package apps.tracker.com.applicationtracker.fragment;

import android.net.TrafficStats;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import apps.tracker.com.applicationtracker.R;
import apps.tracker.com.applicationtracker.TimeFormatUtils;
import apps.tracker.com.applicationtracker.model.AppsInstalledModel;
import butterknife.Bind;
import butterknife.ButterKnife;


public class ApplicationDetailsFragment extends DialogFragment {
    private static final String ARG_PARAM1 = "param1";

    @Bind(R.id.bytes_sent)
    TextView bytesSent;
    @Bind(R.id.bytes_received)
    TextView bytesReceived;
    @Bind(R.id.open_time)
    TextView openTime;
    @Bind(R.id.close_time)
    TextView closeTime;
    @Bind(R.id.app_name)
    TextView appName;

    // TODO: Rename and change types of parameters
    private String mParam1;

    AppsInstalledModel packageInfo;

    public static ApplicationDetailsFragment newInstance(AppsInstalledModel param1) {
        ApplicationDetailsFragment fragment = new ApplicationDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            packageInfo = (AppsInstalledModel) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_application_details, container, false);
        ButterKnife.bind(this, view);
        initializeTextfields();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {
            packageInfo = (AppsInstalledModel) getArguments().getSerializable(ARG_PARAM1);
            initializeTextfields();
        }
    }

    private void initializeTextfields() {
        appName.setText(packageInfo.getPackageInfo().applicationInfo.loadLabel(getActivity().getPackageManager()));
        bytesSent.setText(String.valueOf(TrafficStats.getUidTxBytes(packageInfo.getPackageInfo().applicationInfo.uid)));
        bytesReceived.setText(String.valueOf(TrafficStats.getUidRxBytes(packageInfo.getPackageInfo().applicationInfo.uid)));
        openTime.setText(String.valueOf(TimeFormatUtils.getTimeStamp(packageInfo.getStartTime())));
        closeTime.setText(String.valueOf(TimeFormatUtils.getTimeStamp(packageInfo.getCloseTime())));
    }
}
