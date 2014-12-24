package com.ober.treelistview.view;

/**
 * Created by zlo on 2014/12/23.
 */

public class NodeNotInTreeException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public NodeNotInTreeException(String node) {
        super("The tree does not contain the node specified: " + node);
    }
}
