package com.example.budgetapplication.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "STAGING")
data class Staging (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "S_STAGING_ID")
    val sstagingId_i: Int? = null,

    @ColumnInfo(name = "S_BUDGET_LIMIT")
    val sbudgetLimit_s: String? = null,

    @ColumnInfo(name = "S_BUDGET_START")
    val sbudgetStart_l: Long? = null,

    @ColumnInfo(name = "S_BUDGET_END")
    val sbudgetEnd_l: Long? = null,

    @ColumnInfo(name = "S_CATEGORY_ID")
    val scategoryId_s: Int? = null,

    @ColumnInfo(name = "S_CATEGORY_LIMIT")
    val scategoryLimit_s: String? = null,

    @ColumnInfo(name = "S_ITEM_NAME")
    val sitemName_s: String? = null,

    @ColumnInfo(name = "S_ITEM_PRICE")
    val sitemPrice_s: String? = null,

    @ColumnInfo(name = "S_MERCHANT_NAME")
    val smerchantName_s: String? = null,

    @ColumnInfo(name = "S_RECEIPT_SUBTOTAL")
    val sreceiptSubtotal_s: String? = null,

    @ColumnInfo(name = "S_RECEIPT_TAX")
    val sreceiptTax_s: String? = null,

    @ColumnInfo(name = "S_RECEIPT_TOTAL")
    val sreceiptTotal_s: String? = null,

    @ColumnInfo(name = "S_RECEIPT_DATE")
    val sreceiptDate_s: String? = null,

    @ColumnInfo(name = "S_RECEIPT_TIME")
    val sreceiptTime_s: String? = null

)