package com.bavin.mohsen.backnardeban.Classes.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.bavin.mohsen.backnardeban.R;

public class EndStudyTimeWarningDialog extends Dialog implements View.OnClickListener {
    public Activity activity;
    public EndStudyTimeWarningDialog(Activity activity) {
        super( activity );
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        setContentView( R.layout.end_study_time_warning_dialog );


    }

    @Override
    public void onClick(View v) {

    }
}
