package com.bavin.mohsen.backnardeban;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bavin.mohsen.backnardeban.Classes.Adapters.SetStudyViewPgerAdapter;
import com.bavin.mohsen.backnardeban.Classes.Dialogs.EndStudyTimeWarningDialog;
import com.bavin.mohsen.backnardeban.Classes.Dialogs.ProgressDialog;
import com.bavin.mohsen.backnardeban.Classes.Dialogs.ReturnChallengeDialog;
import com.bavin.mohsen.backnardeban.Classes.Dialogs.StopStudyChallengeDialog;
import com.bavin.mohsen.backnardeban.Classes.Dialogs.StudyResultsDialog;
import com.bavin.mohsen.backnardeban.Classes.Dialogs.WarningExiteMachDialog;
import com.bavin.mohsen.backnardeban.Classes.MainMusicService;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.APIRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.ApiIntarfaceRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.GetStudyQuestions;
import com.bavin.mohsen.backnardeban.Classes.SoundService;
import com.bavin.mohsen.backnardeban.fragments.ShowQuestionLinearFragment;
import com.bavin.mohsen.backnardeban.fragments.ShowQuestionmultipleFragment;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class StudyChallengeActivity extends AppCompatActivity implements ShowQuestionLinearFragment.OnHeadlineSelectedListener
 , ShowQuestionmultipleFragment.OnHeadlineSelectedListener{
    private List<GetStudyQuestions> questionLessons;

    BottomSheetBehavior bottomSheetBehavior;
    ImageView button_slide;
    private boolean slideIsOpen=false,clickSound=false;
    RecyclerView recycler_report_study_line,recycler_report_study_all;
    ProgressDialog progressDialog;
    int correctNumber,incorrectNumber,unansweredNumber;
    List<Integer> myAnswer=new ArrayList<>(  );
    List<Integer> optionsAnswer=new ArrayList<>(  );
    CoordinatorLayout coordinatorLayout;
    LinearLayout linear_answer_guide,linear_question_guide;
    TextView totalTime_study;
    TextView text_question_study;
    TextView all_question_number,current_question_number;
    TextView txt_pauseTime_study,text_stop_time;
    String challengeState="inChallenge";

    int currentNumber=0;
    ViewPager view_Study_question,view_Study_answer;

    MediaPlayer true_sound,false_sound;
    StudyChallengeActivity.ShowLineQuestions adapterQuestionReycle;
    StudyChallengeActivity.ShowLineQuestions adapterQuestionReycleAll;
    AnswerQuestionReport adapterAnswer;
    AnswerQuestionReport adapterAnswerAll;
    //ProgressBar progressBar_timer;
    CircleProgress progressBar_timer;

    LinearLayoutManager horizontalLayoutManagaer =
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

    ConstraintLayout constraint_show_number,constraintLayout_timer,constraintLayout_header_study,constraintLayout_main_study;

    ImageView btn_back_study,btn_next_study;
    ImageView btn_stop_study,btn_pauseTime;

    TextView text_start_studyChallenge;
    int questionNumbers;
    Timer timerSeek,timerShow,timerCounter;
    int pStatusSeek=0;
    int totalTime;
    int statusTime;
    boolean pauseTimer=false,endTime=false;
    String showTime;

    public void onArticleSelected(int position) {
        myAnswer.set( currentNumber,position );
        adapterQuestionReycle.notifyDataSetChanged();
        adapterQuestionReycleAll.notifyDataSetChanged();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_study_challenge );

        Intent intent = getIntent();
        totalTime = intent.getIntExtra("totalTime",100  );
        questionNumbers = intent.getIntExtra("totalquestion",18  );
        false_sound=MediaPlayer.create(StudyChallengeActivity.this , R.raw.coin3);
        false_sound.setVolume( 5.0f,5.0f );


        for (int i=0;i<=questionNumbers-1;i++){
            myAnswer.add( 0 );
        }
        findOptions();
        bottomSheetSetup();
         adapterQuestionReycle = new
                StudyChallengeActivity.ShowLineQuestions( myAnswer, currentNumber + 1 );
         adapterQuestionReycleAll = new
                StudyChallengeActivity.ShowLineQuestions(  myAnswer, currentNumber + 1);
        adapterAnswer = new StudyChallengeActivity.AnswerQuestionReport( optionsAnswer, myAnswer );

        constraint_show_number.setVisibility( View.INVISIBLE );
        constraintLayout_main_study.setVisibility( View.INVISIBLE );
        constraintLayout_header_study.setVisibility( View.INVISIBLE );
        coordinatorLayout.setVisibility( View.GONE );
        recycler_report_study_all.setVisibility( View.GONE );
        button_slide.setVisibility( View.INVISIBLE );
        progressDialog=new ProgressDialog( StudyChallengeActivity.this );
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        getQuestions();

        btn_next_study.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(challengeState.equals( "inChallenge" )){
                    if (currentNumber<questionNumbers-1){
                        btn_next_study.startAnimation( AnimationUtils.loadAnimation(getBaseContext(), R.anim.image_click_animation));
                        currentNumber++;
                        view_Study_question.setCurrentItem( currentNumber );
                        current_question_number.setText( ""+(currentNumber+1) );
                        adapterQuestionReycle.notifyDataSetChanged();
                        adapterQuestionReycleAll.notifyDataSetChanged();
                       if(currentNumber>=3) horizontalLayoutManagaer.scrollToPositionWithOffset(currentNumber-3, currentNumber);
                       else horizontalLayoutManagaer.scrollToPositionWithOffset(0, 5);


                    }
                }else if(challengeState.equals( "inAnswer" )){
                    if (currentNumber<questionNumbers-1){
                        btn_next_study.startAnimation( AnimationUtils.loadAnimation(getBaseContext(), R.anim.image_click_animation));
                        currentNumber++;
                        view_Study_answer.setCurrentItem( currentNumber );
                        current_question_number.setText( ""+(currentNumber+1) );
                        adapterAnswer.notifyDataSetChanged();
                        adapterAnswerAll.notifyDataSetChanged();
                        if(currentNumber>=3) horizontalLayoutManagaer.scrollToPositionWithOffset(currentNumber-3, currentNumber);
                       else horizontalLayoutManagaer.scrollToPositionWithOffset(0, 5);


                    }

                }
            }
        } );

        btn_back_study.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(challengeState.equals( "inChallenge" )){
                        if (currentNumber>0){
                            btn_back_study.startAnimation( AnimationUtils.loadAnimation(getBaseContext(), R.anim.image_click_animation));
                            currentNumber--;
                            view_Study_question.setCurrentItem( currentNumber );
                            current_question_number.setText( ""+(currentNumber+1) );
                            adapterQuestionReycle.notifyDataSetChanged();
                            adapterQuestionReycleAll.notifyDataSetChanged();
                            if(currentNumber>=3) horizontalLayoutManagaer.scrollToPositionWithOffset(currentNumber-3, currentNumber);
                            else horizontalLayoutManagaer.scrollToPositionWithOffset(0, 5);


                        }
                }else if(challengeState.equals( "inAnswer" )){

                    if (currentNumber>0){
                        btn_back_study.startAnimation( AnimationUtils.loadAnimation(getBaseContext(), R.anim.image_click_animation));
                        currentNumber--;
                        view_Study_answer.setCurrentItem( currentNumber );
                        current_question_number.setText( ""+(currentNumber+1) );
                        adapterAnswer.notifyDataSetChanged();
                        adapterAnswerAll.notifyDataSetChanged();
                        if(currentNumber>=3) horizontalLayoutManagaer.scrollToPositionWithOffset(currentNumber-3, currentNumber);
                        else horizontalLayoutManagaer.scrollToPositionWithOffset(0, 5);


                    }


                }


            }
        } );
        view_Study_question.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                 currentNumber = view_Study_question.getCurrentItem();
                current_question_number.setText( ""+(currentNumber+1) );

                adapterQuestionReycle.notifyDataSetChanged();
                adapterQuestionReycleAll.notifyDataSetChanged();
                if(currentNumber>=3) horizontalLayoutManagaer.scrollToPositionWithOffset(currentNumber-3, currentNumber);
                else horizontalLayoutManagaer.scrollToPositionWithOffset(0, 5);

            }
        } );

        view_Study_answer.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                currentNumber = view_Study_answer.getCurrentItem();
                current_question_number.setText( ""+(currentNumber+1) );
                adapterAnswer.notifyDataSetChanged();
                adapterAnswerAll.notifyDataSetChanged();
                if(currentNumber>=3) horizontalLayoutManagaer.scrollToPositionWithOffset(currentNumber-3, currentNumber);
                else horizontalLayoutManagaer.scrollToPositionWithOffset(0, 5);


            }
        } );


        btn_stop_study.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_stop_study.startAnimation( AnimationUtils.loadAnimation(getBaseContext(), R.anim.image_click_animation));

                if(endTime) showDialogs("endStop");
                else showDialogs("stop");
            }
        } );

        btn_pauseTime.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                v.startAnimation( AnimationUtils.loadAnimation(getBaseContext(), R.anim.image_click_animation));
                showDialogs( "pause" );
            }
        } );

        constraintLayout_timer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(challengeState.equals( "inAnswer" )){
                   showDialogs( "endChallenge" );
                }


            }
        } );


    }


    private void getQuestions(){
        ApiIntarfaceRetro apiIntarfaceRetro= APIRetro.getAPI().create( ApiIntarfaceRetro.class );
        Call<List<GetStudyQuestions>> getStudyQuestions=apiIntarfaceRetro.getStudyQuestions( "زیست","زیست",questionNumbers );
        getStudyQuestions.enqueue( new Callback<List<GetStudyQuestions>>() {
            @Override
            public void onResponse(Call<List<GetStudyQuestions>> call, Response<List<GetStudyQuestions>> response) {
                questionLessons=response.body();
                for (int i=0;i<questionLessons.size();i++){
                    optionsAnswer.add( questionLessons.get( i ).getApiAnswerQuestions() );
                }
                timerStartChallenge();
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<List<GetStudyQuestions>> call, Throwable t) {
                Toast.makeText( StudyChallengeActivity.this,"onFailure",Toast.LENGTH_SHORT ).show();

            }
        } );

    }
