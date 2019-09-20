package com.bavin.mohsen.backnardeban.Classes.RetrofitClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCurriculumLessons {
    @SerializedName( "number" )
    @Expose
    private int apiNumber;

    @SerializedName( "date" )
    @Expose
    private String apiDate;

    @SerializedName( "lessonTitle" )
    @Expose
    private String apiLessonTitle;

    @SerializedName( "bookTitle" )
    @Expose
    private String apiBookTitle;

    @SerializedName( "Topic" )
    @Expose
    private String apiTopic;

    @SerializedName( "fromPage" )
    @Expose
    private int apiFromPage;

    @SerializedName( "toPage" )
    @Expose
    private int apiToPage;


    public int getApiNumber() {
        return apiNumber;
    }

    public String getApiDate() {
        return apiDate;
    }

    public String getApiLessonTitle() {
        return apiLessonTitle;
    }

    public String getApiBookTitle() {
        return apiBookTitle;
    }

    public String getApiTopic() {
        return apiTopic;
    }

    public int getApiFromPage() {
        return apiFromPage;
    }

    public int getApiToPage() {
        return apiToPage;
    }
}
