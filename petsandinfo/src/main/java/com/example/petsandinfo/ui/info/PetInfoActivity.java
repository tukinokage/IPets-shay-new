package com.example.petsandinfo.ui.info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.NestedScrollingChild;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.petsandinfo.R;
import com.example.petsandinfo.viewmodel.PetInfoActivityViewModel;
import com.example.petsandinfo.viewmodel.PetInfoActivityViewModelFactory;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.qmuiteam.qmui.alpha.QMUIAlphaTextView;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.shay.baselibrary.dto.Pet;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetInfoActivity extends AppCompatActivity {

    @BindView(R.id.activity_pet_info_nest_scroll_view)
    NestedScrollView nestedScrollView;

   /* @BindView(R.id.activity_pet_info_appbar)
    AppBarLayout appBarLayout;*/

    @BindView(R.id.activity_pet_info_top_layout)
    LinearLayout topLayout;

    @BindView(R.id.pet_info_top_back_btn)
    Button backButton;

    @BindView(R.id.pet_info_pic_num_tv)
    QMUIAlphaTextView textView;

    @BindView(R.id.activity_pet_info_like_fab)
    FloatingActionButton likeFB;
    @BindView(R.id.activity_pet_info_share_fab)
    FloatingActionButton shareFB;

    @BindView(R.id.activity_pet_info_name_textview)
    TextView nameTextView;
    @BindView(R.id.pet_info_introduces_head_ic)
    QMUIRadiusImageView radiusHeadImageV;
    @BindView(R.id.pet_info_introduces_tv)
    TextView introductionTv;
    @BindView(R.id.activity_pet_info_story_textview)
    TextView storyTextView;
    @BindView(R.id.activity_pet_info_warning_tv)
    TextView warningTv;


    @BindView(R.id.activity_pet_info_store_gridview)
    GridView storeGridView;
    @BindView(R.id.activity_pet_info_hospital_gv)
    GridView hospitalGridView;

    private PetInfoActivityViewModel petInfoActivityViewModel;
    //滑动隐藏距离
    private int fadingHeight = 400;
    private Pet mPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info);
        ButterKnife.bind(this);

        petInfoActivityViewModel = new ViewModelProvider(this, new PetInfoActivityViewModelFactory())
                .get(PetInfoActivityViewModel.class);
        init();
        initListener();
    }
    public void initListener(){
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            float fadding = scrollY > fadingHeight? fadingHeight:scrollY;
            float offest = fadding / fadingHeight;
            updateTopAlpha(offest);
        });
    }

    public void init(){
        //top默认透明
        topLayout.setAlpha(0);

        Bundle extras = getIntent().getExtras();
        /*
        mPet = (Pet) extras.get("pet");
        * */

        mPet = new Pet();
    }

    private void updateTopAlpha(float alpha){
        topLayout.setAlpha(alpha);
    }

}
