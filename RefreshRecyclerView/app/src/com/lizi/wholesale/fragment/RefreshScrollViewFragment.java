package com.lizi.wholesale.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lizi.wholesale.R;
import com.lizi.wholesale.adapter.RecyclerAdapter;
import com.lizi.wholesale.refresh.LoadMoreRecyclerView;
import com.lizi.wholesale.refresh.OnNextPageListener;
import com.lizi.wholesale.refresh.RefreshScrollView;
import com.lizi.wholesale.refresh.ScrollViewLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator
 * Time on 2016/1/18.
 * Description
 */
public class RefreshScrollViewFragment extends Fragment{

	private RefreshScrollView mRefreshView;
	private LoadMoreRecyclerView mRecyclerList;
	private RecyclerAdapter mAdapter;
	/** 当前页数 每页十条 */
	private int mPage;
	// 是否刷新状态
	protected boolean isPullRefresh = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_refresh_scrollview, null);
		mRefreshView = (RefreshScrollView) view.findViewById(R.id.refresh_view);
		mRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				if (isPullRefresh) {// 如果正在刷新就返回
					return;
				}
				isPullRefresh = true;
				RefreshScrollViewFragment.this.onRefresh();
			}
		});
		View child = inflater.inflate(R.layout.fragment_refresh_scrollview_child, null);
		mRefreshView.addRefreshView(child);
		mRecyclerList = (LoadMoreRecyclerView) child.findViewById(R.id.recycler_list);
		mRecyclerList.setLayoutManager(new ScrollViewLinearLayoutManager(getActivity()));
		mAdapter = new RecyclerAdapter(getActivity());
		mRecyclerList.setAdapter(mAdapter);
		mRecyclerList.setNestedScrollingEnabled(false);
		mRecyclerList.setFocusable(false);
		mRefreshView.setOnNextPageListener(new OnNextPageListener() {
			@Override
			public void onNextPage() {
				RefreshScrollViewFragment.this.onNextPage();
			}
		});
		mRefreshView.setLoadMoreEnable(true);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		onRefresh();
	}

	protected void onRefresh() {
		mPage = 0;
		netWorkRequest();
	}

	protected void onNextPage() {
		mPage++;
		netWorkRequest();
	}

	protected void netWorkRequest() {
		mHandler.sendEmptyMessageDelayed(0, 2000);
	}


	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			List<String> list = new ArrayList<>();
			for(int i = 0; i < 10; i++) {
				list.add("这是一个测试 page = " + mPage + " i = "+ i);
			}
			if(mPage == 0) {
				mAdapter.resetList(list);
			} else {
				mAdapter.addList(list);
			}
			isPullRefresh = false;
			mRefreshView.refreshComplete();
			mRefreshView.setLoadMoreEnable(mPage < 4);
		}
	};
}
