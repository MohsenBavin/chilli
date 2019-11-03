package com.bavin.mohsen.backnardeban.starterActivities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.APIRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.ApiIntarfaceRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.GetLoginPhoneRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.GetPhoneStateRetro;
import com.bavin.mohsen.backnardeban.MainActivity;
import com.bavin.mohsen.backnardeban.R;
import com.orhanobut.hawk.Hawk;

import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestPassword extends AppCompatActivity {
    Timer timer;
    long timerCounter=30000;
    boolean retry=false;
    private Button buttonCounter,buttonBack,buttonNext;
    private TextView textConfirmCode,textConfirmMobile;
    private EditText getConfirmCode;
    private String ConfirmCodeGet;
    private boolean ConfirmCodeIsOk=false;
    private static int xRand;
   // private ImageView imageViewLogo;
    boolean isOpened = false;

    private ConstraintLayout constraintRequestPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_request_password );
        getWindow().setWindowAnimations(0);
        Hawk.init(this).build();

        buttonCounter=findViewById( R.id.button_timer );
        buttonNext=findViewById( R.id.bottom_next_mobile );
        buttonBack=findViewById( R.id.button_change_number );
        textConfirmCode=findViewById( R.id.text_confirm_code );
        textConfirmMobile=findViewById( R.id.text_confirm_mobile );
        getConfirmCode=findViewById( R.id.edt_get_telephone );
        constraintRequestPassword=findViewById( R.id.constraint_RequestPassword );
        //imageViewLogo=findViewById( R.id.imageViewLogo );

//************************ Set view listeners ******************************************************
//*** constraintRequestPassword ***************
        constraintRequestPassword.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService( Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(constraintRequestPassword.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);

            }
        } );
//*** buttonCounter methods***************

         setTimerCounter();

        buttonCounter.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(retry==true){
                    buttonCounter.setBackground( getResources().getDrawable(R.drawable.button_inactive_shape ) );
                    if ( ConfirmCodeIsOk==true){
                        ConfirmCodeIsOk=false;
                        buttonNext.setBackground( getResources().getDrawable(R.drawable.button_inactive_shape ) );}
                    timerCounter=30000;
                    Random ran = new Random();
                    xRand = ran.nextInt(11111) + 55555;
                    textConfirmCode.setText( ""+xRand );
                    setTimerCounter();


                }
            }
        } );

//*** buttonBack *************************
buttonBack.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity( new Intent( RequestPassword.this,SignUpTelephone.class ) );
        finish();

    }
} );

//*** buttonNext *************************
        buttonNext.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( ConfirmCodeIsOk==true ){
                    ConfirmCodeGet= getConfirmCode.getText().toString().trim();
                    String xRandom=""+xRand;
                    if(xRandom.equals( ConfirmCodeGet )){
                        checkPhoneState();

                        String state=Hawk.get( "phoneState" );


                    }
                    else {
                        Toasty.error(RequestPassword.this, "لطفا کد تایید را درست وارد فرمایید" , Toast.LENGTH_SHORT, true).show();
                    }

                    //startActivity(new Intent(SignUpTelephone.this,SignUpFieldStudy.class  ) );
                }

            }
        } );

//*** getConfirmCode ***************

        getConfirmCode.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                ConfirmCodeGet= getConfirmCode.getText().toString().trim();
                if(ConfirmCodeGet.length()==5 ){
                    ConfirmCodeIsOk=true;
                }
                else {
                    ConfirmCodeIsOk=false;
                }

                if ( ConfirmCodeIsOk==true){
                    buttonNext.setBackground( getResources().getDrawable(R.drawable.button_green_shape ) );
                }
                else {buttonNext.setBackground( getResources().getDrawable(R.drawable.button_inactive_shape ) );}


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );

//*** textConfirmMobile ***************
String confrmPhone=Hawk.get("phone");
textConfirmMobile.setText( confrmPhone );

//*** textConfirmCode ***************
        Random ran = new Random();
        xRand = ran.nextInt(11111) + 55555;
        textConfirmCode.setText( ""+xRand );

