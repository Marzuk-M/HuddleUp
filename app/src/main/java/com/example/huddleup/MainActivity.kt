package com.example.huddleup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.huddleup.auth.LoginScreen
import com.example.huddleup.auth.SignUpScreen
import com.example.huddleup.dashboard.DashboardScreen
import com.example.huddleup.dashboard.GameDetailsScreen
import com.example.huddleup.dashboard.ScheduleScreen
import com.example.huddleup.dashboard.GroupChatScreen
import com.example.huddleup.myleagues.MyLeaguesScreen
import com.example.huddleup.myteams.MyTeamsScreen
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

                LaunchedEffect(Unit) {
                    if (NavigationUtils.isUserLoggedIn()) {
                        navController.navigate(Routes.DASHBOARD) {
                            popUpTo(Routes.LOGIN) { inclusive = true } // Prevent back navigation to login/signup
                        }
                    }
                }

                LaunchedEffect(navController) {
                    navController.currentBackStackEntryFlow.collect { backStackEntry ->
                        selectedScreenRoute.value =
                            backStackEntry.destination.route ?: Routes.DASHBOARD
                    }
                }

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
                        composable(route = Routes.SCHEDULE) { ScheduleScreen(navController) }
                        composable(route = Routes.GAME_DETAILS_WITH_ARG) { backStackEntry ->
                            val gameId = backStackEntry.arguments?.getString("gameId")
                            GameDetailsScreen(gameId)
                        }

                        composable(route = Routes.GROUP_CHAT) { GroupChatScreen(navController) }
                        composable(route = Routes.MY_TEAMS) { MyTeamsScreen(navController) }
                        composable(route = Routes.MY_LEAGUES) { MyLeaguesScreen(navController) }
                        // TODO: ADD OTHER COMPOSABLE ROUTES HERE
                    }
                }
            }
        }
    }
}
