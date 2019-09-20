package com.bavin.mohsen.backnardeban.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bavin.mohsen.backnardeban.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectBookLessonFragment extends Fragment {


    public SelectBookLessonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_select_book_lesson, container, false );
    }

}
