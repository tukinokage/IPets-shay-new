package com.example.petsandinfo.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.petsandinfo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final Map<String, Fragment> fragmentsMap = new LinkedHashMap<>();
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Iterator<Map.Entry<String, Fragment>> iterator = fragmentsMap.entrySet().iterator();

        for (int i = 0; i < fragmentsMap.size(); i++){
            if(iterator.hasNext()){
                Map.Entry<String, Fragment> next = iterator.next();
                if(i == position){
                    return next.getKey();
                }
            }

        }
    }

    public void addFragment(Fragment fragment, String petClassName){
        fragmentsMap.put(petClassName, fragment);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return fragmentsMap.size();
    }
}