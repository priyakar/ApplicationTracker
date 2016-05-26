package apps.tracker.com.applicationtracker.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatUtils {

    public static String getTimeStamp(long milliseconds) {
        Date date = new Date(milliseconds);
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy\nhh:mm:ss a");
        return formatter.format(date);
    }
}
