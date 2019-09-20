package com.bavin.mohsen.backnardeban.fragments;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bavin.mohsen.backnardeban.Classes.Dialogs.WarningSelectChanceDialog;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.APIRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.ApiIntarfaceRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.GetAnsReadychance;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.GetLessonsList;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.Model.ThirdExperimentNum;
import com.bavin.mohsen.backnardeban.R;
import com.bavin.mohsen.backnardeban.WaitChanceMachActivity;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectChanceStudyFragment extends Fragment {

    private RecyclerView recycle_select_chance_study;
    List<GetLessonsList> eduChance=new ArrayList<>(  );
    ThirdExperimentNum numSelect;
    private ProgressBar progress_wait;
    private Handler handler = new Handler();
    int status;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate( R.layout.fragment_select_study, container, false );

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        recycle_select_chance_study=view.findViewById( R.id.recycle_select_chance_study );
        progress_wait=view.findViewById( R.id.progress_wait );
        Hawk.init(getContext()).build();


      new Thread(new Runnable() {
            @Override
            public void run() {
                while (status <= 100) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progress_wait.setProgress(status);
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    status++;
                }
            }
        }).start();




        getLssonsNames();
    }

    private void getLssonsNames() {
        String phone=Hawk.get( "phone" );
        String level=Hawk.get( "level" );
        String field=Hawk.get( "field" );
        ApiIntarfaceRetro apiIntarfaceRetro= APIRetro.getAPI().create( ApiIntarfaceRetro.class );
        Call<List<GetLessonsList>> lessonsCall=apiIntarfaceRetro.getLessons( phone,level,field );
        lessonsCall.enqueue( new Callback<List<GetLessonsList>>() {
            @Override
            public void onResponse(Call<List<GetLessonsList>> call, Response<List<GetLessonsList>> response) {
                eduChance=response.body() ;
                getNumSelect(eduChance);
            }

            @Override
            public void onFailure(Call<List<GetLessonsList>> call, Throwable t) {
                // Toast.makeText( getContext(), "onFailure",Toast.LENGTH_LONG ).show();
                if (t != null) {
                    Log.e("OnFailure",""+ t.getMessage()  );
                    Toast.makeText(getActivity(), "Failed! OnFailure: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        } );
    }

    private void getNumSelect(List<GetLessonsList> eduChance) {
        this.eduChance=eduChance;
        String phone=Hawk.get( "phone" );
        String level=Hawk.get( "level" );
        String field=Hawk.get( "field" );
        ApiIntarfaceRetro apiIntarfaceRetro= APIRetro.getAPI().create( ApiIntarfaceRetro.class );
        Call<ThirdExperimentNum> numCall=apiIntarfaceRetro.getNumSelect( phone,level,field );
        numCall.enqueue( new Callback<ThirdExperimentNum>() {
            @Override
            public void onResponse(Call<ThirdExperimentNum> call, Response<ThirdExperimentNum> response) {
                // Toast.makeText( getContext(), eduChance.getLesson(0),Toast.LENGTH_LONG ).show();
                progress_wait.setVisibility( View.GONE );

                numSelect=response.body();
                SelectChanceStudyList adapterSelectChanceStudy =new SelectChanceStudyList(eduChance,numSelect);
                recycle_select_chance_study.setAdapter( adapterSelectChanceStudy );
                LinearLayoutManager horizontalLayoutManagaer =
                        new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycle_select_chance_study.setLayoutManager(horizontalLayoutManagaer);
            }

            @Override
            public void onFailure(Call<ThirdExperimentNum> call, Throwable t) {
                // Toast.makeText( getContext(), "onFailure",Toast.LENGTH_LONG ).show();
                if (t != null) {
                    Log.e("OnFailure2",""+ t.getMessage()  );
                    Toast.makeText(getActivity(), "Failed! OnFailure2: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        } );

    }

    public class SelectChanceStudyList extends RecyclerView.Adapter<SelectChanceStudyList.MySelectViewHolder>{

        List<GetLessonsList> eduChance;
        ThirdExperimentNum number_select;

        public SelectChanceStudyList( List<GetLessonsList> eduChance,ThirdExperimentNum number_select){
            this.eduChance=eduChance;
            this.number_select=number_select;

        }
        @NonNull
        @Override
        public MySelectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from( viewGroup.getContext() )
                    .inflate( R.layout.select_study_item,viewGroup,false );
            MySelectViewHolder holder;
            holder=new MySelectViewHolder( view );
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final MySelectViewHolder mySelectViewHolder, final int i) {
            mySelectViewHolder.text_chance_study_item.setText( eduChance.get( i ).getApiName() );
            String lesson=eduChance.get( i ).getApiName();
            final int numberSelect=numSelect.getNumOfSelect( lesson );

            switch (numberSelect){
                case 0:
                    mySelectViewHolder.cProgres_chance_item.setProgress((34*numberSelect));   // Main Progress
                    mySelectViewHolder.cProgres_chance_item.setSecondaryProgress(100); // Secondary Progress
                    mySelectViewHolder.text_nemberSelect_chance_item.setText( "x"+3);
                    mySelectViewHolder.img_endSelect_chakce_item.setVisibility( View.GONE );

                    break;
                case 1:
                    mySelectViewHolder.cProgres_chance_item.setProgress((34*numberSelect));   // Main Progress
                    mySelectViewHolder.cProgres_chance_item.setSecondaryProgress(100); // Secondary Progress
                    mySelectViewHolder.text_nemberSelect_chance_item.setText( "x"+2);
                    mySelectViewHolder.img_endSelect_chakce_item.setVisibility( View.GONE );

                    break;
                case 2:
                    mySelectViewHolder.cProgres_chance_item.setProgress((34*numberSelect));   // Main Progress
                    mySelectViewHolder.cProgres_chance_item.setSecondaryProgress(100); // Secondary Progress
                    mySelectViewHolder.text_nemberSelect_chance_item.setText( "x"+1);
                    mySelectViewHolder.img_endSelect_chakce_item.setVisibility( View.GONE );

                    break;
                    default:
                        mySelectViewHolder.cProgres_chance_item.setProgress((34*numberSelect));   // Main Progress
                        mySelectViewHolder.cProgres_chance_item.setSecondaryProgress(100); // Secondary Progress
                        mySelectViewHolder.text_nemberSelect_chance_item.setText( "x"+1);
                        mySelectViewHolder.img_endSelect_chakce_item.setVisibility( View.VISIBLE );
                        break;
            }


            mySelectViewHolder.text_chance_study_item.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String lessonEn=numSelect.getNameLesson(eduChance.get( i ).getApiName()  );
                    int number=numSelect.getNumOfSelect( lesson );
                    String study=eduChance.get( i ).getApiName();
                    if (numberSelect>=3){
                        if (Hawk.contains("notSelectChance")) {
                            Hawk.delete("notSelectChance"  );
                            readinessDeclaration(study,lessonEn,number);
                            progress_wait.setVisibility( View.VISIBLE );
                        }
                        else {
                            WarningSelectChanceDialog warnDialog= new WarningSelectChanceDialog
                                    (getActivity(),study,lesson,number);
                            warnDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            warnDialog.setCanceledOnTouchOutside(false);
                            warnDialog.show();
                            CheckBox checkBox= warnDialog.findViewById( R.id.checkBox_dontShow  );
                            warnDialog.findViewById( R.id.btn_ok_continue  ).setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (checkBox.isChecked()){
                                        Hawk.put("notSelectChance","0");
                                    }
                                    warnDialog.dismiss();
                                    readinessDeclaration(study,lessonEn,number);
                                    progress_wait.setVisibility( View.VISIBLE );

                                }
                            } );
                            warnDialog.findViewById( R.id.btn_no_cancel  ).setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    warnDialog.dismiss();
                                }
                            } );
                           // progress_wait.setVisibility( View.VISIBLE );


                        }

                    }
                    else{
                        readinessDeclaration(study,lessonEn,number);
                        progress_wait.setVisibility( View.VISIBLE );

                    }

                }
            } );


        }

        @Override
        public int getItemCount() {
            return eduChance.size();
        }

        public class MySelectViewHolder extends RecyclerView.ViewHolder{
            ProgressBar cProgres_chance_item;
            TextView text_chance_study_item,text_nemberSelect_chance_item;
            ImageView img_endSelect_chakce_item;
            public MySelectViewHolder(@NonNull View itemView) {
                super( itemView );
                cProgres_chance_item=itemView.findViewById( R.id.cProgres_chance_item );
                text_chance_study_item=itemView.findViewById( R.id.text_chance_study_item );
                text_nemberSelect_chance_item=itemView.findViewById( R.id.text_nemberSelect_chance_item );
                img_endSelect_chakce_item=itemView.findViewById( R.id.img_endSelect_chakce_item );

            }
        }

    }

    private void readinessDeclaration(String study,String lesson,int number) {
        number ++;
        String phone=Hawk.get( "phone" );
        String level=Hawk.get( "level" );
        String field=Hawk.get( "field" );
        ApiIntarfaceRetro apiIntarfaceRetro= APIRetro.getAPI().create( ApiIntarfaceRetro.class );
        Call<GetAnsReadychance > readyCall=apiIntarfaceRetro.addStudyReady
                ( phone,level,field,study,lesson,number);
        readyCall.enqueue( new Callback<GetAnsReadychance>() {
            @Override
            public void onResponse(Call<GetAnsReadychance> call, Response<GetAnsReadychance> response) {
                String answer=response.body().getApiAnswer();
                if (answer.equals( "successfully" )){
                    Intent intent=new Intent( getActivity(), WaitChanceMachActivity.class );
                    intent.putExtra( "study",study);
                    startActivity( intent );
                    getActivity().finish();
                }

            }

            @Override
            public void onFailure(Call<GetAnsReadychance> call, Throwable t) {

            }
        } );

    }


}
