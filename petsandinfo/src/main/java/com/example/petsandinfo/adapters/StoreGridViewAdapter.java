package com.example.petsandinfo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.petsandinfo.R;
import com.example.petsandinfo.R2;
import com.example.petsandinfo.model.Store;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoreGridViewAdapter extends BaseAdapter {

    private List<Store> storeList;
    private Context context;

    private StoreOnclickListener setStoreOnclickListener;

    public StoreGridViewAdapter(List<Store> storeList, Context context) {
        this.storeList = storeList;
        this.context = context;
    }

    public void setStoreList(List<Store> storeList) {
        this.storeList = storeList;
    }

    @Override
    public int getCount() {
        return storeList.size();
    }

    @Override
    public Object getItem(int position) {
        return storeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StoreViewholder viewholder = null;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.pet_store_layout, parent, false);
            viewholder = new StoreViewholder(convertView);
            convertView.setTag(viewholder);
        }
        else {
            viewholder = (StoreViewholder) convertView.getTag();
        }

        //由于是固定的，只要在第一次初始化就可
        viewholder.nameText.setText(storeList.get(position).getStoreName());
        viewholder.introText.setText(storeList.get(position).getStoreIntroduce());

        Glide.with(AppContext.getContext())
                .load(UrlUtil.STATIC_RESOURCE.STORE_PIC_URL + storeList.get(position).getStoreId() + ".jpg")
                .into(viewholder.imageView)
                .onLoadStarted(AppContext.getContext().getDrawable(R.color.material_blue_200));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStoreOnclickListener.onClick(position);
            }
        });

        return convertView;
    }

    public void setStoreOnclickListener(StoreOnclickListener storeOnclickListener){
        this.setStoreOnclickListener = storeOnclickListener;
    }

    public interface StoreOnclickListener{
        void onClick(int position);
    }

    class StoreViewholder{
        @BindView(R2.id.pet_store_name_tv)
        TextView nameText;
        @BindView(R2.id.pet_store_intro_tv)
        TextView introText;
        @BindView(R2.id.pet_store_icon_iv)
        QMUIRadiusImageView imageView;

        public StoreViewholder(View v){
            ButterKnife.bind(this, v);
        }
    }

}
