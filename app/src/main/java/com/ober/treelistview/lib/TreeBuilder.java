package com.ober.treelistview.lib;

/**
 * Created by zlo on 2014/12/23.
 */

public class TreeBuilder<T> {
    private T lastAddedId = null;
    private int lastLevel = -1;
    private final TreeStateManager<T> manager;

    public TreeBuilder(TreeStateManager<T> manager) {
        this.manager = manager;
    }

    private synchronized void  addNodeToParentOneLevelDown(T parent, T id, int level) {
        if ((parent == null) && (level != 0)) {
            throw new TreeConfigurationException
                    ("Trying to add new id " + id + " to top level with level != 0 (" + level + ")");
        }

        if ((parent != null) && (this.manager.getLevel(parent) != level - 1)) {
            throw new TreeConfigurationException
                    ("Trying to add new id " + id + " <" + level + "> to "
                            + parent + " <" + this.manager.getLevel(parent)
                            + ">. The difference in levels up is bigger than 1.");
        }

        this.manager.addAfterChild(parent, id, null);
        setLastAdded(id, level);
    }

    private synchronized T findParentAtLevel(T id, int level) {
        for (T parent = this.manager.getParent(id); ; parent = this.manager.getParent(parent))
            if ((parent == null) || (this.manager.getLevel(parent) == level))
                return parent;
    }

    private synchronized void setLastAdded(T paramT, int paramInt) {
        this.lastAddedId = paramT;
        this.lastLevel = paramInt;
    }

    public synchronized void addRelation(T parent, T id) {
        this.manager.addAfterChild(parent, id, null);
        this.lastAddedId = id;
        this.lastLevel = this.manager.getLevel(id);
    }

    public synchronized void clear() {
        this.manager.clear();
    }

    public synchronized void sequentiallyAddNextNode(T id, int level) {

        if (this.lastAddedId == null) {
            addNodeToParentOneLevelDown(null, id, level);
            return;
        }
        if (level <= this.lastLevel) {
            addNodeToParentOneLevelDown(findParentAtLevel(this.lastAddedId, level - 1), id, level);
        } else {
            addNodeToParentOneLevelDown(this.lastAddedId, id, level);
        }
    }
}