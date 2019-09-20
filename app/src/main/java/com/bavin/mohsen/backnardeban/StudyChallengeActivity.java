package com.bavin.mohsen.backnardeban;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import com.bavin.mohsen.backnardeban.Classes.Dialogs.EndStudyChallengeDialog;
import com.bavin.mohsen.backnardeban.Classes.Dialogs.GameSettingDialog;
import com.bavin.mohsen.backnardeban.Classes.Dialogs.ReturnChallengeDialog;
import com.bavin.mohsen.backnardeban.Classes.Dialogs.StudyResultsDialog;
import com.bavin.mohsen.backnardeban.Classes.Dialogs.WarningExiteMachDialog;
import com.bavin.mohsen.backnardeban.Classes.MainMusicService;
import com.bavin.mohsen.backnardeban.Classes.SoundService;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.orhanobut.hawk.Hawk;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;

public class StudyChallengeActivity extends AppCompatActivity {
    BottomSheetBehavior bottomSheetBehavior;
    ImageView button_slide;
    private boolean slideIsOpen=false,clickSound=false;
    RecyclerView recycler_report_study_line,recycler_report_study_all;

    int correctNumber,incorrectNumber,unansweredNumber,totalNumber;

    CoordinatorLayout coordinatorLayout;
    LinearLayout linear_answer_guide;
    TextView totalTime_study;
    TextView text_question_study;
    TextView all_question_number,current_question_number;
    int currentNumber=0;

    MediaPlayer true_sound,false_sound;

    //ProgressBar progressBar_timer;
    CircleProgress progressBar_timer;

    TextView text_quOne_study_l,text_quTwo_study_l,text_quthree_study_l,text_qufour_study_l,
            text_quOne_study,text_quTwo_study,text_quthree_study,text_qufour_study;
    ConstraintLayout constraint_quOne_study,constraint_quthree_study,constraint_qutfour_study
            ,constraint_quTwo_study,constraint_show_number,constraintLayout_timer;
    ConstraintLayout constraint_quOne_study_l,constraint_quthree_study_l,constraint_qutfour_study_l
            ,constraint_quTwo_study_l;

    ImageView btn_back_study,btn_next_study;
    TextView txt_pauseTime_study,text_stop_time;
    LinearLayout linearLayout_linearOption,linearLayout_twoOption;
ConstraintLayout linearLayout_main_study;

    TextView text_start_studyChallenge;

    String challengeState="null";
    int questionNumber;

    String questionsShowState="l";
    List<String> questionsShow=new ArrayList<>(  );
    List<String> questions=new ArrayList<>(  );
    List<String> firstOptions=new ArrayList<>(  );
    List<String> secondOptions=new ArrayList<>(  );
    List<String> thirdOptions=new ArrayList<>(  );
    List<String> fourthOptions=new ArrayList<>(  );
    List<Integer> answerQuestions=new ArrayList<>(  );
    List<Integer> questionAnswerState=new ArrayList<>(  );
    List<Integer> questionSelectedState=new ArrayList<>(  );
    List<Integer> timeConsumingQuestions=new ArrayList<>(  );
    List<Integer> noAnswerQuestions=new ArrayList<>(  );
    List<Integer> noAnswerQuestionsState=new ArrayList<>(  );

