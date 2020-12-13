package com.google.cloudservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutostartService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();




        if(action.equals(Intent.ACTION_BOOT_COMPLETED))
        {
                Intent intent2=new Intent(context,StartActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);




        }}

}
