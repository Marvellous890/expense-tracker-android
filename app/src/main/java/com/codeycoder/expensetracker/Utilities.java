package com.codeycoder.expensetracker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

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

    public static String geyFriendlyDateTime(long timestamp) {
        Date txDate = new Date(timestamp);

        java.util.Calendar nowCal = java.util.Calendar.getInstance();
        // Normalize to midnight for day-diff comparison
        nowCal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        nowCal.set(java.util.Calendar.MINUTE, 0);
        nowCal.set(java.util.Calendar.SECOND, 0);
        nowCal.set(java.util.Calendar.MILLISECOND, 0);

        java.util.Calendar txCal = java.util.Calendar.getInstance();
        txCal.setTime(txDate);
        txCal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        txCal.set(java.util.Calendar.MINUTE, 0);
        txCal.set(java.util.Calendar.SECOND, 0);
        txCal.set(java.util.Calendar.MILLISECOND, 0);

        long daysDiff = (nowCal.getTimeInMillis() - txCal.getTimeInMillis()) / (24L * 60L * 60L * 1000L);

        String friendlyDate;
        if (daysDiff == 0) {
            friendlyDate = "Today";
        } else if (daysDiff == 1) {
            friendlyDate = "Yesterday";
        } else {
            SimpleDateFormat fmt;
            java.util.Calendar current = java.util.Calendar.getInstance();
            if (current.get(java.util.Calendar.YEAR) == txCal.get(java.util.Calendar.YEAR)) {
                fmt = new SimpleDateFormat("MMM d");
            } else {
                fmt = new SimpleDateFormat("MMM d, yyyy");
            }
            friendlyDate = fmt.format(txDate);
        }
        return friendlyDate;
    }
}
