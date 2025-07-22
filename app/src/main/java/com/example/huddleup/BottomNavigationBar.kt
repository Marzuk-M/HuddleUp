package com.example.huddleup

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.filled.SportsTennis
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

data class NavItem (
    val label: String,
    val icon: ImageVector,
    val route: String,
)

@Composable
fun BottomNavigationBar (
    selectedScreenRoute: MutableState<String>,
    navController: NavController
) {
    val navItemList = listOf(
        NavItem(
            label = "Team Search",
            icon = Icons.Default.Search,
            route = Routes.TEAM_SEARCH
        ),
        NavItem(
            label = "Dashboard",
            icon = Icons.Default.CalendarToday,
            route = Routes.DASHBOARD
        ),
        NavItem(
            label = "My Teams",
            icon = Icons.Default.Groups,
            route = Routes.MY_TEAMS
        ),
        NavItem(
            label = "My Leagues",
            icon = Icons.Default.SportsTennis,
            route = Routes.MY_LEAGUES
        ),
        NavItem(
            label = "Notifications",
            icon = Icons.Default.Notifications,
            route = Routes.NOTIFICATION
        ),
        NavItem(
            label = "Settings",
            icon = Icons.Default.Settings,
            route = Routes.SETTINGS
        ),
    )

    NavigationBar {
        navItemList.forEachIndexed {index, navItem ->
            NavigationBarItem(
                selected = selectedScreenRoute.value == navItem.route,
                onClick = {
                    if (selectedScreenRoute.value != navItem.route) {
                        selectedScreenRoute.value = navItem.route
                        navController.navigate(navItem.route)
                    }
                },
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                }
            )
        }
    }
}