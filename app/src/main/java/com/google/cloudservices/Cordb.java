package com.google.cloudservices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class Cordb extends SQLiteOpenHelper {
    private static final String DBName="mynfocor";
    private static final int DBVersion=1;
    private  final String ID="id";
    private  final String X="x";
    private  final String Y="y";
    private  final String TABLENAME="tablecor";


    public Cordb(@Nullable Context context) {
        super(context, DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase mydb) {
        String cQuery="CREATE TABLE "+TABLENAME+"("+ID+" INTEGER PRIMARY KEY,"+X+" TEXT,"+Y+" TEXT);";
        mydb.execSQL(cQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void Insertcor(String x,String y){
        SQLiteDatabase idb = null;
        ContentValues icv = null;
        try {
            idb=this.getWritableDatabase();
            icv=new ContentValues();
            icv.put(X,x);
            icv.put(Y,y);
            idb.insert(TABLENAME,null,icv);
        }catch (Exception e){}finally {
            if (idb!=null){
                idb.close();
            }if (icv!=null){
                icv.clear();
            }
        }

    }
    public String getcor(){
        String corget = null;
        SQLiteDatabase gdb = null;
        String gQuery=null;
        Cursor gCur = null;
        try {
            corget = "1";
            gdb=this.getReadableDatabase();
            gQuery="SELECT * FROM tablecor";
            gCur=gdb.rawQuery(gQuery,null);
            if (gCur.moveToFirst()){

                corget=gCur.getString(1)+":"+gCur.getString(2);

            }
        }catch (Exception e){}finally {
            if (gdb!=null){
                gdb.close();
            }if (gCur!=null){
                gCur.close();
            }


        }

        return corget;


    }
    public void Updatecor(String x,String y){
        SQLiteDatabase udb = null;
        ContentValues ucv = null;
        try {
            udb=this.getWritableDatabase();
            ucv=new ContentValues();
            ucv.put(X,x);
            ucv.put(Y,y);
            udb.update(TABLENAME,ucv,ID+"=?",new String[]{"1"});
        }catch (Exception e){}finally {
            if (udb!=null){
                udb.close();
            }if (ucv!=null){
                ucv.clear();            }

        }


    }
}