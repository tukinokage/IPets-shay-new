package com.example.petsandinfo.adapters;

import androidx.annotation.NonNull;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MypetRecyclerViewAdapter extends RecyclerView.Adapter<MypetRecyclerViewAdapter.ViewHolder> {

    private final List<Pet> mValues;
    private final OnListFragmentInteractionListener mListener;

    public static final int NORMAL_ITEM = 0;
    public static final int FOOT_ITEM = 0;

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
    public int getItemViewType(int position) {
        if(position == getItemCount() + 1){

        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mValues.size() + 1;
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

    public class FootViewHolder extends RecyclerView.ViewHolder{

        public final View view;
        @BindView(R.id.pet_list_foot_tips)
        TextView textView;


        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }
    }
}
