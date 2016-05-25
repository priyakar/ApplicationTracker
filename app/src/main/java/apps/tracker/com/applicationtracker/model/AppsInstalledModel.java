package apps.tracker.com.applicationtracker.model;

import android.content.pm.PackageInfo;

import java.io.Serializable;

public class AppsInstalledModel implements Serializable {

    PackageInfo packageInfo;
    long startTime, closeTime;

    public AppsInstalledModel(PackageInfo info, long closeTime, long startTime) {
        this.startTime = startTime;
        this.closeTime = closeTime;
        this.packageInfo = info;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getCloseTime() {
        return closeTime;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

}
