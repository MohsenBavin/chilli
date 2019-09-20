package com.bavin.mohsen.backnardeban;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bavin.mohsen.backnardeban.Classes.Dialogs.SettingStudyChallengeDialog;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.APIRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.ApiIntarfaceRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.GetCurriculumLessons;
import com.orhanobut.hawk.Hawk;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectLessonStudyActivity extends AppCompatActivity {
    private RecyclerView recycle_curriculum;
    private Button button_start_study;
    private List<GetCurriculumLessons> curriculumLessons;
    int row_index=-1;
    private boolean clickSound=false;
    String bookTitle,topic;
    MediaPlayer select_sound,accept_selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_select_lesson_study );
        recycle_curriculum=findViewById( R.id.recycle_curriculum );
        button_start_study=findViewById( R.id.button_start_study );
        accept_selected=MediaPlayer.create(SelectLessonStudyActivity.this , R.raw.accept_seslect_study);
        accept_selected.setVolume( 5.0f,5.0f );
        select_sound=MediaPlayer.create(SelectLessonStudyActivity.this , R.raw.click2);
        select_sound.setVolume( 5.0f,5.0f );



        String level= Hawk.get( "level" );
        String field=Hawk.get( "field" );
        ApiIntarfaceRetro apiIntarfaceRetro= APIRetro.getAPI().create( ApiIntarfaceRetro.class );
        Call<List<GetCurriculumLessons>> getCurriculumLessons=apiIntarfaceRetro.getCurriculumLessons( level,field );
        getCurriculumLessons.enqueue( new Callback<List<GetCurriculumLessons>>() {
            @Override
            public void onResponse(Call<List<GetCurriculumLessons>> call, Response<List<GetCurriculumLessons>> response) {

                curriculumLessons=response.body();
                //  Toast.makeText( getActivity(),""+employees.size(),Toast.LENGTH_SHORT ).show();
                SelectAdapter selectAdapter=new SelectAdapter( curriculumLessons );
                recycle_curriculum.setAdapter(selectAdapter);
                recycle_curriculum.setLayoutManager(new GridLayoutManager(SelectLessonStudyActivity.this, 3));
                Toast.makeText( SelectLessonStudyActivity.this,"onResponse",Toast.LENGTH_SHORT ).show();

            }

            @Override
            public void onFailure(Call<List<GetCurriculumLessons>> call, Throwable t) {
                Toast.makeText( SelectLessonStudyActivity.this,"onFailure",Toast.LENGTH_SHORT ).show();

            }
        } );



        button_start_study.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(row_index!=-1){
                    SettingStudyChallengeDialog okDialog= new SettingStudyChallengeDialog
                            (SelectLessonStudyActivity.this,bookTitle,topic);

                   if(clickSound) accept_selected.start();
                    okDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    okDialog.setCanceledOnTouchOutside(false);
                    okDialog.show();
                }

                }

        } );

    }

    public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.MultiViewHolder> {

        private List<GetCurriculumLessons> curriculumLesson=new ArrayList<>(  );

        private SelectAdapter( List<GetCurriculumLessons> curriculumLesson) {
            this.curriculumLesson = curriculumLesson;
        }

        @NotNull
        @Override
        public MultiViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_select_lessons_study, viewGroup, false);
            return new MultiViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MultiViewHolder multiViewHolder, int position) {
            multiViewHolder.text_book_title.setText(curriculumLesson.get( position ).getApiBookTitle());
            multiViewHolder.txt_lesson_title.setText(curriculumLesson.get( position ).getApiLessonTitle());
            multiViewHolder. txt_lesson_topic.setText(curriculumLesson.get( position ).getApiTopic());
            int fromPage=curriculumLesson.get( position ).getApiFromPage();
            int toPage=curriculumLesson.get( position ).getApiToPage();
            multiViewHolder.txt_lessone_page.setText( "صفحه: "+""+fromPage+"تا"+""+toPage );
            multiViewHolder.constrain_lesson_data.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(clickSound) select_sound.start();
                    row_index =position;
                    bookTitle=curriculumLesson.get( position ).getApiBookTitle();
                    topic=curriculumLesson.get( position ).getApiTopic();
                    button_start_study.setBackground( getResources().getDrawable(R.drawable.shape_red ) );
                    notifyDataSetChanged();
                }
            } );

            if(row_index==position){
                multiViewHolder.img_endSelect_chakce_item.setVisibility( View.VISIBLE );

            }
            else
            {

                multiViewHolder.img_endSelect_chakce_item.setVisibility( View.GONE );
            }
        }

        @Override
        public int getItemCount() {
            return curriculumLesson.size();
        }

        class MultiViewHolder extends RecyclerView.ViewHolder {

            private TextView txt_lesson_title,text_book_title,txt_lesson_topic,txt_lessone_page;
            private ImageView img_endSelect_chakce_item;
            private ConstraintLayout constrain_lesson_data;

            MultiViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_lesson_title = itemView.findViewById(R.id.txt_lesson_title);
                text_book_title = itemView.findViewById(R.id.text_book_title);
                txt_lesson_topic = itemView.findViewById(R.id.txt_lesson_topic);
                txt_lessone_page = itemView.findViewById(R.id.txt_lessone_page);
                img_endSelect_chakce_item = itemView.findViewById(R.id.img_endSelect_chakce_item);
                constrain_lesson_data = itemView.findViewById(R.id.constrain_lesson_data);
            }

        }



    }

    @Override
    protected void onStart() {
        super.onStart();
        String sound= Hawk.get( "sound" );
        if (sound.equals( "on" )) {
            clickSound=true;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity( new Intent( SelectLessonStudyActivity.this,MainActivity.class ) );
        finish();
    }
}
