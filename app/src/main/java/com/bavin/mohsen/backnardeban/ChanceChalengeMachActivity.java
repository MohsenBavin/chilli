package com.bavin.mohsen.backnardeban;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bavin.mohsen.backnardeban.Classes.Dialogs.ProgressDialog;
import com.bavin.mohsen.backnardeban.Classes.Dialogs.WarningExiteMachDialog;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.APIRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.ApiIntarfaceRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.GetStudyQuestions;
import com.bavin.mohsen.backnardeban.Classes.SoundService;
import com.bavin.mohsen.backnardeban.fragments.ShowLinearChanceFragment;
import com.bavin.mohsen.backnardeban.fragments.ShowMultipleChanceFragment;
import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChanceChalengeMachActivity extends AppCompatActivity implements ShowLinearChanceFragment.OnHeadlineSelectedListener
        , ShowMultipleChanceFragment.OnHeadlineSelectedListener{
    TextView text_countDown;

   // MediaPlayer true_sound = MediaPlayer.create(this , R.raw.computef);
   // MediaPlayer false_sound = MediaPlayer.create(this , R.raw.computere);
    MediaPlayer bleeb_sound,true_sound,false_sound;
    boolean okBleeb=true;
    ChanceChalengeMachActivity.NumberQuestionReport adapterAnswer ;

    TextView text_my_name_chance_mach,text_my_point_chance_mach,text_rival_name_chance_mach,
            text_rival_point_chance_mach,text_plusPoint;

    ImageView img_my_avatar_chance_mach,img_rival_avatar_chance_mach,btn_setting;
    RecyclerView recycler_report_chance;
    FrameLayout frame_show_chanceQu;
    List<Integer> myAnswer=new ArrayList<>(  );
    List<Integer> optionsAnswer=new ArrayList<>(  );
    List<Fragment> questionFragments=new ArrayList<>(  );
    int currentNumber=0;
    int time=30;
    LinearLayoutManager horizontalLayoutManagaer =
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    ProgressDialog progressDialog;
    private List<GetStudyQuestions> questionLessons;

    int pStatus=100;
    int my_point=0;
    Timer timerSp;
    Timer timerCount;

    SeekBar timer_bar;
    FragmentManager fm ;
    FragmentTransaction fragmentTransaction ;

    public void onArticleSelected(int position) {
        myAnswer.set( currentNumber, position );
        timerSp.cancel();
        if (position == optionsAnswer.get( currentNumber )) {
            Animation animPoint = new ScaleAnimation(
                    1f, 7f, // Start and end values for the X axis scaling
                    1f, 7f, // Start and end values for the Y axis scaling
                    Animation.RELATIVE_TO_SELF, .5f, // Pivot point of X scaling
                    Animation.RELATIVE_TO_SELF, .5f ); // Pivot point of Y scaling
            animPoint.setFillAfter( true ); // Needed to keep the result of the animation
            animPoint.setDuration( 800 );
            if (Hawk.get( "sound" ).equals( "on" )) {
                true_sound = MediaPlayer.create( ChanceChalengeMachActivity.this, R.raw.computef );
                true_sound.setVolume( 5.0f, 5.0f );
                true_sound.start();
            }

            if (currentNumber == questionLessons.size() - 1) {
                text_plusPoint.setVisibility( View.VISIBLE );
                text_plusPoint.setText( "+20" );
                my_point += 20;
                text_plusPoint.setAnimation( animPoint );
            }
            else {
                text_plusPoint.setVisibility( View.VISIBLE );
                text_plusPoint.setText( "+10" );
                my_point += 10;
                text_plusPoint.setAnimation( animPoint );
            }
            text_my_point_chance_mach.setText( ""+my_point );
        }

        currentNumber++;


        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                text_plusPoint.setText( "" );
                 adapterAnswer.notifyDataSetChanged();
              //  adapterAnswer = new ChanceChalengeMachActivity.NumberQuestionReport(myAnswer,optionsAnswer );
               // recycler_report_chance.setAdapter( adapterAnswer );
                if(currentNumber>=3) horizontalLayoutManagaer.scrollToPositionWithOffset(currentNumber-3, currentNumber);
                else horizontalLayoutManagaer.scrollToPositionWithOffset(0, 5);
                text_plusPoint.setVisibility( View.GONE );
if(currentNumber<questionLessons.size()) showQuestion(  );


            }
        },2000 );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_chance_challenge_mach );
        Hawk.init(this).build();
        Hawk.put("cancelTimersp","on");



        text_countDown=findViewById( R.id.text_countDown );


        //***************************************************************************//

        text_plusPoint=findViewById( R.id.text_plusPoint );
        text_my_name_chance_mach=findViewById( R.id.text_my_name_chance_mach );
        text_my_point_chance_mach=findViewById( R.id.text_my_point_chance_mach );
        text_rival_name_chance_mach=findViewById( R.id.text_rival_name_chance_mach );
        text_rival_point_chance_mach=findViewById( R.id.text_rival_point_chance_mach );
        img_my_avatar_chance_mach=findViewById( R.id.img_my_avatar_chance_mach );
        img_rival_avatar_chance_mach=findViewById( R.id.img_rival_avatar_chance_mach );
        frame_show_chanceQu=findViewById( R.id.frame_show_chanceQu );

        recycler_report_chance=findViewById( R.id.recycler_report_chance );
        timer_bar=findViewById( R.id.timer_bar );


        //***************************************************************************//
        fm = getFragmentManager();

        text_plusPoint.setVisibility( View.GONE );



        //**************************************************************************//

        String avatarMyId=Hawk.get("avatarId");
        Glide.with(ChanceChalengeMachActivity.this).load(avatarMyId).into(img_my_avatar_chance_mach);
        String myName=Hawk.get("username");
        text_my_name_chance_mach.setText( myName);
        text_my_point_chance_mach.setText( ""+my_point );
        timer_bar.setMax( 100 );
        timer_bar.setEnabled(false);
       // timerSp=new Timer(  );
        adapterAnswer = new ChanceChalengeMachActivity.NumberQuestionReport(myAnswer,optionsAnswer );
        recycler_report_chance.setLayoutManager(horizontalLayoutManagaer);
        recycler_report_chance.setAdapter( adapterAnswer );

        progressDialog=new ProgressDialog( ChanceChalengeMachActivity.this );
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        getQuestions();

