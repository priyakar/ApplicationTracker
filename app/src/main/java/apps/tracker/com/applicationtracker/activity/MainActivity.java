package apps.tracker.com.applicationtracker.activity;

import android.net.TrafficStats;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import apps.tracker.com.applicationtracker.R;
import apps.tracker.com.applicationtracker.adapter.CustomPagerAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @Bind(R.id.bytes_sent)
    TextView bytesSent;

    @Bind(R.id.bytes_received)
    TextView bytesReceived;

    protected CustomPagerAdapter pagerAdapter;
    long mStartRX, mStartTX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupData();
    }

    public void setupData() {
        mStartRX = TrafficStats.getMobileRxBytes();
        mStartTX = TrafficStats.getMobileTxBytes();

        if (mStartRX == TrafficStats.UNSUPPORTED || mStartTX == TrafficStats.UNSUPPORTED) {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Uh Oh!");

            alert.setMessage("Your device does not support traffic stat monitoring.");

            alert.show();

        } else {
            bytesSent.setText(String.valueOf(mStartTX));
            bytesReceived.setText(String.valueOf(mStartRX));
        }

        pagerAdapter = new CustomPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.white));
    }
}
