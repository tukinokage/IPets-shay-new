package com.example.petsandinfo.debug;

import android.os.Bundle;

import com.example.petsandinfo.R;
import com.example.petsandinfo.ui.main.PetsContentFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.petsandinfo.ui.main.SectionsPagerAdapter;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = PetsContentFragment.newInstance("ss", "xx");
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_content, fragment).commit();


    }
}