//**************************************************************************************************

        //setListnerToRootView();
    }
    public void setTimerCounter(){
        timer=new Timer(  );
        timer.schedule( new TimerTask() {
            @Override
            public void run() {

                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        timerCounter -=1000;
                        String setText=("ارسال مجدد کد "+"("+ getTimeSet( timerCounter )+")");
                        buttonCounter.setText(setText);
                        if(timerCounter==0) {
                            buttonCounter.setText("ارسال مجدد کد");
                            buttonCounter.setBackground( getResources().getDrawable(R.drawable.button_green_shape ) );
                            retry=true;

                            timer.cancel();
                        }

                    }
                } );
            }
        },0,1000 );
    }

    public String getTimeSet(long timerCount2){
        long secound=(timerCount2/1000);

        secound %=60;
        return String.format(Locale.ENGLISH,"%02d",secound);
    }


    private void getLoginData() {
        ApiIntarfaceRetro apiIntarface= APIRetro.getAPI().create( ApiIntarfaceRetro.class );

        Call<GetLoginPhoneRetro> loginPhoneRetroCall=apiIntarface.loginPhoneCall( Hawk.get("phone") );
        loginPhoneRetroCall.enqueue( new Callback<GetLoginPhoneRetro>() {
            @Override
            public void onResponse(Call<GetLoginPhoneRetro> call, Response<GetLoginPhoneRetro> response) {
                String answer=response.body().getApiAnswer();
                Toast.makeText( RequestPassword.this,answer,Toast.LENGTH_SHORT ).show();
                if (answer.equals("SUCCESS")) {
                    String level = response.body().getApiLevel();
                    Hawk.put( "level", level );
                    if (level.equals("اول")){
                        Hawk.put("levelSelect","اول دبیرستان");

                    }else if (level.equals("دوم")){
                        Hawk.put("levelSelect","دوم دبیرستان");

                    }
                    else if (level.equals("سوم")){
                        Hawk.put("levelSelect","سوم دبیرستان");

                    }else if (level.equals("چهارم")){
                        Hawk.put("levelSelect","چهارم دبیرستان");

                    }

                    String field = response.body().getApiField();
                    Hawk.put( "field", field );

                    String zone = response.body().getApiZone();
                    Hawk.put( "zone", zone );

                    String state = response.body().getApiState();
                    Hawk.put( "state", state );

                    String username = response.body().getApiUserName();
                    Hawk.put( "username", username );

                    String time = response.body().getApiTimeFilm();
                    long timer = Long.parseLong( time );
                    Hawk.put( "time", timer );

                    int diamond = response.body().getApiDiamond();
                    Hawk.put( "diamond", diamond );

                    int point = response.body().getApiPoint();
                    Hawk.put( "point", point );

                    String avatarId = response.body().getApiAvatar();
                    Hawk.put( "avatarId", avatarId );

                    Intent intent2 = new Intent( RequestPassword.this, MainActivity.class );
                    intent2.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                    startActivity( intent2 );
                    finish();
                }
                }

            @Override
            public void onFailure(Call<GetLoginPhoneRetro> call, Throwable t) {

            }
        } );
    }

/*
    public void setListnerToRootView(){
        final View activityRootView = getWindow().getDecorView().findViewById(android.R.id.content);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff > 300 ) { // 99% of the time the height diff will be due to a keyboard.
                    Toast.makeText(getApplicationContext(), "Gotcha!!! softKeyboardup", Toast.LENGTH_LONG).show();

                    if(isOpened == false){
                        //Do two things, make the view top visible and the editText smaller
                        imageViewLogo.setVisibility( View.GONE );
                        isOpened = true;

                    }
                }else if(isOpened == true){
                    Toast.makeText(getApplicationContext(), "softkeyborad Down!!!",Toast.LENGTH_LONG).show();
                    imageViewLogo.setVisibility( View.VISIBLE );
                    isOpened = false;
                }
            }
        });
    }
*/
    private void checkPhoneState() {
        String phone= Hawk.get("phone");
        @SuppressLint("HardwareIds")
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        // Log.d("Android","Android ID : " + android_id);
        ApiIntarfaceRetro intarfaceRetro= APIRetro.getAPI().create( ApiIntarfaceRetro.class );
        Call<GetPhoneStateRetro> checkPhoneCall=intarfaceRetro.checkPhoneCall( phone,android_id );
        checkPhoneCall.enqueue( new Callback<GetPhoneStateRetro>() {
            @Override
            public void onResponse(Call<GetPhoneStateRetro> call, Response<GetPhoneStateRetro> response) {
                String answer= response.body().getApiAnswer();
              Toast.makeText( RequestPassword.this,answer,Toast.LENGTH_SHORT ).show();
                switch (answer){
                    case "registerd":
                        Hawk.put("phoneState", "registerd");
                        getLoginData();
                        break;
                    case "login":
                        Hawk.put("phoneState", "login");
                        Toasty.error( RequestPassword.this,
                                "این اکانت برای یگ گوشی دیگر فعال است",Toasty.LENGTH_SHORT ).show();
                        break;
                    case "SUCCESS":
                        Hawk.put("phoneState", "SUCCESS");
                        // intent.putExtra( "phone",phone);
                        Intent intent = new Intent(RequestPassword.this, SignUpFieldStudy.class);
                        intent.putExtra( "statement","no" );
                        startActivity(intent);
                        finish();
                        break;
                    case "ERROR":
                        checkPhoneState();
                        break;

                }

            }

            @Override
            public void onFailure(Call<GetPhoneStateRetro> call, Throwable t) {
                Toast.makeText( RequestPassword.this,"onFailure",Toast.LENGTH_SHORT ).show();
                checkPhoneState();

            }
        } );

    }


    @Override
    public void onBackPressed() {
       /* super.onBackPressed();
        startActivity( new Intent( RequestPassword.this,SignUpTelephone.class ) );
        finish();*/
    }
}
