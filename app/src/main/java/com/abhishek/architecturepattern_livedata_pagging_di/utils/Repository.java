package com.abhishek.architecturepattern_livedata_pagging_di.utils;

import com.google.gson.JsonElement;

import io.reactivex.Observable;



public class Repository {

    private ApiCallInterface apiCallInterface;

    public Repository(ApiCallInterface apiCallInterface) {
        this.apiCallInterface = apiCallInterface;
    }

    /*
     * method to call news api
     * */
    public Observable<JsonElement> executeNewsApi(int index) {
        return apiCallInterface.fetchListNews("in","business", (Constant.API_KEY));
    }

}
