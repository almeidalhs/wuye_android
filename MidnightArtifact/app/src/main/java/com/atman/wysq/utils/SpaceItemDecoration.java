package com.atman.wysq.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by tangbingliang on 17/3/24.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildPosition(view) == 0) {
            outRect.right = space/2;
        } else if (parent.getChildPosition(view) == 4) {
            outRect.left = space/2;
        } else {
            outRect.left = space/2;
            outRect.right = space/2;
        }
    }
}
