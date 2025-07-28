package com.example.huddleup.teamdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.huddleup.Routes
import com.example.huddleup.auth.AuthService
import com.example.huddleup.sharedcomponents.HUDividerWithText
import com.example.huddleup.sharedcomponents.PageHeader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TeamDetailsScreen(
    navController: NavController,
    teamId: String?,
    viewModel: TeamDetailsViewModel = viewModel()
) {
    val teamDetails by viewModel.teamDetails.collectAsState()
    val teamName by viewModel.teamName.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Load Metadata
    LaunchedEffect(teamId) {
        if (teamId != null) {
            viewModel.loadTeamDetails(teamId)
        }
    }

    Scaffold(topBar = { PageHeader(title = teamName) }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            if (isLoading || teamDetails == null) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Team Info
                    item {
                        Text(
                            text = "ID# ${teamDetails!!.teamId}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = "Sport: ${teamDetails!!.sport}",
                            fontSize = 16.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = "Total Members: ${teamDetails!!.members.size}",
                            fontSize = 16.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        HUDividerWithText("Team Description")
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "${teamDetails!!.description}" ,
                            fontSize = 16.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        HUDividerWithText("Team Members")
                        Spacer(modifier = Modifier.height(16.dp))

                        teamDetails!!.members.forEach { member ->
                            Text(
                                text = "${member.name} (${member.username})",
                                fontSize = 16.sp,
                                modifier = Modifier.align(Alignment.Start)
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        HUDividerWithText("Game Stats")
                        Spacer(modifier = Modifier.height(16.dp))

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            teamDetails!!.pastGames.forEachIndexed { index, game ->
                                GameStatCard(
                                    result = game.result,
                                    team = game.opponent,
                                    date = game.date,
                                )

                                if (index < teamDetails!!.pastGames.lastIndex) {
                                    Spacer(modifier = Modifier.height(12.dp))
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            thickness = Dp(1f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { viewModel.leaveTeam(teamDetails!!.teamId) },
                            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                        ) {
                            Text("Leave")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GameStatCard(
    result: GameOutcome,
    team: String,
    date: Long,
) {
    val dateFormater = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val formattedDate = dateFormater.format(Date(date))
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFDCCCCC)),
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
                Text(text = result.name, fontWeight = FontWeight.Bold)
                Text(text = "Competing Team: $team")
                Text(text = "Date: $formattedDate")
            }
        }
    }
}
