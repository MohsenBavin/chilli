package com.bavin.mohsen.backnardeban.Classes.RetrofitClasses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRetro {
    public static final String BASE_URL="http://moonishop.ir/nardeban/";
    public static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

   /* private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }*/

    //public static final String BASE_URL="https://jsonplaceholder.typicode.com/";
    public static Retrofit myRetrofit=null;

    public static Retrofit getAPI(){
        if (myRetrofit==null){
            myRetrofit=new Retrofit.Builder()
                    .baseUrl( BASE_URL )
                    .addConverterFactory( GsonConverterFactory.create(gson) )
                    .build();
        }
        return myRetrofit;
    }
}

