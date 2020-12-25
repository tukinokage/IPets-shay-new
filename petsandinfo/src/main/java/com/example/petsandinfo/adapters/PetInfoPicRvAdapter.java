package com.example.petsandinfo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petsandinfo.R;

import java.util.List;

public class PetInfoPicRvAdapter extends RecyclerView.Adapter {


    Context context;
    List<String> picNameList;
    private ImageOnClickListener onClickListener;

    public PetInfoPicRvAdapter(Context context) {
        this.context = context;
    }

    public void setOnClickListener(ImageOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public void setPicNameList(List<String> picNameList) {
        this.picNameList = picNameList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pet_info_pic_rv_item_layout, parent, false);
        return new PetInfoPicRvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PetInfoPicRvViewHolder){

            ((PetInfoPicRvViewHolder) holder).view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return picNameList.size();
    }

    class PetInfoPicRvViewHolder extends RecyclerView.ViewHolder{

        View view;
        ImageView imageView;
        public PetInfoPicRvViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }
    }

    public interface ImageOnClickListener {
        void onClick(int position);
    }
}
