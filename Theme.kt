package com.example.budgetapplication.screens.main_screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.budgetapplication.data.StagingPayload
import com.example.budgetapplication.data.database.AppDatabase
import com.example.budgetapplication.data.entities.Item
import com.example.budgetapplication.data.entities.Merchant
import com.example.budgetapplication.data.entities.Receipt
import com.example.budgetapplication.data.entities.StagingItem
import com.example.budgetapplication.data.repository.StagingRepository
import com.example.budgetapplication.ui.theme.Mint
import com.example.budgetapplication.ui.theme.RocketCard
import com.example.budgetapplication.ui.theme.TextWhite
import com.example.budgetapplication.views.LazyCardView
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class ManualLineItem(
    val name: String,
    val price: String,
    val categoryId: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualInput(
    onBackClick: () -> Unit,
    onConfirm: () -> Unit,
    lazyCardView: LazyCardView
) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val repo = StagingRepository(db)
    val scope = rememberCoroutineScope()
    val now = LocalDateTime.now()
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")   // Example: 2025-11-30
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")     // Example: 14:35:20

    val staging by repo.allStaging.collectAsState(initial = emptyList())

    val currentDate = now.format(dateFormatter)
    val currentTime = now.format(timeFormatter)

    var merchant by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var subtotal by remember { mutableStateOf("") }
    var tax by remember { mutableStateOf("") }
    var total by remember { mutableStateOf("") }

    var lineItems by remember { mutableStateOf<List<ManualLineItem>>(emptyList()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manual Entry", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint =RocketCard)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Mint, titleContentColor = RocketCard
                )
            )
        }
    ) { padding ->
        LazyColumn(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Receipt Header Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = RocketCard , contentColor = TextWhite),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Receipt Information", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Merchant & Date Row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = merchant,
                        onValueChange = { merchant = it },
                        label = { Text("Merchant") },
                        modifier = Modifier
                            .weight(1f),
                        textStyle = MaterialTheme.typography.bodyMedium,
                        shape = RoundedCornerShape(8.dp)
                    )
                    OutlinedTextField(
                        value = date,
                        onValueChange = { date = it },
                        label = { Text("Date") },
                        modifier = Modifier
                            .weight(1f),
                        textStyle = MaterialTheme.typography.bodyMedium,
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            }

            // Totals Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = RocketCard , contentColor = TextWhite)
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = subtotal,
                                onValueChange = { subtotal = it },
                                label = { Text("Subtotal") },
                                modifier = Modifier
                                    .weight(1f),
                                textStyle = MaterialTheme.typography.bodyMedium,
                                shape = RoundedCornerShape(8.dp)
                            )
                            OutlinedTextField(
                                value = tax,
                                onValueChange = { tax = it },
                                label = { Text("Tax") },
                                modifier = Modifier
                                    .weight(1f),
                                textStyle = MaterialTheme.typography.bodyMedium,
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                        OutlinedTextField(
                            value = total,
                            onValueChange = { total = it },
                            label = { Text("Total") },
                            modifier = Modifier
                                .fillMaxWidth(),
                            textStyle = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }
            }

            // Line Items Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Line Items", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Button(
                        onClick = { lineItems = lineItems + ManualLineItem("", "", "0") },
                        modifier = Modifier.height(32.dp),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(16.dp), tint = RocketCard,)
                        Spacer(Modifier.width(4.dp))
                        Text("Add", style = MaterialTheme.typography.labelSmall, color = RocketCard)
                    }
                }
            }

            // Line Items List
            if (lineItems.isNotEmpty()) {
                itemsIndexed(lineItems) { index, lineItem ->
                    Card(
                        modifier = Modifier.fillMaxWidth().height(160.dp),
                        colors = CardDefaults.cardColors(containerColor = RocketCard , contentColor = TextWhite),
                        border = BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.outlineVariant
                        ),
                        shape = RoundedCornerShape(8.dp)

                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start =12.dp, top =12.dp, bottom = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = lineItem.name,
                                onValueChange = { newName ->
                                    lineItems = lineItems.toMutableList().apply { this[index] = lineItem.copy(name = newName) }
                                },
                                label = { Text("Item") },
                                modifier = Modifier
                                    .weight(2f),
                                textStyle = MaterialTheme.typography.bodySmall,
                                shape = RoundedCornerShape(6.dp)
                            )
                            OutlinedTextField(
                                value = lineItem.price,
                                onValueChange = { newPrice ->
                                    lineItems = lineItems.toMutableList().apply { this[index] = lineItem.copy(price = newPrice) }
                                },
                                label = { Text("Price") },
                                modifier = Modifier
                                    .weight(1f),
                                textStyle = MaterialTheme.typography.bodySmall,
                                shape = RoundedCornerShape(6.dp)
                            )
                            IconButton(
                                onClick = { lineItems = lineItems.filterIndexed { i, _ -> i != index } },
                                modifier = Modifier.size(40.dp)
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Column(modifier = Modifier.padding()) {
                            CategoryDropdownMenu(
                                onCatgorySelected = { newId ->
                                    lineItems = lineItems.toMutableList().apply {
                                        this[index] = lineItem.copy(categoryId = newId.toString())
                                    }
                                },
                            )

                        }
                    }
                }
            } else {
                item {
                    Text(
                        "No line items added",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Confirm Button
            item {
                Button(
                        onClick = {
                            val itemsParsed = lineItems.mapNotNull { item ->
                                val priceVal = item.price.toDoubleOrNull() ?: return@mapNotNull null

                                if (item.name.isBlank()) return@mapNotNull null

                                StagingItem(
                                    name = item.name,
                                    price = priceVal,
                                    categoryId = item.categoryId.toIntOrNull()
                                )
                            }

                        val payload = StagingPayload(
                            merchant = merchant,
                            date = date,
                            time = "",
                            subtotal = subtotal,
                            tax = tax,
                            total = total,
                            items = itemsParsed
                        )

                        scope.launch {
                            repo.checkMerchant(merchant)
                            repo.saveToStaging(payload)
                            if(repo.checkMerchant(merchant)?.merchantName_s != merchant){
                                repo.saveMerchant(Merchant(merchantName_s = merchant, merchantOut_b =  false))
                                repo.saveReceipt(Receipt(
                                    merchantId_i = repo.checkMerchant(merchant)?.merchantId_i,
                                    receiptSubtotal_s = subtotal,
                                    receiptTax_s = tax,
                                    receiptTotal_s = total,
                                    receiptDate_s = currentDate,
                                    receiptTime_s = currentTime,
                                    receiptDatetimeIn_l = null,
                                    receiptOut_b = false ))
                            }else{
                                repo.saveReceipt(Receipt(
                                    merchantId_i = repo.checkMerchant(merchant)?.merchantId_i,
                                    receiptSubtotal_s = subtotal,
                                    receiptTax_s = tax,
                                    receiptTotal_s = total,
                                    receiptDate_s = currentDate,
                                    receiptTime_s = currentTime,
                                    receiptDatetimeIn_l = null,
                                    receiptOut_b = false ))
                            }

                            staging.forEach {stagingItem ->
                                    repo.saveItem(Item(
                                        budgetId_i = null,
                                        categoryId_i = stagingItem.scategoryId_s,
                                        merchantId_i = repo.checkMerchant(merchant)?.merchantId_i,
                                        receiptId_i = repo.checkReceipt(repo.checkMerchant(merchant)?.merchantId_i)?.receiptId_i,
                                        itemName_s = stagingItem.sitemName_s,
                                        itemPrice_s = stagingItem.sitemPrice_s,
                                        receiptDate_s = currentDate,
                                        receiptTime_s = currentTime,
                                        itemOut_b = false
                                    ))

                            }
                            repo.clearStaging()

                            onConfirm()


                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Confirm & Save", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold , color = RocketCard)
                }
            }

            item {
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}
