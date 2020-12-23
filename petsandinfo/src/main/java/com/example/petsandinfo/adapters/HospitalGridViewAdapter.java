package com.example.petsandinfo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petsandinfo.R;
import com.example.petsandinfo.model.entity.Hospital;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HospitalGridViewAdapter extends BaseAdapter {

    private List<Hospital> hospitalList;
    private Context context;

    private HospitalOnclickListener hospitalOnclickListener;

    public HospitalGridViewAdapter(List<Hospital> hospitalList, Context context) {
        this.hospitalList = hospitalList;
        this.context = context;
    }

    public void setSeletionList(List<Hospital> hospitalList) {
        this.hospitalList = hospitalList;
    }

    @Override
    public int getCount() {
        return hospitalList.size();
    }

    @Override
    public Object getItem(int position) {
        return hospitalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HospitalViewholder viewholder = null;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.pet_hospital_item_layout, parent, false);
            viewholder = new HospitalViewholder(convertView);
            convertView.setTag(viewholder);
        }
        else {
            viewholder = (HospitalViewholder) convertView.getTag();
        }

        //由于是固定的，只要在第一次初始化就可
        viewholder.nameTextView.setText(hospitalList.get(position).getHospitalName());
        viewholder.phoneNumTextView.setText(hospitalList.get(position).getHospitalPhoneNum());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hospitalOnclickListener.onClick(position);
            }
        });

        return convertView;
    }

    public void setHospitalOnclickListener(HospitalOnclickListener hospitalOnclickListener){
        this.hospitalOnclickListener = hospitalOnclickListener;
    }

    public interface HospitalOnclickListener{
        void onClick(int position);
    }

    class HospitalViewholder{
        @BindView(R.id.pet_hospital_icon_iv)
        ImageView imageView;

        @BindView(R.id.pet_store_name_tv)
        TextView nameTextView;

        @BindView(R.id.pet_hospital_phone_tv)
        TextView phoneNumTextView;
        public HospitalViewholder(View v){
            ButterKnife.bind(this, v);
        }
    }

}
