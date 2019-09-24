package com.bavin.mohsen.backnardeban;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.bavin.mohsen.backnardeban.Classes.Dialogs.DefaultDialog;
import com.bavin.mohsen.backnardeban.Classes.Dialogs.CancelShowFilmDialog;
import com.bavin.mohsen.backnardeban.Classes.Dialogs.ProgressDialog;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.APIRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.ApiIntarfaceRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.GetAnsAddDiamond;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.GetFilmAddress;
import com.orhanobut.hawk.Hawk;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowFilmActivity extends AppCompatActivity {
    private  int pStatus=100,pStatus2=0 ;
    private Handler handler = new Handler();
    private Timer timerSp;
    ProgressBar mProgress;
    VideoView videoView;
    ImageView cancel_show,play_film;
    int timer=0,diamond;
    private boolean filmComplete=false,firstShow=true;
    String VideoURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_show_film );
        Hawk.init(this).build();
        mProgress =  findViewById(R.id.circularProgressbar );
        videoView =  findViewById(R.id.videoView );
        cancel_show =  findViewById(R.id.cancel_show );
        play_film =  findViewById(R.id.play_film );



        mProgress.setProgress(0);
        mProgress.setVisibility( View.GONE );
        play_film.setVisibility( View.GONE );
        //timerSp.cancel();

        ProgressDialog progressDialog=new ProgressDialog( ShowFilmActivity.this );
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable( false );
        progressDialog.show();
/*

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (pStatus2 <= 100) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressStart.setProgress(pStatus);
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pStatus2++;
                }
            }
        }).start();
        */

       ApiIntarfaceRetro apiIntarface= APIRetro.getAPI().create( ApiIntarfaceRetro.class );
        Call<GetFilmAddress> callFilm=apiIntarface.showFilmCall();
        callFilm.enqueue( new Callback<GetFilmAddress>() {
            @Override
            public void onResponse(Call<GetFilmAddress> call, Response<GetFilmAddress> response) {

                progressDialog.dismiss();
                 VideoURL=response.body().getApiFilmAddress();
                showVideo( VideoURL );

            }

            @Override
            public void onFailure(Call<GetFilmAddress> call, Throwable t) {
                Toast.makeText( ShowFilmActivity.this,"onFailure",Toast.LENGTH_LONG ).show();


            }
        } );

        play_film.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pStatus=0;

                showVideo( VideoURL );
            }
        } );
        cancel_show.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkExite();
            }
        } );
    }

   public void showVideo( String VideoURL) {

       try {
// شروع کار مدیاکنترل
           MediaController mediacontroller = new MediaController(
                   ShowFilmActivity.this);
           mediacontroller.setAnchorView(videoView);
// دریافت فایل ویدیوی از آدرس اینترنتی
           Uri video = Uri.parse(VideoURL);
           // videoView.setMediaController(mediacontroller);
           videoView.setVideoURI(video);

       } catch (Exception e) {
           Log.e("Error", e.getMessage());
           e.printStackTrace();
       }

       videoView.requestFocus();

       videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
           // قطع کار پروسس دیالوگ و شروع نمایش ویدیو
           public void onPrepared(MediaPlayer mp) {
               timer = videoView.getDuration();
               timer-=1000;
               play_film.setVisibility( View.GONE );
               mProgress.setVisibility( View.VISIBLE );
               //  Toast.makeText( ShowFilmActivity.this,""+timer,Toast.LENGTH_LONG ).show();
               videoView.start();

               int current=videoView.getCurrentPosition();
               mProgress.setProgress( current );
               mProgress.setMax( timer );
               timerSp=new Timer(  );
               timerSp.schedule( new TimerTask() {
                   @Override
                   public void run() {

                       runOnUiThread( new Runnable() {
                           @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                           @Override
                           public void run() {
                               int current=videoView.getCurrentPosition();

                               mProgress.setProgress( current );
                               if(current >= timer-50) {
                                   filmComplete=true;
                                   play_film.setVisibility( View.VISIBLE );
                                   timerSp.cancel();
                                   if (firstShow){
                                       firstShow=false;
                                       addDiamond();
                                   }


                               }
                           }
                       } );
                   }
               },0,1 );


           }


       });

   }

   public void checkExite(){
        if (filmComplete==false){
            CancelShowFilmDialog cdd = new CancelShowFilmDialog(ShowFilmActivity.this);
            cdd.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            cdd.show();
        }
        else {
            Intent intent=new Intent( ShowFilmActivity.this, MainActivity.class );
            startActivity( intent );
            finish();
        }
   }

public void showAddDiamondDialog(){
    DefaultDialog diamondDialog= new DefaultDialog(ShowFilmActivity.this,"تبریک","30 الماس هدیه به حساب شما اضافه شد");
    diamondDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    diamondDialog.setCanceledOnTouchOutside(true);
    diamondDialog.show();

}
   public void addDiamond(){


               String phone=Hawk.get( "phone" );
               diamond=Hawk.get( "diamond" );
               diamond +=30;
               Hawk.put( "diamond",diamond );

               long timer= System.currentTimeMillis();
               timer=timer+240000;
               Hawk.put("time", timer);
               String timerFilm=""+timer;

               ApiIntarfaceRetro apiIntarfaceRetro= APIRetro.getAPI().create( ApiIntarfaceRetro.class );
               Call<GetAnsAddDiamond> update=apiIntarfaceRetro.updateDiamond(phone,diamond,timerFilm);
               update.enqueue( new Callback<GetAnsAddDiamond>() {
                   @Override
                   public void onResponse(Call<GetAnsAddDiamond> call, Response<GetAnsAddDiamond> response) {
                       String answer=response.body().getApiAnswer();

                       Toast.makeText( ShowFilmActivity.this,answer,Toast.LENGTH_LONG ).show();
                       if (answer.equals( "successfully" )){
                           showAddDiamondDialog();
                       }
                   }

                   @Override
                   public void onFailure(Call<GetAnsAddDiamond> call, Throwable t) {
                       Toast.makeText( ShowFilmActivity.this,"onFailure",Toast.LENGTH_LONG ).show();

                   }
               } );
   }
    @Override
    public void onBackPressed() {


    checkExite();

    }
}
