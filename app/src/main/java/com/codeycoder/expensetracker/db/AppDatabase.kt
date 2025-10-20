package com.codeycoder.expensetracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Transaction::class],
    version = AppDatabase.LATEST_VERSION,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract val transactionDao: TransactionDao

    companion object {
        const val LATEST_VERSION = 1

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}