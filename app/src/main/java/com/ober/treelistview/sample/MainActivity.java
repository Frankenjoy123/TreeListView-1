package com.ober.treelistview.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ober.treelistview.R;
import com.ober.treelistview.sample.data.MyData;
import com.ober.treelistview.lib.InMemoryTreeStateManager;
import com.ober.treelistview.lib.TreeBuilder;
import com.ober.treelistview.lib.TreeStateManager;
import com.ober.treelistview.lib.TreeViewList;


public class MainActivity extends ActionBarActivity {

    private TreeViewList mTreeList;

    private MyTreeListAdapter mAdapter;

    private TreeStateManager<MyData> treeStateManager;


    MyData myData1 = new MyData(1f);
    MyData myData2 = new MyData(1.1f);
    MyData myData3 = new MyData(1.2f);
    MyData myData4 = new MyData(2f);
    MyData myData5 = new MyData(2.1f);
    MyData myData6 = new MyData(2.2f);
    MyData myData7 = new MyData(2.21f);

    MyData myData8 = new MyData(2.11f);

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

        treeStateManager.addAfterChild(myData5, myData8, null);
    }

    private void initTree() {
        TreeBuilder<MyData> treeBuilder = new TreeBuilder<>(treeStateManager);

        treeBuilder.sequentiallyAddNextNode(myData1, 0);
        treeBuilder.sequentiallyAddNextNode(myData2, 1);
        treeBuilder.sequentiallyAddNextNode(myData3, 1);
        treeBuilder.sequentiallyAddNextNode(myData4, 0);
        treeBuilder.sequentiallyAddNextNode(myData5, 1);
        treeBuilder.sequentiallyAddNextNode(myData6, 1);
        treeBuilder.sequentiallyAddNextNode(myData7, 2);

        treeStateManager.collapseChildren(myData1);
        treeStateManager.collapseChildren(myData4);
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
