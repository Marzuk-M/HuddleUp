package com.example.huddleup.myteams

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huddleup.sharedcomponents.PageHeader
import com.example.huddleup.ui.theme.CocoaBrown
import com.example.huddleup.ui.theme.LightPrimaryContainer

@Composable
fun MyTeamsScreen(
    navController: NavController,
    viewModel: MyTeamsViewModel = viewModel()
) {
    val myTeams by viewModel.myTeams.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getMyTeams()
    }

    Scaffold (topBar = { PageHeader(title = "My Teams") }) {
        Column(
            modifier = Modifier.padding(top = it.calculateTopPadding(), start = 12.dp, end = 12.dp)
        ) {
            if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (myTeams.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Looks like you're not in any Team :(")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
                ) {
                    items(myTeams) { team ->
                        TeamListItem(
                            team = team,
                            onNavigate = { navController.navigate("team_details/${team.id}") },
                            openChat = { navController.navigate("chat/${team.id}") },
                            leaveTeam = { viewModel.leaveTeam(team.id) }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun TeamListItem(
    team: Team,
    onNavigate: () -> Unit,
    openChat: () -> Unit,
    leaveTeam: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = onNavigate
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
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = "#${team.id}",
                    style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${team.members} members",
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                )
            }

            Row (horizontalArrangement = Arrangement.spacedBy(8.dp) ) {
                IconButton(onClick = openChat) {
                    Icon(
                        imageVector = Icons.Filled.ChatBubble,
                        contentDescription = "Open Chat"
                    )
                }

                Button(
                    onClick = leaveTeam,
                    colors = ButtonDefaults.buttonColors(containerColor = LightPrimaryContainer, contentColor = CocoaBrown),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Leave",
                        color = CocoaBrown,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}