package com.example.huddleup.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// 1) Your message model
data class Message(
    val id: String,
    val text: String,
    val timestamp: Long,
    val isMine: Boolean
)

@Composable
fun GroupChatScreen(navController: NavController) {
    // 2) draft state for the input bar
    var draft by remember { mutableStateOf("") }

    // 3) placeholder messages — delete or replace when you wire up real data
    val messages = listOf(
        Message("1", "Hey Guys, I can’t make it to Wednesday’s game.", 0L, false),
        Message("2", "No worries man",                         0L, false),
        Message("3", "I also can’t make it to Wednesday’s game.", 0L, false),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TEAM NAME") },
                actions = {
                    IconButton(onClick = { /* TODO: open overflow menu */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                }
            )
        },
        bottomBar = {
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
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )

                Spacer(Modifier.width(4.dp))

                IconButton(
                    onClick = {
                        // TODO: viewModel.sendMessage(draft)
                        draft = ""
                    }
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Send")
                }
            }
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            itemsIndexed(messages) { index, msg ->
                // show timestamp separator before the 3rd bubble
                if (index == 2) {
                    TimeSeparator("5 pm")
                }
                ChatBubble(msg)
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
    val textColor   = if (message.isMine) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalArrangement = arrangement
    ) {
        Box(
            modifier = Modifier
                .background(color = bgColor, shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = message.text,
                color = textColor,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
