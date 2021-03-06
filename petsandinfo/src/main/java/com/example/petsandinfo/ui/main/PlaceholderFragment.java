package com.example.petsandinfo.ui.main;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.petsandinfo.R;
import com.example.petsandinfo.adapters.MypetRecyclerViewAdapter;
import com.example.petsandinfo.adapters.SelectionsAdapter;
import com.example.petsandinfo.views.AdvanceSwipeRefreshLayout;
import com.shay.baselibrary.AroutePath;
import com.shay.baselibrary.dto.Pet;
import com.example.petsandinfo.viewmodel.PlaceHolderViewModel;
import com.example.petsandinfo.viewmodel.PlaceHolderViewModelFactory;
import com.shay.baselibrary.ToastUntil;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 *
 * @Describe 用于展示通用界面
 */
public class PlaceholderFragment extends Fragment {
    //cb为三大分类
    //gridview通过观察cb切换更改详细设置内容
    private GridView gridView;
    private CheckBox shapeCheck;
    private CheckBox fetchCheck;
    private CheckBox rankCheck;
    private AdvanceSwipeRefreshLayout swipeRefreshLayout;

    LinearLayout.LayoutParams displayParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);

    LinearLayout.LayoutParams hideParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            0);

    private SelectionsAdapter selectionsAdapter;

    private List<String> mSeletionList;
    private List<String> mShapeSelectionList;
    private List<String> mFetchSelectionList;
    private List<String> mRankSelectionList;

    private List<Pet> petList ;

    private MypetRecyclerViewAdapter petRecylerAdapter;
    private RecyclerView recyclerView;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_SECTION_NAME = "section_name";
    private static final String ARG_PARAM1 = "shapeLevel";
    private static final String ARG_PARAM2 = "fetchLevel";
    private static final String ARG_PARAM3 = "rankType";

    //标识当前界面类型
    private int classNum;
    private  String name;

    //选项默认值
    private  int mShapeLevel = 0;
    private  int mFetchLevel = 0;
    private  int mRankType = 1;
    private int mPetClass;

    private PlaceHolderViewModel placeHolderViewModel;

    boolean isShowSelectGridView = false;
    boolean isLoading = false;

    public String getName() {
        return name;
    }

    public static PlaceholderFragment newInstance(int index, String className) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putString(ARG_SECTION_NAME, className);
        fragment.setArguments(bundle);
        Log.d("当前界面标识",  fragment+ className +index);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeHolderViewModel =new ViewModelProvider(this, new PlaceHolderViewModelFactory())
                .get(PlaceHolderViewModel.class);
        if (getArguments() != null) {
            mPetClass = getArguments().getInt(ARG_SECTION_NUMBER);
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
        swipeRefreshLayout = root.findViewById(R.id.fragment_ph_swipe_refresh_layout);


        Log.d(this.getClass().getSimpleName(), getName() + "onCreateView()");
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(this.getClass().getSimpleName(), name +"onStart()");
        mSeletionList = new ArrayList<>();
        petList = new ArrayList<>();


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
        loadList(mShapeLevel, mFetchLevel, mRankType);

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
            if(petListLoadResult.hasError()){
                Toast.makeText(getContext(), petListLoadResult.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }

            isLoading = false;

        });

        placeHolderViewModel.getpetListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Pet>>() {
            @Override
            public void onChanged(List<Pet> petList) {
                petRecylerAdapter.hideFootTip();
                petRecylerAdapter.setmValues(petList);
                petRecylerAdapter.notifyDataSetChanged();
                PlaceholderFragment.this.petList = petList;
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
                    mRankType = position + 1;
                    loadList(mShapeLevel, mFetchLevel, mRankType);

                }
            }
        });


        //下拉刷新
        swipeRefreshLayout.setInterceptEventConditionListener(new AdvanceSwipeRefreshLayout.InterceptEventConditionListener() {
            @Override
            public boolean isInterceptTouchEvent() {
               LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                assert layoutManager != null;
                int lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                return lastCompletelyVisibleItemPosition <= 0;
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onRefresh() {
                new AsyncTask<String, String, String>(){

                    @SuppressLint("StaticFieldLeak")
                    @Override
                    protected String doInBackground(String... strings) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(String s) {

                        ToastUntil.showToast("刷新完毕", getContext());
                        swipeRefreshLayout.setRefreshing(false);
                        petRecylerAdapter.setmValues(petList);
                    }
                }.execute();
            }
        });

        //上拉刷新
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                //
                if(newState == RecyclerView.SCROLL_STATE_IDLE){

                }

            }

            @SuppressLint("StaticFieldLeak")
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //到底部
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                assert layoutManager != null;
                int lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                Log.i("刷新", "lastCompletelyVisibleItemPosition: "+lastCompletelyVisibleItemPosition);
                if(lastCompletelyVisibleItemPosition == layoutManager.getItemCount()-1){
                    Log.d("刷新", "滑动到底部" + layoutManager.getItemCount());
                    if(layoutManager.getItemCount() == petRecylerAdapter.getItemCount()){
                        if(isLoading){
                            return;
                        }else {
                            if(placeHolderViewModel.hasMore()){
                                loadLoadMore(mShapeLevel, mFetchLevel, mRankType);
                                isLoading = true;
                                petRecylerAdapter.hasMoreData();
                                petRecylerAdapter.showFootTip();

                            }else {
                                petRecylerAdapter.noMoreData();
                                petRecylerAdapter.showFootTip();
                            }

                        }

                    }
                }
            }
        });


        petRecylerAdapter.setPetItemOnclickListener(position -> {
            ToastUntil.showToast(petList.get(position).getPetName(), getContext());
            ARouter.getInstance().build(AroutePath.PetInfoActivity).withParcelable(AroutePath.paramName.MPET, petList.get(position)).navigation();
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

    private void loadLoadMore(int shapeLevel, int fetchLevel, int rankType){
        placeHolderViewModel.loadMore(shapeLevel, fetchLevel, rankType, mPetClass);
    }

    @Override
    public void onDestroy() {
        placeHolderViewModel.cancelAsync();
        super.onDestroy();
    }
}