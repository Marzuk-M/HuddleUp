@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.huddleup.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huddleup.sharedcomponents.PageHeader
import java.util.*

private data class Message(
    val id: String,
    val text: String,
    val timestamp: Long,
    val isMine: Boolean
)

@Composable
fun ChatScreen(
    navController: NavController,
    teamId: String?
) {
    var draft by remember { mutableStateOf("") }

    // 1) Use a mutableStateListOf so Compose will recompose on adds
    val messages = remember {
        mutableStateListOf(
            Message("1", "Hey Guys, I can’t make it to Wednesday’s game.", 0L, false),
            Message("2", "No worries man",                         0L, false),
            Message("3", "I also can’t make it to Wednesday’s game.", 0L, false)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                title = { Text("Team X (${teamId.orEmpty()})") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                itemsIndexed(messages) { index, msg ->
                    if (index == 2) TimeSeparator("5 pm")
                    ChatBubble(msg)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = draft,
                    onValueChange = { draft = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Type a message") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(onClick = {
                    if (draft.isNotBlank()) {
                        // 2) Create a new Message and append it
                        messages += Message(
                            id        = UUID.randomUUID().toString(),
                            text      = draft.trim(),
                            timestamp = System.currentTimeMillis(),
                            isMine    = true
                        )
                        draft = ""
                    }
                }) {
                    Icon(Icons.Default.Send, contentDescription = "Send message")
                }
            }
        }
    }
}

@Composable
private fun TimeSeparator(label: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}

@Composable
private fun ChatBubble(message: Message) {
    val arrangement = if (message.isMine) Arrangement.End else Arrangement.Start
    val bgColor     = if (message.isMine) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val txtColor    = if (message.isMine) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = arrangement
    ) {
        Box(
            modifier = Modifier
                .background(color = bgColor, shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = message.text,
                color = txtColor,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
