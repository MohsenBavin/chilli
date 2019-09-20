package com.bavin.mohsen.backnardeban.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bavin.mohsen.backnardeban.R;



import java.util.ArrayList;
import java.util.List;

import im.dacer.androidcharts.PieHelper;
import im.dacer.androidcharts.PieView;


public class PercentFragment extends Fragment {


    public PercentFragment() {
    }

    RecyclerView recyclerViewPercent;

    int scrollDist = 0;
    boolean isVisible = true;
    static final float MINIMUM = 25;
    ConstraintLayout constraintLayout_first_chart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate( R.layout.fragment_percent, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        recyclerViewPercent=view.findViewById( R.id.recyclerView_percent_data );
        constraintLayout_first_chart=view.findViewById( R.id.constraintLayout_first_chart );

        List<String> step_item=new ArrayList<>(  );
        step_item.add( "دوره اول" );
        step_item.add( "دوره دوم" );
        step_item.add( "دوره سوم" );
        step_item.add( "دوره چهارم" );
        step_item.add( "دوره پنجم" );
        step_item.add( "دوره ششم" );
        step_item.add( "دوره هفتم" );
        step_item.add( "دوره هشتم" );
        step_item.add( "دوره نهم" );
        step_item.add( "دوره دهم" );


        float[] percentFloat1={ 75F, 10F, 20F};
        float[] percentFloat2={ 70F, 20F, 10F};
        float[] percentFloat3={ 45F, 25F, 30F};
        float[] percentFloat4={ 80F, 10F, 10F};
        float[] percentFloat5={ 30F, 40F, 30F};
        float[] percentFloat6={ 35F, 35F, 30F};
        float[] percentFloat7={ 60F, 40F, 0F};
        float[] percentFloat8={ 55F, 10F, 35F};
        float[] percentFloat9={ 90F, 5F, 5F};
        float[] percentFloat10={ 10F, 40F, 50F};

        List<float[]> percentPoints_item=new ArrayList<>(  );
        percentPoints_item.add( percentFloat1 );
        percentPoints_item.add( percentFloat2 );
        percentPoints_item.add( percentFloat3 );
        percentPoints_item.add( percentFloat4 );
        percentPoints_item.add( percentFloat5 );
        percentPoints_item.add( percentFloat6 );
        percentPoints_item.add( percentFloat7 );
        percentPoints_item.add( percentFloat8 );
        percentPoints_item.add( percentFloat9 );
        percentPoints_item.add( percentFloat10);



        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<PieHelper>();
        pieHelperArrayList.add(new PieHelper(20, Color.parseColor("#156F19")));
        pieHelperArrayList.add(new PieHelper(50,Color.parseColor("#F33631")));
        pieHelperArrayList.add(new PieHelper(30,Color.parseColor("#90A4AE")));
        PieView pieView = view.findViewById(R.id.pie_view);


        ArrayList<PieHelper> pieHelperArrayList2 = setPresent( percentPoints_item.get( 0 ) );

        pieView.setDate(pieHelperArrayList2);
        //pieView.selectedPie(0); //optional
        //pieView.setOnPieClickListener(listener); //optional
        pieView.showPercentLabel(true);//optional
//*************************************************************************************************
        recyclerViewPercent.setScrollbarFadingEnabled( false );

        SetPercentRecycler adapterAnalyzeDataList =new SetPercentRecycler(percentPoints_item,step_item);
        recyclerViewPercent.setAdapter( adapterAnalyzeDataList );
        recyclerViewPercent.setLayoutManager(new GridLayoutManager(getContext(), 3));
        /*LinearLayoutManager horizontalLayoutManagaer =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerAnalyzeData.setLayoutManager(horizontalLayoutManagaer);*/

    }



    public ArrayList<PieHelper> setPresent (float[] per){
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<PieHelper>();
        pieHelperArrayList.add(new PieHelper(per[0], Color.parseColor("#F33631")));
        pieHelperArrayList.add(new PieHelper(per[1],Color.parseColor("#156F19")));
        pieHelperArrayList.add(new PieHelper(per[2],Color.parseColor("#90A4AE")));

         return pieHelperArrayList;
    }

    //******************* recycler *************
    public class SetPercentRecycler extends RecyclerView.Adapter<SetPercentRecycler.MyPercentViewHolder>{
        List<float[]> percentPoints_item=new ArrayList<>(  );
        List<String> step_item=new ArrayList<>(  );


        public SetPercentRecycler(List<float[]> percentPoints_item,List<String> step_item){
            this.percentPoints_item=percentPoints_item;
            this.step_item=step_item;

        }
        @NonNull
        @Override
        public MyPercentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from( viewGroup.getContext() )
                    .inflate( R.layout.percent_data_layout_item,viewGroup,false );
            MyPercentViewHolder holder;
            holder=new MyPercentViewHolder( view );
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyPercentViewHolder myPercentViewHolder, int i) {
            myPercentViewHolder.text_step_item.setText( step_item.get( i ) );

            ArrayList<PieHelper> pieHelperArrayList2 = setPresent( percentPoints_item.get( i ) );

           myPercentViewHolder.pie_view_item.setDate(pieHelperArrayList2);
            // pieView.selectedPie(2); //optional
            //pieView.setOnPieClickListener(listener); //optional
            myPercentViewHolder.pie_view_item.showPercentLabel(true);//optional

        }

        @Override
        public int getItemCount() {
            return step_item.size();
        }

        public class MyPercentViewHolder extends RecyclerView.ViewHolder{

            TextView text_step_item;
            PieView pie_view_item;
            public MyPercentViewHolder(@NonNull View itemView) {
                super( itemView );
                text_step_item=itemView.findViewById( R.id.text_step_item );
                pie_view_item=itemView.findViewById( R.id.pie_view_item );

            }
        }
    }


//This is taken from a project of mine, it scrolls a Layout up if a snackbar shows up.


}
