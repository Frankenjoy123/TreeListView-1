package com.ober.treelistview.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

/**
 * Created by zlo on 2014/12/23.
 */

public abstract class BaseTreeViewAdapter<T> extends BaseAdapter
        implements ListAdapter {

    protected Context context;
    private BaseTreeViewAdapterDelegate delegate;
    private final int numberOfLevels;
    private final TreeStateManager<T> treeStateManager;

    public BaseTreeViewAdapter(Context context, TreeStateManager<T> treeStateManager, int level) {
        this.treeStateManager = treeStateManager;
        this.context = context;
        this.numberOfLevels = level;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    protected abstract View bindView(View rootView, T t, int level, boolean hasChildren, boolean isExpanded);

    protected void expandCollapse(T t) {
        TreeNodeInfo<T> info = this.treeStateManager.getNodeInfo(t);
        if (!info.hasChildren()) {
            return;
        }

        if (info.isExpanded()) {
            this.treeStateManager.collapseChildren(t);
            if (this.delegate != null)
                this.delegate.onCollapse();
        } else {
            this.treeStateManager.expandDirectChildren(t);
            if (this.delegate != null)
                this.delegate.onExpand();
        }
    }

    protected abstract int getAdapterViewId();

    @Override
    public int getCount() {
        return this.treeStateManager.getVisibleCount();
    }

    @Override
    public Object getItem(int position) {
        return getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return getTreeNodeInfo(position).getLevel();
    }

    protected TreeStateManager<T> getManager() {
        return this.treeStateManager;
    }

    public T getTreeId(int position) {
        return this.treeStateManager.getVisibleList().get(position);
    }

    public TreeNodeInfo<T> getTreeNodeInfo(int position) {
        return this.treeStateManager.getNodeInfo(getTreeId(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("BaseTreeViewAdapter",
                "Creating a view based on " + convertView + " with position " + position);
        TreeNodeInfo<T> info = getTreeNodeInfo(position);
        View root = convertView;
        if ((convertView == null) || (convertView.getId() != getAdapterViewId())) {
            root = newView(this.context, parent, info.getLevel());
            root.setId(getAdapterViewId());
        }
        bindView(root, info.getId(),
                info.getLevel(),
                info.hasChildren(),
                info.isExpanded());
        return root;
    }

    @Override
    public int getViewTypeCount() {
        return this.numberOfLevels;
    }

    public void handleItemClick(View view, int position) {
        TreeNodeInfo<T> info = getTreeNodeInfo(position);
        T id = info.getId();
        if (!info.hasChildren()) {
            //todo
            return;
        }

        boolean hasChild = info.hasChildren();

        onItemClick(id, hasChild);

        if (hasChild) {
            expandCollapse(info.getId());
        }

    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public boolean isEnabled(int paramInt) {
        return true;
    }

    protected abstract View newView(Context context, ViewGroup parent, int level);

    protected abstract void onItemClick(T t, boolean hasChild);

    public void refresh() {
        this.treeStateManager.refresh();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        this.treeStateManager.registerDataSetObserver(observer);
    }

    public void setDelegate(BaseTreeViewAdapterDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        this.treeStateManager.unregisterDataSetObserver(observer);
    }

    public static abstract class BaseTreeViewAdapterDelegate {
        public abstract void onCollapse();
        public abstract void onExpand();
    }
}

