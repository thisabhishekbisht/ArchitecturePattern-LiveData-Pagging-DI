package com.abhishek.architecturepattern_livedata_pagging_di.utils;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class NewsModelClass {

    private String newsImg, newsTitle;

    public String getNewsImg() {
        return newsImg;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public NewsModelClass(String newsTitle, String newsImg) {
        this.newsImg = newsImg;
        this.newsTitle = newsTitle;
    }

    public static DiffUtil.ItemCallback<NewsModelClass> DIFF_CALLBACK = new DiffUtil.ItemCallback<NewsModelClass>() {
        @Override
        public boolean areItemsTheSame(@NonNull NewsModelClass oldItem, @NonNull NewsModelClass newItem) {
            return oldItem.newsTitle.equals(newItem.newsTitle);
        }

        @Override
        public boolean areContentsTheSame(@NonNull NewsModelClass oldItem, @NonNull NewsModelClass newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        NewsModelClass article = (NewsModelClass) obj;
        return article.newsTitle.equals(this.newsTitle);
    }

}
