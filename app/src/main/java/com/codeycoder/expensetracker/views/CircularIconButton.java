package com.codeycoder.expensetracker.views;

import static com.codeycoder.expensetracker.Utilities.dp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

public class CircularIconButton extends FrameLayout {

    public CircularIconButton(@NonNull Context context) {
        super(context);
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        float radius = Math.min(getWidth(), getHeight()) / 2f;
        float cx = getWidth() / 2f;
        float cy = getHeight() / 2f;

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFFFFFFFF);
        canvas.drawCircle(cx, cy, radius, paint);
        paint.setColor(0xFFE7E5E4);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dp(2));
        canvas.drawCircle(cx, cy, radius - paint.getStrokeWidth() / 2f, paint);
        super.dispatchDraw(canvas);
    }
}
