package com.example.huddleup.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.huddleup.sharedcomponents.PageHeader
import androidx.navigation.NavController
import com.example.huddleup.ui.theme.Cream
import com.example.huddleup.ui.theme.RoseQuartz
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DashboardScreen(
    navController: NavController,
) {
    val nextGame: Game? = allGames
        .filter { it.date >= LocalDate.now() }
        .minByOrNull { it.date }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Cream)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        PageHeader(title = "HuddleUp")
        Spacer(modifier = Modifier.height(8.dp))
        // Next Game Card
        Card(
            onClick = {
                nextGame?.let {
                    navController.navigate("game_details/${nextGame.id}")
                }
            },
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = RoseQuartz),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    "NEXT GAME",
                    style = MaterialTheme.typography.labelLarge.copy(color = Color.Gray)
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (nextGame != null) {

                    Text(
                        text = "${nextGame.date.format(DateTimeFormatter.ofPattern("MMM dd"))}",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = nextGame.time,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "vs ${nextGame.team2}", style = MaterialTheme.typography.bodyLarge)
                } else {
                    Text("No upcoming games", style = MaterialTheme.typography.bodyLarge)
                }

            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Navigation List
        DashboardNavItem(
            title = "Schedule",
            subtitle = "View upcoming games",
            onClick = { navController.navigate(com.example.huddleup.Routes.SCHEDULE) }
        )

        DashboardNavItem(
            title = "Chat",
            subtitle = "Team messaging",
            onClick = { navController.navigate(com.example.huddleup.Routes.GROUP_CHAT) }
        )
        DashboardNavItem(
            title = "Teams",
            subtitle = "Manage your teams",
            onClick = { navController.navigate(com.example.huddleup.Routes.TEAM_SEARCH) }
        )
    }
}

@Composable
fun DashboardNavItem(title: String, subtitle: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 20.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = subtitle, style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray))
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Go",
            tint = Color.Gray,
            modifier = Modifier.size(28.dp)
        )
    }
}