package com.bavin.mohsen.backnardeban.Classes.RetrofitClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetStudyQuestions {
    public String getApiQuestions() {
        return apiQuestions;
    }

    public String getApiFirstOptions() {
        return apiFirstOptions;
    }

    public String getApiSecondOptions() {
        return apiSecondOptions;
    }

    public String getApiThirdOptions() {
        return apiThirdOptions;
    }

    public String getApiFourthOptions() {
        return apiFourthOptions;
    }

    public int getApiAnswerQuestions() {
        return apiAnswerQuestions;
    }

    public String getApiQuestionsShow() {
        return apiQuestionsShow;
    }


    @SerializedName( "questions" )
    @Expose
    private String apiQuestions;

    @SerializedName( "firstOptions" )
    @Expose
    private String apiFirstOptions;

    @SerializedName( "secondOptions" )
    @Expose
    private String apiSecondOptions;

    @SerializedName( "thirdOptions" )
    @Expose
    private String apiThirdOptions;

    @SerializedName( "fourthOptions" )
    @Expose
    private String apiFourthOptions;

    @SerializedName( "answerQuestions" )
    @Expose
    private int apiAnswerQuestions;

    @SerializedName( "questionsShow" )
    @Expose
    private String apiQuestionsShow;



}
