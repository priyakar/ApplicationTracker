package apps.tracker.com.applicationtracker.model;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import java.io.Serializable;

public class AppsInstalledModel implements Serializable {

    ApplicationInfo packageInfo;
    long startTime, closeTime;

    public AppsInstalledModel(ApplicationInfo info, long closeTime, long startTime) {
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

    public ApplicationInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(ApplicationInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

}
