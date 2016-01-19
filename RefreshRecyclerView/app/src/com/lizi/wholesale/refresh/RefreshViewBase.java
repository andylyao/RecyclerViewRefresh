package com.lizi.wholesale.refresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Andy
 * Time on 2016/1/16.
 * Description refresh view
 */
public abstract class RefreshViewBase<T extends View> extends SwipeRefreshLayout {

	/* 下拉刷新是否有用*/
	private boolean isRefreshEnable = true;
	protected T mRefreshView;

	public RefreshViewBase(Context context) {
		super(context);
		initView();
	}

	public RefreshViewBase(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView() {
		mRefreshView = getRefreshView();
		SwipeRefreshLayout.LayoutParams params = new SwipeRefreshLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(mRefreshView, params);
	}

	public void setRefreshEnable(boolean isRefreshEnable) {
		this.isRefreshEnable = isRefreshEnable;
		setEnabled(isRefreshEnable);
	}

	public void refreshComplete() {
		setRefreshing(false);
	}

	protected abstract T getRefreshView();
}
