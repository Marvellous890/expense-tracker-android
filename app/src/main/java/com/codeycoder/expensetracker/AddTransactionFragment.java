package com.codeycoder.expensetracker;


import static com.codeycoder.expensetracker.Utilities.TAG;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.codeycoder.expensetracker.databinding.FragmentAddTransactionBinding;
import com.codeycoder.expensetracker.db.AppDatabase;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AddTransactionFragment extends Fragment {
    private Context context;
    private TransactionViewModel viewModel;
    private FragmentAddTransactionBinding binding;
    private MaterialDatePicker<Long> datePicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = requireContext();
        viewModel = new ViewModelProvider(this, new TransactionViewModelFactory(AppDatabase.Companion.getInstance(context).getTransactionDao())).get(TransactionViewModel.class);
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateBack();
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddTransactionBinding.inflate(inflater, container, false);
        return binding.getRoot();
        // return inflater.inflate(R.layout.fragment_add_transaction, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, Bundle saveInstanceState) {
        binding.selectDate.setOnClickListener(v -> {
            datePicker = (MaterialDatePicker<Long>) getParentFragmentManager().findFragmentByTag("date");
            if (datePicker == null || !datePicker.isAdded()) {
                CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder()
                        // .setStart(MaterialDatePicker.thisMonthInUtcMilliseconds())
                        .setStart(MaterialDatePicker.todayInUtcMilliseconds() - (60L * 24 * 60 * 60 * 1000)) // 60 days before
                        .setEnd(MaterialDatePicker.todayInUtcMilliseconds());

                datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText(R.string.select_date)
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
//                        .setCalendarConstraints(constraintsBuilder.build())
                        .build();
            }
            datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                @Override
                public void onPositiveButtonClick(Long selection) {
                    viewModel.setDate(selection);
                }
            });
            datePicker.show(getParentFragmentManager(), "date");
        });

        Calendar calendar = Calendar.getInstance();

        binding.selectDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        binding.selectTime.setText(new SimpleDateFormat("hh:mm a").format(calendar.getTime()));

        binding.selectTime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    viewModel.setTime(hourOfDay, minute);
                }
            }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false);
            timePickerDialog.show();
        });

        binding.headerText.setOnTouchListener((tv, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) return true;

            TextView textView = (TextView) tv;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                Drawable drawableStart = textView.getCompoundDrawables()[0];
                if (drawableStart != null) {
                    RectF drawableStartRectF = new RectF(tv.getPaddingLeft(), tv.getPaddingTop(), tv.getPaddingLeft() + drawableStart.getBounds().width(), tv.getHeight() - tv.getPaddingBottom());
                    if (drawableStartRectF.contains(event.getX(), event.getY())) {
                        navigateBack();
                    }
                }
            }

            return false;
        });

        viewModel.getInsertSuccess().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean newValue) {
                if (newValue) {
                    Toast.makeText(context, "Transaction added successfully", Toast.LENGTH_SHORT).show();
                    navigateBack();
                }
            }
        });

        binding.addTransBtn.setOnClickListener(v -> {
            viewModel.addTransaction();
        });

        // Wire up EditTexts to ViewModel
        binding.expenseName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setExpenseName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.expenseAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setExpenseAmount(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.expenseDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setExpenseDesc(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void navigateBack() {
        Fragment navHostFragment = getParentFragmentManager().getPrimaryNavigationFragment();
        if (Objects.equals(navHostFragment.getArguments().getString("tag"), "add_transaction_f")) {
            View navBar = ((MainActivity) requireActivity()).getBinding().navBar;
            navBar.setVisibility(View.VISIBLE);
            ObjectAnimator.ofFloat(navBar, "alpha", 0, 1).setDuration(250).start();
            NavHostFragment.findNavController(AddTransactionFragment.this).popBackStack();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}