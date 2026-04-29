package com.example.budgetapplication.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime. *

@Entity(tableName = "MERCHANT")
data class Merchant(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "M_MERCHANT_ID")
    val merchantId_i: Int? = null,

    @ColumnInfo(name = "M_MERCHANT_NAME")
    val merchantName_s: String? = null,

    @ColumnInfo(name = "M_OUT")
    val merchantOut_b: Boolean? = null

)
