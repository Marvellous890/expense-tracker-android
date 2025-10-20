package com.codeycoder.expensetracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeycoder.expensetracker.db.Transaction
import com.codeycoder.expensetracker.db.TransactionDao
import kotlinx.coroutines.launch

class TransactionViewModel(val dao: TransactionDao) : ViewModel() {
    fun addTransaction() {
        viewModelScope.launch {
            dao.insert(Transaction("Test", 200f, System.currentTimeMillis(), System.currentTimeMillis()))
        }
    }
}