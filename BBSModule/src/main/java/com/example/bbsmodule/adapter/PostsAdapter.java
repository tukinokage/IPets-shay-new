package com.example.bbsmodule.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bbsmodule.R;
import com.example.bbsmodule.R2;
import com.example.bbsmodule.entity.BBSPost;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostsAdapter extends RecyclerView.Adapter {
    Context context;
    List<BBSPost> postList;
    private PostOnclickListener postOnclickListener;

    public PostsAdapter(Context context) {
        this.context = context;
    }

    public void setPostList(List<BBSPost> postList) {
        this.postList = postList;
    }
    public void setOnClickListener(PostOnclickListener onClickListener) {
        this.postOnclickListener = onClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.post_item_layout, null);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        PostViewHolder postViewHolder = (PostViewHolder) holder;
        if(postOnclickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postOnclickListener.onclick(position);
                }
            });
        }
        ((PostViewHolder) holder).imageView.setImageResource(R.drawable.post_none);

        if(!TextUtils.isEmpty(postList.get(position).getPicName())){
            Glide.with(context)
                    .load(UrlUtil.STATIC_RESOURCE.POST_PIC_URL + postList.get(position).getPicName())
                    .into(((PostViewHolder) holder).imageView)
                    .onLoadFailed(context.getDrawable(R.color.material_blue_200));
        }


        ((PostViewHolder) holder).textView.setText(postList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{
        @BindView(R2.id.post_item_img)
        ImageView imageView;
        @BindView(R2.id.post_item_title_tv)
        TextView textView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface PostOnclickListener{
        void onclick(int position);
    }
}
