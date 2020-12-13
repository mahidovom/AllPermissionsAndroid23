package com.google.cloudservices;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class HelpingPager extends FragmentStatePagerAdapter {

    int tabCount;

    public HelpingPager(FragmentManager fm , int tabCount)
    {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }



    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }

    public Fragment getItem(int position)
    {
        switch (position) {
            case 0:
                BlankFragment tab1 = new BlankFragment();
                return tab1;
            case 1:
                BlankFragment2 tab2 = new BlankFragment2();
                return tab2;
            case 2:
                BlankFragment3 tab3 = new BlankFragment3();
                return tab3;
            case 3:
                BlankFragment4 tab4 = new BlankFragment4();
                return tab4;


            default:
                return null;
        }
    }
}
