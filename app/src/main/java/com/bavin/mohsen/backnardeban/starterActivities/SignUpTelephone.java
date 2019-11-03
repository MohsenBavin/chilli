package com.bavin.mohsen.backnardeban.starterActivities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.bavin.mohsen.backnardeban.R;
import com.orhanobut.hawk.Hawk;

public class SignUpTelephone extends AppCompatActivity {
    ConstraintLayout constraintLayoutPhone;
    private EditText getphone;
    private Button nextPhone;
    private boolean phoneIsOk;
    private String phone;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up_telephone );
        getWindow().setWindowAnimations(0);
        Hawk.init(this).build();

        constraintLayoutPhone=findViewById( R.id.constraint_SignUpTelephone );
        getphone=findViewById( R.id.edt_get_telephone );
        nextPhone=findViewById( R.id.buttonNextforphone );

       // getphone.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.phone_call), null,null, null);

        constraintLayoutPhone.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService( Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(constraintLayoutPhone.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);

            }
        } );


        getphone.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                phone= getphone.getText().toString().trim();
              //if((phone.startsWith( "09" )&&phone.length()==11)||(phone.startsWith( "+989" )&&phone.length()==13)){
                if((phone.startsWith( "09" )&&phone.length()==11)){
                    phoneIsOk=true;
                }
                else {
                    phoneIsOk=false;
                }

                if ( phoneIsOk==true){
                    nextPhone.setBackground( getResources().getDrawable(R.drawable.button_green_click_state ) );
                }
                else {nextPhone.setBackground( getResources().getDrawable(R.drawable.button_inactive_shape ) );}


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );


        nextPhone.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( phoneIsOk==true){
                    Hawk.put("phone", phone);
                    Intent intent = new Intent(SignUpTelephone.this, RequestPassword.class);
                    startActivity(intent);
                    finish();
                    //startActivity(new Intent(SignUpTelephone.this,SignUpFieldStudy.class  ) );
                }

            }
        } );

    }


    //*********************************************************************

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
