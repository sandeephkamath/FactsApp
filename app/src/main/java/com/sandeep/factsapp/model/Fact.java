package com.sandeep.factsapp.model;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Fact {

    private String title;
    private String description;
    private String imageHref;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHref() {
        return imageHref;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }

    public boolean isInvalid() {
        //return TextUtils.isEmpty(getTitle()) && TextUtils.isEmpty(getDescription()) && TextUtils.isEmpty(getImageHref());
        return null == getTitle() && null == getDescription() && null == getImageHref();
    }

    @BindingAdapter({"android:setImage"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }
}
