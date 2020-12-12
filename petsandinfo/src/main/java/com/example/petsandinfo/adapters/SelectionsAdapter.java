package com.example.petsandinfo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.petsandinfo.R;

import java.util.List;

public class SelectionsAdapter extends BaseAdapter {

    private List<String> seletionList;
    private Context context;

    private SelectionOnclickListener selectionOnclickListener;

    @Override
    public int getCount() {
        return seletionList.size();
    }

    @Override
    public Object getItem(int position) {
        return seletionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SelectionViewholder viewholder = null;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.selection_item, parent, false);
            viewholder = new SelectionViewholder();
            convertView.setTag(viewholder);

            //由于是固定的，只要在第一次初始化就可
            viewholder.btn.setText(seletionList.get(position));
            viewholder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectionOnclickListener.onClick(position);
                }
            });
        }/*else {
            viewholder = (SelectionViewholder) convertView.getTag();
        }*/
        return convertView;
    }

    public void setSelectionOnclickListener(SelectionOnclickListener selectionOnclickListener){
        this.selectionOnclickListener = selectionOnclickListener;
    }

    interface SelectionOnclickListener{
        void onClick(int position);
    }

    class SelectionViewholder{
        Button btn;
    }

}
