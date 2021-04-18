package com.example.bbsmodule.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bbsmodule.R;
import com.example.bbsmodule.R2;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostInfoPicRvAdapter extends RecyclerView.Adapter {
   private List<String> picList;
   private PicOnClickListener picOnClickListener;
   private PicOnLongClickListener onPicLongClikedListener;
   private int picType = 0;
   private final static int POST = 0;
   private final static int COMMENT = 1;


    public PostInfoPicRvAdapter(Context context, List<String> picList, PicOnLongClickListener picOnClickListener, int picType) {
        this.picList = picList;
        this.context = context;
        this.onPicLongClikedListener = picOnClickListener;
        this.picType = picType;
    }

    public PostInfoPicRvAdapter(Context context) {
        this.context = context;
    }

    Context context;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_pic_layout, parent, false);

        return new PicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        PicViewHolder picholder = (PicViewHolder)holder;

        String url = "";
        if(picType == POST){
            url = UrlUtil.STATIC_RESOURCE.POST_PIC_URL;
        }else if(picType == COMMENT){
            url = UrlUtil.STATIC_RESOURCE.COMMENT_PIC_URL;
        }
        ((PicViewHolder) holder).loadPic(context, url + picList.get(position));

        String finalUrl = url;
        picholder.imageView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                onPicLongClikedListener.onclick((PicViewHolder) holder, finalUrl + picList.get(position));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return picList.size();
    }

    public class PicViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.rv_pic_imageview)
        ImageView imageView;
        public PicViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void loadPic(Context context, String picUrl){
            Glide.with(context)
                    .load(picUrl)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .skipMemoryCache(true)
                    .into(imageView)
                    .onLoadFailed(context.getDrawable(R.color.material_blueGrey_200));
        }

        public Bitmap getBitmap(){
            if(imageView != null){
                if(imageView.getDrawable() instanceof ColorDrawable){
                    return null;
                }else {
                    return ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                }

            }else {
                return null;
            }

        }
    }


    public interface PicOnClickListener{
        void onclick(String picName);
    }

    public interface PicOnLongClickListener{
        void onclick(PicViewHolder holder, String picUrl);
    }
}
