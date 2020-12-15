package com.example.petsandinfo.ui.main;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.petsandinfo.R;
import com.example.petsandinfo.adapters.SelectionsAdapter;
import com.example.petsandinfo.model.entity.Pet;
import com.example.petsandinfo.ui.main.dummy.DummyContent;
import com.example.petsandinfo.ui.main.dummy.DummyContent.DummyItem;
import com.example.petsandinfo.viewmodel.PageViewModel;
import com.example.petsandinfo.viewmodel.PlaceHolderViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AllPetsFragment extends Fragment {

    List<Pet> allPets = new ArrayList<>();
    // TODO: Customize parameters
    private int mColumnCount = 1;

    SelectionsAdapter selectionsAdapter;

    List<String> mSeletionList;
    List<String> mShapeSelectionList;
    List<String> mFetchSelectionList;
    List<String> mRankSelectionList;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_SECTION_NAME = "section_name";
    private static final String ARG_PARAM1 = "shapeLevel";
    private static final String ARG_PARAM2 = "fetchLevel";
    private static final String ARG_PARAM3 = "rankType";

    //标识当前界面类型
    private int classNum;
    private String name;

    //选项默认值
    private static int mShapeLevel = 0;
    private static int mFetchLevel = 0;
    private static int mRankType = 0;
    private static int mPetClass = 0;

    private PageViewModel pageViewModel;
    private PlaceHolderViewModel placeHolderViewModel;

    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AllPetsFragment() {
    }
    public String getName() {
        return name;
    }
    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AllPetsFragment newInstance(int num, String name) {
        AllPetsFragment fragment = new AllPetsFragment();
        Bundle args = new Bundle();
        mPetClass = num;
        args.putInt(ARG_SECTION_NUMBER, num);
        args.putString(ARG_SECTION_NAME, name);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_SECTION_NUMBER);
            name = getArguments().getString(ARG_SECTION_NAME);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_pets, container, false);

        selectionsAdapter = new SelectionsAdapter(mSeletionList, getActivity());
        // Set the adapter
      /*  if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MypetRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        }*/
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ButterKnife.bind(this, getActivity());

        Log.d("当前界面标识", "全部0");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
