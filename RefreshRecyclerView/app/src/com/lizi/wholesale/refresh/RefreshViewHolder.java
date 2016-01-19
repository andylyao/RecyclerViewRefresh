package com.lizi.wholesale.refresh;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Andy
 * Time on 2016/1/16.
 * Description for header and footer viewHolder
 */
public class RefreshViewHolder extends RecyclerView.ViewHolder{
	public final int mType;
	public RecyclerView.ViewHolder mViewHolder;
	public RefreshViewHolder(View itemView, int type) {
		super(itemView);
		this.mType = type;
	}

}