//********************************************************************************************************
private void setViewPager(String state){
    Fragment fragment;

    //viewFragments.clear();
    if(state.equals( "inChallenge" )){

        recycler_report_study_all.setAdapter( adapterQuestionReycleAll );
        recycler_report_study_all.setLayoutManager(new GridLayoutManager(getBaseContext(), 7));
        recycler_report_study_line.setAdapter( adapterQuestionReycle );

        recycler_report_study_line.setLayoutManager(horizontalLayoutManagaer);

        view_Study_answer.setVisibility( View.GONE );
        view_Study_question.setVisibility( View.VISIBLE );
        List<Fragment> viewFragments=new ArrayList<>(  );


        for (int i=0;i<=questionNumbers-1;i++){
            if(questionLessons.get( i ).getApiQuestionsShow().equals( "l" )){
                fragment=ShowQuestionLinearFragment.newFragment
                        (       state,
                                questionLessons.get( i ).getApiQuestions(),
                                questionLessons.get( i ).getApiFirstOptions(),
                                questionLessons.get( i ).getApiSecondOptions(),
                                questionLessons.get( i ).getApiThirdOptions(),
                                questionLessons.get( i ).getApiFourthOptions(),
                                questionLessons.get( i ).getApiAnswerQuestions(),
                                myAnswer.get( i )
                        );
                viewFragments.add(fragment );
            }else  if(questionLessons.get( i ).getApiQuestionsShow().equals( "g" )){
                fragment=ShowQuestionmultipleFragment.newFragment
                        (       state,
                                questionLessons.get( i ).getApiQuestions(),
                                questionLessons.get( i ).getApiFirstOptions(),
                                questionLessons.get( i ).getApiSecondOptions(),
                                questionLessons.get( i ).getApiThirdOptions(),
                                questionLessons.get( i ).getApiFourthOptions(),
                                questionLessons.get( i ).getApiAnswerQuestions(),
                                myAnswer.get( i )
                        );
                viewFragments.add(fragment );
            }

        }
        SetStudyViewPgerAdapter pagerAdapter;
        pagerAdapter=new SetStudyViewPgerAdapter( getSupportFragmentManager(),viewFragments,questionLessons );
        view_Study_question.setAdapter( pagerAdapter );
        setTimer();
    }else if(state.equals( "inAnswer" )){
        currentNumber=0;
        challengeState="inAnswer";
        progressBar_timer.setProgress(100);
        current_question_number.setText( ""+1 );
        view_Study_answer.setVisibility( View.VISIBLE );
        view_Study_question.setVisibility( View.GONE );


        recycler_report_study_line.setLayoutManager(horizontalLayoutManagaer);
        recycler_report_study_line.setAdapter( adapterAnswer );

         adapterAnswerAll = new
                AnswerQuestionReport(  optionsAnswer,myAnswer);
        recycler_report_study_all.setAdapter( adapterAnswerAll );
        recycler_report_study_all.setLayoutManager(new GridLayoutManager(getBaseContext(), 7));



        List<Fragment> viewFragmentsAns=new ArrayList<>(  );

        for (int i=0;i<=questionNumbers-1;i++){
            if(questionLessons.get( i ).getApiQuestionsShow().equals( "l" )){
                fragment=ShowQuestionLinearFragment.newFragment
                        (       state,
                                questionLessons.get( i ).getApiQuestions(),
                                questionLessons.get( i ).getApiFirstOptions(),
                                questionLessons.get( i ).getApiSecondOptions(),
                                questionLessons.get( i ).getApiThirdOptions(),
                                questionLessons.get( i ).getApiFourthOptions(),
                                questionLessons.get( i ).getApiAnswerQuestions(),
                                myAnswer.get( i )
                        );
                viewFragmentsAns.add(fragment );
            }else  if(questionLessons.get( i ).getApiQuestionsShow().equals( "g" )){
                fragment=ShowQuestionmultipleFragment.newFragment
                        (       state,
                                questionLessons.get( i ).getApiQuestions(),
                                questionLessons.get( i ).getApiFirstOptions(),
                                questionLessons.get( i ).getApiSecondOptions(),
                                questionLessons.get( i ).getApiThirdOptions(),
                                questionLessons.get( i ).getApiFourthOptions(),
                                questionLessons.get( i ).getApiAnswerQuestions(),
                                myAnswer.get( i )
                        );
                viewFragmentsAns.add(fragment );
            }

        }
        SetStudyViewPgerAdapter pagerAdapterAns;
        pagerAdapterAns=new SetStudyViewPgerAdapter( getSupportFragmentManager(),viewFragmentsAns,questionLessons );
        view_Study_answer.setAdapter( pagerAdapterAns );

    }



}
//*********************************************************************************
    private void setTimer(){

        timerSeek = new Timer();
        timerShow = new Timer();
        statusTime=totalTime;


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
                            if (pStatusSeek ==100) {
                                timerSeek.cancel();
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

    private void moreTime(){
        statusTime=0;
        timerShow = new Timer();
        timerShow.schedule( new TimerTask() {
            @Override
            public void run() {
                runOnUiThread( new Runnable() {
                    // @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        if(!pauseTimer){
                            statusTime+=1;
                            long secound=statusTime;
                            long mint=secound/60;
                            secound %=60;
                            showTime= String.format( Locale.ENGLISH,"%02d",mint)+" : "+String.format(Locale.ENGLISH,"%02d",secound);
                            totalTime_study.setText( showTime );

                        }

                    }
                } );
            }
        }, 0, 1000 );
    }
    /**********************************************************************************/
