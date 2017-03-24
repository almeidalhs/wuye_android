package com.atman.wysq.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by tangbingliang on 17/3/24.
 */

public class SpaceItemDecorationGrivView extends RecyclerView.ItemDecoration {
    private int space;

    public SpaceItemDecorationGrivView(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildPosition(view)==0) {
            outRect.left = space*2;
            outRect.right = space;
            outRect.bottom = space;
            outRect.top = space*3;
        } else if (parent.getChildPosition(view)==1) {
            outRect.right = space*2;
            outRect.left = space;
            outRect.bottom = space;
            outRect.top = space*3;
        } else {
            if (parent.getChildPosition(view) %2 == 0) {
                outRect.left = space*2;
                outRect.right = space;
                outRect.bottom = space;
                outRect.top = space;
            } else {
                outRect.right = space*2;
                outRect.left = space;
                outRect.bottom = space;
                outRect.top = space;
            }
        }
    }
}
