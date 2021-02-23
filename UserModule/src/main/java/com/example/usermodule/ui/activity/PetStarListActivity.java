package com.example.usermodule.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.telecom.Call;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.usermodule.R;
import com.example.usermodule.adapters.MyPetRecyclerViewAdapter;
import com.example.usermodule.entity.result.GetStarPetListResult;
import com.example.usermodule.viewmodel.GetDailyRecordListViewModel;
import com.example.usermodule.viewmodel.GetPetStarListViewModel;
import com.example.usermodule.viewmodel.GetPetStarViewModelFactory;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.AroutePath;
import com.shay.baselibrary.ToastUntil;
import com.shay.baselibrary.dto.Pet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = AroutePath.PetStarActivity)
public class PetStarListActivity extends AppCompatActivity {
    public   int PER_PAPER_NUM = 15;
    public   int CURRENT_PAPER_NUM = 1;
    private boolean HASH_MORE = true;
    private boolean IS_LOADING_MORE = false;
   @BindView(R.id.star_pet_list_rv)
    RecyclerView starPetListRecycler;
   @BindView(R.id.main_activity_go_register_tv)
    TextView backTv;

    GetPetStarListViewModel getPetStarListViewModel;
    MyPetRecyclerViewAdapter petRecyclerViewAdapter;

    @Autowired
    public String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_star_list);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);

        getPetStarListViewModel = new ViewModelProvider(this, new GetPetStarViewModelFactory())
                .get(GetPetStarListViewModel.class);

        init();
        initListener();
        initObserver();
    }

    private  void init(){
        petRecyclerViewAdapter = new MyPetRecyclerViewAdapter();
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        starPetListRecycler.setLayoutManager(layoutManager);
        starPetListRecycler.setAdapter(petRecyclerViewAdapter);

        getPetStarListViewModel.getStarPetListData(userId, PER_PAPER_NUM, CURRENT_PAPER_NUM);
    }

    private  void initListener(){
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        petRecyclerViewAdapter.setPetItemOnclickListener(new MyPetRecyclerViewAdapter.PetItemOnclickListener() {
            @Override
            public void onClick(int position) {
                Pet pet = getPetStarListViewModel.getCurrentList().get(position);
                ARouter.getInstance().build(AroutePath.PetInfoActivity).withParcelable(AroutePath.paramName.MPET, pet);
            }
        });

        petRecyclerViewAdapter.setUnlikeOnclickListener(new MyPetRecyclerViewAdapter.PetItemOnclickListener() {
            @Override
            public void onClick(int position) {
                String petId = getPetStarListViewModel.getCurrentList().get(position).getPetId();
                getPetStarListViewModel.removeIndexPet(position, userId);
            }
        });


        starPetListRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //到底部
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                assert layoutManager != null;
                int lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                Log.i("刷新", "lastCompletelyVisibleItemPosition: "+lastCompletelyVisibleItemPosition);
                if(lastCompletelyVisibleItemPosition == layoutManager.getItemCount() -1){
                    Log.d("刷新", "滑动到底部" + layoutManager.getItemCount());
                    if(layoutManager.getItemCount() == petRecyclerViewAdapter.getItemCount()){
                        if(IS_LOADING_MORE){
                            return;
                        }

                        if(HASH_MORE){
                            ToastUntil.showToast("正在加载", AppContext.getContext());
                            IS_LOADING_MORE = true;
                            getPetStarListViewModel.getStarPetListData(userId, PER_PAPER_NUM, CURRENT_PAPER_NUM);
                        }else {
                            ToastUntil.showToast("已无更多", AppContext.getContext());
                        }
                    }
                }
            }
        });

    }

    private void initObserver(){
        getPetStarListViewModel.getPetListMutableLiveData().observe(this, new Observer<List<Pet>>() {
            @Override
            public void onChanged(List<Pet> petList) {
                petRecyclerViewAdapter.setmValues(petList);
                petRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        getPetStarListViewModel.getStarPetListResultMutableLiveData().observe(this, new Observer<GetStarPetListResult>() {
            @Override
            public void onChanged(GetStarPetListResult getStarPetListResult) {
                if (TextUtils.isEmpty(getStarPetListResult.getErrorMsg())){
                    CURRENT_PAPER_NUM += 1;
                    HASH_MORE = getStarPetListResult.isHasMore();
                    getPetStarListViewModel.addPetList(getStarPetListResult.getPetList());
                    IS_LOADING_MORE = false;

                    if(HASH_MORE){
                        petRecyclerViewAdapter.hasMoreData();
                    }else {
                        petRecyclerViewAdapter.noMoreData();
                    }
                } else {
                    ToastUntil.showToast(getStarPetListResult.getErrorMsg(), AppContext.getContext());
                }
            }
        });
    }


}
