package com.atman.wysq.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by tangbingliang on 17/3/24.
 */

public class SpaceItemDecorationLinear extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceItemDecorationLinear(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.bottom = space;
    }
}
