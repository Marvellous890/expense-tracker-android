package com.codeycoder.expensetracker.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    var name: String,

    var amount: Float,

    @ColumnInfo(name = "time_added")
    var timeAdded: Long,

    @ColumnInfo(name = "transaction_time")
    var transactionTime: Long,

    var type: String = "expense",

    @ColumnInfo(defaultValue = "NULL")
    var description: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "budget_id", defaultValue = "NULL")
    var budgetId: Long? = null
}