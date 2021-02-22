package com.example.bbsmodule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bbsmodule.R;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostInfoPicRvAdapter extends RecyclerView.Adapter {
   private List<String> picList;
   private PicOnClickListener picOnClickListener;
   private PicOnClickListener onPicClikedListener;

    public PostInfoPicRvAdapter(Context context, List<String> picList, PicOnClickListener picOnClickListener) {
        this.picList = picList;
        this.context = context;
        this.picOnClickListener = picOnClickListener;
    }

    public PostInfoPicRvAdapter(Context context) {
        this.context = context;
    }

    Context context;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_pic_layout, parent);

        return new PicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        PicViewHolder picholder = (PicViewHolder)holder;
        Glide.with(context)
                .load(UrlUtil.BASE_URL.BASE_URL + picList.get(position))
                .into(picholder.imageView)
        .onLoadStarted(context.getDrawable(R.drawable.pic_zb26_icon));

        picholder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPicClikedListener.onclick(picList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return picList.size();
    }

    class PicViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rv_pic_imageview)
        ImageView imageView;
        public PicViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    interface PicOnClickListener{
        void onclick(String picName);
    }
}
