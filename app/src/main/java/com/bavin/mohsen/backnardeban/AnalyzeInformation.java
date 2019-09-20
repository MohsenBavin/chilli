package com.bavin.mohsen.backnardeban;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.bavin.mohsen.backnardeban.Classes.Adapters.TabFragmentAdapter;
import com.bavin.mohsen.backnardeban.fragments.AnalyzeFragment;
import com.bavin.mohsen.backnardeban.fragments.PercentFragment;

public class AnalyzeInformation extends AppCompatActivity {
    TabLayout mTabs;
    View mIndicator;
    ViewPager mViewPager;
    private int indicatorWidth;
    private boolean changeTab=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_analyze_information );
        //Assign view reference
        mTabs = findViewById(R.id.tab);
        mIndicator = findViewById(R.id.indicator);
        mViewPager = findViewById(R.id.viewPager);

        //Set up the view pager and fragments

        TabFragmentAdapter adapter = new TabFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment( new AnalyzeFragment(),"تحلیل اطلاعات");
        adapter.addFragment( new PercentFragment(), "نتایج بازی ها");
        mViewPager.setAdapter(adapter);
        mTabs.setupWithViewPager(mViewPager);

        mViewPager.setCurrentItem(1);
        mTabs.getTabAt( 1 );
        //Determine indicator width at runtime
        mTabs.post(new Runnable() {
            @Override
            public void run() {
                if (changeTab==false){
                    indicatorWidth = mTabs.getWidth() / mTabs.getTabCount();
                    //Assign new width
                    FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) mIndicator.getLayoutParams();
                    indicatorParams.width = indicatorWidth;
                    mIndicator.setLayoutParams(indicatorParams);
                    changeTab=true;
                }else {
                    indicatorWidth = mTabs.getWidth() / mTabs.getTabCount();
                    //Assign new width
                    FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) mIndicator.getLayoutParams();
                    indicatorParams.width = indicatorWidth;
                    mIndicator.setLayoutParams(indicatorParams);

                }

            }
        });

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)mIndicator.getLayoutParams();

        //Multiply positionOffset with indicatorWidth to get translation
        float translationOffset =indicatorWidth ;
        params.leftMargin = (int) translationOffset;
        mIndicator.setLayoutParams(params);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            float translationOffset;
            //To move the indicator as the user scroll, we will need the scroll offset values
            //positionOffset is a value from [0..1] which represents how far the page has been scrolled
            @Override
            public void onPageScrolled(int i, float positionOffset, int positionOffsetPx) {
                if (changeTab==false)
                {
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)mIndicator.getLayoutParams();
                    //Multiply positionOffset with indicatorWidth to get translation
                    translationOffset =  (positionOffset+i) * indicatorWidth ;
                    params.leftMargin = mTabs.getWidth() / mTabs.getTabCount();

                    // Toast.makeText( AnalyzeInformation.this,""+params.leftMargin,Toast.LENGTH_SHORT ).show();
                    mIndicator.setLayoutParams(params);
                    changeTab=true;

                }else {
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)mIndicator.getLayoutParams();
                    //Multiply positionOffset with indicatorWidth to get translation
                    translationOffset =  (positionOffset+i) * indicatorWidth ;
                    params.leftMargin = (int) translationOffset;
                    // Toast.makeText( AnalyzeInformation.this,""+params.leftMargin,Toast.LENGTH_SHORT ).show();
                    mIndicator.setLayoutParams(params);
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
        startActivity(new Intent( getBaseContext(),UserActivity.class ));
    }
}
