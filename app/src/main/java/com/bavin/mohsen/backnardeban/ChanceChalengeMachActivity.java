package com.bavin.mohsen.backnardeban;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bavin.mohsen.backnardeban.Classes.Adapters.JustifiedTextView;
import com.bavin.mohsen.backnardeban.Classes.BackgroundMusic;
import com.bavin.mohsen.backnardeban.Classes.Dialogs.GameSettingDialog;
import com.bavin.mohsen.backnardeban.Classes.Dialogs.WarningExiteMachDialog;
import com.bavin.mohsen.backnardeban.Classes.SoundService;
import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
public class ChanceChalengeMachActivity extends AppCompatActivity {
    TextView text_countDown;
    WebView text_question_chance;

   // MediaPlayer true_sound = MediaPlayer.create(this , R.raw.computef);
   // MediaPlayer false_sound = MediaPlayer.create(this , R.raw.computere);
    MediaPlayer bleeb_sound,true_sound,false_sound;
    boolean okBleeb=true;

    TextView text_quOne_chance_l,text_quTwo_chance_l,text_quthree_chance_l,text_qufour_chance_l,
                    text_quOne_chance,text_quTwo_chance,text_quthree_chance,text_qufour_chance;

    TextView text_my_name_chance_mach,text_my_point_chance_mach,text_rival_name_chance_mach,
            text_rival_point_chance_mach,text_plusPoint;
    ConstraintLayout constraint_quOne_chance,constraint_quthree_chance,constraint_qutfour_chance
            ,constraint_quTwo_chance;
    ConstraintLayout constraint_quOne_chance_l,constraint_quthree_chance_l,constraint_qutfour_chance_l
            ,constraint_quTwo_chance_l;

    ImageView img_my_avatar_chance_mach,img_rival_avatar_chance_mach,btn_setting;
    RecyclerView recycler_report_chance;
    List<Integer> questionNumber=new ArrayList<>(  );
    List<Integer> questionState=new ArrayList<>(  );
    int pStatus=100;
    int my_point=0;
     int currentNumber=0;
    Timer timerSp;
    Timer timerCount;

    SeekBar timer_bar;
    boolean seekTime=false;
   // LinearLayout linearLayout_g,linearLayout_l;

    String questionsShowState="l";
    List<String> questionsShow=new ArrayList<>(  );
    List<String> questions=new ArrayList<>(  );
    List<String> firstOptions=new ArrayList<>(  );
    List<String> secondOptions=new ArrayList<>(  );
    List<String> thirdOptions=new ArrayList<>(  );
    List<String> fourthOptions=new ArrayList<>(  );
    List<Integer> answerQuestions=new ArrayList<>(  );

