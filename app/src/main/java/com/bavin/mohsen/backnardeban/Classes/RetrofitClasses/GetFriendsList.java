package com.bavin.mohsen.backnardeban.Classes.RetrofitClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetFriendsList {
    @SerializedName( "username" )
    @Expose
    private String apiUsername;

    @SerializedName( "level" )
    @Expose
    private String apiLevel;

    @SerializedName( "field" )
    @Expose
    private String apiField;

    @SerializedName( "avatar" )
    @Expose
    private String apiAvatar;



    @SerializedName( "tokenId" )
    @Expose
    private String apiTokenId;

    public String getApiUsername() {
        return apiUsername;
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
    public String getApiTokenId() {
        return apiTokenId;
    }
}
