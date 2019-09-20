package com.bavin.mohsen.backnardeban.Classes.RetrofitClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAnsupdateAvatar {
    @SerializedName( "answer" )
    @Expose
    private String apiAnswer;

    public String getApiAnswer() {
        return apiAnswer;
    }
}