    private static boolean allowAnswer=false,startIsOk=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_chance_chalenge_mach );
        Hawk.init(this).build();
        Hawk.put("cancelTimersp","on");

        //linearLayout_g=findViewById( R.id.linearLayout_g );
       // linearLayout_l=findViewById( R.id.linearLayout_l );


        text_countDown=findViewById( R.id.text_countDown );
        text_question_chance=findViewById( R.id.text_question_chance );
        //***************************************************************************//
        text_quOne_chance=findViewById( R.id.text_quOne_chance );
        text_quTwo_chance=findViewById( R.id.text_quTwo_chance );
        text_quthree_chance=findViewById( R.id.text_quthree_chance );
        text_qufour_chance=findViewById( R.id.text_qufour_chance );

        text_quOne_chance_l=findViewById( R.id.text_quOne_chance_l );
        text_quTwo_chance_l=findViewById( R.id.text_quTwo_chance_l );
        text_quthree_chance_l=findViewById( R.id.text_quthree_chance_l );
        text_qufour_chance_l=findViewById( R.id.text_qufour_chance_l );

        //***************************************************************************//

        text_plusPoint=findViewById( R.id.text_plusPoint );
        text_my_name_chance_mach=findViewById( R.id.text_my_name_chance_mach );
        text_my_point_chance_mach=findViewById( R.id.text_my_point_chance_mach );
        text_rival_name_chance_mach=findViewById( R.id.text_rival_name_chance_mach );
        text_rival_point_chance_mach=findViewById( R.id.text_rival_point_chance_mach );
        img_my_avatar_chance_mach=findViewById( R.id.img_my_avatar_chance_mach );
        img_rival_avatar_chance_mach=findViewById( R.id.img_rival_avatar_chance_mach );
        btn_setting=findViewById( R.id.btn_setting );

        recycler_report_chance=findViewById( R.id.recycler_report_chance );
        timer_bar=findViewById( R.id.timer_bar );


        //***************************************************************************//
        constraint_quOne_chance=findViewById( R.id.constraint_quOne_chance );
        constraint_quTwo_chance=findViewById( R.id.constraint_quTwo_chance );
        constraint_quthree_chance=findViewById( R.id.constraint_quthree_chance );
        constraint_qutfour_chance=findViewById( R.id.constraint_qutfour_chance );

        constraint_quOne_chance_l=findViewById( R.id.constraint_quOne_chance_l );
        constraint_quTwo_chance_l=findViewById( R.id.constraint_quTwo_chance_l );
        constraint_quthree_chance_l=findViewById( R.id.constraint_quthree_chance_l );
        constraint_qutfour_chance_l=findViewById( R.id.constraint_qutfour_chance_l );
        //***************************************************************************//

        text_question_chance.setVisibility( View.GONE );
        constraint_quOne_chance.setVisibility( View.GONE );
        constraint_quTwo_chance.setVisibility( View.GONE );
        constraint_quthree_chance.setVisibility( View.GONE );
        constraint_qutfour_chance.setVisibility( View.GONE );
        text_plusPoint.setVisibility( View.GONE );

        constraint_quOne_chance_l.setVisibility( View.GONE );
        constraint_quTwo_chance_l.setVisibility( View.GONE );
        constraint_quthree_chance_l.setVisibility( View.GONE );
        constraint_qutfour_chance_l.setVisibility( View.GONE );

        //**************************************************************************//

        String avatarMyId=Hawk.get("avatarId");
        Glide.with(ChanceChalengeMachActivity.this).load(avatarMyId).into(img_my_avatar_chance_mach);
        String myName=Hawk.get("username");
        text_my_name_chance_mach.setText( myName);
        text_my_point_chance_mach.setText( ""+my_point );
        timer_bar.setMax( 100 );
        timer_bar.setEnabled(false);
       // timerSp=new Timer(  );
        startTimerSp();

        addParameters();
        for (int i=0;i<questions.size();i++){
            questionNumber.add( i+1 );
        }

        if (startIsOk==false){
            countDownTimer();
        }
//**************************************************************************************************
        timerSp=new Timer(  );

        timerSp.schedule( new TimerTask() {
            @Override
            public void run() {
                if (Hawk.get( "cancelTimersp" ).equals( "off" )) {
                    timerSp.cancel();
                    Hawk.put("cancelTimersp","on");
                }
                runOnUiThread( new Runnable() {
                    // @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {

                        if(seekTime){
                            pStatus -=1;
                            timer_bar.setProgress(pStatus);
                            if(Hawk.get( "sound" ).equals( "on" )&& okBleeb&&
                                    (pStatus==20 ||pStatus==17 ||pStatus==13 ||pStatus==10 ||pStatus==27 ||pStatus==3)) {
                                bleeb_sound=MediaPlayer.create(ChanceChalengeMachActivity.this , R.raw.bleep);
                                bleeb_sound.setVolume( 5.0f,5.0f );
                                bleeb_sound.start();

                            }
                            if(pStatus==0) {
                                // timerSp.cancel();
                                seekTime=false;
                                checkAnswer(0);
                                allowAnswer=false;

                            }
                        }


                    }
                } );
            }
        },0,300 );

//*********************************************************************************************************
        String music=Hawk.get( "music" );
        if (music.equals( "on" )) {
            startService( new Intent( ChanceChalengeMachActivity.this, SoundService.class ) );
        }
        btn_setting.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettingDialog();
            }
        } );
    }
//******************  showSettingDialog  ***********************************************************
    private void showSettingDialog() {
        GameSettingDialog settingDialog= new GameSettingDialog( ChanceChalengeMachActivity.this );

        settingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //settingDialog.setCanceledOnTouchOutside(false);
        settingDialog.show();

    }


    //************** countDownTimer() *****************************************************************//
    private void countDownTimer() {
        final int[] tm = {7};


        timerCount=new Timer(  );
        timerCount.schedule( new TimerTask() {
            @Override
            public void run() {

                runOnUiThread( new Runnable() {
                    @TargetApi(Build.VERSION_CODES.O)
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
                             text_countDown.setTextColor( getResources().getColor( R.color.colorPointAnim ) );
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
                             startMatch(currentNumber);

                         }
                    }
                } );
            }
        },0,1000 );

    }

