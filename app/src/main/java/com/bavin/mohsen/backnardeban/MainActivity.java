package com.bavin.mohsen.backnardeban;

import android.content.Intent;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bavin.mohsen.backnardeban.Classes.MainMusicService;
import com.bavin.mohsen.backnardeban.fragments.HomeFragment;
import com.bavin.mohsen.backnardeban.fragments.SettingFragment;
import com.bavin.mohsen.backnardeban.fragments.ShopFragment;
import com.bavin.mohsen.backnardeban.starterActivities.SplashActivity;
import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
ImageView imageFilm;
String userName;
HomeFragment frgHome = new HomeFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        getWindow().setWindowAnimations(0);

        // userName = PreferenceManager.getDefaultSharedPreferences(this).getString("username","not") ;
        Hawk.init(this).build();
        userName = Hawk.get("username");
       // Toast.makeText( MainActivity.this,userName,Toast.LENGTH_LONG ).show();
        if (!Hawk.contains("music")) {
            Hawk.put("music","on" );
        }
        if (!Hawk.contains("sound")) {
            Hawk.put("sound","on" );
        }

        //Intent getUsername=getIntent();
        //userName=getUsername.getStringExtra( "username" );

      setFrgHome();

        //****************************************************
        final BubbleNavigationConstraintView bubbleNavigationConstraintView =
                findViewById(R.id.bottom_navigation_view_Constraint);



//****************************************************



        //bubbleNavigationConstraintView.setTypeface( Typeface.createFromAsset(getAssets(), "rubik.ttf"));
/*
        bubbleNavigationConstraintView.setBadgeValue(0, "40");
        bubbleNavigationConstraintView.setBadgeValue(1, null); //invisible badge
        bubbleNavigationConstraintView.setBadgeValue(2, "7");
*/
        bubbleNavigationConstraintView.setNavigationChangeListener( new BubbleNavigationChangeListener() {


            @Override
            public void onNavigationChanged(View view, int position) {
                if (position==0){/*
                    HomeFragment frgHome = new HomeFragment();
                    FragmentManager fm2 = getSupportFragmentManager();
                    FragmentTransaction ft2 = fm2.beginTransaction();
                    //ft2.replace(R.id.frag_main, frgHome);
                    ft.replace(R.id.frag_main, fragmentCurrent);

                    ft2.commit();*/
                    setFrgHome();

                }
                else if (position==1){
                    SettingFragment frgSetting = new SettingFragment();
                    FragmentManager fm2 = getSupportFragmentManager();
                    FragmentTransaction ft2 = fm2.beginTransaction();
                    ft2.replace(R.id.frag_main, frgSetting);
                    ft2.commit();

                }
                else if (position==2){
                    ShopFragment frgShop = new ShopFragment();
                    FragmentManager fm2 = getSupportFragmentManager();
                    FragmentTransaction ft2 = fm2.beginTransaction();
                    ft2.replace(R.id.frag_main, frgShop);
                    ft2.commit();

                }

            }
        } );






    }
    public void setFrgHome(){
        userName=PreferenceManager.getDefaultSharedPreferences(this)
                .getString("username","not") ;

        final Fragment fragmentCurrent;
        fragmentCurrent=HomeFragment.newHome(userName);


        FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frag_main, fragmentCurrent);
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }



    @Override
    protected void onStart() {
        super.onStart();

        String music= Hawk.get( "music" );
        if (music.equals( "on" )){
            MainActivity.this.startService( new Intent( MainActivity.this, MainMusicService.class ) );
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String music= Hawk.get( "music" );
        if (music.equals( "on" )) {
            MainActivity.this.stopService(new Intent( MainActivity.this, MainMusicService.class));
        }
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
        finish();
    }
}
