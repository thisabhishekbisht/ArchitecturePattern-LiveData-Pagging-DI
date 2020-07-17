package com.abhishek.architecturepattern_livedata_pagging_di.utils;

import com.google.gson.JsonElement;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCallInterface {

    @GET(Urls.FetchNewsList)
    Observable<JsonElement> fetchListNews(
            @Query("country") String country,
            @Query("category") String category,
            @Query("apiKey") String apiKey);

}
