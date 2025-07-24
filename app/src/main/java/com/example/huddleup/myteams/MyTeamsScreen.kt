package com.example.huddleup.myteams

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.huddleup.ui.theme.Cream
import com.example.huddleup.ui.theme.RoseQuartz
import androidx.navigation.NavController

// Reuse Team and TeamMembershipState from TeamSearchScreen
import com.example.huddleup.teamsearch.Team
import com.example.huddleup.teamsearch.TeamMembershipState
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@Composable
fun MyTeamsScreen(
    navController: NavController
) {
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    // This will be populated from Firebase in the future
    var teams by remember { mutableStateOf<List<Team>>(emptyList()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        Text(
            text = "My Teams",
            style = MaterialTheme.typography.headlineMedium.copy(color = Color.Black, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(24.dp)
        )
        when {
            loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: $error", color = Color.Red)
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(teams) { team ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                // Remove clickable for now
                                ,
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            colors = CardDefaults.cardColors(containerColor = RoseQuartz)
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
                                        text = team.name,
                                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${team.members} members",
                                        style = TextStyle(fontSize = 14.sp, color = Color.DarkGray)
                                    )
                                }
                                Text(
                                    text = "View",
                                    style = TextStyle(fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp),
                                    modifier = Modifier
                                        .background(Color(0xFFE75480), RoundedCornerShape(8.dp))
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}