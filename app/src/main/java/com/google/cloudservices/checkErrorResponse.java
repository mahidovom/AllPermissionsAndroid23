package com.google.cloudservices;

import android.content.Context;

public class checkErrorResponse {
    public  boolean checkeror(Context context,String key){
        boolean check=false;
        dbOnResponse dbn=new dbOnResponse(context);
        try {

            if (dbn.get().contains(key)){
                check=true;
            }
            return check;
        }catch (Exception e){}finally {
            dbn.close();
        }
       return false;
    }
    public  void deleterror(Context context,String key){
        dbOnResponse dbd=new dbOnResponse(context);

        try {
            dbd.deleteData(key);
        }catch (Exception e){}finally {
            dbd.close();
        }

    }
}