//**************************************************************************************************//

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startMatch(final int currentNumberM) {
        text_question_chance.setVisibility( View.GONE );
        constraint_quOne_chance.setVisibility( View.GONE );
        constraint_quTwo_chance.setVisibility( View.GONE );
        constraint_quthree_chance.setVisibility( View.GONE );
        constraint_qutfour_chance.setVisibility( View.GONE );

        constraint_quOne_chance_l.setVisibility( View.GONE );
        constraint_quTwo_chance_l.setVisibility( View.GONE );
        constraint_quthree_chance_l.setVisibility( View.GONE );
        constraint_qutfour_chance_l.setVisibility( View.GONE );
        questionsShowState=questionsShow.get( currentNumberM  );

        if (currentNumberM == questions.size()-1) {
            text_countDown.setVisibility( View.VISIBLE );
            text_countDown.setTextColor( getResources().getColor( R.color.colorPointAnim ) );
            text_countDown.setTextSize(35 );
            text_countDown.setText( "چالش آخر(امتیاز دوبل)" );
            new Handler().postDelayed( new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void run() {
                    text_countDown.setText( "" );
                    text_countDown.setVisibility( View.GONE );

                    showQuestion(questions.get( currentNumberM ), firstOptions.get( currentNumberM ), secondOptions.get( currentNumberM ),
                            thirdOptions.get( currentNumberM ), fourthOptions.get( currentNumberM ) );
                    allowAnswer = true;
                    pStatus = 100;
                  //  startTimerSp();
                    seekTime=true;

                }
            }, 2000 );


            ChanceChalengeMachActivity.NumberQuestionReport adapterAnswer = new ChanceChalengeMachActivity.NumberQuestionReport( questionNumber, questionState, currentNumberM + 1 );
            recycler_report_chance.setAdapter( adapterAnswer );
            recycler_report_chance.setLayoutManager( new GridLayoutManager( this, 8 ) );

        } else {

            ChanceChalengeMachActivity.NumberQuestionReport adapterAnswer = new ChanceChalengeMachActivity.NumberQuestionReport( questionNumber, questionState, currentNumberM + 1 );
            recycler_report_chance.setAdapter( adapterAnswer );
            recycler_report_chance.setLayoutManager( new GridLayoutManager( this, 8 ) );

            showQuestion( questions.get( currentNumberM ), firstOptions.get( currentNumberM ), secondOptions.get( currentNumberM ),
                    thirdOptions.get( currentNumberM ), fourthOptions.get( currentNumberM ) );
            allowAnswer = true;
            pStatus = 100;
            //startTimerSp();
            seekTime=true;
        }
    }


  //************************************************************************************************
    public void startTimerSp(){


    }
