package com.example.bbsmodule.adapter;

import android.content.Context;
import android.graphics.BitmapRegionDecoder;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bbsmodule.R;
import com.example.bbsmodule.R2;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.Comment;
import com.shay.baselibrary.dto.Post;

import java.util.ArrayList;
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

    private ClikLongPicListener clikPicListener;
    private ClickUserHeadIconListener onClickListener;

    public void setPost(Post post) {
        this.post = post;
    }

    public PostInfoListAdapter(Context context) {
        this.context = context;
        mLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        commentsList = new ArrayList<>();
    }

    public void setCommentsList(List<Comment> commentsList) {
        this.commentsList.addAll(commentsList);
    }

    public void setOnUserHeadClickListener(ClickUserHeadIconListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public void setClikPicListener(ClikLongPicListener clikPicListener) {
        this.clikPicListener = clikPicListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //没有数据
        if (post == null){
            return null;
        }

        View view = LayoutInflater.from(context).inflate(R.layout.post_comment_item_layout, parent, false);
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
            postViewHolder.icon.setImageResource(R.color.btn_filled_blue_bg_normal);
            Glide.with(context)
                    .load(UrlUtil.STATIC_RESOURCE.HEAD_ICON_URL + post.getUserId() + ".jpg")
                    .placeholder(AppContext.getContext().getDrawable(R.color.material_blue_200))
                    .skipMemoryCache(true)
                    .into(postViewHolder.icon)
                    .onLoadStarted(context.getDrawable(R.color.qmui_config_color_gray_5));
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

            LinearLayoutManager layoutManager= new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            postViewHolder.picRv.setLayoutManager(layoutManager);

            if( post.getPicNameList() != null && !(post.getPicNameList().isEmpty())) {
                if (postViewHolder.picRv.getAdapter() == null) {
                    PostInfoPicRvAdapter picRvAdapter = new PostInfoPicRvAdapter(context,
                            post.getPicNameList(), new PostInfoPicRvAdapter.PicOnLongClickListener() {
                        @Override
                        public void onclick(PostInfoPicRvAdapter.PicViewHolder holder, String picUrl) {
                            clikPicListener.onClick(holder, picUrl);
                        }

                    },
                            POST);


                    postViewHolder.picRv.setAdapter(picRvAdapter);
                }
            }
        }else {

            int rposition = position -1;
            CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
            commentViewHolder.contentText.setText(commentsList.get(rposition).getContentText());
            commentViewHolder.icon.setImageResource(R.color.btn_filled_blue_bg_normal);
            Glide.with(context)

                    .load(UrlUtil.STATIC_RESOURCE.HEAD_ICON_URL + commentsList.get(rposition).getHeadPicName())
                    .placeholder(AppContext.getContext().getDrawable(R.color.material_blue_200))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into( commentViewHolder.icon)
                    .onLoadStarted(context.getDrawable(R.color.material_white));

            commentViewHolder.name.setText(commentsList.get(rposition).getUserName());
            commentViewHolder.dateTextView.setText(commentsList.get(rposition).getDateTime());
            commentViewHolder.numTextView.setText("#" + position);
            if(onClickListener != null){
                ((CommentViewHolder) holder).icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        onClickListener.onclick(rposition);
                    }
                });

            }
            if(!(commentsList.get(position - 1).getPicList().isEmpty())){
                LinearLayoutManager layoutManager= new LinearLayoutManager(context);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                commentViewHolder.picRv.setLayoutManager(layoutManager);

                if(commentViewHolder.picRv.getAdapter() == null){
                    PostInfoPicRvAdapter picRvAdapter = new PostInfoPicRvAdapter(context,
                            commentsList.get(position - 1).getPicList(),
                            new PostInfoPicRvAdapter.PicOnLongClickListener(){
                                @Override
                                public void onclick(PostInfoPicRvAdapter.PicViewHolder holder, String picUrl) {
                                    clikPicListener.onClick(holder, picUrl );
                                }

                            },
                            COMMENT);

                    commentViewHolder.picRv.setAdapter(picRvAdapter);
                }
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
    public int getItemCount()
    {
        if(post == null){
            return 0;
        }else {
            return commentsList.size() + 1;
        }

    }

    public class PostViewHolder extends RecyclerView.ViewHolder{

        @BindView(R2.id.post_comment_item_headicon_iv)
        ImageView icon;
        @BindView(R2.id.post_comment_item_usernamne_textview)
        TextView name;

        @BindView(R2.id.post_comment_item_contenttext_textview)
        TextView contentText;
        @BindView(R2.id.post_comment_item_content_pic_rv)
        RecyclerView picRv;

        @BindView(R2.id.post_comment_item_datetime_textview)
        TextView dateTextView;
        @BindView(R2.id.post_comment_item_num_textview)
        TextView numTextView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{
        @BindView(R2.id.post_comment_item_headicon_iv)
        ImageView icon;
        @BindView(R2.id.post_comment_item_usernamne_textview)
        TextView name;

        @BindView(R2.id.post_comment_item_contenttext_textview)
        TextView contentText;
        @BindView(R2.id.post_comment_item_content_pic_rv)
        RecyclerView picRv;

        @BindView(R2.id.post_comment_item_datetime_textview)
        TextView dateTextView;
        @BindView(R2.id.post_comment_item_num_textview)
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

    public interface ClikLongPicListener{
        void onClick(RecyclerView.ViewHolder viewHolder, String picUrl);
    }

}
