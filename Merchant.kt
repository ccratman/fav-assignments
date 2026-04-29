package com.example.budgetapplication.data.databasetest.seeders

import com.example.budgetapplication.data.dao.ReceiptDAO
import com.example.budgetapplication.data.entities.Receipt
import com.example.budgetapplication.data.databasetest.DateTimeHelper

class ReceiptSeeder {

    suspend fun populateTransactions(receiptDAO: ReceiptDAO) {

        val amazonId = SeederState.ids["amazonMerchant"]!!.toInt()
        val walmartId = SeederState.ids["walmartMerchant"]!!.toInt()
        val comedyCenterId = SeederState.ids["comedyCenterMerchant"]!!.toInt()

        fun dateTimeStrings(dateTime: Long): Pair<String, String> {
            return DateTimeHelper.formatDate(dateTime) to DateTimeHelper.formatTime(dateTime)
        }

        // Receipt 1 - Amazon
        val amazonDateTime = DateTimeHelper.create(11, 2, 2025, 14, 32, 0).time
        val (amazonDate, amazonTime) = dateTimeStrings(amazonDateTime)
        val amazonReceiptId01 = receiptDAO.insert(
            Receipt(
                merchantId_i = amazonId,
                receiptSubtotal_s = "100.00",
                receiptTax_s = "6.00",
                receiptTotal_s = "106.00",
                receiptDate_s = amazonDate,
                receiptTime_s = amazonTime,
                receiptDatetimeIn_l = DateTimeHelper.create(11, 2, 2025, 14, 35, 0).time,
                receiptOut_b = false
            )
        )

        // Receipt 2 - Walmart
        val walmartDateTime = DateTimeHelper.create(11, 3, 2025, 10, 18, 0).time
        val (walmartDate, walmartTime) = dateTimeStrings(walmartDateTime)
        val walmartReceiptId01 = receiptDAO.insert(
            Receipt(
                merchantId_i = walmartId,
                receiptSubtotal_s = "18.0",
                receiptTax_s = "2.0",
                receiptTotal_s = "20.0",
                receiptDate_s = walmartDate,
                receiptTime_s = walmartTime,
                receiptDatetimeIn_l = DateTimeHelper.create(11, 3, 2025, 10, 25, 0).time,
                receiptOut_b = false
            )
        )

        // Receipt 3 - Comedy Center
        val comedy1DateTime = DateTimeHelper.create(11, 4, 2025, 8, 15, 0).time
        val (comedy1Date, comedy1Time) = dateTimeStrings(comedy1DateTime)
        val comedyCenterReceiptId01 = receiptDAO.insert(
            Receipt(
                merchantId_i = comedyCenterId,
                receiptSubtotal_s = "12.50",
                receiptTax_s = "0.75",
                receiptTotal_s = "13.25",
                receiptDate_s = comedy1Date,
                receiptTime_s = comedy1Time,
                receiptDatetimeIn_l = DateTimeHelper.create(11, 4, 2025, 8, 17, 0).time,
                receiptOut_b = false
            )
        )

        // Receipt 4 - Comedy Center
        val comedy2DateTime = DateTimeHelper.create(11, 5, 2025, 8, 15, 0).time
        val (comedy2Date, comedy2Time) = dateTimeStrings(comedy2DateTime)
        val comedyCenterReceiptId02 = receiptDAO.insert(
            Receipt(
                merchantId_i = comedyCenterId,
                receiptSubtotal_s = "100.0",
                receiptTax_s = "6.0",
                receiptTotal_s = "106.0",
                receiptDate_s = comedy2Date,
                receiptTime_s = comedy2Time,
                receiptDatetimeIn_l = DateTimeHelper.create(11, 5, 2025, 8, 17, 0).time,
                receiptOut_b = false
            )
        )

        SeederState.ids["amazonTransaction01"] = amazonReceiptId01
        SeederState.ids["walmartTransaction01"] = walmartReceiptId01
        SeederState.ids["comedyCenterTransaction01"] = comedyCenterReceiptId01
        SeederState.ids["comedyCenterTransaction02"] = comedyCenterReceiptId02
    }
}