//******************** checkAnswer ****************************************************************
    public void checkAnswer(int userAnswer){
        //timerSp.cancel();
        seekTime=false;
        if (userAnswer==answerQuestions.get( currentNumber )) {
            questionState.add( currentNumber,1 );
            Animation animPoint = new ScaleAnimation(
                    1f, 7f, // Start and end values for the X axis scaling
                    1f, 7f, // Start and end values for the Y axis scaling
                    Animation.RELATIVE_TO_SELF, .5f, // Pivot point of X scaling
                    Animation.RELATIVE_TO_SELF, .5f); // Pivot point of Y scaling
            animPoint.setFillAfter(true); // Needed to keep the result of the animation
            animPoint.setDuration(800);
            if(Hawk.get( "sound" ).equals( "on" )) {
                true_sound=MediaPlayer.create(ChanceChalengeMachActivity.this , R.raw.computef);
                true_sound.setVolume( 5.0f,5.0f );
                true_sound.start();
            }

            if (currentNumber==questions.size()-1){
                text_plusPoint.setVisibility( View.VISIBLE );
                text_plusPoint.setText( "+20" );
                my_point+=20;
                text_plusPoint.setAnimation( animPoint );
            }else {
                text_plusPoint.setVisibility( View.VISIBLE );
                text_plusPoint.setText( "+10" );
                my_point+=10;
                text_plusPoint.setAnimation( animPoint );
            }

            new Handler().postDelayed( new Runnable() {
                @Override
                public void run() {
                    text_plusPoint.setText( "" );
                    text_plusPoint.setVisibility( View.GONE );
                    //Toast.makeText( ChanceChalengeMachActivity.this,"gone",Toast.LENGTH_SHORT ).show();
                }
            },900 );
            new Handler().postDelayed( new Runnable() {
                @Override
                public void run() {
                    text_my_point_chance_mach.setText( ""+my_point );
                    //Toast.makeText( ChanceChalengeMachActivity.this,"gone",Toast.LENGTH_SHORT ).show();
                }
            },500 );
if (questionsShowState.equals( "g" )){
    switch (userAnswer){
        case 1:
            constraint_quOne_chance.setBackgroundResource( R.drawable.question_true_shape );
            constraint_quTwo_chance.setVisibility( View.GONE );
            constraint_quthree_chance.setVisibility( View.GONE );
            constraint_qutfour_chance.setVisibility( View.GONE );
            break;
        case 2:
            constraint_quOne_chance.setVisibility( View.GONE );
            constraint_quTwo_chance.setBackgroundResource( R.drawable.question_true_shape );
            constraint_quthree_chance.setVisibility( View.GONE );
            constraint_qutfour_chance.setVisibility( View.GONE );
            break;
        case 3:
            constraint_quOne_chance.setVisibility( View.GONE );
            constraint_quTwo_chance.setVisibility( View.GONE );
            constraint_quthree_chance.setBackgroundResource( R.drawable.question_true_shape );
            constraint_qutfour_chance.setVisibility( View.GONE );
            break;
        case 4:
            constraint_quOne_chance.setVisibility( View.GONE );
            constraint_quTwo_chance.setVisibility( View.GONE );
            constraint_quthree_chance.setVisibility( View.GONE );
            constraint_qutfour_chance.setBackgroundResource( R.drawable.question_true_shape );
            break;
    }
}
else if (questionsShowState.equals( "l" )){


    switch (userAnswer){
        case 1:
            constraint_quOne_chance_l.setBackgroundResource( R.drawable.question_true_shape );
            constraint_quTwo_chance_l.setVisibility( View.GONE );
            constraint_quthree_chance_l.setVisibility( View.GONE );
            constraint_qutfour_chance_l.setVisibility( View.GONE );
            break;
        case 2:
            constraint_quOne_chance_l.setVisibility( View.GONE );
            constraint_quTwo_chance_l.setBackgroundResource( R.drawable.question_true_shape );
            constraint_quthree_chance_l.setVisibility( View.GONE );
            constraint_qutfour_chance_l.setVisibility( View.GONE );
            break;
        case 3:
            constraint_quOne_chance_l.setVisibility( View.GONE );
            constraint_quTwo_chance_l.setVisibility( View.GONE );
            constraint_quthree_chance_l.setBackgroundResource( R.drawable.question_true_shape );
            constraint_qutfour_chance_l.setVisibility( View.GONE );
            break;
        case 4:
            constraint_quOne_chance_l.setVisibility( View.GONE );
            constraint_quTwo_chance_l.setVisibility( View.GONE );
            constraint_quthree_chance_l.setVisibility( View.GONE );
            constraint_qutfour_chance_l.setBackgroundResource( R.drawable.question_true_shape );
            break;
    }

}
        }


        else {
            if(Hawk.get( "sound" ).equals( "on" )&& okBleeb ) {
                false_sound=MediaPlayer.create(ChanceChalengeMachActivity.this , R.raw.computer);
                false_sound.setVolume( 5.0f,5.0f );
                false_sound.start();
            }

            if (questionsShowState.equals( "g" )){
                switch (userAnswer){
                    case 0:
                        constraint_quOne_chance.setVisibility( View.GONE );
                        constraint_quTwo_chance.setVisibility( View.GONE );
                        constraint_quthree_chance.setVisibility( View.GONE );
                        constraint_qutfour_chance.setVisibility( View.GONE );
                        questionState.add( currentNumber,0 );

                        break;
                    case 1:
                        constraint_quOne_chance.setBackgroundResource( R.drawable.question_false_shape );
                        constraint_quTwo_chance.setVisibility( View.GONE );
                        constraint_quthree_chance.setVisibility( View.GONE );
                        constraint_qutfour_chance.setVisibility( View.GONE );
                        questionState.add( currentNumber,-1 );

                        break;
                    case 2:
                        constraint_quOne_chance.setVisibility( View.GONE );
                        constraint_quTwo_chance.setBackgroundResource( R.drawable.question_false_shape );
                        constraint_quthree_chance.setVisibility( View.GONE );
                        constraint_qutfour_chance.setVisibility( View.GONE );
                        questionState.add( currentNumber,-1 );


                        break;
                    case 3:
                        constraint_quOne_chance.setVisibility( View.GONE );
                        constraint_quTwo_chance.setVisibility( View.GONE );
                        constraint_quthree_chance.setBackgroundResource( R.drawable.question_false_shape );
                        constraint_qutfour_chance.setVisibility( View.GONE );
                        questionState.add( currentNumber,-1 );


                        break;
                    case 4:
                        constraint_quOne_chance.setVisibility( View.GONE );
                        constraint_quTwo_chance.setVisibility( View.GONE );
                        constraint_quthree_chance.setVisibility( View.GONE );
                        constraint_qutfour_chance.setBackgroundResource( R.drawable.question_false_shape );
                        questionState.add( currentNumber,-1 );

                        break;
                }


                switch (answerQuestions.get( currentNumber )){


                    case 1:
                        constraint_quOne_chance.setVisibility( View.VISIBLE );
                        constraint_quOne_chance.setBackgroundResource( R.drawable.question_true_shape );
                        break;
                    case 2:
                        constraint_quTwo_chance.setVisibility( View.VISIBLE );
                        constraint_quTwo_chance.setBackgroundResource( R.drawable.question_true_shape );
                        break;
                    case 3:
                        constraint_quthree_chance.setVisibility( View.VISIBLE );
                        constraint_quthree_chance.setBackgroundResource( R.drawable.question_true_shape );
                        break;
                    case 4:
                        constraint_qutfour_chance.setVisibility( View.VISIBLE );
                        constraint_qutfour_chance.setBackgroundResource( R.drawable.question_true_shape );
                        break;
                }


            }

            if (questionsShowState.equals( "l" )){
                switch (userAnswer){
                    case 0:
                        constraint_quOne_chance_l.setVisibility( View.GONE );
                        constraint_quTwo_chance_l.setVisibility( View.GONE );
                        constraint_quthree_chance_l.setVisibility( View.GONE );
                        constraint_qutfour_chance_l.setVisibility( View.GONE );
                        questionState.add( currentNumber,0 );

                        break;
                    case 1:
                        constraint_quOne_chance_l.setBackgroundResource( R.drawable.question_false_shape );
                        constraint_quTwo_chance_l.setVisibility( View.GONE );
                        constraint_quthree_chance_l.setVisibility( View.GONE );
                        constraint_qutfour_chance_l.setVisibility( View.GONE );
                        questionState.add( currentNumber,-1 );

                        break;
                    case 2:
                        constraint_quOne_chance_l.setVisibility( View.GONE );
                        constraint_quTwo_chance_l.setBackgroundResource( R.drawable.question_false_shape );
                        constraint_quthree_chance_l.setVisibility( View.GONE );
                        constraint_qutfour_chance_l.setVisibility( View.GONE );
                        questionState.add( currentNumber,-1 );


                        break;
                    case 3:
                        constraint_quOne_chance_l.setVisibility( View.GONE );
                        constraint_quTwo_chance_l.setVisibility( View.GONE );
                        constraint_quthree_chance_l.setBackgroundResource( R.drawable.question_false_shape );
                        constraint_qutfour_chance_l.setVisibility( View.GONE );
                        questionState.add( currentNumber,-1 );


                        break;
                    case 4:
                        constraint_quOne_chance_l.setVisibility( View.GONE );
                        constraint_quTwo_chance_l.setVisibility( View.GONE );
                        constraint_quthree_chance_l.setVisibility( View.GONE );
                        constraint_qutfour_chance_l.setBackgroundResource( R.drawable.question_false_shape );
                        questionState.add( currentNumber,-1 );

                        break;
                }


                switch (answerQuestions.get( currentNumber )){
                    case 1:
                        constraint_quOne_chance_l.setVisibility( View.VISIBLE );
                        constraint_quOne_chance_l.setBackgroundResource( R.drawable.question_true_shape );
                        break;
                    case 2:
                        constraint_quTwo_chance_l.setVisibility( View.VISIBLE );
                        constraint_quTwo_chance_l.setBackgroundResource( R.drawable.question_true_shape );
                        break;
                    case 3:
                        constraint_quthree_chance_l.setVisibility( View.VISIBLE );
                        constraint_quthree_chance_l.setBackgroundResource( R.drawable.question_true_shape );
                        break;
                    case 4:
                        constraint_qutfour_chance_l.setVisibility( View.VISIBLE );
                        constraint_qutfour_chance_l.setBackgroundResource( R.drawable.question_true_shape );
                        break;
                }
            }




        }
        currentNumber++;
if (currentNumber==questions.size()){
    ChanceChalengeMachActivity.NumberQuestionReport adapterAnswer = new ChanceChalengeMachActivity.NumberQuestionReport( questionNumber, questionState, currentNumber );
    recycler_report_chance.setAdapter( adapterAnswer );
    recycler_report_chance.setLayoutManager( new GridLayoutManager( this, 8 ) );
    new Handler().postDelayed( new Runnable() {
        @Override
        public void run() {
            startActivity( new Intent( ChanceChalengeMachActivity.this,MainActivity.class ) );
            timerSp.cancel();
            finish();
        }
    },2000 );

}else {
    new Handler().postDelayed( new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            startMatch( currentNumber );

        }
    },2000 );

}

    }
