package com.bavin.mohsen.backnardeban.Classes.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.bavin.mohsen.backnardeban.MainActivity;
import com.bavin.mohsen.backnardeban.R;
import com.orhanobut.hawk.Hawk;

public class WarningExiteMachDialog extends Dialog implements
        View.OnClickListener{
    public Activity activity;
    public Button yes, no;
    public WarningExiteMachDialog(Activity activity) {
        super( activity );
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        setContentView( R.layout.dialog_warning_exite_mach);
        Hawk.init(activity).build();

        yes = (Button) findViewById(R.id.yes_exite_mach);
        no = (Button) findViewById(R.id.no_resume_mach);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes_exite_mach:
                Intent intent=new Intent( activity, MainActivity.class );
                getContext().startActivity( intent );
                Hawk.put( "cancelTimersp","off" );
                activity.finish();
                break;
            case R.id.no_resume_mach:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();

    }
}
