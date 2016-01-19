package com.lizi.wholesale.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by Andy
 * Time on 2016/1/ 16.
 * Description refresh webView
 */
public class RefreshWebView extends RefreshViewBase<WebView>{
	public RefreshWebView(Context context) {
		super(context);
	}

	public RefreshWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected WebView getRefreshView() {
		WebView webView = new WebView(getContext());
		return webView;
	}

	public void loadUrl(String url) {
		mRefreshView.loadUrl(url);
	}
}
