package com.bavin.mohsen.backnardeban.Classes.RetrofitClasses;

import com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.Model.ThirdExperimentNum;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public  interface ApiIntarfaceRetro {



    @GET("loginPhone.php")
    Call<GetLoginPhoneRetro> loginPhoneCall(@Query("phone") String phone);

    @GET("registerLogin.php")
    Call<GetRegisterLoginDataRetro> loginRegisterCall(
            @Query("phone") String phone
           , @Query("username") String username
           , @Query("level") String level
           , @Query("field") String field
           , @Query("avatar") String avatar
            , @Query("zone") String zone
            , @Query("state") String state
    );


    @GET("login.php")
    Call<GetLoginDataRetro> loginCall(@Query("username") String UserName);

    @GET("chekPhoneState.php")
    Call<GetPhoneStateRetro> checkPhoneCall(@Query("phoneNum") String phoneNum);

    @GET("filmAddress.php")
    Call<GetFilmAddress> showFilmCall();

    @GET("updateFilmParameters.php")
    Call<GetAnsAddDiamond> updateDiamond(
            @Query("phone") String username
            , @Query("diamond") int diamond
            , @Query("timeFilm") String timeFilm);

    @GET("avatarImages.php")
    Call<List<GetAvatarsAddress>> showAvatarCall();

    @GET("updateAvatar.php")
    Call<GetAnsupdateAvatar> updateAvatar(
            @Query("phone") String username
            , @Query("avatar") String avatar);

    @GET("lessonsList.php")
    Call<List<GetLessonsList>> getLessons(
            @Query("phone") String phone
            , @Query("level") String level
            , @Query("field") String field);

    @GET("numberOfSelectLessons.php")
    Call<ThirdExperimentNum> getNumSelect(
            @Query("phone") String phone
            , @Query("level") String level
            , @Query("field") String field);

    @GET("updateUserData.php")
    Call<GetUpdateLoginDataRetro> updateUserData(
            @Query("phone") String phone
            , @Query("username") String username
            , @Query("level") String level
            , @Query("field") String field
            , @Query("avatar") String avatar
            , @Query("zone") String zone
            , @Query("state") String state
    );

    @GET("addUserRedyCahnce.php")
    Call<GetAnsReadychance> addStudyReady(
            @Query("phone") String phone
            , @Query("level") String level
            , @Query("field") String field
            , @Query("study") String study
            , @Query("lesson") String lesson
            , @Query("number") int number);


    @GET("selectCurriculum.php")
    Call<List<GetCurriculumLessons>> getCurriculumLessons(
             @Query("level") String level
            , @Query("field") String field);

}

