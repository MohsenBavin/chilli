package com.bavin.mohsen.backnardeban.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bavin.mohsen.backnardeban.Classes.Adapters.EduSpinnerAdapter;
import com.bavin.mohsen.backnardeban.R;

import java.util.ArrayList;
import java.util.List;

import id.yuana.chart.pie.PieChartView;

public class AnalyzeFragment extends Fragment {
    private String[] edu;

    int scrollDist = 0;
    boolean isVisible = true;
    static final float MINIMUM = 50;
    public AnalyzeFragment() {
    }

  private   PieChartView pieChartView,pieChartView2,pieChartView3;
    private RecyclerView recyclerAnalyzeData;
    ConstraintLayout constraintLayout_first_chart;
   boolean check_ScrollingUp=true;
   Spinner spinner_select_edu;




    float[] percentPoints={ 60F, 30F, 10F};
    int[] percentColors={R.color.colorPrimary,R.color.colorRed,R.color.colorGrayEdit};

    float[] accuracyPoints={ 60F, 40F};
    int[] accuracyolors={R.color.colorBlueDark,R.color.colorWhite};

    float[] speedPints={ 80F, 20F};
    int[] speedColors={R.color.colorYellow,R.color.colorWhite};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate( R.layout.fragment_analyze, container, false );
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        pieChartView=view.findViewById( R.id.pieChart );
        pieChartView2=view.findViewById( R.id.pieChart2 );
        pieChartView3=view.findViewById( R.id.pieChart3 );
        recyclerAnalyzeData=view.findViewById( R.id.recyclerView_analiyze_data );
        constraintLayout_first_chart=view.findViewById( R.id.constraintLayout_first_chart );
        spinner_select_edu=view.findViewById( R.id.spinner_select_edu );


        edu=getResources().getStringArray( R.array.edunames_arrays);

        List<Integer> percents_item=new ArrayList<>(  );
        percents_item.add( 50 );
        percents_item.add( 70 );
        percents_item.add( 45 );
        percents_item.add( 80 );
        percents_item.add( 30 );

        List<String> subject_item=new ArrayList<>(  );
        subject_item.add( "ملکول های اطلاعاتی" );
        subject_item.add( "جریان اطلاعات در یاخته" );
        subject_item.add( "تولید و ساخت مواد آلی" );
        subject_item.add( "مصرف مواد آلی برای تولید انرژی" );
        subject_item.add( "فناوری های زیستی جدید" );



        float[] percentFloat1={ 50F, 30F, 20F};
        float[] percentFloat2={ 70F, 20F, 10F};
        float[] percentFloat3={ 45F, 25F, 30F};
        float[] percentFloat4={ 80F, 10F, 10F};
        float[] percentFloat5={ 30F, 40F, 30F};
        List<float[]> percentPoints_item=new ArrayList<>(  );
        percentPoints_item.add( percentFloat1 );
        percentPoints_item.add( percentFloat2 );
        percentPoints_item.add( percentFloat3 );
        percentPoints_item.add( percentFloat4 );
        percentPoints_item.add( percentFloat5 );


        float[] accuracyFloat1={ 50F, 50F};
        float[] accuracyFloat2={ 80F, 20F};
        float[] accuracyFloat3={ 40F, 60F};
        float[] accuracyFloat4={ 70F, 30F};
        float[] accuracyFloat5={ 60F, 40F};
        List<float[]> accuracyPoints_item=new ArrayList<>(  );
        accuracyPoints_item.add( accuracyFloat1 );
        accuracyPoints_item.add( accuracyFloat2 );
        accuracyPoints_item.add( accuracyFloat3 );
        accuracyPoints_item.add( accuracyFloat4 );
        accuracyPoints_item.add( accuracyFloat5 );

        float[] speedFloat1={ 70F, 30F};
        float[] speedFloat2={ 50F, 50F};
        float[] speedFloat3={ 80F, 20F};
        float[] speedFloat4={ 30F, 70F};
        float[] speedFloat5={ 55F, 45F};
        List<float[]> speedPoints_item=new ArrayList<>(  );
        speedPoints_item.add( speedFloat1 );
        speedPoints_item.add( speedFloat2 );
        speedPoints_item.add( speedFloat3 );
        speedPoints_item.add( speedFloat4 );
        speedPoints_item.add( speedFloat5 );




