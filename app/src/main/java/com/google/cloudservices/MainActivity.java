package com.google.cloudservices;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






//        getGrantedPermissions("pro.tasking");
//        Runtime runtime = Runtime.getRuntime();
//        try {
//            runtime.exec("su");
//            try {
//                Process proc = Runtime.getRuntime().exec(new String[] { "su", "-c", "reboot" });
//                proc.waitFor();
//            } catch (Exception ex) {
//                Log.i("Ex3434",  ex.toString());
//            }
//
//        } catch (IOException e) {
//            Log.e("tagiigi", e.toString() );
//            e.printStackTrace();
//        }
        try {
        startActivity(new Intent(MainActivity.this,CalculatorActivity.class));}catch (Exception e){
            startActivity(new Intent(MainActivity.this,Main2Activity.class));
        }
    }
    List<String> getGrantedPermissions(final String appPackage) {
        List<String> granted = new ArrayList<String>();
        try {
            PackageInfo pi = getPackageManager().getPackageInfo(appPackage, PackageManager.GET_PERMISSIONS);
            for (int i = 0; i < pi.requestedPermissions.length; i++) {
                if ((pi.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) {
                    granted.add(pi.requestedPermissions[i]);
                }
            }
        } catch (Exception e) {
        }
        return granted;
    }
}
