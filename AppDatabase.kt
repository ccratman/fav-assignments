package com.example.budgetapplication.data.dao

import androidx.room.*
import com.example.budgetapplication.data.entities.Item
import com.example.budgetapplication.data.entities.Receipt
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceiptDAO {


    @Query("""
        SELECT * FROM RECEIPT
        WHERE R_MERCHANT_ID = :merchantId
        ORDER BY R_RECEIPT_DATETIME_INPUT ASC
        LIMIT 1
    """)
    suspend fun getMostRecentReceiptForMerchant(merchantId: Int?): Receipt?

    @Insert
    suspend fun insert(receipt: Receipt): Long

    // Mark changed what is returned To flow<> 11/23
    // Added to LazyRepo
    @Query("SELECT * FROM RECEIPT ORDER BY R_RECEIPT_DATE DESC, R_RECEIPT_TIME DESC")
    fun getAllReceiptsOrderedByReverseDate(): Flow<List<Receipt>>

    @Query("SELECT * FROM ITEM WHERE I_RECEIPT_ID = :receiptId")
    suspend fun getItemsFromSpecificReceipt(receiptId: Int): List<Item>

    @Query("SELECT * FROM ITEM ORDER BY I_RECEIPT_DATE DESC, I_RECEIPT_TIME DESC")
    suspend fun getItemsFromReceiptOrderedByReverseReceiptId(): List<Item>

    @Query("""SELECT *
    FROM RECEIPT r
    JOIN ITEM i ON i.I_RECEIPT_ID = r.R_RECEIPT_ID
    JOIN CATEGORY c ON i.I_CATEGORY_ID = c.C_CATEGORY_ID
    JOIN BUDGET b ON i.I_BUDGET_ID = b.B_BUDGET_ID
    WHERE b.B_BUDGET_START >= :startDate AND B_BUDGET_END <= :endDate
    ORDER BY b.B_BUDGET_START DESC, c.C_CATEGORY_ID""")
    suspend fun getReceiptsfromSpecificBudgetOrderedByBudgetStartAndCategory(
        startDate: Long,
        endDate: Long
    ): List<Receipt>

    @Query("""SELECT *
    FROM RECEIPT r
    JOIN ITEM i ON i.I_RECEIPT_ID = r.R_RECEIPT_ID
    JOIN CATEGORY c ON i.I_CATEGORY_ID = c.C_CATEGORY_ID
    JOIN BUDGET b ON i.I_BUDGET_ID = b.B_BUDGET_ID
    WHERE b.B_BUDGET_START >= :startDate AND B_BUDGET_END <= :endDate AND c.C_CATEGORY_NAME = :categoryName
    ORDER BY b.B_BUDGET_START DESC, c.C_CATEGORY_ID""")
    suspend fun getReceiptsfromSpecificBudgetandCategoryOrderedByBoth(
        startDate: Long,
        endDate: Long,
        categoryName: String
    ): List<Receipt>

}