    Timer timerSeek,timerShow,timerCounter;
    int pStatusSeek=0;
    int totalTime;
    int statusTime;
    boolean pauseTimer=false,endTime=false;
    String showTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_study_challenge );
        false_sound=MediaPlayer.create(StudyChallengeActivity.this , R.raw.coin3);
        false_sound.setVolume( 5.0f,5.0f );


        findOptions();
        linearLayout_main_study.setVisibility( View.GONE );
        bottomSheetSetup();
        addParameters();
        button_slide.setVisibility( View.GONE );
        constraint_show_number.setVisibility( View.GONE );

        for (int j=0; j<questions.size();j++){
            questionSelectedState.add( 0 );
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                StartChallenge();

            }

        } , 1500 ) ;


        btn_next_study.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qu=0;
                if(challengeState.equals( "inChallenge" )){
                        if(currentNumber<questions.size()-1){
                            currentNumber++;
                            startMatch(currentNumber); }


                }
                else if(challengeState.equals( "inAnswer" )){
                    if(currentNumber<questions.size()-1){
                        currentNumber++;
                        startAnswer(currentNumber);
                    }
                }


            }
        } );

        btn_back_study.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qu=0;

                if(challengeState.equals( "inChallenge" )){
                    if(currentNumber>0){
                            currentNumber--;
                            startMatch(currentNumber);
                    }
                }else if(challengeState.equals( "inAnswer" )){
                    if(currentNumber>0){
                        currentNumber--;
                        startAnswer(currentNumber);
                    }
                }
            }
        } );



        text_stop_time.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogs("stop");
            }
        } );

        txt_pauseTime_study.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogs( "pause" );
            }
        } );

        constraintLayout_timer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (challengeState.equals( "inChallenge" )){
                    if(endTime==true){
                        currentNumber=0;
                        challengeState="inAnswer";
                        totalTime_study.setVisibility( View.GONE );
                        currentNumber=0;
                        startAnswer(currentNumber);
                        totalTime_study.setVisibility( View.GONE );
                }
                }else if(challengeState.equals( "inAnswer" )){
                    StudyResultsDialog studyResultsDialog=new
                            StudyResultsDialog(StudyChallengeActivity.this,correctNumber,incorrectNumber,unansweredNumber,totalNumber  );
                    studyResultsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    studyResultsDialog.setCanceledOnTouchOutside(false);
                    studyResultsDialog.show();
                    studyResultsDialog.findViewById( R.id. btn_show_answers).setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText( StudyChallengeActivity.this,"inChallenge",Toast.LENGTH_SHORT ).show();
                            //progressBar_timer.setProgress( 100 );
                            studyResultsDialog.dismiss();

                        }
                    } );
                }


                }
        } );


        // contentMainSetup();
    }

    //*************************************************************************************//
    private void StartChallenge() {

        final int[] tm = {4};


        timerCounter=new Timer(  );
        timerCounter.schedule( new TimerTask() {
            @Override
            public void run() {

                runOnUiThread( new Runnable() {
                    //@TargetApi(Build.VERSION_CODES.O)
                   // @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        Animation anim = new ScaleAnimation(
                                7f, 1f, // Start and end values for the X axis scaling
                                7f, 1f, // Start and end values for the Y axis scaling
                                Animation.RELATIVE_TO_SELF, .5f, // Pivot point of X scaling
                                Animation.RELATIVE_TO_SELF, .5f); // Pivot point of Y scaling
                        anim.setFillAfter(true); // Needed to keep the result of the animation
                        anim.setDuration(1000);


                        tm[0]--;
                        if (tm[0]==0){
                            //text_start_studyChallenge.setTextColor( getResources().getColor( R.color.colorPointAnim ) );
                            text_start_studyChallenge.setTextSize( 75 );
                            text_start_studyChallenge.setText( "شروع چالش" );

                            String music= Hawk.get( "music" );
                            if (music.equals( "on" )) {
                                StudyChallengeActivity.this.startService(new Intent( StudyChallengeActivity.this, SoundService.class));
                            }

                        }

                        else if ( tm[0]>0) {
                            text_start_studyChallenge.setText( ""+tm[0] );
                            text_start_studyChallenge.setAnimation( anim );
                            String music= Hawk.get( "music" );
                            if (music.equals( "on" )) {

                                false_sound.start();
                            }

                        }
                        else {
                            text_start_studyChallenge.setText( "" );
                            timerCounter.cancel();
                            linearLayout_main_study.setVisibility( View.VISIBLE );
                            button_slide.setVisibility( View.VISIBLE );
                            constraint_show_number.setVisibility( View.VISIBLE );
                            startMatch(currentNumber);
                            timer( totalTime );
                            text_start_studyChallenge.setVisibility( View.GONE );
                            challengeState="inChallenge";
                            all_question_number.setText( ""+ questions.size() );
                            timerCounter.cancel();
                        }
                    }
                } );
            }
        },0,1000 );
    }

    //**************************************************************************************//

    private void showDialogs(String mode) {
        switch (mode){
            case "pause":
                pauseTimer=true;
                ReturnChallengeDialog returnChallengeDialog=new ReturnChallengeDialog( StudyChallengeActivity.this );
                returnChallengeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                returnChallengeDialog.setCanceledOnTouchOutside(false);
                returnChallengeDialog.setCancelable(false);
                returnChallengeDialog.show();
                returnChallengeDialog.findViewById( R.id.btn_return_challenge ).setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pauseTimer=false;
                        returnChallengeDialog.dismiss();
                    }
                } );
                break;

            case "stop":
                EndStudyChallengeDialog warnDialog= new EndStudyChallengeDialog
                        (StudyChallengeActivity.this,"stop");
                warnDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                warnDialog.setCanceledOnTouchOutside(false);
                warnDialog.setCancelable(false);
                warnDialog.show();
                warnDialog.findViewById( R.id.btn_continue_study  ).setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        warnDialog.dismiss();

                    }
                } );
                warnDialog.findViewById( R.id.btn_end_study  ).setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        totalNumber=questions.size();
                        for (int j=0; j<totalNumber;j++){
                          if(questionSelectedState.get( j )==0||questionSelectedState.get( j )==5||questionSelectedState.get( j )==6){
                              unansweredNumber++;
                          }
                          else{
                              if(questionSelectedState.get( j )==answerQuestions.get( j )){
                                  correctNumber++;
                              }else {
                                  incorrectNumber++;

                              }
                          }
                        }

                        warnDialog.dismiss();
                        StudyResultsDialog studyResultsDialog=new
                                StudyResultsDialog(StudyChallengeActivity.this,correctNumber,incorrectNumber,unansweredNumber,totalNumber  );
                        studyResultsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        studyResultsDialog.setCanceledOnTouchOutside(false);
                        studyResultsDialog.show();
                        studyResultsDialog.findViewById( R.id. btn_show_answers).setOnClickListener( new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText( StudyChallengeActivity.this,"inChallenge",Toast.LENGTH_SHORT ).show();
                                challengeState="inAnswer";
                                currentNumber=0;
                                text_stop_time.setVisibility( View.GONE );
                                txt_pauseTime_study.setVisibility( View.GONE );

                                progressBar_timer.setProgress( 100 );
                                totalTime_study.setText( "نتیجه آزمون" );
                                totalTime_study.setTextColor( getResources().getColor( R.color.colorRedError ) );
                                timerSeek.cancel();
                                timerShow.cancel();

                                startAnswer(currentNumber);
                                studyResultsDialog.dismiss();

                            }
                        } );

                    }
                } );
                break;
            case "endTime":
                text_stop_time.setVisibility( View.GONE );
                txt_pauseTime_study.setVisibility( View.GONE );

                EndStudyChallengeDialog warnTimeDialog= new EndStudyChallengeDialog
                        (StudyChallengeActivity.this,"endTime");
                warnTimeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                warnTimeDialog.setCanceledOnTouchOutside(false);
                warnTimeDialog.show();
                warnTimeDialog.findViewById( R.id.btn_continue_study  ).setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        totalTime_study.setText( "پایان چالش و دیدن جوابها" );
                        warnTimeDialog.dismiss();

                    }
                } );
                warnTimeDialog.findViewById( R.id.btn_end_study  ).setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText( StudyChallengeActivity.this,"inChallenge",Toast.LENGTH_SHORT ).show();

                        totalTime_study.setVisibility( View.GONE );


                        currentNumber=0;

                        startAnswer(currentNumber);
                        warnTimeDialog.dismiss();
                        StudyResultsDialog studyResultsDialog=new
                                StudyResultsDialog(StudyChallengeActivity.this,correctNumber,incorrectNumber,unansweredNumber,totalNumber  );
                        studyResultsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        studyResultsDialog.setCanceledOnTouchOutside(false);
                        studyResultsDialog.show();
                        studyResultsDialog.findViewById( R.id. btn_show_answers).setOnClickListener( new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText( StudyChallengeActivity.this,"inChallenge",Toast.LENGTH_SHORT ).show();
                                challengeState="inAnswer";
                                currentNumber=0;
                                text_stop_time.setVisibility( View.GONE );
                                txt_pauseTime_study.setVisibility( View.GONE );

                                totalTime_study.setVisibility( View.GONE );

                                timerSeek.cancel();
                                timerShow.cancel();

                                startAnswer(currentNumber);
                                studyResultsDialog.dismiss();

                            }
                        } );

                    }
                } );
                break;
        }
    }


    //****************************************************************************************************//
