package com.example.bbsmodule;

import android.content.Intent;
import android.os.Bundle;

import com.example.bbsmodule.adapter.PostSelectionsAdapter;
import com.example.bbsmodule.adapter.PostsAdapter;
import com.example.bbsmodule.entity.BBSPost;
import com.example.bbsmodule.entity.result.GetPostListResult;
import com.example.bbsmodule.viewmodel.BBSViewModel;
import com.example.bbsmodule.viewmodel.BBSViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.shay.baselibrary.AppContext;
import com.shay.baselibrary.ToastUntil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shay.baselibrary.AppContext.getContext;

public class BBSMainActivity extends AppCompatActivity {

    public  int PER_PAPER_NUM = 15;
    public static int CURRENT_PAPER_NUM = 1;
    public  static int CURRENT_TYPE = 0;
    private boolean HASH_MORE = true;
    private boolean IS_LOADING_MORE = false;

    int lastVisibleItem = 0;
    int index = 1;
    int temp = 0;

    @BindView(R2.id.posts_activity_post_rv)
    RecyclerView recyclerView;
    @BindView(R2.id.posts_activity_selection_grid_view)
    GridView selectGridView;
    @BindView(R2.id.posts_activity_auto_t)
    AutoCompleteTextView searchInput;
    @BindView(R2.id.posts_activity_btn)
    Button searchBtn;

    PostsAdapter postsAdapter;
    PostSelectionsAdapter postSelectionsAdapter;
    List<String> selectionList;
    List<BBSPost> bbsPostsList;

    BBSViewModel bbsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbsmain);
        ButterKnife.bind(this);


        bbsViewModel = new ViewModelProvider(this, new BBSViewModelFactory())
                .get(BBSViewModel.class);

        init();
        initObserver();
        initListener();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bbsmain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init(){
        bbsPostsList = new ArrayList<>();
        selectionList = new ArrayList<>();
        postsAdapter = new PostsAdapter(this);

        postsAdapter.setPostList(bbsPostsList);
        postSelectionsAdapter = new PostSelectionsAdapter(selectionList, this);

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
       // manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(postsAdapter);
        selectGridView.setAdapter(postSelectionsAdapter);

        bbsViewModel.getBBSPostLIstByCondition(CURRENT_TYPE, null, PER_PAPER_NUM, CURRENT_PAPER_NUM);
        IS_LOADING_MORE = true;

        bbsViewModel.getSelectionList();
        postsAdapter.notifyDataSetChanged();
    }

    private void initObserver(){

        bbsViewModel.getSelectonListMutableLiveData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                postSelectionsAdapter.setSeletionList(strings);
                postSelectionsAdapter.notifyDataSetChanged();
            }
        });

        bbsViewModel.getPostListResultMutableLiveData().observe(this, new Observer<GetPostListResult>() {
            @Override
            public void onChanged(GetPostListResult getPostListResult) {
                if (TextUtils.isEmpty(getPostListResult.getErrorMg())){

                    HASH_MORE = getPostListResult.isHasMore();
                    if (HASH_MORE){
                        CURRENT_PAPER_NUM += 1;
                    }
                    bbsPostsList.addAll(getPostListResult.getBbsPostList());
                    postsAdapter.setPostList(bbsPostsList);
                    postsAdapter.notifyDataSetChanged();
                    IS_LOADING_MORE = false;
                }else {
                    ToastUntil.showToast(getPostListResult.getErrorMg(), AppContext.getContext());
                }
            }
        });
    }



    private void initListener(){
        postsAdapter.setOnClickListener(new PostsAdapter.PostOnclickListener() {
            @Override
            public void onclick(int position) {
                Intent intent = new Intent(BBSMainActivity.this, PostInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(PostInfoActivity.POST_DATA_BUNDLE_NAME, bbsPostsList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
                }
        });

        postSelectionsAdapter.setSelectionOnclickListener(new PostSelectionsAdapter.SelectionOnclickListener() {
            @Override
            public void onClick(int position) {
                CURRENT_TYPE = position;
                CURRENT_PAPER_NUM = 1;
                bbsPostsList.clear();
                bbsViewModel.getBBSPostLIstByCondition(CURRENT_TYPE, null, PER_PAPER_NUM, CURRENT_PAPER_NUM);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //获取总的适配器的数量
                int totalCount = postsAdapter.getItemCount();
                //判断当前滑动停止，并且获取当前屏幕最后一个可见的条目是第几个，当前屏幕数据已经显示完毕的时候就去加载数据
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == totalCount) {
                    //请求数据

                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //到底部
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                assert layoutManager != null;
               //int spanCount = layoutManager.getSpanCount();
                //int[] lastPositions = new int[spanCount];
                //int[] lastCompletelyVisibleItemPosition = layoutManager.findLastVisibleItemPositions(lastPositions);
                layoutManager.findFirstVisibleItemPositions(null);
                int[] firstVisibleItemPositions = layoutManager.findLastVisibleItemPositions(null);
                for (int firstVisibleItemPosition : firstVisibleItemPositions) {
                   int temp = firstVisibleItemPosition;
                    if (lastVisibleItem < temp) {
                        lastVisibleItem = firstVisibleItemPosition;//标记最后一个显示的postion
                        if(IS_LOADING_MORE){
                            return;
                        }

                        if(HASH_MORE){
                            ToastUntil.showToast("正在加载", AppContext.getContext());
                            IS_LOADING_MORE = true;
                            bbsViewModel.getBBSPostLIst(CURRENT_TYPE, PER_PAPER_NUM, CURRENT_PAPER_NUM);
                        }
                    }
                }
               /* if(lastCompletelyVisibleItemPosition[0] == layoutManager.getItemCount() -1){
                    Log.d("刷新", "滑动到底部" + layoutManager.getItemCount());
                    if(layoutManager.getItemCount() == postsAdapter.getItemCount()){
                        if(IS_LOADING_MORE){
                            return;
                        }

                        if(HASH_MORE){
                            ToastUntil.showToast("正在加载", AppContext.getContext());
                            IS_LOADING_MORE = true;
                            bbsViewModel.getBBSPostLIst(CURRENT_TYPE, PER_PAPER_NUM, CURRENT_PAPER_NUM);
                        }

                    }
                }*/

            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c = searchInput.getText().toString().trim();
                if(!TextUtils.isEmpty(c)){
                   Intent intent = new Intent(BBSMainActivity.this, SreachResultActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(SreachResultActivity.SEARCH_DATA_BUNDLE_NAME, c);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    ToastUntil.showToast("不能为空", AppContext.getContext());
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
