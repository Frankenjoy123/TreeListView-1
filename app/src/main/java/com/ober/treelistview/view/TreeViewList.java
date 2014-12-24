package com.ober.treelistview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by zlo on 2014/12/23.
 */
public class TreeViewList extends ListView {
    private BaseTreeViewAdapter<?> treeAdapter;

    public TreeViewList(Context paramContext) {
        super(paramContext);
    }

    public TreeViewList(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public TreeViewList(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    private void syncAdapter() {
        setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < getHeaderViewsCount()) {
                    //todo:
                    return;
                }

                treeAdapter.handleItemClick(view, position - getHeaderViewsCount());
            }
        });
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        if (adapter == null) {
            super.setAdapter(null);
            return;
        }

        if (!(adapter instanceof BaseTreeViewAdapter)) {
            throw new TreeConfigurationException("The adapter is not of TreeViewAdapter type");
        }

        this.treeAdapter = ((BaseTreeViewAdapter) adapter);
        syncAdapter();
        super.setAdapter(this.treeAdapter);

    }
}
