package com.example.petsandinfo.ui.main;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.petsandinfo.R;
import com.example.petsandinfo.adapters.SelectionsAdapter;
import com.example.petsandinfo.model.entity.PetListLoadResult;
import com.example.petsandinfo.viewmodel.PlaceHolderViewModel;
import com.example.petsandinfo.viewmodel.PlaceHolderViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class AllPetsFragment extends Fragment {


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


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AllPetsFragment() {
    }
    public String getName() {
        return name;
    }
    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AllPetsFragment newInstance(int num, String name) {
        AllPetsFragment fragment = new AllPetsFragment();
        Bundle args = new Bundle();
        mPetClass = num;
        args.putInt(ARG_SECTION_NUMBER, num);
        args.putString(ARG_SECTION_NAME, name);

        fragment.setArguments(args);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_all_pets, container, false);

        selectionsAdapter = new SelectionsAdapter(mSeletionList, getActivity());
        //1，组件在此处实例化 2，不可采用黄油刀
        shapeCheck = root.findViewById(R.id.fragment_ph_shape_selection_cb);
        fetchCheck = root.findViewById(R.id.fragment_ph_fetch_selection_cb);
        rankCheck = root.findViewById(R.id.fragment_ph_rank_selection_cb);
        gridView = root.findViewById(R.id.selection_grid_view);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        ButterKnife.bind(this, getActivity());

        Log.d("当前界面标识", "全部0");
        mSeletionList = new ArrayList<>();
        selectionsAdapter = new SelectionsAdapter(mSeletionList, getActivity());
        gridView.setAdapter(selectionsAdapter);
        initListener();
        initObserver();
        initSelection();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */



    private void initSelection(){
        placeHolderViewModel.LoadSelection();
    }

    private void initObserver(){


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
