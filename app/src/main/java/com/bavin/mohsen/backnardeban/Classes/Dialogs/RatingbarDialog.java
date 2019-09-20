package com.bavin.mohsen.backnardeban.Classes.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bavin.mohsen.backnardeban.R;

import es.dmoral.toasty.Toasty;

public class RatingbarDialog extends Dialog implements View.OnClickListener {
    public Activity activity;
    Button ratingButton;
    RatingBar ratingBar;

    public RatingbarDialog(Activity activity) {
        super( activity );
        this.activity=activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.activity_rating_bar );
        ratingBar=findViewById( R.id.rating_bar );
        ratingButton=findViewById( R.id.button_ok_rate );
        ratingButton.setOnClickListener( this );

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button_ok_rate){
            if(ratingBar.getRating() !=0){
                Animation anim = new ScaleAnimation(
                        .97f, 1f,
                        .97f, 1f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 1f);
                anim.setFillAfter(true);
                v.startAnimation(anim);
                dismiss();

            }
            else  Toasty.warning( activity, "لطفا امتیاز را وارد کنید", Toast.LENGTH_SHORT, true ).show();

        }
        }

    }

