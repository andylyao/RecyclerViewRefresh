package com.lizi.wholesale.refresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Andy
 * Time on 2016/1/16.
 * Description refresh and load more recyclerView
 */
public class RefreshRecyclerView extends RefreshViewBase<LoadMoreRecyclerView> {

	public RefreshRecyclerView(Context context) {
		super(context);
	}

	public RefreshRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected LoadMoreRecyclerView getRefreshView() {
		mRefreshView = new LoadMoreRecyclerView(getContext());
		return mRefreshView;
	}

	public void setAdapter(RecyclerView.Adapter adapter) {
		mRefreshView.setAdapter(adapter);
	}

	public void setOnNextPageListener(OnNextPageListener nextPageListener) {
		mRefreshView.setOnNextPageListener(nextPageListener);
	}

	public void setLoadMoreEnable(boolean isLoadMoreEnable) {
		mRefreshView.setLoadMoreEnable(isLoadMoreEnable);
	}

	public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
		mRefreshView.setLayoutManager(layoutManager);
	}

	@Override
	public void refreshComplete() {
		super.refreshComplete();
		mRefreshView.refreshComplete();
	}

	public RecyclerView.Adapter getAdapter() {
		return mRefreshView.getAdapter();
	}

	public HeaderRecyclerViewAdapter getHeaderAdapter() {
		return mRefreshView.getHeaderAdapter();
	}
}
