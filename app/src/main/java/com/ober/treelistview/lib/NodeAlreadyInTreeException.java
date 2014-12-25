package com.ober.treelistview.lib;

/**
 * Created by zlo on 2014/12/23.
 */
public class NodeAlreadyInTreeException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public NodeAlreadyInTreeException(String tree, String node) {
        super("The node has already been added to the tree: " + tree + ". Old node is:" + node);
    }
}
