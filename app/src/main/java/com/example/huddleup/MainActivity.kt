package com.example.huddleup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.huddleup.auth.LoginScreen
import com.example.huddleup.auth.SignUpScreen
import com.example.huddleup.dashboard.DashboardScreen
import com.example.huddleup.notifications.NotificationsScreen
import com.example.huddleup.settings.SettingsScreen
import com.example.huddleup.teamsearch.TeamSearchScreen
import com.example.huddleup.ui.theme.HuddleUpTheme
import com.example.huddleup.ui.theme.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()

            HuddleUpTheme(themeViewModel = themeViewModel) {
                val startRoute = if (NavigationUtils.isUserLoggedIn()) Routes.DASHBOARD else Routes.LOGIN
                val navController = rememberNavController()
                val selectedScreenRoute = remember { mutableStateOf(startRoute)}

                Scaffold(
                    containerColor = MaterialTheme.colorScheme.background,
                    bottomBar = {
                    if (NavigationUtils.checkIfDisabledNavBarRoute(selectedScreenRoute.value)) {
                        BottomNavigationBar(selectedScreenRoute, navController)
                    }
                }) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = startRoute,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                    {
                        composable(route = Routes.LOGIN) { LoginScreen(navController) }
                        composable(route = Routes.SIGNUP) { SignUpScreen(navController) }
                        composable(route = Routes.DASHBOARD) { DashboardScreen(navController) }
                        composable(route = Routes.SETTINGS) { SettingsScreen(navController) }
                        composable(route = Routes.NOTIFICATION) {
                            NotificationsScreen(
                                navController
                            )
                        }
                        composable(route = Routes.TEAM_SEARCH) {
                            TeamSearchScreen(
                                navController
                            )
                        }
                        // TODO: ADD OTHER COMPOSABLE ROUTES HERE
                    }
                }
            }
        }
    }
}