public void startMatch( int currentNumberSM) {
linearLayout_twoOption.setVisibility( View.GONE );
linearLayout_linearOption.setVisibility( View.GONE );

       currentNumber=currentNumberSM;
       questionsShowState=questionsShow.get( currentNumberSM  );
       current_question_number.setText( ""+(currentNumberSM+1) );
       questionNumber=questions.size();

       StudyChallengeActivity.ShowLineQuestions adapterAnswer = new
               StudyChallengeActivity.ShowLineQuestions( questionSelectedState, currentNumberSM + 1 );
       recycler_report_study_line.setAdapter( adapterAnswer );
       LinearLayoutManager horizontalLayoutManagaer =
               new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
       //Scroll item 2 to 20 pixels from the top
       if(currentNumberSM<=questions.size()-3){
           horizontalLayoutManagaer.scrollToPositionWithOffset(currentNumber-3, currentNumber+3);

       }   else {
           horizontalLayoutManagaer.scrollToPositionWithOffset(questions.size()-8, questions.size());

       }
       recycler_report_study_line.setLayoutManager(horizontalLayoutManagaer);

       StudyChallengeActivity.ShowLineQuestions adapterAnswerAll = new
               StudyChallengeActivity.ShowLineQuestions(  questionSelectedState, currentNumberSM + 1);
       recycler_report_study_all.setAdapter( adapterAnswerAll );
       recycler_report_study_all.setLayoutManager(new GridLayoutManager(getBaseContext(), 7));


    showQuestion(questions.get( currentNumberSM ), firstOptions.get( currentNumberSM ), secondOptions.get( currentNumberSM ),
            thirdOptions.get( currentNumberSM ), fourthOptions.get( currentNumberSM ) );

}
//****************************************************************************************************//
public void startAnswer( int currentNumberSA){

    currentNumber=currentNumberSA;
    questionsShowState=questionsShow.get( currentNumberSA  );
    current_question_number.setText( ""+(currentNumberSA+1) );
    questionNumber=questions.size();

    AnswerQuestionReport adapterAnswer = new AnswerQuestionReport( questionSelectedState, answerQuestions,currentNumberSA + 1 );
    recycler_report_study_line.setAdapter( adapterAnswer );
    LinearLayoutManager horizontalLayoutManagaer =
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    //Scroll item 2 to 20 pixels from the top
    if(currentNumberSA<questions.size()-3){
        horizontalLayoutManagaer.scrollToPositionWithOffset(currentNumber-3, currentNumber+3);

    }   else {
        horizontalLayoutManagaer.scrollToPositionWithOffset(questions.size()-8, questions.size());

    }
    recycler_report_study_line.setLayoutManager(horizontalLayoutManagaer);

    AnswerQuestionReport adapterAnswerAll = new
            AnswerQuestionReport(  questionSelectedState,answerQuestions,currentNumberSA + 1);
    recycler_report_study_all.setAdapter( adapterAnswerAll );
    recycler_report_study_all.setLayoutManager(new GridLayoutManager(getBaseContext(), 7));

    showAnswerQuestion(questions.get( currentNumberSA ), firstOptions.get( currentNumberSA ), secondOptions.get( currentNumberSA ),
            thirdOptions.get( currentNumberSA ), fourthOptions.get( currentNumberSA ) );


}

    private void showAnswerQuestion(String question, String first, String second, String third, String fourth){
        first=".     "+first;
        second=".     "+second;
        third=".     "+third;
        fourth=".     "+fourth;
        //*****************
        int numSelect=questionSelectedState.get( currentNumber );
        int numAnswer=answerQuestions.get( currentNumber );

        if (questionsShowState.equals( "g" )){
            linearLayout_linearOption.setVisibility( View.GONE );
            linearLayout_twoOption.setVisibility( View.VISIBLE );

            constraint_quOne_study.setBackgroundResource( R.drawable.options_questions_shape );
                constraint_quTwo_study.setBackgroundResource( R.drawable.options_questions_shape );
                constraint_quthree_study.setBackgroundResource( R.drawable.options_questions_shape );
                constraint_qutfour_study.setBackgroundResource( R.drawable.options_questions_shape );

                switch (numSelect){
                    case 1:
                        if(numSelect!=numAnswer) {
                            constraint_quOne_study.setBackgroundResource( R.drawable.question_false_shape );
                        }

                        break;
                    case 2:
                        if(numSelect!=numAnswer) {
                            constraint_quTwo_study.setBackgroundResource( R.drawable.question_false_shape );
                        }

                        break;
                    case 3:
                        if(numSelect!=numAnswer) {
                            constraint_quthree_study.setBackgroundResource( R.drawable.question_false_shape );
                        }

                        break;
                    case 4:
                        if(numSelect!=numAnswer) {
                            constraint_qutfour_study.setBackgroundResource( R.drawable.question_false_shape );
                        }

                }

                switch (numAnswer){
                    case 1:
                        constraint_quOne_study.setBackgroundResource( R.drawable.question_true_shape );


                        break;
                    case 2:
                        constraint_quTwo_study.setBackgroundResource( R.drawable.question_true_shape );


                        break;
                    case 3:
                        constraint_quthree_study.setBackgroundResource( R.drawable.question_true_shape );

                        break;
                    case 4:
                        constraint_qutfour_study.setBackgroundResource( R.drawable.question_true_shape );


                }


            text_question_study.setText( question );

            //text_question_study.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

            text_quOne_study.setText( first );
            // text_quOne_study.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

            text_quTwo_study.setText( second );
            //text_quTwo_study.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

            text_quthree_study.setText( third );
            //text_quthree_study.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

            text_qufour_study.setText( fourth );
            //  text_qufour_study.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

        }

        else if (questionsShowState.equals( "l" )){

            linearLayout_linearOption.setVisibility( View.VISIBLE );
            linearLayout_twoOption.setVisibility( View.GONE );



                constraint_quOne_study_l.setBackgroundResource( R.drawable.options_questions_shape );
                constraint_quTwo_study_l.setBackgroundResource( R.drawable.options_questions_shape );
                constraint_quthree_study_l.setBackgroundResource( R.drawable.options_questions_shape );
                constraint_qutfour_study_l.setBackgroundResource( R.drawable.options_questions_shape );
                switch (numSelect){
                    case 1:
                        if(numSelect!=numAnswer) {
                            constraint_quOne_study_l.setBackgroundResource( R.drawable.question_false_shape );
                        }

                        break;
                    case 2:
                        if(numSelect!=numAnswer) {
                            constraint_quTwo_study_l.setBackgroundResource( R.drawable.question_false_shape );
                        }

                        break;
                    case 3:
                        if(numSelect!=numAnswer) {
                            constraint_quthree_study_l.setBackgroundResource( R.drawable.question_false_shape );
                        }

                        break;
                    case 4:
                        if(numSelect!=numAnswer) {
                            constraint_qutfour_study_l.setBackgroundResource( R.drawable.question_false_shape );
                        }

                }
                switch (numAnswer){
                    case 1:
                        constraint_quOne_study_l.setBackgroundResource( R.drawable.question_true_shape );


                        break;
                    case 2:
                        constraint_quTwo_study_l.setBackgroundResource( R.drawable.question_true_shape );


                        break;
                    case 3:
                        constraint_quthree_study_l.setBackgroundResource( R.drawable.question_true_shape );

                        break;
                    case 4:
                        constraint_qutfour_study_l.setBackgroundResource( R.drawable.question_true_shape );


            }

            text_question_study.setText(  question);
            //text_question_study.setText( question );
            text_quOne_study_l.setTextSize( TypedValue.COMPLEX_UNIT_SP,20);

            //text_qufour_study.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

            text_quOne_study_l.setText( first );
            text_quOne_study_l.setTextSize( TypedValue.COMPLEX_UNIT_SP,15);

            // text_quOne_study_l.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

            text_quTwo_study_l.setText( second );
            //text_quTwo_study_l.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

            text_quthree_study_l.setText( third );
            // text_quthree_study_l.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

            text_qufour_study_l.setText( fourth );
            // text_qufour_study_l.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

        }

    }

    //****************************************************************************************************//
