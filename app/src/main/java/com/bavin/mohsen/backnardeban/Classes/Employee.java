package com.bavin.mohsen.backnardeban.Classes;

import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.GetCurriculumLessons;

import java.io.Serializable;

public class Employee implements Serializable {
    private boolean isChecked = false;
    private GetCurriculumLessons getCurriculumLessons;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    public GetCurriculumLessons getLessons() {
        return getCurriculumLessons;
    }

    public void setLessons(GetCurriculumLessons getCurriculumLessons) {
        this.getCurriculumLessons = getCurriculumLessons;
    }


}