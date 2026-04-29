package com.example.budgetapplication.data.dao

import androidx.room.*
import com.example.budgetapplication.data.entities.Budget
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDAO {

    @Insert
    suspend fun insert(budget: Budget): Long

    @Query("SELECT * FROM BUDGET")
   fun getAllBudgets(): Flow<List<Budget>>

    @Query("""SELECT *
    FROM BUDGET
    WHERE B_BUDGET_START >= :startDate AND B_BUDGET_END <= :endDate""")
    suspend fun getSpecificBudget(
        startDate: Long,
        endDate: Long
    ): List<Budget>

    @Query("""SELECT B_BUDGET_START, B_BUDGET_END FROM BUDGET ORDER BY B_BUDGET_START DESC""")
    suspend fun getAllBudgetDatesByReverseDate(): List<Budget>

    @Query("""SELECT *
    FROM BUDGET
    WHERE B_BUDGET_START >= :startDate AND B_BUDGET_END <= :endDate
    ORDER BY B_BUDGET_START DESC""")
    suspend fun getAllBudgetByReverseDate(
        startDate: Long,
        endDate: Long
    ): List<Budget>

    @Query("""SELECT B_BUDGET_LIMIT FROM BUDGET ORDER BY B_BUDGET_START DESC""")
    suspend fun getBudgetLimitByReverseDate(): List<Budget>

    @Query("""SELECT B_BUDGET_LIMIT
    FROM BUDGET
    WHERE B_BUDGET_START >= :startDate AND B_BUDGET_END <= :endDate""")
    suspend fun getSpecificBudgetLimit(
        startDate: Long,
        endDate: Long
    ): List<Budget>


}
