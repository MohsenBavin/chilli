package com.bavin.mohsen.backnardeban.starterActivities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bavin.mohsen.backnardeban.R;

public class Login extends AppCompatActivity {
    private EditText getUserName,getPassword;
    private TextView errorUserName,errorPassword,forgetPass;
    private Button buttonLogin;
    private ConstraintLayout constraintLayoutLogin;
    private boolean userIsOk=false,passIsOk=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login_2 );
        getWindow().setWindowAnimations(0);


        constraintLayoutLogin=findViewById( R.id.constraint_login_2 );
        getUserName=findViewById( R.id.edt_get_username );
        getPassword=findViewById( R.id.edt_get_password );
        errorUserName=findViewById( R.id.textErrorUser );
           // errorPassword=findViewById( R.id.textErrorrass);
       // forgetPass=findViewById( R.id.textForgetPass );
        buttonLogin=findViewById( R.id.btnLogin );
//*******************************************************************
      //  getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN );
       //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//**********************************************************************************
constraintLayoutLogin.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(constraintLayoutLogin.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

    }
} );


//**********************************************************************************
        getUserName.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @SuppressLint("ResourceType")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               String user= getUserName.getText().toString().trim();
                if (user.length()<4){
                    errorUserName.setText( "نام کاربری نباید کمتر از 4 کاراکتر باشد" );
                    userIsOk=false;
                }
                else{errorUserName.setText( "" );
                userIsOk=true;
                }
                if (passIsOk==true && userIsOk==true){
                    buttonLogin.setBackground( getResources().getDrawable(R.drawable.button_green_shape ) );
                }
                else {buttonLogin.setBackground( getResources().getDrawable(R.drawable.edit_text_shape) );}


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );

        getPassword.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pass= getPassword.getText().toString().trim();
                if (pass.length()<6){
                  //  errorPassword.setText( "رمز عبور نباید کمتر از 6 کاراکتر باشد" );
                    passIsOk=false;
                }
                else{errorPassword.setText( "" );
                    passIsOk=true;
                }

                if (passIsOk==true && userIsOk==true){
                    buttonLogin.setBackground( getResources().getDrawable(R.drawable.button_green_shape ) );
                }
                else {buttonLogin.setBackground( getResources().getDrawable(R.drawable.edit_text_shape) ); }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );

//************************************************************************************
        /*
        forgetPass.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );
*/
    }

//**************************************************************************************
    @Override
    public void onBackPressed() {
        super.onBackPressed();
      finish();
    }
}
