package com.bavin.mohsen.backnardeban.Classes.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bavin.mohsen.backnardeban.R;
import com.bavin.mohsen.backnardeban.SelectLessonStudyActivity;
import com.bavin.mohsen.backnardeban.StudyChallengeActivity;
import com.orhanobut.hawk.Hawk;

import java.util.Locale;

public class SettingStudyChallengeDialog extends Dialog implements
        android.view.View.OnClickListener {
    public Activity activity;
    private String bookTitle,topic;

    TextView text_all_timeStudy,text_topic_study_dialog,text_book_name,text_number_questions,text_time_studyQuestion;
    ImageView btn_plus_questions,btn_minus_questions,btn_time_study_plus,btn_time_study_minus;
    Button btn_cancel_study,btn_start_study;
    int quNumber=12,quTime=60,totalTime;
    MediaPlayer increase,accept_selected,reject;
    private boolean clickSound=false;

    public SettingStudyChallengeDialog( Activity activity,String bookTitle,String topic) {
        super( activity );
        this.activity = activity;
        this.bookTitle = bookTitle;
        this.topic = topic;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.dialog_setting_study_challenge);
        text_all_timeStudy=findViewById( R.id.text_all_timeStudy );
        text_topic_study_dialog=findViewById( R.id.text_topic_study_dialog );
        text_book_name=findViewById( R.id.text_book_name );
        text_number_questions=findViewById( R.id.text_number_questions );
        text_time_studyQuestion=findViewById( R.id.text_time_studyQuestion );

        btn_plus_questions=findViewById( R.id.btn_plus_questions );
        btn_minus_questions=findViewById( R.id.btn_minus_questions );
        btn_time_study_plus=findViewById( R.id.btn_time_study_plus );
        btn_time_study_minus=findViewById( R.id.btn_time_study_minus );

        btn_cancel_study=findViewById( R.id.btn_cancel_study );
        btn_start_study=findViewById( R.id.btn_start_study );
        quNumber=12;
        quTime=60;
        text_book_name.setText( bookTitle );
        text_topic_study_dialog.setText( topic );
        text_all_timeStudy.setText( getTime() );
        text_number_questions.setText( ""+quNumber );
        text_time_studyQuestion.setText( ""+quTime );

        increase= MediaPlayer.create(activity , R.raw.increase_decrease_question_1);
        increase.setVolume( 5.0f,5.0f );
        super.onStart();
        String sound= Hawk.get( "sound" );
        if (sound.equals( "on" )) {
            clickSound=true;
        }

        btn_plus_questions.setOnClickListener(this);
        btn_minus_questions.setOnClickListener(this);
        btn_time_study_plus.setOnClickListener(this);
        btn_time_study_minus.setOnClickListener(this);

        btn_cancel_study.setOnClickListener(this);
        btn_start_study.setOnClickListener(this);

    }

    public String getTime(){
        totalTime=quTime*quNumber;
        int secound=totalTime;
        int hour=secound/3600;
        int mint=secound/60;
        secound %=60;
        return String.format( Locale.ENGLISH,"%02d",hour)+" : "+String.format( Locale.ENGLISH,"%02d",mint)+" : "+String.format(Locale.ENGLISH,"%02d",secound);
    }

    @Override
    public void onClick(View v) {
        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                switch (v.getId()) {

                    case R.id.btn_plus_questions:

                        if(quNumber<18){

                            if(clickSound)increase.start();

                            quNumber +=3;
                            text_all_timeStudy.setText( getTime() );
                            text_number_questions.setText( ""+quNumber );

                        }

                        break;
                    case R.id.btn_minus_questions:


                        if(quNumber>9){

                            if(clickSound)  increase.start();
                            quNumber -=3;
                            text_all_timeStudy.setText( getTime() );
                            text_number_questions.setText( ""+quNumber );
                        }


                        break;

                    case R.id.btn_time_study_plus:

                        if(quTime<120){

                            if(clickSound)  increase.start();
                            quTime +=15;
                            text_all_timeStudy.setText( getTime() );
                            text_time_studyQuestion.setText( ""+quTime );
                        }


                        break;
                    case R.id.btn_time_study_minus:

                        if(quTime>30){

                            if(clickSound) increase.start();
                            quTime -=15;
                            text_all_timeStudy.setText( getTime() );
                            text_time_studyQuestion.setText( ""+quTime );
                        }

                        break;

                    case R.id.btn_cancel_study:
                        reject= MediaPlayer.create(activity , R.raw.reject);
                        reject.setVolume( 5.0f,5.0f );
                        if(clickSound)reject.start();
                        dismiss();
                        break;
                    case R.id.btn_start_study:
                        accept_selected= MediaPlayer.create(activity , R.raw.accept_seslect_study);
                        accept_selected.setVolume( 5.0f,5.0f );
                        if(clickSound) accept_selected.start();
                        dismiss();
                        Intent intent=new Intent( activity, StudyChallengeActivity.class );
                        intent.putExtra( "totalTime",totalTime );
                        activity.startActivity( intent );
                        activity.finish();

                        break;
                }

            }
        } , 200 ) ;


    }
}
