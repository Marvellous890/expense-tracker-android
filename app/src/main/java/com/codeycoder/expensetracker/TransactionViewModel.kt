package com.codeycoder.expensetracker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeycoder.expensetracker.Utilities.TAG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.codeycoder.expensetracker.db.Transaction
import com.codeycoder.expensetracker.db.TransactionDao
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class TransactionViewModel(val dao: TransactionDao) : ViewModel() {
    private val _expenseName = MutableStateFlow("")
    val expenseName: StateFlow<String> = _expenseName.asStateFlow()

    private val _expenseAmount = MutableStateFlow("")
    val expenseAmount: StateFlow<String> = _expenseAmount

    private val _expenseDesc = MutableStateFlow("")
    val expenseDesc: StateFlow<String> = _expenseDesc

    private val _insertSuccess = MutableLiveData(false)
    val insertSuccess: LiveData<Boolean> = _insertSuccess

    private val expenseDate = MutableLiveData(0L)
    private val expenseTime = MutableLiveData(0L)

    fun setExpenseName(name: String) {
        _expenseName.value = name
    }

    fun setExpenseAmount(amount: String) {
        _expenseAmount.value = amount
    }

    fun setExpenseDesc(desc: String) {
        _expenseDesc.value = desc
    }

    fun setDate(date: Long) {
        expenseDate.value = date
    }

    fun setTime(hour: Int, minute: Int) {
        expenseTime.value = 60_000L * (hour * 60L + minute)
    }

    fun addTransaction() {
        viewModelScope.launch {
            var timeAdded = (expenseDate.value ?: 0L) + (expenseTime.value ?: 0L)
            val offset = TimeZone.getDefault().getOffset(timeAdded)
            timeAdded -= offset

            if (timeAdded <= 0L) timeAdded = System.currentTimeMillis()

            val id = dao.insert(
                Transaction(
                    _expenseName.value,
                    _expenseAmount.value.toFloatOrNull() ?: 0f,
                    timeAdded,
                    System.currentTimeMillis(),
                    _expenseDesc.value
                )
            )

            if (id > 0L) _insertSuccess.value = true
            Log.d(TAG,
                SimpleDateFormat.getInstance()
                    .format(Date(timeAdded))
            )
        }
    }
}