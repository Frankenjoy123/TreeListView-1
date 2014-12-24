package com.ober.treelistview.view;

import android.database.DataSetObserver;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zlo on 2014/12/23.
 */
public class InMemoryTreeStateManager<T>
        implements TreeStateManager<T> {

    private final Map<T, InMemoryTreeNode<T>> allNodes = new HashMap<>();
    private final transient Set<DataSetObserver> observers = new HashSet<>();
    private final InMemoryTreeNode<T> topNode = new InMemoryTreeNode<>(null, null, -1, true);
    private transient List<T> unmodifiableVisibleList = null;
    private boolean visibleByDefault = true;
    private transient List<T> visibleListCache = null;

    private void appendToSb(StringBuilder sBuilder, T id) {
        if (id != null) {
            TreeNodeInfo info = getNodeInfo(id);
            char[] arrayOfChar = new char[4 * info.getLevel()];
            Arrays.fill(arrayOfChar, ' ');
            sBuilder.append(arrayOfChar);
            sBuilder.append(info.toString());
            sBuilder.append(Arrays.asList(getHierarchyDescription(id)).toString());
            sBuilder.append("\n");
        }

        for (T t : getChildren(id)) {
            appendToSb(sBuilder, t);
        }
    }

    private void expectNodeNotInTreeYet(T id) {
        InMemoryTreeNode<T> localInMemoryTreeNode = this.allNodes.get(id);
        if (localInMemoryTreeNode != null)
            throw new NodeAlreadyInTreeException(id.toString(), localInMemoryTreeNode.toString());
    }

    private boolean getChildrenVisibility(InMemoryTreeNode<T> node) {
        List<InMemoryTreeNode<T>> childList = node.getChildren();
        if (childList.isEmpty()) {
            return visibleByDefault;
        }
        return (childList.get(0)).isVisible();
    }

    private InMemoryTreeNode<T> getNodeFromTreeOrThrow(T id) {
        if (id == null) {
            throw new NodeNotInTreeException("(null)");
        }
        InMemoryTreeNode<T> node = this.allNodes.get(id);
        if (node == null) {
            throw new NodeNotInTreeException(id.toString());
        }

        return node;
    }

    private InMemoryTreeNode<T> getNodeFromTreeOrThrowAllowRoot(T id) {
        if (id == null) {
            return this.topNode;
        }

        return getNodeFromTreeOrThrow(id);
    }

    private void internalDataSetChanged() {
        this.visibleListCache = null;
        this.unmodifiableVisibleList = null;
        Iterator<DataSetObserver> localIterator = this.observers.iterator();

        if (localIterator.hasNext()) {
            localIterator.next().onChanged();
        }

    }

    private boolean removeNodeRecursively(InMemoryTreeNode<T> node) {
        boolean visibleChanged = false;

        for (InMemoryTreeNode<T> child : node.getChildren()) {
            visibleChanged = removeNodeRecursively(child);
        }

        node.clearChildren();

        if (node.getId() != null) {
            this.allNodes.remove(node.getId());
            visibleChanged = node.isVisible();
        }

        return visibleChanged;
    }

    private void setChildrenVisibility(InMemoryTreeNode<T> parent,
                                       boolean visible, boolean recursive) {
        for (InMemoryTreeNode<T> child : parent.getChildren()) {
            child.setVisible(visible);

            if (recursive) {
                setChildrenVisibility(child, visible, true);
            }
        }
    }

    @Override
    public void addAfterChild(T parentId, T newId, T afterId) {
        while (true) {
            expectNodeNotInTreeYet(newId);
            InMemoryTreeNode<T> parent = getNodeFromTreeOrThrowAllowRoot(parentId);
            boolean visibility = getChildrenVisibility(parent);

            if (afterId == null) {
                InMemoryTreeNode<T> newNode = parent
                        .add(parent.getChildrenListSize(), newId, visibility);
                this.allNodes.put(newId, newNode);
                if (visibility) {
                    internalDataSetChanged();
                }
                return;
            }

            int index = parent.indexOf(afterId);
            InMemoryTreeNode<T> newNode;
            int newIndex = index == -1 ? parent.getChildrenListSize() : index + 1;

            newNode = parent.add(newIndex, newId, visibility);
            this.allNodes.put(newId, newNode);

            if (visibility) {
                internalDataSetChanged();
            }
        }

    }

    @Override
    public void addBeforeChild(T parentId, T newId, T beforeId) {

        expectNodeNotInTreeYet(newId);
        InMemoryTreeNode<T> parent = getNodeFromTreeOrThrowAllowRoot(parentId);
        boolean visibility = getChildrenVisibility(parent);
        if (beforeId == null) {
            InMemoryTreeNode<T> newNode = parent.add(0, newId, visibility);
            this.allNodes.put(newId, newNode);
            if (visibility) {
                internalDataSetChanged();
            }
            return;
        }

        int index = parent.indexOf(beforeId);
        if (index == -1)
            index = 0;
        InMemoryTreeNode<T> newNode = parent.add(index, newId, visibility);
        this.allNodes.put(newId, newNode);
    }

    @Override
    public void clear() {
        this.allNodes.clear();
        this.topNode.clearChildren();
        internalDataSetChanged();
    }

    @Override
    public void collapseChildren(T id) {
        InMemoryTreeNode<T> node;

        node = getNodeFromTreeOrThrowAllowRoot(id);
        if (node == this.topNode) {
            for (InMemoryTreeNode<T> child : this.topNode.getChildren())
                setChildrenVisibility(child, false, true);
        }

        setChildrenVisibility(node, false, true);
        internalDataSetChanged();
    }

    @Override
    public void expandDirectChildren(T id) {
        Log.d("InMemoryTreeStateManager", "Expanding direct children of " + id);
        setChildrenVisibility(getNodeFromTreeOrThrowAllowRoot(id), true, false);
        internalDataSetChanged();
    }

    @Override
    public void expandEverythingBelow(T id) {
        Log.d("InMemoryTreeStateManager", "Expanding all children below " + id);
        setChildrenVisibility(getNodeFromTreeOrThrowAllowRoot(id), true, true);
        internalDataSetChanged();
    }

    @Override
    public List<T> getChildren(T id) {
        return getNodeFromTreeOrThrowAllowRoot(id).getChildIdList();
    }

    @Override
    public Integer[] getHierarchyDescription(T id) {
        int index = getLevel(id);
        Integer[] description = new Integer[index + 1];
        T current = id;
        T parent = getParent(current);
        for (int j = index; j >= 0; j--) {
            description[j] = getChildren(parent).indexOf(current);
            current = parent;
            parent = getParent(parent);
        }
        return description;
    }

    @Override
    public int getLevel(T id) {
        return getNodeFromTreeOrThrow(id).getLevel();
    }

    @Override
    public T getNextSibling(T id) {
        InMemoryTreeNode<T> parent = getNodeFromTreeOrThrowAllowRoot(getParent(id));

        boolean found = false;
        for (InMemoryTreeNode<T> child : parent.getChildren()) {
            if (found) {
                return child.getId();
            }
            found = child.getId().equals(id);
        }
        return null;
    }

    @Override
    public T getNextVisible(T id) {
        final InMemoryTreeNode<T> node = getNodeFromTreeOrThrowAllowRoot(id);
        if (!node.isVisible()) {
            return null;
        }

        final List<InMemoryTreeNode<T>> children = node.getChildren();
        if (!children.isEmpty()) {
            final InMemoryTreeNode<T> firstChild = children.get(0);
            if (firstChild.isVisible()) {
                return firstChild.getId();
            }
        }

        final T sibling = getNextSibling(id);
        if (sibling != null) {
            return sibling;
        }
        T parent = node.getParent();
        do {
            if (parent == null) {
                return null;
            }
            final T parentSibling = getNextSibling(parent);
            if (parentSibling != null) {
                return parentSibling;
            }
            parent = getNodeFromTreeOrThrow(parent).getParent();
        } while (true);
    }

    @Override
    public TreeNodeInfo<T> getNodeInfo(T id) {
        boolean hasChildren = false;
        boolean expanded = false;

        InMemoryTreeNode<T> node = getNodeFromTreeOrThrow(id);
        List<InMemoryTreeNode<T>> children = node.getChildren();

        if (!children.isEmpty()) {
            hasChildren = true;
        }

        if ((!children.isEmpty()) && (children.get(0).isVisible())) {
            expanded = true;
        }

        int level = node.getLevel();

        return new TreeNodeInfo<>(id, level, hasChildren, node.isVisible(), expanded);
    }

    @Override
    public T getParent(T id) {
        return getNodeFromTreeOrThrowAllowRoot(id).getParent();
    }

    @Override
    public T getPreviousSibling(T id) {
        final T parent = getParent(id);
        final InMemoryTreeNode<T> parentNode = getNodeFromTreeOrThrowAllowRoot(parent);
        T previousSibling = null;
        for (final InMemoryTreeNode<T> child : parentNode.getChildren()) {
            if (child.getId().equals(id)) {
                return previousSibling;
            }
            previousSibling = child.getId();
        }
        return null;
    }

    @Override
    public int getVisibleCount() {
        return getVisibleList().size();
    }

    @Override
    public List<T> getVisibleList() {
        T id = null;

        if (this.visibleListCache == null) {
            this.visibleListCache = new ArrayList<>(this.allNodes.size());
        }

        while (true) {
            id = getNextVisible(id);
            if (id == null) {
                if (this.unmodifiableVisibleList == null)
                    this.unmodifiableVisibleList = Collections.unmodifiableList(this.visibleListCache);
                return this.unmodifiableVisibleList;
            }
            this.visibleListCache.add(id);
        }

    }

    @Override
    public boolean isInTree(T id) {
        return this.allNodes.containsKey(id);
    }

    @Override
    public void refresh() {
        internalDataSetChanged();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver paramDataSetObserver) {
        observers.add(paramDataSetObserver);
    }

    @Override
    public void removeNodeRecursively(T id) {
        InMemoryTreeNode<T> node = getNodeFromTreeOrThrowAllowRoot(id);
        boolean visibleChanged = removeNodeRecursively(node);
        getNodeFromTreeOrThrowAllowRoot(node.getParent()).removeChild(id);
        if (visibleChanged) {
            internalDataSetChanged();
        }
    }

    public void setVisibleByDefault(boolean paramBoolean) {
        this.visibleByDefault = paramBoolean;
    }

    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        appendToSb(sBuilder, null);
        return sBuilder.toString();
    }

    public void unregisterDataSetObserver(DataSetObserver paramDataSetObserver) {
        this.observers.remove(paramDataSetObserver);
    }
}
