package com.example.usermodule.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.usermodule.R;
import com.example.usermodule.R2;
import com.example.usermodule.adapters.UserCommentViewAdapter;
import com.example.usermodule.entity.result.GetStarPetListResult;
import com.example.usermodule.entity.result.GetUserCommentResult;
import com.example.usermodule.viewmodel.GetUserCommentListViewModel;
import com.example.usermodule.viewmodel.GetUserCommentViewModelFactory;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.AroutePath;
import com.shay.baselibrary.ToastUntil;
import com.shay.baselibrary.dto.Pet;
import com.shay.baselibrary.dto.UserCommentItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = AroutePath.CommentActivity)
public class CommentListActivity extends AppCompatActivity {
    public  int PER_PAPER_NUM = 15;
    public  int CURRENT_PAPER_NUM = 1;
    private boolean HASH_MORE = true;
    private boolean IS_LOADING_MORE = false;

    @Autowired
    public String userId;

    @BindView(R2.id.comment_list_rv)
     RecyclerView recyclerView;
    @BindView(R2.id.main_activity_go_register_tv)
    TextView backTv;

    GetUserCommentListViewModel commentListViewModel;
    UserCommentViewAdapter userCommentViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);

        commentListViewModel = new ViewModelProvider(this, new GetUserCommentViewModelFactory())
                .get(GetUserCommentListViewModel.class);

        init();
        initListener();
        initObserver();
    }

    private void init(){
        userCommentViewAdapter = new UserCommentViewAdapter(this);
        recyclerView.setAdapter(userCommentViewAdapter);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        commentListViewModel.getCommentListData(userId, PER_PAPER_NUM, CURRENT_PAPER_NUM);
    }


    private void initListener(){
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    if(layoutManager.getItemCount() == userCommentViewAdapter.getItemCount()){
                        if(IS_LOADING_MORE){
                            return;
                        }

                        if(HASH_MORE){
                            ToastUntil.showToast("正在加载", AppContext.getContext());
                            IS_LOADING_MORE = true;
                            commentListViewModel.getCommentListData(userId, PER_PAPER_NUM, CURRENT_PAPER_NUM );
                        }else {
                            ToastUntil.showToast("已无更多", AppContext.getContext());
                        }
                    }
                }
            }
        });

        userCommentViewAdapter.setItemClickedListener(new UserCommentViewAdapter.ItemClickedListener() {
            @Override
            public void onclick(int position) {
                String posId = commentListViewModel.getCurrentList().get(position).getPostId();
                ARouter.getInstance().build(AroutePath.PostInfoActivity).withString(AroutePath.paramName.POST_ID, posId).navigation();
            }
        });

    }




    private void initObserver(){
        commentListViewModel.getCommentListMutableData().observe(this, new Observer<List<UserCommentItem>>() {
            @Override
            public void onChanged(List<UserCommentItem> commentItems) {
                userCommentViewAdapter.setmValues(commentItems);
                userCommentViewAdapter.notifyDataSetChanged();
            }
        });

        commentListViewModel.getUserCommentResultMutableLiveData().observe(this, new Observer<GetUserCommentResult>() {
            @Override
            public void onChanged(GetUserCommentResult commentResult) {
                if (TextUtils.isEmpty(commentResult.getErrorMsg())){
                    CURRENT_PAPER_NUM += 1;
                    HASH_MORE = commentResult.isHasMore();
                    commentListViewModel.addCommentList(commentResult.getUserCommentItemList());
                    IS_LOADING_MORE = false;

                    if(HASH_MORE){
                        userCommentViewAdapter.hasMoreData();
                    }else {
                        userCommentViewAdapter.noMoreData();
                    }
                } else {
                    ToastUntil.showToast(commentResult.getErrorMsg(), AppContext.getContext());
                }
            }
        });

    }
}