//*********************** bottomSheetsetup() ************************************//
    private void bottomSheetSetup() {
        View bottomSheet = findViewById(R.id.bottom_sheet);


        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);



        recycler_report_study_line.setVisibility( View.VISIBLE );
        recycler_report_study_all.setVisibility( View.GONE );
        linear_answer_guide.setVisibility( View.GONE );
        linear_question_guide.setVisibility( View.VISIBLE );


        button_slide.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(slideIsOpen){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    slideIsOpen=false;
                    button_slide.setImageResource(R.drawable.up);

                }
                else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    slideIsOpen=true;
                    button_slide.setImageResource(R.drawable.down);


                }


            }
        } );




        //Handling movement of bottom sheets from sliding
        bottomSheetBehavior.setBottomSheetCallback( new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_COLLAPSED) {
                    slideIsOpen=false;
                    button_slide.setImageResource(R.drawable.up);

                    recycler_report_study_line.setVisibility( View.VISIBLE );
                    recycler_report_study_all.setVisibility( View.GONE );

                    if(challengeState.equals( "inAnswer" )){
                        linear_answer_guide.setVisibility( View.GONE );
                        linear_question_guide.setVisibility( View.VISIBLE );


                    }

                } else if (i == BottomSheetBehavior.STATE_EXPANDED) {
                    slideIsOpen=true;
                    button_slide.setImageResource(R.drawable.down);

                    recycler_report_study_line.setVisibility( View.GONE );
                    if(challengeState.equals( "inAnswer" )){
                        linear_answer_guide.setVisibility( View.VISIBLE );
                        linear_question_guide.setVisibility( View.GONE );

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
    /**********************************************************************************/    //**************************************************************************************//

    private void showDialogs(String mode) {
        switch (mode){
            case "pause":
                pauseTimer=true;
                ReturnChallengeDialog returnChallengeDialog=new ReturnChallengeDialog( StudyChallengeActivity.this );
                returnChallengeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                returnChallengeDialog.setCancelable(false);
                returnChallengeDialog.show();
                returnChallengeDialog.findViewById( R.id.btn_return_challenge ).setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.startAnimation( AnimationUtils.loadAnimation(getBaseContext(), R.anim.button_click_animation));
                        pauseTimer=false;
                        returnChallengeDialog.dismiss();
                    }
                } );
                break;

            case "stop":
                pauseTimer=true;
                StopStudyChallengeDialog warnDialog= new StopStudyChallengeDialog
                        (StudyChallengeActivity.this,true);
                warnDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                warnDialog.setCanceledOnTouchOutside(false);
                warnDialog.setCancelable(false);
                warnDialog.show();
                warnDialog.findViewById( R.id.btn_continue_study  ).setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pauseTimer=false;
                        warnDialog.dismiss();

                    }
                } );
                warnDialog.findViewById( R.id.btn_end_study  ).setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialogs( "endChallenge" );
                        progressBar_timer.setProgress( 100 );
                        setViewPager( "inAnswer" );
                        warnDialog.dismiss();
                    }
                } );
                break;
            case "endStop":
                pauseTimer=true;
                StopStudyChallengeDialog esDialog= new StopStudyChallengeDialog
                        (StudyChallengeActivity.this,false);
                esDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                esDialog.setCanceledOnTouchOutside(false);
                esDialog.setCancelable(false);
                esDialog.show();
                esDialog.findViewById( R.id.btn_continue_study  ).setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pauseTimer=false;
                        esDialog.dismiss();

                    }
                } );
                esDialog.findViewById( R.id.btn_end_study  ).setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialogs( "endChallenge" );
                        setViewPager( "inAnswer" );
                        esDialog.dismiss();
                    }
                } );
                break;
            case "endTime":
                text_stop_time.setVisibility( View.GONE );
                txt_pauseTime_study.setVisibility( View.GONE );

                EndStudyTimeWarningDialog warnTimeDialog= new EndStudyTimeWarningDialog
                        (StudyChallengeActivity.this);
                warnTimeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                warnTimeDialog.setCanceledOnTouchOutside(false);
                warnTimeDialog.show();
                warnTimeDialog.findViewById( R.id.btn_get_time_study  ).setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        moreTime();
                        warnTimeDialog.dismiss();

                    }
                } );
                warnTimeDialog.findViewById( R.id.btn_end_studyTime  ).setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialogs( "endChallenge" );
                        setViewPager( "inAnswer" );
                        warnTimeDialog.dismiss();

                    }
                } );
                break;
            case "endChallenge":

                incorrectNumber=0;
                correctNumber=0;
                incorrectNumber=0;

                for (int j=0;j<questionNumbers;j++){
                    if(myAnswer.get( j )==0) unansweredNumber++;
                    else {
                        if (myAnswer.get( j )==optionsAnswer.get( j )) correctNumber++;
                        else if (myAnswer.get( j )!=optionsAnswer.get( j )) incorrectNumber++;
                    }
                }
                StudyResultsDialog studyResultsDialog=new
                        StudyResultsDialog(StudyChallengeActivity.this,correctNumber,incorrectNumber,unansweredNumber,questionNumbers  );
                studyResultsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                studyResultsDialog.setCanceledOnTouchOutside(false);
                studyResultsDialog.show();
                studyResultsDialog.findViewById( R.id. btn_show_answers).setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timerSeek.cancel();
                        timerShow.cancel();
                        text_stop_time.setVisibility( View.GONE );
                        txt_pauseTime_study.setVisibility( View.GONE );
                        btn_pauseTime.setVisibility( View.GONE );
                        btn_stop_study.setVisibility( View.GONE );
                        totalTime_study.setTextSize( 18);

                        totalTime_study.setText( "مشاهده درصدها" );
                        //startAnswer(currentNumber);
                        studyResultsDialog.dismiss();

                    }
                } );

                break;
        }
    }


    //****************************************************************************************************//
    private void showQuestionNumber() {
        all_question_number.setText( ""+questionNumbers );
        current_question_number.setText( ""+currentNumber+1 );

    }

    //****************************************************************************************************//
    private void timerStartChallenge() {

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
                            text_start_studyChallenge.setTextSize( 50 );
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
                            /*linearLayout_main_study.setVisibility( View.VISIBLE );
                            button_slide.setVisibility( View.VISIBLE );
                            constraint_show_number.setVisibility( View.VISIBLE );
                            //startMatch(currentNumber);
                            timer( totalTime );
                            text_start_studyChallenge.setVisibility( View.GONE );
                            challengeState="inChallenge";*/
                            text_start_studyChallenge.setVisibility( View.GONE );
                            constraint_show_number.setVisibility( View.VISIBLE );
                            recycler_report_study_all.setVisibility( View.VISIBLE );
                            recycler_report_study_line.setVisibility( View.VISIBLE );
                            constraintLayout_main_study.setVisibility( View.VISIBLE );
                            constraintLayout_header_study.setVisibility( View.VISIBLE );
                            button_slide.setVisibility( View.VISIBLE );
                            coordinatorLayout.setVisibility( View.VISIBLE );
                            all_question_number.setText( ""+questionNumbers );
                            current_question_number.setText( ""+(currentNumber+1) );

                            setViewPager( "inChallenge" );
                            timerCounter.cancel();
                        }
                    }
                } );
            }
        },0,1000 );
    }


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
            if (currentNumber==i){
                lineQuestionsViewHolder. text_number_chance_item
                        .setTextSize( TypedValue.COMPLEX_UNIT_DIP, 28 );
                lineQuestionsViewHolder. text_number_chance_item
                        .setTextColor( getResources().getColor( R.color.colorDarkText ) );
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
            return questionSelectedState.size();

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

                        currentNumber=getAdapterPosition();
                        adapterQuestionReycle.notifyDataSetChanged();
                        adapterQuestionReycleAll.notifyDataSetChanged();
                        // if (currentNumber>3)recycler_report_study_line.getLayoutManager().scrollToPosition(currentNumber-3);
                        // recycler_report_study_line.getLayoutManager().scrollToPosition(currentNumber+3);
                        if(currentNumber>=3) horizontalLayoutManagaer.scrollToPositionWithOffset(currentNumber-3, currentNumber);
                       else horizontalLayoutManagaer.scrollToPositionWithOffset(0,5);
                        view_Study_question.setCurrentItem( currentNumber );




                    }
                } );
            }
        }
    }
