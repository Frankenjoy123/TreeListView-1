package com.ober.treelistview.lib;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zlo on 2014/12/23.
 */

class InMemoryTreeNode<T> implements Serializable {

    private List<T> childIdListCache = null;
    private final List<InMemoryTreeNode<T>> children = new LinkedList<>();
    private final T id;
    private final int level;
    private final T parent;
    private boolean visible = true;

    public InMemoryTreeNode(T id, T parent, int level, boolean visible) {
        this.id = id;
        this.parent = parent;
        this.level = level;
        this.visible = visible;
    }

    public synchronized InMemoryTreeNode<T> add(int index, T newId, boolean visible) {
        this.childIdListCache = null;
        T parentId = getId();
        int newLevel = 1 + getLevel();
        if (getId() == null) {
            visible = true;
        }
        InMemoryTreeNode<T> newNode = new InMemoryTreeNode<>(newId, parentId, newLevel, visible);
        this.children.add(index, newNode);
        return newNode;
    }

    public synchronized void clearChildren() {
        this.children.clear();
        this.childIdListCache = null;
    }

    public synchronized List<T> getChildIdList() {
        if (this.childIdListCache == null) {
            this.childIdListCache = new LinkedList<>();
            for (InMemoryTreeNode<T> child : this.children) {
                this.childIdListCache.add(child.getId());
            }
        }
        return this.childIdListCache;
    }

    public synchronized List<InMemoryTreeNode<T>> getChildren() {
        return this.children;
    }

    public synchronized int getChildrenListSize() {
        return this.children.size();
    }

    T getId() {
        return this.id;
    }

    int getLevel() {
        return this.level;
    }

    T getParent() {
        return this.parent;
    }

    public synchronized int indexOf(T paramT) {
        return getChildIdList().indexOf(paramT);
    }

    public synchronized boolean isVisible() {
        return this.visible;
    }

    public synchronized void removeChild(T id) {
        int i = indexOf(id);
        if (i != -1) {
            this.children.remove(i);
            this.childIdListCache = null;
        }
    }

    public synchronized void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return "InMemoryTreeNode [id="
                + getId() + ", parent="
                + getParent() + ", level="
                + getLevel() + ", visible="
                + this.visible + ", children="
                + this.children + ", childIdListCache="
                + this.childIdListCache + "]";
    }
}
