package com.ober.treelistview.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ober.treelistview.R;
import com.ober.treelistview.sample.data.MyData;
import com.ober.treelistview.view.InMemoryTreeStateManager;
import com.ober.treelistview.view.TreeBuilder;
import com.ober.treelistview.view.TreeStateManager;
import com.ober.treelistview.view.TreeViewList;


public class MainActivity extends ActionBarActivity {

    private TreeViewList mTreeList;

    private MyTreeListAdapter mAdapter;

    private TreeStateManager<MyData> treeStateManager;

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

    }

    private void initTree() {

        TreeBuilder<MyData> treeBuilder = new TreeBuilder<>(treeStateManager);

        MyData myData1 = new MyData(0);
        MyData myData2 = new MyData(1);
        MyData myData3 = new MyData(2);

        treeBuilder.sequentiallyAddNextNode(myData1, 0);
        treeBuilder.sequentiallyAddNextNode(myData2, 1);
        treeBuilder.sequentiallyAddNextNode(myData3, 1);

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
