package com.example.budgetapplication.data.databasetest.seeders

import com.example.budgetapplication.data.entities.Category
import com.example.budgetapplication.data.dao.CategoryDAO

class CategorySeeder {

    suspend fun populateCategories(categoryDAO: CategoryDAO) {

        val foodBudget = SeederState.ids["foodBudget"]!!.toInt()
        val billsBudget = SeederState.ids["billsBudget"]!!.toInt()
        val entertainmentBudget = SeederState.ids["entertainmentBudget"]!!.toInt()
        val unsortedBudget = SeederState.ids["unsortedBudget"]!!.toInt()


        val unsortedCategoryId = categoryDAO.insert(
            Category(
                budgetId_i = unsortedBudget,
                categoryName_s = "Unsorted",
                categoryLimit_s = "600.0",
                categoryOut_b = false
            )
        )
        val foodCategoryId = categoryDAO.insert(
            Category(
                budgetId_i = foodBudget,
                categoryName_s = "Food",
                categoryLimit_s = "300.0",
                categoryOut_b = false
            )
        )

        val billsCategoryId = categoryDAO.insert(
            Category(
                budgetId_i = billsBudget,
                categoryName_s = "Bills",
                categoryLimit_s = "1200.0",
                categoryOut_b = false
            )
        )

        val entertainmentCategoryId = categoryDAO.insert(
            Category(
                budgetId_i = entertainmentBudget,
                categoryName_s = "Entertainment",
                categoryLimit_s = "250.0",
                categoryOut_b = false
            )
        )

        SeederState.ids["unsorted"] = unsortedCategoryId
        SeederState.ids["food"] = foodCategoryId
        SeederState.ids["bills"] = billsCategoryId
        SeederState.ids["entertainment"] = entertainmentCategoryId


    }

}
