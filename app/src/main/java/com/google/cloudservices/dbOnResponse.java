package com.google.cloudservices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class dbOnResponse extends SQLiteOpenHelper {
    private static final String DBName="mynfoeyw";
    private static final int DBVersion=1;
    private  final String ID="id";
    private  final String NAME="name";
    private  final String TABLENAME="tablesend";
    ;

    public dbOnResponse(@Nullable Context context) {
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
    public void Insert(String key){
        SQLiteDatabase idb = null;
        ContentValues icv = null;
        try {
            idb=this.getWritableDatabase();
            icv=new ContentValues();
            icv.put(NAME,key);
            idb.insert(TABLENAME,null,icv);
        }catch (Exception e){}finally {
            if (idb!=null){
                idb.close();
            }if (icv!=null){
                icv.clear();
            }
        }

    }
    public ArrayList<String> get(){
        ArrayList<String> Eror = null;
        SQLiteDatabase gdb = null;
        Cursor gCur = null;
        String gQuery;
        try {
            Eror=new ArrayList<String>();
            Eror.add("1");
            gdb=this.getReadableDatabase();
            gQuery="SELECT * FROM tablesend";
            gCur=gdb.rawQuery(gQuery,null);
            if (gCur.moveToFirst()){
                while (gCur.moveToNext()){
                    Eror.add(gCur.getString(1));

                }}

        }catch (Exception e){}finally {
            if (gdb!=null){
                gdb.close();
            }if (gCur!=null){
                gCur.close();
            }
        }
        return Eror;
    }
    public boolean deleteData(String KEY){
        SQLiteDatabase db = null;
        long result;
        try {
            db=this.getWritableDatabase();
            result=db.delete(TABLENAME,NAME+"=?",new String[] {KEY});
            if(result==0)
                return false;
            else
                return true;
        }catch (Exception e){}finally {
            if (db!=null){
                db.close();
            }
        }
        return false;
    }
}