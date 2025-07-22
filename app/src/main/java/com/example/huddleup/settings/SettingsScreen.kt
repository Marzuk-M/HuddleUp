package com.example.huddleup.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.huddleup.dashboard.DashboardNavItem
import com.example.huddleup.sharedcomponents.PageHeader
import com.example.huddleup.ui.theme.Cream

@Composable
fun SettingsScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        // Main content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            PageHeader(title = "Settings")
            Spacer(modifier = Modifier.height(16.dp))
            DashboardNavItem(
                title = "\uD83D\uDC64 Profile",
                subtitle = "     Account Details",
                onClick = { navController.navigate(com.example.huddleup.Routes.PROFILE) }
            )
            DashboardNavItem(
                title = "\uD83D\uDD14Notifications",
                subtitle = "     View Reminders",
                onClick = { navController.navigate(com.example.huddleup.Routes.NOTIFICATION) }
            )
            DashboardNavItem(
                title = "\uD83C\uDFA7 Help and Support",
                subtitle = "     Guidelines",
                onClick = { navController.navigate(com.example.huddleup.Routes.DASHBOARD) }
            )
            DashboardNavItem(
                title = "❓ About",
                subtitle = "     More Information",
                onClick = { navController.navigate(com.example.huddleup.Routes.DASHBOARD) }
            )
        }

        // Logout Button pinned to bottom
        Button(
            onClick = { }, // TODO: Call AuthService.logout() and navigate
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Logout")
        }
    }
}