        pieChartView.setDataPoints(percentPoints);
        //pieChartView.setCenterColor(R.color.colorPink);
        pieChartView.setSliceColor(percentColors);


        pieChartView2.setDataPoints(accuracyPoints);
        pieChartView2.setSliceColor(accuracyolors);

        pieChartView3.setDataPoints(speedPints);
        pieChartView3.setSliceColor(speedColors);

        recyclerAnalyzeData.setScrollbarFadingEnabled( false );
        AnalyzeDataList adapterAnalyzeDataList =new AnalyzeDataList(percentPoints_item,accuracyPoints_item,
                speedPoints_item,percents_item,subject_item );
        recyclerAnalyzeData.setAdapter( adapterAnalyzeDataList );
        LinearLayoutManager horizontalLayoutManagaer =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerAnalyzeData.setLayoutManager(horizontalLayoutManagaer);


//****************************************************************************

        EduSpinnerAdapter adapter=new EduSpinnerAdapter(getContext(),edu);
        spinner_select_edu.setAdapter( adapter );

/*
ArrayAdapter<String> adapterEdu = new ArrayAdapter<String>
        (getContext(),  android.R.layout.simple_spinner_dropdown_item,edu);

        spinner_select_edu.setAdapter(adapterEdu);*/


        spinner_select_edu.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                int fieldPosition=position;
                if (fieldPosition!=0){

                }
                else {
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );


//***********AvatarList RecyclerView ******************************************
    }


public class AnalyzeDataList extends RecyclerView.Adapter <AnalyzeDataList.MyAnalyzeViewHolder>{
    List<float[]> percentPoints_item=new ArrayList<>(  );
    List<float[]> accuracyPoints_item=new ArrayList<>(  );
    List<float[]> speedPoints_item=new ArrayList<>(  );
    List<Integer> percents_item=new ArrayList<>(  );
    List<String> subject_item=new ArrayList<>(  );

    public AnalyzeDataList(List<float[]> percentPoints_item, List<float[]> accuracyPoints_item,
                           List<float[]> speedPoints_item, List<Integer> percents_item,List<String> subject_item){
        this.percentPoints_item=percentPoints_item;
        this.accuracyPoints_item=accuracyPoints_item;
        this.speedPoints_item=speedPoints_item;
        this.percents_item=percents_item;
        this.subject_item=subject_item;

}
    @NonNull
    @Override
    public MyAnalyzeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from( viewGroup.getContext() )
                .inflate( R.layout.analize_data_layout_item,viewGroup,false );
        MyAnalyzeViewHolder holder;
        holder=new MyAnalyzeViewHolder( view );
        return holder;    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return 1;
        else return 2;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAnalyzeViewHolder myAnalyzeViewHolder, int i) {

        myAnalyzeViewHolder.textSubject_item.setText( subject_item.get( i ) );
        myAnalyzeViewHolder.textPercent_item.setText( ""+percents_item.get( i )+"%" );

        myAnalyzeViewHolder.pieChartPercent.setDataPoints(percentPoints_item.get( i ));
        myAnalyzeViewHolder.pieChartPercent.setSliceColor(percentColors);

        myAnalyzeViewHolder.pieChartAccuracy.setDataPoints(accuracyPoints_item.get( i ));
        myAnalyzeViewHolder.pieChartAccuracy.setSliceColor(accuracyolors);

        myAnalyzeViewHolder.pieChartSpeed.setDataPoints(speedPoints_item.get( i ));
        myAnalyzeViewHolder.pieChartSpeed.setSliceColor(speedColors);

    }

    @Override
    public int getItemCount() {
        return percentPoints_item.size();
    }

    public class MyAnalyzeViewHolder extends RecyclerView.ViewHolder{

          PieChartView pieChartPercent,pieChartAccuracy,pieChartSpeed;
          TextView textSubject_item,textPercent_item;

            public MyAnalyzeViewHolder(@NonNull View itemView) {
                super( itemView );
                textSubject_item=itemView.findViewById( R.id.text_subject_item );
                textPercent_item=itemView.findViewById( R.id.text_percent_item );
                pieChartPercent=itemView.findViewById( R.id.pieChart_percent_item );
                pieChartAccuracy=itemView.findViewById( R.id.pieChart_accuracy_item );
                pieChartSpeed=itemView.findViewById( R.id.pieChart_speed_item );
            }
        }
    }


}