package com.example.budgetapplication.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "ITEM",
    foreignKeys = [
        ForeignKey(
            entity = Receipt::class,
            parentColumns = ["R_RECEIPT_ID"],
            childColumns = ["I_RECEIPT_ID"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["C_CATEGORY_ID"],
            childColumns = ["I_CATEGORY_ID"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = Budget::class,
            parentColumns = ["B_BUDGET_ID"],
            childColumns = ["I_BUDGET_ID"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = Merchant::class,
            parentColumns = ["M_MERCHANT_ID"],
            childColumns = ["I_MERCHANT_ID"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Item(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "I_ITEM_ID")
    val itemId_i: Int? = null,

    @ColumnInfo(name = "I_BUDGET_ID")
    val budgetId_i: Int? = null,

    @ColumnInfo(name = "I_CATEGORY_ID")
    val categoryId_i: Int? = null,

    @ColumnInfo(name = "I_MERCHANT_ID")
    val merchantId_i: Int? = null,

    @ColumnInfo(name = "I_RECEIPT_ID")
    val receiptId_i: Int? = null,

    @ColumnInfo(name = "I_ITEM_NAME")
    val itemName_s: String? = null,

    @ColumnInfo(name = "I_ITEM_PRICE")
    val itemPrice_s: String? = null,

    @ColumnInfo(name = "I_RECEIPT_DATE")
    val receiptDate_s: String? = null,

    @ColumnInfo(name = "I_RECEIPT_TIME")
    val receiptTime_s: String? = null,

    @ColumnInfo(name = "I_OUT")
    val itemOut_b: Boolean? = null

)
