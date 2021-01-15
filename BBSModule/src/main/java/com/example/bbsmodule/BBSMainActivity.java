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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shay.baselibrary.AppContext.getContext;

public class BBSMainActivity extends AppCompatActivity {

    public  static int PER_PAPER_NUM = 15;
    public  static int CURRENT_PAPER_NUM = 1;
    public  static int CURRENT_TYPE = 0;
    private boolean HASH_MORE = true;

    @BindView(R.id.posts_activity_post_rv)
    RecyclerView recyclerView;
    @BindView(R.id.posts_activity_selection_grid_view)
    GridView selectGridView;
    @BindView(R.id.posts_activity_auto_t)
    AutoCompleteTextView searchInput;
    @BindView(R.id.posts_activity_btn)
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


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        init();
        initObserver();
        initListener();
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

        for(int i = 0; i < 10; i++ ){
            BBSPost bbsPost = new BBSPost();
            bbsPost.setTitle(String.valueOf(i));
            if (i == 4){
                bbsPost.setTitle("斯佩伯爵 海军上校String.valueOf(i)String.valueOf(i)String.valueOf(i)");
            }
            bbsPostsList.add(bbsPost);
            Log.d("sdbb", String.valueOf(i));
        }

        postsAdapter.setPostList(bbsPostsList);
        postSelectionsAdapter = new PostSelectionsAdapter(selectionList, this);

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
       // manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(postsAdapter);
        selectGridView.setAdapter(postSelectionsAdapter);

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
                    CURRENT_PAPER_NUM += 1;
                    HASH_MORE = getPostListResult.isHasMore();
                    bbsPostsList.addAll(getPostListResult.getBbsPostList());
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
                bundle.putSerializable("BBSPost", bbsPostsList.get(position));
                intent.putExtra("data", bundle);
                startActivity(intent);
                }
        });

        postSelectionsAdapter.setSelectionOnclickListener(new PostSelectionsAdapter.SelectionOnclickListener() {
            @Override
            public void onClick(int position) {
                CURRENT_TYPE = position;
                CURRENT_PAPER_NUM = 1;
                bbsViewModel.getBBSPostLIst(CURRENT_TYPE, null, PER_PAPER_NUM, CURRENT_PAPER_NUM);
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
                        if(HASH_MORE){
                            ToastUntil.showToast("正在加载", AppContext.getContext());
                            bbsViewModel.getBBSPostLIst(CURRENT_TYPE, null, PER_PAPER_NUM, CURRENT_PAPER_NUM + 1);
                        }else {
                            ToastUntil.showToast("已无更多", AppContext.getContext());
                        }

                    }
                }

            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c = searchInput.getText().toString().trim();
                if(!TextUtils.isEmpty(c)){
                   /* Intent intent = new Intent(BBSMainActivity.this, PostInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("searchCondition", c);
                    intent.putExtra("data", bundle);
                    startActivity(intent);*/
                }else {
                    ToastUntil.showToast("不能为空", AppContext.getContext());
                }

            }
        });  


    }
}
