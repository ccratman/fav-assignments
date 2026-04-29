package com.example.budgetapplication.screens.main_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgetapplication.ui.theme.Mint
import com.example.budgetapplication.ui.theme.MintDark
import com.example.budgetapplication.ui.theme.NeonBlue
import com.example.budgetapplication.ui.theme.NeonRed
import com.example.budgetapplication.ui.theme.RocketBlack
import com.example.budgetapplication.ui.theme.RocketCard
import com.example.budgetapplication.ui.theme.TextGray
import com.example.budgetapplication.ui.theme.TextWhite
import com.example.budgetapplication.views.SharedView


@Composable
fun ProfileScreen(
    onProfileClick: () -> Unit,
    onBackClick: () -> Unit,
    onHomeClick: (String) -> Unit,
    onInputSpendingClick: () -> Unit,
    onViewTransactionClick: () -> Unit,
    sharedView: SharedView,
    onLogoutClick: () -> Unit = {},
) {
// Settings toggles
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkThemeEnabled by remember { mutableStateOf(true) }

    Scaffold(
        containerColor = RocketBlack,
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
                .padding(innerPadding)
                .padding(22.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

// 🔥 PAGE TITLE
            Text(
                text = "Settings",
                color = Mint,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

// 🔹 PROFILE CARD
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                colors = CardDefaults.cardColors(containerColor = RocketCard),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

// Avatar
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(MintDark),
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = sharedView.currentUser,
                        fontSize = 22.sp,
                        color = TextWhite,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Premium User",
                        color = NeonBlue.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                }
            }

// 🔹 GENERAL SETTINGS
            SettingSectionTitle("General")

            SettingToggleRow(
                title = "Notifications",
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )

            SettingToggleRow(
                title = "Dark Mode",
                checked = darkThemeEnabled,
                onCheckedChange = { darkThemeEnabled = it }
            )

            SettingClickableRow(
                title = "Change Password",
                onClick = { /* TODO */ }
            )

            SettingClickableRow(
                title = "Privacy Settings",
                onClick = { /* TODO */ }
            )

// 🔹 APP SETTINGS
            SettingSectionTitle("App")

            SettingClickableRow(
                title = "Clear Local Data",
                color = NeonRed,
                onClick = { /* TODO - clear db */ }
            )

            SettingClickableRow(
                title = "About App",
                onClick = { /* TODO */ }
            )

            Spacer(modifier = Modifier.height(24.dp))

// 🔥 LOGOUT BUTTON
            Button(
                onClick = onLogoutClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NeonRed),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Logout", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SettingSectionTitle(title: String) {
    Text(
        text = title,
        color = TextGray,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(vertical = 10.dp)
    )
}

@Composable
fun SettingToggleRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(RocketCard, RoundedCornerShape(14.dp))
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, color = TextWhite, fontSize = 18.sp)
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Mint,
                checkedTrackColor = MintDark,
                uncheckedThumbColor = TextGray,
            )
        )
    }

    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun SettingClickableRow(
    title: String,
    color: Color = TextWhite,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(RocketCard, RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, color = color, fontSize = 18.sp)
    }

    Spacer(modifier = Modifier.height(12.dp))
}
