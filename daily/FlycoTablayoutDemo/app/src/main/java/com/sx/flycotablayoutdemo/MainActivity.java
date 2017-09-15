package com.sx.flycotablayoutdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SlidingTabLayout slidingTab;
    private ViewPager vp;

    String[] titles = {"快速预约", "预约记录"};
    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private PagersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private ArrayList<Fragment> fragments = new ArrayList<>();

    private void initData() {
        oneFragment = new OneFragment();
        twoFragment = new TwoFragment();
        fragments.add(oneFragment);
        fragments.add(twoFragment);

        //直接为FlycoTablayout设置viewpager和fragment
        slidingTab.setViewPager(vp, titles, this, fragments);
    }

    private void initView() {
        slidingTab = (SlidingTabLayout) findViewById(R.id.slidingTab);
        vp = (ViewPager) findViewById(R.id.vp);
        adapter = new PagersAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

    }

    class PagersAdapter extends FragmentPagerAdapter {

        public PagersAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            return super.instantiateItem(container, position);
        }

        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


}
