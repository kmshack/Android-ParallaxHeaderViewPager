package com.kmshack.newsstand;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements ScrollTabHolder, ViewPager.OnPageChangeListener {
	
	public static final boolean NEEDS_PROXY = Integer.valueOf(Build.VERSION.SDK_INT).intValue() < 11;
	
	private View mHeader; 

	private PagerSlidingTabStrip mPagerSlidingTabStrip;
	private ViewPager mViewPager;
	private PagerAdapter mPagerAdapter;

	private int mMinHeaderHeight;
	private int mHeaderHeight;
	private int mMinHeaderTranslation;
	
	private TextView info;
	private int mLastY;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.min_header_height);
		mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
		mMinHeaderTranslation = -mMinHeaderHeight;

		setContentView(R.layout.activity_main);

		mHeader = findViewById(R.id.header);
		info = (TextView) findViewById(R.id.info);

		mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setOffscreenPageLimit(4);

		mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
		mPagerAdapter.setTabHolderScrollingContent(this);

		mViewPager.setAdapter(mPagerAdapter);

		mPagerSlidingTabStrip.setViewPager(mViewPager);
		mPagerSlidingTabStrip.setOnPageChangeListener(this);
		mLastY=0;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// nothing
	}
	
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

		if (positionOffsetPixels > 0) {
			int currentItem = mViewPager.getCurrentItem();

			SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mPagerAdapter.getScrollTabHolders();
			ScrollTabHolder currentHolder;

			if (position < currentItem) {
				currentHolder = scrollTabHolders.valueAt(position);
			} else {
				currentHolder = scrollTabHolders.valueAt(position + 1);
			}

			if (NEEDS_PROXY) {
				// TODO is not good
				currentHolder.adjustScroll(mHeader.getHeight() - mLastY);
				mHeader.postInvalidate();
			} else {
				currentHolder.adjustScroll((int) (mHeader.getHeight() + mHeader.getTranslationY()));
			}
		}
	}

	@Override
	public void onPageSelected(int position) {
		SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mPagerAdapter.getScrollTabHolders();
		ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);
		if(NEEDS_PROXY){
			//TODO is not good 
			currentHolder.adjustScroll(mHeader.getHeight()-mLastY);
			mHeader.postInvalidate();
		}else{
			currentHolder.adjustScroll((int) (mHeader.getHeight() +mHeader.getTranslationY()));	
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
		if (mViewPager.getCurrentItem() == pagePosition) {
			int scrollY = getScrollY(view);
			if(NEEDS_PROXY){
				//TODO is not good 
				mLastY=-Math.max(-scrollY, mMinHeaderTranslation);
				info.setText(String.valueOf(scrollY));
				mHeader.scrollTo(0, mLastY);
				mHeader.postInvalidate();
			}else{
				mHeader.setTranslationY(Math.max(-scrollY, mMinHeaderTranslation));				
			}
		}
	}

	@Override
	public void adjustScroll(int scrollHeight) {
		// nothing
	}

	public int getScrollY(AbsListView view) {
		View c = view.getChildAt(0);
		if (c == null) {
			return 0;
		}

		int firstVisiblePosition = view.getFirstVisiblePosition();
		int top = c.getTop();

		int headerHeight = 0;
		if (firstVisiblePosition >= 1) {
			headerHeight = mHeaderHeight;
		}

		return -top + firstVisiblePosition * c.getHeight() + headerHeight;
	}

	public static float clamp(float value, float max, float min) {
		return Math.max(Math.min(value, min), max);
	}

	public class PagerAdapter extends FragmentPagerAdapter {

		private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
		private final String[] TITLES = { "Page 1", "Page 2", "Page 3", "Page 4"};
		private ScrollTabHolder mListener;

		public PagerAdapter(FragmentManager fm) {
			super(fm);
			mScrollTabHolders = new SparseArrayCompat<ScrollTabHolder>();
		}

		public void setTabHolderScrollingContent(ScrollTabHolder listener) {
			mListener = listener;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {
			ScrollTabHolderFragment fragment = (ScrollTabHolderFragment) SampleListFragment.newInstance(position);

			mScrollTabHolders.put(position, fragment);
			if (mListener != null) {
				fragment.setScrollTabHolder(mListener);
			}

			return fragment;
		}

		public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
			return mScrollTabHolders;
		}

	}
}
