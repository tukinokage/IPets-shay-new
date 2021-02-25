package com.example.usermodule.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usermodule.R;
import com.example.usermodule.R2;
import com.shay.baselibrary.dto.Pet;
import com.shay.baselibrary.dto.UserCommentItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UserCommentViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private  List<UserCommentItem> mValues;
    public static final int NORMAL_ITEM = 0;
    public static final int FOOT_ITEM = 1;

    private boolean isShowFootTip = false;
    private boolean hasMoreData = true;

    public static final String FOOT_ITEM_TIP_TEXT = "正在加载...";
    public static final String FOOT_ITEM_TIP_NOT_MORE_TEXT = "下面没有了";

    Context context;

    ItemClickedListener itemClickedListener;

    public UserCommentViewAdapter(Context context) {
        this.context = context;
        mValues = new ArrayList<>();
    }

    public void setmValues(List<UserCommentItem> mValues) {
        this.mValues = mValues;
    }
    public void setItemClickedListener(ItemClickedListener itemClickedListener) {
        this.itemClickedListener = itemClickedListener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View  view = null;
        if(viewType == FOOT_ITEM){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.foot_item_layout, parent, false);
            viewHolder = new FootViewHolder(view);
        }else if(viewType == NORMAL_ITEM){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comment_list_item_layout, parent, false);
            viewHolder = new NormalViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof FootViewHolder) {

            FootViewHolder footViewHolder = (FootViewHolder) holder;
            if(hasMoreData){
                footViewHolder.textView.setText(FOOT_ITEM_TIP_TEXT);
            }else {
                footViewHolder.textView.setText(FOOT_ITEM_TIP_NOT_MORE_TEXT);
            }

            if(isShowFootTip){
                footViewHolder.view.setVisibility(View.VISIBLE);
            }else {
                footViewHolder.view.setVisibility(View.INVISIBLE);
            }

        } else {

            UserCommentItem userCommentItem = mValues.get(position);
            NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
            normalViewHolder.commentTextTv.setText(userCommentItem.getCommentText());
            normalViewHolder.postTitleTv.setText(userCommentItem.getPostTitle());
            normalViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickedListener.onclick(position);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == getItemCount()-1){
            return FOOT_ITEM;
        }else {
            return NORMAL_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size() + 1;
    }

    public interface PetItemOnclickListener{
        void onClick(int position);
    }
    public class NormalViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R2.id.comment_content_text_tv)
        TextView commentTextTv ;
        @BindView(R2.id.comment_post_title_tv)
        TextView postTitleTv ;

        public NormalViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, mView);
        }

        @Override
        public String toString() {
            return super.toString() + " '";
        }
    }

    public class FootViewHolder extends RecyclerView.ViewHolder{

        public final View view;

        @BindView(R2.id.pet_list_foot_tips)
        TextView textView;

        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }

    public void showFootTip(){
        isShowFootTip = true;
        this.notifyDataSetChanged();
    }

    public void hideFootTip(){
        isShowFootTip = false;
        this.notifyDataSetChanged();
    }

    public void hasMoreData() {
        hasMoreData = true;
    }
    public  void noMoreData() {
        hasMoreData = false;
    }

    public List<UserCommentItem> getmValues() {
        return mValues;
    }

    public interface ItemClickedListener{
        void onclick(int position);
    }

}
