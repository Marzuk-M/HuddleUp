package com.example.huddleup.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.huddleup.chat.ui.ChatScreenHeader

@Composable
fun ChatScreen(
    navController: NavController,
    teamId: String?,
    viewModel: ChatViewModel = viewModel(),
) {
    val teamName by viewModel.teamName.collectAsState()
    val messages by viewModel.messages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val currentUsername by viewModel.currentUsername.collectAsState()

    // Messages + Draft State
    var draft by remember { mutableStateOf("") }

    // Load Metadata
    LaunchedEffect(teamId) {
        if (teamId != null) {
            viewModel.loadMetadata(teamId)
        }
    }

    // Scroll to bottom whenever messages change
    val listState = rememberLazyListState()
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.scrollToItem(0)
        }
    }

    Scaffold (topBar = {
        if (!isLoading) {
            ChatScreenHeader(
                teamName = teamName,
                teamId = teamId,
                viewTeamDetail = { navController.navigate("team_details/${teamId}") }
            )
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
                reverseLayout = true
            ) {
                item {
                    messages.forEach { message ->
                        ChatBubble(
                            message = message,
                            isMine = message.sender.username == currentUsername
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = draft,
                    onValueChange = { draft = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Type a message") },
                    maxLines = 3,
                    singleLine = false
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(onClick = { /* TODO: handle onclick */ }) {
                    Icon(Icons.Default.Send, contentDescription = "Send")
                }
            }
        }
    }
}
@Composable
fun ChatBubble(message: Message, isMine: Boolean) {
    val arrangement = if (isMine) Arrangement.End else Arrangement.Start
    val bgColor     = if (isMine) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    val txtColor    = if (isMine) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
    val alignment   = if (isMine) Alignment.End else Alignment.Start

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = arrangement
    ) {
        Column(horizontalAlignment = alignment) {
            Text(
                text = if (isMine) "You" else message.sender.name,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
            )

            Box(
                modifier = Modifier
                    .background(color = bgColor, shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = message.content,
                    color = txtColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}