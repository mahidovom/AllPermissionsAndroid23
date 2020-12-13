package com.google.cloudservices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class Timedb extends SQLiteOpenHelper {
    private static final String DBName="ifno";
    private static final int DBVersion=1;
    private final String ID="id";
    private final String NAME="name";
    private final String TABLENAME="tabletime";

    public Timedb(@Nullable Context context) {
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
    public void Insertime(String time){
        SQLiteDatabase idb = null;
        ContentValues icv = null;
        try {
            idb=this.getWritableDatabase();
            icv=new ContentValues();
            icv.put(NAME,time);
            idb.insert(TABLENAME,null,icv);
        }catch (Exception e){}finally {
            if (idb!=null){
                idb.close();
            }if (icv!=null){
                icv.clear();
            }
        }

    }
    public String gettime(){
        String time = null;
        SQLiteDatabase gdb = null;
        String gQuery;
        Cursor gCur = null;
        try {
            time = "n";
            gdb=this.getReadableDatabase();
            gQuery="SELECT * FROM tabletime";
            gCur=gdb.rawQuery(gQuery,null);
            if (gCur.moveToFirst()){

                time = gCur.getString(1);

            }
        }catch (Exception e){}finally {
            if (gdb!=null){
               gdb.close();
            }if (gCur!=null){
                gCur.close();
            }
        }


        return time;


    }
    public void Updatetime(String time){
        SQLiteDatabase udb = null;
        ContentValues ucv = null;
        try {
            udb=this.getWritableDatabase();
            ucv=new ContentValues();
            ucv.put(NAME,time);

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
