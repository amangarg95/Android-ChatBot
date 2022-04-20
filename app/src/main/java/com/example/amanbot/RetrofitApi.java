package com.example.amanbot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitApi {
    @GET
    Call<Chat> getMessage(@Url String url);
}
