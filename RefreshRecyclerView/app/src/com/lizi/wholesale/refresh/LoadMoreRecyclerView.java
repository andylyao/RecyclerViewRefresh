package com.lizi.wholesale.refresh;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by Andy
 * Time on 2016/1/18.
 * Description add load more function for recyclerView
 */
public class LoadMoreRecyclerView extends RecyclerView{
	private HeaderRecyclerViewAdapter mHeaderAdapter;
	private OnNextPageListener mNextPageListener;

	/* 加载更多*/
	private FooterLoadingLayout mFooterView;
	/* 上拉加载是否可用*/
	private boolean isLoadMoreEnable = true;

	public LoadMoreRecyclerView(Context context) {
		super(context);
	}

	public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void setAdapter(RecyclerView.Adapter adapter) {
		mHeaderAdapter = new HeaderRecyclerViewAdapter(adapter);
		super.setAdapter(mHeaderAdapter);
		addOnScrollListener(mScrollListener);
		mFooterView = new FooterLoadingLayout(getContext());
		mHeaderAdapter.addFooterView(mFooterView);
	}

	private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {

		/** 最后一个可见的item的位置 */
		private int mLastVisiblePosition;

		@Override
		public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
			super.onScrolled(recyclerView, dx, dy);
			RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
			if (layoutManager instanceof LinearLayoutManager) {
				mLastVisiblePosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
			} else if (layoutManager instanceof GridLayoutManager) {
				mLastVisiblePosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
			} else if (layoutManager instanceof StaggeredGridLayoutManager) {
				StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
				int[] lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
				staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
				mLastVisiblePosition = findMaxPosition(lastPositions);
			}
		}

		@Override
		public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
			super.onScrollStateChanged(recyclerView, newState);
			RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
			int visibleItemCount = layoutManager.getChildCount();
			int totalItemCount = layoutManager.getItemCount();
			if ((visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && (mLastVisiblePosition) >= totalItemCount - 1)) {
				if(mNextPageListener != null && isLoadMoreEnable) {
					if (null != mFooterView) {
						mFooterView.setState(ILoadingLayout.State.REFRESHING);
					}
					mNextPageListener.onNextPage();
					isLoadMoreEnable = false;
				}
			}
		}
	};

	private int findMaxPosition(int[] lastPositions) {
		int maxPosition = lastPositions[0];
		for (int value : lastPositions) {
			if (value > maxPosition) {
				maxPosition = value;
			}
		}
		return maxPosition;
	}

	@Override
	public Adapter getAdapter() {
		return mHeaderAdapter.getAdapter();
	}

	public HeaderRecyclerViewAdapter getHeaderAdapter() {
		return mHeaderAdapter;
	}

	public void setOnNextPageListener(OnNextPageListener nextPageListener) {
		mNextPageListener = nextPageListener;
	}

	public void setLoadMoreEnable(boolean isLoadMoreEnable) {
		this.isLoadMoreEnable = isLoadMoreEnable;
		mHeaderAdapter.setFooterEnable(isLoadMoreEnable);
	}

	public void refreshComplete() {
		if(mFooterView != null) {
			mFooterView.setState(ILoadingLayout.State.RESET);
		}
	}
}
