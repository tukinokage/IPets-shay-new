package com.example.petsandinfo.ui.main;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.petsandinfo.R;
import com.example.petsandinfo.model.entity.Pet;
import com.example.petsandinfo.ui.main.AllPetsFragment.OnListFragmentInteractionListener;
import com.example.petsandinfo.ui.main.dummy.DummyContent.DummyItem;

import java.util.List;

import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MypetRecyclerViewAdapter extends RecyclerView.Adapter<MypetRecyclerViewAdapter.ViewHolder> {

    private final List<Pet> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MypetRecyclerViewAdapter(List<Pet> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pet_list_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Pet pet = mValues.get(position);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                 //   mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, mView);
        }

        @Override
        public String toString() {
            return super.toString() + " '";
        }
    }
}
