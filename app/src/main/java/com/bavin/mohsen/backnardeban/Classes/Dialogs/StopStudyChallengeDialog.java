package com.bavin.mohsen.backnardeban.Classes.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.bavin.mohsen.backnardeban.R;

public class StopStudyChallengeDialog extends Dialog implements View.OnClickListener {
    public Activity activity;
    TextView text_warning_endStudy;
    boolean ok;
    public StopStudyChallengeDialog(Activity activity,boolean ok) {
        super( activity );
        this.activity = activity;
        this.ok = ok;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        setContentView( R.layout.dialog_stop_study_challenge );
        text_warning_endStudy=findViewById( R.id.text_warning_endStudy );
        if (ok) text_warning_endStudy.setText( "هنوز وقت داری" );
        else text_warning_endStudy.setVisibility( View.GONE );



    }

    @Override
    public void onClick(View v) {

    }
}
