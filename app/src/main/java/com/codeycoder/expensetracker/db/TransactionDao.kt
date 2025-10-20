package com.codeycoder.expensetracker.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: Transaction)

    @Update
    suspend fun update(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Query("SELECT * FROM transactions WHERE id = :transactionId")
    fun get(transactionId: Long): LiveData<Transaction>

    @Query("SELECT * FROM transactions ORDER BY id DESC")
    fun getAll(): LiveData<List<Transaction>>
}
