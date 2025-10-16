package com.codeycoder.expensetracker.views;


import static com.codeycoder.expensetracker.Utilities.tmpPath;
import static com.codeycoder.expensetracker.Utilities.tmpRect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.codeycoder.expensetracker.R;

public class RoundableProgressBar extends View {
    int radius = 20;
    float progress = 0.5f;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public RoundableProgressBar(Context context, int radius) {
        super(context);
        this.radius = radius;
    }

    public RoundableProgressBar(Context context) {
        this(context, null);
    }

    public RoundableProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        tmpPath.reset();
        tmpPath.addRoundRect(0, 0, getWidth(), getHeight(), radius, radius, Path.Direction.CW);
        canvas.clipPath(tmpPath);

        paint.setColor(getContext().getColor(R.color.grayscale_100));
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        paint.setColor(getContext().getColor(R.color.success_300));
        canvas.drawRect(0, 0, getWidth() * progress, getHeight(), paint);

    }
}