package com.example.budgetapplication.screens.main_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/*
* Text field for Vendor
* Text field total price
* Text field for Date Time
*
* Lazy Col
* Card
* Text field for Name
* Text field for Price
* Category dropdown
* Trash Icon to add new card
* Plus Icon to add new card
*
*
* This screen could also be for editing transactions and confirmation screen for OCR??
*
*check box for save receipt
*Button to confirm
*
*
*
*
*
* */
@Composable
fun InputSpending(
    onBackClick: () -> Unit,
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                modifier = Modifier.padding(32.dp).fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                text = "Input Screen"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onBackClick() }, modifier = Modifier.fillMaxWidth()
                .padding(32.dp).height(60.dp)) { Text("Back") }

        }
    }

}