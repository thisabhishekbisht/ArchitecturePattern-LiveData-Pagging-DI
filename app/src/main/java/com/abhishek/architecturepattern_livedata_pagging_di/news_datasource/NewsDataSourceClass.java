package com.abhishek.architecturepattern_livedata_pagging_di.news_datasource;

import android.annotation.SuppressLint;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.abhishek.architecturepattern_livedata_pagging_di.utils.Constant;
import com.abhishek.architecturepattern_livedata_pagging_di.utils.NewsModelClass;
import com.abhishek.architecturepattern_livedata_pagging_di.utils.Repository;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class NewsDataSourceClass extends PageKeyedDataSource<Integer, NewsModelClass> {
    private Repository repository;
    private Gson gson;
    private int sourceIndex;
    private MutableLiveData<String> progressLiveStatus;
    private CompositeDisposable compositeDisposable;

    NewsDataSourceClass(Repository repository, CompositeDisposable compositeDisposable) {
        this.repository = repository;
        this.compositeDisposable = compositeDisposable;
        progressLiveStatus = new MutableLiveData<>();
        GsonBuilder builder =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gson = builder.setLenient().create();
    }


    public MutableLiveData<String> getProgressLiveStatus() {
        return progressLiveStatus;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, NewsModelClass> callback) {

        repository.executeNewsApi(sourceIndex)
                .doOnSubscribe(disposable ->
                {
                    compositeDisposable.add(disposable);
                    progressLiveStatus.postValue(Constant.LOADING);
                })
                .subscribe(
                        (JsonElement result) ->
                        {
                            progressLiveStatus.postValue(Constant.LOADED);

                            JSONObject object = new JSONObject(gson.toJson(result));
                            JSONArray array = object.getJSONArray("articles");

                            ArrayList<NewsModelClass> arrayList = new ArrayList<>();

                            for (int i = 0; i < array.length(); i++) {
                                arrayList.add(new NewsModelClass(array.getJSONObject(i).optString("title"), array.getJSONObject(i).optString("urlToImage")));
                            }

                            sourceIndex++;
                            callback.onResult(arrayList, null, sourceIndex);
                        },
                        throwable ->
                                progressLiveStatus.postValue(Constant.LOADED)

                );

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, NewsModelClass> callback) {

    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, NewsModelClass> callback) {

        repository.executeNewsApi(params.key)
                .doOnSubscribe(disposable ->
                {
                    compositeDisposable.add(disposable);
                    progressLiveStatus.postValue(Constant.LOADING);
                })
                .subscribe(
                        (JsonElement result) ->
                        {
                            progressLiveStatus.postValue(Constant.LOADED);

                            JSONObject object = new JSONObject(gson.toJson(result));
                            JSONArray array = object.getJSONArray("articles");

                            ArrayList<NewsModelClass> arrayList = new ArrayList<>();

                            for (int i = 0; i < array.length(); i++) {
                                arrayList.add(new NewsModelClass(array.getJSONObject(i).optString("title"),
                                        array.getJSONObject(i).optString("urlToImage")));
                            }

                            callback.onResult(arrayList, params.key == 3 ? null : params.key + 1);

                        },
                        throwable ->
                                progressLiveStatus.postValue(Constant.LOADED)
                );
    }
}