//******************************************************************************************************//
//******************************************************************************************************//
public class AnswerQuestionReport extends RecyclerView.Adapter<AnswerQuestionReport.MyAnswerQuestionReport>{
    List<Integer> questionSelectedState=new ArrayList<>(  );
    List<Integer> answerQuestions=new ArrayList<>(  );


    public AnswerQuestionReport(List<Integer> answerQuestions,List<Integer> questionSelectedState){
        this.questionSelectedState=questionSelectedState;
        this.answerQuestions=answerQuestions;
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
            default:
                if(answerQuestions.get( i )== questionSelectedState.get( i )){
                    myAnswerQuestionReport.report_chance_back_item
                            .setBackgroundResource( R.drawable.circle_shape_number_question_green );
                }
                else {
                    myAnswerQuestionReport.report_chance_back_item
                            .setBackgroundResource( R.drawable.circle_shape_number_question_red );
                }


                break;
        }
        if (currentNumber==i){
            myAnswerQuestionReport.text_number_chance_item
                    .setTextSize( TypedValue.COMPLEX_UNIT_DIP, 28 );
            myAnswerQuestionReport.text_number_chance_item
                    .setTextColor( getResources().getColor( R.color.colorWetasphalt ) );
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
                    currentNumber=getAdapterPosition();
                    adapterAnswer.notifyDataSetChanged();
                    adapterAnswerAll.notifyDataSetChanged();
                    if(currentNumber>=3) horizontalLayoutManagaer.scrollToPositionWithOffset(currentNumber-3, currentNumber);
                    else horizontalLayoutManagaer.scrollToPositionWithOffset(0, 5);
                    view_Study_answer.setCurrentItem( currentNumber );

                }
            } );

        }
    }
}

