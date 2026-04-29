package com.example.budgetapplication.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "RECEIPT",
    foreignKeys = [
        ForeignKey(
            entity = Merchant::class,
            parentColumns = ["M_MERCHANT_ID"],
            childColumns = ["R_MERCHANT_ID"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Receipt(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "R_RECEIPT_ID")
    val receiptId_i: Int? = null,

    @ColumnInfo(name = "R_MERCHANT_ID")
    val merchantId_i: Int? = null,

    @ColumnInfo(name = "R_RECEIPT_SUBTOTAL")
    val receiptSubtotal_s: String? = null,

    @ColumnInfo(name = "R_RECEIPT_TAX")
    val receiptTax_s: String? = null,

    @ColumnInfo(name = "R_RECEIPT_TOTAL")
    val receiptTotal_s: String? = null,

    @ColumnInfo(name = "R_RECEIPT_DATE")
    val receiptDate_s: String? = null,

    @ColumnInfo(name = "R_RECEIPT_TIME")
    val receiptTime_s: String? = null,

    @ColumnInfo(name = "R_RECEIPT_DATETIME_INPUT")
    val receiptDatetimeIn_l: Long? = null,

    @ColumnInfo(name = "R_OUT")
    val receiptOut_b: Boolean? = null

)
