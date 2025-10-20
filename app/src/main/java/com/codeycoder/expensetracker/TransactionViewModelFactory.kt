package com.codeycoder.expensetracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codeycoder.expensetracker.db.TransactionDao

class TransactionViewModelFactory constructor(private val dao: TransactionDao) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            return TransactionViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}