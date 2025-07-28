package com.example.huddleup.myteams

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun TeamDetails(navController: NavController) {
    Scaffold(

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Button(
                onClick = { navController.navigate(com.example.huddleup.Routes.MY_TEAMS) },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("Teams")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Team Icon
            Icon(
                imageVector = Icons.Default.Groups,
                contentDescription = "Team Icon",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(72.dp)
            )

            // Team Info
            Text(
                text = "Team Name",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Sport: Volleyball",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Level: Beginner",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Game Stats:", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(3) { index ->
                    val game = listOf(
                        Triple("Game Won", "FI", "05/02/2025"),
                        Triple("Game Lost", "FI", "04/19/2025"),
                        Triple("Game Won", "W23", "04/10/2025")
                    )[index]

                    GameStatCard(
                        result = game.first,
                        team = game.second,
                        date = game.third,
                        rank = if (game.first == "Game Won") "1" else "2",
                        isWin = game.first == "Game Won"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }


    }
}

@Composable
fun GameStatCard(result: String, team: String, date: String, rank: String, isWin: Boolean) {
    val colors = MaterialTheme.colorScheme
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colors.primaryContainer),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Groups,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = result, fontWeight = FontWeight.Bold)
                Text(text = "Competing Team: $team")
                Text(text = "Date: $date")
                Text(text = "New Rank: $rank")
            }

            if (isWin) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = "Win Medal",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
