package com.ober.treelistview.lib;

/**
 * Created by zlo on 2014/12/26.
 */
public interface TreeBuilder<T> {


    /**
     * Add child @id to parent @parent
     * if @parent has some children ,@id will add to last
     */
    public void addRelation(T parent, T id);

    /**
     * Clear all nodes
     */
    public void clear();

    /**
     *  Most likely Use to sequentially add nodes.
     *  The function relates to recent state of TreeBuilder.
     */

    public void sequentiallyAddNextNode(T id, int level);

}
