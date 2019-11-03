package com.bavin.mohsen.backnardeban.fragments;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bavin.mohsen.backnardeban.R;
import com.orhanobut.hawk.Hawk;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class ShowLinearChanceFragment extends Fragment implements View.OnClickListener {

    String question,firstOption,secondOption,thirdOption,fourthOption,answerQuestion ;
    int answer,selectedOption=0,time;
    Timer timerAnswer;
    boolean allowAnswer=true;

    TextView text_question_study,text_quOne_study_l,text_quTwo_study_l,text_quthree_study_l,text_qufour_study_l;
    ConstraintLayout constraint_quOne_study_l,constraint_quthree_study_l,constraint_qutfour_study_l,constraint_quTwo_study_l;
    MediaPlayer true_sound;
    ShowLinearChanceFragment.OnHeadlineSelectedListener mCallback;




    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        void onArticleSelected(int position);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (ShowLinearChanceFragment.OnHeadlineSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public static ShowLinearChanceFragment newChanceFragment
            ( String question,String firstOption,String secondOption,
             String thirdOption,String fourthOption,int answer,int time)
    {
        ShowLinearChanceFragment newFragmentIntc=new ShowLinearChanceFragment();
        Bundle args=new Bundle();
        args.putString("question",question);
        args.putString("firstOption",firstOption);
        args.putString("secondOption",secondOption);
        args.putString("thirdOption",thirdOption);
        args.putString("fourthOption",fourthOption);
        args.putInt( "answer",answer);
        args.putInt( "time",time);
        newFragmentIntc.setArguments(args);
        return newFragmentIntc;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.question=getArguments().getString("question");
        this.firstOption=getArguments().getString("firstOption");
        this.secondOption=getArguments().getString("secondOption");
        this.thirdOption=getArguments().getString("thirdOption");
        this.fourthOption=getArguments().getString("fourthOption");
        this.answer=getArguments().getInt("answer");
        this.time=getArguments().getInt("time");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate( R.layout.fragment_show_question_linear, container, false );
        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        text_question_study=view.findViewById( R.id.text_question_study );
        text_quOne_study_l=view.findViewById( R.id.text_quOne_study_l );
        text_quTwo_study_l=view.findViewById( R.id.text_quTwo_study_l );
        text_quthree_study_l=view.findViewById( R.id.text_quthree_study_l );
        text_qufour_study_l=view.findViewById( R.id.text_qufour_study_l );

        constraint_quOne_study_l=view.findViewById( R.id.constraint_quOne_study_l );
        constraint_quTwo_study_l=view.findViewById( R.id.constraint_quTwo_study_l );
        constraint_quthree_study_l=view.findViewById( R.id.constraint_quthree_study_l );
        constraint_qutfour_study_l=view.findViewById( R.id.constraint_qutfour_study_l );
        constraint_quOne_study_l.setOnClickListener(this);
        constraint_quTwo_study_l.setOnClickListener(this);
        constraint_quthree_study_l.setOnClickListener(this);
        constraint_qutfour_study_l.setOnClickListener(this);

        text_question_study.setText( question );
        text_quOne_study_l.setText( "    "+firstOption );
        text_quTwo_study_l.setText( "    "+secondOption );
        text_quthree_study_l.setText("    "+ thirdOption );
        text_qufour_study_l.setText( "    "+fourthOption );
        timerAnswer = new Timer();

        timerAnswer.schedule( new TimerTask() {
            @Override
            public void run() {
                Objects.requireNonNull( getActivity() ).runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        time-=1000;

                        if (time <= 0) {
                            allowAnswer=false;
                            checkAnswer(0);
                            timerAnswer.cancel();


                        }


                    }
                } );
            }
        }, 0, 1000 );


    }

    @Override
    public void onClick(View v) {
        if(allowAnswer){
            allowAnswer=false;
            timerAnswer.cancel();
            if ((v.getId() == R.id.constraint_quOne_study_l))
            { selectedOption=1;}
            if((v.getId()==R.id.constraint_quTwo_study_l)){
                selectedOption=2;}
            if((v.getId()==R.id.constraint_quthree_study_l)){
                selectedOption=3;}
            if((v.getId()==R.id.constraint_qutfour_study_l)){
                selectedOption=4;}
            checkAnswer(selectedOption);

        }

    }

    private void checkAnswer(int selected) {
        constraint_quOne_study_l.setVisibility( View.GONE );
        constraint_quTwo_study_l.setVisibility( View.GONE );
        constraint_quthree_study_l.setVisibility( View.GONE );
        constraint_qutfour_study_l.setVisibility( View.GONE );
        mCallback.onArticleSelected(selected);

        switch (selected) {
            case 1:
        if(selected!=answer) {
            constraint_quOne_study_l.setVisibility( View.VISIBLE );
            constraint_quOne_study_l.setBackgroundResource( R.drawable.question_false_shape );
        }

        break;
        case 2:
        if(selected!=answer) {
            constraint_quTwo_study_l.setVisibility( View.VISIBLE );
            constraint_quTwo_study_l.setBackgroundResource( R.drawable.question_false_shape );
        }

        break;
        case 3:
        if(selected!=answer) {
            constraint_quthree_study_l.setVisibility( View.VISIBLE );
            constraint_quthree_study_l.setBackgroundResource( R.drawable.question_false_shape );
        }

        break;
        case 4:
        if(selected!=answer) {
            constraint_qutfour_study_l.setVisibility( View.VISIBLE );
            constraint_qutfour_study_l.setBackgroundResource( R.drawable.question_false_shape );
        }
        break;
         default:
             break;

    }

        switch (answer){
        case 1:
            constraint_quOne_study_l.setVisibility( View.VISIBLE );
            constraint_quOne_study_l.setBackgroundResource( R.drawable.question_true_shape );


            break;
        case 2:
            constraint_quTwo_study_l.setVisibility( View.VISIBLE );
            constraint_quTwo_study_l.setBackgroundResource( R.drawable.question_true_shape );


            break;
        case 3:
            constraint_quthree_study_l.setVisibility( View.VISIBLE );
            constraint_quthree_study_l.setBackgroundResource( R.drawable.question_true_shape );

            break;
        case 4:
            constraint_qutfour_study_l.setVisibility( View.VISIBLE );
            constraint_qutfour_study_l.setBackgroundResource( R.drawable.question_true_shape );
break;
            default:
                break;

    }


    }

}
