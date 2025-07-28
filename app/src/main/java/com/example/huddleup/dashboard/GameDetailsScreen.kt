package com.example.huddleup.dashboard

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huddleup.sharedcomponents.HUDividerWithText
import com.google.firebase.auth.FirebaseAuth

val gameAvailability = mutableStateMapOf<Int, MutableMap<String, String>>() // gameId -> (email -> status)

@Composable
fun GameDetailsScreen(
    navController: NavController, 
    gameId: String?,
    viewModel: ScheduleViewModel = viewModel()
) {
    val games by viewModel.games.collectAsState()
    val game = games.find { it.originalId == gameId }
    val currentUser = FirebaseAuth.getInstance().currentUser
    val userEmail = currentUser?.email ?: "unknown@email.com"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (game != null) {
            Text("Game Details", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            // Game Info Box
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text("Teams:", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                Text("${game.team1} vs ${game.team2}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Date:", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                Text("${game.date}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Time:", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                Text("${game.time}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Place:", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                Text("${game.place}", style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(modifier = Modifier.height(24.dp))

            val availability = remember { gameAvailability.getOrPut(game.id) { mutableStateMapOf() } }
            val userStatus = availability[userEmail] ?: "none"
            val inUsers = availability.filter { it.value == "in" }.keys
            val outUsers = availability.filter { it.value == "out" }.keys

            Text("Availability: ${inUsers.size} In / ${outUsers.size} Out", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { 
                        availability[userEmail] = "in"
                        gameId?.let { viewModel.updateGameAvailability(it, "in", userEmail) }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (userStatus == "in") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("I'm In")
                }

                Button(
                    onClick = { 
                        availability[userEmail] = "out"
                        gameId?.let { viewModel.updateGameAvailability(it, "out", userEmail) }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (userStatus == "out") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("I'm Out")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "Your Status: ${userStatus.uppercase()}",
                style = MaterialTheme.typography.titleMedium,
                color = if (userStatus == "in") Color(0xFF2E7D32) else if (userStatus == "out") Color(0xFFC62828) else MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(24.dp))

            Column (modifier = Modifier.fillMaxWidth()) {

                HUDividerWithText("IN")
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    inUsers.forEach { Text(it) }
                }

                HUDividerWithText("OUT")
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    outUsers.forEach { Text(it) }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        } else {
            Text("Game not found", color = MaterialTheme.colorScheme.error)
        }
    }
}
