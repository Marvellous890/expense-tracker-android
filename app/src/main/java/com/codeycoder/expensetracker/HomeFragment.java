package com.codeycoder.expensetracker;

import static com.codeycoder.expensetracker.Utilities.dp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codeycoder.expensetracker.databinding.FragmentHomeBinding;
import com.codeycoder.expensetracker.db.AppDatabase;
import com.codeycoder.expensetracker.views.Button;
import com.codeycoder.expensetracker.views.CircularIconButton;
import com.codeycoder.expensetracker.views.RoundableProgressBar;
import com.codeycoder.expensetracker.views.RoundedCard;

import java.text.NumberFormat;

public class HomeFragment extends Fragment implements ViewModelOwner {
    private FragmentHomeBinding binding;
    private Context context;
    private HomeViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = requireContext();
        viewModel = new ViewModelProvider(this, new WithDaoViewModelFactory(AppDatabase.Companion.getInstance(context).getTransactionDao())).get(HomeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();*/

//        FrameLayout rootFrame = new FrameLayout(context);

        LinearLayout root = new LinearLayout(context);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        root.setPadding(dp(8), dp(16), dp(8), dp(8));
//        rootFrame.addView(root, LayoutHelper.createFrameMatchParent());

        GradientDrawable backgroundGradient = new GradientDrawable();
        backgroundGradient.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        int[] bgColors = {Color.parseColor("#DA6736"), Color.parseColor("#00DA6736"), 0xFFFFFFFF};
        backgroundGradient.setColors(bgColors);

        root.setBackgroundColor(0xFFE8753B);

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
        mainCardParams.topMargin = dp(8);
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
        totalBalanceText.setTextColor(getResources().getColor(R.color.grayscale_600));
        mainCardLayout.addView(totalBalanceText);

        TextView totalBalance = new TextView(context);
        totalBalance.setText(getString(R.string.naira_amount, NumberFormat.getNumberInstance().format(9_298_321.66)));
        totalBalance.setTextSize(24);
        totalBalance.setTextColor(getResources().getColor(R.color.grayscale_900));
        mainCardLayout.addView(totalBalance);

        TextView thisMonthText = new TextView(context);
        thisMonthText.setText(R.string.this_month);
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

        LinearLayout secondRowContainer = new LinearLayout(context);
        root.addView(secondRowContainer, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 16, 0, 16));

        RoundedCard budgetCard = new RoundedCard(context, 16);
        secondRowContainer.addView(budgetCard, LayoutHelper.createLinear(0, 150, 1));

        LinearLayout budgetCardLayout = new LinearLayout(context);
        budgetCardLayout.setOrientation(LinearLayout.VERTICAL);
        budgetCard.addView(budgetCardLayout, LayoutHelper.createFrameMatchParent());

        TextView budgetHeader = new TextView(context);
        budgetHeader.setCompoundDrawablesWithIntrinsicBounds(
                getContext().getDrawable(R.drawable.piggy_bank),
                null,
                getContext().getDrawable(R.drawable.arrow_right_line),
                null
        );
        budgetHeader.setCompoundDrawablePadding(dp(4));
        budgetHeader.setPadding(dp(8), dp(8), dp(8), dp(8));
        budgetHeader.setText(getString(R.string.budgets));
        budgetHeader.setGravity(Gravity.BOTTOM);
        budgetCardLayout.addView(budgetHeader, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        budgetCardLayout.addView(Utilities.createDivider(context, context.getColor(R.color.grayscale_100), 1), LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, 1));

        LinearLayout budgetContainerLayout = new LinearLayout(context);
        budgetContainerLayout.setOrientation(LinearLayout.VERTICAL);
        budgetContainerLayout.setPadding(dp(8), dp(10), dp(8), dp(8));
        budgetCardLayout.addView(budgetContainerLayout, LayoutHelper.createLinearMatchParent());

