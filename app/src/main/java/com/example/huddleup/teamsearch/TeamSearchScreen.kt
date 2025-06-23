package com.example.huddleup.teamsearch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.huddleup.ui.theme.*

// Data class to represent a team
data class Team(
    val id: String,
    val name: String,
    val members: Int,
    val membershipState: TeamMembershipState
)

// Enum to represent the user's membership state with a team
enum class TeamMembershipState {
    MEMBER,
    REQUESTED,
    NOT_A_MEMBER
}

// Dummy data for teams
val sampleTeams = listOf(
    Team("212231", "Team", 15, TeamMembershipState.NOT_A_MEMBER),
    Team("212232", "Team A", 15, TeamMembershipState.MEMBER),
    Team("212233", "Team B", 15, TeamMembershipState.REQUESTED),
    Team("212234", "Team C", 15, TeamMembershipState.NOT_A_MEMBER),
    Team("212235", "Team D", 15, TeamMembershipState.NOT_A_MEMBER),
)

@Composable
fun TeamSearchScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        // Top search bar section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(RoseQuartz)
                .padding(16.dp)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search for a team...", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp)),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp)
            )
        }

        // Teams list
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
        ) {
            items(sampleTeams.filter { it.name.contains(searchQuery, ignoreCase = true) || it.id.contains(searchQuery, ignoreCase = true) }) { team ->
                TeamListItem(team = team)
            }
        }
    }
}

@Composable
fun TeamListItem(team: Team) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "${team.name} (ID#${team.id})",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${team.members} members",
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                )
            }

            when (team.membershipState) {
                TeamMembershipState.NOT_A_MEMBER -> Button(
                    onClick = { /* Handle Join */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Coral),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Join", color = Color.White)
                }
                TeamMembershipState.MEMBER -> Button(
                    onClick = { /* Handle Leave */ },
                    colors = ButtonDefaults.buttonColors(containerColor = LightPrimaryContainer, contentColor = CocoaBrown),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Leave")
                }
                TeamMembershipState.REQUESTED -> Button(
                    onClick = { /* Handle Request Sent */ },
                    enabled = false,
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = SurfaceVariantLight,
                        disabledContentColor = CocoaBrown
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Requested")
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun TeamSearchScreenPreview() {
//    HuddleUpTheme() {
//        TeamSearchScreen(rememberNavController())
//    }
//}