//**************************************************************************************************


//*********************************************************************************************************
        String music=Hawk.get( "music" );
        if (music.equals( "on" )) {
            startService( new Intent( ChanceChalengeMachActivity.this, SoundService.class ) );
        }

    }

    private void getQuestions() {
        ApiIntarfaceRetro apiIntarfaceRetro= APIRetro.getAPI().create( ApiIntarfaceRetro.class );
        Call<List<GetStudyQuestions>> getStudyQuestions=apiIntarfaceRetro.getStudyQuestions( "زیست","زیست",10 );
        getStudyQuestions.enqueue( new Callback<List<GetStudyQuestions>>() {
            @Override
            public void onResponse(Call<List<GetStudyQuestions>> call, Response<List<GetStudyQuestions>> response) {
                questionLessons=response.body();
                for (int i=0;i<questionLessons.size();i++){
                    optionsAnswer.add( questionLessons.get( i ).getApiAnswerQuestions() );
                    myAnswer.add( 0 );
                }
                quantificationQestinFragments(questionLessons);
                countDownTimer();
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<List<GetStudyQuestions>> call, Throwable t) {
                Toast.makeText( ChanceChalengeMachActivity.this,"onFailure",Toast.LENGTH_SHORT ).show();

            }
        } );

    }
//********************************************

    private void quantificationQestinFragments(List<GetStudyQuestions> questionLessons) {

        for(int j=0;j<questionLessons.size();j++){
            if (questionLessons.get( j ).getApiQuestionsShow().equals( "l" )){
                questionFragments.add( ShowLinearChanceFragment.newChanceFragment(
                        questionLessons.get( j ).getApiQuestions(),
                        questionLessons.get( j ).getApiFirstOptions(),
                        questionLessons.get( j ).getApiSecondOptions(),
                        questionLessons.get( j ).getApiThirdOptions(),
                        questionLessons.get( j ).getApiFourthOptions(),
                        questionLessons.get( j ).getApiAnswerQuestions(),
                        30000 )); }
            else if (questionLessons.get( j ).getApiQuestionsShow().equals( "g" )){
                questionFragments.add( ShowMultipleChanceFragment.newChanceFragment(
                        questionLessons.get( j ).getApiQuestions(),
                        questionLessons.get( j ).getApiFirstOptions(),
                        questionLessons.get( j ).getApiSecondOptions(),
                        questionLessons.get( j ).getApiThirdOptions(),
                        questionLessons.get( j ).getApiFourthOptions(),
                        questionLessons.get( j ).getApiAnswerQuestions(),
                        30000 ));
            }
        }
    }

    private void setFrafment() {
//Toast.makeText( getBaseContext(),"setFrafment"+""+currentNumber,Toast.LENGTH_SHORT ).show();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_show_chanceQu, questionFragments.get( currentNumber ));
        fragmentTransaction.commit();
        startTimerSp();



    }

    //************** countDownTimer() *****************************************************************//
    private void countDownTimer() {
        final int[] tm = {4};


        timerCount=new Timer(  );
        timerCount.schedule( new TimerTask() {
            @Override
            public void run() {

                runOnUiThread( new Runnable() {

                    @Override
                    public void run() {
                         Animation anim = new ScaleAnimation(
                                7f, 1f, // Start and end values for the X axis scaling
                                7f, 1f, // Start and end values for the Y axis scaling
                                Animation.RELATIVE_TO_SELF, .5f, // Pivot point of X scaling
                                Animation.RELATIVE_TO_SELF, .5f); // Pivot point of Y scaling
                        anim.setFillAfter(true); // Needed to keep the result of the animation
                        anim.setDuration(900);


                        tm[0]--;
                         if (tm[0]==0){
                             text_countDown.setTextColor( getResources().getColor( R.color.colorRed ) );
                             text_countDown.setTextSize( 75 );
                            text_countDown.setText( "چالش اول" );

                        }

                        else if ( tm[0]>0) {
                            text_countDown.setText( ""+tm[0] );
                            text_countDown.setAnimation( anim );
                             if(Hawk.get( "sound" ).equals( "on" )&& okBleeb) {
                                 bleeb_sound=MediaPlayer.create(ChanceChalengeMachActivity.this , R.raw.bleep);
                                 bleeb_sound.setVolume( 5.0f,5.0f );
                                 bleeb_sound.start();
                             }
                        }
                        else {
                             text_countDown.setText( "" );
                             timerCount.cancel();
                             adapterAnswer.notifyDataSetChanged();
                             showQuestion();

                         }
                    }
                } );
            }
        },0,1000 );

    }

