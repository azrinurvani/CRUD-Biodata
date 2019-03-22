package com.mobile.azrinurvani.crudbiodata.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigRetrofit {

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.95.238:8080/biodata_diri/index.php/Server/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static RestApi service = retrofit.create(RestApi.class);

}
