package com.example.budgetapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.budgetapplication.data.database.AppDatabase
import com.example.budgetapplication.data.databasetest.seeders.BudgetSeeder
import com.example.budgetapplication.data.databasetest.seeders.CategorySeeder
import com.example.budgetapplication.data.databasetest.seeders.ItemSeeder
import com.example.budgetapplication.data.databasetest.seeders.MerchantSeeder
import com.example.budgetapplication.data.databasetest.seeders.ReceiptSeeder
import com.example.budgetapplication.data.databasetest.seeders.SeederState
import com.example.budgetapplication.data.databasetest.seeders.UserSeeder
import com.example.budgetapplication.navigation.AppNavigation
import com.example.budgetapplication.ui.theme.BudgetApplicationTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BudgetApplicationTheme {
                AppNavigation()
            }
        }
        //Database testing - Kevin 11/15/25
        lifecycleScope.launch {
            try {

                // Clear SeederState
                SeederState.ids.clear()

                // Delete old database
                applicationContext.deleteDatabase("test-db")

                // Create new database
                val db = Room.databaseBuilder(this@MainActivity, AppDatabase::class.java, "test-db")
                    .allowMainThreadQueries()
                    .build()

                // Populate seeders
                BudgetSeeder().populateBudgets(db.budgetDAO())
                CategorySeeder().populateCategories(db.categoryDAO())
                MerchantSeeder().populateMerchants(db.merchantDAO())
                UserSeeder().populateUsers(db.userDAO())
                ReceiptSeeder().populateTransactions(db.receiptDAO())
                ItemSeeder().populateItems(db.itemDAO())

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun Preview() {
    BudgetApplicationTheme  {
        AppNavigation()
    }
}