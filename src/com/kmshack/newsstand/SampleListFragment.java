package com.kmshack.newsstand;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SampleListFragment extends ScrollTabHolderFragment{

	private static final String ARG_POSITION = "position";

	private ListView mListView;
	private ArrayList<String> mListItems;

	private int mPosition;

	public static Fragment newInstance(int position) {
		SampleListFragment f = new SampleListFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPosition = getArguments().getInt(ARG_POSITION);

		mListItems = new ArrayList<String>();

		for (int i = 1; i <= 100; i++) {
			mListItems.add(i + ". item - currnet page: " + (mPosition + 1));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_list, null);

		mListView = (ListView) v.findViewById(R.id.listView);

		View placeHolderView = inflater.inflate(R.layout.view_header_placeholder, mListView, false);
		placeHolderView.setBackgroundColor(0xFFFFFFFF);
		mListView.addHeaderView(placeHolderView);

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mListView.setOnScrollListener(new OnScroll());
		mListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item, android.R.id.text1, mListItems));
		
		if(MainActivity.NEEDS_PROXY){//in my moto phone(android 2.1),setOnScrollListener do not work well
			mListView.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (mScrollTabHolder != null)
						mScrollTabHolder.onScroll(mListView, 0, 0, 0, mPosition);
					return false;
				}
			});
		}
	}

	@Override
	public void adjustScroll(int scrollHeight) {
		if (scrollHeight == 0 && mListView.getFirstVisiblePosition() >= 1) {
			return;
		}

		mListView.setSelectionFromTop(1, scrollHeight);

	}
	
	public class OnScroll implements OnScrollListener{

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			if (mScrollTabHolder != null)
				mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, mPosition);
		}
		
	}


	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount, int pagePosition) {
	}

}