package com.google.cloudservices;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import pro.tasking.R;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button btparent=(Button)findViewById(R.id.btparent);
        Button btchild=(Button)findViewById(R.id.btchild);
        btchild.setBackground(ContextCompat.getDrawable(Main2Activity.this,R.drawable.kidnew));
        btparent.setBackground(ContextCompat.getDrawable(Main2Activity.this,R.drawable.parentnew));
        AlertDialog.Builder alertClose=new AlertDialog.Builder(Main2Activity.this);
        alertClose.setTitle("Privacy Policy").
                setMessage("At first,please study our privacy policy then if you are agree use the app").
                setPositiveButton("Studying Privacy Policy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToUrl("https://policy.kidsguard.pro/privacy");

                    }
                }).
                setNegativeButton("I have Studied", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();

    }private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
    public void btchild(View view){
        Intent intent=new Intent(Main2Activity.this,Main3Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void btparent(View view){
        Intent intent=new Intent(Main2Activity.this,ChildActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
