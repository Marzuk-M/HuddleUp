package com.example.huddleup.settings

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.huddleup.sharedcomponents.PageHeader
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.draw.rotate
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.ui.text.font.FontWeight
import com.example.huddleup.ui.theme.ThemeViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    themeViewModel: ThemeViewModel
) {
    // Toggle state for Notifications
    var notificationsEnabled by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            PageHeader(title = "Settings")
            Spacer(modifier = Modifier.height(16.dp))

            // Profile
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate(com.example.huddleup.Routes.PROFILE)}
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "\uD83D\uDC64 Profile", style = MaterialTheme.typography.titleMedium)
                    Text(text = "      Account", style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray))
                }
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Go",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(28.dp)
                )
            }

            // Notifications with toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "\uD83D\uDD14 Notifications", style = MaterialTheme.typography.titleMedium)
                    Text(text = "      View Reminders", style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray))
                }
                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it }
                ) // Back end ppl can add logic to this toggle *****
            }

            // Dark Mode with toggle
            ThemeToggleRow(themeViewModel = themeViewModel)

            // Help and Support
            HelpSupportExpandableItem(
                title = "\uD83C\uDFA7 Help and Support",
                subtitle = "      Policy Details",
                policyText = "Here is our policy: We value your privacy and ensure your data is protected. We don't have any third party connections and your data is secure."
            )

            // About
            HelpSupportExpandableItem(
                title = "❓ About",
                subtitle = "      More Information",
                policyText = "HuddleUp is a platform dedicated to helping individuals and teams stay connected, organized, and inspired. We believe in building intuitive tools that promote collaboration, personal growth, and productivity, whether you're planning your next meetup or just checking in with yourself. Our mission is to empower users through thoughtful design and reliable technology that supports real-world connection."
            )
        }

        // Logout button at the bottom
        Button(
            onClick = { /* TODO: Call AuthService.logout() and navigate */ },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            Text("Logout")
        }
    }
}

@Composable
fun HelpSupportExpandableItem(
    title: String,
    subtitle: String,
    policyText: String
) {
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(targetValue = if (expanded) 90f else 0f, label = "rotation")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = subtitle, style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray))
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Expand",
                tint = Color.Gray,
                modifier = Modifier
                    .rotate(rotation)
                    .size(28.dp)
            )
        }

        AnimatedVisibility(visible = expanded) {
            Column(modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)) {
                Text(
                    text = policyText,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun ThemeToggleRow(themeViewModel: ThemeViewModel) {
    // Collect dark theme state as a boolean
    val isDarkTheme = themeViewModel.darkTheme.collectAsState().value

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "\uD83D\uDCA1 Dark Mode",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "      Change for visibility",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
            )
        }
        Switch(
            checked = isDarkTheme,
            onCheckedChange = { themeViewModel.setDarkTheme(it) }
        )
    }
}