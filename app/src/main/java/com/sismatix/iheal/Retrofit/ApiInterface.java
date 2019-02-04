package com.sismatix.iheal.Retrofit;

import com.sismatix.iheal.Preference.LoginModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("user_ragistration.php")
    @FormUrlEncoded
    Call<LoginModel> register(@Field("firstname") String username,
                              @Field("lastname") String lastname,
                              @Field("mobile") String mobile,
                              @Field("email") String email,
                              @Field("password") String password,
                              @Field("city") String city);

    @POST("user_login.php")
    @FormUrlEncoded
    Call<LoginModel> login(@Field("email") String username,
                           @Field("password") String password);

}
