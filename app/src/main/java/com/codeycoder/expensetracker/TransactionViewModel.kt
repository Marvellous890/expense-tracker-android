package com.codeycoder.expensetracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
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
            val timeAdded = computeTimeAdded()

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
        }
    }

    fun computeTimeAdded(): Long {
        var timeAdded = (expenseDate.value ?: 0L) + (expenseTime.value ?: 0L)
        val offset = TimeZone.getDefault().getOffset(timeAdded)
        timeAdded -= offset

        if (timeAdded <= 0L) timeAdded = System.currentTimeMillis()

        return timeAdded
    }

    fun updateTransaction(transactionId: Long) {
        val transLiveData = dao.get(transactionId)

        val observer = object : Observer<Transaction> {
            override fun onChanged(value: Transaction) {
                value.name = _expenseName.value
                value.amount = _expenseAmount.value.toFloatOrNull() ?: 0f
                value.description = _expenseDesc.value
                value.timeAdded = computeTimeAdded()
                viewModelScope.launch {
                    dao.update(value)
                    _insertSuccess.value = true
                    transLiveData.removeObserver{ this }
                }
            }

        }
        transLiveData.observeForever(observer)
    }
}