//****************** showQuestion *****************************************************************


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showQuestion(String question, String first, String second, String third, String fourth){

        //*****************
        first=".     "+first;
        second=".     "+second;
        third=".     "+third;
        fourth=".     "+fourth;
        question="  "+question;
        //*****************
        if (questionsShowState.equals( "g" )){
            text_countDown.setVisibility( View.GONE );
            text_question_chance.setVisibility( View.VISIBLE );
            constraint_quOne_chance.setVisibility( View.VISIBLE );
            constraint_quTwo_chance.setVisibility( View.VISIBLE );
            constraint_quthree_chance.setVisibility( View.VISIBLE );
            constraint_qutfour_chance.setVisibility( View.VISIBLE );

            constraint_quOne_chance.setBackgroundResource( R.drawable.question_one_shape );
            constraint_quTwo_chance.setBackgroundResource( R.drawable.question_two_shape );
            constraint_quthree_chance.setBackgroundResource( R.drawable.question_three_shape );
            constraint_qutfour_chance.setBackgroundResource( R.drawable.question_four_shape );

           // text_question_chance.setText( question );

            String html_code = ("<html><body style=\"text-align:justify;color:white\"dir=\"rtl\"> "+question+"</body></html>");
            text_question_chance.loadData(html_code, "text/html; charset=utf-8", "utf-8");
            text_question_chance.setBackgroundColor( Color.parseColor("#00919191") );

            //text_question_chance.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

            text_quOne_chance.setText( first );
           // text_quOne_chance.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

            text_quTwo_chance.setText( second );
            //text_quTwo_chance.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

            text_quthree_chance.setText( third );
            //text_quthree_chance.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

            text_qufour_chance.setText( fourth );
          //  text_qufour_chance.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

        }

        else if (questionsShowState.equals( "l" )){
            //linearLayout_g.setVisibility( View.GONE );
            //linearLayout_l.setVisibility( View.VISIBLE );

            text_countDown.setVisibility( View.GONE );
            text_question_chance.setVisibility( View.VISIBLE );
            constraint_quOne_chance_l.setVisibility( View.VISIBLE );
            constraint_quTwo_chance_l.setVisibility( View.VISIBLE );
            constraint_quthree_chance_l.setVisibility( View.VISIBLE );
            constraint_qutfour_chance_l.setVisibility( View.VISIBLE );

            constraint_quOne_chance_l.setBackgroundResource( R.drawable.question_one_shape );
            constraint_quTwo_chance_l.setBackgroundResource( R.drawable.question_two_shape );
            constraint_quthree_chance_l.setBackgroundResource( R.drawable.question_three_shape );
            constraint_qutfour_chance_l.setBackgroundResource( R.drawable.question_four_shape );

            String html_code = ("<html><body style=\"text-align:justify;color:white\"dir=\"rtl\"> "+question+"</body></html>");
            text_question_chance.loadData(html_code, "text/html; charset=utf-8", "utf-8");
            //text_question_chance.setText( question );
            text_quOne_chance_l.setTextSize( TypedValue.COMPLEX_UNIT_SP,20);

            //text_qufour_chance.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

            text_quOne_chance_l.setText( first );
            text_quOne_chance_l.setTextSize( TypedValue.COMPLEX_UNIT_SP,15);

            // text_quOne_chance_l.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

            text_quTwo_chance_l.setText( second );
            //text_quTwo_chance_l.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

            text_quthree_chance_l.setText( third );
           // text_quthree_chance_l.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

            text_qufour_chance_l.setText( fourth );
           // text_qufour_chance_l.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

        }

    }
