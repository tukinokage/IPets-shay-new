package com.example.petsandinfo.ui.main;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.shay.baselibrary.enums.petInfo.PetClassesEnum;

import java.util.ArrayList;
/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager fm;
    private  ArrayList<Fragment> fs = new ArrayList<>();


    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Fragment fragment = fs.get(position);
        Log.d(this.getClass().getSimpleName(), fragment + ":" + position);
        if (fragment.isAdded()){
            return null;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

      /* Fragment mfragment = fs.get(position);
       return  ((PlaceholderFragment)mfragment).getName();
*/
      if(position < PetClassesEnum.values().length){
          return PetClassesEnum.getEnumByNum(position).getChinese();
      }

      return null;
    }


    public void addFragment(Fragment fragment){

        fs.add(fragment);
    }

    @Override
    public int getCount() {
        // Show  total pages.
        return fs.size();
    }


    // private static final Map<String, Fragment> fragmentsMap = new LinkedHashMap<>();

}