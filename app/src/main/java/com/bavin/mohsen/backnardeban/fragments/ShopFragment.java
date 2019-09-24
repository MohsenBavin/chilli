package com.bavin.mohsen.backnardeban.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bavin.mohsen.backnardeban.Classes.OnboardingPageTransformer;
import com.bavin.mohsen.backnardeban.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment {

    ViewPager viewPager;

    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_shop, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        viewPager = view.findViewById(R.id.viewPagerTest);
        // Set Adapter on ViewPager
        viewPager.setAdapter(new ScreenSlidePagerAdapter(getChildFragmentManager()));

        // Set PageTransformer on ViewPager
        //viewPager.setPageTransformer(false, new OnboardingPageTransformer());

        viewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    int curr = viewPager.getCurrentItem();
                    int lastReal = Objects.requireNonNull( viewPager.getAdapter() ).getCount() - 2;
                    Toast.makeText( getActivity(), ""+lastReal, Toast.LENGTH_SHORT ).show();
                    if (curr == 0) {
                        viewPager.setCurrentItem(lastReal, false);
                    } else if (curr > lastReal) {
                        viewPager.setCurrentItem(1, false);
                    }
                }

            }
        } );

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new AnimTestFragment();
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
