package com.bavin.mohsen.backnardeban.Classes.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bavin.mohsen.backnardeban.R;

import id.yuana.chart.pie.PieChartView;

public class StudyResultsDialog extends Dialog implements View.OnClickListener {
    public Activity activity;
    private Button btn_show_answers;
    private PieChartView pieChart_percent_study;
    TextView text_percent_study,txt_correct_number,txt_incorrect_number,txt_unanswered_number;

    int correctNumber,incorrectNumber,unansweredNumber,totalNumber;

    float correctPercent,incorrectPercent,unansweredPercent;

    int[] percentColors={R.color.colorGreen,R.color.colorRed,R.color.colorGrayEdit};

    public StudyResultsDialog( Activity activity,int correctNumber,int incorrectNumber,
                               int unansweredNumber,int totalNumber) {
        super( activity );
        this.activity = activity;
        this.correctNumber = correctNumber;
        this.incorrectNumber = incorrectNumber;
        this.unansweredNumber = unansweredNumber;
        this.totalNumber = totalNumber;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        setContentView( R.layout.dialg_study_results );
        btn_show_answers=findViewById( R.id.btn_show_answers );
        text_percent_study=findViewById( R.id.text_percent_study );

        txt_correct_number=findViewById( R.id.txt_correct_number );
        txt_incorrect_number=findViewById( R.id.txt_incorrect_number );
        txt_unanswered_number=findViewById( R.id.txt_unanswered_number );

        pieChart_percent_study=findViewById( R.id.pieChart_percent_study );


        txt_correct_number.setText(""+correctNumber);
        txt_incorrect_number.setText(""+incorrectNumber);
        txt_unanswered_number.setText(""+unansweredNumber);

        incorrectPercent=(incorrectNumber*100/totalNumber);
        unansweredPercent=(unansweredNumber*100/totalNumber);
        correctPercent=(correctNumber*100/totalNumber);

        float percent=(((correctNumber*3-incorrectNumber))*100/(totalNumber*3));
        float[] percentPoints={ correctPercent, incorrectPercent, unansweredPercent};
        pieChart_percent_study.setDataPoints(percentPoints );
        pieChart_percent_study.setSliceColor(percentColors);
        //Toast.makeText( activity,""+correctPercent+""+incorrectPercent+""+unansweredPercent,Toast.LENGTH_SHORT ).show();
        text_percent_study.setText(""+percent+"%");

    }

    @Override
    public void onClick(View v) {

    }
}


