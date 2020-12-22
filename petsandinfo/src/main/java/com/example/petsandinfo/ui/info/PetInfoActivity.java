package com.example.petsandinfo.ui.info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.NestedScrollingChild;
import androidx.core.widget.NestedScrollView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.petsandinfo.R;
import com.google.android.material.appbar.AppBarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetInfoActivity extends AppCompatActivity {


    @BindView(R.id.activity_pet_info_nest_scroll_view)
    NestedScrollView nestedScrollView;

    @BindView(R.id.activity_pet_info_appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.activity_pet_info_top_layout)
    LinearLayout topLayout;

    //滑动隐藏距离
    private int fadingHeight = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info);
        ButterKnife.bind(this);

        init();
        initListener();
    }
    public void initListener(){
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                float fadding = scrollY > fadingHeight? fadingHeight:scrollY;
                float offest = fadding / fadingHeight;
                updateTopAlpha(offest);
            }
        });
    }

    public void init(){
        topLayout.setAlpha(0);
    }

    private void updateTopAlpha(float alpha){
        topLayout.setAlpha(alpha);
    }

}
