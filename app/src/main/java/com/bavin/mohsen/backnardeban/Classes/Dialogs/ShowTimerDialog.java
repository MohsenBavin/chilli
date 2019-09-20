package com.bavin.mohsen.backnardeban.Classes.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.bavin.mohsen.backnardeban.R;
import com.orhanobut.hawk.Hawk;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class ShowTimerDialog extends Dialog implements android.view.View.OnClickListener {
    private TextView timerText;
    private Button okTimer;
    Timer timer;
    long timerCount;
    long timewaite2,nowtime2;
    Activity activity;
    public ShowTimerDialog(Activity activity) {
        super( activity );
        this.activity=activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        setContentView( R.layout.dialog_show_timer );
        timerText=findViewById( R.id.txt_timer );
        okTimer=findViewById( R.id.btn_ok_timer );
        okTimer.setOnClickListener( this );


        timewaite2 = Hawk.get( "time" );
        nowtime2=System.currentTimeMillis();
        timerCount=timewaite2-nowtime2;

        timer=new Timer(  );
        timer.schedule( new TimerTask() {
            @Override
            public void run() {

                activity.runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        timerCount -=1000;
                        timerText.setText( getTimetgn( timerCount ) );
                        if(timerCount<=0) {
                            timer.cancel();
                            dismiss();
                        }

                    }
                } );
            }
        },0,1000 );



    }

    public String getTimetgn(long timerCount2){
        long secound=(timerCount2/1000);
        Long hour=secound/3600;
        long mint=secound/60;
        secound %=60;
        return String.format( Locale.ENGLISH,"%02d",hour)+" : "+String.format( Locale.ENGLISH,"%02d",mint)+" : "+String.format(Locale.ENGLISH,"%02d",secound);
    }


    @Override
    public void onClick(View v) {
dismiss();
    }
}