public void showQuestion(String question, String first, String second, String third, String fourth){
    //*****************
    first=".     "+first;
    second=".     "+second;
    third=".     "+third;
    fourth=".     "+fourth;
    //*****************
    if (questionsShowState.equals( "g" )){
        linearLayout_linearOption.setVisibility( View.GONE );
       linearLayout_twoOption.setVisibility( View.VISIBLE );

if (questionSelectedState.get( currentNumber )>0){
    constraint_quOne_study.setBackgroundResource( R.drawable.options_questions_shape );
    constraint_quTwo_study.setBackgroundResource( R.drawable.options_questions_shape );
    constraint_quthree_study.setBackgroundResource( R.drawable.options_questions_shape );
    constraint_qutfour_study.setBackgroundResource( R.drawable.options_questions_shape );


    switch (questionSelectedState.get( currentNumber )){
        case 1:
            constraint_quOne_study.setBackgroundResource( R.drawable.question_selected_shape );

            break;
        case 2:
            constraint_quTwo_study.setBackgroundResource( R.drawable.question_selected_shape );

            break;
        case 3:
            constraint_quthree_study.setBackgroundResource( R.drawable.question_selected_shape );

            break;
        case 4:
            constraint_qutfour_study.setBackgroundResource( R.drawable.question_selected_shape );

            break;
    }

}
else {
    constraint_quOne_study.setBackgroundResource( R.drawable.options_questions_shape );
    constraint_quTwo_study.setBackgroundResource( R.drawable.options_questions_shape );
    constraint_quthree_study.setBackgroundResource( R.drawable.options_questions_shape );
    constraint_qutfour_study.setBackgroundResource( R.drawable.options_questions_shape );

}


        // text_question_study.setText( question );

        text_question_study.setText( question );

        //text_question_study.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

        text_quOne_study.setText( first );
        // text_quOne_study.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

        text_quTwo_study.setText( second );
        //text_quTwo_study.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

        text_quthree_study.setText( third );
        //text_quthree_study.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

        text_qufour_study.setText( fourth );
        //  text_qufour_study.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

    }

    else if (questionsShowState.equals( "l" )){

linearLayout_linearOption.setVisibility( View.VISIBLE );
linearLayout_twoOption.setVisibility( View.GONE );

        if (questionSelectedState.get( currentNumber )>0){

            constraint_quOne_study_l.setBackgroundResource( R.drawable.options_questions_shape );
            constraint_quTwo_study_l.setBackgroundResource( R.drawable.options_questions_shape );
            constraint_quthree_study_l.setBackgroundResource( R.drawable.options_questions_shape );
            constraint_qutfour_study_l.setBackgroundResource( R.drawable.options_questions_shape );
            switch (questionSelectedState.get( currentNumber )){
                case 1:
                    constraint_quOne_study_l.setBackgroundResource( R.drawable.question_selected_shape );

                    break;
                case 2:
                    constraint_quTwo_study_l.setBackgroundResource( R.drawable.question_selected_shape );

                    break;
                case 3:
                    constraint_quthree_study_l.setBackgroundResource( R.drawable.question_selected_shape );

                    break;
                case 4:
                    constraint_qutfour_study_l.setBackgroundResource( R.drawable.question_selected_shape );

                    break;

            }

        }
        else {
            constraint_quOne_study_l.setBackgroundResource( R.drawable.options_questions_shape );
            constraint_quTwo_study_l.setBackgroundResource( R.drawable.options_questions_shape );
            constraint_quthree_study_l.setBackgroundResource( R.drawable.options_questions_shape );
            constraint_qutfour_study_l.setBackgroundResource( R.drawable.options_questions_shape );

        }

        text_question_study.setText(  question);
        //text_question_study.setText( question );
        text_quOne_study_l.setTextSize( TypedValue.COMPLEX_UNIT_SP,20);

        //text_qufour_study.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

        text_quOne_study_l.setText( first );
        text_quOne_study_l.setTextSize( TypedValue.COMPLEX_UNIT_SP,15);

        // text_quOne_study_l.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

        text_quTwo_study_l.setText( second );
        //text_quTwo_study_l.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

        text_quthree_study_l.setText( third );
        // text_quthree_study_l.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

        text_qufour_study_l.setText( fourth );
        // text_qufour_study_l.setJustificationMode( Layout.JUSTIFICATION_MODE_INTER_WORD);

    }


}
    //*************************************************************************************************
    public void constraintOnClick(View v){

        if(challengeState.equals( "inChallenge" )){
            if(Hawk.get( "sound" ).equals( "on" )) {
                true_sound=MediaPlayer.create(StudyChallengeActivity.this , R.raw.click2);
                true_sound.setVolume( 5.0f,5.0f );
                true_sound.start();
            }
            constraint_quOne_study.setBackgroundResource( R.drawable.options_questions_shape );
            constraint_quTwo_study.setBackgroundResource( R.drawable.options_questions_shape );
            constraint_quthree_study.setBackgroundResource( R.drawable.options_questions_shape );
            constraint_qutfour_study.setBackgroundResource( R.drawable.options_questions_shape );
            constraint_quOne_study_l.setBackgroundResource( R.drawable.options_questions_shape );
            constraint_quTwo_study_l.setBackgroundResource( R.drawable.options_questions_shape );
            constraint_quthree_study_l.setBackgroundResource( R.drawable.options_questions_shape );
            constraint_qutfour_study_l.setBackgroundResource( R.drawable.options_questions_shape );



            if((v.getId()==R.id.constraint_quOne_study) ||(v.getId()==R.id.constraint_quOne_study_l ) ){
                //Toast.makeText( studyChalengeMachActivity.this,"click 1",Toast.LENGTH_SHORT ).show();
                checkAnswer(1);
                questionSelectedState.set( currentNumber,1 );

                if (questionsShowState.equals( "g" )){
                    constraint_quOne_study.setBackgroundResource( R.drawable.question_selected_shape );

                }
                else if (questionsShowState.equals( "l" )){
                    constraint_quOne_study_l.setBackgroundResource( R.drawable.question_selected_shape );


                }

            }
            if((v.getId()==R.id.constraint_quTwo_study)||(v.getId()==R.id.constraint_quTwo_study_l)){
                checkAnswer(2);
                questionSelectedState.set( currentNumber,2 );

                if (questionsShowState.equals( "g" )){
                    constraint_quTwo_study.setBackgroundResource( R.drawable.question_selected_shape );

                }
                else if (questionsShowState.equals( "l" )){
                    constraint_quTwo_study_l.setBackgroundResource( R.drawable.question_selected_shape );


                }

            }
            if((v.getId()==R.id.constraint_quthree_study)||(v.getId()==R.id.constraint_quthree_study_l)){
                checkAnswer(3);
                questionSelectedState.set( currentNumber,3 );

                if (questionsShowState.equals( "g" )){
                    constraint_quthree_study.setBackgroundResource( R.drawable.question_selected_shape );

                }
                else if (questionsShowState.equals( "l" )){
                    constraint_quthree_study_l.setBackgroundResource( R.drawable.question_selected_shape );


                }

            }
            if((v.getId()==R.id.constraint_qutfour_study)||(v.getId()==R.id.constraint_qutfour_study_l)){
                checkAnswer(4);
                questionSelectedState.set( currentNumber,4 );

                if (questionsShowState.equals( "g" )){
                    constraint_qutfour_study.setBackgroundResource( R.drawable.question_selected_shape );

                }
                else if (questionsShowState.equals( "l" )){
                    constraint_qutfour_study_l.setBackgroundResource( R.drawable.question_selected_shape );


                }


            }

        }

    }

    private void checkAnswer(int i) {
    }

    //****************************************************************************************************//
    private void findOptions() {
        recycler_report_study_line = findViewById(R.id.recycler_report_study_line);
        recycler_report_study_all = findViewById(R.id.recycler_report_study_all);

        //***************************************************************************//
        btn_back_study=findViewById( R.id.btn_back_study );
        btn_next_study=findViewById( R.id.btn_next_study );
        text_stop_time=findViewById( R.id.txt_stop_time );
        txt_pauseTime_study=findViewById( R.id.txt_pauseTime_study );

        progressBar_timer=findViewById( R.id.progressBar_timer );
        constraint_show_number=findViewById( R.id.constraint_show_number );
        constraintLayout_timer=findViewById( R.id.constraintLayout_timer );

        linearLayout_main_study=findViewById( R.id.linearLayout_main_study );
        linearLayout_linearOption=findViewById( R.id.linearLayout_linearOption );
        linearLayout_twoOption=findViewById( R.id.linearLayout_twoOption );
        text_start_studyChallenge=findViewById( R.id.text_start_studyChallenge );


        text_question_study=findViewById( R.id.text_question_study );
        current_question_number=findViewById( R.id.current_question_number );



        text_quOne_study=findViewById( R.id.text_quOne_study );
        text_quTwo_study=findViewById( R.id.text_quTwo_study );
        text_quthree_study=findViewById( R.id.text_quthree_study );
        text_qufour_study=findViewById( R.id.text_qufour_study );

        all_question_number=findViewById( R.id.all_question_number );

        text_quOne_study_l=findViewById( R.id.text_quOne_study_l );
        text_quTwo_study_l=findViewById( R.id.text_quTwo_study_l );
        text_quthree_study_l=findViewById( R.id.text_quthree_study_l );
        text_qufour_study_l=findViewById( R.id.text_qufour_study_l );

        constraint_quOne_study=findViewById( R.id.constraint_quOne_study );
        constraint_quTwo_study=findViewById( R.id.constraint_quTwo_study );
        constraint_quthree_study=findViewById( R.id.constraint_quthree_study );
        constraint_qutfour_study=findViewById( R.id.constraint_qutfour_study );

        constraint_quOne_study_l=findViewById( R.id.constraint_quOne_study_l );
        constraint_quTwo_study_l=findViewById( R.id.constraint_quTwo_study_l );
        constraint_quthree_study_l=findViewById( R.id.constraint_quthree_study_l );
        constraint_qutfour_study_l=findViewById( R.id.constraint_qutfour_study_l );
        //***************************************************************************//
        //***************************************************************************//
        totalTime_study = findViewById(R.id.totalTime_study);
    }


    /**********************************************************************************/
