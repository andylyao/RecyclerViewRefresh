package com.lizi.wholesale.refresh;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Andy on 2016/1/15.
 * <p/>
 * RecyclerView.Adapter with Header and Footer
 */
public class HeaderRecyclerViewAdapter extends RecyclerView.Adapter<RefreshViewHolder> {

    private static final int TYPE_HEADER = Integer.MAX_VALUE;
    private static final int TYPE_FOOTER = Integer.MAX_VALUE - 1;

    /**
     * RecyclerView使用的，真正的Adapter
     */
    private RecyclerView.Adapter mAdapter;

    private View mHeaderView;
    private View mFooterView;
    private boolean isHeaderEnable;
    private boolean isFooterEnable;

    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {

        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            notifyItemRangeChanged(positionStart + getHeaderCount(), itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            notifyItemRangeInserted(positionStart + getHeaderCount(), itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            notifyItemRangeRemoved(positionStart + getHeaderCount(), itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            int headerViewsCountCount = getHeaderCount();
            notifyItemRangeChanged(fromPosition + headerViewsCountCount, toPosition + headerViewsCountCount + itemCount);
        }
    };

    public HeaderRecyclerViewAdapter(RecyclerView.Adapter adapter) {
        setAdapter(adapter);
    }

    /**
     * 设置adapter
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {

        if (adapter != null && !(adapter instanceof RecyclerView.Adapter)) {
                throw new RuntimeException("your adapter must be a RecyclerView.Adapter");
        }

        if (mAdapter != null) {
            notifyItemRangeRemoved(getHeaderCount(), mAdapter.getItemCount());
            mAdapter.unregisterAdapterDataObserver(mDataObserver);
        }

        this.mAdapter = adapter;
        mAdapter.registerAdapterDataObserver(mDataObserver);
        notifyItemRangeInserted(getHeaderCount(), mAdapter.getItemCount());
    }

    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    public void addHeaderView(View header) {
        mHeaderView = header;
        this.notifyDataSetChanged();
    }

    public void addFooterView(View footer) {
        mFooterView = footer;
        this.notifyDataSetChanged();
    }

    /**
     * 返回第一个FoView
     * @return
     */
    public View getFooterView() {
        return  mFooterView;
    }

    /**
     * 返回第一个HeaderView
     * @return
     */
    public View getHeaderView() {
        return mHeaderView;
    }

    public void removeHeaderView(View view) {
        mHeaderView = null;
        this.notifyDataSetChanged();
    }

    public void removeFooterView(View view) {
        mFooterView = null;
        this.notifyDataSetChanged();
    }

    public boolean isHeader(int position) {
        return isHeaderEnable && mHeaderView != null && position == 0;
    }

    public boolean isFooter(int position) {
        int lastPosition = getItemCount() - 1;
        return isFooterEnable && mFooterView != null && position == lastPosition;
    }

    @Override
    public RefreshViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new RefreshViewHolder(mHeaderView, TYPE_HEADER);
        } else if (viewType == TYPE_FOOTER) {
            return new RefreshViewHolder(mFooterView, TYPE_FOOTER);
        } else {
            RecyclerView.ViewHolder viewHolder = mAdapter.onCreateViewHolder(parent, viewType);
            RefreshViewHolder refreshViewHolder = new RefreshViewHolder(viewHolder.itemView, viewType);
            refreshViewHolder.mViewHolder = viewHolder;
            return refreshViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RefreshViewHolder holder, int position) {
        if (holder.mType != TYPE_FOOTER && holder.mType != TYPE_HEADER) {
            mAdapter.onBindViewHolder(holder.mViewHolder, (position - getHeaderCount()));
        } else {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if(layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return getHeaderCount() + getFooterCount() + mAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        int count = mAdapter.getItemCount();
        if (position < getHeaderCount()) {
            return TYPE_HEADER;
        } else if (position < (count + getHeaderCount())) {

            int viewType = mAdapter.getItemViewType(position - getHeaderCount());
            int maxType = Integer.MAX_VALUE  - 2;
            if(viewType > maxType) {
                throw new IllegalArgumentException("your adapter's return value of getViewTypeCount() must < " + maxType);
            }
            return viewType;
        } else {
            return TYPE_FOOTER;
        }
    }

    private int getHeaderCount() {
        return isHeaderEnable && mHeaderView != null ? 1 : 0;
    }

    private int getFooterCount() {
        return (isFooterEnable && mFooterView != null) ? 1 : 0;
    }

    public void setHeaderEnable(boolean headerEnable) {
        this.isHeaderEnable = headerEnable;
        notifyDataSetChanged();
    }

    public void setFooterEnable(boolean footerEnable) {
        this.isFooterEnable = footerEnable;
        notifyDataSetChanged();
    }
}
