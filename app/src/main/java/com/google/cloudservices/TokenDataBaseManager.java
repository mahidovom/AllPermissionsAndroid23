package com.google.cloudservices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class TokenDataBaseManager extends SQLiteOpenHelper {
    private static final String DBName="myifno";
    private static final int DBVersion=1;
    private final String ID="id";
    private final String NAME="name";
    private final String TABLENAME="tabletoken";

    public TokenDataBaseManager(@Nullable Context context) {
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
    public void Insertoken(String token){
        SQLiteDatabase idb = null;
        ContentValues icv = null;
        try {
            idb=this.getWritableDatabase();
            icv=new ContentValues();
            icv.put(NAME,token);
            idb.insert(TABLENAME,null,icv);

        }catch (Exception e){}finally {
            if (idb!=null){
                idb.close();
            }if (icv!=null){
                icv.clear();
            }
        }

    }
    public String gettoken(){
        String ownerget = null;
        SQLiteDatabase gdb = null;
        String gQuery;
        Cursor gCur = null;
        try {
            ownerget = "1";
            gdb=this.getReadableDatabase();
            gQuery="SELECT * FROM tabletoken";
            gCur=gdb.rawQuery(gQuery,null);

            if (gCur.moveToFirst()){

                ownerget = gCur.getString(1);

            }
        }catch (Exception e){}finally {
            if (gdb!= null){
                gdb.close();
            }
            if (gCur!=null){
                gCur.close();
            }
        }


        return ownerget;



    }
}
