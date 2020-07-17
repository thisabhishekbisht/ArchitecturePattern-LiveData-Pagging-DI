package com.abhishek.architecturepattern_livedata_pagging_di.ui;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishek.architecturepattern_livedata_pagging_di.R;
import com.abhishek.architecturepattern_livedata_pagging_di.databinding.RowLayoutBinding;
import com.abhishek.architecturepattern_livedata_pagging_di.utils.NewsModelClass;
import com.jakewharton.rxbinding4.view.RxView;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


public class MyPageListAdapter extends PagedListAdapter<NewsModelClass, MyPageListAdapter.MyViewHolder> {

    MyPageListAdapter() {
        super(NewsModelClass.DIFF_CALLBACK);
    }

    private PublishSubject<View> mViewClickSubject = PublishSubject.create();

    public Observable<View> getViewClickedObservable() {
        return mViewClickSubject.hide();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RowLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_layout, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.binding.setNewsmodel(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        RowLayoutBinding binding;

        MyViewHolder(RowLayoutBinding itemView) {

            super(itemView.getRoot());
            binding = itemView;

        }

    }
}
