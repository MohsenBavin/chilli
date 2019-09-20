package com.bavin.mohsen.backnardeban.Classes.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.APIRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.ApiIntarfaceRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.GetAnsReadychance;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.GetLessonsList;
import com.bavin.mohsen.backnardeban.R;
import com.bavin.mohsen.backnardeban.WaitChanceMachActivity;
import com.orhanobut.hawk.Hawk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//implements android.view.View.OnClickListener
public class WarningSelectChanceDialog extends Dialog  {
TextView text_warning_select;
    Activity activity;
    String study,lesson;
    int number;
    Button ok_continue,no_cancel;
    CheckBox checkBox;
    public WarningSelectChanceDialog(Activity activity, String study,String lesson,int number) {
        super( activity );
        this.activity=activity;
        this.study=study;
        this.lesson=lesson;
        this.number=number;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        setContentView( R.layout.dialog_warning_select_chance );
        text_warning_select=findViewById( R.id.text_warning_select );
        ok_continue=findViewById( R.id.btn_ok_continue );
        no_cancel=findViewById( R.id.btn_no_cancel );

        //ok_continue.setOnClickListener( this );
        //no_cancel.setOnClickListener( this );
        checkBox=findViewById( R.id.checkBox_dontShow );

        text_warning_select.setText("شما بازی های امتیازی " +study+ " را انجام داده اید،آیا مایل شروع بازی جدید هستید؟");
    }

}
