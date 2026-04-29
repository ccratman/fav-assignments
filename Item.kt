package com.example.budgetapplication.data.databasetest.seeders

import com.example.budgetapplication.data.dao.MerchantDAO
import com.example.budgetapplication.data.entities.Merchant

class MerchantSeeder {

    suspend fun populateMerchants(merchantDAO: MerchantDAO) {

        val amazonId = merchantDAO.insert(
            Merchant(
                merchantName_s = "Amazon",
                merchantOut_b = false
            )
        )

        val walmartId = merchantDAO.insert(
            Merchant(
                merchantName_s = "Walmart",
                merchantOut_b = false
            )
        )

        val comedyCenterId = merchantDAO.insert(
            Merchant(
                merchantName_s = "Comedy Center",
                merchantOut_b = false
            )
        )

        SeederState.ids["amazonMerchant"] = amazonId
        SeederState.ids["walmartMerchant"] = walmartId
        SeederState.ids["comedyCenterMerchant"] = comedyCenterId
    }
}
