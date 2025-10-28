package com.codeycoder.expensetracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeycoder.expensetracker.db.Transaction
import com.codeycoder.expensetracker.db.TransactionDao
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {
    public var navigating = false
}