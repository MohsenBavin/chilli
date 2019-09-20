package com.bavin.mohsen.backnardeban.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bavin.mohsen.backnardeban.Classes.Employee;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.APIRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.ApiIntarfaceRetro;
import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.GetCurriculumLessons;
import com.bavin.mohsen.backnardeban.R;
import com.bavin.mohsen.backnardeban.StudyChallengeActivity;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SelectCurriculumFragment extends Fragment {

    private android.support.v7.widget.RecyclerView recycle_curriculum;
    private ArrayList<Employee> employees = new ArrayList<>();
    private Button button_start_study;

    public SelectCurriculumFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate( R.layout.fragment_select_curriculum, container, false );
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        recycle_curriculum=view.findViewById( R.id.recycle_curriculum );
        button_start_study=view.findViewById( R.id.button_start_study );
        MultiAdapter adapter = new MultiAdapter(getActivity(),employees);


        String level= Hawk.get( "level" );
        String field=Hawk.get( "field" );
        ApiIntarfaceRetro apiIntarfaceRetro= APIRetro.getAPI().create( ApiIntarfaceRetro.class );
        Call<List<GetCurriculumLessons>> getCurriculumLessons=apiIntarfaceRetro.getCurriculumLessons( level,field );
        getCurriculumLessons.enqueue( new Callback<List<GetCurriculumLessons>>() {
            @Override
            public void onResponse(Call<List<GetCurriculumLessons>> call, Response<List<GetCurriculumLessons>> response) {

                employees = new ArrayList<>();
                for (int i = 0; i < response.body().size(); i++) {
                    Employee employee = new Employee();
                    employee.setLessons(response.body().get( i ));
                    employees.add(employee);
                }
                adapter.setEmployees(employees);
              //  Toast.makeText( getActivity(),""+employees.size(),Toast.LENGTH_SHORT ).show();

                recycle_curriculum.setAdapter(adapter);
                recycle_curriculum.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            }

            @Override
            public void onFailure(Call<List<GetCurriculumLessons>> call, Throwable t) {
               // Toast.makeText( getActivity(),"onResponse",Toast.LENGTH_SHORT ).show();

            }
        } );



        button_start_study.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getSelected().size() > 0) {
                    StringBuilder stringBuilderBookTitle = new StringBuilder();
                    StringBuilder stringBuilderTopic = new StringBuilder();
                    for (int i = 0; i < adapter.getSelected().size(); i++) {
                        //***************************************************************//
                        stringBuilderBookTitle.append(adapter.getSelected().get(i).getLessons().getApiBookTitle());
                        stringBuilderTopic.append(adapter.getSelected().get(i).getLessons().getApiTopic());

                    }
                   // Toast.makeText(getActivity(),stringBuilderBookTitle.toString(),Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent( getActivity(), StudyChallengeActivity.class );
                    intent.putExtra( "totalTime",250 );
                    startActivity( intent );
                    getActivity().finish();
                } else {
                    //showToast("No Selection");
                }
            }
        } );



    }

    //************************************RecyclerView.Adapter***********************************************//
    public class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.MultiViewHolder> {

        private Context context;
        private ArrayList<Employee> employees;

        public MultiAdapter(Context context, ArrayList<Employee> employees) {
            this.context = context;
            this.employees = employees;
        }

        public void setEmployees(ArrayList<Employee> employees) {
            this.employees = new ArrayList<>();
            this.employees = employees;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_select_lessons_study, viewGroup, false);
            return new MultiViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MultiViewHolder multiViewHolder, int position) {
            multiViewHolder.bind(employees.get(position));
        }

        @Override
        public int getItemCount() {
            return employees.size();
        }

        class MultiViewHolder extends RecyclerView.ViewHolder {

            private TextView txt_lesson_title,text_book_title,txt_lesson_topic,txt_lessone_page;
            private ImageView img_endSelect_chakce_item;

            MultiViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_lesson_title = itemView.findViewById(R.id.txt_lesson_title);
                text_book_title = itemView.findViewById(R.id.text_book_title);
                txt_lesson_topic = itemView.findViewById(R.id.txt_lesson_topic);
                txt_lessone_page = itemView.findViewById(R.id.txt_lessone_page);
                img_endSelect_chakce_item = itemView.findViewById(R.id.img_endSelect_chakce_item);
            }

            void bind(final Employee employee) {
                img_endSelect_chakce_item.setVisibility(employee.isChecked() ? View.VISIBLE : View.GONE);
                text_book_title.setText(employee.getLessons().getApiBookTitle());
                txt_lesson_title.setText(employee.getLessons().getApiLessonTitle());
                txt_lesson_topic.setText(employee.getLessons().getApiTopic());
                int fromPage=employee.getLessons().getApiFromPage();
                int toPage=employee.getLessons().getApiToPage();
                txt_lessone_page.setText( "صفحه: "+""+fromPage+"تا"+""+toPage );

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        employee.setChecked(!employee.isChecked());
                        img_endSelect_chakce_item.setVisibility(employee.isChecked() ? View.VISIBLE : View.GONE);
                    }
                });
            }
        }

        public ArrayList<Employee> getAll() {
            return employees;
        }

        public ArrayList<Employee> getSelected() {
            ArrayList<Employee> selected = new ArrayList<>();
            for (int i = 0; i < employees.size(); i++) {
                if (employees.get(i).isChecked()) {
                    selected.add(employees.get(i));
                }
            }
            return selected;
        }
    }

}
