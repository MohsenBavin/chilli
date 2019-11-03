package com.bavin.mohsen.backnardeban.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bavin.mohsen.backnardeban.R;
import com.orhanobut.hawk.Hawk;

public class ShowQuestionmultipleFragment extends Fragment implements View.OnClickListener{

    String question,firstOption,secondOption,thirdOption,fourthOption,answerQuestion ;
    int answer,myAnswer,selectedOption=0;
    String challengeState;
    TextView text_quOne_study,text_quTwo_study,text_quthree_study,text_qufour_study;
    TextView text_question_m_study;
    ConstraintLayout constraint_quOne_study,constraint_quthree_study,constraint_qutfour_study,constraint_quTwo_study;
    MediaPlayer true_sound;
    OnHeadlineSelectedListener mCallback;

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
            mCallback = (OnHeadlineSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public static ShowQuestionmultipleFragment newFragment
            (String challengeState, String question,String firstOption,String secondOption,
             String thirdOption,String fourthOption,int answer,int myAnswer)
    {
        ShowQuestionmultipleFragment newFragmentIntc=new ShowQuestionmultipleFragment();
        Bundle args=new Bundle();
        args.putString("challengeState",challengeState);
        args.putString("question",question);
        args.putString("firstOption",firstOption);
        args.putString("secondOption",secondOption);
        args.putString("thirdOption",thirdOption);
        args.putString("fourthOption",fourthOption);
        args.putInt( "answer",answer);
        args.putInt( "myAnswer",myAnswer);
        newFragmentIntc.setArguments(args);
        return newFragmentIntc;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.challengeState=getArguments().getString("challengeState");
        this.question=getArguments().getString("question");
        this.firstOption=getArguments().getString("firstOption");
        this.secondOption=getArguments().getString("secondOption");
        this.thirdOption=getArguments().getString("thirdOption");
        this.fourthOption=getArguments().getString("fourthOption");

        this.myAnswer=getArguments().getInt("myAnswer");
        this.answer=getArguments().getInt("answer");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate( R.layout.fragment_show_questionmultiple, container, false );
        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        text_question_m_study=view.findViewById( R.id.text_question_m_study );
        text_quOne_study=view.findViewById( R.id.text_quOne_study );
        text_quTwo_study=view.findViewById( R.id.text_quTwo_study );
        text_quthree_study=view.findViewById( R.id.text_quthree_study );
        text_qufour_study=view.findViewById( R.id.text_qufour_study );
        constraint_quOne_study=view.findViewById( R.id.constraint_quOne_study );
        constraint_quTwo_study=view.findViewById( R.id.constraint_quTwo_study );
        constraint_quthree_study=view.findViewById( R.id.constraint_quthree_study );
        constraint_qutfour_study=view.findViewById( R.id.constraint_qutfour_study );

        text_quOne_study.setText("    "+firstOption);
        text_quTwo_study.setText("    "+secondOption );
        text_quthree_study.setText("    "+thirdOption );
        text_qufour_study.setText( "    "+fourthOption );
        text_question_m_study.setText( question );


        constraint_quOne_study.setOnClickListener(this);
        constraint_quTwo_study.setOnClickListener(this);
        constraint_quthree_study.setOnClickListener(this);
        constraint_qutfour_study.setOnClickListener(this);




    }

    private void showAnswer() {
        switch (myAnswer){
            case 1:
                if(myAnswer!=answer) {
                    constraint_quOne_study.setBackgroundResource( R.drawable.question_false_shape );
                }

                break;
            case 2:
                if(myAnswer!=answer) {
                    constraint_quTwo_study.setBackgroundResource( R.drawable.question_false_shape );
                }

                break;
            case 3:
                if(myAnswer!=answer) {
                    constraint_quthree_study.setBackgroundResource( R.drawable.question_false_shape );
                }

                break;
            case 4:
                if(myAnswer!=answer) {
                    constraint_qutfour_study.setBackgroundResource( R.drawable.question_false_shape );
                }

        }

        switch (answer){
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
    }

    @Override
    public void onClick(View v) {
        if(challengeState.equals( "inChallenge" )){
            if(Hawk.get( "sound" ).equals( "on" )) {
                constraint_quOne_study.setBackgroundResource( R.drawable.options_questions_shape );
                constraint_quTwo_study.setBackgroundResource( R.drawable.options_questions_shape );
                constraint_quthree_study.setBackgroundResource( R.drawable.options_questions_shape );
                constraint_qutfour_study.setBackgroundResource( R.drawable.options_questions_shape );
                true_sound= MediaPlayer.create(getActivity(), R.raw.click2);
                true_sound.setVolume( 5.0f,5.0f );
                true_sound.start();
                if(selectedOption==0){
                    if ((v.getId() == R.id.constraint_quOne_study))
                    {constraint_quOne_study.setBackgroundResource( R.drawable.question_selected_shape );
                        selectedOption=1;}
                    if((v.getId()==R.id.constraint_quTwo_study)){
                        constraint_quTwo_study.setBackgroundResource( R.drawable.question_selected_shape );
                        selectedOption=2;}
                    if((v.getId()==R.id.constraint_quthree_study)){
                        constraint_quthree_study.setBackgroundResource( R.drawable.question_selected_shape );
                        selectedOption=3;}
                    if((v.getId()==R.id.constraint_qutfour_study)){
                        constraint_qutfour_study.setBackgroundResource( R.drawable.question_selected_shape );
                        selectedOption=4;}
                }
                else {
                    if ((v.getId() == R.id.constraint_quOne_study)) {
                        if (selectedOption != 1) {
                            constraint_quOne_study.setBackgroundResource( R.drawable.question_selected_shape );
                            selectedOption = 1;
                        } else {
                            selectedOption = 0;
                        }
                    }
                    if ((v.getId() == R.id.constraint_quTwo_study)) {
                        if (selectedOption != 2) {
                            constraint_quTwo_study.setBackgroundResource( R.drawable.question_selected_shape );
                            selectedOption = 2;
                        } else {
                            selectedOption = 0;
                        }
                    }
                    if ((v.getId() == R.id.constraint_quthree_study)) {
                        if (selectedOption != 3) {
                            constraint_quthree_study.setBackgroundResource( R.drawable.question_selected_shape );
                            selectedOption = 3;
                        } else {
                            selectedOption = 0;
                        }
                    }
                    if ((v.getId() == R.id.constraint_qutfour_study)) {
                        if (selectedOption != 4) {
                            constraint_qutfour_study.setBackgroundResource( R.drawable.question_selected_shape );
                            selectedOption = 4;
                        } else {
                            selectedOption = 0;
                        }
                    }
                }
            }
            mCallback.onArticleSelected(selectedOption);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText( getContext(),challengeState,Toast.LENGTH_SHORT ).show();
        if(challengeState.equals( "inChallenge" )){
            switch (selectedOption){
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
                default:
                    break;
            }
        }
        if(challengeState.equals( "inAnswer" )){
            showAnswer();

        }


    }
}

