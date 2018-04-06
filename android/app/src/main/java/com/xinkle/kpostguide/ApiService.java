package com.xinkle.kpostguide;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService{
    String API_URL = "http://116.39.0.146:7777/";

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> request(@Field("request_ip") String ip);
}