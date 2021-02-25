package com.example.usermodule.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.usermodule.R;
import com.example.usermodule.R2;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.dto.Pet;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MyPetRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  List<Pet> mValues;

    private PetItemOnclickListener petItemOnclickListener;

    private PetItemOnclickListener unlikeOnclickListener;

    public static final int NORMAL_ITEM = 0;
    public static final int FOOT_ITEM = 1;

    private boolean isShowFootTip = false;
    private boolean hasMoreData = true;

    public static final String FOOT_ITEM_TIP_TEXT = "正在加载...";
    public static final String FOOT_ITEM_TIP_NOT_MORE_TEXT = "下面没有了";

    public MyPetRecyclerViewAdapter() {
        mValues = new ArrayList<>();
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
                    .inflate(R.layout.star_pet_list_item_layout, parent, false);
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
                footViewHolder.view.setVisibility(View.GONE);
            }
        } else {

            Pet pet = mValues.get(position);
            NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
            normalViewHolder.nameView.setText(pet.getPetName());
            Glide.with(((NormalViewHolder) holder).mView)
                    .load(UrlUtil.STATIC_RESOURCE.BG_PIC_URL + pet.getPetHeadImg())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(normalViewHolder.imageView);
            normalViewHolder.unlikedLy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    unlikeOnclickListener.onClick(position);
                }
            });
            normalViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    petItemOnclickListener.onClick(position);
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

    public void setPetItemOnclickListener(PetItemOnclickListener petItemOnclickListener) {
            this.petItemOnclickListener = petItemOnclickListener;
    }


    public interface PetItemOnclickListener{
        void onClick(int position);
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        @BindView(R2.id.pet_list_item_iv)
        ImageView imageView;

        @BindView(R2.id.pet_list_item_name_tv)
        TextView nameView;

        @BindView(R2.id.cancel_liked_ly)
        LinearLayout unlikedLy;

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

    public void setUnlikeOnclickListener(PetItemOnclickListener unlikeOnclickListener) {
        this.unlikeOnclickListener = unlikeOnclickListener;
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


    public void setmValues(List<Pet> mValues) {
        this.mValues = mValues;
    }
}
