package com.tuned.irbed1.retrofit;


import com.tuned.irbed1.model.CartResData;
import com.tuned.irbed1.model.CategoriesResData;
import com.tuned.irbed1.model.ImageResData;
import com.tuned.irbed1.model.ItemsResData;
import com.tuned.irbed1.model.NotificationResModel;
import com.tuned.irbed1.model.ResultModel;
import com.tuned.irbed1.model.SignUpModel;
import com.tuned.irbed1.model.UserModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {


    @Multipart
    @POST("login.php")
    Call<UserModel> login(@Part("Email") RequestBody Email,
                          @Part("Password") RequestBody Password
    );

    @Multipart
    @POST("SignUp.php")
    Call<SignUpModel> SignUp(@Part("Name") RequestBody Name,
                             @Part("Email") RequestBody Email,
                             @Part("Password") RequestBody Password,
                             @Part("Phone") RequestBody Phone

    );

    @GET("GetCategries.php")
    Call<CategoriesResData> GetCategries();

    @Multipart
    @POST("GetItems.php")
    Call<ItemsResData> GetItems(
            @Part("Id_categories") RequestBody Id_categories
    );


    @Multipart
    @POST("getItemImages.php")
    Call<ImageResData> getItemImages(
            @Part("Id_items") RequestBody Id_items

    );
    @Multipart
    @POST("AddToCart.php")
    Call<ResultModel> AddToCart(
            @Part("Id_items") RequestBody Id_items,
            @Part("Id_users") RequestBody Id_users
    );

    @Multipart
    @POST("UpdateCart.php")
    Call<ResultModel> UpdateCart(
            @Part("Id") RequestBody Id,
            @Part("count") RequestBody count
    );

    @Multipart
    @POST("getCart.php")
    Call<CartResData> getCart(
            @Part("Id_users") RequestBody Id_users
    );


    @Multipart
    @POST("deleteCart.php")
    Call<ResultModel> deleteCart(
            @Part("Id_cart") RequestBody Id_cart
    );


    @Multipart
    @POST("updateProfile.php")
    Call<SignUpModel> updateProfile(
            @Part("Name") RequestBody Name,
            @Part("Email") RequestBody Email,
            @Part("Password") RequestBody Password,
            @Part("Phone") RequestBody Phone,
            @Part("Id_users") RequestBody Id_users

            );


    @Multipart
    @POST("AddOrder.php")
    Call<ResultModel> AddOrder(
            @Part("Id_users") RequestBody Id_users,
            @Part("TotalPrice") RequestBody TotalPrice,
            @Part("Latitude") RequestBody Latitude,
            @Part("Longitude") RequestBody Longitude

            );

    @GET("getNotification.php")
    Call<NotificationResModel> getNotification();
}
