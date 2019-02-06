package com.sismatix.iheal.Retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("AppCreateUser.php")
    @FormUrlEncoded
    Call<ResponseBody>signup(@Field("firstname") String fullname,
                              @Field("email") String email,
                              @Field("password") String password);

    @POST("AppLogin.php")
    @FormUrlEncoded
    Call<ResponseBody>login(@Field("email") String username,
                             @Field("password") String password);

    @POST("logout.php")
    @FormUrlEncoded
    Call<ResponseBody>logout(@Field("customer_id") String username);

    @POST("AppResetPassword.php")
    @FormUrlEncoded
    Call<ResponseBody>forgotpassword(@Field("email") String username);

    @POST("AppCategoryList.php")
    @FormUrlEncoded
    Call<ResponseBody>categorylist(@Field("type") String type);

    @POST("AppCategoryProducts.php")
    @FormUrlEncoded
    Call<ResponseBody>addcategoryprod(@Field("category_id") String category_id);

}
