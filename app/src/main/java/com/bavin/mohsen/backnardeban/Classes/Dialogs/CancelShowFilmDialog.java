package com.bavin.mohsen.backnardeban.Classes.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.bavin.mohsen.backnardeban.MainActivity;
import com.bavin.mohsen.backnardeban.R;

public class CancelShowFilmDialog extends Dialog implements
        android.view.View.OnClickListener{
    public Activity activity;
    public Dialog d;
    public Button yes, no;
    public CancelShowFilmDialog(Activity activity) {
        super( activity );
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        setContentView( R.layout.dialog_cancel_showfilm);
        yes = (Button) findViewById(R.id.yes_cancel_show);
        no = (Button) findViewById(R.id.no_cancel_show);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes_cancel_show:
                Intent intent=new Intent( activity, MainActivity.class );
                getContext().startActivity( intent );
                activity.finish();
                break;
            case R.id.no_cancel_show:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();

    }
}
