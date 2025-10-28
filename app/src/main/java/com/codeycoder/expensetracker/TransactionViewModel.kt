package com.codeycoder.expensetracker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.codeycoder.expensetracker.db.Transaction
import com.codeycoder.expensetracker.db.TransactionDao
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.TimeZone
import androidx.lifecycle.asLiveData
import com.codeycoder.expensetracker.Utilities.TAG
import kotlinx.coroutines.Dispatchers
import java.text.SimpleDateFormat
import java.util.Date

class TransactionViewModel(val dao: TransactionDao) : ViewModel() {
    private val _expenseName = MutableStateFlow("")
    val expenseName: StateFlow<String> = _expenseName.asStateFlow()

    private val _expenseAmount = MutableStateFlow("")
    val expenseAmount: StateFlow<String> = _expenseAmount.asStateFlow()

    private val _expenseDesc = MutableStateFlow("")
    val expenseDesc: StateFlow<String> = _expenseDesc.asStateFlow()

    // Expose LiveData wrappers for easy observation from Java fragments
    val expenseNameLive: LiveData<String> = _expenseName.asLiveData()
    val expenseAmountLive: LiveData<String> = _expenseAmount.asLiveData()
    val expenseDescLive: LiveData<String> = _expenseDesc.asLiveData()

    private val _insertSuccess = MutableLiveData(false)
    val insertSuccess: LiveData<Boolean> = _insertSuccess

    private var expenseDate = 0L
    private var expenseTime = 0L

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
        expenseDate = date
    }

    fun setDateIfZero(date: Long) {
        if (expenseDate == 0L) setDate(date)
    }

    fun setTimeIfZero(hour: Int, minute: Int) {
        if (expenseTime == 0L) setTime(hour, minute)
    }

    fun setTime(hour: Int, minute: Int) {
        expenseTime = 60_000L * (hour * 60L + minute)
    }

    fun addTransaction() {
        viewModelScope.launch {
            val id = dao.insert(
                Transaction(
                    _expenseName.value,
                    _expenseAmount.value.toFloatOrNull() ?: 0f,
                    computeTimeAdded(),
                    System.currentTimeMillis(),
                    _expenseDesc.value
                )
            )
            if (id > 0L) _insertSuccess.value = true
        }
    }

    private fun computeTimeAdded(): Long {
        var timeAdded = expenseDate + expenseTime
        val offset = TimeZone.getDefault().getOffset(timeAdded)
        timeAdded -= offset

        if (timeAdded <= 0L) timeAdded = System.currentTimeMillis()

        return timeAdded
    }

    fun updateTransaction(transactionId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val transaction = dao.get(transactionId).apply {
                name = _expenseName.value
                amount = _expenseAmount.value.toFloatOrNull() ?: 0f
                description = _expenseDesc.value
                timeAdded = computeTimeAdded()
            }
            dao.update(transaction)
            _insertSuccess.postValue(true)
        }
    }
}