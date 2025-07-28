package com.example.huddleup.createteam

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.huddleup.sharedcomponents.PageHeader

@Composable
fun CreateTeamScreen(
    navController: NavController,
    viewModel: CreateTeamViewModel = viewModel()
) {
    var teamName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedSport by remember { mutableStateOf("") }

    Scaffold(topBar = { PageHeader(title = "Create New Team") }) {
        Column(
            modifier = Modifier.padding(top = it.calculateTopPadding(), start = 12.dp, end = 12.dp)
        ) {
            OutlinedTextField(
                value = teamName,
                onValueChange = { teamName = it },
                label = { Text("Team Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = selectedSport,
                onValueChange = { selectedSport = it },
                label = { Text("Sport Type") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.createTeam(
                        name = teamName,
                        description = description,
                        sport = selectedSport,
                        onSuccess = { navController.popBackStack() },
                        onError = { /* Handle error */ }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text("Create Team")
            }
        }
    }
}
