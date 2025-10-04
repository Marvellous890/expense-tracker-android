package com.codeycoder.expensetracker;

import static com.codeycoder.expensetracker.Utilities.dp;
import static com.codeycoder.expensetracker.Utilities.TAG;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.lifecycle.ViewModelProvider;

import com.codeycoder.expensetracker.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private Context context;
    private HomeViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        context = requireContext();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();*/

        LinearLayout root = new LinearLayout(context);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        root.setPadding(16, 64, 32, 16);

        GradientDrawable backgroundGradient = new GradientDrawable();
        backgroundGradient.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        int[] bgColors = {Color.parseColor("#DA6736"), Color.parseColor("#00DA6736"), 0xFFFFFFFF};
        backgroundGradient.setColors(bgColors);

        root.setBackground(backgroundGradient);

        LinearLayout appBar = new LinearLayout(context);
        root.addView(appBar);

        ImageView logo = new ImageView(context);
        logo.setImageResource(R.drawable.logo);
        logo.setScaleType(ImageView.ScaleType.FIT_START);
        appBar.addView(logo, new LinearLayout.LayoutParams(dp(200), dp(50), 1));


        LinearLayout appBarBtnGroups = new LinearLayout(context);
        appBarBtnGroups.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams appBarBtnGroupsParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        appBar.addView(appBarBtnGroups, appBarBtnGroupsParams);

        FrameLayout giftButton = new CircularIconButton(context);
        ImageView giftIcon = new ImageView(context);
        giftIcon.setImageResource(R.drawable.gift);
        giftButton.setPadding(10, 10, 10, 10);
        giftButton.addView(giftIcon, new FrameLayout.LayoutParams(dp(24), dp(24), Gravity.CENTER));

        FrameLayout notifButton = new CircularIconButton(context);
        ImageView notificationIcon = new ImageView(context);
        notificationIcon.setImageResource(R.drawable.notification_bell);
        notifButton.setPadding(10, 10, 10, 10);
        notifButton.addView(notificationIcon, new FrameLayout.LayoutParams(dp(24), dp(24), Gravity.CENTER));

        appBarBtnGroups.addView(giftButton);
        LinearLayout.LayoutParams notifBtnParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        notifBtnParams.leftMargin = dp(4);
        appBarBtnGroups.addView(notifButton, notifBtnParams);

        RoundedCard mainCard = new RoundedCard(context, dp(16));
        LinearLayout.LayoutParams mainCardParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, dp(240));
        mainCardParams.topMargin = dp(16);
        mainCard.setBackgroundColor(0xFFFFFFFF);

        root.addView(mainCard, mainCardParams);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

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

    public class RoundedCard extends FrameLayout {
        int radius;

        public RoundedCard(Context context, int radius) {
            super(context);
            this.radius = radius;
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            Path clipPath = new Path();
            clipPath.addRoundRect(new RectF(0, 0, getWidth(), getHeight()), radius, radius, Path.Direction.CW);
            canvas.clipPath(clipPath);
            super.draw(canvas);
        }
    }
}