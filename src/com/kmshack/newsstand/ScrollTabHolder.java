package com.kmshack.newsstand;

import android.widget.AbsListView;

public interface ScrollTabHolder {

	void adjustScroll(int tabBarTop);

	void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition);
}
