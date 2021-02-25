package com.example.petsandinfo.ui.main;

import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.shay.baselibrary.*;
import com.shay.baselibrary.UrlInfoUtil.*;

import java.util.List;

public class DetailsPagerAdapter extends PagerAdapter {

    private List<String> list;

    public DetailsPagerAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(AppContext.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(AppContext.getContext()).load(UrlUtil.STATIC_RESOURCE.PET_PIC_URL + list.get(position)).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}