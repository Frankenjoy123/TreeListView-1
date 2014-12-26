package com.ober.treelistview.lib;

import android.database.DataSetObserver;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zlo on 2014/12/23.
 */

public abstract interface TreeStateManager<T> extends Serializable {


    /**
     * @param parentId It will be @newId's parent
     * @param newId The new node to add
     * @param afterId If not null, it will be standing before @newId .
     *                If null, @newId will be the last child of @parentId
     */

    public abstract void addAfterChild(T parentId, T newId, T afterId);

    /**
     * @param parentId it will be @newId's parent
     * @param newId the new node to add
     * @param beforeId If not null, it will be standing after @newId .
     *                If null, @newId will be the first child of @parentId
     */

    public abstract void addBeforeChild(T parentId, T newId, T beforeId);

    /**
     * Clear all
     */

    public abstract void clear();

    /**
     *  Remove ALL children of @id
     */

    public abstract void removeNodeRecursively(T id);

    /**
     * @param id All children of it will be collapsed
     */

    public abstract void collapseChildren(T id);

    /**
     * @param id All DIRECT children of it will be expanded
     */

    public abstract void expandDirectChildren(T id);

    /**
     * @param id ALL children if it will be expanded
     */

    public abstract void expandEverythingBelow(T id);

    /**
     * @param id The parent to be shown it's children
     * @return All DIRECT children of @id
     */

    public abstract List<T> getChildren(T id);

    /**
     * @param id The node to be described;
     * @return The index info array from TopNode to @id;
     */
    public abstract Integer[] getHierarchyDescription(T id);

    /**
     * @return The level of @id
     */

    public abstract int getLevel(T id);

    /**
     * @return The next sibling of @id.
     * Null is returned if @id hasn't next sibling;
     */

    public abstract T getNextSibling(T id);

    /**
     * @return The next visible node next to @id in the tree View;
     */

    public abstract T getNextVisible(T id);

    /**
     * @return The TreeNodeInfo of @id
     */

    public abstract TreeNodeInfo<T> getNodeInfo(T id);

    /**
     * @return The parent of @id
     * note: topNode is null
     */

    public abstract T getParent(T id);

    /**
     * @return The previous sibling of @id
     * Null is returned if @id hasn't previous sibling
     */

    public abstract T getPreviousSibling(T id);

    /**
     * @return The visible nodes' count in the tree View
     */

    public abstract int getVisibleCount();

    /**
     * @return The visible nodes' list in the tree View
     */

    public abstract List<T> getVisibleList();

    /**
     * @return True if @id is in the tree View, false otherwise
     */

    public abstract boolean isInTree(T id);

    /**
     * To notify inner observers that dataSet is changed
     */

    public abstract void refresh();

    public abstract void registerDataSetObserver(DataSetObserver observer);
    public abstract void unregisterDataSetObserver(DataSetObserver observer);
}

