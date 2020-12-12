package com.example.petsandinfo.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.petsandinfo.R;
import com.example.petsandinfo.viewmodel.PageViewModel;
import com.shay.baselibrary.enums.petInfo.PetClassesEnum;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 *
 * @Describe 用于展示通用界面
 */
public class PlaceholderFragment extends Fragment {

    private GridView

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_SECTION_NAME = "section_name";
    private static final String ARG_PARAM1 = "shapeLevel";
    private static final String ARG_PARAM2 = "fetchLevel";
    private static final String ARG_PARAM3 = "rankType";

    private int  classNum;
    private String name;

    //选项默认值
    private static int mShapeLevel = 0;
    private static int mFetchLevel = 0;
    private static int mRankType = 0;

    private PageViewModel pageViewModel;

    public String getName() {
        return name;
    }
    public static PlaceholderFragment newInstance(int index, String className) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putString(ARG_SECTION_NAME, className);
       /* bundle.putInt(ARG_PARAM1, mShapeLevel);
        bundle.putInt(ARG_PARAM2, mFetchLevel);
        bundle.putInt(ARG_PARAM3, mRankType);*/
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        if (getArguments() != null) {
            classNum = getArguments().getInt(ARG_SECTION_NUMBER);
            name = getArguments().getString(ARG_SECTION_NAME);
        }
        pageViewModel.setIndex(classNum);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_place_holder, container, false);
        final TextView textView = root.findViewById(R.id.section_label);

        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        ButterKnife.bind(this, getActivity());
    }
}