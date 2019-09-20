package com.bavin.mohsen.backnardeban;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class RatingBarActivity extends AppCompatActivity {
    RatingBar ratingBar;
    Button button_ok_rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_rating_bar );
        ratingBar=findViewById( R.id.rating_bar );
        button_ok_rate=findViewById( R.id.button_ok_rate );

        button_ok_rate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ratingBar.getRating() !=0){
                    Animation anim = new ScaleAnimation(
                            .97f, 1f,
                            .97f, 1f,
                            Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 1f);
                    anim.setFillAfter(true);
                    v.startAnimation(anim);
                }
                else  Toasty.warning( RatingBarActivity.this, "لطفا امتیاز را وارد کنید", Toast.LENGTH_SHORT, true ).show();

            }
        } );
    }

    @Override
    public void onBackPressed() {
        startActivity( new Intent( RatingBarActivity.this,UserActivity.class ) );
        finish();
    }
}
