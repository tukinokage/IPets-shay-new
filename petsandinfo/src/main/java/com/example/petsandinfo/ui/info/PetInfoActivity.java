package com.example.petsandinfo.ui.info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.petsandinfo.R;
import com.example.petsandinfo.R2;
import com.example.petsandinfo.adapters.HospitalGridViewAdapter;
import com.example.petsandinfo.adapters.PetInfoPicRvAdapter;
import com.example.petsandinfo.adapters.StoreGridViewAdapter;
import com.example.petsandinfo.entity.Conditions.LoadPetHospitalCondition;
import com.example.petsandinfo.entity.Conditions.LoadPetIntroductionCondition;
import com.example.petsandinfo.entity.Conditions.LoadPetPicCondition;
import com.example.petsandinfo.entity.Conditions.LoadPetStoreCondition;
import com.example.petsandinfo.model.Hospital;
import com.example.petsandinfo.model.Store;
import com.example.petsandinfo.ui.main.DetailsPagerAdapter;
import com.example.petsandinfo.viewmodel.PetInfoActivityViewModel;
import com.example.petsandinfo.viewmodel.PetInfoActivityViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.qmuiteam.qmui.alpha.QMUIAlphaTextView;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.shay.baselibrary.UrlInfoUtil.UrlUtil;
import com.shay.baselibrary.UserInfoUtil.UserInfoUtil;
import com.shay.baselibrary.dto.Pet;
import com.shay.baselibrary.*;
import com.shay.baselibrary.enums.petInfo.*;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
@Route(path = AroutePath.PetInfoActivity)
public class PetInfoActivity extends AppCompatActivity {

    @BindView(R2.id.activity_pet_info_nest_scroll_view)
    NestedScrollView nestedScrollView;

    @BindView(R2.id.activity_pet_info_top_layout)
    LinearLayout topLayout;

    @BindView(R2.id.pet_info_top_back_btn)
    Button backButton;

    @BindView(R2.id.pet_info_pic_num_tv)
    QMUIAlphaTextView numTextView;

    @BindView(R2.id.activity_pet_info_like_fab)
    TextView likeFB;
    @BindView(R2.id.activity_pet_info_share_fab)
    TextView shareFB;

    @BindView(R2.id.activity_pet_info_name_textview)
    TextView nameTextView;
    @BindView(R2.id.pet_info_introduces_head_ic)
    QMUIRadiusImageView radiusHeadImageV;
    @BindView(R2.id.pet_info_introduces_tv)
    TextView introductionTv;
    @BindView(R2.id.activity_pet_info_story_textview)
    TextView storyTextView;
    @BindView(R2.id.activity_pet_info_warning_tv)
    TextView warningTv;
    @BindView(R2.id.pet_info_pic_view_pager)
    ViewPager picViewPager;

    DetailsPagerAdapter detailsPagerAdapter;

    @BindView(R2.id.activity_pet_info_store_gridview)
    GridView storeGridView;
    @BindView(R2.id.activity_pet_info_hospital_gv)
    GridView hospitalGridView;

    private Unbinder unbinder;
    private PetInfoActivityViewModel petInfoActivityViewModel;
    //滑动隐藏距离
    private int fadingHeight = 400;
    int currentPicIndex = 0;
    int currentPicListLength = 0;

    @Autowired
    public Pet mPet;

    private HospitalGridViewAdapter hospitalGridViewAdapter;
    private PetInfoPicRvAdapter petInfoPicRvAdapter;
    private StoreGridViewAdapter storeGridViewAdapter;

