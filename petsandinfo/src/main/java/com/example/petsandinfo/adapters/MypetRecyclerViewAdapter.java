package com.example.petsandinfo.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petsandinfo.R;
import com.shay.baselibrary.dto.Pet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MypetRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  List<Pet> mValues;


    private PetItemOnclickListener petItemOnclickListener;

    public static final int NORMAL_ITEM = 0;
    public static final int FOOT_ITEM = 1;

    private boolean isShowFootTip = false;
    private boolean hasMoreData = true;

    public static final String FOOT_ITEM_TIP_TEXT = "正在加载...";
    public static final String FOOT_ITEM_TIP_NOT_MORE_TEXT = "下面没有了";

    public MypetRecyclerViewAdapter() {

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
                    .inflate(R.layout.pet_list_item_layout, parent, false);
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

            Pet pet = mValues.get(position);
            NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
            normalViewHolder.nameView.setText(pet.getPetName());
            normalViewHolder.numView.setText(pet.getViewNum());
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

        @BindView(R.id.pet_list_item_iv)
        ImageView imageView;

        @BindView(R.id.pet_list_item_name_tv)
        TextView nameView;

        @BindView(R.id.pet_list_item_view_num_tv)
        TextView numView;

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

        @BindView(R.id.pet_list_foot_tips)
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


    public void setmValues(List<Pet> mValues) {
        this.mValues = mValues;
    }
}
