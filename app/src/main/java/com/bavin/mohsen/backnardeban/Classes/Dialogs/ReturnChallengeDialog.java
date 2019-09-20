package com.bavin.mohsen.backnardeban.Classes.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.bavin.mohsen.backnardeban.R;

public class ReturnChallengeDialog extends Dialog implements View.OnClickListener {
public Activity activity;
 Button btn_return_challenge;
    public ReturnChallengeDialog( Activity activity) {
        super( activity );
        this.activity = activity;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        setContentView( R.layout.dialog_return_challenge );
        btn_return_challenge=findViewById( R.id.btn_return_challenge );
    }

    @Override
    public void onClick(View v) {

    }
}
