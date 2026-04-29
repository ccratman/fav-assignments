package com.example.budgetapplication.screens.main_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgetapplication.ui.theme.Mint
import com.example.budgetapplication.ui.theme.RocketBlack
import com.example.budgetapplication.ui.theme.RocketCard
import com.example.budgetapplication.views.LazyCardView
import com.example.budgetapplication.views.SharedView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProfileClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onHomeClick: (String) -> Unit,
    onInputSpendingClick: () -> Unit,
    onViewTransactionClick: () -> Unit,
    sharedView: SharedView,
    lazyCardView: LazyCardView,
    onCreateBudgetClick: () -> Unit,
) {
    val categories by lazyCardView.categories.collectAsState()
    Scaffold(
        containerColor = RocketBlack,
        topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Mint, titleContentColor = RocketCard
            ),
            title = {
                Text("Home" )
            },
            navigationIcon = {
                IconButton(onClick = {onLogoutClick()}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = "Localized description",
                        tint = RocketCard
                    )
                }
            }
        )
    },
        bottomBar = {
            NavBar(
                onSettingsClick = { onProfileClick() },
                onHomeClick = { onHomeClick("") },
                onInputSpendingClick = { onInputSpendingClick() },
                onViewTransactionClick = { onViewTransactionClick() },
            )
        }
    ) { innerPadding ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(items = categories) { item ->
                    LazyCard(item)
                }
                item { Button(
                    onClick = ({ onCreateBudgetClick() }),
                    modifier = Modifier.width(300.dp).padding(12.dp)
                ) {
                    Text("Edit Categories", color = RocketCard)
                }}


            }

        }

    }
}

