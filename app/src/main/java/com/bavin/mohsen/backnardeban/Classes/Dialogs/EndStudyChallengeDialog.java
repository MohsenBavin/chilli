package com.bavin.mohsen.backnardeban.Classes.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.bavin.mohsen.backnardeban.R;

import org.w3c.dom.Text;

public class EndStudyChallengeDialog extends Dialog implements View.OnClickListener {
    public Activity activity;
    TextView text_warning_endStudy;
    Button btn_continue_study,btn_end_study;
    String mode;
    public EndStudyChallengeDialog( Activity activity,String mode) {
        super( activity );
        this.activity = activity;
        this.mode = mode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        setContentView( R.layout.dialog_end_study_challenge );
        text_warning_endStudy=findViewById( R.id.text_warning_endStudy );
        if (mode.equals( "endTime" )){
            text_warning_endStudy.setText( "زمانت به اتمام رسید!" );
        }
        else if (mode.equals( "stop" )){
            text_warning_endStudy.setText( "نمی خوای بازی رو ادامه بدی ؟" );

        }
    }

    @Override
    public void onClick(View v) {

    }
}
