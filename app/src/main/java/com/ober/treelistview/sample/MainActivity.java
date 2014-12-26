package com.ober.treelistview.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ober.treelistview.R;
import com.ober.treelistview.lib.TreeBuilderImpl;
import com.ober.treelistview.sample.data.SimpleData;
import com.ober.treelistview.lib.InMemoryTreeStateManager;
import com.ober.treelistview.lib.TreeStateManager;
import com.ober.treelistview.lib.TreeViewList;


public class MainActivity extends ActionBarActivity {

    private TreeViewList mTreeList;

    private MyTreeListAdapter mAdapter;

    protected TreeStateManager<SimpleData> treeStateManager;

    SimpleData simpleData1 = new SimpleData("1");
    SimpleData simpleData2 = new SimpleData("1.1");
    SimpleData simpleData3 = new SimpleData("1.2");
    SimpleData simpleData4 = new SimpleData("2");
    SimpleData simpleData5 = new SimpleData("2.1");
    SimpleData simpleData6 = new SimpleData("2.2");
    SimpleData simpleData7 = new SimpleData("2.21");

    SimpleData simpleData8 = new SimpleData("2.11");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTreeList = (TreeViewList) findViewById(R.id.treeViewList);

        treeStateManager = new InMemoryTreeStateManager<>();

        treeStateManager.clear();
        initTree();

        mAdapter = new MyTreeListAdapter(this, treeStateManager, 5);

        mTreeList.setAdapter(mAdapter);

        treeStateManager.addAfterChild(simpleData5, simpleData8, null);

    }

    private void initTree() {
        TreeBuilderImpl<SimpleData> treeBuilder = new TreeBuilderImpl<>(treeStateManager);

        treeBuilder.sequentiallyAddNextNode(simpleData1, 0);
        treeBuilder.sequentiallyAddNextNode(simpleData2, 1);
        treeBuilder.sequentiallyAddNextNode(simpleData3, 1);
        treeBuilder.sequentiallyAddNextNode(simpleData4, 0);
        treeBuilder.sequentiallyAddNextNode(simpleData5, 1);
        treeBuilder.sequentiallyAddNextNode(simpleData6, 1);
        treeBuilder.sequentiallyAddNextNode(simpleData7, 2);

        treeStateManager.collapseChildren(simpleData1);
        treeStateManager.collapseChildren(simpleData4);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
