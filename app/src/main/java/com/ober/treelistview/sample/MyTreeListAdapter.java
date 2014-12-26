package com.ober.treelistview.sample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ober.treelistview.R;
import com.ober.treelistview.sample.data.SimpleData;
import com.ober.treelistview.lib.BaseTreeViewAdapter;
import com.ober.treelistview.lib.TreeStateManager;

/**
 * Created by zlo on 2014/12/24.
 */
public class MyTreeListAdapter extends BaseTreeViewAdapter<SimpleData> {

    public MyTreeListAdapter(Context context, TreeStateManager<SimpleData> treeStateManager, int level) {
        super(context, treeStateManager, level);
    }

    @Override
    protected View bindView(View rootView, SimpleData t, int level, boolean hasChildren, boolean isExpanded) {
        TreeLevelIndicator indicator = (TreeLevelIndicator)rootView.findViewById(R.id.indicator);
        TextView textView = (TextView)rootView.findViewById(R.id.tv);

        SimpleData nextVisibleId = getManager().getNextVisible(t);

        boolean showBottom = false;
        if(nextVisibleId != null) {
            showBottom = getManager().getLevel(nextVisibleId) != 0;
        }

        boolean showTop = getManager().getLevel(t) != 0;

        indicator.setUp(level, hasChildren, isExpanded, showTop, showBottom);
        textView.setText("MyData s = " + t.s);
        return null;
    }

    @Override
    protected int getAdapterViewId() {
        return R.layout.item_listview;
    }

    @Override
    protected View newView(Context context, ViewGroup parent, int level) {
        View root = View.inflate(context, R.layout.item_listview, null);
        return root;
    }

    @Override
    protected void onItemClick(SimpleData t, boolean hasChild) {

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
