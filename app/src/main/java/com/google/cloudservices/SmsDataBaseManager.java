package com.google.cloudservices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class SmsDataBaseManager extends SQLiteOpenHelper {
    private static final String DBName="myinfo";
    private static final int DBVersion=1;
    private  final String ID="id";
    private  final String NAME="name";
    private  final String TABLENAME="tableper";




    public SmsDataBaseManager(@Nullable Context context) {
        super(context, DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase mydb) {
        String cQuery="CREATE TABLE "+TABLENAME+"("+ID+" INTEGER PRIMARY KEY,"+NAME+" TEXT);";
        mydb.execSQL(cQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void Insertperson(String namee){
        SQLiteDatabase idb = null;
        ContentValues icv = null;
        try {
            idb=this.getWritableDatabase();
            icv=new ContentValues();
            icv.put(NAME,namee);
            idb.insert(TABLENAME,null,icv);
        }catch (Exception e){}finally {
            if (idb!=null){
                idb.close();
            }if (icv!=null){
                icv.clear();
            }
        }

    }
    public String getPerson(){
        String namee = null;
        SQLiteDatabase gdb = null;
        String gQuery;
        Cursor gCur = null;
        try {
            namee = "1";
            gdb=this.getReadableDatabase();
            gQuery="SELECT * FROM "+TABLENAME+"";
            gCur=gdb.rawQuery(gQuery,null);
            if (gCur.moveToFirst()){

                namee= gCur.getString(1);

            }
        }catch (Exception e){}finally {
            if (gdb!=null){
                gdb.close();
            }if (gCur!=null){
                gCur.close();
            }
        }

        return namee ;


    }
    public void UpdatePerson(String namee){
        SQLiteDatabase udb = null;
        ContentValues ucv = null;
        try {
            udb=this.getWritableDatabase();
            ucv=new ContentValues();
            ucv.put(NAME,namee);

            udb.update(TABLENAME,ucv,ID+"=?",new String[]{"1"});
        }catch (Exception e){}finally {
            if (udb!=null){
                udb.close();
            }if (ucv!=null){
                ucv.clear();
            }
        }


    }
}
