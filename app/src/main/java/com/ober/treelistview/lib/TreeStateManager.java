package com.ober.treelistview.lib;

import android.database.DataSetObserver;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zlo on 2014/12/23.
 */

public abstract interface TreeStateManager<T> extends Serializable {

    public abstract void addAfterChild(T parentId, T newId, T afterId);

    public abstract void addBeforeChild(T parentId, T newId, T beforeId);

    public abstract void clear();

    public abstract void collapseChildren(T id);

    public abstract void expandDirectChildren(T id);

    public abstract void expandEverythingBelow(T id);

    public abstract List<T> getChildren(T id);

    /**
     * @param id node的id
     * @return 从根节点到当前节点，每一层的index
     */
    public abstract Integer[] getHierarchyDescription(T id);

    public abstract int getLevel(T id);

    public abstract T getNextSibling(T id);

    public abstract T getNextVisible(T id);

    public abstract TreeNodeInfo<T> getNodeInfo(T id);

    public abstract T getParent(T id);

    public abstract T getPreviousSibling(T id);

    public abstract int getVisibleCount();

    public abstract List<T> getVisibleList();

    public abstract boolean isInTree(T id);

    public abstract void refresh();

    public abstract void registerDataSetObserver(DataSetObserver observer);

    public abstract void removeNodeRecursively(T id);

    public abstract void unregisterDataSetObserver(DataSetObserver observer);
}

