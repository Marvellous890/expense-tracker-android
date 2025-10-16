package com.codeycoder.expensetracker.views;

import static com.codeycoder.expensetracker.Utilities.dp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.codeycoder.expensetracker.LayoutHelper;

public class Button extends LinearLayout {
    TextView textView;
    ImageView icon;

    public Button(@NonNull Context context) {
        this(context, null);
    }

    public Button(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        setGravity(Gravity.CENTER);
        setPadding(dp(10), dp(4), dp(10), dp(4));

        textView = new TextView(getContext());
        // textView.setText(getContext().getString(R.string.see_all));
        addView(textView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        icon = new ImageView(getContext());
        addView(icon, LayoutHelper.createLinear(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    public void setText(CharSequence text) {
        textView.setText(text);
    }

    public void setIcon(Drawable icon) {
        this.icon.setImageDrawable(icon);
    }

    public void setIconMargins(float leftMargin, float topMargin, float rightMargin, float bottomMargin) {
        LayoutParams layoutParams = (LayoutParams) icon.getLayoutParams();
        layoutParams.leftMargin = dp(leftMargin);
        layoutParams.topMargin = dp(topMargin);
        layoutParams.rightMargin = dp(rightMargin);
        layoutParams.bottomMargin = dp(bottomMargin);
        icon.requestLayout();
    }

    public ImageView getIconView() {
        return icon;
    }
}
