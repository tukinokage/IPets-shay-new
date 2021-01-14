package com.example.bbsmodule;

import android.os.Bundle;

import com.example.bbsmodule.adapter.PostSelectionsAdapter;
import com.example.bbsmodule.adapter.PostsAdapter;
import com.example.bbsmodule.entity.BBSPost;
import com.example.bbsmodule.viewmodel.BBSViewModel;
import com.example.bbsmodule.viewmodel.BBSViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BBSMainActivity extends AppCompatActivity {

    public final static int PER_PAPER_NUM = 15;
    public final static int CURRENT_PAPER_NUM = 1;
    public final static int CURRENT_TYPE = 0;

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        init();
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

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(postsAdapter);
        selectGridView.setAdapter(postSelectionsAdapter);

        bbsViewModel.getSelectionList();
    }

    private void initObserver(){

        bbsViewModel.getSelectonListMutableLiveData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                postSelectionsAdapter.setSeletionList(strings);
                postSelectionsAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initListener(){
        postsAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        postSelectionsAdapter.setSelectionOnclickListener(new PostSelectionsAdapter.SelectionOnclickListener() {
            @Override
            public void onClick(int position) {

            }
        });


    }
}
