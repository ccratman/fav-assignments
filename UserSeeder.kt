package com.example.budgetapplication.data.databasetest.seeders

import com.example.budgetapplication.data.dao.BudgetDAO
import com.example.budgetapplication.data.databasetest.DateTimeHelper
import com.example.budgetapplication.data.entities.Budget

class BudgetSeeder {

    suspend fun populateBudgets(budgetDAO: BudgetDAO) {

        val budgetZero = budgetDAO.insert(
            Budget(
                budgetLimit_s = "300.0",
                budgetStart_l = DateTimeHelper.create(11, 1, 2025, 0, 0, 0).time,
                budgetEnd_l = DateTimeHelper.create(11, 30, 2025, 11, 59, 59).time,
                budgetOut_b = false
            )
        )

        val budgetOne = budgetDAO.insert(
            Budget(
                budgetLimit_s = "1200.0",
                budgetStart_l = DateTimeHelper.create(11, 1, 2025, 0, 0, 0).time,
                budgetEnd_l = DateTimeHelper.create(11, 30, 2025, 11, 59, 59).time,
                budgetOut_b = false
            )
        )

        val budgetTwo = budgetDAO.insert(
            Budget(
                budgetLimit_s = "250.0",
                budgetStart_l = DateTimeHelper.create(11, 1, 2025, 0, 0, 0).time,
                budgetEnd_l = DateTimeHelper.create(11, 30, 2025, 11, 59, 59).time,
                budgetOut_b = false
            )
        )

        val budgetThree = budgetDAO.insert(
            Budget(
                budgetLimit_s = "600.0",
                budgetStart_l = DateTimeHelper.create(11, 1, 2025, 0, 0, 0).time,
                budgetEnd_l = DateTimeHelper.create(11, 30, 2025, 11, 59, 59).time,
                budgetOut_b = false
            )
        )

        SeederState.ids["foodBudget"] = budgetZero
        SeederState.ids["billsBudget"] = budgetOne
        SeederState.ids["entertainmentBudget"] = budgetTwo
        SeederState.ids["unsortedBudget"] = budgetThree
    }
}
