package com.abhishek.architecturepattern_livedata_pagging_di.news_datasource;


import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.abhishek.architecturepattern_livedata_pagging_di.utils.NewsModelClass;
import com.abhishek.architecturepattern_livedata_pagging_di.utils.Repository;

import io.reactivex.disposables.CompositeDisposable;



public class NewsDataSourceFactory extends DataSource.Factory<Integer, NewsModelClass> {

    private MutableLiveData<NewsDataSourceClass> liveData;
    private Repository repository;
    private CompositeDisposable compositeDisposable;

    public NewsDataSourceFactory(Repository repository, CompositeDisposable compositeDisposable) {
        this.repository = repository;
        this.compositeDisposable = compositeDisposable;
        liveData = new MutableLiveData<>();
    }

    public MutableLiveData<NewsDataSourceClass> getMutableLiveData() {
        return liveData;
    }

    @Override
    public DataSource<Integer, NewsModelClass> create() {
        NewsDataSourceClass dataSourceClass = new NewsDataSourceClass(repository, compositeDisposable);
        liveData.postValue(dataSourceClass);
        return dataSourceClass;
    }
}