//*********************** contentMainSetup() ************************************//

    private void contentMainSetup() {
    }
//*********************** contentMainSetup() ************************************//
/**********************************************************************************/


/**********************************************************************************/
//*********************** bottomSheetsetup() ************************************//
    private void bottomSheetSetup() {
        View bottomSheet = findViewById(R.id.bottom_sheet);


        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        button_slide=findViewById( R.id.button_slide );
        coordinatorLayout=findViewById( R.id.coordinatorLayout );
        linear_answer_guide=findViewById( R.id.linear_answer_guide );


        recycler_report_study_line.setVisibility( View.VISIBLE );
        recycler_report_study_all.setVisibility( View.GONE );
        linear_answer_guide.setVisibility( View.GONE );

        Intent intent = getIntent();
        totalTime = intent.getIntExtra("totalTime",100  );


        button_slide.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(slideIsOpen){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    slideIsOpen=false;
                }
                else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    slideIsOpen=true;

                }


            }
        } );




        //Handling movement of bottom sheets from sliding
        bottomSheetBehavior.setBottomSheetCallback( new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_COLLAPSED) {
                    recycler_report_study_line.setVisibility( View.VISIBLE );
                    recycler_report_study_all.setVisibility( View.GONE );

                    if(challengeState.equals( "inAnswer" )){
                         linear_answer_guide.setVisibility( View.GONE );
                    }

                } else if (i == BottomSheetBehavior.STATE_EXPANDED) {
                    recycler_report_study_line.setVisibility( View.GONE );
                    if(challengeState.equals( "inAnswer" )){
                        linear_answer_guide.setVisibility( View.VISIBLE );
                    }
                    recycler_report_study_all.setVisibility( View.VISIBLE );



                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }
