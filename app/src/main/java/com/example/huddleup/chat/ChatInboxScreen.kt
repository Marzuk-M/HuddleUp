package com.example.huddleup.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huddleup.sharedcomponents.HUTextFieldSpacer
import com.example.huddleup.sharedcomponents.PageHeader


@Composable
fun ChatInboxScreen(
    navController: NavController
) {
    Scaffold(topBar = { PageHeader(title = "Your Inbox") }) {
        Column(
            modifier = Modifier.padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp)
        ) {
            HUTextFieldSpacer()
        }
    }
}