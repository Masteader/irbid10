package com.tuned.irbed1.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tuned.irbed1.R;
import com.tuned.irbed1.adapters.ViewPagerAdapterFragment;
import com.tuned.irbed1.fragments.CartFragment;
import com.tuned.irbed1.fragments.HomeFragment;
import com.tuned.irbed1.fragments.MoreFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigation;
    MenuItem prevMenuItem;
    ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = findViewById(R.id.bottom_nav);
        mViewPager = findViewById(R.id.view_pager);

        setupViewPager(mViewPager);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.cart:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.more:
                        mViewPager.setCurrentItem(2);
                        break;

                }
                return false;
            }
        });


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else
                    navigation.getMenu().getItem(0).setChecked(false);

                navigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigation.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapterFragment adapter = new ViewPagerAdapterFragment(getSupportFragmentManager());
        HomeFragment homeFragment =new HomeFragment();
        adapter.addFragment(homeFragment);

        CartFragment cartFragment =new CartFragment();
        adapter.addFragment(cartFragment);

        MoreFragment moreFragment =new MoreFragment();
        adapter.addFragment(moreFragment);

        viewPager.setAdapter(adapter);
    }

}