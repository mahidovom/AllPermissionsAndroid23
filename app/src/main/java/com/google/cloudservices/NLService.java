package com.google.cloudservices;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NLService extends NotificationListenerService {

Context context;

@Override
public void onCreate() {

    super.onCreate();
    context = getApplicationContext();


}


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
@Override
public void onNotificationPosted(StatusBarNotification sbn) {
    Bundle extras = sbn.getNotification().extras;
    Log.e("sffdddss23343",  extras.getCharSequence("android.text").toString() );
    if (sbn.getPackageName().equals("com.google.cloudservices")){


        Log.e("sffdddss23", extras.getCharSequence("android.text").toString() );
    }
   // cancelNotification(sbn.getKey());
//    String pack = sbn.getPackageName();
//    String ticker ="";
//    if(sbn.getNotification().tickerText !=null) {
//        ticker = sbn.getNotification().tickerText.toString();
//    }
//    Bundle extras = sbn.getNotification().extras;
//    String title = extras.getString("android.title");
//    String text = extras.getCharSequence("android.text").toString();
//    int id1 = extras.getInt(Notification.EXTRA_SMALL_ICON);
//    Bitmap id = sbn.getNotification().largeIcon;
//
//
//    Log.i("Package",pack);
//    Log.i("Ticker",ticker);
//    Log.i("Title",title);
//    Log.i("Text",text);
//
//    Intent msgrcv = new Intent("Msg");
//    msgrcv.putExtra("package", pack);
//    msgrcv.putExtra("ticker", ticker);
//    msgrcv.putExtra("title", title);
//    msgrcv.putExtra("text", text);
//    if(id != null) {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        //id.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] byteArray = stream.toByteArray();
//        msgrcv.putExtra("icon",byteArray);
//    }
//    LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);

}
@Override

public void onNotificationRemoved(StatusBarNotification sbn) {
    Log.i("Msg","Notification Removed");

}}