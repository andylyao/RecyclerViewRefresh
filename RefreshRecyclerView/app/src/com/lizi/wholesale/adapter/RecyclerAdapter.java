package com.lizi.wholesale.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lizi.wholesale.R;
import com.lizi.wholesale.adapter.holder.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator
 * Time on 2016/1/15.
 * Description
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private List<String> mDataSource;
	public RecyclerAdapter(Context context) {
		mContext = context;
		mLayoutInflater = LayoutInflater.from(context);
		mDataSource = new ArrayList<>();
	}

	@Override
	public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = mLayoutInflater.inflate(R.layout.activity_recycle_refresh_item, parent, false);
		return new RecyclerViewHolder(view, viewType);
	}

	@Override
	public void onBindViewHolder(RecyclerViewHolder holder, int position) {
		holder.text.setText(mDataSource.get(position));
	}

	@Override
	public int getItemCount() {
		return mDataSource.size();
	}

	public void resetList(List<String> list) {
		mDataSource.clear();
		mDataSource.addAll(list);
		notifyDataSetChanged();
	}

	public void addList(List<String> list) {
		mDataSource.addAll(list);
		notifyDataSetChanged();
	}
}
