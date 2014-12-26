package com.ober.treelistview.lib;

/**
 * Created by zlo on 2014/12/26.
 */
public interface TreeBuilder<T> {

    public void addRelation(T parent, T id);

    public void clear();

    public void sequentiallyAddNextNode(T id, int level);

}
