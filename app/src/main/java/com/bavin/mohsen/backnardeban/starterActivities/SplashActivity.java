package com.bavin.mohsen.backnardeban.starterActivities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.APIRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.ApiIntarfaceRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.GetLoginDataRetro;
import com.bavin.mohsen.backnardeban.MainActivity;
import com.bavin.mohsen.backnardeban.R;
import com.orhanobut.hawk.Hawk;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private TextView txtProgress;
    private ProgressBar progressBar_chilli,progressBar_fire;
    private static int pStatus ;
    private Handler handler = new Handler();
    private Timer timerSp;

    public static ApiIntarfaceRetro apiIntarface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView( R.layout.activity_splash );



        Hawk.init(this).build();

       // getSupportActionBar().hide();
        txtProgress = (TextView) findViewById(R.id.txtProgress);
        progressBar_chilli =  findViewById(R.id.progressBar_chilli);
        progressBar_fire = findViewById(R.id.progressBar_fire);
        // Adding colors on progress bar
//       ProgressBar.getProgressDrawable().setColorFilter( Color.CYAN, PorterDuff.Mode.SRC_IN);

        checkConnection("first");

    }

//******************* notConnected method*******************************************************

    private void notConnected(){

        LayoutInflater aInflater = getLayoutInflater();
        new AlertDialog.Builder(this)
                .setTitle("خطا")
                .setCancelable(false)
                .setMessage(
                        "گوشی شما به اینترنت دسترسی ندارد.لطفا از روشن بودن دیتای گوشی و یا اتصال به وای فای اطمینان حاصل فرمایید" )
                .setIcon(R.drawable.cancel)
                .setPositiveButton("امتحان دوباره", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        checkConnection("first");
                    }
                })

                .setNegativeButton("خروج", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                })
                .create()
                .show();

    }


//******************** yesConnected method ********************************************************

    private void yesConnected(){

        pStatus=20;
        timerSp=new Timer(  );
        timerSp.schedule( new TimerTask() {
            @Override
            public void run() {

                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        pStatus +=1;
                        progressBar_chilli.setProgress(pStatus);
                        txtProgress.setText(pStatus + " %");
                        if (pStatus==50){
                            timerSp.cancel();

                            if (Hawk.contains("username")) {
                                getUserData();
                            }

                            else {
                                goRegisterUser();

                            }
                        }
                    }
                } );
            }
        },0,50 );



    }

    public void getUserData(){
        String user=Hawk.get("username");
        apiIntarface= APIRetro.getAPI().create( ApiIntarfaceRetro.class );
        Call<GetLoginDataRetro> logCall=apiIntarface.loginCall( user );
        logCall.enqueue( new Callback<GetLoginDataRetro>() {
            @Override
            public void onResponse(Call<GetLoginDataRetro> call, Response<GetLoginDataRetro> response) {
                String answer=response.body().getApiAnswer();
                if (answer.equals( "SUCCESS" )){
                    String level=response.body().getApiLevel();
                    Hawk.put( "level",level );
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
                    Toast.makeText( SplashActivity.this,level,Toast.LENGTH_SHORT ).show();

                    String field=response.body().getApiField();
                    Hawk.put( "field",field );

                    String zone=response.body().getApiZone();
                    Hawk.put( "zone",zone );

                    String state=response.body().getApiState();
                    Hawk.put( "state",state );

                    String phone=response.body().getApiPhone();
                    Hawk.put( "",phone );

                    String time=response.body().getApiTimeFilm();
                    long timer=Long.parseLong(time);
                    Hawk.put("time", timer);

                    int diamond=response.body().getApiDiamond();
                    Hawk.put( "diamond",diamond );

                    int point=response.body().getApiPoint();
                    Hawk.put( "point",point );

                    String avatarId=response.body().getApiAvatar();
                    Hawk.put( "avatarId",avatarId );

                    pStatus=50;
                    timerSp = new Timer();
                    timerSp.schedule( new TimerTask() {
                        @Override
                        public void run() {

                            runOnUiThread( new Runnable() {
                                @Override
                                public void run() {
                                    if (pStatus >= 100) {
                                        timerSp.cancel();
                                        fillfire("getUserData");

                                    }
                                    pStatus += 1;
                                    progressBar_chilli.setProgress( pStatus );
                                    txtProgress.setText( pStatus + " %" );


                                }
                            } );
                        }
                    }, 0, 50 );




                }else if (answer.equals( "FAILED" )){
                    Hawk.put( "avatarId", "http://moonishop.ir/nardeban/images/graduates.png" );
                    goRegisterUser();
                }




            }

            @Override
            public void onFailure(Call<GetLoginDataRetro> call, Throwable t) {
                checkConnection( "getUserData" );

            }
        } );
    }
    private void fillfire(String statefire){

        pStatus=0;
        timerSp=new Timer(  );
        timerSp.schedule( new TimerTask() {
            @Override
            public void run() {

                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        pStatus +=1;
                        progressBar_fire.setProgress(pStatus);

                        if (pStatus==100){
                            new Handler().postDelayed( new Runnable() {
                                @Override
                                public void run() {
                                    timerSp.cancel();


                                    if(statefire.equals( "goRegisterUser" )){
                                        Hawk.put( "avatarId", "http://moonishop.ir/nardeban/images/chilli.png");
                                        Intent intent2 = new Intent( SplashActivity.this, SignUpTelephone.class );
                                        intent2.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                                        startActivity( intent2 );
                                        finish();
                                    }
                                    else if(statefire.equals( "getUserData" )){
                                        Intent intent = new Intent( SplashActivity.this, MainActivity.class );
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity( intent );
                                        finish();
                                    }

                                }
                            } , 800 ) ;


                        }
                    }
                } );
            }
        },0,3 );



    }

    public void goRegisterUser(){
        pStatus=50;
        timerSp = new Timer();
        timerSp.schedule( new TimerTask() {
            @Override
            public void run() {

                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        if (pStatus >= 100) {

                            timerSp.cancel();
                            fillfire("goRegisterUser");

                        }

                        pStatus += 1;
                        progressBar_chilli.setProgress( pStatus );
                        txtProgress.setText( pStatus + " %" );

                    }
                } );
            }
        }, 0, 50 );

    }


//******************* checkConnectioncheckConnection method ****************************************

    private void checkConnection(String state){
        boolean connected = false;
        pStatus=0;
        timerSp=new Timer(  );
        timerSp.schedule( new TimerTask() {
            @Override
            public void run() {

                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        pStatus +=1;
                        progressBar_chilli.setProgress(pStatus);
                        txtProgress.setText(pStatus + " %");
                        if (pStatus==20){
                            timerSp.cancel();

                            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService( Context.CONNECTIVITY_SERVICE);
                            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                                if (state.equals( "first" )) yesConnected();
                                else if (state.equals( "getUserData" )) getUserData();

                            }
                            else{

                                notConnected();
                            }

                        }

                    }
                } );
            }
        },0,50 );

    }
}
