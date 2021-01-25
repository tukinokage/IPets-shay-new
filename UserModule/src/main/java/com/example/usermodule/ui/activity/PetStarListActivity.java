package com.example.usermodule.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.usermodule.R;
import com.example.usermodule.adapters.MyPetRecyclerViewAdapter;
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

   MyPetRecyclerViewAdapter petRecyclerViewAdapter;


    @Autowired
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_star_list);
        ButterKnife.bind(this);
    }

    private  void init(){


        petRecyclerViewAdapter = new MyPetRecyclerViewAdapter();
        petRecyclerViewAdapter.setmValues(petList);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        starPetListRecycler.setLayoutManager(layoutManager);
        starPetListRecycler.setAdapter(petRecyclerViewAdapter);
    }

    private  void initListener(){
        petRecyclerViewAdapter.setPetItemOnclickListener(new MyPetRecyclerViewAdapter.PetItemOnclickListener() {
            @Override
            public void onClick(int position) {
                petList.remove(position);
            }
        });

        petRecyclerViewAdapter.setUnlikeOnclickListener(new MyPetRecyclerViewAdapter.PetItemOnclickListener() {
            @Override
            public void onClick(int position) {

            }
        });
    }

    private  void initObserver(){

    }


}
