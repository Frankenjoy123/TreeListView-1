package com.ober.treelistview.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ober.treelistview.R;

/**
 * Created by zlo on 2014/12/23.
 */
public class TreeLevelIndicator extends RelativeLayout {
    protected ImageView imageToggle;
    protected View lineTop;
    protected View lineBottom;

    public TreeLevelIndicator(Context context) {
        this(context, null);
    }

    public TreeLevelIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TreeLevelIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.view_indicator_tree_level, this);
        init();
    }

    private void init() {

        imageToggle = (ImageView) findViewById(R.id.imageToggle);
        lineTop = findViewById(R.id.lineTop);
        lineBottom = findViewById(R.id.lineBottom);
    }

    public void setUp(int level, boolean hasChild, boolean expanded,
                      boolean showTop, boolean showBottom) {

        int image = hasChild ? expanded ?
                R.drawable.tree_level_indicator_expanded :
                R.drawable.tree_level_indicator_collapsed :
                R.drawable.tree_level_indicator_null;

        imageToggle.setImageResource(image);
        imageToggle.setImageLevel(level);
        lineTop.setVisibility(showTop ? VISIBLE : INVISIBLE);
        lineBottom.setVisibility(showBottom ? VISIBLE : INVISIBLE);
    }
}