package com.example.huddleup.settings

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
import androidx.compose.runtime.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.draw.rotate
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huddleup.Routes
import com.example.huddleup.auth.AuthService
import com.example.huddleup.sharedcomponents.HUDividerWithText

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = viewModel()
) {
    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val username by viewModel.username.collectAsState()
    val memberSince by viewModel.memberSince.collectAsState()
    val notificationEnabled by viewModel.notificationEnabled.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isLoadingName by viewModel.isLoadingName.collectAsState()
    val isLoadingNotif by viewModel.isLoadingNotif.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUserProfile()
    }

    Scaffold (topBar = { PageHeader(title = "Settings") }) {
        Column(
            modifier = Modifier.padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp)
        ) {

            LazyColumn(modifier = Modifier.weight(1f)) {

                // Profile
                item {
                    HUDividerWithText(text = "Profile")
                    if (isLoading || isLoadingName) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    } else {
                        EditableUserProfileCard(
                            name = name,
                            email = email,
                            username = username,
                            memberSince = memberSince,
                            onNameChange = { viewModel.updateName(it) }
                        )
                    }
                }


                // Notifications toggle
                item {
                    HUDividerWithText(text = "Notification")
                    if (isLoading || isLoadingNotif) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Notifications",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Enable push notification on your phone",
                                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                                )
                            }
                            Switch(
                                checked = notificationEnabled,
                                onCheckedChange = { viewModel.updateNotification(!notificationEnabled) }
                            )
                        }
                    }
                }


                // Help and Support
                item {
                    HUDividerWithText(text = "Help and Support")
                    HelpSupportExpandableItem(
                        title = "Help and Support",
                        subtitle = "Policy Details",
                        policyText = "Here is our policy: We value your privacy and ensure your data is protected. We don't have any third party connections and your data is secure."
                    )
                }

                // About
                item {
                    HUDividerWithText(text = "About this App")
                    HelpSupportExpandableItem(
                        title = "About",
                        subtitle = "More Information",
                        policyText = "HuddleUp is a platform dedicated to helping individuals and teams stay connected, organized, and inspired. We believe in building intuitive tools that promote collaboration, personal growth, and productivity, whether you're planning your next meetup or just checking in with yourself. Our mission is to empower users through thoughtful design and reliable technology that supports real-world connection."
                    )
                }
            }

            // Logout button at the bottom
            Button(
                onClick = {
                    AuthService.logout {
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.DASHBOARD) { inclusive = true }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text("Logout")
            }
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
                Text(text = title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
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
fun EditableUserProfileCard(
    name: String,
    email: String,
    username: String,
    memberSince: String,
    onNameChange: (String) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var editableName by remember { mutableStateOf(name) }

    LaunchedEffect(name) {
        if (!isEditing) {
            editableName = name
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isEditing) {
                    OutlinedTextField(
                        value = editableName,
                        onValueChange = { editableName = it },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.titleMedium,
                        label = { Text("Name") }
                    )
                    IconButton(onClick = {
                        isEditing = false
                        onNameChange(editableName)
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
                    }
                } else {
                    Text(
                        text = editableName,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { isEditing = true }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Name")
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = username,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = email,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Member since $memberSince",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
            )
        }
    }
}

