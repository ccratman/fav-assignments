package com.example.budgetapplication.screens.main_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.budgetapplication.views.LazyCardView
import com.example.budgetapplication.views.SharedView

@Composable
fun CategoryItems(
    lazyCardView: LazyCardView,

) {
    var categoryId: Int? by remember { mutableStateOf(1) }
    val items by lazyCardView.getItemsForCategory(categoryId)
        .collectAsState(initial = emptyList())
    Scaffold(
        bottomBar = {
            CategoryDropdownMenu(
                onCatgorySelected = {newId ->
                    categoryId = newId
                })
        }
    ) {innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){items(items = items) { item ->
                LazyCard(item)
            }

        }
    }
}

