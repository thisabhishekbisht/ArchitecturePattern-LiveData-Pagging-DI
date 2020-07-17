package com.abhishek.architecturepattern_livedata_pagging_di.ui;



import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.abhishek.architecturepattern_livedata_pagging_di.news_datasource.NewsDataSourceClass;
import com.abhishek.architecturepattern_livedata_pagging_di.news_datasource.NewsDataSourceFactory;
import com.abhishek.architecturepattern_livedata_pagging_di.utils.NewsModelClass;
import com.abhishek.architecturepattern_livedata_pagging_di.utils.Repository;

import io.reactivex.disposables.CompositeDisposable;


public class PagingLibViewModel extends ViewModel {

    private NewsDataSourceFactory newsDataSourceFactory;
    private LiveData<PagedList<NewsModelClass>> listLiveData;

    private LiveData<String> progressLoadStatus = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public PagingLibViewModel(Repository repository) {
        newsDataSourceFactory = new NewsDataSourceFactory(repository, compositeDisposable);
        initializePaging();
    }



    private void initializePaging() {

        PagedList.Config pagedListConfig =
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(10).build();

        listLiveData = new LivePagedListBuilder<>(newsDataSourceFactory, pagedListConfig)
                .build();

        progressLoadStatus = Transformations.switchMap(newsDataSourceFactory.getMutableLiveData(), NewsDataSourceClass::getProgressLiveStatus);

    }

    public LiveData<String> getProgressLoadStatus() {
        return progressLoadStatus;
    }

    public LiveData<PagedList<NewsModelClass>> getListLiveData() {
        return listLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}