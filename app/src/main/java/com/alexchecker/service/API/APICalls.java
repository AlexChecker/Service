package com.alexchecker.service.API;

import com.alexchecker.service.API.Models.LoginModel;
import com.alexchecker.service.API.Models.OrderModel;
import com.alexchecker.service.API.Models.OrderStatusModel;
import com.alexchecker.service.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APICalls {
    @GET("ClientLogin/login")
    Call<LoginModel> LogIn(@Query("login") String login,@Query("password") String password);
    @GET("OrderStatus")
    Call<List<OrderStatusModel>> getOrderStatuses( @Header("Authorization") String token);
    @GET("Orders/search/{user}")
    Call<List<OrderModel>> getOrders(@Path("user")String login, @Header("Authorization") String token,@Query("page") int page );
    @GET("Orders/filtered/{user}")
    Call<List<OrderModel>> getFilteredOrders(@Path("user") String login, @Header("Authorization") String token,@Query("price") double price);
    @GET("Orders/sorted/{user}")
    Call<List<OrderModel>> getSortedOrders(@Path("user") String login, @Header("Authorization") String token);
    @GET("Orders/search/{user}/{type}")
    Call<List<OrderModel>> getFindOrders(@Path("user") String login, @Path("type") String type, @Header("Authorization") String token);
}
