package com.codeycoder.expensetracker;

import android.content.res.Resources;

public class Utilities {
    public static final String TAG = "mdebug";

    public static float dpF(float px) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return px * density;
    }

    public static int dp(int px) {
        return (int) dpF(px);
    }
}
