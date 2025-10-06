package com.codeycoder.expensetracker;

import static com.codeycoder.expensetracker.Utilities.dp;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class LayoutHelper {
    public static final int MATCH_PARENT = -1;
    public static final int WRAP_CONTENT = -2;

    public static int getSize(float size) {
        return (int) (size < 0 ? size : dp(size));
    }

    // region FrameLayout

    public static FrameLayout.LayoutParams createFrame(float width, float height) {
        return new FrameLayout.LayoutParams(getSize(width), getSize(height));
    }

    public static FrameLayout.LayoutParams createFrame(float width, float height, int gravity) {
        return new FrameLayout.LayoutParams(getSize(width), getSize(height), gravity);
    }

    public static FrameLayout.LayoutParams createFrame(float width, float height, float leftMargin, float topMargin, float rightMargin, float bottomMargin) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(getSize(width), getSize(height));
        layoutParams.setMargins(dp(leftMargin), dp(topMargin), dp(rightMargin), dp(bottomMargin));
        return layoutParams;
    }

    public static FrameLayout.LayoutParams createFrame(float width, float height, int gravity, float leftMargin, float topMargin, float rightMargin, float bottomMargin) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(getSize(width), getSize(height), gravity);
        layoutParams.setMargins(dp(leftMargin), dp(topMargin), dp(rightMargin), dp(bottomMargin));
        return layoutParams;
    }

    public static FrameLayout.LayoutParams createFrameMatchParent() {
        return new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
    }

    // endregion

    // region LinearLayout

    public static LinearLayout.LayoutParams createLinear(float width, float height) {
        return new LinearLayout.LayoutParams(getSize(width), getSize(height));
    }

    public static LinearLayout.LayoutParams createLinear(float width, float height, float weight) {
        return new LinearLayout.LayoutParams(getSize(width), getSize(height), weight);
    }

    public static LinearLayout.LayoutParams createLinear(float width, float height, float leftMargin, float topMargin, float rightMargin, float bottomMargin) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getSize(width), getSize(height));
        layoutParams.setMargins(dp(leftMargin), dp(topMargin), dp(rightMargin), dp(bottomMargin));
        return layoutParams;
    }

    public static LinearLayout.LayoutParams createLinear(float width, float height, int weight, float leftMargin, float topMargin, float rightMargin, float bottomMargin) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getSize(width), getSize(height), weight);
        layoutParams.setMargins(dp(leftMargin), dp(topMargin), dp(rightMargin), dp(bottomMargin));
        return layoutParams;
    }

    public static LinearLayout.LayoutParams createLinearMatchParent() {
        return new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
    }

    public static LinearLayout.LayoutParams createLinearWrapContent() {
        return new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
    }

    // endregion

}