//**************************************************************************************************//



  //************************************************************************************************
    public void startTimerSp(){
        pStatus=100;
        timerSp=new Timer(  );

        timerSp.schedule( new TimerTask() {
            @Override
            public void run() {
                runOnUiThread( new Runnable() {
                    // @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {

                            pStatus -=1;
                            timer_bar.setProgress(pStatus);
                            if(pStatus==0) {
                                 timerSp.cancel();

                        }


                    }
                } );
            }
        },0,time*10 );


    }
//******************** checkAnswer ****************************************************************




//****************** showQuestion *****************************************************************


    public void showQuestion(){
        if (currentNumber == questionLessons.size()-1) {
            frame_show_chanceQu.setVisibility( View.INVISIBLE );
            text_countDown.setVisibility( View.VISIBLE );
            text_countDown.setTextColor( getResources().getColor( R.color.colorRed ) );
            text_countDown.setTextSize(35 );
            text_countDown.setText( "چالش آخر(امتیاز دوبل)" );
            new Handler().postDelayed( new Runnable() {
                @Override
                public void run() {
                    text_countDown.setText( "" );
                    text_countDown.setVisibility( View.GONE );
                    frame_show_chanceQu.setVisibility( View.VISIBLE );

                    setFrafment();
                }
            }, 2000 );
    } else setFrafment();
}



    //*************************************************************************************************
