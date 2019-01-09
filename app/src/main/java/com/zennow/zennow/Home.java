package com.zennow.zennow;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        viewPager = (ViewPager) findViewById(R.id.pager);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.toolbar_title);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    intialeToolBar("Dashboard");
                }
                else if(position==1){
                    intialeToolBar("Booking");
                }
                else if(position==2){
                    intialeToolBar("Scheduling");
                }
                else if(position==3){
                    intialeToolBar("Menu");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void intialeToolBar(String Title){
        title.setText(Title);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void setupTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Dashboard");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_dashboard, 0, 0);

        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Booking");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_booking, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Scheduling");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_sheduling, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFor = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFor.setText("more");
        tabFor.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_more, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFor);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabDashboard(), "Dashboard");
        adapter.addFragment(new TabBooking(), "Booking");
        adapter.addFragment(new TabSchedule(), "Schedule");
        adapter.addFragment(new TabMore(), "More");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {return mFragmentTitleList.get(position);}
    }
}
