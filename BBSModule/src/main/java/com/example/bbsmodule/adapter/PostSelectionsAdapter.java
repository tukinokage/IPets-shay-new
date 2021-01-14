package com.example.bbsmodule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.bbsmodule.R;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostSelectionsAdapter extends BaseAdapter {

    private List<String> seletionList;
    private Context context;

    private SelectionOnclickListener selectionOnclickListener;

    public PostSelectionsAdapter(List<String> seletionList, Context context) {
        this.seletionList = seletionList;
        this.context = context;
    }

    public void setSeletionList(List<String> seletionList) {
        this.seletionList = seletionList;
    }

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
            convertView = LayoutInflater.from(context).inflate(R.layout.post_selection_item, parent, false);
            viewholder = new SelectionViewholder(convertView);
            convertView.setTag(viewholder);
        }
        else {
            viewholder = (SelectionViewholder) convertView.getTag();
        }

        //由于是固定的，只要在第一次初始化就可
        viewholder.btn.setText(seletionList.get(position));
        viewholder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectionOnclickListener.onClick(position);
            }
        });

        return convertView;
    }

    public void setSelectionOnclickListener(SelectionOnclickListener selectionOnclickListener){
        this.selectionOnclickListener = selectionOnclickListener;
    }

    public interface SelectionOnclickListener{
        void onClick(int position);
    }

    class SelectionViewholder{
        @BindView(R.id.post_selection_btn)
        QMUIRoundButton btn;

        public SelectionViewholder(View v){
            ButterKnife.bind(this, v);
        }
    }

}
