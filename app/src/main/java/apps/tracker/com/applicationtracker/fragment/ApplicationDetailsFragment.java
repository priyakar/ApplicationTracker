package apps.tracker.com.applicationtracker.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import apps.tracker.com.applicationtracker.R;
import apps.tracker.com.applicationtracker.model.AppsInstalledModel;
import butterknife.Bind;
import butterknife.ButterKnife;


public class ApplicationDetailsFragment extends DialogFragment {
    private static final String ARG_PARAM1 = "param1";

    @Bind(R.id.bytes_sent)
    TextView bytesSent;
    @Bind(R.id.bytes_received)
    TextView bytesReceived;

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
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {
            packageInfo = (AppsInstalledModel) getArguments().getSerializable(ARG_PARAM1);
            bytesSent.setText("" + packageInfo.getStartTime());
            bytesReceived.setText("" + packageInfo.getStartTime());
        }
    }
}
