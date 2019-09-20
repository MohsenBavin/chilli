package com.bavin.mohsen.backnardeban.Classes.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.bavin.mohsen.backnardeban.R;

public class DefaultDialog extends Dialog implements android.view.View.OnClickListener {
    public Activity activity;
    public Dialog d;
    public Button okBtn;
    public TextView txt_default_tittle,txt_default_textuary;
    public String tittle,textuary;


    public DefaultDialog(Activity activity,String tittle,String textuary) {
        super( activity );
        this.activity = activity;
        this.tittle=tittle;
        this.textuary=textuary;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        setContentView( R.layout.dialog_default );

        okBtn=(Button)findViewById( R.id.ok_default );
        txt_default_textuary=findViewById( R.id.txt_default_textuary );
        txt_default_tittle=findViewById( R.id.txt_default_tittle );
        txt_default_textuary.setText( textuary );
        txt_default_tittle.setText( tittle );
        okBtn.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {

        dismiss();



    }
}