    ArrayList<Hospital> hospitals = new ArrayList<>();
    ArrayList<Store> stores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info);
        unbinder = ButterKnife.bind(this);
        ARouter.getInstance().inject(this);

        petInfoActivityViewModel = new ViewModelProvider(this, new PetInfoActivityViewModelFactory())
                .get(PetInfoActivityViewModel.class);
        init();
        initIntroductionParam();
        initObserver();
        initListener();
    }
    public void initListener(){
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            float fadding = scrollY > fadingHeight? fadingHeight:scrollY;
            float offest = fadding / fadingHeight;
            updateTopAlpha(offest);
        });

        likeFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!UserInfoUtil.isLogin()){
                    ToastUntil.showToast("请先登录", AppContext.getContext());
                    return;
                }
                try {
                    petInfoActivityViewModel.starPet(mPet.getPetId());
                }
                catch (Exception e) {
                    Toast.makeText(PetInfoActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        shareFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUntil.showToast("暂未开放微博分享", AppContext.getContext());
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        numTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPicListLength == 0){
                    numTextView.setText("暂时没有图片" );
                    return;
                }
                if(currentPicIndex + 1 == currentPicListLength){
                    currentPicIndex = 0;
                    picViewPager.setCurrentItem(currentPicIndex);
                }else {
                    picViewPager.setCurrentItem(++currentPicIndex);
                }

                numTextView.setText("查看下一张: " + (currentPicIndex + 1) + "/" + currentPicListLength);

            }
        });

        storeGridViewAdapter.setStoreOnclickListener(new StoreGridViewAdapter.StoreOnclickListener() {
            @Override
            public void onClick(int position) {
                JumpToTianMaoUtils.totaoBao(PetInfoActivity.this, stores.get(position).getStoreUrl());
            }
        });

        hospitalGridViewAdapter.setHospitalOnclickListener(new HospitalGridViewAdapter.HospitalOnclickListener() {
            @Override
            public void onClick(int position) {
                Uri uri = Uri.parse(hospitals.get(position).getHospitalUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                PetInfoActivity.this.startActivity(intent);
            }
        });

    }

    private void init()  {
        //top默认透明
        topLayout.setAlpha(0);

        Window window = getWindow();
        View decorView = window.getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        hospitalGridViewAdapter = new HospitalGridViewAdapter(hospitals, this);
        storeGridViewAdapter = new StoreGridViewAdapter(stores, this);
        petInfoPicRvAdapter = new PetInfoPicRvAdapter(this);
        storeGridView.setAdapter(storeGridViewAdapter);
        hospitalGridView.setAdapter(hospitalGridViewAdapter);

        Bundle extras = getIntent().getExtras();
        //mPet = (Pet) extras.get("pet");

        if(mPet == null){
            Toast.makeText(this, "未获取到宠物", Toast.LENGTH_SHORT).show();
            return;
        }

       //mPet = new Pet();

    }

    private void initIntroductionParam(){
        petInfoActivityViewModel.loadHeadPic( mPet.getPetHeadImg() );

        nameTextView.setText(mPet.getPetName());
        introductionTv.setText("全名：" + mPet.getPetName() + "\n"
                + "英文名：" + mPet.getPetEnglishName() + "\n"
                + "产地：" + mPet.getOriginPlace() + "\n"
                + "成年雄性体重：" + mPet.getMaleWeight() + "\n"
                + "成年雌性体重：" + mPet.getFemaleWeight() + "\n"
                + "分类：" + PetClassesEnum.getEnumByNum(mPet.getPetClass()).getChinese() +"\n");

        try {
            petInfoActivityViewModel.checkIsStar(mPet.getPetId());
        } catch (Exception e) {
            Toast.makeText(this, "本地访问出错了", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        petInfoActivityViewModel.loadPetIntroduction(new LoadPetIntroductionCondition(mPet.getPetId()));
        petInfoActivityViewModel.loadPetHospitalList(new LoadPetHospitalCondition(mPet.getPetId()));
        petInfoActivityViewModel.loadPetPicNameList(new LoadPetPicCondition(mPet.getPetId()));
        petInfoActivityViewModel.loadPetStoreList(new LoadPetStoreCondition(mPet.getPetId()));

    }

    private void initObserver(){

        petInfoActivityViewModel.getStartPetResultMutableLiveData().observe(this, startPetResult->{
            if(TextUtils.isEmpty(startPetResult.getErrorMsg())){
                try {
                    petInfoActivityViewModel.checkIsStar(mPet.getPetId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(this, startPetResult.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }
        });

        petInfoActivityViewModel.getCheckPetIsStarResultMutableLiveData().observe(this, checkPetIsStarResult->{
            if(TextUtils.isEmpty(checkPetIsStarResult.getErrorMsg())){
                if(checkPetIsStarResult.isStar()){
                    likeFB.setBackground(getDrawable(R.drawable.ic_like_liked));
                }else {
                    likeFB.setBackground(getDrawable(R.drawable.ic_like_defult));
                }


            }else {
                Toast.makeText(this, checkPetIsStarResult.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }
        });

        petInfoActivityViewModel.getIntroduceResultMutableLiveData().observe(this, loadIntroduceResult -> {

            if(loadIntroduceResult.hasError()){

            }else {
                warningTv.setText(loadIntroduceResult.getData().getPetAttention());
                storyTextView.setText(loadIntroduceResult.getData().getPetStory());
            }
        });

        petInfoActivityViewModel.getLoadHospitalResultMutableLiveData().observe(this, loadHospitalResult -> {

            if(loadHospitalResult.hasError()){

            }else {
                hospitals = (ArrayList<Hospital>) loadHospitalResult.getData();
                hospitalGridViewAdapter.setSeletionList(loadHospitalResult.getData());
                hospitalGridViewAdapter.notifyDataSetChanged();
            }
        });

        petInfoActivityViewModel.getLoadPetPicNameResulticMutableLiveData().observe(this, loadPetPicNameResult -> {

            if(loadPetPicNameResult.hasError()){

            }else {
                petInfoPicRvAdapter.setPicNameList(loadPetPicNameResult.getData());
                petInfoPicRvAdapter.notifyDataSetChanged();

                //测试显示

                if(loadPetPicNameResult.getData() == null || loadPetPicNameResult.getData().isEmpty()){
                    return;
                }

                detailsPagerAdapter = new DetailsPagerAdapter(loadPetPicNameResult.getData());
                currentPicListLength = loadPetPicNameResult.getData().size();
                picViewPager.setAdapter(detailsPagerAdapter);
                numTextView.setText("查看下一张: " + (currentPicIndex + 1) + "/" + currentPicListLength);
              /*  if(!loadPetPicNameResult.getData().isEmpty()){
                    Glide.with(AppContext.getContext())
                            .load(UrlUtil.STATIC_RESOURCE.PET_PIC_URL + loadPetPicNameResult.getData().get(0))
                            .into()
                            .onLoadStarted(AppContext.getContext().getDrawable(R.color.material_blue_200));
                }
*/

            }
        });

        petInfoActivityViewModel.getLoadStoreResultMutableLiveData().observe(this, loadStoreResult -> {

            if(loadStoreResult.hasError()){

            }else {
                stores = (ArrayList<Store>) loadStoreResult.getData();
                storeGridViewAdapter.setStoreList(loadStoreResult.getData());
                storeGridViewAdapter.notifyDataSetChanged();
            }
        });

        petInfoActivityViewModel.getBitmapMutableLiveData().observe(this, new Observer<Drawable>() {
            @Override
            public void onChanged(Drawable drawable) {
                radiusHeadImageV.setImageDrawable(drawable);
            }
        });

    }

    private void updateTopAlpha(float alpha){
        topLayout.setAlpha(alpha);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        petInfoActivityViewModel.cancelAsyncTask();
        unbinder.unbind();
    }
}
