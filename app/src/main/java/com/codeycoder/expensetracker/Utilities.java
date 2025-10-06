package com.codeycoder.expensetracker;

import android.content.res.Resources;

public class Utilities {
    public static final String TAG = "mdebug";
    public static float density = Resources.getSystem().getDisplayMetrics().density;

    public static int dp(float px) {
        return (int) Math.ceil(px * density);
    }
}
