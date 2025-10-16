package com.codeycoder.expensetracker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

public class Utilities {
    public static final Rect tmpRect = new Rect();
    public static final RectF tmpRectF = new RectF();
    public static final Path tmpPath = new Path();
    public static final String TAG = "mdebug";
    public static float density = Resources.getSystem().getDisplayMetrics().density;

    public static int dp(float px) {
        return (int) Math.ceil(px * density);
    }

    public static View createDivider(Context context, int color, int height) {
        View divider = new View(context);
        divider.setBackgroundColor(color);
        divider.setLayoutParams(LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, height));
        return divider;
    }
}
