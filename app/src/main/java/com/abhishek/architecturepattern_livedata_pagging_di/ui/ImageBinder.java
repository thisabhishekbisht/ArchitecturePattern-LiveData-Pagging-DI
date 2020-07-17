package com.abhishek.architecturepattern_livedata_pagging_di.ui;


import android.net.Uri;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;


public class ImageBinder {

    @BindingAdapter({"imageURL"})
    public static void loadImage(ImageView img, String imageUrl) {
        img.setImageURI(Uri.parse(imageUrl));
    }
}
