package com.example.budgetapplication.screens.main_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun Splash(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF6200EE)), // purple background
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Welcome to My Budget!",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
    }
}