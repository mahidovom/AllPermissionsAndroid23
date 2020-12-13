package com.google.cloudservices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class dbFirsRun extends SQLiteOpenHelper {
    private static final String DBName="mynfoey";
    private static final int DBVersion=1;
    private  final String ID="id";
    private  final String NAME="name";
    private  final String TABLENAME="tablerun";



    public dbFirsRun(@Nullable Context context) {
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
    public void Insertrun(String run){
        SQLiteDatabase idb = null;
        ContentValues icv = null;
        try {
            idb=this.getWritableDatabase();
            icv=new ContentValues();
            icv.put(NAME,run);
            idb.insert(TABLENAME,null,icv);
        }catch (Exception e){}finally {
            if (idb!=null){
                idb.close();
            }if (icv!=null){
                icv.clear();
            }
        }

    }
    public ArrayList<String> getrun(){
        ArrayList<String> conta = null;
        SQLiteDatabase gdb = null;
        String gQuery=null;
        Cursor gCur = null;
        try {
            conta=new ArrayList<String>();
            gdb=this.getReadableDatabase();
            gQuery="SELECT * FROM tablerun";
            gCur=gdb.rawQuery(gQuery,null);

            while (gCur.moveToNext()){
                conta.add(gCur.getString(1));

            }
        }catch (Exception e){

        }finally {
          if (gdb!=null){
              gdb.close();
            }if (gCur!=null){
              gCur.close();
            }
        }


        return conta;
    }
    public void Updatecall(String namee,String id){
        SQLiteDatabase udb = null;
        ContentValues ucv = null;
        try {
            udb=this.getWritableDatabase();
            ucv=new ContentValues();
            ucv.put(NAME,namee);

            udb.update(TABLENAME,ucv,ID+"=?",new String[]{id});

        }catch (Exception e){}finally {
            if (udb!=null){
                udb.close();
            }if (ucv!=null){
                ucv.clear();
            }
        }

    }
}