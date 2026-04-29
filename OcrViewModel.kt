package com.example.budgetapplication.screens.main_screens

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.budgetapplication.data.entities.Category
import com.example.budgetapplication.data.entities.Item
import com.example.budgetapplication.data.entities.Receipt
import com.example.budgetapplication.ui.theme.Mint
import com.example.budgetapplication.ui.theme.RocketCard
import com.example.budgetapplication.ui.theme.TextWhite
import com.example.budgetapplication.views.LazyCardView

@Composable
fun NavBar(
    onHomeClick: (String) -> Unit,
    onInputSpendingClick: () -> Unit,
    onViewTransactionClick: () -> Unit,
    onSettingsClick: () -> Unit,
){

    NavigationBar(containerColor = Mint , contentColor = Color.Black) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home" , tint = RocketCard) },
            label = { Text("Home", color =  RocketCard) },
            selected = false,
            onClick = {onHomeClick("")}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = RocketCard) },
            label = { Text("Settings", color =  RocketCard) },
            selected = false,
            onClick = {onSettingsClick()}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Add, contentDescription = "Input",tint = RocketCard) },
            label = { Text("Input", color =  RocketCard)},
            selected = false,
            onClick = {onInputSpendingClick()}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.AccountBalance, contentDescription = "View", tint = RocketCard) },
            label = { Text("View", color =  RocketCard) },
            selected = false,
            onClick = {onViewTransactionClick()}
        )

    }
}

@Composable
fun LazyCard(item: Item) {
    val context = LocalContext.current.applicationContext as Application
    val lazyCardView: LazyCardView = viewModel(
        factory = LazyCardView.provideFactory(context)
    )
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = RocketCard , contentColor = TextWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item.itemName_s?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            item.itemPrice_s?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            }
        }
    }
}

@Composable
fun LazyCard(receipt: Receipt, onItemClick: () -> Unit) {
    val context = LocalContext.current.applicationContext as Application
    val lazyCardView: LazyCardView = viewModel(
        factory = LazyCardView.provideFactory(context)
    )
    LaunchedEffect(receipt.merchantId_i) {
        lazyCardView.loadMerchant(receipt.merchantId_i)
    }
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = {onItemClick()}),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = RocketCard , contentColor = TextWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            lazyCardView.merchants[receipt.merchantId_i]?.merchantName_s?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text ="${receipt.receiptTotal_s}" ,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
/**/
@Composable
fun LazyCard(category: Category,) {
    val context = LocalContext.current.applicationContext as Application
    val lazyCardView: LazyCardView = viewModel(
        factory = LazyCardView.provideFactory(context)
    )
    val spending = lazyCardView.spendingMap[category.categoryId_i] ?: 0.0

    LaunchedEffect(category.categoryId_i) {
        lazyCardView.getCurrentSpending(category.categoryId_i)
    }


    Card(
        modifier = Modifier.height(100.dp).fillMaxWidth().padding(start = 20.dp,end =20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = RocketCard , contentColor = TextWhite)
    ){
        Column() {
            Text(
                text = "${category.categoryName_s}",
                modifier = Modifier.padding(start = 16.dp, top =16.dp),
                style = MaterialTheme.typography.titleLarge
            )
            Row {
                Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                    Text(
                        text = "Limit",
                        modifier = Modifier.padding(1.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "$" + "${category.categoryLimit_s}",
                        modifier = Modifier.padding(1.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Column {
                    Text(
                        text = "Current Spending",
                        modifier = Modifier.padding(1.dp),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "$"+"${spending}",
                        modifier = Modifier.padding(1.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }

}
@SuppressLint("SuspiciousIndentation")
@Composable
fun LazyCategoryInputCard(lazyCardView: LazyCardView)
{
//    val context = LocalContext.current.applicationContext as Application
//    val lazyCardView: LazyCardView = viewModel(
//        factory = LazyCardView.provideFactory(context)
//    )
    val cardHeight by animateDpAsState(
        targetValue = if (lazyCardView.expanded) 118.dp else 40.dp,
        label = "cardWidth"
    )
    val titlePadding by animateDpAsState(
        targetValue = if (lazyCardView.expanded) 8.dp else 120.dp,
        label = "cardWidth"
    )
        Card(
            modifier = Modifier.height(cardHeight).fillMaxWidth().padding(start = 20.dp,end =20.dp, bottom = 8.dp, top = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            onClick = {lazyCardView.expanded = !lazyCardView.expanded},
            colors = CardDefaults.cardColors(containerColor = RocketCard , contentColor = TextWhite)

        ){
            if (lazyCardView.expanded){
            Text(
                text = "New Category",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = titlePadding, end = 8.dp, )
            )}
            else{
                Text(
                    text = "Add Category",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = titlePadding, end = 8.dp, )
                )
            }
            Row {
                OutlinedTextField(
                    value = lazyCardView.categoryName_s,
                    onValueChange = { lazyCardView.changeCategoryName(it) },
                    label = { Text("Name", color = TextWhite) },
                    placeholder = { Text("Rent", color = TextWhite) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    modifier = Modifier.weight(.33f).padding(8.dp)
                )
                OutlinedTextField(
                    value = lazyCardView.categoryLimit_s,
                    onValueChange = { lazyCardView.changeCategoryLimit(it) },
                    label = { Text("Limit", color = TextWhite) },
                    placeholder = { Text("100.00", color = TextWhite) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    modifier = Modifier.weight(.33f).padding(8.dp),

                )
                IconButton(
                    onClick = {
                        lazyCardView.buildCategory()
                        lazyCardView.createNewCategory()
                        lazyCardView.categoryName_s = ""
                        lazyCardView.categoryLimit_s = ""
                    }
                ) { Icon(Icons.Filled.Add, contentDescription = "Add")}
            }
        }
}
@Composable
fun LazyCard() {
    Card(
        modifier = Modifier.width(400.dp).height(300.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
        Text(text = "GRAPH")
    }

}
@SuppressLint("UnrememberedMutableState")
@Composable
fun CategoryDropdownMenu(onCatgorySelected: (Int?) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    // Placeholder list of 100 strings for demonstration
    val menuItemData = List(100) { "Option ${it + 1}" }
    val context = LocalContext.current.applicationContext as Application
    val lazyCardView: LazyCardView = viewModel(
        factory = LazyCardView.provideFactory(context)
    )
    val categories by lazyCardView.categories.collectAsState()
    var sampleText: String? by remember { mutableStateOf("Set Category")}

//    Box(
//        modifier = Modifier
//            .padding(8.dp).height(50.dp).width(100.dp)
//    ) {
        Card(
            onClick = {expanded = !expanded},
            modifier = Modifier.fillMaxWidth().padding(12.dp)
        ) {
            sampleText?.let { Text(text = it, modifier = Modifier.padding(8.dp)) }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }

        ) {
            categories.forEach { option ->
                DropdownMenuItem(
                    text = { option.categoryName_s?.let { Text(it) } },
                    onClick = {
                        sampleText = option.categoryName_s
                        onCatgorySelected(option.categoryId_i)
                        expanded=false
                    }
                )
            }
        }
    }


