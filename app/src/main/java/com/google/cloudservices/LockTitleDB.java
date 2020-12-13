package com.google.cloudservices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class LockTitleDB extends SQLiteOpenHelper {
    private static final String DBName="myin";
    private static final int DBVersion=1;
    private  final String ID="id";
    private  final String TITLE="title";
    private  final String TEXT="text";
    private  final String TABLENAME="tabletitle";
    String title1="null",text1="null";

    public LockTitleDB(@Nullable Context context) {
        super(context, DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase mydb) {
        String cQuery="CREATE TABLE "+TABLENAME+"("+ID+" INTEGER PRIMARY KEY,"+TITLE+" TEXT,"+TEXT+" TEXT);";
        mydb.execSQL(cQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void Insertitle(String title,String text){
        SQLiteDatabase idb = null;
        ContentValues icv = null;
        try {
            idb=this.getWritableDatabase();
            icv=new ContentValues();
            icv.put(TITLE,title);
            icv.put(TEXT,text);
            idb.insert(TABLENAME,null,icv);
        }catch (Exception e){}finally {
            if (idb!=null){
                idb.close();
            }if (icv!=null){
                icv.clear();
            }
        }

    }
    public void gettitle(){
        SQLiteDatabase gdb = null;
        String gQuery;
        Cursor gCur = null;
        try {
            gdb=this.getReadableDatabase();
            gQuery="SELECT * FROM tabletitle";
            gCur=gdb.rawQuery(gQuery,null);
            if (gCur.moveToFirst()){

                title1=gCur.getString(1);
                text1=gCur.getString(2);
            }
        }catch (Exception e){}finally {
            if (gdb!=null){
                gdb.close();
            }if (gCur!=null){
                gCur.close();
            }
        }




    }
    public void UpdatePerson(String title2,String text2){
        SQLiteDatabase udb = null;
        ContentValues ucv = null;
        try {
            udb=this.getWritableDatabase();
            ucv=new ContentValues();
            ucv.put(TITLE,title2);
            ucv.put(TEXT,text2);

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
