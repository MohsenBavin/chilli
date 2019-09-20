package com.bavin.mohsen.backnardeban;

import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class VSchance extends AppCompatActivity {

    ImageView image_back_vs_one,image_back_vs_two,image_vs
            ,img_my_avatar_chanceChalenge,img_rival_avatar_chance;
    TextView text_my_name_chance,text_my_level_chance,text_my_mach_number_chance
            ,text_my_record_chance,text_rival_name_chance,text_rival_level_chance
            ,text_rival_mach_number_chance,text_rival_record_chance;
    ConstraintLayout constraint_rival,constraint_my;

    ProgressBar progress_rival_percent,progress_my_persent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_vschance );

        image_vs=findViewById( R.id.image_vs );

        img_my_avatar_chanceChalenge=findViewById( R.id.img_my_avatar_chanceChalenge );
        text_my_name_chance=findViewById( R.id.text_my_name_chance );
        text_my_level_chance=findViewById( R.id.text_my_level_chance );
        text_my_mach_number_chance=findViewById( R.id.text_my_mach_number_chance );
        text_my_record_chance=findViewById( R.id.text_my_record_chance );
        progress_my_persent=findViewById( R.id.progress_my_persent );

        img_rival_avatar_chance=findViewById( R.id.img_rival_avatar_chance );
        text_rival_name_chance=findViewById( R.id.text_rival_name_chance );
        text_rival_level_chance=findViewById( R.id.text_rival_level_chance );
        text_rival_mach_number_chance=findViewById( R.id.text_rival_mach_number_chance );
        text_rival_record_chance=findViewById( R.id.text_rival_record_chance );
        progress_rival_percent=findViewById( R.id.progress_rival_percent );

        constraint_rival=findViewById( R.id.constraint_rival );
        constraint_my=findViewById( R.id.constraint_my );

        constraint_rival.setVisibility( View.GONE );
        constraint_my.setVisibility( View.GONE );





        Animation anim = new ScaleAnimation(
                7f, 1f, // Start and end values for the X axis scaling
                7f, 1f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, .5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, .5f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(500);
        image_vs.setVisibility( View.VISIBLE );
        image_vs.startAnimation(anim);
        new Handler().postDelayed( () -> runOnUiThread( () -> {
            Animation animationMoveRight= AnimationUtils.loadAnimation(VSchance.this , R.anim.move_to_right);
            constraint_my.setVisibility( View.VISIBLE );

            constraint_my.startAnimation( animationMoveRight );

            Animation animationMoveLft= AnimationUtils.loadAnimation(VSchance.this , R.anim.move_to_left);
            constraint_rival.setVisibility( View.VISIBLE );

            constraint_rival.startAnimation( animationMoveLft );

        } ), 500 ) ;






    }
}
