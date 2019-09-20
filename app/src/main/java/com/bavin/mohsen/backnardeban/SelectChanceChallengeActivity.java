package com.bavin.mohsen.backnardeban;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bavin.mohsen.backnardeban.Classes.Adapters.TabFragmentAdapter;
import com.bavin.mohsen.backnardeban.fragments.PercentFragment;
import com.bavin.mohsen.backnardeban.fragments.SelectChanceStudyFragment;

public class SelectChanceChallengeActivity extends AppCompatActivity {
    TabLayout mTabsChance;
    ViewPager mViewPagerChance;
    private int indicatorWidthChance;
    View mIndicatorChance;
    private boolean changedTab=false;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_chance_challenge );
        mTabsChance = findViewById(R.id.tab_Chance);
        mIndicatorChance = findViewById(R.id.indicator_Chance);
        mViewPagerChance = findViewById(R.id.viewPager_Chance);

        //Set up the view pager and fragments

        TabFragmentAdapter adapter = new TabFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment( new PercentFragment(), "برنامه درسی چالش ها");
        adapter.addFragment( new SelectChanceStudyFragment(),"انتخاب درس");
        mViewPagerChance.setAdapter(adapter);
        mTabsChance.setupWithViewPager(mViewPagerChance);
        mViewPagerChance.setCurrentItem(1);
        mTabsChance.getTabAt( 1 );

        mTabsChance.post(new Runnable() {
            @Override
            public void run() {
                indicatorWidthChance = mTabsChance.getWidth() / mTabsChance.getTabCount();
                //Toast.makeText( SelectChanceChallengeActivity.this,""+indicatorWidthChance,Toast.LENGTH_SHORT ).show();
                FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) mIndicatorChance.getLayoutParams();
                indicatorParams.width = indicatorWidthChance;
                mIndicatorChance.setLayoutParams(indicatorParams);
            }
        });


        mViewPagerChance.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float positionOffset, int positionOffsetPx) {
                if (changedTab==false)
                {
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)mIndicatorChance.getLayoutParams();
                   // Toast.makeText( SelectChanceChallengeActivity.this,
                           // ""+indicatorWidthChance+"v"+params.leftMargin,Toast.LENGTH_SHORT ).show();
                    //params.leftMargin = 511;
                    float translationOffset =  (positionOffset+i) * indicatorWidthChance ;
                    params.leftMargin = mTabsChance.getWidth() / mTabsChance.getTabCount();
                    mIndicatorChance.setLayoutParams(params);
                    changedTab=true;
                }else {
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)mIndicatorChance.getLayoutParams();

                    float translationOffset =  (positionOffset+i) * indicatorWidthChance ;
                    params.leftMargin = (int) translationOffset;

                    mIndicatorChance.setLayoutParams(params);
                }

            }

            @Override
            public void onPageSelected(int i) {


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity( new Intent( SelectChanceChallengeActivity.this,MainActivity.class ) );
        finish();
    }
}
