package com.example.usermodule.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.usermodule.R;
import com.example.usermodule.adapters.MyPetRecyclerViewAdapter;
import com.example.usermodule.viewmodel.GetDailyRecordListViewModel;
import com.example.usermodule.viewmodel.GetPetStarListViewModel;
import com.example.usermodule.viewmodel.GetPetStarViewModelFactory;
import com.shay.baselibrary.AroutePath;
import com.shay.baselibrary.dto.Pet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = AroutePath.PetStarActivity)
public class PetStarListActivity extends AppCompatActivity {

   @BindView(R.id.star_pet_list_rv)
    RecyclerView starPetListRecycler;
   @BindView(R.id.user_info_top_back_tv)
    TextView backTv;

    GetPetStarListViewModel getPetStarListViewModel;

   MyPetRecyclerViewAdapter petRecyclerViewAdapter;

    @Autowired
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_star_list);
        ButterKnife.bind(this);

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

            }
        });

        petRecyclerViewAdapter.setUnlikeOnclickListener(new MyPetRecyclerViewAdapter.PetItemOnclickListener() {
            @Override
            public void onClick(int position) {

            }
        });
    }

    private void initObserver(){

    }


}
