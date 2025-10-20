package com.codeycoder.expensetracker;


import static com.codeycoder.expensetracker.Utilities.TAG;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.codeycoder.expensetracker.databinding.FragmentAddTransactionBinding;
import com.codeycoder.expensetracker.db.AppDatabase;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.Calendar;

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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddTransactionBinding.inflate(inflater, container, false);
        return binding.getRoot();
        // return inflater.inflate(R.layout.fragment_add_transaction, container, false);
    }

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
                    Log.d(TAG, "onPositiveButtonClick: " + selection + ", current time: " + System.currentTimeMillis());
                }
            });
            datePicker.show(getParentFragmentManager(), "date");
        });

        Calendar calendar = Calendar.getInstance();

        binding.selectTime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Log.d(TAG, "onTimeSet: " + hourOfDay + ", minute: " + minute);
                }
            }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false);
//            timePickerDialog.getTimePicker();
            timePickerDialog.show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
    }
}