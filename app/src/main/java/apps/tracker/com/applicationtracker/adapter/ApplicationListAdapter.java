package apps.tracker.com.applicationtracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apps.tracker.com.applicationtracker.R;
import apps.tracker.com.applicationtracker.utils.TimeFormatUtils;
import apps.tracker.com.applicationtracker.model.AppsInstalledModel;

public class ApplicationListAdapter extends BaseAdapter {

    Context context;
    List<AppsInstalledModel> packageInfo;
    LayoutInflater inflater;

    public ApplicationListAdapter(Context context, ArrayList<AppsInstalledModel> packageInfo) {
        this.context = context;
        this.packageInfo = packageInfo;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        holder.applicationName.setText(packageInfo.get(position).getPackageInfo().loadLabel(context.getPackageManager()).toString());
        holder.lastOpened.setText(TimeFormatUtils.getTimeStamp(packageInfo.get(position).getCloseTime()));

        return convertView;
    }

    public class ViewHolder {
        public TextView applicationName, lastOpened;
    }
}
