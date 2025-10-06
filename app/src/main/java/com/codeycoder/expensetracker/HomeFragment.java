package com.codeycoder.expensetracker;

import static com.codeycoder.expensetracker.Utilities.dp;

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

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.codeycoder.expensetracker.databinding.FragmentHomeBinding;

import java.text.NumberFormat;

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
        logo.setImageResource(R.drawable.text_logo);
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
        LinearLayout.LayoutParams mainCardParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, dp(210));
        mainCardParams.topMargin = dp(16);
        root.addView(mainCard, mainCardParams);

        ImageView noiseBg = new ImageView(context);
        noiseBg.setImageResource(R.drawable.noise);
        noiseBg.setAlpha(0.6f);
        noiseBg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mainCard.addView(noiseBg, LayoutHelper.createFrameMatchParent());

        ImageView ambientGradientBg = new ImageView(context);
        ambientGradientBg.setImageResource(R.drawable.ambient_gradient);
        ambientGradientBg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mainCard.addView(ambientGradientBg, LayoutHelper.createFrameMatchParent());

        LinearLayout mainCardLayout = new LinearLayout(context);
        mainCardLayout.setOrientation(LinearLayout.VERTICAL);
        mainCardLayout.setPadding(dp(16), dp(16), dp(16), dp(16));
        mainCard.addView(mainCardLayout, LayoutHelper.createFrameMatchParent());

        TextView totalBalanceText = new TextView(context);
        totalBalanceText.setText(R.string.total_balance);
        totalBalanceText.setTextSize(14);
        totalBalanceText.setTextColor(getResources().getColor(R.color.grayscale_600));
        mainCardLayout.addView(totalBalanceText);

        TextView totalBalance = new TextView(context);
        totalBalance.setText(getString(R.string.naira_amount, NumberFormat.getNumberInstance().format(9_298_321.66)));
        totalBalance.setTextSize(24);
        totalBalance.setTextColor(getResources().getColor(R.color.grayscale_900));
        mainCardLayout.addView(totalBalance);

        TextView thisMonthText = new TextView(context);
        thisMonthText.setText(R.string.this_month);
        thisMonthText.setTextSize(14);
        thisMonthText.setTextColor(getResources().getColor(R.color.grayscale_500));
        thisMonthText.setTypeface(getResources().getFont(R.font.notoserif_medium));
        mainCardLayout.addView(thisMonthText, LayoutHelper.createLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 0, 16, 0, 8));

        LinearLayout ieLayout = new LinearLayout(context);
        mainCardLayout.addView(ieLayout, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 1));


        LinearLayout incomeLayout = new LinearLayout(context);
        incomeLayout.setOrientation(LinearLayout.VERTICAL);
        ieLayout.addView(incomeLayout, LayoutHelper.createLinear(0, LayoutHelper.WRAP_CONTENT, 1));

        TextView income = new TextView(context);
        income.setText(R.string.income);
        income.setTextSize(12);
        income.setTextColor(getResources().getColor(R.color.grayscale_600));
        incomeLayout.addView(income, LayoutHelper.createLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 4));

        LinearLayout incomeBalanceLayout = new LinearLayout(context);
        incomeBalanceLayout.setOrientation(LinearLayout.HORIZONTAL);
        incomeLayout.addView(incomeBalanceLayout);

        TextView incomeBalance = new TextView(context);
        incomeBalance.setText(getString(R.string.naira_amount, NumberFormat.getNumberInstance().format(0.00)));
        incomeBalance.setTextSize(12);
        incomeBalance.setTextColor(0xFF000000);
        incomeBalance.setTypeface(getResources().getFont(R.font.notoserif_medium));
        incomeBalanceLayout.addView(incomeBalance);

        TextView incomeBalancePercent = new TextView(context);
        incomeBalancePercent.setText(getString(R.string.balance_percent, 0f));
        incomeBalancePercent.setTextSize(12);
        incomeBalancePercent.setTextColor(getResources().getColor(R.color.success_300));
        incomeBalancePercent.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_up_line, 0, 0, 0);
        incomeBalanceLayout.addView(incomeBalancePercent, LayoutHelper.createLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 4, 0, 0, 0));

        TextView comparedToText = new TextView(context);
        comparedToText.setText(getString(R.string.comparedTo, 0.0));
        comparedToText.setTextSize(12);
        comparedToText.setTextColor(getResources().getColor(R.color.grayscale_600));
        incomeLayout.addView(comparedToText);

        LinearLayout expenseLayout = new LinearLayout(context);
        expenseLayout.setOrientation(LinearLayout.VERTICAL);
        ieLayout.addView(expenseLayout, LayoutHelper.createLinear(0, LayoutHelper.WRAP_CONTENT, 1));

        TextView expense = new TextView(context);
        expense.setText(R.string.expense);
        expense.setTextSize(12);
        expense.setTextColor(getResources().getColor(R.color.grayscale_600));
        expenseLayout.addView(expense, LayoutHelper.createLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 4));

        LinearLayout expenseBalanceLayout = new LinearLayout(context);
        expenseBalanceLayout.setOrientation(LinearLayout.HORIZONTAL);
        expenseLayout.addView(expenseBalanceLayout);

        TextView expenseBalance = new TextView(context);
        expenseBalance.setText(getString(R.string.naira_amount, NumberFormat.getNumberInstance().format(181_990.00)));
        expenseBalance.setTextSize(12);
        expenseBalance.setTextColor(0xFF000000);
        expenseBalance.setTypeface(getResources().getFont(R.font.notoserif_medium));
        expenseBalanceLayout.addView(expenseBalance);

        TextView expenseBalancePercent = new TextView(context);
        expenseBalancePercent.setText(getString(R.string.balance_percent, -3.03));
        expenseBalancePercent.setTextSize(12);
        expenseBalancePercent.setTextColor(getResources().getColor(R.color.success_300));
        expenseBalancePercent.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_down_line, 0, 0, 0);
        expenseBalanceLayout.addView(expenseBalancePercent, LayoutHelper.createLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 4, 0, 0, 0));

        TextView comparedToTextExpense = new TextView(context);
        comparedToTextExpense.setText(getString(R.string.comparedTo, 187_685f));
        comparedToTextExpense.setTextSize(12);
        comparedToTextExpense.setTextColor(getResources().getColor(R.color.grayscale_600));
        expenseLayout.addView(comparedToTextExpense);



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
}