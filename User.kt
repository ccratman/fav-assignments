package com.example.budgetapplication.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import kotlinx.datetime. *

@Entity(tableName = "BUDGET")
data class Budget(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "B_BUDGET_ID")
    val budgetId_i: Int? = null,

    @ColumnInfo(name = "B_BUDGET_LIMIT")
    val budgetLimit_s: String? = null,

    @ColumnInfo(name = "B_BUDGET_START")
    val budgetStart_l: Long? = null,

    @ColumnInfo(name = "B_BUDGET_END")
    val budgetEnd_l: Long? = null,

    @ColumnInfo(name = "B_OUT")
    val budgetOut_b: Boolean? = null

)
