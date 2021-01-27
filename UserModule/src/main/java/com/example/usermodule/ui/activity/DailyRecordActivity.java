package com.example.usermodule.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.usermodule.R;
import com.example.usermodule.adapters.DailyRecordViewAdapter;
import com.example.usermodule.entity.result.GetDaliyRecordResult;
import com.example.usermodule.viewmodel.GetDailyRecordListViewModel;
import com.example.usermodule.viewmodel.GetDailyViewModelFactory;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.AroutePath;
import com.shay.baselibrary.ToastUntil;
import com.shay.baselibrary.dto.UserCommentItem;
import com.shay.baselibrary.dto.UserDailyRecordItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = AroutePath.DailyRecordActivity)
public class DailyRecordActivity extends AppCompatActivity {
    public static final String PARAM_NAME = "userId";
    public  static int PER_PAPER_NUM = 15;
    public  static int CURRENT_PAPER_NUM = 1;
    public  static int CURRENT_TYPE = 0;
    private boolean HASH_MORE = true;
    private boolean IS_LOADING_MORE = false;
    GetDailyRecordListViewModel dailyRecordListViewModel;
    DailyRecordViewAdapter dailyRecordViewAdapter;
    @BindView(R.id.daily_list_rv)
    RecyclerView recyclerView;

    @Autowired
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_record);
        ButterKnife.bind(this);
        dailyRecordListViewModel = new ViewModelProvider(this, new GetDailyViewModelFactory())
                .get(GetDailyRecordListViewModel.class);

        init();
        initListener();
        initObserver();
    }

    private void init(){
        dailyRecordViewAdapter = new DailyRecordViewAdapter(this);
        recyclerView.setAdapter(dailyRecordViewAdapter);
    }


    private void initListener(){

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //到底部
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                assert layoutManager != null;
                int spanCount = layoutManager.getSpanCount();
                int[] lastPositions = new int[spanCount];
                int[] lastCompletelyVisibleItemPosition = layoutManager.findLastVisibleItemPositions(lastPositions);
                if(lastCompletelyVisibleItemPosition[0] == layoutManager.getItemCount() -1){
                    Log.d("刷新", "滑动到底部" + layoutManager.getItemCount());
                    if(layoutManager.getItemCount() == dailyRecordViewAdapter.getItemCount()){
                        if(IS_LOADING_MORE){
                            return;
                        }

                        if(HASH_MORE){
                            ToastUntil.showToast("正在加载", AppContext.getContext());
                            IS_LOADING_MORE = true;
                            dailyRecordListViewModel.getDailyRecordData(userId, PER_PAPER_NUM, CURRENT_PAPER_NUM + 1);
                        }else {
                            ToastUntil.showToast("已无更多", AppContext.getContext());
                        }
                    }
                }
            }
        });

    }




    private void initObserver(){
        dailyRecordListViewModel.getRecordListMutableLiveData().observe(this, new Observer<List<UserDailyRecordItem>>() {
            @Override
            public void onChanged(List<UserDailyRecordItem> userDailyRecordItems) {
                dailyRecordViewAdapter.setmValues(userDailyRecordItems);
                dailyRecordViewAdapter.notifyDataSetChanged();
            }
        });

        dailyRecordListViewModel.getRecordResultMutableLiveData().observe(this, new Observer<GetDaliyRecordResult>() {
            @Override
            public void onChanged(GetDaliyRecordResult getDaliyRecordResult) {
                if (TextUtils.isEmpty(getDaliyRecordResult.getErrorMsg())){
                    CURRENT_PAPER_NUM += 1;
                    HASH_MORE = getDaliyRecordResult.isHasMore();
                    dailyRecordListViewModel.addDailyRecordList(getDaliyRecordResult.getDailyRecordItemList());
                    IS_LOADING_MORE = false;

                    if(HASH_MORE){
                        dailyRecordViewAdapter.hasMoreData();
                    }else {
                        dailyRecordViewAdapter.noMoreData();
                    }
                } else {
                    ToastUntil.showToast(getDaliyRecordResult.getErrorMsg(), AppContext.getContext());
                }
            }
        });

    }
}
