package com.bavin.mohsen.backnardeban.Classes.RetrofitClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAvatarsAddress {
    @SerializedName("address")
    @Expose
    private String apiAvatarAddress;

    public String getApiAvatarAddress() {
        return apiAvatarAddress;
    }
}
