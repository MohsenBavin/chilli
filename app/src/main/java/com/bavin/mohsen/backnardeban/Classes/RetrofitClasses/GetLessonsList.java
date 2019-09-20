package com.bavin.mohsen.backnardeban.Classes.RetrofitClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetLessonsList {
    @SerializedName( "name" )
    @Expose
    private String apiName;


    public String getApiName() {
        return apiName;
    }
}
