package com.example.budgetapplication.data.dao

import androidx.room.*
import com.example.budgetapplication.data.entities.Category
import com.example.budgetapplication.data.entities.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDAO {

    @Insert
    suspend fun insert(category: Category): Long

    @Query("SELECT * FROM CATEGORY")
    fun getAllCategories(): Flow<List<Category>>

    @Query("""SELECT *
    FROM CATEGORY c
    JOIN BUDGET b ON c.C_BUDGET_ID = b.B_BUDGET_ID
    ORDER BY b.B_BUDGET_START DESC""")
    suspend fun getAllCategoryByReverseBudgetStart(): List<Category>

    @Query("""SELECT C_CATEGORY_NAME, C_CATEGORY_LIMIT
    FROM CATEGORY c
    JOIN BUDGET b ON c.C_BUDGET_ID = b.B_BUDGET_ID
    WHERE b.B_BUDGET_START >= :startDate AND b.B_BUDGET_END <= :endDate""")
    suspend fun getAllCategoryLimit(
        startDate: Long,
        endDate: Long
    ): List<Category>

    @Query("""
    SELECT 
        c.C_CATEGORY_NAME AS categoryName,
        c.C_CATEGORY_LIMIT AS categoryLimit,
        b.B_BUDGET_LIMIT AS budgetLimit
    FROM CATEGORY c
    JOIN BUDGET b ON c.C_BUDGET_ID = b.B_BUDGET_ID
    WHERE b.B_BUDGET_START >= :startDate 
      AND b.B_BUDGET_END <= :endDate
""")
    suspend fun getBudgetandAllCategoryLimit(
        startDate: Long,
        endDate: Long
    ): List<CategoryLimit>

    data class CategoryLimit(
        val categoryName: String,
        val categoryLimit: String,
        val budgetLimit: String
    )

    @Query("""SELECT *
    FROM ITEM i
    JOIN CATEGORY c ON i.I_CATEGORY_ID = c.C_CATEGORY_ID
    JOIN BUDGET b ON i.I_BUDGET_ID = b.B_BUDGET_ID
    WHERE b.B_BUDGET_START >= :startDate
    ORDER BY b.B_BUDGET_START DESC, c.C_CATEGORY_ID""")
    suspend fun getItemsfromBudgetOrderedByBudgetStartAndCategory(
        startDate: Long
    ): List<Item>

    @Query("""SELECT *
    FROM ITEM i
    JOIN CATEGORY c ON i.I_CATEGORY_ID = c.C_CATEGORY_ID
    JOIN BUDGET b ON i.I_BUDGET_ID = b.B_BUDGET_ID
    WHERE I_BUDGET_ID = (
            SELECT B_BUDGET_ID
            FROM BUDGET
            ORDER BY B_BUDGET_START DESC
            LIMIT 1)""")
    suspend fun getItemsfromCurrentBudgetOrderedByCategory(): List<Item>

    @Query("""SELECT C_CATEGORY_NAME, C_CATEGORY_LIMIT
    FROM CATEGORY c
    JOIN BUDGET b ON c.C_BUDGET_ID = b.B_BUDGET_ID
    WHERE b.B_BUDGET_START >= :startDate AND b.B_BUDGET_END <= :endDate""")
    suspend fun getAllCategoryByBudgetPeriod(
        startDate: Long,
        endDate: Long
    ): List<Category>

    @Query("""SELECT C_CATEGORY_NAME, C_CATEGORY_LIMIT
    FROM CATEGORY c
    JOIN ITEM i ON c.C_CATEGORY_ID = i.I_CATEGORY_ID
    WHERE i.I_RECEIPT_DATE >= :startDate AND i.I_RECEIPT_DATE <= :endDate""")
    suspend fun getCategoryByReceiptDate(
        startDate: Long,
        endDate: Long
    ): List<Category>

    @Query("""SELECT *
    FROM CATEGORY c
    JOIN BUDGET b ON c.C_BUDGET_ID = b.B_BUDGET_ID
    WHERE b.B_BUDGET_START >= :startDate AND b.B_BUDGET_END <= :endDate""")
    suspend fun getCategoriesFromSpecificBudget(
        startDate: Long,
        endDate: Long
    ): List<Category>

    @Query("""SELECT *
    FROM CATEGORY c
    JOIN BUDGET b ON c.C_BUDGET_ID = b.B_BUDGET_ID
    WHERE b.B_BUDGET_START >= :startDate AND b.B_BUDGET_END <= :endDate AND c.C_CATEGORY_NAME = :categoryName
    ORDER BY b.B_BUDGET_START DESC, c.C_CATEGORY_ID""")
    suspend fun getCategoriesFromSpecificBudgetOrdered(
        startDate: Long,
        endDate: Long,
        categoryName: String
    ): List<Category>


}
