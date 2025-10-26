package com.codeycoder.expensetracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeycoder.expensetracker.db.Transaction
import com.codeycoder.expensetracker.db.TransactionDao
import kotlinx.coroutines.launch

class HomeViewModel(val dao : TransactionDao) : ViewModel() {
    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            dao.delete(transaction)
        }
    }
}