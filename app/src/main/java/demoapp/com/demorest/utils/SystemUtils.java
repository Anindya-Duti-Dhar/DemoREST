package demoapp.com.demorest.utils;

import android.app.ActivityManager;
import android.content.Context;
import demoapp.com.demorest.App;
import java.util.List;

public class SystemUtils {

    public static boolean isAppRunningNow() {
        ActivityManager activityManager = (ActivityManager) App.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        if (!runningTasks.isEmpty()) {
            return runningTasks.get(0).topActivity.getPackageName().equalsIgnoreCase(App.getInstance().getPackageName());
        } else {
            return false;
        }
    }
}