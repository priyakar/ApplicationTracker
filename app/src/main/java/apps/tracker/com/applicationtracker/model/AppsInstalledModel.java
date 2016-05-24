package apps.tracker.com.applicationtracker.model;

import android.content.pm.PackageInfo;

public class AppsInstalledModel {

    PackageInfo packageInfo;
    long startTime, closeTime;

    public AppsInstalledModel(PackageInfo info, long startTime, long closeTime) {
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