//*********************** bottomSheetsetup() ************************************//
/**********************************************************************************/


//***********************************************************************************************//
    private void timer(int time) {
        timerSeek = new Timer();
        timerShow = new Timer();
        statusTime=time;


        timerSeek.schedule( new TimerTask() {
            @Override
            public void run() {
                runOnUiThread( new Runnable() {
                    // @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        if(!pauseTimer){
                            pStatusSeek += 1;
                            progressBar_timer.setProgress(pStatusSeek);
                            if (pStatusSeek == 0) {
                            }
                        }

                    }
                } );
            }
        }, 0, (statusTime*10) );

        timerShow.schedule( new TimerTask() {
            @Override
            public void run() {
                runOnUiThread( new Runnable() {
                    // @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        if(!pauseTimer){
                            statusTime-=1;
                            long secound=statusTime;
                            long mint=secound/60;
                            secound %=60;
                            showTime= String.format( Locale.ENGLISH,"%02d",mint)+" : "+String.format(Locale.ENGLISH,"%02d",secound);
                            totalTime_study.setText( showTime );

                            if (statusTime <= 0) {
                                timerSeek.cancel();
                                timerShow.cancel();
                                endTime=true;
                                showDialogs( "endTime" );


                            }
                        }

                    }
                } );
            }
        }, 0, 1000 );


    }
