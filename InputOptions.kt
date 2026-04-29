package com.example.budgetapplication.data.repository

import com.example.budgetapplication.data.dao.BudgetDAO
import com.example.budgetapplication.data.dao.CategoryDAO
import com.example.budgetapplication.data.dao.ItemDAO
import com.example.budgetapplication.data.dao.MerchantDAO
import com.example.budgetapplication.data.dao.ReceiptDAO
import com.example.budgetapplication.data.entities.Budget
import com.example.budgetapplication.data.entities.Category
import com.example.budgetapplication.data.entities.Item
import com.example.budgetapplication.data.entities.Receipt
import kotlinx.coroutines.flow.Flow

class LazyRepository(
    private val receiptDAO: ReceiptDAO,
    private val merchantDAO: MerchantDAO,
    private val itemDAO: ItemDAO,
    private val categoryDAO: CategoryDAO,
    private val budgetDAO: BudgetDAO,
) {


    val AllCategories: Flow<List<Category>> = categoryDAO.getAllCategories()

    fun getItemsByCategory(categoryId: Int?): Flow<List<Item>> {
        return itemDAO.getItemsByCategory(categoryId)
    }
    //Select All Receipt
    val AllReceipts: Flow<List<Receipt>> = receiptDAO.getAllReceiptsOrderedByReverseDate()
//    val AllReceiptItems: Flow<List<Item>> = itemDAO.getItemsForReceipt(receiptId)

    val AllLineItems: Flow<List<Item>> = itemDAO.getAllItems()

    suspend fun insert(receipt: Receipt) = receiptDAO.insert(receipt)
    suspend fun getMerchantById(id: Int?) = merchantDAO.getMerchantById(id)

    suspend fun createNewBudget(budget: Budget) = budgetDAO.insert(budget)
    suspend fun createNewCategory(category: Category) = categoryDAO.insert(category)
    suspend fun getCurrentSpending(id: Int?) = itemDAO.getCategoryTotal(id)

    fun getItemsForReceipt(receiptId: Int?) = itemDAO.getItemsForReceipt(receiptId)







}