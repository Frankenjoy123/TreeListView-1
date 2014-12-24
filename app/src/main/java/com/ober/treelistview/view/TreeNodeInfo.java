package com.ober.treelistview.view;

/**
 * Created by zlo on 2014/12/23.
 */
public class TreeNodeInfo<T> {
    private final boolean expanded;
    private final boolean hasChildren;
    private final T id;
    private final int level;
    private final boolean visible;

    public TreeNodeInfo(T id, int level, boolean hasChildren, boolean visible, boolean expanded)
    {
        this.id = id;
        this.level = level;
        this.hasChildren = hasChildren;
        this.visible = visible;
        this.expanded = expanded;
    }

    public T getId()
    {
        return this.id;
    }

    public int getLevel()
    {
        return this.level;
    }

    public boolean hasChildren()
    {
        return this.hasChildren;
    }

    public boolean isExpanded()
    {
        return this.expanded;
    }

    public boolean isVisible()
    {
        return this.visible;
    }

    public String toString()
    {
        return "TreeNodeInfo [id=" + this.id
                + ", level=" + this.level
                + ", withChildren=" + this.hasChildren
                + ", visible=" + this.visible
                + ", expanded=" + this.expanded
                + "]";
    }
}