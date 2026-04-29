package com.example.budgetapplication.data.dao

import androidx.room.*
import com.example.budgetapplication.data.entities.Merchant

@Dao
interface MerchantDAO {


    @Insert
    suspend fun insert(merchant: Merchant): Long

    @Query("SELECT * FROM MERCHANT WHERE M_MERCHANT_NAME = :name LIMIT 1")
    suspend fun getMerchantByName(name: String): Merchant?
    //FOR VIEW ALL RECEIPTS SCREEN
    // Added to LazyRepo
    @Query("SELECT * FROM MERCHANT WHERE M_MERCHANT_ID = :id")
    suspend fun getMerchantById(id: Int?): Merchant

    @Query("""SELECT *
    FROM MERCHANT m
    JOIN RECEIPT r ON m.M_MERCHANT_ID = r.R_MERCHANT_ID
    JOIN ITEM i ON i.I_RECEIPT_ID = r.R_RECEIPT_ID
    JOIN CATEGORY c ON i.I_CATEGORY_ID = c.C_CATEGORY_ID
    JOIN BUDGET b ON i.I_BUDGET_ID = b.B_BUDGET_ID
    WHERE b.B_BUDGET_START >= :startDate AND B_BUDGET_END <= :endDate
    ORDER BY b.B_BUDGET_START DESC, c.C_CATEGORY_ID""")
    suspend fun getMerchantsfromSpecificBudgetOrderedByBudgetStartAndCategory(
        startDate: Long,
        endDate: Long
    ): List<Merchant>
}
