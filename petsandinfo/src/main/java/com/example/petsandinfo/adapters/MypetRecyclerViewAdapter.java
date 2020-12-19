package com.example.petsandinfo.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petsandinfo.R;
import com.example.petsandinfo.model.entity.Pet;
import com.example.petsandinfo.ui.main.AllPetsFragment.OnListFragmentInteractionListener;
import com.example.petsandinfo.ui.main.dummy.DummyContent.DummyItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MypetRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private  List<Pet> mValues;
    private final OnListFragmentInteractionListener mListener;

    public static final int NORMAL_ITEM = 0;
    public static final int FOOT_ITEM = 0;

    private boolean isShowFootTip = false;
    private boolean hasMoreData = true;

    public static final String FOOT_ITEM_TIP_TEXT = "正在加载...";
    public static final String FOOT_ITEM_TIP_NOT_MORE_TEXT = "下面没有了";

    public MypetRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        mListener = listener;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == FOOT_ITEM){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.pet_list_item_layout, parent, false);
        }else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.pet_list_item_layout, parent, false);
        }

        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Pet pet = mValues.get(position);
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
            NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
            normalViewHolder.nameView.setText(pet.getPetName());
            normalViewHolder.numView.setText(pet.getViewNum());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == getItemCount()){
            return FOOT_ITEM;
        }
        return NORMAL_ITEM;
    }

    @Override
    public int getItemCount() {
        return mValues.size() + 1;
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
        }
    }

    public void showFootTip(){
        isShowFootTip = true;
        notifyDataSetChanged();
    }

    public void hideFootTip(){
        isShowFootTip = false;
        notifyDataSetChanged();
    }

    public  void hasMoreData() {
        hasMoreData = true;
    }
    public  void noMoreData() {
        hasMoreData = false;
    }


    public void setmValues(List<Pet> mValues) {
        this.mValues = mValues;
    }
}
