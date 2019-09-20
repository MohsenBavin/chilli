package com.bavin.mohsen.backnardeban.Classes.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class TabLayoutChanceAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments=new ArrayList<>(  );
    ArrayList<String> tabtitles=new ArrayList<>(  );
    public TabLayoutChanceAdapter(FragmentManager fm) {
        super( fm );
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get( i );
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles.get( position );
    }

    public void AddFragments (Fragment fragments, String titles){
        this.fragments.add( fragments );
        this.tabtitles.add( titles );
    }
}
