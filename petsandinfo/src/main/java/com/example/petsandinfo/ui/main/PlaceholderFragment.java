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

import com.example.petsandinfo.R;
import com.example.petsandinfo.adapters.SelectionsAdapter;
import com.example.petsandinfo.model.entity.PetListLoadResult;
import com.example.petsandinfo.viewmodel.PageViewModel;
import com.example.petsandinfo.viewmodel.PlaceHolderViewModel;
import com.example.petsandinfo.viewmodel.PlaceHolderViewModelFactory;
import com.shay.baselibrary.enums.petInfo.PetClassesEnum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 *
 * @Describe 用于展示通用界面
 */
public class PlaceholderFragment extends Fragment {

    @BindView(R.id.selection_grid_view)
    GridView gridView;
    @BindView(R.id.fragment_ph_shape_selection_cb)
    CheckBox shapeCheck;
    @BindView(R.id.fragment_ph_fetch_selection_cb)
    CheckBox fetchCheck;
    @BindView(R.id.fragment_ph_rank_selection_cb)
    CheckBox rankCheck;

    @BindView(R.id.section_label)
     TextView textView;
    LinearLayout.LayoutParams displayParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);

    LinearLayout.LayoutParams hideParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            0);

    SelectionsAdapter selectionsAdapter;

    List<String> mSeletionList;
    List<String> mShapeSelectionList;
    List<String> mFetchSelectionList;
    List<String> mRankSelectionList;

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

    private PageViewModel pageViewModel;
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
       /* bundle.putInt(ARG_PARAM1, mShapeLevel);
        bundle.putInt(ARG_PARAM2, mFetchLevel);
        bundle.putInt(ARG_PARAM3, mRankType);*/
        fragment.setArguments(bundle);
        mPetClass = index;
        Log.d("当前界面标识",  fragment+ className +index);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        placeHolderViewModel =new ViewModelProvider(this, new PlaceHolderViewModelFactory())
                .get(PlaceHolderViewModel.class);


        if (getArguments() != null) {
            classNum = getArguments().getInt(ARG_SECTION_NUMBER);
            name = getArguments().getString(ARG_SECTION_NAME);
        }
        pageViewModel.setIndex(classNum);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_place_holder, container, false);

        Log.d(this.getClass().getSimpleName(), "onCreateView()");
        return root;
    }

    @Override
    public void onStart() {
        Log.d(this.getClass().getSimpleName(), "start（）" + name);
        super.onStart();
        ButterKnife.bind(this, getActivity());
        mSeletionList = new ArrayList<>();
        selectionsAdapter = new SelectionsAdapter(mSeletionList, getActivity());
        gridView.setAdapter(selectionsAdapter);
        Log.d(this.getClass().getSimpleName(), "onStart()");
        initListener();
        initObserver();
        initSelection();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(this.getClass().getSimpleName(), "onResume()");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(this.getClass().getSimpleName(), "onPause()");
    }

    private void initSelection(){
        placeHolderViewModel.LoadSelection();
    }
    private void initObserver(){

        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        placeHolderViewModel.getFetchLevelSelection().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                mFetchSelectionList = strings;
            }
        });

        placeHolderViewModel.getShapeLevelSelection().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                mShapeSelectionList = strings;
            }
        });

        placeHolderViewModel.getRankTypeSelection().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                mRankSelectionList = strings;
            }
        });

        //加载列表
        placeHolderViewModel.getPetListLoadResultLiveData().observe(getViewLifecycleOwner(), new Observer<PetListLoadResult>() {
            @Override
            public void onChanged(PetListLoadResult petListLoadResult) {

            }
        });
    }

    private void initListener(){

        shapeCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    //可触发OnCheckedChangeListener
                    fetchCheck.setChecked(false);
                    rankCheck.setChecked(false);
                    setSelectionContent(mShapeSelectionList);
                    showSelectGridView();
                }else {
                    hideSelectionView();
                }
            }
        });

        fetchCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    shapeCheck.setChecked(false);
                    rankCheck.setChecked(false);
                    setSelectionContent(mFetchSelectionList);
                    showSelectGridView();
                }else {
                    hideSelectionView();
                }
            }
        });

        rankCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    shapeCheck.setChecked(false);
                    fetchCheck.setChecked(false);
                    setSelectionContent(mRankSelectionList);
                    showSelectGridView();
                }else {
                    hideSelectionView();
                }
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


    }

    private void setSelectionContent(List list){
        selectionsAdapter.setSeletionList(list);
        selectionsAdapter.notifyDataSetChanged();
    }

    private void hideSelectionView(){
        if (isShowSelectGridView){
            gridView.setLayoutParams(hideParams);
        }
    }

    private void showSelectGridView(){
        if (!isShowSelectGridView){
            gridView.setLayoutParams(displayParams);
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