//****************************************************************************************************************//
    public class ShowLineQuestions extends RecyclerView.Adapter<ShowLineQuestions.LineQuestionsViewHolder>{
        List<Integer> questionSelectedState=new ArrayList<>(  );
        int currentNumberQ;

        public ShowLineQuestions(List<Integer> questionSelectedState,int currentNumberQ){
            this.questionSelectedState=questionSelectedState;
            this.currentNumberQ=currentNumberQ;
        }
        @NonNull
        @Override
        public LineQuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from( viewGroup.getContext() )
                    .inflate( R.layout.report_chance_item,viewGroup,false );
            StudyChallengeActivity.ShowLineQuestions.LineQuestionsViewHolder holder;
            holder= new LineQuestionsViewHolder( view );
            return holder;    }

        @Override
        public void onBindViewHolder(@NonNull LineQuestionsViewHolder lineQuestionsViewHolder, int i) {
            lineQuestionsViewHolder. text_number_chance_item
                    .setTextSize( TypedValue.COMPLEX_UNIT_DIP, 15 );
            lineQuestionsViewHolder. text_number_chance_item
                    .setTextColor( getResources().getColor( R.color.colorDarkText ) );

                int num=i+1;

                lineQuestionsViewHolder.text_number_chance_item.setText( ""+num );
                int state=questionSelectedState.get( i );
                switch (state){
                    case 0:
                            lineQuestionsViewHolder.report_chance_back_item
                                    .setBackgroundResource( R.drawable.circle_shape_number_question_gray  );

                        break;

                    default:
                        lineQuestionsViewHolder.report_chance_back_item
                                .setBackgroundResource( R.drawable.circle_shape_select_question_blue  );
                        break;
                }
                if (currentNumberQ==i+1){
                   // lineQuestionsViewHolder.report_chance_back_item
                            //.setBackgroundResource( R.drawable.circle_shape_number_question_current );

                    lineQuestionsViewHolder.report_chance_back_item
                            .setBackgroundResource( R.drawable.circle_shape_number_question_gray );
                    lineQuestionsViewHolder. text_number_chance_item
                            .setTextSize( TypedValue.COMPLEX_UNIT_DIP, 25 );
                    lineQuestionsViewHolder. text_number_chance_item
                            .setTextColor( getResources().getColor( R.color.colorBlueText ) );
                }





        }

        @Override
        public int getItemCount() {
           /* int size=0;
            if(rState.equals( "timeConsuming" )){
                size=timeConsumingQuestions.size();
            }else if(rState.equals( "all" )){
                size=questionNumber;
            }*/
            return questionNumber;

        }

        public class LineQuestionsViewHolder extends RecyclerView.ViewHolder{
            ConstraintLayout report_chance_back_item;
            TextView text_number_chance_item;
            public LineQuestionsViewHolder(@NonNull View itemView) {
                super( itemView );
                report_chance_back_item=itemView.findViewById( R.id.report_chance_back_item );
                text_number_chance_item=itemView.findViewById( R.id.text_number_chance_item );

                report_chance_back_item.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            int num=getAdapterPosition();
                            startMatch( num );



                    }
                } );
            }
        }



    }
    //******************************************************************************************************//
    public class AnswerQuestionReport extends RecyclerView.Adapter<AnswerQuestionReport.MyAnswerQuestionReport>{
        List<Integer> questionSelectedState=new ArrayList<>(  );
        List<Integer> answerQuestions=new ArrayList<>(  );

        int currentNumberN;

        public AnswerQuestionReport(List<Integer> questionSelectedState,List<Integer> answerQuestions,int currentNumberN){
            this.questionSelectedState=questionSelectedState;
            this.answerQuestions=answerQuestions;
            this.currentNumberN=currentNumberN;
        }

        @NonNull
        @Override
        public MyAnswerQuestionReport onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from( viewGroup.getContext() )
                    .inflate( R.layout.report_chance_item,viewGroup,false );
            MyAnswerQuestionReport holder;
            holder= new MyAnswerQuestionReport( view );
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyAnswerQuestionReport myAnswerQuestionReport, int i) {
            int num=i+1;
            myAnswerQuestionReport.text_number_chance_item
                    .setTextSize( TypedValue.COMPLEX_UNIT_DIP, 15 );
            myAnswerQuestionReport.text_number_chance_item
                    .setTextColor( getResources().getColor( R.color.colorDarkText ) );
            myAnswerQuestionReport.text_number_chance_item.setText( ""+num );
            int state=questionSelectedState.get( i );
            switch (state){
                case 0:
                    myAnswerQuestionReport.report_chance_back_item
                            .setBackgroundResource( R.drawable.circle_shape_number_question_gray  );

                    break;
                case 5:

                default:
                    if(answerQuestions.get( i )==questionSelectedState.get( i )){
                        myAnswerQuestionReport.report_chance_back_item
                                .setBackgroundResource( R.drawable.circle_shape_number_question_green );
                    }
                    else {
                        myAnswerQuestionReport.report_chance_back_item
                                .setBackgroundResource( R.drawable.circle_shape_number_question_red );
                    }


                    break;
            }
            if (currentNumberN==i+1){
                myAnswerQuestionReport.text_number_chance_item
                        .setTextSize( TypedValue.COMPLEX_UNIT_DIP, 25 );
                myAnswerQuestionReport.text_number_chance_item
                        .setTextColor( getResources().getColor( R.color.colorBlueText ) );
            }


        }

        @Override
        public int getItemCount() {
            return answerQuestions.size();
        }


        public class MyAnswerQuestionReport extends RecyclerView.ViewHolder{
            ConstraintLayout report_chance_back_item;
            TextView text_number_chance_item;
            public MyAnswerQuestionReport(@NonNull View itemView) {
                super( itemView );
                report_chance_back_item=itemView.findViewById( R.id.report_chance_back_item );
                text_number_chance_item=itemView.findViewById( R.id.text_number_chance_item );
                report_chance_back_item.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int num=getAdapterPosition();
                        startAnswer( num );
                    }
                } );

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
    secondOptions.add( "برخلاف ظاهر، درون و باطن انسان با خدا باشد - عمل به احکام دین" );
    secondOptions.add( "« یا اخت ھارون ما کان ابوک امرأ سوءٍ و ما کانت امّک بغیّاً »" );
    secondOptions.add( "تعالی روح شخصیت انسان علت استحکام رشته ي عفاف در اوست." );
    secondOptions.add( "« یقیمون الصلاه ویؤتون الزکاه ویطیعون لله و رسولھ »" );
    secondOptions.add( "عرضی و طولی - طولی" );
    secondOptions.add( " امداد « و لو انّ اھل القری ءامنوا و اتّقوا لفتحنا علیھم برکاتٍ من السّماء و الارض » " );
    secondOptions.add( " استدراج-« و لکن کذّبوا فاخذناھم بما کانوا یکسبون ...»  " );
    secondOptions.add( " « من جاء بالحسنة فلھ عشر أمثالھا »-نیکوکاران و بدکاران - آشکار کردن سرشت خود " );
    secondOptions.add( " « من جاء بالحسنة فلھ عشر أمثالھا »-نیکوکاران و بدکاران - آشکار کردن سرشت خود " );

    thirdOptions.add( "برخلاف ظاهر، درون و باطن انسان با خدا باشد - عمل به احکام دین" );
    thirdOptions.add( "« یا اخت ھارون ما کان ابوک امرأ سوءٍ و ما کانت امّک بغیّاً »" );
    thirdOptions.add( "تعالی روح شخصیت انسان علت استحکام رشته ي عفاف در اوست." );
    thirdOptions.add( "« یقیمون الصلاه ویؤتون الزکاه ویطیعون لله و رسولھ »" );
    thirdOptions.add( "عرضی و طولی - طولی" );
    thirdOptions.add( " امداد « و لو انّ اھل القری ءامنوا و اتّقوا لفتحنا علیھم برکاتٍ من السّماء و الارض » " );
    thirdOptions.add( " استدراج-« و لکن کذّبوا فاخذناھم بما کانوا یکسبون ...»  " );
    thirdOptions.add( " « من جاء بالحسنة فلھ عشر أمثالھا »-نیکوکاران و بدکاران - آشکار کردن سرشت خود " );
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
    questionsShow.add("g");
    questionsShow.add("l");
    questionsShow.add("l");
    questionsShow.add("g");
    questionsShow.add("g");
    questionsShow.add("l");
    questionsShow.add("l");
    questionsShow.add("g");

    questionAnswerState.add( 3);
    questionAnswerState.add( 3);
    questionAnswerState.add( 3);
    questionAnswerState.add( 3);
    questionAnswerState.add( 3);
    questionAnswerState.add( 3);
    questionAnswerState.add( 3);
    questionAnswerState.add( 3);
    questionAnswerState.add( 3);
    questionAnswerState.add( 3);
    questionAnswerState.add( 3);
    questionAnswerState.add( 3);
    questionAnswerState.add( 3);
    questionAnswerState.add( 3);
    questionAnswerState.add( 3);
    questionAnswerState.add( 3);

}

    @Override
    protected void onStart() {
        super.onStart();
        String sound= Hawk.get( "sound" );
        if (sound.equals( "on" )) {
            clickSound=true;

        }
        String music= Hawk.get( "music" );
        if (music.equals( "on" )) {
            StudyChallengeActivity.this.stopService(new Intent( StudyChallengeActivity.this, MainMusicService.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String music= Hawk.get( "music" );
        if (music.equals( "on" )) {
            StudyChallengeActivity.this.stopService(new Intent( StudyChallengeActivity.this, SoundService.class));


        }
    }

    @Override
    public void onBackPressed() {
        WarningExiteMachDialog dialog=new WarningExiteMachDialog( StudyChallengeActivity.this );
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

    }
}
