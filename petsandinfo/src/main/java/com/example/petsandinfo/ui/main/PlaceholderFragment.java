package com.example.petsandinfo.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ArrayRes;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petsandinfo.R;
import com.example.petsandinfo.adapters.MypetRecyclerViewAdapter;
import com.example.petsandinfo.adapters.SelectionsAdapter;
import com.example.petsandinfo.model.entity.Pet;
import com.example.petsandinfo.model.entity.PetListLoadResult;
import com.example.petsandinfo.viewmodel.PageViewModel;
import com.example.petsandinfo.viewmodel.PlaceHolderViewModel;
import com.example.petsandinfo.viewmodel.PlaceHolderViewModelFactory;
import com.shay.baselibrary.ToastUntil;
import com.shay.baselibrary.enums.petInfo.PetClassesEnum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A placeholder fragment containing a simple view.
 *
 * @Describe 用于展示通用界面
 */
public class PlaceholderFragment extends Fragment {


    //cb为三大分类
    //gridview通过观察cb切换更改详细设置内容
    GridView gridView;

    CheckBox shapeCheck;
    CheckBox fetchCheck;
    CheckBox rankCheck;

    LinearLayout.LayoutParams displayParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);

    LinearLayout.LayoutParams hideParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            0);

    SelectionsAdapter selectionsAdapter;

    List<String> mSeletionList;
    List<String> mShapeSelectionList;
    List<String> mFetchSelectionList;
    List<String> mRankSelectionList;

    List<Pet> petList ;

    MypetRecyclerViewAdapter petRecylerAdapter;
    RecyclerView recyclerView;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_SECTION_NAME = "section_name";
    private static final String ARG_PARAM1 = "shapeLevel";
    private static final String ARG_PARAM2 = "fetchLevel";
    private static final String ARG_PARAM3 = "rankType";

    //标识当前界面类型
    private int classNum;
    private String name;

    //选项默认值
    private static int mShapeLevel = 0;
    private static int mFetchLevel = 0;
    private static int mRankType = 0;
    private static int mPetClass = 0;

    private PlaceHolderViewModel placeHolderViewModel;

    boolean isShowSelectGridView = false;

    public String getName() {
        return name;
    }

    public static PlaceholderFragment newInstance(int index, String className) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putString(ARG_SECTION_NAME, className);
        fragment.setArguments(bundle);
        mPetClass = index;
        Log.d("当前界面标识",  fragment+ className +index);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeHolderViewModel =new ViewModelProvider(this, new PlaceHolderViewModelFactory())
                .get(PlaceHolderViewModel.class);
        if (getArguments() != null) {
            classNum = getArguments().getInt(ARG_SECTION_NUMBER);
            name = getArguments().getString(ARG_SECTION_NAME);
        }

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_place_holder, container, false);

        //1，组件在此处实例化 2，不采用黄油刀
        shapeCheck = root.findViewById(R.id.fragment_ph_shape_selection_cb);
        fetchCheck = root.findViewById(R.id.fragment_ph_fetch_selection_cb);
        rankCheck = root.findViewById(R.id.fragment_ph_rank_selection_cb);
        gridView = root.findViewById(R.id.selection_grid_view);
        recyclerView = root.findViewById(R.id.fragment_ph_pet_rcv);

        Log.d(this.getClass().getSimpleName(), getName() + "onCreateView()");
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(this.getClass().getSimpleName(), name +"onStart()");
        mSeletionList = new ArrayList<>();
        petList = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            Pet pet = new Pet();
            pet.setPetName(String.valueOf(i));
            pet.setViewNum(String.valueOf(50));
            petList.add(pet);
        }

        petRecylerAdapter = new MypetRecyclerViewAdapter();
        petRecylerAdapter.setmValues(petList);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(petRecylerAdapter);

        selectionsAdapter = new SelectionsAdapter(mSeletionList, getActivity());
        gridView.setAdapter(selectionsAdapter);
        initListener();
        initObserver();
        initSelection();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(this.getClass().getSimpleName(), name + "onResume()");
        Log.d(this.getClass().getSimpleName(), String.valueOf(shapeCheck.hashCode()));

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(this.getClass().getSimpleName(), name + "onPause()");
    }

    private void initSelection(){
        placeHolderViewModel.LoadSelection();
    }

    private void initObserver(){

        placeHolderViewModel.getFetchLevelSelection().observe(getViewLifecycleOwner(),
                strings -> mFetchSelectionList = strings);

        placeHolderViewModel.getShapeLevelSelection().observe(getViewLifecycleOwner(),
                strings -> mShapeSelectionList = strings);

        placeHolderViewModel.getRankTypeSelection().observe(getViewLifecycleOwner(),
                strings -> mRankSelectionList = strings);

        //加载列表
        placeHolderViewModel.getPetListLoadResultLiveData().observe(getViewLifecycleOwner(),
                petListLoadResult -> {
            petRecylerAdapter.hideFootTip();
            petRecylerAdapter.notifyDataSetChanged();
        });
    }

    private void initListener(){

        shapeCheck.setOnCheckedChangeListener((compoundButton, b) -> {

            if(b){
                //可触发OnCheckedChangeListener
                fetchCheck.setChecked(false);
                rankCheck.setChecked(false);
                setSelectionContent(mShapeSelectionList);
                showSelectGridView();

                Log.d("shape", "check1");

            }else {
                hideSelectionView();
                Log.d("shape", "check2");
            }
        });

        fetchCheck.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                shapeCheck.setChecked(false);
                rankCheck.setChecked(false);
                setSelectionContent(mFetchSelectionList);
                showSelectGridView();
                Log.d("fetch", "check1");

            }else {
                hideSelectionView();
                Log.d("fetch", "check2");
            }
        });

        rankCheck.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                shapeCheck.setChecked(false);
                fetchCheck.setChecked(false);
                setSelectionContent(mRankSelectionList);
                showSelectGridView();
            }else {
                hideSelectionView();
            }
        });

        //选中筛选条件
        selectionsAdapter.setSelectionOnclickListener(new SelectionsAdapter.SelectionOnclickListener(){

            @Override
            public void onClick(int position) {
                if (shapeCheck.isChecked()){
                    mShapeLevel = position;
                    loadList(mShapeLevel, mFetchLevel, mRankType);

                }else if(fetchCheck.isChecked()){
                    mFetchLevel = position;
                    loadList(mShapeLevel, mFetchLevel, mRankType);

                }else if(rankCheck.isChecked()){
                    mRankType = position;
                    loadList(mShapeLevel, mFetchLevel, mRankType);

                }
            }
        });

        //下拉刷新
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                //
                if(newState == RecyclerView.SCROLL_STATE_IDLE){

                }

                //到底部
                if(!recyclerView.canScrollVertically(-1)){
                    petRecylerAdapter.showFootTip();
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });


        petRecylerAdapter.setPetItemOnclickListener(position -> {

            ToastUntil.showToast(petList.get(position).getPetName(), getContext());
        });

    }

    private void setSelectionContent(List list){
        selectionsAdapter.setSeletionList(list);
        selectionsAdapter.notifyDataSetChanged();
    }

    private void hideSelectionView(){
        if (isShowSelectGridView){
            gridView.setLayoutParams(hideParams);
            isShowSelectGridView = false;
        }
    }

    private void showSelectGridView(){
        if (!isShowSelectGridView){
            gridView.setLayoutParams(displayParams);
            isShowSelectGridView = true;
        }
    }


    private void loadList(int shapeLevel, int fetchLevel, int rankType){
        placeHolderViewModel.loadList(shapeLevel, fetchLevel, rankType, mPetClass);
    }

    @Override
    public void onDestroy() {
        placeHolderViewModel.cancelAsync();
        super.onDestroy();
    }
}