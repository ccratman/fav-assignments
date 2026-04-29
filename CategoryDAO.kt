package com.example.budgetapplication.data

import com.example.budgetapplication.data.entities.StagingItem

data class StagingPayload(
    val merchant: String,
    val date: String,
    val time: String,
    val subtotal: String,
    val tax: String,
    val total: String,
    val items: List<StagingItem>
)
