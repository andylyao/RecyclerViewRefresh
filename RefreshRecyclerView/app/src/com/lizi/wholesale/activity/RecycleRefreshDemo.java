package com.lizi.wholesale.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.lizi.wholesale.R;
import com.lizi.wholesale.fragment.RefreshGridLayoutFragment;
import com.lizi.wholesale.fragment.RefreshLinearLayoutFragment;
import com.lizi.wholesale.fragment.RefreshScrollViewFragment;
import com.lizi.wholesale.fragment.RefreshWebViewFragment;

import java.util.ArrayList;
import java.util.List;

public class RecycleRefreshDemo extends AppCompatActivity {

	private ViewPager mViewPager;

	private ActionBar mActionBar;

	private MyPagerAdapter mPagerAdapter;

	private List<Fragment> mFragments;

	private ActionBar.Tab[] mTabs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recycle_refresh);
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(mPageChangeListener);

		mActionBar = getSupportActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setDisplayShowHomeEnabled(false);
		List<String> titles = new ArrayList<>();
		mFragments = new ArrayList<>();
		mFragments.add(new RefreshLinearLayoutFragment());
		titles.add("list");
		mFragments.add(new RefreshGridLayoutFragment());
		titles.add("grid");
		//mFragments.add(new RefreshStaggeredGridLayoutFragment());
		//titles.add("list");
		mFragments.add(new RefreshScrollViewFragment());
		titles.add("scrollView");
		mFragments.add(new RefreshWebViewFragment());
		titles.add("webView");
		mTabs = new ActionBar.Tab[mFragments.size()];
		for (int i = 0; i != mFragments.size(); i++) {
			mTabs[i] = mActionBar.newTab().setText(titles.get(i)).setTabListener(mTabListener);
			mActionBar.addTab(mTabs[i]);
		}
		mPagerAdapter.notifyDataSetChanged();
	}

	private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			mActionBar.setSelectedNavigationItem(arg0);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	};

	private ActionBar.TabListener mTabListener = new ActionBar.TabListener() {

		@Override
		public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
			if (tab == mTabs[0]) {
				mViewPager.setCurrentItem(0);
			} else if (tab == mTabs[1]) {
				mViewPager.setCurrentItem(1);
			} else if (tab == mTabs[2]) {
				mViewPager.setCurrentItem(2);
			} else if(tab == mTabs[3]) {
				mViewPager.setCurrentItem(3);
			} else if(tab == mTabs[4]) {
				mViewPager.setCurrentItem(4);
			}

		}

		@Override
		public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
		}

		@Override
		public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
		}
	};

	public class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

		@Override
		public int getCount() {
			return mFragments == null ? 0 : mFragments.size();
		}
	}

}
