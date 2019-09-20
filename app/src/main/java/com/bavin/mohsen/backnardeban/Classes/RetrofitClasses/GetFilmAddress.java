package com.bavin.mohsen.backnardeban.Classes.RetrofitClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetFilmAddress {
    @SerializedName( "filmAddress" )
    @Expose
    private String apiFilmAddress;

    public String getApiFilmAddress() {
        return apiFilmAddress;
    }
}
