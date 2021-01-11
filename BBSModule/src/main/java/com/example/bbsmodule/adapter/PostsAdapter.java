package com.example.bbsmodule.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbsmodule.R;
import com.example.bbsmodule.entity.BBSPost;

import java.util.List;

import butterknife.ButterKnife;

public class PostsAdapter extends RecyclerView.Adapter {
    Context context;
    List<BBSPost> postList;
    private View.OnClickListener onClickListener;

    public PostsAdapter(Context context) {
        this.context = context;
    }

    public void setPostList(List<BBSPost> postList) {
        this.postList = postList;
    }
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.post_item_layout, null);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        PostViewHolder postViewHolder = (PostViewHolder) holder;
        if(onClickListener != null){
            holder.itemView.setOnClickListener(onClickListener);
        }


    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
