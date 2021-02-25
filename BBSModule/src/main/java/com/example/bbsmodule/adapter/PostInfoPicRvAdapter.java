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
import com.example.bbsmodule.R2;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostInfoPicRvAdapter extends RecyclerView.Adapter {
   private List<String> picList;
   private PicOnClickListener picOnClickListener;
   private PicOnClickListener onPicClikedListener;
   private int picType = 0;
   private final static int POST = 0;
   private final static int COMMENT = 1;


    public PostInfoPicRvAdapter(Context context, List<String> picList, PicOnClickListener picOnClickListener, int picType) {
        this.picList = picList;
        this.context = context;
        this.onPicClikedListener = picOnClickListener;
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
        Glide.with(context)
                .load( url + picList.get(position))
                .into(picholder.imageView)
                .onLoadFailed(context.getDrawable(R.color.material_blueGrey_200)) ;

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

        @BindView(R2.id.rv_pic_imageview)
        ImageView imageView;
        public PicViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface PicOnClickListener{
        void onclick(String picName);
    }
}
