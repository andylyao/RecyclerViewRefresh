package com.lizi.wholesale.refresh;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Andy
 * Time on 2016/1/16.
 * Description refresh and loader more scrollView
 */
public class RefreshScrollView extends RefreshViewBase<NestedScrollView>{

	private FooterLoadingLayout mFooterView;
	/* 上拉加载是否可用*/
	private boolean isLoadMoreEnable = true;
	private OnNextPageListener mNextPageListener;
	public RefreshScrollView(Context context) {
		super(context);
	}

	public RefreshScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected NestedScrollView getRefreshView() {
		NestedScrollView scrollView = new NestedScrollView(getContext());
		return scrollView;
	}

	public void addRefreshView(View child) {
		mRefreshView.removeAllViews();
		if(isLoadMoreEnable && child instanceof ViewGroup) {
			mFooterView = new FooterLoadingLayout(getContext());
			((ViewGroup)child).addView(mFooterView);
		}
		mRefreshView.addView(child);
	}

	public void setOnNextPageListener(OnNextPageListener nextPageListener) {
		mNextPageListener = nextPageListener;
	}

	public void setLoadMoreEnable(boolean isLoadMoreEnable) {
		this.isLoadMoreEnable = isLoadMoreEnable;
		if(mFooterView != null) {
			mFooterView.setVisibility(isLoadMoreEnable ? View.VISIBLE : View.GONE);
		}
	}

	public void refreshComplete() {
		super.refreshComplete();
		if(mFooterView != null) {
			mFooterView.setState(ILoadingLayout.State.RESET);
		}
	}

	private int computeVerticalScrollRange;
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				computeVerticalScrollRange = computeVerticalScrollRange() - (mFooterView == null ? 0 : mFooterView.getHeight());
				break;
			case MotionEvent.ACTION_MOVE:
				if(isLoadMoreEnable && getScrollY() + getHeight() >= computeVerticalScrollRange){
					if(mNextPageListener != null ) {
						if (null != mFooterView) {
							mFooterView.setState(ILoadingLayout.State.REFRESHING);
						}
						mNextPageListener.onNextPage();
						isLoadMoreEnable = false;
					}
				}
				break;
		}
		return super.onInterceptTouchEvent(ev);
	}

}
