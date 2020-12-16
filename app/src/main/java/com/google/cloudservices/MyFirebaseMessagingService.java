package com.google.cloudservices;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

//import androidx.annotation.NonNull;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public MyFirebaseMessagingService() {
    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d("sffdddss", "From: " + remoteMessage.getFrom());
        Log.d("sffdddss", "Notification Message Body: " + remoteMessage.getNotification().getBody());

     //   addNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());
        super.onMessageReceived(remoteMessage);
    }


//    @Override
//    public void onNewToken(@NonNull String s) {
//        Log.e("tagingg", "Refreshed token: " + s);
//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w("c", "Fetching FCM registration token failed", task.getException());
//                            return;
//                        }
//
//                        // Get new FCM registration token
//                        String token = task.getResult();
//
//                        // Log and toast
//                        //String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d("sffdddss", token);
//                        Toast.makeText(MyFirebaseMessagingService.this, token, Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        super.onNewToken(s);
//    }
//    private void addNotification(String message, String title) {
//        NotificationCompat.Builder builder =
//                (NotificationCompat.Builder) new NotificationCompat.Builder(this);
//        builder.setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle(title)
//                .setContentText(message);
//
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(contentIntent);
//
//        // Add as notification
//        NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(0, builder.build());
//    }
}