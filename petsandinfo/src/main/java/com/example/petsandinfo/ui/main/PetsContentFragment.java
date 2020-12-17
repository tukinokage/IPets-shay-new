package com.example.petsandinfo.ui.main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.petsandinfo.R;
import com.google.android.material.tabs.TabLayout;
import com.shay.baselibrary.enums.petInfo.PetClassesEnum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PetsContentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PetsContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * 承载viewpage的fragment
 */
/*
* 要在主activity销毁pagerAdapter
* 第0固定为全部
* */
public class PetsContentFragment extends Fragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    SectionsPagerAdapter sectionsPagerAdapter;

    //更换，种类
    //private List<String> petClasses = new ArrayList<>();

    private int classesNum = PetClassesEnum.values().length;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "PetClass";
    private static final String ARG_PARAM2 = "shapeLevel";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private int mParam2;

    private OnFragmentInteractionListener mListener;

    public PetsContentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PetsContentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PetsContentFragment newInstance(String param1, String param2) {
        PetsContentFragment fragment = new PetsContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pets_content, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        ButterKnife.bind(this, getActivity());
        sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        sectionsPagerAdapter.addFragment(AllPetsFragment.newInstance(0, "全部"));
        for (int i = 1; i <= classesNum; i++){
            sectionsPagerAdapter.addFragment(PlaceholderFragment.newInstance(i, PetClassesEnum.getEnumByNum(i).getChinese()));
        }

        viewPager.setOffscreenPageLimit(classesNum/2);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        TabLayout tabs =  getActivity().findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
