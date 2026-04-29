package com.example.budgetapplication.screens.main_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.budgetapplication.views.SharedView

@Composable
fun InputSpending(
    onProfileClick: () -> Unit,
    onBackClick: () -> Unit,
    onHomeClick: (String) -> Unit,
    onInputSpendingClick: () -> Unit,
    onViewTransactionClick: () -> Unit,
    onOcrClick: () -> Unit,
    onInputTransaction: () -> Unit,
    sharedView: SharedView,
) {
    Scaffold(
        bottomBar = {
            NavBar(
                onSettingsClick = { onProfileClick() },
                onHomeClick = { onHomeClick("") },
                onInputSpendingClick = { onInputSpendingClick() },
                onViewTransactionClick = { onViewTransactionClick() },
            )
        }
    ) {innerPadding ->
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        FlowColumn {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                item {
                    Button(
                        onClick = { onOcrClick() },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .height(60.dp)
                    ) {
                        Text(
                            text = "OCR",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }

                item {
                    Text(
                        modifier = Modifier
                            .padding(32.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        text = "Or"
                    )
                }

                item {
                    Button(
                        onClick = { onInputTransaction() },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .height(60.dp)
                    ) {
                        Text(
                            "Manual Input",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }
}
}
