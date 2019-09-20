package com.bavin.mohsen.backnardeban.Classes.RetrofitClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetLoginPhoneRetro {
    @SerializedName( "answer" )
    @Expose
    private String apiAnswer;

    @SerializedName( "username" )
    @Expose
    private String apiUserName;


    @SerializedName( "level" )
    @Expose
    private String apiLevel;


    @SerializedName( "field" )
    @Expose
    private String apiField;


    @SerializedName( "avatar" )
    @Expose
    private String apiAvatar;


    @SerializedName( "diamond" )
    @Expose
    private int apiDiamond;


    @SerializedName( "point" )
    @Expose
    private int apiPoint;


    @SerializedName( "zone" )
    @Expose
    private String apiZone;

    @SerializedName( "state" )
    @Expose
    private String apiState;

    @SerializedName( "timeFilm" )
    @Expose
    private String apiTimeFilm;

    public String getApiAnswer() {
        return apiAnswer;
    }

    public String getApiUserName() {
        return apiUserName;
    }

    public String getApiLevel() {
        return apiLevel;
    }

    public String getApiField() {
        return apiField;
    }

    public String getApiAvatar() {
        return apiAvatar;
    }

    public int getApiDiamond() {
        return apiDiamond;
    }

    public int getApiPoint() {
        return apiPoint;
    }

    public String getApiZone() {
        return apiZone;
    }

    public String getApiState() {
        return apiState;
    }

    public String getApiTimeFilm() {
        return apiTimeFilm;
    }
}
