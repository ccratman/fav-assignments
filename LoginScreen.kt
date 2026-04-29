package com.example.budgetapplication.data.repository

import com.example.budgetapplication.data.StagingPayload
import com.example.budgetapplication.data.database.AppDatabase
import com.example.budgetapplication.data.entities.Item
import com.example.budgetapplication.data.entities.Merchant
import com.example.budgetapplication.data.entities.Receipt
import com.example.budgetapplication.data.entities.Staging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class StagingRepository(private val db: AppDatabase) {

    private val stagingDao = db.stagingDAO()
    private val merchantDAO = db.merchantDAO()
    private val receiptDAO = db.receiptDAO()

    private  val itemDAO = db.itemDAO()


    val allStaging: Flow<List<Staging>> = stagingDao.getAllStagingById()

    suspend fun saveItem(item: Item) = itemDAO.insert(item)

    suspend fun checkReceipt(id: Int?) = receiptDAO.getMostRecentReceiptForMerchant(id)
    suspend fun clearStaging() = stagingDao.clearStagingTable()

    suspend fun saveMerchant(merchant: Merchant) = merchantDAO.insert(merchant)
    suspend fun checkMerchant(name: String) = merchantDAO.getMerchantByName(name)

    suspend fun saveReceipt(receipt: Receipt) = receiptDAO.insert(receipt)

    suspend fun saveToStaging(payload: StagingPayload) = withContext(Dispatchers.IO) {

        payload.items.forEach { item ->

            val row = Staging(
                sbudgetLimit_s = "",
                sbudgetStart_l = 0L,
                sbudgetEnd_l = 0L,

                scategoryId_s = item.categoryId,
                scategoryLimit_s = "",

                sitemName_s = item.name,
                sitemPrice_s = item.price.toString(),

                smerchantName_s = payload.merchant,
                sreceiptSubtotal_s = payload.subtotal,
                sreceiptTax_s = payload.tax,
                sreceiptTotal_s = payload.total,
                sreceiptDate_s = payload.date,
                sreceiptTime_s = payload.time
            )

            stagingDao.insertOCRStaging(row)
        }
    }
}
