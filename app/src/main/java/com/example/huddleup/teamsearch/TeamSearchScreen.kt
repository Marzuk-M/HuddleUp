package com.example.huddleup.teamsearch

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.huddleup.ui.theme.*

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    placeholder: String
) {
    TextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        placeholder = { Text(placeholder) },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search Icon")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
    )
}

@Composable
fun TeamSearchScreen(
    navController: NavController,
    viewModel: TeamSearchViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val searchResults by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Trigger initial search when screen loads
    LaunchedEffect(Unit) {
        viewModel.searchTeams("")
    }
    
    // Search when query changes
    LaunchedEffect(searchQuery) {
        viewModel.searchTeams(searchQuery)
    }

    Scaffold (
        topBar = {
            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                placeholder = "Search by team name or ID..."
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(top = it.calculateTopPadding(), start = 12.dp, end = 12.dp)
        ) {
            if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (searchResults.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = if (searchQuery.isEmpty()) "No teams found" else "No teams match your search")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
                ) {
                    items(searchResults) { team ->
                        TeamListItem(
                            team = team,
                            onNavigate = { /* TODO: navController.navigate("details/${team.id}") */ },
                            onJoin = { viewModel.sendJoinRequest(team.id, searchQuery) },
                            onLeave = { viewModel.leaveTeam(team.id, searchQuery) },
                            onCancelRequest = { viewModel.unsendJoinRequest(team.id, searchQuery) }
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
    onJoin: () -> Unit,
    onLeave: () -> Unit,
    onCancelRequest: () -> Unit
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

            when (team.membershipState) {
                TeamMembershipState.NOT_A_MEMBER -> Button(
                    onClick = onJoin,
                    colors = ButtonDefaults.buttonColors(containerColor = Coral),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Join", color = Color.White)
                }
                TeamMembershipState.MEMBER -> Button(
                    onClick = onLeave,
                    colors = ButtonDefaults.buttonColors(containerColor = LightPrimaryContainer, contentColor = CocoaBrown),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Leave")
                }
                TeamMembershipState.REQUESTED -> Button(
                    onClick = onCancelRequest,
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = SurfaceVariantLight,
                        disabledContentColor = CocoaBrown
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}
