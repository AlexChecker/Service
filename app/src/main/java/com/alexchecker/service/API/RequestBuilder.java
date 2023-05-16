package com.alexchecker.service.API;

import com.alexchecker.service.utils.UnsafeOkHttpClient;
import com.alexchecker.service.utils.Utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestBuilder {
    private static String URL = "https://[2a00:1370:819e:4b85:6385:9a22:c935:df9b]:7253/api/";
    private static Retrofit retrofit = null;

    public static Retrofit buildRequest()
    {
        OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        return retrofit;

    }
}

