package com.example.budgetapplication.navigation

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.budgetapplication.screens.main_screens.Create_Budget
import com.example.budgetapplication.screens.main_screens.OCR
import com.example.budgetapplication.screens.main_screens.HomeScreen
import com.example.budgetapplication.screens.main_screens.LoginScreen
import com.example.budgetapplication.screens.main_screens.ManualInput
import com.example.budgetapplication.screens.main_screens.ProfileScreen
import com.example.budgetapplication.screens.main_screens.Splash
import com.example.budgetapplication.screens.main_screens.ViewTransactions
import com.example.budgetapplication.views.LazyCardView
import com.example.budgetapplication.views.LoginView
import com.example.budgetapplication.views.SharedView
import kotlinx.coroutines.delay
import com.example.budgetapplication.screens.main_screens.OCRReview
import com.example.budgetapplication.screens.main_screens.CategoryItems

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val sharedView: SharedView = viewModel()
//    val loginView: LoginView = viewModel()
    val context = LocalContext.current.applicationContext as Application
    val loginView: LoginView = viewModel(factory = LoginView.provideFactory(context))
    val lazyCardView: LazyCardView = viewModel(
        factory = LazyCardView.provideFactory(context)
    )
    NavHost(
        navController = navController,
        startDestination = NavRoutes.PROFILE
    ) {
        composable(NavRoutes.SPLASH){
            LaunchedEffect(Unit) {
                delay(5000)
                navController.navigate(NavRoutes.TRANSACTION_HISTORY)
            }
            Splash()
        }
        composable(NavRoutes.LOGIN){

            LoginScreen(
                loginView = loginView,
                sharedView = sharedView,
                onHomeClick = { userName -> navController.navigate(NavRoutes.HOME) },
                onInputSpendingClick = {},
                onViewTransactionClick = {}
            )
        }
        composable(NavRoutes.HOME){
            HomeScreen(
                sharedView = sharedView,
                lazyCardView = lazyCardView,
                onProfileClick = { navController.navigate(NavRoutes.PROFILE) },
                onLogoutClick = {
                    navController.navigate(NavRoutes.LOGIN) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                },
                onHomeClick = {userName -> navController.navigate(NavRoutes.HOME)},
                onInputSpendingClick = {navController.navigate(NavRoutes.INPUT)},
                onViewTransactionClick = {navController.navigate(NavRoutes.TRANSACTION_HISTORY)},
                onCreateBudgetClick = {navController.navigate(NavRoutes.CREATE_BUDGET)},
            )

        }
        composable(NavRoutes.PROFILE){
            ProfileScreen(
                sharedView = sharedView,
                onBackClick = { navController.popBackStack() },
                onProfileClick = {navController.navigate(NavRoutes.PROFILE)} ,
                onHomeClick = {userName -> navController.navigate(NavRoutes.HOME)},
                onInputSpendingClick = {navController.navigate(NavRoutes.INPUT)},
                onViewTransactionClick = {navController.navigate(NavRoutes.TRANSACTION_HISTORY)}
            )
        }
        composable(NavRoutes.INPUT){
            OCR(
                onNavigateToReview = { encodedJson ->
                    navController.navigate("${NavRoutes.OCR_REVIEW}/$encodedJson")
                },
                onNavigateToManual = {
                    navController.navigate(NavRoutes.MANUAL)
                }
            )
        }
        composable(NavRoutes.TRANSACTION_HISTORY){
            ViewTransactions(
                sharedView = sharedView,
                lazyCardView = lazyCardView,
                onBackClick = { navController.popBackStack() },
                onProfileClick = {navController.navigate(NavRoutes.PROFILE)} ,
                onHomeClick = {userName -> navController.navigate(NavRoutes.HOME)},
                onInputSpendingClick = {navController.navigate(NavRoutes.INPUT)},
                onViewTransactionClick = {navController.navigate(NavRoutes.TRANSACTION_HISTORY)}
            )
        }
        composable(NavRoutes.OCR) {
            OCR(
                onNavigateToReview = { encodedJson ->
                    navController.navigate("${NavRoutes.OCR_REVIEW}/$encodedJson")
                },
                onNavigateToManual = {
                    navController.navigate(NavRoutes.MANUAL)
                }
            )
        }


        composable(
            route = "${NavRoutes.OCR_REVIEW}/{encodedJson}"
        ) { backStackEntry ->

            val encodedJson = backStackEntry.arguments?.getString("encodedJson")

            OCRReview(
                encodedJson = encodedJson,
                onBackClick = { navController.popBackStack() },

                // REQUIRED
                onConfirm = {
                    // After confirming, navigate to HOME or INPUT
                    navController.navigate(NavRoutes.HOME) {
                        popUpTo(NavRoutes.OCR) { inclusive = true }
                    }
                }
            )
        }


        composable(NavRoutes.MANUAL) {
            ManualInput(
                onBackClick = { navController.popBackStack() },
                onConfirm = { navController.navigate(NavRoutes.HOME) },
                lazyCardView = lazyCardView,
            )
        }


        composable(NavRoutes.CREATE_BUDGET){
            Create_Budget(onBackClick = { navController.popBackStack() },
                lazyCardView = lazyCardView,)
        }
        composable(NavRoutes.TEST_CARD){
            CategoryItems(
                lazyCardView = lazyCardView,

            )
        }
    }


}