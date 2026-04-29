package com.example.budgetapplication.data.dao

import androidx.room.*
import com.example.budgetapplication.data.entities.Item
import com.example.budgetapplication.data.entities.Merchant
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDAO {

    @Insert
    suspend fun insert(item: Item): Long


    @Query("""
        SELECT *
        FROM ITEM
        WHERE I_CATEGORY_ID = :categoryId
    """)
    fun getItemsByCategory(categoryId: Int?): Flow<List<Item>>
    @Query("""
    SELECT SUM(CAST(I_ITEM_PRICE AS REAL))
    FROM ITEM
    WHERE I_CATEGORY_ID = :categoryId
""")
    suspend fun getCategoryTotal(categoryId: Int?): Double?


    @Query("SELECT * FROM ITEM ORDER BY I_RECEIPT_DATE DESC, I_RECEIPT_TIME DESC")
    fun getAllItems(): Flow<List<Item>>

    // Mark changed what is returned To flow<> 11/23
    // Added to LazyRepo
    @Query("SELECT * FROM ITEM WHERE I_RECEIPT_ID = :receiptId")
    fun getItemsForReceipt(receiptId: Int?): Flow<List<Item>>


    @Query("SELECT * FROM ITEM ORDER BY I_RECEIPT_DATE DESC, I_RECEIPT_TIME DESC")
    suspend fun getAllItemsOrderedByReverseDate(): List<Item>

    //For checksum Toast
    @Query("""SELECT ROUND(SUM(CAST(I_ITEM_PRICE AS REAL)), 2)FROM ITEM""")
    suspend fun sumAllItemPrices(): Double?

    @Query("""SELECT *
    FROM ITEM i
    JOIN CATEGORY c ON i.I_CATEGORY_ID = c.C_CATEGORY_ID
    JOIN BUDGET b ON i.I_BUDGET_ID = b.B_BUDGET_ID
    WHERE b.B_BUDGET_START >= :startDate AND B_BUDGET_END <= :endDate
    ORDER BY b.B_BUDGET_START DESC, c.C_CATEGORY_ID""")
    suspend fun getItemsfromSpecificBudgetOrderedByBudgetStartAndCategory(
        startDate: Long,
        endDate: Long
    ): List<Item>

    @Query("""SELECT *
    FROM ITEM i
    JOIN CATEGORY c ON i.I_CATEGORY_ID = c.C_CATEGORY_ID
    JOIN BUDGET b ON i.I_BUDGET_ID = b.B_BUDGET_ID
    WHERE b.B_BUDGET_START >= :startDate AND B_BUDGET_END <= :endDate AND c.C_CATEGORY_NAME = :categoryName
    ORDER BY b.B_BUDGET_START DESC, c.C_CATEGORY_ID""")
    suspend fun getItemsfromSpecificBudgetandCategoryOrderedByBoth(
        startDate: Long,
        endDate: Long,
        categoryName: String
    ): List<Item>

    @Query("""SELECT *
    FROM MERCHANT m
    JOIN RECEIPT r ON m.M_MERCHANT_ID = r.R_MERCHANT_ID
    JOIN ITEM i ON i.I_RECEIPT_ID = r.R_RECEIPT_ID
    JOIN CATEGORY c ON i.I_CATEGORY_ID = c.C_CATEGORY_ID
    JOIN BUDGET b ON i.I_BUDGET_ID = b.B_BUDGET_ID
    WHERE b.B_BUDGET_START >= :startDate AND B_BUDGET_END <= :endDate AND c.C_CATEGORY_NAME = :categoryName
    ORDER BY b.B_BUDGET_START DESC, c.C_CATEGORY_ID""")
    suspend fun getMerchantsfromSpecificBudgetandCategoryOrderedByBoth(
        startDate: Long,
        endDate: Long,
        categoryName: String
    ): List<Merchant>


}
