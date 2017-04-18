package com.atman.wysq.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by tangbingliang on 17/3/24.
 */

public class SpaceItemDecorationAblumGrivView extends RecyclerView.ItemDecoration {
    private int space;

    public SpaceItemDecorationAblumGrivView(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.top = space;
        if (parent.getChildPosition(view) % 3 == 0) {
            outRect.right = space/2;
            outRect.left = space;
        } else if (parent.getChildPosition(view) % 3 == 2) {
            outRect.left = space/2;
            outRect.right = space;
        } else {
            outRect.right = space/2;
            outRect.left = space/2;
        }
    }
}
