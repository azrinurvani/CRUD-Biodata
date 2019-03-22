package com.mobile.azrinurvani.crudbiodata.network;

import com.mobile.azrinurvani.crudbiodata.model.ResponseGetBiodata;
import com.mobile.azrinurvani.crudbiodata.model.ResponseInsertBiodata;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {

    @FormUrlEncoded
    @POST("insertBiodata")
    Call<ResponseInsertBiodata>insertBiodata(
            @Field("nama")String nama,
            @Field("jekel")String jekel,
            @Field("hobi")String hobi,
            @Field("alamat")String alamat
            );

    @GET("getBiodata")
    Call<ResponseGetBiodata>getBiodata();

    @FormUrlEncoded
    @POST("updateBiodata")
    Call<ResponseInsertBiodata>updateBiodata(
            @Field("id")String id,
            @Field("nama")String nama,
            @Field("jekel")String jekel,
            @Field("hobi")String hobi,
            @Field("alamat")String alamat
    );

    @FormUrlEncoded
    @POST("deleteBiodata")
    Call<ResponseInsertBiodata>deleteBiodata(
            @Field("id")String id
    );
}
