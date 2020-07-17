package com.abhishek.architecturepattern_livedata_pagging_di.utils;



import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.abhishek.architecturepattern_livedata_pagging_di.ui.PagingLibViewModel;

import javax.inject.Inject;



public class ViewModelFactory implements ViewModelProvider.Factory {

    private Repository repository;

    @Inject
    public ViewModelFactory(Repository repository) {
        this.repository = repository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PagingLibViewModel.class)) {
            return (T) new PagingLibViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