//********************************************************************************************************
    private void findOptions() {
        recycler_report_study_line = findViewById(R.id.recycler_report_study_line);
        recycler_report_study_all = findViewById(R.id.recycler_report_study_all);

        view_Study_question=findViewById( R.id.view_Study_question );
        view_Study_answer=findViewById( R.id.view_Study_answer );

        //***************************************************************************//
        btn_back_study=findViewById( R.id.btn_back_study );
        btn_next_study=findViewById( R.id.btn_next_study );
        text_stop_time=findViewById( R.id.text_stop_time );
        txt_pauseTime_study=findViewById( R.id.txt_pauseTime_study );
        btn_pauseTime=findViewById( R.id.btn_pauseTime );
        btn_stop_study=findViewById( R.id.btn_stop_study );
        button_slide=findViewById( R.id.button_slide );

        constraintLayout_timer=findViewById( R.id.constraintLayout_timer );
        progressBar_timer=findViewById( R.id.progressBar_timer );
        constraint_show_number=findViewById( R.id.constraint_show_number );
        constraintLayout_main_study=findViewById( R.id.constraintLayout_main_study );
        constraintLayout_header_study=findViewById( R.id.constraintLayout_header_study );
        coordinatorLayout=findViewById( R.id.coordinatorLayout );
        button_slide=findViewById( R.id.button_slide );
        linear_answer_guide=findViewById( R.id.linear_answer_guide );
        linear_question_guide=findViewById( R.id.linear_question_guide );

        text_start_studyChallenge=findViewById( R.id.text_start_studyChallenge );
        text_question_study=findViewById( R.id.text_question_study );
        current_question_number=findViewById( R.id.current_question_number );
        all_question_number=findViewById( R.id.all_question_number );

        totalTime_study = findViewById(R.id.totalTime_study);
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
        if(slideIsOpen){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            slideIsOpen=false;
            button_slide.setImageResource(R.drawable.up);

        }
       else{
            WarningExiteMachDialog dialog=new WarningExiteMachDialog( StudyChallengeActivity.this );
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        }

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext( CalligraphyContextWrapper.wrap(newBase));
    }
}

