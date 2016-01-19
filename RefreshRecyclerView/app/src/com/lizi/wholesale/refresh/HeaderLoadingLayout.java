package com.lizi.wholesale.refresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lizi.wholesale.R;


/**
 * 这个类封装了下拉刷新的布局
 * 
 * @author Li Hong
 * @since 2013-7-30
 */
public class HeaderLoadingLayout extends LoadingLayout {
	// /** 旋转动画时间 */
	// private static final int ROTATE_ANIM_DURATION = 150;
	/** Header的容器 */
	private RelativeLayout mHeaderContainer;
	/** 箭头图片 */
	private ImageView mArrowImageView;
	/** 进度条 */
	private ImageView progressAnimView;
	/** 状态提示TextView */
	private TextView mHintTextView;
	/** 最后更新时间的TextView */
	// private TextView mHeaderTimeView;
	/** 最后更新时间的标题 */
	// private TextView mHeaderTimeViewTitle;

	// /**向上的动画*/
	// private Animation mRotateUpAnim;
	// /**向下的动画*/
	// private Animation mRotateDownAnim;
	//
	/**
	 * 构造方法
	 * 
	 * @param context
	 *            context
	 */
	public HeaderLoadingLayout(Context context) {
		super(context);
		init(context);
	}

	public HeaderLoadingLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		mHeaderContainer = (RelativeLayout) findViewById(R.id.pull_to_refresh_header_content);
		mArrowImageView = (ImageView) findViewById(R.id.pull_to_refresh_header_arrow);
		mHintTextView = (TextView) findViewById(R.id.pull_to_refresh_header_hint_textview);
		// mHeaderTimeView = (TextView) findViewById(R.id.pull_to_refresh_header_time);
		// mHeaderTimeViewTitle = (TextView) findViewById(R.id.pull_to_refresh_last_update_time_text);
		progressAnimView = (ImageView) findViewById(R.id.refresh_loading_image);
		AnimationDrawable animationDrawable = (AnimationDrawable) progressAnimView.getDrawable();
		animationDrawable.stop();
	}

	@Override
	public void setLastUpdatedLabel(CharSequence label) {
		// 如果最后更新的时间的文本是空的话，隐藏前面的标题
		/*mHeaderTimeViewTitle.setVisibility(TextUtils.isEmpty(label) ? View.INVISIBLE : View.VISIBLE);
		mHeaderTimeView.setText(label);*/
	}

	@Override
	public int getContentSize() {
		if (null != mHeaderContainer) {
			return mHeaderContainer.getHeight();
		}
		return (int) (getResources().getDisplayMetrics().density * 60);
	}

	@Override
	protected View createLoadingView(Context context, AttributeSet attrs) {
		View container = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header, null);
		return container;
	}

	@Override
	protected void onStateChanged(State curState, State oldState) {
		mArrowImageView.setVisibility(View.VISIBLE);
		progressAnimView.setVisibility(View.INVISIBLE);
		super.onStateChanged(curState, oldState);
	}

	@Override
	protected void onReset() {
		mArrowImageView.clearAnimation();
		mHintTextView.setText(R.string.pull_to_refresh_header_text);
	}

	@Override
	protected void onPullToRefresh() {
		if (State.RELEASE_TO_REFRESH == getPreState()) {
			// mArrowImageView.clearAnimation();
			// mArrowImageView.startAnimation(mRotateDownAnim);
		}
		mHintTextView.setText(R.string.pull_to_refresh_header_text);
	}

	@Override
	protected void onReleaseToRefresh() {
		mHintTextView.setText(R.string.pull_to_refresh_header_hint_ready);
	}

	@Override
	protected void onRefreshing() {
		mArrowImageView.clearAnimation();
		mArrowImageView.setVisibility(View.INVISIBLE);
		progressAnimView.setVisibility(View.VISIBLE);
		mHintTextView.setText(R.string.pull_to_refresh_load_more_text);
	}
}
