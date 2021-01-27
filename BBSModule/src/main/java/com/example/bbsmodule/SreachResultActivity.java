package com.example.bbsmodule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.bbsmodule.adapter.PostsAdapter;
import com.example.bbsmodule.entity.BBSPost;
import com.example.bbsmodule.entity.result.GetPostListResult;
import com.example.bbsmodule.viewmodel.BBSViewModel;
import com.example.bbsmodule.viewmodel.BBSViewModelFactory;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.AroutePath;
import com.shay.baselibrary.ToastUntil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = AroutePath.SearchResultActivity)
public class SreachResultActivity extends AppCompatActivity {
    public  static int PER_PAPER_NUM = 15;
    public  static int CURRENT_PAPER_NUM = 1;
    public  static int CURRENT_TYPE = 0;
    private boolean HASH_MORE = true;
    private boolean IS_LOADING_MORE = false;

    @Autowired
     String searchCondition = "";

    @Autowired
    String userId = "";

    @BindView(R.id.posts_activity_post_rv)
    RecyclerView recyclerView;

    @BindView(R.id.activity_post_top_back_btn)
    Button backBtn;
    @BindView(R.id.activity_post_info_top_title)
    TextView topText;

    PostsAdapter postsAdapter;
    List<String> selectionList;
    List<BBSPost> bbsPostsList;

    BBSViewModel bbsViewModel;

    public static final String SEARCH_DATA_BUNDLE_NAME = "searchCondition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sreach_result);
        ButterKnife.bind(this);
        bbsViewModel = new ViewModelProvider(this, new BBSViewModelFactory())
                .get(BBSViewModel.class);
        if(!getIntent().getExtras().isEmpty()){
            searchCondition = getIntent().getExtras().getString(SEARCH_DATA_BUNDLE_NAME);
        }

        init();
        initObserver();
        initListener();

        if(!TextUtils.isEmpty(searchCondition)){
            bbsViewModel.getBBSPostLIstByCondition(CURRENT_TYPE, searchCondition, PER_PAPER_NUM, CURRENT_PAPER_NUM);
        }else if(!TextUtils.isEmpty(userId)){
            bbsViewModel.getBBSPostListByUId(CURRENT_TYPE, userId, PER_PAPER_NUM, CURRENT_PAPER_NUM);
        }

    }

    private void init(){
        bbsPostsList = new ArrayList<>();
        selectionList = new ArrayList<>();
        postsAdapter = new PostsAdapter(this);

        for(int i = 0; i < 10; i++ ){
            BBSPost bbsPost = new BBSPost();
            bbsPost.setTitle(String.valueOf(i));
            if (i == 4){
                bbsPost.setTitle("斯佩伯爵 海军上校String.valueOf(i)String.valueOf(i)String.valueOf(i)");
            }
            bbsPostsList.add(bbsPost);
            Log.d("测试post序号", String.valueOf(i));
        }

        topText.setText(searchCondition);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 finish();
            }
        });

        postsAdapter.setPostList(bbsPostsList);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        // manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(postsAdapter);

        bbsViewModel.getSelectionList();
        postsAdapter.notifyDataSetChanged();
    }

    private void initObserver(){
        bbsViewModel.getPostListResultMutableLiveData().observe(this, new Observer<GetPostListResult>() {
            @Override
            public void onChanged(GetPostListResult getPostListResult) {
                if (TextUtils.isEmpty(getPostListResult.getErrorMg())){
                    CURRENT_PAPER_NUM += 1;
                    HASH_MORE = getPostListResult.isHasMore();
                    bbsPostsList.addAll(getPostListResult.getBbsPostList());
                    IS_LOADING_MORE = false;
                } else {
                    ToastUntil.showToast(getPostListResult.getErrorMg(), AppContext.getContext());
                }
            }
        });
    }

    private void initListener(){
        postsAdapter.setOnClickListener(new PostsAdapter.PostOnclickListener() {
            @Override
            public void onclick(int position) {
                Intent intent = new Intent(SreachResultActivity.this, PostInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(PostInfoActivity.POST_DATA_BUNDLE_NAME, bbsPostsList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
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
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                assert layoutManager != null;
                int spanCount = layoutManager.getSpanCount();
                int[] lastPositions = new int[spanCount];
                int[] lastCompletelyVisibleItemPosition = layoutManager.findLastVisibleItemPositions(lastPositions);
                if(lastCompletelyVisibleItemPosition[0] == layoutManager.getItemCount() -1){
                    Log.d("刷新", "滑动到底部" + layoutManager.getItemCount());
                    if(layoutManager.getItemCount() == postsAdapter.getItemCount()){
                        if(IS_LOADING_MORE){
                            return;
                        }

                        if(HASH_MORE){
                            ToastUntil.showToast("正在加载", AppContext.getContext());
                            IS_LOADING_MORE = true;
                            bbsViewModel.getBBSPostLIstByCondition(CURRENT_TYPE, searchCondition, PER_PAPER_NUM, CURRENT_PAPER_NUM + 1);
                        }else {
                            ToastUntil.showToast("已无更多", AppContext.getContext());
                        }
                    }
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bbsViewModel.cancelAsyTask();
    }
}
