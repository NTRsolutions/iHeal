package com.sismatix.iheal.Retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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

    @POST("AppProductView.php")
    @FormUrlEncoded
    Call<ResponseBody>appprodview(@Field("product_id") String product_id);


    @POST("AppAddToCart.php")
    @FormUrlEncoded
    Call<ResponseBody>addtocart(@Field("product_id") String product_id,
                                @Field("customer_id") String password);
    @POST("AppAddToCart.php")
    @FormUrlEncoded
    Call<ResponseBody>withoutlogin_quote_addtocart(@Field("product_id") String product_id);
    @POST("AppAddToCart.php")
    @FormUrlEncoded
    Call<ResponseBody>withoutlogin_addtocart(@Field("product_id") String product_id,
                                                   @Field("quote_id") String quote_id);
    @POST("AppCartList.php")
    @FormUrlEncoded
    Call<ResponseBody>Cartlist(@Field("email") String email);

    @POST("AppCartList.php")
    @FormUrlEncoded
    Call<ResponseBody> getlistcart(@Field("quote_id") String email);

    //wishlist api
    //https://ihealkuwait.com/customapi/AppAddWishlist.php?productid=50&customerid=1

    @POST("AppAddWishlist.php")
    @FormUrlEncoded
    Call<ResponseBody>add_to_wishlist(@Field("productid") String product_id,
                                @Field("customerid") String customerid,@Field("action") String action);

    //remove from wishlist api
    //https://ihealkuwait.com/customapi/AppRemoveWishlistProduct.php?productid=50&customerid=1

    @POST("AppRemoveWishlistProduct.php")
    @FormUrlEncoded
    Call<ResponseBody>remove_from_wishlist(@Field("productid") String product_id,
                                      @Field("customerid") String customerid);


    //remove from cartlist
    //https://ihealkuwait.com/customapi/AppRemoveFromCart.php?product_id=4&email=developertest2018@gmail.com


    @POST("AppRemoveFromCart.php")
    @FormUrlEncoded
    Call<ResponseBody>remove_from_cartlist(@Field("product_id") String product_id,@Field("email") String email);

    @POST("AppRemoveFromCart.php")
    @FormUrlEncoded
    Call<ResponseBody>withoutlogin_remove_from_cartlist(@Field("product_id") String product_id,@Field("quote_id") String email);

    @POST("AppGetWishlist.php")
    @FormUrlEncoded
    Call<ResponseBody> GetWishlist(@Field("customerid") String email);

    @POST("AppAddWishlist.php")
    @FormUrlEncoded
    Call<ResponseBody> Wishlistactions(@Field("action") String action,
                                       @Field("productid") String productid,
                                       @Field("customerid") String customerid);

    //country list
    //https://ihealkuwait.com/customapi/AppGetCountryList.php
    @GET("AppGetCountryList.php")
    Call<ResponseBody>get_country_list();



}
