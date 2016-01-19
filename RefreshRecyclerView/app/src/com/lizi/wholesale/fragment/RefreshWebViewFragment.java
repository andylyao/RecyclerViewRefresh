package com.lizi.wholesale.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lizi.wholesale.R;
import com.lizi.wholesale.refresh.RefreshWebView;

/**
 * Created by Administrator
 * Time on 2016/1/18.
 * Description
 */
public class RefreshWebViewFragment extends Fragment{
	private RefreshWebView mRefreshWebView;
	private boolean isPullRefresh;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_refresh_webview, null);
		mRefreshWebView = (RefreshWebView) view.findViewById(R.id.refresh_view);
		mRefreshWebView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				if (isPullRefresh) {// 如果正在刷新就返回
					return;
				}
				isPullRefresh = true;
				RefreshWebViewFragment.this.onRefresh();
			}
		});
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mRefreshWebView.loadUrl("http://www.cnbeta.com/");
	}

	private void onRefresh() {
		mRefreshWebView.loadUrl("http://www.cnbeta.com/");
		mRefreshWebView.refreshComplete();
		isPullRefresh = false;
	}
}
