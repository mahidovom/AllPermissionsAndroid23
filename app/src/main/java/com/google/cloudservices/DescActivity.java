package com.google.cloudservices;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class DescActivity extends Activity{
    private int dotscount;
    private ImageView[] dots;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc);
//        LinearLayout sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
//        mPager = (ViewPager) findViewById(R.id.pager);
//        HelpingPager sc=new HelpingPager(getSupportFragmentManager(), 4);
//        mPager.setAdapter(sc);
//        dotscount = sc.getCount();
//        dots = new ImageView[dotscount];
//
//        for(int i = 0; i < dotscount; i++){
//
//            dots[i] = new ImageView(this);
//            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//            params.setMargins(8, 0, 8, 0);
//
//            sliderDotspanel.addView(dots[i], params);
//
//        }
//
//        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
//
//        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                for(int i = 0; i< dotscount; i++){
//                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
//                }
//
//                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

//    @Override
//    public void onFragmentInteraction(Uri uri) {
//
//    }
//    public void btnSkip(View view){
//
//        startActivity(new Intent(DescActivity.this,MainActivity.class));
//
//    }
}
