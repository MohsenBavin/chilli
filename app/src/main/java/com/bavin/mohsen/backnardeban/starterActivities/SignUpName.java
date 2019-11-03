package com.bavin.mohsen.backnardeban.starterActivities;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.APIRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.ApiIntarfaceRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.GetRegisterLoginDataRetro;
import com.bavin.mohsen.backnardeban.MainActivity;
import com.bavin.mohsen.backnardeban.R;
import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;
import com.orhanobut.hawk.Hawk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpName extends AppCompatActivity {
    private ConstraintLayout constraintLayoutSignUpName;
    private EditText edtEntername;
    private TextView txtErrorEntername;
    private Button buttonSignUp;
    private boolean enterNameIsOk=false;
    private String pass;
    String name;
protected  static ApiIntarfaceRetro apiInterfaceRetro;

    ImageView pluseAvatar,avatarImage,imageAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up_name );
        getWindow().setWindowAnimations(0);
        Hawk.init(this).build();



        constraintLayoutSignUpName=findViewById( R.id.constraint_SignUpName );
        edtEntername=findViewById( R.id.edt_enterUserName );
        txtErrorEntername=findViewById( R.id.txt_errorReEnterPass );
        buttonSignUp=findViewById( R.id.btn_SignUp );
        pluseAvatar=findViewById( R.id.image_setAvatar );
        avatarImage=findViewById( R.id.image_avatar );


        avatarImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( SignUpName.this,AvatarsActivity.class  );
                intent.putExtra( "activity","SignUpName" );
                startActivity(  intent );

            }
        } );

        Intent getUserDataIntent=getIntent();


            String avatarId=Hawk.get("avatarId");
          //  avatarImage.setImageResource( avatarId );
        Glide.with(SignUpName.this).load(avatarId).into(avatarImage);




            pluseAvatar.setOnClickListener( new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent( SignUpName.this,AvatarsActivity.class  );
                 intent.putExtra( "activity","SignUpName" );
                 startActivity(  intent );

             }
         } );
        constraintLayoutSignUpName.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService( Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(constraintLayoutSignUpName.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        } );


        //******
        edtEntername.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                 name= edtEntername.getText().toString().trim();
                 Hawk.put( "name2",name );
                if (name.length()<4){
                    txtErrorEntername.setText( "نام کاربری نباید کمتر از 4 کاراکتر باشد" );
                    enterNameIsOk=false;
                }
                else{txtErrorEntername.setText( "" );
                    enterNameIsOk=true; }


                if (enterNameIsOk==true  ){
                    buttonSignUp.setBackground( getResources().getDrawable(R.drawable.button_green_shape ) );
                }
                else {buttonSignUp.setBackground( getResources().getDrawable(R.drawable.edit_text_shape) );}


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );

        //********

        //*******************************************
        buttonSignUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enterNameIsOk==true  ){

                    name= edtEntername.getText().toString().trim();
                    Hawk.put("username", name);
                    String phone=Hawk.get("phone");
                    String level=Hawk.get("level");
                    String st=Hawk.get( "levelSelect" );
                    String field=Hawk.get( "field" );
                    String state=Hawk.get( "state" );
                    String zone=Hawk.get( "zone" );
                    String avatar=Hawk.get("avatarId");
                    String token = FirebaseInstanceId.getInstance().getToken();

                    ApiIntarfaceRetro apiIntarface= APIRetro.getAPI().create( ApiIntarfaceRetro.class );
                    Call<GetRegisterLoginDataRetro> regCall=apiIntarface.loginRegisterCall
                            ( phone,name,level,field,avatar,zone,state,token );
                    regCall.enqueue( new Callback<GetRegisterLoginDataRetro>() {
                        @Override
                        public void onResponse(Call<GetRegisterLoginDataRetro> call, Response<GetRegisterLoginDataRetro> response) {
                            String answer=response.body().getApiAnswer();
                            Toast.makeText( SignUpName.this,answer,Toast.LENGTH_SHORT ).show();

                            if (answer.equals( "successfully" )){
                                long timer=1;
                                Hawk.put("time", timer);
                                Hawk.put("diamond", 100);
                                Hawk.put("point", 200);
                                Intent intent = new Intent( SignUpName.this, MainActivity.class );
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity( intent );
                                finish();
                             if (answer.equals( "registered" )){
                                 txtErrorEntername.setText( "این نام کاربری قبلا ثبت شده،لطفا یک نام دیگر انتخاب کنید" );

                             }
                            }else if (answer.equals( "error" )){

                            }
                        }

                        @Override
                        public void onFailure(Call<GetRegisterLoginDataRetro> call, Throwable t) {

                        }
                    } );
                }
            }
        } );



    }

    //*************************************************

    @Override
    protected void onStart() {
        super.onStart();
        if (Hawk.contains( "name2" )){
            String name2=Hawk.get( "name2" );
            edtEntername.setText( name2 );
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SignUpName.this, SignUpFieldStudy.class);
        intent.putExtra( "statement","yes" );
        startActivity(intent);
        finish();
    }
}
