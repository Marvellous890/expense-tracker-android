package com.codeycoder.expensetracker.views;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.codeycoder.expensetracker.R;
import com.codeycoder.expensetracker.Utilities;

public class RoundedCard extends FrameLayout {
    int radius;

    // todo remove
    public RoundedCard(Context context) {
        this(context, null);
    }

    public RoundedCard(Context context, AttributeSet attrs) {
        super(context, attrs);

        try (TypedArray styleAttrs = context.obtainStyledAttributes(attrs, R.styleable.RoundedCard)) {
            this.radius = styleAttrs.getDimensionPixelSize(R.styleable.RoundedCard_radius, 0);
        }
    }
    // --------

    public RoundedCard(Context context, int radius) {
        super(context);
        this.radius = radius;
        setBackgroundColor(0xFFFFFFFF);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Path clipPath = new Path();
        clipPath.addRoundRect(new RectF(0, 0, getWidth(), getHeight()), radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.draw(canvas);
    }
}