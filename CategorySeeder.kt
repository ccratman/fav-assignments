package com.example.budgetapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.budgetapplication.data.dao.*
import com.example.budgetapplication.data.entities.*

@Database(
    entities = [
        Budget::class,
        Category::class,
        Receipt::class,
        Merchant::class,
        Item::class,
        User::class,
        Staging::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun budgetDAO(): BudgetDAO
    abstract fun categoryDAO(): CategoryDAO
    abstract fun receiptDAO(): ReceiptDAO
    abstract fun merchantDAO(): MerchantDAO
    abstract fun itemDAO(): ItemDAO
    abstract fun userDAO(): UserDAO

    abstract fun stagingDAO(): StagingDAO


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    //This is needed  to change where the data base is accessing
                    "test-db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

