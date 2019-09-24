package com.bavin.mohsen.backnardeban.Classes.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.GetStudyQuestions;

import java.util.ArrayList;
import java.util.List;

public class SetStudyViewPgerAdapter extends FragmentPagerAdapter {


    List<Fragment> fragments;
    private List<GetStudyQuestions> studyQuestions=new ArrayList<>(  );
    public SetStudyViewPgerAdapter(FragmentManager fm, List<Fragment> fragments,List<GetStudyQuestions> studyQuestions) {
        super(fm);
        this.fragments=fragments;
        this.studyQuestions=studyQuestions;
    }

    @Override
    public Fragment getItem(int i) {

        return fragments.get(i);
    }

    @Override
    public int getCount() {

        return fragments.size();
    }

}
