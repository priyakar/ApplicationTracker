package apps.tracker.com.applicationtracker.adapter;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import apps.tracker.com.applicationtracker.R;

public class RunningApplicationListAdapter extends BaseAdapter {

    Context context;
    List<RunningAppProcessInfo> packageInfo;
    LayoutInflater inflater;
    PackageManager packageManager;

    public RunningApplicationListAdapter(Context context, List<RunningAppProcessInfo> packageInfo) {
        this.context = context;
        this.packageInfo = packageInfo;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        packageManager = context.getPackageManager();
    }

    @Override
    public int getCount() {
        return packageInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return packageInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.applicaiton_details_item, parent, false);
            holder.applicationName = (TextView) convertView.findViewById(R.id.app_name);
            holder.lastOpened = (TextView) convertView.findViewById(R.id.last_opened_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            holder.applicationName.setText(getApplicationName(position));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        holder.lastOpened.setText(String.valueOf(packageInfo.get(position).uid));
        return convertView;
    }

    @NonNull
    private String getApplicationName(int position) throws PackageManager.NameNotFoundException {
        String str = packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageInfo.get(position).processName, PackageManager.GET_META_DATA)).toString();
        if (TextUtils.isEmpty(str)) {
            str = packageInfo.get(position).processName;
        }
        return str;
    }

    public class ViewHolder {
        public TextView applicationName, lastOpened;
    }
}