        TextView budgetCountsText = new TextView(context);
        SpannableString budgetCounts = new SpannableString(getString(R.string.n_budgets, 3));
        budgetCounts.setSpan(new StyleSpan(Typeface.BOLD), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        budgetCounts.setSpan(new RelativeSizeSpan(1.5f), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        budgetCounts.setSpan(new ForegroundColorSpan(0xFF000000), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        budgetCountsText.setText(budgetCounts);
        budgetContainerLayout.addView(budgetCountsText, LayoutHelper.createLinearWrapContent());

        RoundableProgressBar progressBar = new RoundableProgressBar(context);
        budgetContainerLayout.addView(progressBar, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, 8, 0, 32, 0, 0));

        TextView percentOfTotalBudgetSpent = new TextView(context);
        percentOfTotalBudgetSpent.setText(getString(R.string.percent_of_total_budget_spent, 50));
        percentOfTotalBudgetSpent.setTextSize(12);
        budgetContainerLayout.addView(percentOfTotalBudgetSpent, LayoutHelper.createLinearWrapContent());

        RoundedCard analyticsCard = new RoundedCard(context, 16);
        secondRowContainer.addView(analyticsCard, LayoutHelper.createLinear(0, 150, 1, 8, 0, 0, 0));

        LinearLayout analyticsCardLayout = new LinearLayout(context);
        analyticsCardLayout.setOrientation(LinearLayout.VERTICAL);
        analyticsCard.addView(analyticsCardLayout, LayoutHelper.createFrameMatchParent());

        TextView analyticsHeader = new TextView(context);
        analyticsHeader.setCompoundDrawablesWithIntrinsicBounds(
                getContext().getDrawable(R.drawable.analytics),
                null,
                getContext().getDrawable(R.drawable.arrow_right_line),
                null
        );
        analyticsHeader.setCompoundDrawablePadding(dp(4));
        analyticsHeader.setPadding(dp(8), dp(8), dp(8), dp(8));
        analyticsHeader.setText(getString(R.string.analytics));
        analyticsHeader.setGravity(Gravity.BOTTOM);
        analyticsCardLayout.addView(analyticsHeader, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        analyticsCardLayout.addView(Utilities.createDivider(context, context.getColor(R.color.grayscale_100), 1), LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, 1));

        LinearLayout analyticsCardContentLayout = new LinearLayout(context);
        analyticsCardContentLayout.setOrientation(LinearLayout.VERTICAL);
        analyticsCardContentLayout.setPadding(dp(8), dp(8), dp(8), dp(8));
        analyticsCardLayout.addView(analyticsCardContentLayout, LayoutHelper.createLinearMatchParent());

        TextView thisMonthSpending = new TextView(context);
        thisMonthSpending.setText(getString(R.string.this_month_spending));
        analyticsCardContentLayout.addView(thisMonthSpending, LayoutHelper.createLinearWrapContent());

        TextView analyticsNairaAmount = new TextView(context);
        analyticsNairaAmount.setText(getString(R.string.naira_amount, NumberFormat.getNumberInstance().format(309_087)));
        analyticsNairaAmount.setTextColor(0xFF000000);
        analyticsNairaAmount.setTextSize(16);
        analyticsCardContentLayout.addView(analyticsNairaAmount, LayoutHelper.createLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 0, 8, 0, 8));

        TextView analyticsComparedText = new TextView(context);
        analyticsComparedText.setText(getString(R.string.analytics_compared_last_month));
        analyticsComparedText.setTextSize(12);
        analyticsComparedText.setCompoundDrawablesWithIntrinsicBounds(getContext().getDrawable(R.drawable.decrease_peformance), null, null, null);
        analyticsComparedText.setCompoundDrawablePadding(dp(4));
        analyticsComparedText.setTextColor(context.getColor(R.color.success_300));
        analyticsCardContentLayout.addView(analyticsComparedText, LayoutHelper.createLinearWrapContent());

        RoundedCard transactionsCard = new RoundedCard(context, 16);
        root.addView(transactionsCard, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        LinearLayout transactionsCardLayout = new LinearLayout(context);
        transactionsCardLayout.setOrientation(LinearLayout.VERTICAL);
        transactionsCardLayout.setPadding(dp(16), dp(16), dp(16), dp(16));
        transactionsCard.addView(transactionsCardLayout, LayoutHelper.createFrameMatchParent());

        LinearLayout recentTransactionsLayout = new LinearLayout(context);
        recentTransactionsLayout.setGravity(Gravity.CENTER_VERTICAL);
        transactionsCardLayout.addView(recentTransactionsLayout, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 8));

        TextView recentTransactions = new TextView(context);
        recentTransactions.setText(getString(R.string.recent_transactions));
        recentTransactions.setTextColor(0xFF000000);
        recentTransactions.setTypeface(getResources().getFont(R.font.notoserif_semibold));
        recentTransactionsLayout.addView(recentTransactions, LayoutHelper.createLinear(0, -2, 1));

        /*Button seeAllBtn = new Button(context);
        seeAllBtn.setBackground(context.getDrawable(R.drawable.bg));
        seeAllBtn.setText(getString(R.string.see_all));
        recentTransactionsLayout.addView(seeAllBtn, LayoutHelper.createLinearWrapContent());*/

        RecyclerView transactionsList = new RecyclerView(context);
        transactionsList.setLayoutManager(new LinearLayoutManager(context));
        TransactionListAdapter adapter = new TransactionListAdapter(context, this);
        transactionsList.setAdapter(adapter);
        transactionsCardLayout.addView(transactionsList, LayoutHelper.createLinearMatchParent());

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(adapter.getItemTouchHelperCallBack());
        itemTouchHelper.attachToRecyclerView(transactionsList);

//        FrameLayout bottomBar = new RoundedCard(context, dp(50));
//        bottomBar.setBackgroundColor(0xFFFF0000);
//        rootFrame.addView(bottomBar, LayoutHelper.createFrame(-1, 80, Gravity.BOTTOM, 10, 0, 10, 8));

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public ViewModel getViewModel() {
        return viewModel;
    }
}