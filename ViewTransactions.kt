package com.example.budgetapplication.screens.main_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.budgetapplication.ui.theme.Mint
import com.example.budgetapplication.ui.theme.RocketCard
import com.example.budgetapplication.views.LazyCardView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Create_Budget(onBackClick: () -> Boolean, lazyCardView: LazyCardView) {
    val categories by lazyCardView.categories.collectAsState()
    var budgetLimit by remember{ mutableStateOf("") }
    var state by remember { mutableStateOf(false) }




    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Mint, titleContentColor = RocketCard
                ),
                title = {
                    Text("Edit Categories" )
                },
                navigationIcon = {
                    IconButton(onClick = {onBackClick()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description",
                            tint = RocketCard
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                lazyCardView.expanded = !lazyCardView.expanded
            }, containerColor = Mint) {
                if (lazyCardView.expanded == true){
                Icon(Icons.Default.Check, contentDescription = "Add",tint = RocketCard)
                }else{
                Icon(Icons.Default.Add, contentDescription = "Add", tint = RocketCard)}
            }
        },


    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

                LazyCategoryInputCard(lazyCardView)
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    items(items = categories) { item ->
                    LazyCard(item)

                }

                }
            }
        }
    }

