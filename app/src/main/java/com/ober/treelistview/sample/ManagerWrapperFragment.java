package com.ober.treelistview.sample;

import android.app.Activity;
import android.database.DataSetObserver;
import android.support.v4.app.Fragment;

import com.ober.treelistview.lib.TreeNodeInfo;
import com.ober.treelistview.lib.TreeStateManager;
import com.ober.treelistview.sample.data.SimpleData;

import java.util.List;

/**
 * Created by zlo on 2014/12/26.
 */
public class ManagerWrapperFragment extends Fragment implements TreeStateManager<SimpleData> {

    private TreeStateManager<SimpleData> mManager;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof  MainActivity) {
            this.mManager = ((MainActivity) activity).treeStateManager;
        }
    }

    @Override
    public void addAfterChild(SimpleData parentId, SimpleData newId, SimpleData afterId) {

    }

    @Override
    public void addBeforeChild(SimpleData parentId, SimpleData newId, SimpleData beforeId) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void collapseChildren(SimpleData id) {

    }

    @Override
    public void expandDirectChildren(SimpleData id) {

    }

    @Override
    public void expandEverythingBelow(SimpleData id) {

    }

    @Override
    public List<SimpleData> getChildren(SimpleData id) {
        return null;
    }

    @Override
    public Integer[] getHierarchyDescription(SimpleData id) {
        return new Integer[0];
    }

    @Override
    public int getLevel(SimpleData id) {
        return 0;
    }

    @Override
    public SimpleData getNextSibling(SimpleData id) {
        return null;
    }

    @Override
    public SimpleData getNextVisible(SimpleData id) {
        return null;
    }

    @Override
    public TreeNodeInfo<SimpleData> getNodeInfo(SimpleData id) {
        return null;
    }

    @Override
    public SimpleData getParent(SimpleData id) {
        return null;
    }

    @Override
    public SimpleData getPreviousSibling(SimpleData id) {
        return null;
    }

    @Override
    public int getVisibleCount() {
        return 0;
    }

    @Override
    public List<SimpleData> getVisibleList() {
        return null;
    }

    @Override
    public boolean isInTree(SimpleData id) {
        return false;
    }

    @Override
    public void refresh() {

    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void removeNodeRecursively(SimpleData id) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }
}
