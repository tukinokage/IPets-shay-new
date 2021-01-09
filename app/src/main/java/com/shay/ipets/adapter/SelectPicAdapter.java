package com.shay.ipets.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.audiofx.BassBoost;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shay.baselibrary.dto.PostPicInfo;
import com.shay.baselibrary.picUtils.LoadLocalPic;
import com.shay.ipets.R;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.PUT;

public class SelectPicAdapter extends BaseAdapter {
    Context context;



    private List<PostPicInfo> postPicInfoList;

    private CancelBtnListener cancelBtnListener;

    private ItemOnclickListener itemOnclickListener;
    private View.OnClickListener addOnclickListener;

    @Override
    public int getCount() {
        return postPicInfoList.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return postPicInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setPostPicInfoList(List<PostPicInfo> postPicInfoList) {
        this.postPicInfoList = postPicInfoList;
    }

    public void setCancelBtnListener(CancelBtnListener cancelBtnListener) {
        this.cancelBtnListener = cancelBtnListener;
    }
    public void setItemOnclickListener(ItemOnclickListener itemOnclickListener) {
        this.itemOnclickListener = itemOnclickListener;
    }

    public void setAddOnclickListener(View.OnClickListener addOnclickListener) {
        this.addOnclickListener = addOnclickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder itemViewHolder;

        //addbtn
        if(position == postPicInfoList.size()){
            if (convertView == null){
                convertView = View.inflate(context, R.layout.img_preview_item_layout, parent);
                itemViewHolder = new ItemViewHolder(convertView);

                convertView.setTag(itemViewHolder);
            }else {
                itemViewHolder = (ItemViewHolder) convertView.getTag(position);
            }

            itemViewHolder.tipTextView.setVisibility(View.INVISIBLE);
            itemViewHolder.pgLinerLayout.setVisibility(View.INVISIBLE);
            itemViewHolder.cancelBtn.setVisibility(View.INVISIBLE);
            itemViewHolder.img.setImageResource(R.drawable.ic_add);
            itemViewHolder.img.setOnClickListener(addOnclickListener);
            return convertView;
        }


        //pic
        if(convertView == null){
            convertView = View.inflate(context, R.layout.img_preview_item_layout, parent);
            itemViewHolder = new ItemViewHolder(convertView);
            itemViewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            itemViewHolder.cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelBtnListener.onclick(position);
                }
            });
            itemViewHolder.tipTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemOnclickListener.onclick(position);
                }
            });
        }else {
            itemViewHolder = (ItemViewHolder) convertView.getTag(position);
        }

        itemViewHolder.cancelBtn.setVisibility(View.VISIBLE);
        itemViewHolder.tipTextView.setVisibility(View.INVISIBLE);
        itemViewHolder.pgLinerLayout.setVisibility(View.INVISIBLE);

        /////
        try {
            itemViewHolder.img.setImageBitmap(LoadLocalPic.loadPic(postPicInfoList.get(position).getUri()));
            if(postPicInfoList.get(position).isSucceed()){
                itemViewHolder.tipTextView.setVisibility(View.INVISIBLE);
                itemViewHolder.pgLinerLayout.setVisibility(View.INVISIBLE);
            }else if(postPicInfoList.get(position).isFailed()) {
                itemViewHolder.tipTextView.setVisibility(View.VISIBLE);
                itemViewHolder.pgLinerLayout.setVisibility(View.VISIBLE);
            }
        } catch (FileNotFoundException e) {
            itemViewHolder.tipTextView.setVisibility(View.VISIBLE);
            itemViewHolder.pgLinerLayout.setVisibility(View.VISIBLE);
            itemViewHolder.tipTextView.setText("加载出错");
            e.printStackTrace();
        }

        return convertView;
    }

    class ItemViewHolder{
        @BindView(R.id.img_preview_iv)
        public ImageView img;
        @BindView(R.id.img_preview_black_fg)
        public LinearLayout pgLinerLayout;
        @BindView(R.id.img_preview_tip_text_tv)
        public TextView tipTextView;
        @BindView(R.id.img_preview_cancel)
        public Button cancelBtn;

        View view;

        public ItemViewHolder(View view){
            ButterKnife.bind(this, view);
            this.view = view;
        }
    }


    public interface CancelBtnListener{
        void onclick(int position);
    }

    public interface ItemOnclickListener{
        void  onclick(int position);
    }


}
