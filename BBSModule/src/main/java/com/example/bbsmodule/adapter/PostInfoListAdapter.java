package com.example.bbsmodule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbsmodule.R;
import com.shay.baselibrary.dto.Comment;
import com.shay.baselibrary.dto.Post;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//详情
public class PostInfoListAdapter extends RecyclerView.Adapter {
    Context context;
    LinearLayoutManager mLayoutManager;
    List<Comment> commentsList;
    Post post;

    private final static int POST = 0;
    private final static int COMMENT = 1;

    private ClikPicListener clikPicListener;
    private ClickUserHeadIconListener onClickListener;

    public void setPost(Post post) {
        this.post = post;
    }

    public PostInfoListAdapter(Context context) {
        this.context = context;
        mLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
    }

    public void setCommentsList(List<Comment> commentsList) {
        this.commentsList = commentsList;
    }

    public void setOnUserHeadClickListener(ClickUserHeadIconListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public void setClikPicListener(ClikPicListener clikPicListener) {
        this.clikPicListener = clikPicListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //没有数据
        if (post == null){
            return null;
        }

        View view = LayoutInflater.from(context).inflate(R.layout.post_comment_item_layout, parent);
        if(viewType == POST){
            return new PostViewHolder(view);
        }else{
            return new CommentViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof PostViewHolder){
            PostViewHolder postViewHolder = (PostViewHolder) holder;
            postViewHolder.contentText.setText(post.getContextText());
            postViewHolder.icon.setImageResource(R.drawable.pic_ZB26_icon);
            postViewHolder.name.setText(post.getUserName());
            postViewHolder.dateTextView.setText(post.getDateTime());
            postViewHolder.numTextView.setText("楼主");
            if(onClickListener != null){
                postViewHolder.icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onclick(post);
                    }
                });
            }

            if(postViewHolder.picRv.getAdapter() == null){
                PostInfoPicRvAdapter picRvAdapter = new PostInfoPicRvAdapter(context,
                        post.getPicNameList(),

                        picName -> {
                            clikPicListener.onClick(picName);
                        });

                postViewHolder.picRv.setAdapter(picRvAdapter);
            }
        }else {
            CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
            commentViewHolder.contentText.setText(commentsList.get(position).getContentText());
            commentViewHolder.icon.setImageResource(R.drawable.pic_ZB26_icon);
            commentViewHolder.name.setText(commentsList.get(position).getUserName());
            commentViewHolder.dateTextView.setText(commentsList.get(position).getDateTime());
            commentViewHolder.numTextView.setText("#" + position);
            if(onClickListener != null){
                ((CommentViewHolder) holder).icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int rposition = position -1;
                        onClickListener.onclick(rposition);
                    }
                });

            }

            if(commentViewHolder.picRv.getAdapter() == null){
                PostInfoPicRvAdapter picRvAdapter = new PostInfoPicRvAdapter(context,
                        post.getPicNameList(),

                        picName -> {
                            clikPicListener.onClick(picName);
                        });

                commentViewHolder.picRv.setAdapter(picRvAdapter);
            }
        }



    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return POST;
        }else {
            return COMMENT;
        }
    }

    @Override
    public int getItemCount() {
        return commentsList.size() + 1;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.post_comment_item_headicon_iv)
        ImageView icon;
        @BindView(R.id.post_comment_item_headicon_iv)
        TextView name;

        @BindView(R.id.post_comment_item_contenttext_textview)
        TextView contentText;
        @BindView(R.id.post_comment_item_content_pic_rv)
        RecyclerView picRv;

        @BindView(R.id.post_comment_item_num_textview)
        TextView dateTextView;
        @BindView(R.id.post_comment_item_num_textview)
        TextView numTextView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.post_comment_item_headicon_iv)
        ImageView icon;
        @BindView(R.id.post_comment_item_headicon_iv)
        TextView name;

        @BindView(R.id.post_comment_item_contenttext_textview)
        TextView contentText;
        @BindView(R.id.post_comment_item_content_pic_rv)
        RecyclerView picRv;

        @BindView(R.id.post_comment_item_num_textview)
        TextView dateTextView;
        @BindView(R.id.post_comment_item_num_textview)
        TextView numTextView;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface ClickUserHeadIconListener{
        void onclick(Post post);
        void onclick(int position);
    }

     public List<Comment> getCommentsList(){
        return commentsList;
     }

    public interface ClikPicListener{
        void onClick(String picName);
    }

}
