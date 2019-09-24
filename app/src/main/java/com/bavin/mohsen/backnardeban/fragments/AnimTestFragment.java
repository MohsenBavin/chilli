package com.bavin.mohsen.backnardeban.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bavin.mohsen.backnardeban.Classes.OnboardingPageTransformer;
import com.bavin.mohsen.backnardeban.Classes.TimeService;
import com.bavin.mohsen.backnardeban.R;
import com.bavin.mohsen.backnardeban.ShowFilmActivity;

import java.util.Timer;
import java.util.TimerTask;

public class AnimTestFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu( true );

        View v = inflater.inflate( R.layout.fragment_anim_test, container, false );
         return v;
    }



}