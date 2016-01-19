package com.lizi.wholesale.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lizi.wholesale.R;
import com.lizi.wholesale.adapter.RecyclerAdapter;
import com.lizi.wholesale.refresh.ExStaggeredGridLayoutManager;
import com.lizi.wholesale.refresh.HeaderSpanSizeLookup;
import com.lizi.wholesale.refresh.OnNextPageListener;
import com.lizi.wholesale.refresh.RefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RefreshStaggeredGridLayoutFragment extends Fragment {

	private RefreshRecyclerView mPullToRefreshView;
	private RecyclerAdapter mAdapter;
	/** 当前页数 每页十条 */
	private int mPage;
	// 是否刷新状态
	protected boolean isPullRefresh = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_refresh_staggered_grid, null);
		mPullToRefreshView = (RefreshRecyclerView) view.findViewById(R.id.recycle_refresh);
		mAdapter = new RecyclerAdapter(getActivity());
		mPullToRefreshView.setAdapter(mAdapter);
		ExStaggeredGridLayoutManager manager = new ExStaggeredGridLayoutManager (2, StaggeredGridLayoutManager.VERTICAL);
		manager.setSpanSizeLookup(new HeaderSpanSizeLookup(mPullToRefreshView.getHeaderAdapter(), manager.getSpanCount()));
		mPullToRefreshView.setLayoutManager(manager);
		mPullToRefreshView.setRefreshEnable(true);
		mPullToRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				if (isPullRefresh) {// 如果正在刷新就返回
					return;
				}
				isPullRefresh = true;
				RefreshStaggeredGridLayoutFragment.this.onRefresh();
			}
		});
		mPullToRefreshView.setOnNextPageListener(new OnNextPageListener() {
			@Override
			public void onNextPage() {
				RefreshStaggeredGridLayoutFragment.this.onNextPage();
			}
		});
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
			for(int i = 0; i < 11; i++) {
				list.add("这是一个测试 page = " + mPage + " i = "+ i);
			}
			if(mPage == 0) {
				mAdapter.resetList(list);
			} else {
				mAdapter.addList(list);
			}
			isPullRefresh = false;
			mPullToRefreshView.refreshComplete();
			mPullToRefreshView.setLoadMoreEnable(mPage < 3);
		}
	};
}