//********* recyclerAdapter *******************************************//
    public class NumberQuestionReport extends RecyclerView.Adapter<NumberQuestionReport.MyNumberQuestionReport>{
        List<Integer> userAnswer=new ArrayList<>(  );

        List<Integer> questionAnswer=new ArrayList<>(  );

        public NumberQuestionReport(List<Integer> userAnswer,List<Integer> questionAnswer){
            this.userAnswer=userAnswer;
            this.questionAnswer=questionAnswer;
        }

        @NonNull
        @Override
        public ChanceChalengeMachActivity.NumberQuestionReport.MyNumberQuestionReport onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from( viewGroup.getContext() )
                    .inflate( R.layout.report_chance_item,viewGroup,false );
            ChanceChalengeMachActivity.NumberQuestionReport.MyNumberQuestionReport holder;
            holder= new MyNumberQuestionReport( view );
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyNumberQuestionReport myNumberQuestionReport, int i) {

            int num=i+1;

            myNumberQuestionReport.text_number_chance_item.setText( ""+num );

            if (i==currentNumber){
                myNumberQuestionReport.text_number_chance_item
                        .setTextSize( TypedValue.COMPLEX_UNIT_DIP, 28 );
                myNumberQuestionReport.text_number_chance_item
                        .setTextColor( getResources().getColor( R.color.colorWetasphalt ) );
            }else {
                myNumberQuestionReport.text_number_chance_item
                        .setTextSize( TypedValue.COMPLEX_UNIT_DIP, 15 );
                myNumberQuestionReport.text_number_chance_item
                        .setTextColor( getResources().getColor( R.color.colorDarkText ) );
            }

            int state=userAnswer.get( i );

            switch (state){
                case 0:
                    myNumberQuestionReport.report_chance_back_item
                            .setBackgroundResource( R.drawable.circle_shape_number_question_gray  );

                    break;
                default:
                    if(userAnswer.get( i )== questionAnswer.get( i )){
                        myNumberQuestionReport.report_chance_back_item
                                .setBackgroundResource( R.drawable.circle_shape_number_question_green );
                    }
                    else {
                        myNumberQuestionReport.report_chance_back_item

                                .setBackgroundResource( R.drawable.circle_shape_number_question_red );
                    }


                    break;
            }


        }




        @Override
        public int getItemCount() {
            return questionAnswer.size();
        }

        public class MyNumberQuestionReport extends RecyclerView.ViewHolder{

            ConstraintLayout report_chance_back_item;
            TextView text_number_chance_item;
            public MyNumberQuestionReport(@NonNull View itemView) {
                super( itemView );
                report_chance_back_item=itemView.findViewById( R.id.report_chance_back_item );
                text_number_chance_item=itemView.findViewById( R.id.text_number_chance_item );

            }
        }
    }




    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText( ChanceChalengeMachActivity.this,"onresum",Toast.LENGTH_LONG ).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
      //  mBackgroundMusic.execute(  );
        okBleeb=true;

    }

    @Override
    protected void onStop() {
        stopService(new Intent(ChanceChalengeMachActivity.this, SoundService.class));
        okBleeb=false;
        super.onStop();
      //  mBackgroundMusic.cancel(true);

    }

    @Override
    protected void onDestroy() {
        timerSp.cancel();

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
       /* startActivity( new Intent( ChanceChalengeMachActivity.this,MainActivity.class ) );
        timerSp.cancel();
        finish();*/
        WarningExiteMachDialog dialog=new WarningExiteMachDialog( ChanceChalengeMachActivity.this );
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
       // dialog.setCanceledOnTouchOutside(false);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext( CalligraphyContextWrapper.wrap(newBase));
    }
}
