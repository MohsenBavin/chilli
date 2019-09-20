package com.bavin.mohsen.backnardeban;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class WaitChanceMachActivity extends AppCompatActivity {
Timer timer;
int timerCounter;
TextView text_timer_wait;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_wait_chance_mach );
        text_timer_wait=findViewById( R.id.text_timer_wait );


        timer=new Timer(  );
        timer.schedule( new TimerTask() {
            @Override
            public void run() {

               runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        timerCounter +=1000;
                        text_timer_wait.setText( getTimetgn( timerCounter ) );
                        if(timerCounter<=0) {
                            timer.cancel();
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
    public void onBackPressed() {
        startActivity( new Intent( WaitChanceMachActivity.this,MainActivity.class ) );
        finish();
    }
}
