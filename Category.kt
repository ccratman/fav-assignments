package com.example.budgetapplication.data.databasetest.seeders

import com.example.budgetapplication.data.dao.ItemDAO
import com.example.budgetapplication.data.databasetest.DateTimeHelper
import com.example.budgetapplication.data.entities.Item
import java.time.format.DateTimeFormatter

class ItemSeeder {

    data class ItemData(
        val name: String,
        val price: String,
        val budgetId: Int?,
        val categoryId: Int?
    )

    private suspend fun insertItems(
        itemDAO: ItemDAO,
        items: List<ItemData>,
        merchantId: Int?,
        receiptId: Int?,
        dateTime: Long
    ) {
        val dateStr = DateTimeHelper.formatDate(dateTime)
        val timeStr = DateTimeHelper.formatTime(dateTime)

        items.forEach { item ->
            itemDAO.insert(
                Item(
                    budgetId_i = item.budgetId,
                    categoryId_i = item.categoryId,
                    merchantId_i = merchantId,
                    receiptId_i = receiptId,
                    itemName_s = item.name,
                    itemPrice_s = item.price,
                    receiptDate_s = dateStr,
                    receiptTime_s = timeStr,
                    itemOut_b = false
                )
            )
        }
    }

    suspend fun populateItems(itemDAO: ItemDAO) {

        val amazonId = SeederState.ids["amazonMerchant"]?.toInt()
        val walmartId = SeederState.ids["walmartMerchant"]?.toInt()
        val comedyCenterId = SeederState.ids["comedyCenterMerchant"]?.toInt()

        val amazonReceipt01 = SeederState.ids["amazonTransaction01"]?.toInt()
        val walmartReceipt01 = SeederState.ids["walmartTransaction01"]?.toInt()
        val comedyCenterReceipt01 = SeederState.ids["comedyCenterTransaction01"]?.toInt()
        val comedyCenterReceipt02 = SeederState.ids["comedyCenterTransaction02"]?.toInt()

        val foodBudget = SeederState.ids["foodBudget"]?.toInt()
        val billsBudget = SeederState.ids["billsBudget"]?.toInt()
        val entertainmentBudget = SeederState.ids["entertainmentBudget"]?.toInt()
        val unsortedBudget = SeederState.ids["unsortedBudget"]?.toInt()

        val foodCategory = SeederState.ids["food"]?.toInt()
        val billsCategory = SeederState.ids["bills"]?.toInt()
        val entertainmentCategory = SeederState.ids["entertainment"]?.toInt()
        val unsortedCategory = SeederState.ids["unsorted"]?.toInt()

        // Receipt 1 - Walmart
        val receipt1 = listOf(
            ItemData("Apples", "5.0", foodBudget, foodCategory),
            ItemData("Bread", "3.0", foodBudget, foodCategory),
            ItemData("Chicken", "10.0", foodBudget, foodCategory)
        )
        insertItems(itemDAO, receipt1, walmartId, walmartReceipt01, DateTimeHelper.create(11,2,2025,14,32,0).time)

        // Receipt 2 - Amazon
        val receipt2 = listOf(
            ItemData("Headphones", "92.0", entertainmentBudget, entertainmentCategory),
            ItemData("Batteries", "8.0", unsortedBudget, unsortedCategory)
        )
        insertItems(itemDAO, receipt2, amazonId, amazonReceipt01, DateTimeHelper.create(11,8,2025,19,11,0).time)

        // Receipt 3 - Comedy Center
        val receipt3 = listOf(
            ItemData("Orangutans", "25.05", entertainmentBudget, entertainmentCategory),
            ItemData("Breakfast Cereals", "25.05", foodBudget, foodCategory),
            ItemData("Bad Jokes", "50.15", entertainmentBudget, entertainmentCategory),
            ItemData("Banana Subscription Box", "15.99", billsBudget, billsCategory),
            ItemData("Mismatched Socks", "8.50", unsortedBudget, unsortedCategory),
            ItemData("Inflatable Keyboard", "32.00", entertainmentBudget, entertainmentCategory),
            ItemData("Left-Handed Coffee Mug", "12.25", unsortedBudget, unsortedCategory),
            ItemData("Philosophical Pancakes", "9.75", foodBudget, foodCategory),
            ItemData("Surprise Box of Regrets", "49.99", billsBudget, billsCategory)
        )
        insertItems(itemDAO, receipt3, comedyCenterId, comedyCenterReceipt01, DateTimeHelper.create(11,15,2025,21,0,0).time)

        // Receipt 4 - Comedy Center
        val receipt4 = listOf(
            ItemData("Used Time Machine (No Refunds)", "299.95", entertainmentBudget, entertainmentCategory),
            ItemData("Invisible Umbrella", "18.40", unsortedBudget, unsortedCategory),
            ItemData("Premium Dad Jokes Expansion Pack", "22.00", entertainmentBudget, entertainmentCategory),
            ItemData("Emergency Disco Ball", "45.25", entertainmentBudget, entertainmentCategory),
            ItemData("Pet Rock Deluxe Edition", "10.00", entertainmentBudget, entertainmentCategory),
            ItemData("Certified Unicorn Dust", "125.75", unsortedBudget, unsortedCategory),
            ItemData("Quantum Toaster", "85.50", unsortedBudget, unsortedCategory),
            ItemData("Suspiciously Cheap Jetpack", "199.99", unsortedBudget, unsortedCategory),
            ItemData("Interdimensional Sunglasses", "67.89", unsortedBudget, unsortedCategory)
        )
        insertItems(itemDAO, receipt4, comedyCenterId, comedyCenterReceipt02, DateTimeHelper.create(12,7,2025,21,0,0).time)
    }
}