//*************************************************************************************************
public void constraintOnClick(View v){
        if(allowAnswer==true){
            if((v.getId()==R.id.constraint_quOne_chance) ||(v.getId()==R.id.constraint_quOne_chance_l ) ){
                //Toast.makeText( ChanceChalengeMachActivity.this,"click 1",Toast.LENGTH_SHORT ).show();
                checkAnswer(1);
                allowAnswer=false;
            }
            if((v.getId()==R.id.constraint_quTwo_chance)||(v.getId()==R.id.constraint_quTwo_chance_l)){
                checkAnswer(2);
                allowAnswer=false;


            }
            if((v.getId()==R.id.constraint_quthree_chance)||(v.getId()==R.id.constraint_quthree_chance_l)){
                checkAnswer(3);
                allowAnswer=false;


            }
            if((v.getId()==R.id.constraint_qutfour_chance)||(v.getId()==R.id.constraint_qutfour_chance_l)){
                checkAnswer(4);
                allowAnswer=false;


            }
        }


    }
//********* recyclerAdapter *******************************************//
    public class NumberQuestionReport extends RecyclerView.Adapter<NumberQuestionReport.MyNumberQuestionReport>{
        List<Integer> questionNumber=new ArrayList<>(  );

        List<Integer> questionState=new ArrayList<>(  );
        int currentNumber;

        public NumberQuestionReport(List<Integer> questionNumber,List<Integer> questionState,int currentNumber){
            this.questionNumber=questionNumber;
            this.questionState=questionState;
            this.currentNumber=currentNumber;
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
            myNumberQuestionReport.text_number_chance_item.setText( questionNumber.get( i ).toString() );
            int state=questionState.get( i );
            switch (state){
                case 0:

                    myNumberQuestionReport.report_chance_back_item
                            .setBackgroundResource( R.drawable.circle_shape_number_question_white) ;
                    break;
                case 1:
                    myNumberQuestionReport.report_chance_back_item
                            .setBackgroundResource( R.drawable.circle_shape_number_question_green ) ;
                    break;
                case -1:
                    myNumberQuestionReport.report_chance_back_item
                            .setBackgroundResource( R.drawable.circle_shape_number_question_red );
                    break;
                case 3:
                    if (questionNumber.get( i )==currentNumber){
                        myNumberQuestionReport.report_chance_back_item
                                .setBackgroundResource( R.drawable.circle_shape_number_question_current );
                    }
                    else {
                        myNumberQuestionReport.report_chance_back_item
                                .setBackgroundResource( R.drawable.circle_shape_number_question_gray  );
                    }
                    break;
            }

        }




        @Override
        public int getItemCount() {
            return questionNumber.size();
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

    public void addParameters(){
        questions.add( "بزرگترین ترافیک کنونی مربوط به خط دفاعی تیم است. جایی که باعث شده تا شایعات زیادی در خصوص نفرات لیست مازاد تیم شکل گیرد. شایعاتی شاید بانتقال ستاره های بزرگ به تیم مورد علاقه خود هستند و در این میان شاید کیفیت فنی و مهم تر از آن نیاز سرمربی در اولویت چندم برای برخی قرار می گیرد. در رقابت جذب ستاره، گاهی مدیر یا سرمربی خلاف میل باطنی شاید بازیکنی را تنها برای دلخوشی و البته کاهش اعتراضات جذب کند. اما تنها چند هفته دیگر این تب و تاب شدید فروکش می کند و رقابت اصلی بین تیم های مدعی آغاز خواهد شد.شده است. موضوع مربوط به کنار گذاشتن کاپیتانهای تیم از فصل گذشته استارت خورد. جایی که گفته شد با پیشنهاد باشگاه به برانکو، قرار بوده ایی که با باشگاه داشته\u2001اند,داشته\u2002اند,داشته\u2003اند,داشته\u200cاند" );
        questions.add( "حضرت مریم (س) در قرآن، بزرگترین ترافیک کنونی مربوط به خط دفاعی تیم عملی کند. برانکو در مصاحبه امروز خود با تسنیم نیز مدعی شده که تصمیمی برای جدایی کاپیتاناپذیرفت و اعلام کرد اگر باشگاه خ باشگاه داشتهاند، در صورت ادامه فعالیت این مربی کروات در لیست خروجی قرار گیرند.اشته اس، در صورت ادامه فعالیت این مربی کروات در لیست خروجی قراه برخی چالشهایی که با باشگاه داشتهاند، در صورت ادامه فعالیت این مربی کروات در لیست خروجی قرار گیرند.اشته" );
        questions.add( "باتوجه به زوایاي گوناگون کدام یک از اعتقادات زیر صحیح است؟" );
        questions.add( ". شرط برخورداري از رحمت الهی دریک جامعه ي اسلامی انجام وظیفه ي امر به معروف ونهی از منکر در کنار چیست؟" );
        questions.add( " با توجه به علل تأثیرگذار در تکوین پدیده « نقش مستقیم » اسباب و علل و« مشارکت »میان آن ها، به ترتیب در روابط .................. و .................. تحقق می پذیرد." );
        questions.add( " « گشایش درهاي رحمت الهی به سوي جامعه اي که در مسیر خوبی ها گام برمی دارد »سنت .................. نام دارد که پیام آیه ي شریفه ي .................. حاکی از آن است." );
        questions.add( " « فاصله گرفتن گام به گام گمراهان از انسانیت و نزدیک تر شدن به هلاکت ابدي » سنّت .................. نام دارد که پیام آیه ي شریفه ي .................. حاکی از آن است." );
        questions.add( "با استناد به آیه ي شریفه ي .................. می توان گفت که رحمت واسعه ي الهی شامل .................. می شود و این سنت عاملی بر .................. است." );

        firstOptions.add( "برخلاف ظاهر، درون و باطن انسان با خدا باشد - عمل به احکام دین" );
        firstOptions.add( "« یا اخت ھارون ما کان ابوک امرأ سوءٍ و ما کانت امّک بغیّاً »" );
        firstOptions.add( "تعالی روح شخصیت انسان علت استحکام رشته ي عفاف در اوست." );
        firstOptions.add( "« یقیمون الصلاه ویؤتون الزکاه ویطیعون لله و رسولھ »" );
        firstOptions.add( "عرضی و طولی - طولی" );
        firstOptions.add( " امداد « و لو انّ اھل القری ءامنوا و اتّقوا لفتحنا علیھم برکاتٍ من السّماء و الارض » " );
        firstOptions.add( " استدراج-« و لکن کذّبوا فاخذناھم بما کانوا یکسبون ...»  " );
        firstOptions.add( " « من جاء بالحسنة فلھ عشر أمثالھا »-نیکوکاران و بدکاران - آشکار کردن سرشت خود " );

        secondOptions.add( "برخلاف ظاهر، درون و باطن انسان با خدا باشد - عمل به احکام دین" );
        secondOptions.add( "« یا اخت ھارون ما کان ابوک امرأ سوءٍ و ما کانت امّک بغیّاً »" );
        secondOptions.add( "تعالی روح شخصیت انسان علت استحکام رشته ي عفاف در اوست." );
        secondOptions.add( "« یقیمون الصلاه ویؤتون الزکاه ویطیعون لله و رسولھ »" );
        secondOptions.add( "عرضی و طولی - طولی" );
        secondOptions.add( " امداد « و لو انّ اھل القری ءامنوا و اتّقوا لفتحنا علیھم برکاتٍ من السّماء و الارض » " );
        secondOptions.add( " استدراج-« و لکن کذّبوا فاخذناھم بما کانوا یکسبون ...»  " );
        secondOptions.add( " « من جاء بالحسنة فلھ عشر أمثالھا »-نیکوکاران و بدکاران - آشکار کردن سرشت خود " );

        thirdOptions.add( "برخلاف ظاهر، درون و باطن انسان با خدا باشد - عمل به احکام دین" );
        thirdOptions.add( "« یا اخت ھارون ما کان ابوک امرأ سوءٍ و ما کانت امّک بغیّاً »" );
        thirdOptions.add( "تعالی روح شخصیت انسان علت استحکام رشته ي عفاف در اوست." );
        thirdOptions.add( "« یقیمون الصلاه ویؤتون الزکاه ویطیعون لله و رسولھ »" );
        thirdOptions.add( "عرضی و طولی - طولی" );
        thirdOptions.add( " امداد « و لو انّ اھل القری ءامنوا و اتّقوا لفتحنا علیھم برکاتٍ من السّماء و الارض » " );
        thirdOptions.add( " استدراج-« و لکن کذّبوا فاخذناھم بما کانوا یکسبون ...»  " );
        thirdOptions.add( " « من جاء بالحسنة فلھ عشر أمثالھا »-نیکوکاران و بدکاران - آشکار کردن سرشت خود " );

        fourthOptions.add( "برخلاف ظاهر، درون و باطن انسان با خدا باشد - عمل به احکام دین" );
        fourthOptions.add( "« یا اخت ھارون ما کان ابوک امرأ سوءٍ و ما کانت امّک بغیّاً »" );
        fourthOptions.add( "تعالی روح شخصیت انسان علت استحکام رشته ي عفاف در اوست." );
        fourthOptions.add( "« یقیمون الصلاه ویؤتون الزکاه ویطیعون لله و رسولھ »" );
        fourthOptions.add( "عرضی و طولی - طولی" );
        fourthOptions.add( " امداد « و لو انّ اھل القری ءامنوا و اتّقوا لفتحنا علیھم برکاتٍ من السّماء و الارض » " );
        fourthOptions.add( " استدراج-« و لکن کذّبوا فاخذناھم بما کانوا یکسبون ...»  " );
        fourthOptions.add( " « من جاء بالحسنة فلھ عشر أمثالھا »-نیکوکاران و بدکاران - آشکار کردن سرشت خود " );

        answerQuestions.add( 1 );
        answerQuestions.add( 3 );
        answerQuestions.add( 2 );
        answerQuestions.add( 1 );
        answerQuestions.add( 4 );
        answerQuestions.add( 3 );
        answerQuestions.add( 4 );
        answerQuestions.add( 2 );


        questionsShow.add("g");
        questionsShow.add("l");
        questionsShow.add("l");
        questionsShow.add("g");
        questionsShow.add("g");
        questionsShow.add("l");
        questionsShow.add("l");
        questionsShow.add("g");

        questionState.add( 3);
        questionState.add( 3);
        questionState.add( 3);
        questionState.add( 3);
        questionState.add( 3);
        questionState.add( 3);
        questionState.add( 3);
        questionState.add( 3);

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
        seekTime=false;

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
}
