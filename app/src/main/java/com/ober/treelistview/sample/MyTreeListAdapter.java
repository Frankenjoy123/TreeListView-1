package com.ober.treelistview.sample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ober.treelistview.R;
import com.ober.treelistview.sample.data.MyData;
import com.ober.treelistview.lib.BaseTreeViewAdapter;
import com.ober.treelistview.lib.TreeStateManager;

/**
 * Created by zlo on 2014/12/24.
 */
public class MyTreeListAdapter extends BaseTreeViewAdapter<MyData> {

    public MyTreeListAdapter(Context context, TreeStateManager<MyData> treeStateManager, int level) {
        super(context, treeStateManager, level);
    }

    @Override
    protected View bindView(View rootView, MyData t, int level, boolean hasChildren, boolean isExpanded) {
        TreeLevelIndicator indicator = (TreeLevelIndicator)rootView.findViewById(R.id.indicator);
        TextView textView = (TextView)rootView.findViewById(R.id.tv);

        indicator.setUp(level, hasChildren, isExpanded, isExpanded, isExpanded);
        textView.setText("MyData num = " + t.num);
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
    protected void onItemClick(MyData t, boolean hasChild) {

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
